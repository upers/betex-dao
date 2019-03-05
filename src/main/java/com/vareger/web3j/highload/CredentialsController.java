package com.vareger.web3j.highload;

import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.Credentials;

/**
 * This class control of credentials loading.<br>
 * You MUST not use credentials which you submit as constructor parameter in other places.
 * @author misha
 *
 */
public class CredentialsController {
	
	private List<CredentialsWrapper> allCredentials;
	
	public CredentialsController(List<Credentials> inCredentials) {
		if (allCredentials == null || allCredentials.isEmpty())
			throw new IllegalStateException("Credentials MUST not be empty!!!");
		
		allCredentials = new ArrayList<>();
		for (Credentials cred : inCredentials) {
			allCredentials.add(new CredentialsWrapper(cred));
		}
	}
	
	/**
	 * This method give you free Credentials or NULL if all busy.
	 * @return
	 */
	public synchronized Credentials getFree() {
		Credentials result = null;
		
		for (CredentialsWrapper credent : allCredentials) {
			if (credent.state == State.FREE) {
				result = credent.credentials;
				credent.state = State.BUSY;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * This method release input credentials.(Set it state to FREE)
	 * inCred
	 */
	public synchronized void release(Credentials inCred) {
		if (inCred == null)
			return;
		
		String inAddress = inCred.getAddress();
		for (CredentialsWrapper credent : allCredentials) {
			String address = credent.credentials.getAddress();
			if (address.equalsIgnoreCase(inAddress)) {
				credent.state = State.FREE;
				break;
			}
		}
	}

	/**
	 * Check is input credentials last in list.
	 * inCred
	 * @return
	 */
	public synchronized boolean isLast(Credentials inCred) {
		if (inCred == null)
			return false;
		String inCredAddress = inCred.getAddress();
		
		CredentialsWrapper lastCredWrap = allCredentials.get(allCredentials.size() - 1);
		String lastCredAddress = lastCredWrap.credentials.getAddress();
		
		if (lastCredAddress.equalsIgnoreCase(inCredAddress))
			return true;
		
		return false;
	}
	
	
	private class CredentialsWrapper {
		
		private Credentials credentials;
		
		private State state;
		
		public CredentialsWrapper(Credentials credentials) {
			this.credentials = credentials;
			this.state = State.FREE;
		}
		
		public CredentialsWrapper(Credentials credentials, State state) {
			this.credentials = credentials;
			this.state = state;
		}
		
	}
	
	private enum State {
		FREE, BUSY;
	}
}
