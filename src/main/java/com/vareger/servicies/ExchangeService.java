package com.vareger.servicies;

import java.util.List;

import com.vareger.models.Exchange;

public interface ExchangeService extends Service<String, Exchange> {
	List<Exchange> findByUserAddress(String userAddress);

	/**
	 * Merge user by transaction hash.<br>
	 * <b> Input exchange will not be changed.<br>
	 * Only {@link Exchange#setId()} or {@link Exchange#setConfirmed(boolean)} field can be changed. </b><br>
	 * {@link Exchange#setId()} will be changed if user is not exist in DB.<br>
	 * {@link Exchange#setConfirmed()} will be changed if existing in DB exchange has confirmed true.
	 * 
	 * exchange
	 * userTxHash
	 * @return {@link Exchange} detached exchange.
	 */
	Exchange mergeByUserTxHash(Exchange exchange, String userTxHash);

	Exchange findByOraclizeTxHash(String oraclizeTxHash);
}
