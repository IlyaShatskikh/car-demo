package rzd.rrd.rails.cardemo.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.stereotype.Service;

import rzd.rrd.rails.cardemo.CarWallet;

@Service
public class ClientService {
    private final CarWallet carWallet;

    public String evaluateTransaction(String name, String... args) throws Exception {
        // Load a file system based wallet for managing identities.
		Wallet wallet = carWallet.getWallet();
		// load a CCP
		Path networkConfigPath = Paths.get("config/connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "admin").networkConfig(networkConfigPath).discovery(true);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("fabcar");

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