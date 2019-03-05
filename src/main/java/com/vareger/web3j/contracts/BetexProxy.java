package com.vareger.web3j.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class BetexProxy extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516020806111188339810180604052602081101561003057600080fd5b505160008054600160a060020a0319163317808255604051600160a060020a039190911691907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3336000908152600160208190526040909120805460ff1916909117905560038054600160a060020a0392909216600160a060020a0319928316811790915560028054909216179055611046806100d26000396000f3fe6080604052600436106101275763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166305de6ca4811461012c57806312874688146101635780631dd75ffd1461018d57806337c07f781461025157806339d6d6d2146102815780633bd6cad6146102a85780633fa558f3146102d8578063715018a61461030b57806371eb125e146103205780638da5cb5b146103535780638e71fd8e146103845780638f32d59b14610399578063a39acca0146103ae578063b6f8184d146103e1578063b8343a8014610411578063b938835d14610426578063be5a3e611461045f578063c9340cbf14610474578063d2d4aacd146104a7578063d991ed86146104ef578063f2fde38b1461051f578063fe2c13c414610552575b600080fd5b6101616004803603606081101561014257600080fd5b5063ffffffff813581169160208101358216916040909101351661057c565b005b34801561016f57600080fd5b506101616004803603602081101561018657600080fd5b503561061c565b34801561019957600080fd5b5061023d600480360360208110156101b057600080fd5b8101906020810181356401000000008111156101cb57600080fd5b8201836020820111156101dd57600080fd5b803590602001918460208302840111640100000000831117156101ff57600080fd5b9190808060200260200160405190810160405280939291908181526020018383602002808284376000920191909152509295506106e0945050505050565b604080519115158252519081900360200190f35b34801561025d57600080fd5b506101616004803603602081101561027457600080fd5b503563ffffffff16610757565b34801561028d57600080fd5b50610296610805565b60408051918252519081900360200190f35b3480156102b457600080fd5b50610296600480360360208110156102cb57600080fd5b503563ffffffff16610894565b3480156102e457600080fd5b5061023d600480360360208110156102fb57600080fd5b5035600160a060020a0316610930565b34801561031757600080fd5b50610161610969565b34801561032c57600080fd5b5061023d6004803603602081101561034357600080fd5b5035600160a060020a03166109d3565b34801561035f57600080fd5b506103686109e8565b60408051600160a060020a039092168252519081900360200190f35b34801561039057600080fd5b506103686109f7565b3480156103a557600080fd5b5061023d610a06565b3480156103ba57600080fd5b50610161600480360360208110156103d157600080fd5b5035600160a060020a0316610a17565b3480156103ed57600080fd5b506102966004803603602081101561040457600080fd5b503563ffffffff16610ac6565b34801561041d57600080fd5b50610296610b30565b34801561043257600080fd5b506101616004803603604081101561044957600080fd5b50600160a060020a038135169060200135610b8e565b34801561046b57600080fd5b50610161610c5b565b34801561048057600080fd5b5061023d6004803603602081101561049757600080fd5b5035600160a060020a0316610d24565b3480156104b357600080fd5b50610161600480360360808110156104ca57600080fd5b5063ffffffff8135811691602081013582169160408201358116916060013516610d61565b3480156104fb57600080fd5b506101616004803603602081101561051257600080fd5b503563ffffffff16610e27565b34801561052b57600080fd5b506101616004803603602081101561054257600080fd5b5035600160a060020a0316610ed5565b34801561055e57600080fd5b506101616004803603602081101561057557600080fd5b5035610ef4565b600254604080517fe6aad09300000000000000000000000000000000000000000000000000000000815233600482015263ffffffff80871660248301528086166044830152841660648201529051600160a060020a039092169163e6aad093913491608480830192600092919082900301818588803b1580156105fe57600080fd5b505af1158015610612573d6000803e3d6000fd5b5050505050505050565b3360009081526001602052604090205460ff16151561065f576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517f12874688000000000000000000000000000000000000000000000000000000008152600481018490529051600160a060020a039092169163128746889160248082019260009290919082900301818387803b1580156106c557600080fd5b505af11580156106d9573d6000803e3d6000fd5b5050505050565b60006106ea610a06565b15156106f557600080fd5b60005b825181101561074e576001806000858481518110151561071457fe5b602090810291909101810151600160a060020a03168252810191909152604001600020805460ff19169115159190911790556001016106f8565b50600192915050565b3360009081526001602052604090205460ff16151561079a576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517f37c07f7800000000000000000000000000000000000000000000000000000000815263ffffffff841660048201529051600160a060020a03909216916337c07f789160248082019260009290919082900301818387803b1580156106c557600080fd5b600254604080517f39d6d6d20000000000000000000000000000000000000000000000000000000081529051600092600160a060020a0316916339d6d6d2916004808301926020929190829003018186803b15801561086357600080fd5b505afa158015610877573d6000803e3d6000fd5b505050506040513d602081101561088d57600080fd5b5051905090565b600254604080517f3bd6cad600000000000000000000000000000000000000000000000000000000815263ffffffff841660048201529051600092600160a060020a031691633bd6cad6916024808301926020929190829003018186803b1580156108fe57600080fd5b505afa158015610912573d6000803e3d6000fd5b505050506040513d602081101561092857600080fd5b505192915050565b600061093a610a06565b151561094557600080fd5b600160a060020a039091166000908152600160205260409020805460ff1916905590565b610971610a06565b151561097c57600080fd5b60008054604051600160a060020a03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a36000805473ffffffffffffffffffffffffffffffffffffffff19169055565b60016020526000908152604090205460ff1681565b600054600160a060020a031690565b600354600160a060020a031681565b600054600160a060020a0316331490565b3360009081526001602052604090205460ff161515610a5a576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b60038054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff199182168117909255600280549091168217905560408051918252517ff97ef35f8677741d88994491335f982a520f58251d4b8e79ae38510c23a862ce9181900360200190a150565b600254604080517fb6f8184d00000000000000000000000000000000000000000000000000000000815263ffffffff841660048201529051600092600160a060020a03169163b6f8184d916024808301926020929190829003018186803b1580156108fe57600080fd5b600254604080517fb8343a800000000000000000000000000000000000000000000000000000000081529051600092600160a060020a03169163b8343a80916004808301926020929190829003018186803b15801561086357600080fd5b3360009081526001602052604090205460ff161515610bd1576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517fb938835d000000000000000000000000000000000000000000000000000000008152600160a060020a038581166004830152602482018590529151919092169163b938835d91604480830192600092919082900301818387803b158015610c3f57600080fd5b505af1158015610c53573d6000803e3d6000fd5b505050505050565b3360009081526001602052604090205460ff161515610c9e576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600260009054906101000a9004600160a060020a0316600160a060020a031663be5a3e616040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401600060405180830381600087803b158015610d0a57600080fd5b505af1158015610d1e573d6000803e3d6000fd5b50505050565b6000610d2e610a06565b1515610d3957600080fd5b50600160a060020a03166000908152600160208190526040909120805460ff19168217905590565b3360009081526001602052604090205460ff161515610da4576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517fd2d4aacd00000000000000000000000000000000000000000000000000000000815263ffffffff808816600483015280871660248301528086166044830152841660648201529051600160a060020a039092169163d2d4aacd9160848082019260009290919082900301818387803b1580156105fe57600080fd5b3360009081526001602052604090205460ff161515610e6a576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517fd991ed8600000000000000000000000000000000000000000000000000000000815263ffffffff841660048201529051600160a060020a039092169163d991ed869160248082019260009290919082900301818387803b1580156106c557600080fd5b610edd610a06565b1515610ee857600080fd5b610ef181610f9d565b50565b3360009081526001602052604090205460ff161515610f37576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600254604080517ffe2c13c4000000000000000000000000000000000000000000000000000000008152600481018490529051600160a060020a039092169163fe2c13c49160248082019260009290919082900301818387803b1580156106c557600080fd5b600160a060020a0381161515610fb257600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039290921691909117905556fea165627a7a7230582037113671c8dfcbfc5dad82a8bced0f5cf095ef17caacdd76267a6c009e72541e0029";

    public static final String FUNC_ADDORACLEDADDRESSES = "addOracledAddresses";

    public static final String FUNC_DELETEORACLEDADDRESS = "deleteOracledAddress";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_ORACLEADDRESSES = "oracleAddresses";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_BETEXADDRESS = "betexAddress";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_ADDORACLEDADDRESS = "addOracledAddress";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_SETBETEX = "setBetex";

    public static final String FUNC_BIDUSER = "bidUser";

    public static final String FUNC_CLOSEBASKET = "closeBasket";

    public static final String FUNC_GETBIDSAMOUNT = "getBidsAmount";

    public static final String FUNC_REWARD = "reward";

    public static final String FUNC_PAYOUTCOMMISSION = "payOutCommission";

    public static final String FUNC_TRANSFERCOMMISSIONFORGAS = "transferCommissionForGas";

    public static final String FUNC_SETCOMMISSIONPROCENT = "setCommissionProcent";

    public static final String FUNC_SETMINBID = "setMinBid";

    public static final String FUNC_GETCOMMISSIONDEBT = "getCommissionDebt";

    public static final String FUNC_GETCOMMISSIONFORGAS = "getCommissionForGas";

    public static final Event BETEXCHANGED_EVENT = new Event("BetexChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1", "0x527bff3529539ebd56c96eea8b342e723333af2f");
        _addresses.put("3", "0xd0c1de38090031cb97d2434e78c3468e31648162");
    }

    @Deprecated
    protected BetexProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BetexProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BetexProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BetexProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addOracledAddresses(List<String> _oracled) {
        final Function function = new Function(
                FUNC_ADDORACLEDADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_oracled, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deleteOracledAddress(String _oracled) {
        final Function function = new Function(
                FUNC_DELETEORACLEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_oracled)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> oracleAddresses(String param0) {
        final Function function = new Function(FUNC_ORACLEADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> betexAddress() {
        final Function function = new Function(FUNC_BETEXADDRESS, 
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

    public RemoteCall<TransactionReceipt> addOracledAddress(String _oracled) {
        final Function function = new Function(
                FUNC_ADDORACLEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_oracled)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<BetexChangedEventResponse> getBetexChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BETEXCHANGED_EVENT, transactionReceipt);
        ArrayList<BetexChangedEventResponse> responses = new ArrayList<BetexChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BetexChangedEventResponse typedResponse = new BetexChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.betexAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BetexChangedEventResponse> betexChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BetexChangedEventResponse>() {
            @Override
            public BetexChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BETEXCHANGED_EVENT, log);
                BetexChangedEventResponse typedResponse = new BetexChangedEventResponse();
                typedResponse.log = log;
                typedResponse.betexAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BetexChangedEventResponse> betexChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BETEXCHANGED_EVENT));
        return betexChangedEventFlowable(filter);
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

    public RemoteCall<TransactionReceipt> setBetex(String _betexAddress) {
        final Function function = new Function(
                FUNC_SETBETEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> bidUser(BigInteger _bidType, BigInteger _key, BigInteger _brokerId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BIDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_bidType), 
                new org.web3j.abi.datatypes.generated.Uint32(_key), 
                new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> closeBasket(BigInteger _key) {
        final Function function = new Function(
                FUNC_CLOSEBASKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBidsAmount(BigInteger _key) {
        final Function function = new Function(FUNC_GETBIDSAMOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> reward(BigInteger _from, BigInteger _to, BigInteger _bidType, BigInteger _key) {
        final Function function = new Function(
                FUNC_REWARD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_from), 
                new org.web3j.abi.datatypes.generated.Uint32(_to), 
                new org.web3j.abi.datatypes.generated.Uint32(_bidType), 
                new org.web3j.abi.datatypes.generated.Uint32(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> payOutCommission(BigInteger brokerId) {
        final Function function = new Function(
                FUNC_PAYOUTCOMMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(brokerId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> payOutCommission() {
        final Function function = new Function(
                FUNC_PAYOUTCOMMISSION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferCommissionForGas(String _to, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFERCOMMISSIONFORGAS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setCommissionProcent(BigInteger _percent) {
        final Function function = new Function(
                FUNC_SETCOMMISSIONPROCENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_percent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setMinBid(BigInteger _minBid) {
        final Function function = new Function(
                FUNC_SETMINBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minBid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getCommissionDebt(BigInteger brokerId) {
        final Function function = new Function(FUNC_GETCOMMISSIONDEBT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(brokerId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getCommissionDebt() {
        final Function function = new Function(FUNC_GETCOMMISSIONDEBT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getCommissionForGas() {
        final Function function = new Function(FUNC_GETCOMMISSIONFORGAS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static BetexProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetexProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BetexProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetexProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BetexProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BetexProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BetexProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BetexProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BetexProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _betexAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexAddress)));
        return deployRemoteCall(BetexProxy.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<BetexProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _betexAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexAddress)));
        return deployRemoteCall(BetexProxy.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BetexProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _betexAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexAddress)));
        return deployRemoteCall(BetexProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BetexProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _betexAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexAddress)));
        return deployRemoteCall(BetexProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class BetexChangedEventResponse {
        public Log log;

        public String betexAddress;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
