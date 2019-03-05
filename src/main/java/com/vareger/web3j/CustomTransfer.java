package com.vareger.web3j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Async;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

/**
 * Class for performing Ether transactions on the Ethereum blockchain.
 */
public class CustomTransfer extends ManagedTransaction {

    public final BigInteger gasLimit;

    public CustomTransfer(Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
        gasLimit = BigInteger.valueOf(200000l);
    }
    
    public CustomTransfer(Web3j web3j, TransactionManager transactionManager, BigInteger gasLimit) {
        super(web3j, transactionManager);
        this.gasLimit = gasLimit;
    }

    /**
     * Given the duration required to execute a transaction, asyncronous execution is strongly
     *
     * toAddress destination address
     * value amount to send
     * unit of specified send
     *
     * @return {@link Optional} containing our transaction receipt
     * @throws ExecutionException if the computation threw an
     *                            exception
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     * @throws TransactionException 
     */
    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit)
            throws IOException, InterruptedException, TransactionException{


        return send(toAddress, value, unit, GAS_PRICE, gasLimit);
    }

    private TransactionReceipt send(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit) throws IOException, InterruptedException, TransactionException {

        BigDecimal weiValue = Convert.toWei(value, unit);
        if (!Numeric.isIntegerValue(weiValue)) {
            throw new UnsupportedOperationException(
                    "Non decimal Wei value provided: " + value + " " + unit.toString()
                            + " = " + weiValue + " Wei");
        }

        return send(toAddress, "", weiValue.toBigIntegerExact(), gasPrice, gasLimit);
    }

    /**
     * Execute the provided function as a transaction asynchronously. This is intended for one-off
     * fund transfers. For multiple, create an instance.
     *
     * toAddress destination address
     * value amount to send
     * unit of specified send
     *
     * @return {@link Future} containing executing transaction
     */
    public CompletableFuture<TransactionReceipt> sendFundsAsync(
            String toAddress, BigDecimal value, Convert.Unit unit) {
        return Async.run(() -> send(toAddress, value, unit));
    }

    public CompletableFuture<TransactionReceipt> sendFundsAsync(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit) {
        return Async.run(() -> send(toAddress, value, unit, gasPrice, gasLimit));
    }
    
    public BigInteger getGasLimit() {
    	return gasLimit;
    }
    
    public String getFromAddress() {
    	return transactionManager.getFromAddress();
    }

}
