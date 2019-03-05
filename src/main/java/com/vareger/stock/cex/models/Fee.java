package com.vareger.stock.cex.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fee {
	
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	public double getSellTakerPercent() {
		return data.getEthUsd().getSellTaker();
	}
	
	public double getBuyTakerPercent() {
		return data.getEthUsd().getBuyTaker();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Data {
		@JsonProperty("ETH:USD")
		private CurrFee ethUsd;

		public CurrFee getEthUsd() {
			return ethUsd;
		}

		public void setEthUsd(CurrFee ethUsd) {
			this.ethUsd = ethUsd;
		}
		
		/**
		 * Maker fee it's fee for when you place order.
		 * Taker fee it's fee for when you buy or sell in open order.
		 * @author MykhailoSavchuk
		 *
		 */
		@JsonIgnoreProperties(ignoreUnknown = true)
		public class CurrFee {
			
			double sellMaker;
			
			@JsonProperty("sell")
			double sellTaker;
			
			double buyMaker;
			
			@JsonProperty("buy")
			double buyTaker;

			public double getSellMaker() {
				return sellMaker;
			}

			public void setSellMaker(double sellMaker) {
				this.sellMaker = sellMaker;
			}

			public double getSellTaker() {
				return sellTaker;
			}

			public void setSellTaker(double sellTaker) {
				this.sellTaker = sellTaker;
			}

			public double getBuyMaker() {
				return buyMaker;
			}

			public void setBuyMaker(double buyMaker) {
				this.buyMaker = buyMaker;
			}

			public double getBuyTaker() {
				return buyTaker;
			}

			public void setBuyTaker(double buyTaker) {
				this.buyTaker = buyTaker;
			}
		}
	}
	
}
