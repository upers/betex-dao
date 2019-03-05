package com.vareger.web3j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Class which handle transactions.<br>
 * Method {@code handleTx(txHash)} will check transaction status and confirmations.<br>
 * If transaction success then future will returns true.<br>
 * If transaction fail then future will returns false.<br>
 * <b>All methods actions do in separate thread in thread pool.</b>
 * 
 * @author MykhailoSavchuk
 *
 */
public class TransactionHandler {

	private final static Logger log = LoggerFactory.getLogger(TransactionHandler.class);

	protected final long interval;

	protected final Web3j web3j;

	protected final BigInteger confAmount;

	protected ThreadPoolTaskScheduler threadPoolTaskScheduler;

	/**
	 * web3j
	 * threadPoolTaskScheduler
	 */
	public TransactionHandler(Web3j web3j, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		this.web3j = web3j;
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		this.confAmount = BigInteger.valueOf(12);
		this.interval = 20;
	}

	/**
	 * web3j
	 * threadPoolTaskScheduler
	 * interval
	 */
	public TransactionHandler(Web3j web3j, ThreadPoolTaskScheduler threadPoolTaskScheduler, int interval) {
		this.web3j = web3j;
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		this.interval = interval;
		this.confAmount = BigInteger.valueOf(12);
	}

	/**
	 * web3j
	 * threadPoolTaskScheduler
	 * interval
	 * confAmount
	 */
	public TransactionHandler(Web3j web3j, ThreadPoolTaskScheduler threadPoolTaskScheduler, int interval, int confAmount) {
		this.web3j = web3j;
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		this.interval = interval;
		this.confAmount = BigInteger.valueOf(confAmount);
	}

	/**
	 * Check transaction status and confirmations.<br>
	 * <b>Check in a separate thread in thread pool.</b>
	 * txHash
	 * @return {@link CompletableFuture}
	 */
	public CompletableFuture<Boolean> handleTx(String txHash) {
		final CompletableFuture<Boolean> future = new CompletableFuture<>();
		ConfirmationTask task = new ConfirmationTask(txHash, confAmount, 1200, future);
		
		long startTime = System.currentTimeMillis() + (interval / 3 * 1000);
		threadPoolTaskScheduler.schedule(task, new Date(startTime));
		
		return future;
	}
	
	/**
	 * Check transaction status and confirmations.<br>
	 * <b>Check in a separate thread in thread pool.</b>
	 * txHash
	 * confAmount
	 * @return {@link CompletableFuture}
	 */
	public CompletableFuture<Boolean> handleTx(String txHash, BigInteger confAmount) {
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		ConfirmationTask task = new ConfirmationTask(txHash, confAmount, 1200, future);
		
		long startTime = System.currentTimeMillis() + (interval * 1000);
		threadPoolTaskScheduler.schedule(task, new Date(startTime));
		
		return future;
	}

	/**
	 * Check transaction status and confirmations.<br>
	 * <b>Check in a separate thread in thread pool.</b>
	 * txHash
	 * confAmount
	 * maxTimeToWait
	 * @return {@link CompletableFuture}
	 */
	public CompletableFuture<Boolean> handleTx(String txHash, BigInteger confAmount, int maxTimeToWait) {
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		ConfirmationTask task = new ConfirmationTask(txHash, confAmount, maxTimeToWait, future);

		long startTime = System.currentTimeMillis() + (interval * 1000);
		threadPoolTaskScheduler.schedule(task, new Date(startTime));

		return future;
	}

	private class ConfirmationTask implements Runnable {

		private String txHash;
		
		private BigInteger confAmount;
		
		private final CompletableFuture<Boolean> future;

		private final long maxTimeToWait;

		private final Date limitDate;

		public ConfirmationTask(String txHash, BigInteger confAmount, int maxTimeToWait, CompletableFuture<Boolean> future) {
			this.txHash = txHash;
			this.confAmount = confAmount;
			this.future = future;
			this.maxTimeToWait = maxTimeToWait;
			this.limitDate = new Date(System.currentTimeMillis() + maxTimeToWait * 1000);
			if (maxTimeToWait < 0)
				throw new RuntimeException("Max time to wait must be positive number but got: " + maxTimeToWait);
		}

		@Override
		public void run() {
			web3j.ethGetTransactionByHash(txHash).sendAsync().thenAccept(ethTx -> {
				try {
					Transaction transaction = ethTx.getResult();
					if (transaction != null) {
						String blockHash = transaction.getBlockHash();
						String cleanHex = (blockHash.startsWith("0x")) ? blockHash.substring(2, blockHash.length()) : blockHash;

						if (new BigInteger(cleanHex, 16).compareTo(BigInteger.ZERO) == 0) {
							log.debug("Transaction still not mined.\nApplication will check transaction status in: " + interval + "seconds. TxHash: " + txHash + " GasPrice: " + transaction.getGasPrice() + " Faile date if not be mined: " + limitDate);
							//If transaction not mined to long fail it.
							if (new Date().after(limitDate)) {
								log.debug("Transaction hasn't been mined for: " + maxTimeToWait + ". Execute on fail lambda.");
								onFail();
							}
							else
								checkInFuture();
						} else {
							TransactionReceipt tr = web3j.ethGetTransactionReceipt(transaction.getHash()).send().getResult();
							String status = tr.getStatus();
							if (status != null && !status.equalsIgnoreCase("0x1")) {
								log.error("Transaction status is fail! txHash: " + txHash);
								log.error("Gas used: " + tr.getGasUsed());
								// if tx status fail
								onFail();
							} else {
								BigInteger blockNumber = web3j.ethGetBlockByHash(blockHash, false).send().getBlock().getNumber();
								BigInteger lastBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock().getNumber();
								BigInteger confirmations = lastBlock.subtract(blockNumber);

								if (confirmations.compareTo(ConfirmationTask.this.confAmount) >= 0) {
									log.info("Transaction is successful!! Confirmations amount: " + confirmations + " txHash: " + txHash);
									// if success transaction
									onSuccess();
								} else {
									log.debug("Transacion has only: " + confirmations + " confirmations.");
									// if to low confirmation
									checkInFuture();
								}

							}

						}
					} else {
						log.error("Transaction wasn't add in pool. TxHash: " + txHash + "Execute method on fail.");
						// tx wasn't add in pool
						onFail();
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);

					checkInFuture();
				}
			}).exceptionally(ex -> {
				log.error("Something went wrong with getting information about transaction. Try again in: " + interval + " seconds.");
				
				Date ranDate = new Date(System.currentTimeMillis() + interval * 1000);
				threadPoolTaskScheduler.schedule(ConfirmationTask.this, ranDate);

				return null;
			});
		}

		private void onFail() {
			future.complete(false);
		}

		private void onSuccess() {
			future.complete(true);
		}

		private void checkInFuture() {
			log.debug("Check transaction txHash: " + txHash + " in: " + interval + " seconds.");
			Date ranDate = new Date(System.currentTimeMillis() + interval * 1000);
			threadPoolTaskScheduler.schedule(ConfirmationTask.this, ranDate);
		}
	}

	public BigInteger getConfAmount() {
		return confAmount;
	}

}
