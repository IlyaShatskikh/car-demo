package rzd.rrd.rails.cardemo.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rzd.rrd.rails.cardemo.CarWallet;

@Service
public class ClientService {
    private final CarWallet carWallet;

    @Value("${username}")
    private String username;

    @Value("${network}")
    private String networkName;

    @Value("${contract}")
    private String contractName;

    @Value("${config}")
    private String configPath;

    public String evaluateTransaction(String name, String... args) throws Exception {
        // Load a file system based wallet for managing identities.
		Wallet wallet = carWallet.getWallet();
		if (wallet.get(username) == null){
		    return "enroll user before making the query";
        }
		// load a CCP
        File config = new File(configPath);
        if(!config.exists()){
            return String.format("network config file is absent(path: %s)", configPath);
        }

        Path networkConfigPath = config.toPath();

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, username).networkConfig(networkConfigPath).discovery(true);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork(networkName);
			Contract contract = network.getContract(contractName);

            byte[] result = contract.evaluateTransaction(name, args);

            return new String(result);

			// result = contract.evaluateTransaction("queryAllCars");
			// System.out.println(new String(result));

			// contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");

			// result = contract.evaluateTransaction("queryCar", "CAR10");
			// System.out.println(new String(result));

			// contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

			// result = contract.evaluateTransaction("queryCar", "CAR10");
            // System.out.println(new String(result));
        }
    }

    public ClientService(CarWallet carWallet) {
        this.carWallet = carWallet;
    }

}