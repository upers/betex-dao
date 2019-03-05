package com.vareger.web3j;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;

public class FastRawTransactionManagerLastNonce extends RawTransactionManager {
    private volatile BigInteger nonce = BigInteger.valueOf(-1L);

    private final Web3j _web3j;

    private final Credentials _credentials;

    public FastRawTransactionManagerLastNonce(Web3j web3j, Credentials credentials, byte chainId) {
        super(web3j, credentials, chainId);
        _web3j = web3j;
        _credentials = credentials;
    }

    public FastRawTransactionManagerLastNonce(Web3j web3j, Credentials credentials) {
        super(web3j, credentials);
        _web3j = web3j;
        _credentials = credentials;
    }

    public FastRawTransactionManagerLastNonce(Web3j web3j, Credentials credentials, TransactionReceiptProcessor transactionReceiptProcessor) {
        super(web3j, credentials, (byte)-1, transactionReceiptProcessor);
        _web3j = web3j;
        _credentials = credentials;
    }

    public FastRawTransactionManagerLastNonce(Web3j web3j, Credentials credentials, byte chainId, TransactionReceiptProcessor transactionReceiptProcessor) {
        super(web3j, credentials, chainId, transactionReceiptProcessor);
        _web3j = web3j;
        _credentials = credentials;
    }

    protected synchronized BigInteger getNonce() throws IOException {
        if (this.nonce.signum() == -1) {
            EthGetTransactionCount ethGetTransactionCount = (EthGetTransactionCount)this._web3j.ethGetTransactionCount(this._credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            this.nonce = ethGetTransactionCount.getTransactionCount();
        } else {
            this.nonce = this.nonce.add(BigInteger.ONE);
        }

        return this.nonce;
    }

    public BigInteger getCurrentNonce() {
        return this.nonce;
    }

    public synchronized void resetNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = (EthGetTransactionCount)this._web3j.ethGetTransactionCount(this._credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        this.nonce = ethGetTransactionCount.getTransactionCount();
    }

    public synchronized void setNonce(BigInteger value) {
        this.nonce = value;
    }

    public Credentials getCredentials() {
        return _credentials;
    }
}
