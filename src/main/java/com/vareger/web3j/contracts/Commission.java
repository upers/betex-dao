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
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple4;
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
public class Commission extends Contract {
    private static final String BINARY = "0x60806040526009805463ffffffff1916600117905534801561002057600080fd5b5060008054600160a060020a0319163317808255604051600160a060020a039190911691907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a36001805433600160a060020a031991821681179092556002805482168317905560038054909116909117905561161d806100a56000396000f3fe60806040526004361061017f5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630b52b41581146101845780630efec2bc146101c6578063106805c6146101fb5780632203fba714610231578063522f68151461027d578063550d5a4f146102b65780635968327d146102cb5780636171eae214610330578063663ada8c1461035a578063715018a6146103a957806373b9540a146103be578063744d17951461040157806384b7a3f61461041657806386a3cd271461045d578063879bc912146104725780638da5cb5b146104a25780638f32d59b146104b7578063960960ce146104cc57806396326b65146104f657806396fc2be014610526578063a39acca01461053b578063a886e5f91461056e578063a89ae4ba14610583578063bd17f91314610598578063cc03c342146105cb578063dbe55e56146105fe578063eef6671314610613578063f2fde38b1461067c578063f83c3289146106af578063ff001b32146106c4575b600080fd5b34801561019057600080fd5b506101b4600480360360208110156101a757600080fd5b503563ffffffff166106ee565b60408051918252519081900360200190f35b3480156101d257600080fd5b506101f9600480360360208110156101e957600080fd5b5035600160a060020a0316610709565b005b34801561020757600080fd5b506101f96004803603604081101561021e57600080fd5b508035906020013563ffffffff1661073e565b34801561023d57600080fd5b506102616004803603602081101561025457600080fd5b503563ffffffff16610d82565b60408051600160a060020a039092168252519081900360200190f35b34801561028957600080fd5b506101f9600480360360408110156102a057600080fd5b50600160a060020a038135169060200135610dab565b3480156102c257600080fd5b506101b4610e47565b3480156102d757600080fd5b506102e0610e4d565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561031c578181015183820152602001610304565b505050509050019250505060405180910390f35b34801561033c57600080fd5b506101f96004803603602081101561035357600080fd5b5035610ed1565b34801561036657600080fd5b506101f96004803603608081101561037d57600080fd5b5063ffffffff81351690600160a060020a03602082013581169160408101359091169060600135610f2b565b3480156103b557600080fd5b506101f961110a565b3480156103ca57600080fd5b506103e8600480360360208110156103e157600080fd5b5035611167565b6040805163ffffffff9092168252519081900360200190f35b34801561040d57600080fd5b506101b461119f565b34801561042257600080fd5b506104496004803603602081101561043957600080fd5b5035600160a060020a03166111a5565b604080519115158252519081900360200190f35b34801561046957600080fd5b506103e86111dd565b34801561047e57600080fd5b506101f96004803603602081101561049557600080fd5b503563ffffffff166111e9565b3480156104ae57600080fd5b506102616112c3565b3480156104c357600080fd5b506104496112d2565b3480156104d857600080fd5b506101f9600480360360208110156104ef57600080fd5b50356112e3565b34801561050257600080fd5b506102616004803603602081101561051957600080fd5b503563ffffffff1661133d565b34801561053257600080fd5b50610261611361565b34801561054757600080fd5b506101f96004803603602081101561055e57600080fd5b5035600160a060020a0316611370565b34801561057a57600080fd5b506101b46113a5565b34801561058f57600080fd5b506102616113ab565b3480156105a457600080fd5b506101f9600480360360208110156105bb57600080fd5b5035600160a060020a03166113ba565b3480156105d757600080fd5b506101f9600480360360208110156105ee57600080fd5b5035600160a060020a03166113ef565b34801561060a57600080fd5b50610261611424565b34801561061f57600080fd5b506106436004803603602081101561063657600080fd5b503563ffffffff16611433565b6040805163ffffffff9095168552600160a060020a03938416602086015291909216838201526060830191909152519081900360800190f35b34801561068857600080fd5b506101f96004803603602081101561069f57600080fd5b5035600160a060020a031661146f565b3480156106bb57600080fd5b506101b461148e565b3480156106d057600080fd5b506101f9600480360360208110156106e757600080fd5b5035611493565b63ffffffff166000908152600b602052604090206002015490565b6107116112d2565b151561071c57600080fd5b60058054600160a060020a031916600160a060020a0392909216919091179055565b600454600160a060020a0316331461077a576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b81151561078657610d7e565b61078e6115ca565b5063ffffffff8181166000908152600b6020908152604091829020825160808101845281549485168152640100000000909404600160a060020a039081169285018390526001820154169284019290925260029091015460608301521515610851575060095463ffffffff9081166000908152600b6020908152604091829020825160808101845281549485168152640100000000909404600160a060020a03908116928501929092526001810154909116918301919091526002015460608201525b60065460009061087b906103e89061086f908763ffffffff6114ed16565b9063ffffffff61152116565b9050600061089c6003600a0a61086f876008546114ed90919063ffffffff16565b905060006108bd6003600a0a61086f886007546114ed90919063ffffffff16565b606085015190915015610a605760608401516000906108ea906103e89061086f908563ffffffff6114ed16565b90506108fc828263ffffffff61154516565b600554604080880151815160e060020a6394081e21028152600160a060020a039182166004820152602481018690529151939550909116916394081e21916044808201926020929091908290030181600087803b15801561095c57600080fd5b505af1158015610970573d6000803e3d6000fd5b505050506040513d602081101561098657600080fd5b50511515610a04576040805160e560020a62461bcd02815260206004820152602e60248201527f7472616e7366657220636f6d6d697373696f6e20746f2062726f6b657220726560448201527f736572766520776173206661696c000000000000000000000000000000000000606482015290519081900360840190fd5b60408086015186518251600160a060020a0390921682526020820184905263ffffffff168183015242606082015290517fb704f35d342ff8338b702614bd337bc70e4170961c0ea902501627e3748339f49181900360800190a1505b6005546020808601516040805160e060020a6394081e21028152600160a060020a03928316600482015260248101869052905191909316926394081e219260448083019391928290030181600087803b158015610abc57600080fd5b505af1158015610ad0573d6000803e3d6000fd5b505050506040513d6020811015610ae657600080fd5b50511515610b18576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b602080850151855160408051600160a060020a03909316835292820184905263ffffffff168183015242606082015290517fa91c41ad778866caf16369f2942d0719025bac662b4ff26f36f23ff4cfb99da39181900360800190a16005546002546040805160e060020a6394081e21028152600160a060020a03928316600482015260248101879052905191909216916394081e219160448083019260209291908290030181600087803b158015610bcf57600080fd5b505af1158015610be3573d6000803e3d6000fd5b505050506040513d6020811015610bf957600080fd5b50511515610c2b576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b60025460408051600160a060020a039092168252602082018590524282820152517f7fff61597f2fc6ccef187cf3c7511756132a5dab1737b9b61fb3eb682e86e6499181900360600190a16005546003546040805160e060020a6394081e21028152600160a060020a03928316600482015260248101869052905191909216916394081e219160448083019260209291908290030181600087803b158015610cd257600080fd5b505af1158015610ce6573d6000803e3d6000fd5b505050506040513d6020811015610cfc57600080fd5b50511515610d2e576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b60035460408051600160a060020a039092168252602082018490524282820152517f9eff218a5515cdc2358811e9f623b56cd2c340daa8d28ca648af2f8eccfe65679181900360600190a1505050505b5050565b63ffffffff166000908152600b60205260409020546401000000009004600160a060020a031690565b610db36112d2565b1515610dbe57600080fd5b6005546040805160e060020a6394081e21028152600160a060020a03858116600483015260248201859052915191909216916394081e219160448083019260209291908290030181600087803b158015610e1757600080fd5b505af1158015610e2b573d6000803e3d6000fd5b505050506040513d6020811015610e4157600080fd5b50505050565b60075481565b6060600a805480602002602001604051908101604052809291908181526020018280548015610ec757602002820191906000526020600020906000905b82829054906101000a900463ffffffff1663ffffffff1681526020019060040190602082600301049283019260010382029150808411610e8a5790505b5050505050905090565b610ed96112d2565b1515610ee457600080fd5b600081118015610ef657506103e88111155b1515610f26576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600855565b600154600160a060020a03163314610f4257600080fd5b60008110158015610f5557506103e88111155b1515610f85576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b63ffffffff84166000908152600b60205260409020546401000000009004600160a060020a0316151561100d57600a80546001810182556000919091527fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a86008820401805460079092166004026101000a63ffffffff81810219909316928716029190911790555b604080516080808201835263ffffffff878116808452600160a060020a0388811660208087018281528a8416888a018181526060808b018d81526000898152600b87528d90209b518c54955163ffffffff199096169a169990991777ffffffffffffffffffffffffffffffffffffffff00000000191664010000000094881694909402939093178a555160018a018054600160a060020a03191691909616179094559451600290970196909655865192835294820194909452808501939093528201849052429082015290517fa6401b8c1a812fa68db2c9152d6b036ab34068e2798b9faa9cc1ec6fcea544aa9160a0908290030190a150505050565b6111126112d2565b151561111d57600080fd5b60008054604051600160a060020a03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a360008054600160a060020a0319169055565b600a80548290811061117557fe5b9060005260206000209060089182820401919006600402915054906101000a900463ffffffff1681565b60065481565b60006111af6112d2565b15156111ba57600080fd5b5060018054600160a060020a038316600160a060020a0319909116178155919050565b60095463ffffffff1681565b600154600160a060020a0316331461120057600080fd5b63ffffffff81166000818152600b60209081526040808320805460018201805460028401805477ffffffffffffffffffffffffffffffffffffffffffffffff1985168655600160a060020a03198316909355969096558351968752600160a060020a03640100000000909204821694870185905294168583018190526060860185905242608087015291519094929391927fea3bb2bf6f54e20a2dcc91a0cb5bbdf9fdcc12c43699dab1a9b93f7cc42703aa919081900360a00190a15050505050565b600054600160a060020a031690565b600054600160a060020a0316331490565b6112eb6112d2565b15156112f657600080fd5b60008111801561130857506103e88111155b1515611338576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600755565b63ffffffff166000908152600b6020526040902060010154600160a060020a031690565b600354600160a060020a031681565b6113786112d2565b151561138357600080fd5b60048054600160a060020a031916600160a060020a0392909216919091179055565b60085481565b600154600160a060020a031681565b6113c26112d2565b15156113cd57600080fd5b60038054600160a060020a031916600160a060020a0392909216919091179055565b6113f76112d2565b151561140257600080fd5b60028054600160a060020a031916600160a060020a0392909216919091179055565b600254600160a060020a031681565b600b6020526000908152604090208054600182015460029092015463ffffffff821692600160a060020a03640100000000909304831692169084565b6114776112d2565b151561148257600080fd5b61148b8161155a565b50565b600381565b61149b6112d2565b15156114a657600080fd5b6000811180156114b857506103e88111155b15156114e8576040805160e560020a62461bcd028152602060048201526000602482015290519081900360640190fd5b600655565b60008215156114fe5750600061151b565b82820282848281151561150d57fe5b041461151857600080fd5b90505b92915050565b600080821161152f57600080fd5b6000828481151561153c57fe5b04949350505050565b60008282111561155457600080fd5b50900390565b600160a060020a038116151561156f57600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a360008054600160a060020a031916600160a060020a0392909216919091179055565b6040805160808101825260008082526020820181905291810182905260608101919091529056fea165627a7a723058200277c9f7888a31a4e5c407993190fe166b055a90b07adf48831c4582e15fc7cd0029";

    public static final String FUNC_BROKERPERCENT = "brokerPercent";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_BROKERKEYS = "brokerKeys";

    public static final String FUNC_PLATFORMPERCENT = "platformPercent";

    public static final String FUNC_CHANGEORACLEDADDRESS = "changeOracledAddress";

    public static final String FUNC_DEFAULTKEY = "defaultKey";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_HOLDERADDRESS = "holderAddress";

    public static final String FUNC_HOLDERPERCENT = "holderPercent";

    public static final String FUNC_ORACLEADDRESS = "oracleAddress";

    public static final String FUNC_PLATFORMADDRESS = "platformAddress";

    public static final String FUNC_BROKERS = "brokers";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_PERCENTAGE_EXPONENT = "PERCENTAGE_EXPONENT";

    public static final String FUNC_RECEIVED = "received";

    public static final String FUNC_ADDBROKERINFO = "addBrokerInfo";

    public static final String FUNC_GETBROKERKEYS = "getBrokerKeys";

    public static final String FUNC_DELETEBROKER = "deleteBroker";

    public static final String FUNC_GETBROKERADDRESS = "getBrokerAddress";

    public static final String FUNC_GETBROKERRESERVEPERCENT = "getBrokerReservePercent";

    public static final String FUNC_GETRESERVEADDRESS = "getReserveAddress";

    public static final String FUNC_SETHOLDERADDRESS = "setHolderAddress";

    public static final String FUNC_SETPLATFORMADDRESS = "setPlatformAddress";

    public static final String FUNC_SETPLATFORMPERCENT = "setPlatformPercent";

    public static final String FUNC_SETBROKERPERCENT = "setBrokerPercent";

    public static final String FUNC_SETHOLDERPERCENT = "setHolderPercent";

    public static final String FUNC_SETBETEX = "setBetex";

    public static final String FUNC_SETBETEXSTORAGE = "setBetexStorage";

    public static final String FUNC_WITHDRAWETHER = "withdrawEther";

    public static final Event USERHOLDERPAYMENT_EVENT = new Event("UserHolderPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERBROKERPAYMENT_EVENT = new Event("UserBrokerPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERBROKERRESERVEPAYMENT_EVENT = new Event("UserBrokerReservePayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERPLATFORMPAYMENT_EVENT = new Event("UserPlatformPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event HOLDERPAYMENT_EVENT = new Event("HolderPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BROKERPAYMENT_EVENT = new Event("BrokerPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BROKERRESERVEPAYMENT_EVENT = new Event("BrokerReservePayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PLATFORMPAYMENT_EVENT = new Event("PlatformPayment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BROKERADDED_EVENT = new Event("BrokerAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BROKERDELETED_EVENT = new Event("BrokerDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1", "0xf87be8bdc211ba3e268f441cab93e6eae25cb708");
        _addresses.put("3", "0x62c46b516da19280264212098e76d90dc62cdfd6");
    }

    @Deprecated
    protected Commission(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Commission(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Commission(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Commission(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> brokerPercent() {
        final Function function = new Function(FUNC_BROKERPERCENT, 
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

    public RemoteCall<BigInteger> brokerKeys(BigInteger param0) {
        final Function function = new Function(FUNC_BROKERKEYS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> platformPercent() {
        final Function function = new Function(FUNC_PLATFORMPERCENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeOracledAddress(String _to) {
        final Function function = new Function(
                FUNC_CHANGEORACLEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> defaultKey() {
        final Function function = new Function(FUNC_DEFAULTKEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<String> holderAddress() {
        final Function function = new Function(FUNC_HOLDERADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> holderPercent() {
        final Function function = new Function(FUNC_HOLDERPERCENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> oracleAddress() {
        final Function function = new Function(FUNC_ORACLEADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> platformAddress() {
        final Function function = new Function(FUNC_PLATFORMADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<BigInteger, String, String, BigInteger>> brokers(BigInteger param0) {
        final Function function = new Function(FUNC_BROKERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<BigInteger, String, String, BigInteger>>(
                new Callable<Tuple4<BigInteger, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> PERCENTAGE_EXPONENT() {
        final Function function = new Function(FUNC_PERCENTAGE_EXPONENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<UserHolderPaymentEventResponse> getUserHolderPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERHOLDERPAYMENT_EVENT, transactionReceipt);
        ArrayList<UserHolderPaymentEventResponse> responses = new ArrayList<UserHolderPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserHolderPaymentEventResponse typedResponse = new UserHolderPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.holderAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserHolderPaymentEventResponse> userHolderPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UserHolderPaymentEventResponse>() {
            @Override
            public UserHolderPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERHOLDERPAYMENT_EVENT, log);
                UserHolderPaymentEventResponse typedResponse = new UserHolderPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.holderAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserHolderPaymentEventResponse> userHolderPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERHOLDERPAYMENT_EVENT));
        return userHolderPaymentEventFlowable(filter);
    }

    public List<UserBrokerPaymentEventResponse> getUserBrokerPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERBROKERPAYMENT_EVENT, transactionReceipt);
        ArrayList<UserBrokerPaymentEventResponse> responses = new ArrayList<UserBrokerPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserBrokerPaymentEventResponse typedResponse = new UserBrokerPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.brokerAddrr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserBrokerPaymentEventResponse> userBrokerPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UserBrokerPaymentEventResponse>() {
            @Override
            public UserBrokerPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERBROKERPAYMENT_EVENT, log);
                UserBrokerPaymentEventResponse typedResponse = new UserBrokerPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.brokerAddrr = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserBrokerPaymentEventResponse> userBrokerPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERBROKERPAYMENT_EVENT));
        return userBrokerPaymentEventFlowable(filter);
    }

    public List<UserBrokerReservePaymentEventResponse> getUserBrokerReservePaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERBROKERRESERVEPAYMENT_EVENT, transactionReceipt);
        ArrayList<UserBrokerReservePaymentEventResponse> responses = new ArrayList<UserBrokerReservePaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserBrokerReservePaymentEventResponse typedResponse = new UserBrokerReservePaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reservAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserBrokerReservePaymentEventResponse> userBrokerReservePaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UserBrokerReservePaymentEventResponse>() {
            @Override
            public UserBrokerReservePaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERBROKERRESERVEPAYMENT_EVENT, log);
                UserBrokerReservePaymentEventResponse typedResponse = new UserBrokerReservePaymentEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reservAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserBrokerReservePaymentEventResponse> userBrokerReservePaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERBROKERRESERVEPAYMENT_EVENT));
        return userBrokerReservePaymentEventFlowable(filter);
    }

    public List<UserPlatformPaymentEventResponse> getUserPlatformPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERPLATFORMPAYMENT_EVENT, transactionReceipt);
        ArrayList<UserPlatformPaymentEventResponse> responses = new ArrayList<UserPlatformPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserPlatformPaymentEventResponse typedResponse = new UserPlatformPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.devAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserPlatformPaymentEventResponse> userPlatformPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UserPlatformPaymentEventResponse>() {
            @Override
            public UserPlatformPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERPLATFORMPAYMENT_EVENT, log);
                UserPlatformPaymentEventResponse typedResponse = new UserPlatformPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.devAddr = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserPlatformPaymentEventResponse> userPlatformPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERPLATFORMPAYMENT_EVENT));
        return userPlatformPaymentEventFlowable(filter);
    }

    public List<HolderPaymentEventResponse> getHolderPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HOLDERPAYMENT_EVENT, transactionReceipt);
        ArrayList<HolderPaymentEventResponse> responses = new ArrayList<HolderPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HolderPaymentEventResponse typedResponse = new HolderPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.holderAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HolderPaymentEventResponse> holderPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, HolderPaymentEventResponse>() {
            @Override
            public HolderPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HOLDERPAYMENT_EVENT, log);
                HolderPaymentEventResponse typedResponse = new HolderPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.holderAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<HolderPaymentEventResponse> holderPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HOLDERPAYMENT_EVENT));
        return holderPaymentEventFlowable(filter);
    }

    public List<BrokerPaymentEventResponse> getBrokerPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROKERPAYMENT_EVENT, transactionReceipt);
        ArrayList<BrokerPaymentEventResponse> responses = new ArrayList<BrokerPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BrokerPaymentEventResponse typedResponse = new BrokerPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.brokerAddrr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BrokerPaymentEventResponse> brokerPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BrokerPaymentEventResponse>() {
            @Override
            public BrokerPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROKERPAYMENT_EVENT, log);
                BrokerPaymentEventResponse typedResponse = new BrokerPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.brokerAddrr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BrokerPaymentEventResponse> brokerPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROKERPAYMENT_EVENT));
        return brokerPaymentEventFlowable(filter);
    }

    public List<BrokerReservePaymentEventResponse> getBrokerReservePaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROKERRESERVEPAYMENT_EVENT, transactionReceipt);
        ArrayList<BrokerReservePaymentEventResponse> responses = new ArrayList<BrokerReservePaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BrokerReservePaymentEventResponse typedResponse = new BrokerReservePaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reservAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BrokerReservePaymentEventResponse> brokerReservePaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BrokerReservePaymentEventResponse>() {
            @Override
            public BrokerReservePaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROKERRESERVEPAYMENT_EVENT, log);
                BrokerReservePaymentEventResponse typedResponse = new BrokerReservePaymentEventResponse();
                typedResponse.log = log;
                typedResponse.reservAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.brokerId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BrokerReservePaymentEventResponse> brokerReservePaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROKERRESERVEPAYMENT_EVENT));
        return brokerReservePaymentEventFlowable(filter);
    }

    public List<PlatformPaymentEventResponse> getPlatformPaymentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PLATFORMPAYMENT_EVENT, transactionReceipt);
        ArrayList<PlatformPaymentEventResponse> responses = new ArrayList<PlatformPaymentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PlatformPaymentEventResponse typedResponse = new PlatformPaymentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.devAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PlatformPaymentEventResponse> platformPaymentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PlatformPaymentEventResponse>() {
            @Override
            public PlatformPaymentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PLATFORMPAYMENT_EVENT, log);
                PlatformPaymentEventResponse typedResponse = new PlatformPaymentEventResponse();
                typedResponse.log = log;
                typedResponse.devAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PlatformPaymentEventResponse> platformPaymentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PLATFORMPAYMENT_EVENT));
        return platformPaymentEventFlowable(filter);
    }

    public List<BrokerAddedEventResponse> getBrokerAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROKERADDED_EVENT, transactionReceipt);
        ArrayList<BrokerAddedEventResponse> responses = new ArrayList<BrokerAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BrokerAddedEventResponse typedResponse = new BrokerAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.brokerAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.reserveAddress = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.reservPercent = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BrokerAddedEventResponse> brokerAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BrokerAddedEventResponse>() {
            @Override
            public BrokerAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROKERADDED_EVENT, log);
                BrokerAddedEventResponse typedResponse = new BrokerAddedEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.brokerAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.reserveAddress = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.reservPercent = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BrokerAddedEventResponse> brokerAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROKERADDED_EVENT));
        return brokerAddedEventFlowable(filter);
    }

    public List<BrokerDeletedEventResponse> getBrokerDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BROKERDELETED_EVENT, transactionReceipt);
        ArrayList<BrokerDeletedEventResponse> responses = new ArrayList<BrokerDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BrokerDeletedEventResponse typedResponse = new BrokerDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.brokerAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.reserveAddress = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.reservPercent = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BrokerDeletedEventResponse> brokerDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BrokerDeletedEventResponse>() {
            @Override
            public BrokerDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BROKERDELETED_EVENT, log);
                BrokerDeletedEventResponse typedResponse = new BrokerDeletedEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.brokerAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.reserveAddress = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.reservPercent = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BrokerDeletedEventResponse> brokerDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BROKERDELETED_EVENT));
        return brokerDeletedEventFlowable(filter);
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

    public RemoteCall<TransactionReceipt> received(BigInteger _amount, BigInteger _brokerId) {
        final Function function = new Function(
                FUNC_RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addBrokerInfo(BigInteger _brokerId, String _brokerAddr, String _reserveAddrr, BigInteger _referralPercent) {
        final Function function = new Function(
                FUNC_ADDBROKERINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_brokerId), 
                new org.web3j.abi.datatypes.Address(_brokerAddr), 
                new org.web3j.abi.datatypes.Address(_reserveAddrr), 
                new org.web3j.abi.datatypes.generated.Uint256(_referralPercent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getBrokerKeys() {
        final Function function = new Function(FUNC_GETBROKERKEYS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint32>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> deleteBroker(BigInteger _brokerId) {
        final Function function = new Function(
                FUNC_DELETEBROKER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getBrokerAddress(BigInteger _brokerId) {
        final Function function = new Function(FUNC_GETBROKERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getBrokerReservePercent(BigInteger _brokerId) {
        final Function function = new Function(FUNC_GETBROKERRESERVEPERCENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getReserveAddress(BigInteger _brokerId) {
        final Function function = new Function(FUNC_GETRESERVEADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_brokerId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setHolderAddress(String _address) {
        final Function function = new Function(
                FUNC_SETHOLDERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setPlatformAddress(String _address) {
        final Function function = new Function(
                FUNC_SETPLATFORMADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setPlatformPercent(BigInteger _platformPercent) {
        final Function function = new Function(
                FUNC_SETPLATFORMPERCENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_platformPercent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBrokerPercent(BigInteger _brokerPercent) {
        final Function function = new Function(
                FUNC_SETBROKERPERCENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_brokerPercent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setHolderPercent(BigInteger _holderPercent) {
        final Function function = new Function(
                FUNC_SETHOLDERPERCENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_holderPercent)), 
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

    public RemoteCall<TransactionReceipt> setBetexStorage(String _betexStorage) {
        final Function function = new Function(
                FUNC_SETBETEXSTORAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_betexStorage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawEther(String _destination, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAWETHER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_destination), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Commission load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Commission(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Commission load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Commission(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Commission load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Commission(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Commission load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Commission(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Commission> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Commission.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Commission> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Commission.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Commission> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Commission.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Commission> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Commission.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class UserHolderPaymentEventResponse {
        public Log log;

        public String user;

        public String holderAddr;

        public BigInteger amount;

        public BigInteger _timestamp;
    }

    public static class UserBrokerPaymentEventResponse {
        public Log log;

        public String user;

        public String brokerAddrr;

        public BigInteger amount;

        public BigInteger brokerId;

        public BigInteger _timestamp;
    }

    public static class UserBrokerReservePaymentEventResponse {
        public Log log;

        public String user;

        public String reservAddr;

        public BigInteger amount;

        public BigInteger brokerId;

        public BigInteger _timestamp;
    }

    public static class UserPlatformPaymentEventResponse {
        public Log log;

        public String user;

        public String devAddr;

        public BigInteger amount;

        public BigInteger _timestamp;
    }

    public static class HolderPaymentEventResponse {
        public Log log;

        public String holderAddr;

        public BigInteger amount;

        public BigInteger _timestamp;
    }

    public static class BrokerPaymentEventResponse {
        public Log log;

        public String brokerAddrr;

        public BigInteger amount;

        public BigInteger brokerId;

        public BigInteger _timestamp;
    }

    public static class BrokerReservePaymentEventResponse {
        public Log log;

        public String reservAddr;

        public BigInteger amount;

        public BigInteger brokerId;

        public BigInteger _timestamp;
    }

    public static class PlatformPaymentEventResponse {
        public Log log;

        public String devAddr;

        public BigInteger amount;

        public BigInteger _timestamp;
    }

    public static class BrokerAddedEventResponse {
        public Log log;

        public BigInteger id;

        public String brokerAddress;

        public String reserveAddress;

        public BigInteger reservPercent;

        public BigInteger _timestamp;
    }

    public static class BrokerDeletedEventResponse {
        public Log log;

        public BigInteger id;

        public String brokerAddress;

        public String reserveAddress;

        public BigInteger reservPercent;

        public BigInteger _timestamp;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
