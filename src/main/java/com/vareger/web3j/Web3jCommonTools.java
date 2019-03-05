package com.vareger.web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Web3jCommonTools {

    public static BigInteger getGasPrice(Web3j web3) throws InterruptedException, ExecutionException {
        EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();

        //add gas price on 5 percents
        return ethGasPrice.getGasPrice().multiply(BigInteger.valueOf(2));
    }

    public static BigInteger getGasPrice(Web3j web3, EthNet ethNet) throws Exception {
        BigInteger gasPrice = BigInteger.ZERO;
        if (ethNet != EthNet.BETEX_QUORUM_PROD && ethNet != EthNet.BETEX_POA_PROD) {
            gasPrice = web3.ethGasPrice().send().getGasPrice();
            BigInteger five = new BigInteger("5000000000");
            if (gasPrice.compareTo(five) <= 0)
                gasPrice = five;
        }

        return gasPrice;
    }

    public static BigInteger getGasPriceUnchecked(Web3j web3) {
        try {
            EthGasPrice ethGasPrice = web3.ethGasPrice().send();

            return ethGasPrice.getGasPrice().multiply(BigInteger.valueOf(3));
        } catch (IOException e) {
            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }

    }

    public static BigInteger getGasPriceUnchecked(Web3j web3, EthNet ethNet) {
        try {
            BigInteger gasPrice = BigInteger.ZERO;

            if (ethNet != EthNet.BETEX_QUORUM_PROD && ethNet != EthNet.BETEX_POA_PROD) {
                gasPrice = web3.ethGasPrice().send().getGasPrice();
                if (ethNet == EthNet.ROPSTEN_VAREGER || ethNet == EthNet.ROPSTEN || ethNet == EthNet.ROPSTEN_FULL)
                    gasPrice = gasPrice.multiply(BigInteger.valueOf(3));

                BigInteger ten = new BigInteger("10000000000");
                if (gasPrice.compareTo(ten) <= 0)
                    gasPrice = ten;
            }

            return gasPrice;
        } catch (IOException e) {
            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }

    }


    public static <T extends Contract> BigInteger getGasPrice(T contract) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = Contract.class.getDeclaredField("gasProvider"); //NoSuchFieldException
        field.setAccessible(true);
        ContractGasProvider gasProvider = (ContractGasProvider) field.get(contract);

        return gasProvider.getGasPrice();
    }

    public static <T extends Contract> void setGasPrice(T contract, BigInteger gasPrice) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = Contract.class.getDeclaredField("gasProvider"); //NoSuchFieldException
        field.setAccessible(true);
        ContractGasProvider gasProvider = (ContractGasProvider) field.get(contract);
        BigInteger gasLimit = gasProvider.getGasLimit();
        field.set(contract, new StaticGasProvider(gasPrice, gasLimit));
    }
}

