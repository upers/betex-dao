package com.vareger.stock.cex;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.vareger.stock.Stock;
import com.vareger.stock.cex.models.CexBalance;
import com.vareger.stock.cex.models.CexCryptoAddress;
import com.vareger.stock.cex.models.CexLastPrice;
import com.vareger.stock.cex.models.Fee;
import com.vareger.stock.cex.models.Order;

public class Cex implements Stock {
	
	private static final Logger log = LoggerFactory.getLogger(Cex.class);
	
	private final static String ETH = "eth";
	
	private final static String USD = "usd";
	
	private final static int ETH_DECIMALS = 8;
	
//	private final static int USD_DECIMALS = 2;
	
	private final static String SELL = "sell";
	
	private final static String BUY = "buy";
	
	private CexAPI cexApi;

	private ObjectMapper mapper;
	
	public Cex(CexAPI cexApi, ObjectMapper mapper) {
		this.cexApi = cexApi;
		this.mapper = mapper;
	}
	
	@Override
	public double getEthPrice() throws IOException {
		String resp = cexApi.lastPrice(ETH, USD);
		CexLastPrice cexLastPrice = mapper.readValue(resp, CexLastPrice.class);
		log.trace(resp);
		
		return cexLastPrice.getLastPrice();
	}

	@Override
	public double getEthBalance() throws IOException {
		String resp = cexApi.balance();
		CexBalance cexBalance = mapper.readValue(resp, CexBalance.class);
		Double ethBalance = cexBalance.getEth().getAvailable();
		log.trace(resp);
		
		return ethBalance;
	}

	@Override
	public double getUsdBalance() throws JsonParseException, JsonMappingException, IOException {
		String resp = cexApi.balance();
		CexBalance cexBalance = mapper.readValue(resp, CexBalance.class);
		Double usdBalance = cexBalance.getUsd().getAvailable();
		log.trace(resp);
		
		return usdBalance;
	}

	@Override
	public Order sellEth(double eth) throws IOException {
		double ethPrice = getEthPrice();
		eth = round(eth, ETH_DECIMALS);
		String resp = cexApi.placeOrder(ETH, USD, SELL, eth, ethPrice);
		
		Order order = mapper.readValue(resp, Order.class);
		
		return order;
	}

	@Override
	public Order buyEth(double eth) throws IOException {
		double ethPrice = getEthPrice();
		eth = round(eth, ETH_DECIMALS);
		
		log.trace("Eth: " + eth + " Eth price: " + ethPrice);
		String resp = cexApi.placeOrder(ETH, USD, BUY, eth, ethPrice);
		log.trace(resp);
		
		Order order = mapper.readValue(resp, Order.class);
		
		return order;
	}

	@Override
	public String getEthDepositAddress() throws IOException {
		String resp = cexApi.getCryptoAddress(ETH);
		CexCryptoAddress cryptoAddress = mapper.readValue(resp, CexCryptoAddress.class);
		log.trace(resp);
		
		return cryptoAddress.getAddress();
	}
	
	public double getEthSellFeePercent() throws IOException {
		String resp = cexApi.getMyFee();
		System.out.println(resp);
		Fee fee = mapper.readValue(resp, Fee.class);
		
		return fee.getSellTakerPercent();
	}
	
	public double getEthBuyFeePercent() throws IOException {
		String resp = cexApi.getMyFee();
		System.out.println(resp);
		Fee fee = mapper.readValue(resp, Fee.class);
		
		return fee.getBuyTakerPercent();
	}
	
	@Override
	public List<Order> openOrders() throws IOException {
		String strOrders = cexApi.openOrders(ETH, USD);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Order.class);
		List<Order> orders = mapper.readValue(strOrders, collectionType);
		
		return orders;
	}
	
	public String cancelOrder(String id) throws IOException {
		return cexApi.cancelOrder(id);
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    return bd.doubleValue();
	}

	@Override
	public double getEthDepositFeePrecent() throws IOException {
		return 0;
	}

}
