package com.vareger.config;

public interface ChartConfig {
	/**
	 * 	1w 3d 1d 12h 6h 4h 2h 1h 30m 15m 5m 3m 1m<br> 
	 *  in seconds
	 */
	Integer[] ALLOWED_INTERVALS = {60, 180, 300, 900, 1800, 3600, 7200, 14400, 21600, 43200, 86400, 259200, 604800};
	
	/**
	 * How much currency rate points will be selected by query to database
	 */
	Integer CHART_POINTS_LIMIT = 100;
}
