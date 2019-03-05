package com.vareger.stock.cex;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CexAPI {
	private String apiEnterPoint;
	protected Integer connectTimout;
	protected Integer readTimeout;
	protected final String username;
	protected final String apiKey;
	protected final String apiSecret;
	protected AtomicInteger nonce;

	/**
	 * Creates a CexAPI Object.
	 * 
	 * user
	 *            Cex.io Username
	 * key
	 *            Cex.io API Key
	 * secret
	 *            Cex.io API Secret
	 */
	public CexAPI(String user, String key, String secret) {
		this.username = user;
		this.apiKey = key;
		this.apiSecret = secret;
		this.nonce = new AtomicInteger((int) (System.currentTimeMillis() / 1000));
		this.connectTimout = 20 * 1000;
		this.readTimeout = 20 * 1000;
		this.apiEnterPoint = "https://cex.io/api/";
	}

	/**
	 * Debug the contents of the a CexAPI Object.
	 * 
	 * @return The CexAPI object data: username, apiKey, apiSecret, and nonce.
	 */
	public String toString() {
		return "{\"username\":\"" + this.username + "\",\"apiKey\":\"" + this.apiKey + "\",\"apiSecret\":\"" + this.apiSecret
				+ "\",\"nonce\":\"" + this.nonce.get() + "\"}";
	}

	/**
	 * Create HMAC-SHA256 signature for our POST call.
	 * 
	 * @return HMAC-SHA256 message for POST authentication.
	 */
	private String signature(int nonce) {
		String message = new String(nonce + this.username + this.apiKey);
		Mac hmac = null;

		try {
			hmac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(this.apiSecret.getBytes("UTF-8"), "HmacSHA256");
			hmac.init(secret_key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return String.format("%X", new BigInteger(1, hmac.doFinal(message.getBytes())));
	}

	/**
	 * Make a POST request to the Cex.io API, with the given data.
	 * 
	 * addr
	 *            HTTP Address to make the request.
	 * param
	 *            Parameters to add to the POST request.
	 * auth
	 *            Authentication required flag.
	 * 
	 * @return Result from POST sent to server.
	 * @throws IOException
	 */
	private String post(String addr, String param, boolean auth) throws IOException {

		String response = "";
		HttpURLConnection connection = null;
		DataOutputStream output = null;
		BufferedReader input = null;
		String charset = "UTF-8";

		try {
			connection = (HttpURLConnection) new URL(addr).openConnection();
			connection.setConnectTimeout(connectTimout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestProperty("User-Agent", "Cex.io Java API");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Charset", charset);

			// Add parameters if included with the call or authorization is required.
			if (param != "" || auth) {
				String content = "";
				connection.setDoOutput(true);
				output = new DataOutputStream(connection.getOutputStream());

				// Add authorization details if required for the API method.
				if (auth) {
					// Generate POST variables and catch errors.
					int nonce = this.nonce.incrementAndGet();
					String tSig = this.signature(nonce);
					String tNon = String.valueOf(nonce);

					content = "key=" + URLEncoder.encode(this.apiKey, charset) + "&signature=" + URLEncoder.encode(tSig, charset)
							+ "&nonce=" + URLEncoder.encode(tNon, charset);
				}

				// Separate parameters and add them to the request URL.
				if (param.contains(",")) {
					String[] temp = param.split(",");

					for (int a = 0; a < temp.length; a += 2) {
						content += "&" + temp[a] + "=" + temp[a + 1] + "&";
					}

					content = content.substring(0, content.length() - 1);
				}
				System.out.println("Body: " + content);
				output.writeBytes(content);
				output.flush();
			}

			String temp = "";
			input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while ((temp = input.readLine()) != null) {
				response += temp;
			}
			
		} finally {
			if (output != null)
				output.close();
			if (input != null)
				input.close();
			if (connection != null)
				connection.disconnect();
		}
		System.out.println("Responce: " + response);

		return response;
	}

	/**
	 * Wrapper for post method; builds the correct URL for the POST request.
	 * 
	 * method
	 *            Method for the POST request.
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * param
	 *            Parameters to add to the POST request.
	 * auth
	 *            Authentication required flag.
	 * 
	 * @return Result from POST sent to server.
	 * @throws IOException
	 */
	private String apiCall(String method, String pair, String param, boolean auth) throws IOException {
		return this.post((apiEnterPoint + method + "/" + pair), param, auth);
	}

	/**
	 * Fetch the ticker data, for the given currency pair.
	 * 
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * 
	 * @return The public ticker data for the given pair.
	 * @throws IOException
	 */
	public String ticker(String pair) throws IOException {
		return this.apiCall("ticker", pair, "", false);
	}

	/**
	 * Fetch the last price for the given currency pairs.
	 *
	 * major
	 *            Cex.io major currency pair.
	 * minor
	 *            Cex.io minor currency pair.
	 *
	 * @return The last trade price for the given currency pairs.
	 * @throws IOException
	 */
	public String lastPrice(String curr, String baseCurr) throws IOException {
		curr = (curr != null) ? curr.toUpperCase() : null;
		baseCurr = (baseCurr != null) ? baseCurr.toUpperCase() : null;

		return this.apiCall("last_price", (curr + "/" + baseCurr), "", false);
	}

	/**
	 * Fetch the price conversion from the Major to Minor currency.
	 *
	 * major
	 *            Cex.io major currency pair.
	 * minor
	 *            Cex.io minor currency pair.
	 *
	 * @return The the value of the minor currency in relation to the major
	 *         currency.
	 * @throws IOException
	 */
	public String convert(String major, String minor, float amount) throws IOException {
		return this.apiCall("convert", (major + "/" + minor), ("amnt," + amount), false);
	}

	/**
	 * Fetch the historical data points for the Major to Minor currency.
	 *
	 * major
	 *            Cex.io major currency pair.
	 * minor
	 *            Cex.io minor currency pair.
	 * hours
	 *            The past-tense number of hours to pull data from.
	 * count
	 *            The maximum number of results desired.
	 *
	 * @return historical data points for the Major to Minor currency.
	 * @throws IOException
	 */
	public String chart(String major, String minor, int hours, int count) throws IOException {
		return this.apiCall("price_stats", (major + "/" + minor), ("lastHours," + hours + ",maxRespArrSize," + count), false);
	}

	/**
	 * Fetch the order book data, for the given currency pair.
	 * 
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * 
	 * @return The public order book data for the given pair.
	 * @throws IOException
	 */
	public String orderBook(String pair) throws IOException {
		return this.apiCall("order_book", pair, "", false);
	}

	/**
	 * Fetch the trade history data, for the given currency pair.
	 * 
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * since
	 *            Unix time stamp to retrieve the data from.
	 * 
	 * @return The public trade history for the given pair (Currently limited to the
	 *         last 1000 trades).
	 * @throws IOException
	 */
	public String tradeHistory(String curr, String baseCurr, int since) throws IOException {
		curr = (curr != null) ? curr.toLowerCase() : null;
		baseCurr = (baseCurr != null) ? baseCurr.toLowerCase() : null;
		return this.apiCall("trade_history", curr + "_" + baseCurr, ("since," + since), false);
	}

	/**
	 * Fetch the account balance data, for the Cex.io API Object.
	 * 
	 * @return The account balance for all currency pairs.
	 * @throws IOException
	 */
	public String balance() throws IOException {
		return this.apiCall("balance", "", "", true);
	}
	
	/**
	 * Fetch the account maker and taker fee, for the Cex.io API.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getMyFee() throws IOException {
		return this.apiCall("get_myfee", "", "", true);
	}

	/**
	 * Fetch the accounts open orders, for the given currency pair.
	 * 
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * 
	 * @return The account open orders for the currency pair.
	 * @throws IOException
	 */
	public String openOrders(String curr, String baseCurr) throws IOException {
		curr = (curr != null) ? curr.toLowerCase() : null;
		baseCurr = (baseCurr != null) ? baseCurr.toLowerCase() : null;
		return this.apiCall("open_orders", curr + "_" + baseCurr, "", true);
	}

	/**
	 * Cancel the account order, with the given ID.
	 * 
	 * id
	 *            The order ID number
	 * 
	 * @return The boolean successfulness of the order cancellation: (True/False).
	 * @throws IOException
	 */
	public String cancelOrder(String id) throws IOException {
		return this.apiCall("cancel_order", "", ("id," + id), true);
	}

	/**
	 * Place an order, via the Cex.io API, for the given currency pair, with the
	 * given amount and price.
	 * 
	 * pair
	 *            Cex.io currency pair for the POST request.
	 * type
	 *            Order type (buy/sell).
	 * amount
	 *            Order amount.
	 * price
	 *            Order price.
	 * 
	 * @return The order information, including: the order id, time, pending,
	 *         amount, type, and price.
	 * @throws IOException
	 */
	public String placeOrder(String curr, String baseCurr, String type, double amount, double price) throws IOException {
		curr = (curr != null) ? curr.toUpperCase() : null;
		baseCurr = (baseCurr != null) ? baseCurr.toUpperCase() : null;
		return this.apiCall("place_order", curr + "/" + baseCurr, ("type," + type + ",amount," + amount + ",price," + price), true);
	}

	/**
	 * Get crypto address of current account.
	 * 
	 * currency
	 * @return
	 * @throws IOException
	 */
	public String getCryptoAddress(String currency) throws IOException {
		currency = (currency != null) ? currency.toUpperCase() : null;
		return this.apiCall("get_address", "", ("currency," + currency.toUpperCase()), true);
	}

	/**
	 * Get the GHash hash rates.
	 *
	 * @return The hash rates for the past day, with various time metrics.
	 * @throws IOException
	 */
	public String hashrate() throws IOException {
		return this.apiCall("ghash.io", "hashrate", "", true);
	}

	/**
	 * Get the GHash hash rate for each worker.
	 *
	 * @return The hash rates for each worker for the past day, with various time
	 *         metrics.
	 * @throws IOException
	 */
	public String workers() throws IOException {
		return this.apiCall("ghash.io", "workers", "", true);
	}
}