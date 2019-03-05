package com.vareger.web3j;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ChainId;
import org.web3j.tx.FastRawTransactionManager;

public class FastHashTransactionManager extends FastRawTransactionManager {

	public FastHashTransactionManager(Web3j web3j, Credentials credentials) {
		super(web3j, credentials, ChainId.NONE, new SubstitutingHashProcessor(web3j));
	}

	
}