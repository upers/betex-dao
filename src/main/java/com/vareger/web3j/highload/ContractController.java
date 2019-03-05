package com.vareger.web3j.highload;

import com.vareger.web3j.FastRawTransactionManagerLastNonce;
import com.vareger.web3j.Web3jCommonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.tx.Contract;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class control of credentials loading.<br>
 * You MUST not use credentials which you use in Contracts you submit as constructor parameter in other places.
 *
 * @author misha
 */
public class ContractController<T extends Contract> {

    private final static Logger log = LoggerFactory.getLogger(ContractController.class);

    protected List<ContractWrapper> contractsWrapers;

    protected BigInteger maxGasPrice;

    /**
     * This web3j contract wrappers MUST have {@link FastRawTransactionManager}.
     * For correct Contract work don't use this credentials in another place.
     *
     * contracts
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public ContractController(List<T> contracts) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        this(contracts, new BigInteger("100000000000"));
    }
    /**
     * This web3j contract wrappers MUST have {@link FastRawTransactionManager}.
     * For correct Contract work don't use this credentials in another place.
     *
     * contracts
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public ContractController(List<T> contracts, BigInteger maxGasPrice) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        contractsWrapers = new ArrayList<>();
        for (T contract : contracts) {
            Field field = ManagedTransaction.class.getDeclaredField("transactionManager");
            field.setAccessible(true);
            FastRawTransactionManagerLastNonce txManager = (FastRawTransactionManagerLastNonce) field.get(contract);
            log.info("Oracle address: " + txManager.getFromAddress() + " nonce: " + txManager.getCurrentNonce());

            contractsWrapers.add(new ContractWrapper(contract, txManager));
        }
        this.maxGasPrice = maxGasPrice;
    }

    /**
     * This method give you contract with new Transaction Manager which update its nonce before give you.<br>
     * If all tx managers are busy then last will be returned without updating it nonce.
     *
     * @return
     */
    public synchronized T getFree() {
        T result = null;

        for (ContractWrapper currContractWrap : contractsWrapers) {
            if (currContractWrap.state == State.FREE) {
                result = currContractWrap.contract;
                log.info("Tx Manager from: " + currContractWrap.txManager
                        .getFromAddress() + " nonce: " + currContractWrap.txManager.getCurrentNonce());

                if (!isLast(currContractWrap))
                    currContractWrap.state = State.BUSY;

                log.info("Tx Manager from: " + currContractWrap.txManager
                        .getFromAddress() + " nonce: " + currContractWrap.txManager.getCurrentNonce());
                log.info("Get free contract with index: " + contractsWrapers.indexOf(currContractWrap));
                break;
            }
        }

        return result;
    }

    /**
     * This method release input contract.(Set it state to FREE)
     *
     * inContract
     */
    public synchronized void release(T inContract) {
        if (inContract == null)
            return;

        for (ContractWrapper currContrWrap : contractsWrapers) {
            T currContract = currContrWrap.contract;
            if (currContract.equals(inContract)) {
                currContrWrap.state = State.FREE;
                try {
                    Web3jCommonTools.setGasPrice(inContract, currContrWrap.initGasPrice);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                log.info("Release contract with index: " + contractsWrapers.indexOf(currContrWrap));
                break;
            }
        }
    }

    /**
     * This method reset nonce to input contract.
     *
     * inContract
     */
    public synchronized void resetNonce(T inContract) {
        if (inContract == null)
            return;

        for (ContractWrapper currContrWrap : contractsWrapers) {
            T currContract = currContrWrap.contract;
            if (currContract.equals(inContract)) {
                try {
                    currContrWrap.txManager.resetNonce();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("Reset nonce to contract: " + currContrWrap.txManager
                        .getFromAddress() + " nonce: " + currContrWrap.txManager
                        .getCurrentNonce() + " index: " + contractsWrapers.indexOf(currContrWrap));
                break;
            }
        }
    }

    /**
     * Increase gas price on submitted percent.
     *
     * inContract
     * percent
     */
    public synchronized void increaseGasPrice(T inContract, int percent) {
        if (percent < 0)
            throw new IllegalArgumentException("Percent must be more then zero");

        ContractWrapper contractWrapper = getWrapperForContract(inContract);
        BigInteger newGasPrice = contractWrapper.lastGasPrice
                .add(contractWrapper.lastGasPrice.multiply(BigInteger.valueOf(percent))
                        .divide(BigInteger.valueOf(100)));

        try {
            newGasPrice = (maxGasPrice.compareTo(newGasPrice) > 0) ? newGasPrice : maxGasPrice;
            Web3jCommonTools.setGasPrice(inContract, newGasPrice);
            log.debug("Gas price was increasing. Contract: " + inContract
                    .getContractAddress() + " old gas price: " + contractWrapper.lastGasPrice + " new gas price: " + newGasPrice);
            contractWrapper.lastGasPrice = newGasPrice;
        } catch (NoSuchFieldException e) {
            log.error(e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Reset gas price for contract in parameters. Set it to it initialize value.
     * inContract
     */
    public synchronized void resetGasPrice(T inContract) {
        ContractWrapper contractWrapper = getWrapperForContract(inContract);
        try {
            Web3jCommonTools.setGasPrice(inContract, contractWrapper.initGasPrice);
            log.debug("Gas price was reset. Contract: " + inContract
                    .getContractAddress() + " gas price: " + contractWrapper.initGasPrice);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    public synchronized Credentials getContractCredentials(T inContract) {
        return getWrapperForContract(inContract).credentials;
    }

    public synchronized BigInteger getCurrentContractNonce(T inContract) {
        return getWrapperForContract(inContract).txManager.getCurrentNonce();
    }

    protected ContractWrapper getWrapperForContract(T inContract) {
        for (ContractWrapper currContrWrap : contractsWrapers) {
            T currContract = currContrWrap.contract;
            if (currContract.equals(inContract)) {
                return currContrWrap;
            }
        }
        return null;
    }

    protected boolean isLast(ContractWrapper contractWrap) {
        ContractWrapper lastContrWrap = contractsWrapers.get(contractsWrapers.size() - 1);

        return lastContrWrap.equals(contractWrap);
    }

    public int size() {
        return contractsWrapers.size();
    }

    protected enum State {
        FREE, BUSY;
    }

    protected class ContractWrapper {

        T contract;

        FastRawTransactionManagerLastNonce txManager;

        Credentials credentials;

        BigInteger initGasPrice;

        BigInteger lastGasPrice;

        State state;

        public ContractWrapper(T contract, FastRawTransactionManagerLastNonce txManager) throws NoSuchFieldException,
                IllegalAccessException {
            this.contract = contract;
            this.txManager = txManager;
            this.state = State.FREE;
            this.initGasPrice = Web3jCommonTools.getGasPrice(contract);
            this.lastGasPrice = this.initGasPrice;
            this.credentials = txManager.getCredentials();
        }

        @Override
        public int hashCode() {
            return txManager.getFromAddress().toLowerCase().hashCode();
        }

        @Override
        public boolean equals(Object o1) {
            if (o1 == null)
                return false;

            if (o1 instanceof ContractController.ContractWrapper) {
                String o1Address = ((ContractController<?>.ContractWrapper) o1).txManager.getFromAddress();
                String thisAddress = txManager.getFromAddress();

                return thisAddress.equalsIgnoreCase(o1Address);
            }

            return false;
        }
    }

}
