package com.vareger.web3j;

public enum EthNet {
	MAINNET("http://el.frontnode.vareger.com:9095"),
	MAINNET_INFURA("https://mainnet.infura.io/v3"),
	PROPY_MAIN_NET("http://eth.propy.biz:8545"),
	ROPSTEN("https://rbst1.betex.io"),
	ROPSTEN_FULL("http://parity.betex.io:8545"),
	RINKEBY("https://eth1.betex.io"),
	TESTRPC("http://localhost:8545"),
	BETEX_DEV_NODE("https://testnet1.vareger.com"),
	ROPSTEN_INFURA("https://ropsten.infura.io/v3"),
	BETEX_QUORUM_PROD("https://quorum-nv.betex.io"),
	BETEX_POA_PROD("https://eth-poa.betex.io"),
//	BETEX_POA_PROD("https://neth-poa.betex.io"),
	VAREGER_MAIN_NET("https://geth.vareger.com"),
	ROPSTEN_VAREGER("http://ropsten.frontnode.vareger.com:8545"),
	ROPSTEN_INFURA_WS("wss://ropsten.infura.io/ws/v2"),
	MAINNET_INFURA_WS("wss://mainnet.infura.io/ws/v2");
	
	
	private static final String infuraToken = "92c117e0df0646b2b385df39743acbbc";
	
	private EthNet(String apn) {
		if (apn.indexOf("infura") > -1)
			this.accessPoint = apn + "/" + infuraToken;
		else
			this.accessPoint = apn;
	}

	final public String accessPoint;
	
	public boolean isInfura() {
		return (accessPoint.indexOf("infura") > -1);
	}

	public static EthNet getByURL(String url) {
		EthNet[] array = EthNet.values();
		for (int i = 0; i < array.length; i++) {
			EthNet currNet = array[i];
			if (currNet.accessPoint.equals(url))
				return currNet;
		}

		return null;
	}
}
