package com.vareger.web3j;

import java.io.IOException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.TransactionReceiptProcessor;

public class SubstitutingHashProcessor extends TransactionReceiptProcessor {
	
	public SubstitutingHashProcessor(Web3j web3j) {
		super(web3j);
	}

	@Override
    public TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException {
		TransactionReceipt tr = new TransactionReceipt();
		tr.setTransactionHash(transactionHash);
		
        return tr;
    }
	
}