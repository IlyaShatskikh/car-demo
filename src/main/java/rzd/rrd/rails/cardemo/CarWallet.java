package rzd.rrd.rails.cardemo;

import java.io.IOException;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Repository;

@Component
public class CarWallet {
    private final Wallet wallet;

    public CarWallet() throws IOException {
        // Create a wallet for managing identities
        wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
        // wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));
    }

    public Wallet getWallet() {
        return wallet;
    }

}