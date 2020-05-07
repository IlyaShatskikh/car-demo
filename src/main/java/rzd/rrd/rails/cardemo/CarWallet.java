package rzd.rrd.rails.cardemo;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Paths;

@Component
public class CarWallet {
    private final Wallet wallet;

    public CarWallet() throws IOException {
        // Delete directory if exists
        FileSystemUtils.deleteRecursively(Paths.get("wallet"));
        // Create a wallet for managing identities
        this.wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
        // wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));
    }

    public Wallet getWallet() {
        return wallet;
    }

}