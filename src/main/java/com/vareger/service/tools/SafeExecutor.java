package com.vareger.service.tools;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.web3j.protocol.Web3j;

import com.vareger.interfaces.Supplier;

public class SafeExecutor {
	
	private static final Logger log = LoggerFactory.getLogger(SafeExecutor.class);
	
	private final ThreadPoolTaskScheduler threadPool;
	
	private final long interval;
	
	private final Web3j web3j;
	
	public SafeExecutor(ThreadPoolTaskScheduler threadPool, Web3j web3j, long interval) {
		this.threadPool = threadPool;
		this.interval = interval;
		this.web3j = web3j;
	}
	
	/**
	 * Supply something in safe mode.<br>
	 * If there no connection with node or we get error during supply.
	 * This method will attempt again in {@link SafeExecutor#interval}
	 * supplier
	 */
	public <T> CompletableFuture<T> supplyEthAsync(Supplier<T> supplier) {
		final CompletableFuture<T> future = new CompletableFuture<T>();
		
		SafeEthSupplier<T> task = new SafeEthSupplier<>(future, supplier);
		threadPool.execute(task);
		
		return future;
	}
	
	/**
	 * Supply something in safe mode.<br>
	 * If there some exceptions during supply.
	 * This method will attempt again in {@link SafeExecutor#interval}
	 * supplier
	 */
	public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
		final CompletableFuture<T> future = new CompletableFuture<T>();
		
		SafeSupplier<T> task = new SafeSupplier<>(future, supplier);
		threadPool.execute(task);
		
		return future;
	}
	
	/**
	 * Supply something in safe mode.<br>
	 * If there no connection with node or we get error during supply.
	 * This method will attempt again in {@link SafeExecutor#interval}
	 * supplier
	 * threadPool
	 */
	public <T> CompletableFuture<T> supplyEthAsync(Supplier<T> supplier, ThreadPoolTaskScheduler threadPool) {
		final CompletableFuture<T> future = new CompletableFuture<T>();
		
		SafeEthSupplier<T> task = new SafeEthSupplier<T>(future, supplier, threadPool);
		threadPool.execute(task);
		
		return future;
	}
	
	/**
	 * Run something in safe mode.<br>
	 * If there no connection with node or we get error during run.
	 * This method will attempt again in {@link SafeExecutor#interval}
	 * supplier
	 * threadPool
	 */
	public CompletableFuture<Void> runEthAsync(com.vareger.interfaces.Runnable rannable, ThreadPoolTaskScheduler threadPool) {
		final CompletableFuture<Void> future = new CompletableFuture<>();
		
		SafeEthRunner task = new SafeEthRunner(future, rannable, threadPool);
		threadPool.execute(task);
		
		return future;
	}
	
	/**
	 * Run something in safe mode.<br>
	 * If there no connection with node or we get error during run.
	 * This method will attempt again in {@link SafeExecutor#interval}
	 * supplier
	 * threadPool
	 */
	public CompletableFuture<Void> runEthAsync(com.vareger.interfaces.Runnable rannable) {
		final CompletableFuture<Void> future = new CompletableFuture<>();
		
		SafeEthRunner task = new SafeEthRunner(future, rannable);
		threadPool.execute(task);
		
		return future;
	}
	
	private class SafeEthSupplier<T> implements Runnable {

		private final CompletableFuture<T> future;

		private ThreadPoolTaskScheduler threadPool;

		private final Supplier<T> supplier;
		
		public SafeEthSupplier(CompletableFuture<T> future, Supplier<T> supplier, ThreadPoolTaskScheduler threadPool) {
			this.future = future;
			this.supplier = supplier;
			this.threadPool = threadPool;
		}
		
		public SafeEthSupplier(CompletableFuture<T> future, Supplier<T> supplier) {
			this.future = future;
			this.threadPool = SafeExecutor.this.threadPool;
			this.supplier = supplier;
		}

		@Override
		public void run() {
			try {
				chechConnection();
				
				T subscription = supplier.get();
				
				future.complete(subscription);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				log.error("Error during try supply somthing from ethereum. Try supply in: " + interval + " sec.");

				long attemptTime = System.currentTimeMillis() + interval * 1000;
				threadPool.schedule(SafeEthSupplier.this, new Date(attemptTime));
			}
		}
		
		protected void chechConnection() throws IOException {
			web3j.ethGasPrice().send();
		}

	}
	
	private class SafeSupplier<T> extends SafeEthSupplier<T> {
		public SafeSupplier(CompletableFuture<T> future, Supplier<T> supplier, ThreadPoolTaskScheduler threadPool) {
			super(future, supplier, threadPool);
		}
		
		public SafeSupplier(CompletableFuture<T> future, Supplier<T> supplier) {
			super(future, supplier);
		}
	}
	
	private class SafeEthRunner implements Runnable {

		private final CompletableFuture<Void> future;

		private ThreadPoolTaskScheduler threadPool;

		private final com.vareger.interfaces.Runnable runnable;
		
		public SafeEthRunner(CompletableFuture<Void> future, com.vareger.interfaces.Runnable runnable, ThreadPoolTaskScheduler threadPool) {
			this.future = future;
			this.runnable = runnable;
			this.threadPool = threadPool;
		}
		
		public SafeEthRunner(CompletableFuture<Void> future, com.vareger.interfaces.Runnable runnable) {
			this.future = future;
			this.threadPool = SafeExecutor.this.threadPool;
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				chechConnection();
				
				runnable.run();
				
				future.complete(null);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				log.error("Error during try run. Try run in: " + interval + " sec.");

				long attemptTime = System.currentTimeMillis() + interval * 1000;
				threadPool.schedule(SafeEthRunner.this, new Date(attemptTime));
			}
		}
		
		private void chechConnection() throws IOException {
			web3j.ethGasPrice().send();
		}

	}
	
}
