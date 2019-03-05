package com.vareger.stock;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.exceptions.TransactionException;

import com.vareger.stock.cex.models.Order;

public interface Stock {

	double getEthPrice() throws IOException;

	double getEthBalance() throws IOException;

	double getUsdBalance() throws IOException;

	Order sellEth(double amount) throws IOException;

	Order buyEth(double amount) throws IOException;

	String getEthDepositAddress() throws IOException;
	
	double getEthDepositFeePrecent() throws IOException;
	
	List<Order> openOrders() throws IOException;

}
