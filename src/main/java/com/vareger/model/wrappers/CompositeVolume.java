package com.vareger.model.wrappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializer;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.models.Ticker;

/**
 * This class representation Composite Volume by murkets in the tickers.<br>
 * Use {@link CompositeVolume#CompositeVolume(List tickers)}.<br>
 * The list of tickers <b>MUST</b> contain tickers with the same pairs but with different markets.<br>
 * @author misha
 *
 */
@JsonInclude(Include.NON_NULL)
public class CompositeVolume {

	@JsonIgnore
	private List<Ticker> tickers;

	private Double totalVolume;
	
	private List<MurcketVolume> murcketsVolumes;
	
	private String currency;
	
	private String baseCurrency;

	public CompositeVolume() {
		
	}

	public CompositeVolume(List<Ticker> tickers) {
		if (tickers == null)
			return;
		
		this.tickers = tickers;
		this.init();
	}
	
	private void init() {
		totalVolume = tickers.stream().mapToDouble((ticker) -> ticker.getVolume()).sum();
		murcketsVolumes = tickers.stream().map((ticker) -> {
			String name = ticker.getMurket().getName();
			Double volume = ticker.getVolume();
			Double price = ticker.getLastPrice();
			Double ratio = BigDecimal.valueOf(volume / totalVolume)
				    .setScale(8, RoundingMode.HALF_UP)
				    .doubleValue();
			Date date = ticker.getDate();
			
			return new MurcketVolume(name, volume, ratio, date, price);
		}).collect(Collectors.toList());
	}
	
	public void merge(CompositeVolume overalVolume) {
		if (overalVolume == null)
			return;
		
		List<MurcketVolume> inMurketsVolumes = overalVolume.getMurcketsVolumes();
		
		List<String> pressentMurketsName = murcketsVolumes.stream()
				.map((m) -> m.getName())
				.collect(Collectors.toList());
		
		for (MurcketVolume inMv : inMurketsVolumes) {
			if (inMv.isExpired())
				continue;
			
			String murketName = inMv.getName();
			
			if (!pressentMurketsName.contains(murketName))
				murcketsVolumes.add(inMv);
			
		}
		
		this.recount();
		Collections.sort(murcketsVolumes);
	}
	
	private void recount() {
		totalVolume = murcketsVolumes.stream().mapToDouble((ticker) -> ticker.getVolume()).sum();
		murcketsVolumes.stream().forEach((ticker) -> {
			ticker.percent = BigDecimal.valueOf(ticker.volume / totalVolume)
				    .setScale(8, RoundingMode.HALF_UP)
				    .doubleValue();
		});
	}
	
	public List<Ticker> getTickers() {
		return tickers;
	}

	public void setTickers(List<Ticker> tickers) {
		this.tickers = tickers;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public List<MurcketVolume> getMurcketsVolumes() {
		return murcketsVolumes;
	}

	public void setMurcketsVolumes(List<MurcketVolume> murcketsVolumes) {
		this.murcketsVolumes = murcketsVolumes;
	}

	@JsonRootName("MurcketVolume")
	@JsonInclude(Include.NON_NULL)
	public static class MurcketVolume implements Comparable<MurcketVolume>{
		private String name;
		
		private Double volume;

		private Double percent;
		
		private Double price;
		
		@JsonDeserialize(using=DoubleToDateDeserializer.class)
		@JsonSerialize(using=DateToTimeestampSerializer.class)
		private Date timestamp;
		
		public MurcketVolume() {}
		
		public MurcketVolume(String name, Double volume, Double precent, Date date, Double price) {
			this.name = name;
			this.volume = volume;
			this.percent = precent;
			this.timestamp = date;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@JsonGetter("value")
		public Double getVolume() {
			return volume;
		}

		public void setVolume(Double volume) {
			this.volume = volume;
		}

		public Double getPercent() {
			return percent;
		}

		public void setPercent(Double Percent) {
			this.percent = Percent;
		}
		
		private boolean isExpired() {
			if (timestamp == null)
				return true;
			
			long time = timestamp.getTime();
			long previous24Hour = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
			if (time < previous24Hour)
				return true;
			
			return false;
		}

		@Override
		public int compareTo(MurcketVolume o) {
			if (o.getVolume() == null)
				return 1;
			
			return o.getVolume().compareTo(volume);
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
}
