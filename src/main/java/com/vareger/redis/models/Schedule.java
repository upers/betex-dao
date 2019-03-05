package com.vareger.redis.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vareger.config.ChartConfig;

@JsonPropertyOrder({ "interval", "timestamp" })
public class Schedule {
		
		private Integer interval;
		
		private List<Date> pointsDate;
		
		public Integer getInterval() {
			return interval;
		}

		public void setInterval(Integer interval) {
			this.interval = interval;
		}
		
		public List<Date> getPointsDate() {
			return pointsDate;
		}

		public void updateTimestamps() {
			long currTimeSec = System.currentTimeMillis() / 1000;
			long firstPointSec = (currTimeSec - currTimeSec % interval) - interval * (ChartConfig.CHART_POINTS_LIMIT - 1);
			
			List<Date> buffTimestamp = new ArrayList<>(ChartConfig.CHART_POINTS_LIMIT);
			buffTimestamp.add(new Date(firstPointSec * 1000));
			
			long prevPoint = firstPointSec;
			for (int i = 1; i < ChartConfig.CHART_POINTS_LIMIT; i++) {
				prevPoint += interval;
				buffTimestamp.add(new Date(prevPoint * 1000));
			}
			pointsDate = buffTimestamp;
		}

}
