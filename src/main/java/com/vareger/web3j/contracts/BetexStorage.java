package com.vareger.web3j.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class BetexStorage extends Contract {
    private static final String BINARY = "0x6080604081905260008054600160a060020a0319163317808255600160a060020a0316917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a360018054600160a060020a0319163317905561149c806100696000396000f3fe6080604052600436106101685763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166218a7fe811461016d5780630a9b406f146101c35780630f2b994014610207578063153f046514610237578063441a0a8a146102615780634c9b44e4146102925780635948f2fd146102b9578063662a0f271461032f578063715018a61461035f57806374d0acf91461037457806384b7a3f6146103c45780638da5cb5b146103f75780638f32d59b1461040c57806394081e21146104215780639681782d1461045a578063a1fe8df914610484578063a39acca0146104b4578063a89ae4ba146104e7578063a8faf002146104fc578063b6f8184d14610532578063cd69b25014610562578063d521e02814610592578063e148919114610602578063e60548c214610617578063ea02ced914610647578063ed8a669a1461067d578063f2fde38b146106e2578063f3c855b114610715575b600080fd5b34801561017957600080fd5b506101c1600480360360c081101561019057600080fd5b5063ffffffff81358116916020810135916040820135916060810135821691608082013581169160a0013516610748565b005b3480156101cf57600080fd5b506101f3600480360360208110156101e657600080fd5b503563ffffffff166108b9565b604080519115158252519081900360200190f35b34801561021357600080fd5b506101c16004803603602081101561022a57600080fd5b503563ffffffff166108d5565b34801561024357600080fd5b506101c16004803603602081101561025a57600080fd5b503561095c565b34801561026d57600080fd5b506102766109da565b60408051600160a060020a039092168252519081900360200190f35b34801561029e57600080fd5b506102a76109e9565b60408051918252519081900360200190f35b3480156102c557600080fd5b506102f1600480360360408110156102dc57600080fd5b5063ffffffff813581169160200135166109ef565b60408051600160a060020a039096168652602086019490945263ffffffff928316858501529015156060850152166080830152519081900360a00190f35b34801561033b57600080fd5b506101c16004803603602081101561035257600080fd5b503563ffffffff16610a91565b34801561036b57600080fd5b506101c1610b37565b6101c1600480360360c081101561038a57600080fd5b50600160a060020a038135169060208101359063ffffffff60408201358116916060810135151591608082013581169160a0013516610b94565b3480156103d057600080fd5b506101f3600480360360208110156103e757600080fd5b5035600160a060020a0316610d23565b34801561040357600080fd5b50610276610d5b565b34801561041857600080fd5b506101f3610d6b565b34801561042d57600080fd5b506101f36004803603604081101561044457600080fd5b50600160a060020a038135169060200135610d7c565b34801561046657600080fd5b506101c16004803603602081101561047d57600080fd5b5035610e81565b34801561049057600080fd5b506101c1600480360360208110156104a757600080fd5b503563ffffffff16610eef565b3480156104c057600080fd5b506101c1600480360360208110156104d757600080fd5b5035600160a060020a0316610f73565b3480156104f357600080fd5b50610276610fa8565b34801561050857600080fd5b506101c16004803603604081101561051f57600080fd5b5063ffffffff8135169060200135610fb7565b34801561053e57600080fd5b506102a76004803603602081101561055557600080fd5b503563ffffffff16611039565b34801561056e57600080fd5b506101c16004803603602081101561058557600080fd5b503563ffffffff16611051565b34801561059e57600080fd5b506105c2600480360360208110156105b557600080fd5b503563ffffffff166110d5565b6040805163ffffffff978816815260208101969096528581019490945291851660608501528416608084015290921660a082015290519081900360c00190f35b34801561060e57600080fd5b50610276611150565b34801561062357600080fd5b506102a76004803603602081101561063a57600080fd5b503563ffffffff1661115f565b34801561065357600080fd5b506101c16004803603604081101561066a57600080fd5b5063ffffffff8135169060200135611171565b34801561068957600080fd5b506101c1600480360360e08110156106a057600080fd5b50600160a060020a038135169060208101359063ffffffff60408201358116916060810135151591608082013581169160a08101359091169060c001356111f4565b3480156106ee57600080fd5b506101c16004803603602081101561070557600080fd5b5035600160a060020a0316611333565b34801561072157600080fd5b506101c16004803603602081101561073857600080fd5b5035600160a060020a031661134f565b600254600160a060020a031633146107ab576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b60c0604051908101604052808763ffffffff1681526020018681526020018581526020018463ffffffff1681526020018363ffffffff1681526020018263ffffffff1660038111156107f957fe5b600381111561080457fe5b905263ffffffff8088166000908152600760209081526040918290208451815490851663ffffffff19918216178255918501516001820155918401516002830155606084015160038084018054608088015187166401000000000267ffffffff000000001994909716941693909317919091169390931780825560a0850151929368ff00000000000000001990911690680100000000000000009084908111156108aa57fe5b02179055505050505050505050565b63ffffffff908116600090815260056020526040902054161590565b600254600160a060020a03163314610938576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff166000818152600560205260409020805463ffffffff19169091179055565b600254600160a060020a031633146109bf576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b6004548111156109ce57600080fd5b60048054919091039055565b600254600160a060020a031681565b60045481565b600080600080600080600660008963ffffffff1663ffffffff1681526020019081526020016000208763ffffffff16815481101515610a2a57fe5b60009182526020909120600391820201805460018201546002830154929450600160a060020a0390911692909160ff1690811115610a6457fe5b60029390930154919a909950919750610100810460ff16965062010000900463ffffffff16945092505050565b600254600160a060020a03163314610af4576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff166000908152600760205260408120805463ffffffff19168155600181018290556002810191909155600301805468ffffffffffffffffff19169055565b610b3f610d6b565b1515610b4a57600080fd5b60008054604051600160a060020a03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a360008054600160a060020a0319169055565b600254600160a060020a03163314610bf7576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b600660008363ffffffff1663ffffffff16815260200190815260200160002060a06040519081016040528088600160a060020a031681526020018781526020018663ffffffff166003811115610c4957fe5b6003811115610c5457fe5b815285151560208083019190915263ffffffff851660409283015283546001808201808755600096875295839020855160039384029091018054600160a060020a031916600160a060020a0390921691909117815592850151838201559284015160028301805493949193909260ff1990911691908490811115610cd457fe5b021790555060608201516002909101805460809093015163ffffffff16620100000265ffffffff0000199215156101000261ff0019909416939093179190911691909117905550505050505050565b6000610d2d610d6b565b1515610d3857600080fd5b5060018054600160a060020a038316600160a060020a0319909116178155919050565b600054600160a060020a03165b90565b600054600160a060020a0316331490565b600254600090600160a060020a0316331480610da25750600354600160a060020a031633145b80610dc55750610db0610d5b565b600160a060020a031633600160a060020a0316145b1515610e41576040805160e560020a62461bcd02815260206004820152602960248201527f73686f756c642062652063616c6c6564206f6e6c79206279207472757374656460448201527f206163636f756e74730000000000000000000000000000000000000000000000606482015290519081900360840190fd5b604051600160a060020a0384169083156108fc029084906000818181858888f19350505050158015610e77573d6000803e3d6000fd5b5060019392505050565b600254600160a060020a03163314610ee4576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b600480549091019055565b600254600160a060020a03163314610f52576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff166000908152600560205260409020805463ffffffff19169055565b610f7b610d6b565b1515610f8657600080fd5b60028054600160a060020a031916600160a060020a0392909216919091179055565b600154600160a060020a031681565b600254600160a060020a0316331461101a576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff909116600090815260086020526040902080549091019055565b63ffffffff1660009081526006602052604090205490565b600254600160a060020a031633146110b4576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff811660009081526006602052604081206110d2916113f4565b50565b63ffffffff81811660009081526007602052604081208054600182015460028301546003808501549596879687968796879687969295918516949093808216926401000000008204909216916801000000000000000090910460ff169081111561113b57fe5b949d939c50919a509850965090945092505050565b600354600160a060020a031681565b60086020526000908152604090205481565b600254600160a060020a031633146111d4576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b63ffffffff90911660009081526008602052604090208054919091039055565b600254600160a060020a03163314611257576040805160e560020a62461bcd0281526020600482015260276024820152600080516020611451833981519152604482015260ca60020a661bdb9d1c9858dd02606482015290519081900360840190fd5b60a06040519081016040528088600160a060020a031681526020018781526020018663ffffffff16600381111561128a57fe5b600381111561129557fe5b815285151560208083019190915263ffffffff808616604093840152861660009081526006909152208054839081106112ca57fe5b906000526020600020906003020160008201518160000160006101000a815481600160a060020a030219169083600160a060020a031602179055506020820151816001015560408201518160020160006101000a81548160ff02191690836003811115610cd457fe5b61133b610d6b565b151561134657600080fd5b6110d281611384565b611357610d6b565b151561136257600080fd5b60038054600160a060020a031916600160a060020a0392909216919091179055565b600160a060020a038116151561139957600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a360008054600160a060020a031916600160a060020a0392909216919091179055565b50805460008255600302906000526020600020908101906110d29190610d6891905b8082111561144c578054600160a060020a03191681556000600182015560028101805465ffffffffffff19169055600301611416565b509056fe73686f756c642062652063616c6c6564206f6e6c792062792062657465782063a165627a7a7230582051fc0f5706e685118f603176a87bbdf7418e9336b1efc6886d047296b066687c0029";

    public static final String FUNC_BETEX = "betex";

    public static final String FUNC_WEIFORGAS = "weiForGas";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_CHANGEORACLEDADDRESS = "changeOracledAddress";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_ORACLEADDRESS = "oracleAddress";

    public static final String FUNC_COMMISSION = "commission";

    public static final String FUNC_COMMISSIONPAYMENTS = "commissionPayments";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_ISNOTFROZEN = "isNotFrozen";

    public static final String FUNC_FREEZEBASKET = "freezeBasket";

    public static final String FUNC_UNFREEZEBASKET = "unfreezeBasket";

    public static final String FUNC_GETBASKET = "getBasket";

    public static final String FUNC_SETBASKET = "setBasket";

    public static final String FUNC_ADDBROKERBALANCE = "addBrokerBalance";

    public static final String FUNC_SUBBROKERBALANCE = "subBrokerBalance";

    public static final String FUNC_ADDWEIFORGASBALANCE = "addWeiForGasBalance";

    public static final String FUNC_SUBWEIFORGASBALANCE = "subWeiForGasBalance";

    public static final String FUNC_DELETEBASKET = "deleteBasket";

    public static final String FUNC_GETBID = "getBid";

    public static final String FUNC_SETBID = "setBid";

    public static final String FUNC_UPDATEBID = "updateBid";

    public static final String FUNC_GETBIDSAMOUNT = "getBidsAmount";

    public static final String FUNC_DELETEBIDSBYKEY = "deleteBidsByKey";

    public static final String FUNC_SETBETEX = "setBetex";

    public static final String FUNC_SETCOMMISSION = "setCommission";

    public static final String FUNC_TRANSFERETHERTO = "transferEtherTo";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1", "0x6ab715efbcbbebdc9f7d3138ec689edb9587af69");
        _addresses.put("3", "0x12c7984398a1c54f2f345638acf02d80b2d9c4ca");
    }

    @Deprecated
    protected BetexStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BetexStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BetexStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BetexStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> betex() {
        final Function function = new Function(FUNC_BETEX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> weiForGas() {
        final Function function = new Function(FUNC_WEIFORGAS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOracledAddress(String _to) {
        final Function function = new Function(
                FUNC_CHANGEORACLEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> oracleAddress() {
        final Function function = new Function(FUNC_ORACLEADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> commission() {
        final Function function = new Function(FUNC_COMMISSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> commissionPayments(BigInteger param0) {
        final Function function = new Function(FUNC_COMMISSIONPAYMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteCall<Boolean> isNotFrozen(BigInteger _key) {
        final Function function = new Function(FUNC_ISNOTFROZEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> freezeBasket(BigInteger _key) {
        final Function function = new Function(
                FUNC_FREEZEBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unfreezeBasket(BigInteger _key) {
        final Function function = new Function(
                FUNC_UNFREEZEBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getBasket(BigInteger _key) {
        final Function function = new Function(FUNC_GETBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}));
        return new RemoteCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setBasket(BigInteger _key, BigInteger _bidCallTotal, BigInteger _bidPutTotal, BigInteger _putAmount, BigInteger _callAmount, BigInteger _bidType) {
        final Function function = new Function(
                FUNC_SETBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_bidCallTotal), 
                new org.web3j.abi.datatypes.generated.Uint256(_bidPutTotal), 
                new org.web3j.abi.datatypes.generated.Uint32(_putAmount), 
                new org.web3j.abi.datatypes.generated.Uint32(_callAmount), 
                new org.web3j.abi.datatypes.generated.Uint32(_bidType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addBrokerBalance(BigInteger brokerId, BigInteger amount) {
        final Function function = new Function(
                FUNC_ADDBROKERBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(brokerId), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> subBrokerBalance(BigInteger brokerId, BigInteger amount) {
        final Function function = new Function(
                FUNC_SUBBROKERBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(brokerId), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addWeiForGasBalance(BigInteger _wei) {
        final Function function = new Function(
                FUNC_ADDWEIFORGASBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_wei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> subWeiForGasBalance(BigInteger _wei) {
        final Function function = new Function(
                FUNC_SUBWEIFORGASBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_wei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deleteBasket(BigInteger _key) {
        final Function function = new Function(
                FUNC_DELETEBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple5<String, BigInteger, BigInteger, Boolean, BigInteger>> getBid(BigInteger _key, BigInteger _index) {
        final Function function = new Function(FUNC_GETBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key), 
                new org.web3j.abi.datatypes.generated.Uint32(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint32>() {}));
        return new RemoteCall<Tuple5<String, BigInteger, BigInteger, Boolean, BigInteger>>(
                new Callable<Tuple5<String, BigInteger, BigInteger, Boolean, BigInteger>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, Boolean, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setBid(String _bidderAddress, BigInteger _value, BigInteger _bidType, Boolean _isRewarded, BigInteger _key, BigInteger _brokerId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SETBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_bidderAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.generated.Uint32(_bidType), 
                new org.web3j.abi.datatypes.Bool(_isRewarded), 
                new org.web3j.abi.datatypes.generated.Uint32(_key), 
                new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> updateBid(String _bidderAddress, BigInteger _value, BigInteger _bidType, Boolean _isRewarded, BigInteger _key, BigInteger _brokerId, BigInteger _bidIndex) {
        final Function function = new Function(
                FUNC_UPDATEBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_bidderAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.generated.Uint32(_bidType), 
                new org.web3j.abi.datatypes.Bool(_isRewarded), 
                new org.web3j.abi.datatypes.generated.Uint32(_key), 
                new org.web3j.abi.datatypes.generated.Uint32(_brokerId), 
                new org.web3j.abi.datatypes.generated.Uint256(_bidIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBidsAmount(BigInteger _key) {
        final Function function = new Function(FUNC_GETBIDSAMOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> deleteBidsByKey(BigInteger _key) {
        final Function function = new Function(
                FUNC_DELETEBIDSBYKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBetex(String _betex) {
        final Function function = new Function(
                FUNC_SETBETEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setCommission(String _commission) {
        final Function function = new Function(
                FUNC_SETCOMMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_commission)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferEtherTo(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERETHERTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static BetexStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetexStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BetexStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetexStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BetexStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BetexStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BetexStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BetexStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BetexStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BetexStorage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BetexStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BetexStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<BetexStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BetexStorage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BetexStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BetexStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
