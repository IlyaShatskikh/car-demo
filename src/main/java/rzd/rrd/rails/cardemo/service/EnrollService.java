package rzd.rrd.rails.cardemo.service;

import java.io.File;
import java.util.Properties;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rzd.rrd.rails.cardemo.CarWallet;

@Service
public class EnrollService {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	@Value("https://${ca.host}:${ca.port}")
	private String caUrl;
	@Value("${ca.host}")
	private String caHost;

	@Value("${username}")
	private String username;
	@Value("${usersecret}")
	private String userSecret;

	@Value("${pemFile}")
	private String pemFilePath;

	private final CarWallet carWallet;

	public EnrollService(CarWallet carWallet) {
		this.carWallet = carWallet;
	}

//	public void enrollAdmin() throws Exception {
//		// Check to see if we've already enrolled the admin user.
//		Wallet wallet = carWallet.getWallet();
//		final Identity admin = wallet.get("admin");
//		if (admin != null) {
//			System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
//			return;
//		}
//
//		// Create a CA client for interacting with the CA.
//		final Properties props = new Properties();
//		props.put("pemFile", "cert/ca.org1.example.com-cert.pem");
//		props.put("allowAllHostNames", "true");
//		final HFCAClient caClient = HFCAClient.createNewInstance(CA_URL, props);
//		final CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
//		caClient.setCryptoSuite(cryptoSuite);
//
//		// Enroll the admin user, and import the new identity into the wallet.
//		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
//		enrollmentRequestTLS.addHost("localhost");
//		enrollmentRequestTLS.setProfile("tls");
//		final Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
//		final Identity user = Identities.newX509Identity("Org1MSP", enrollment);
//		wallet.put("admin", user);
//		System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
//	}

	public String enrollUser(boolean forced) throws Exception {
		// Check to see if we've already enrolled the user.
		Wallet wallet = carWallet.getWallet();
		if (!forced){
			final Identity user = wallet.get(username);
			if (user != null) {
				return String.format("An identity for \"%s\" already exists in the wallet", username);
			}
		}

		var pemFile = new File(pemFilePath);
		if(!pemFile.exists()){
			return String.format(".pem file is absent (path: %s)", pemFilePath);
		}

		// Create a CA client for interacting with the CA.
		final Properties props = new Properties();
		props.put("pemFile", pemFilePath);
		props.put("allowAllHostNames", "true");
		final HFCAClient caClient = HFCAClient.createNewInstance(caUrl, props);
		final CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Enroll the user, and import the new identity into the wallet.
		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		enrollmentRequestTLS.addHost(caHost);
		enrollmentRequestTLS.setProfile("tls");
		final Enrollment enrollment = caClient.enroll(username, userSecret, enrollmentRequestTLS);
		final Identity user = Identities.newX509Identity("Org1MSP", enrollment);
		wallet.put(username, user);
		return String.format("Successfully enrolled user \"%s\" and imported it into the wallet", username);
	}

//	public void enrollUser() throws Exception {
//		Wallet wallet = carWallet.getWallet();
//		Identity adminIdentity = wallet.get("admin");
//		if(adminIdentity == null){
//			System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
//			return;
//		}
//
//		// Check to see if we've already enrolled the user.
//		final Identity appUser = wallet.get("appUser");
//		if (appUser != null) {
//			System.out.println("An identity for the user \"appUser\" already exists in the wallet");
//			return;
//		}
//
//		// Create a CA client for interacting with the CA.
//		Properties props = new Properties();
//		props.put("pemFile", "cert/ca.org1.example.com-cert.pem");
//		props.put("allowAllHostNames", "true");
//		HFCAClient caClient = HFCAClient.createNewInstance(CA_URL, props);
//		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
//		caClient.setCryptoSuite(cryptoSuite);
//
//		User admin = new User() {
//
//			@Override
//			public String getName() {
//				return "admin";
//			}
//
//			@Override
//			public Set<String> getRoles() {
//				return null;
//			}
//
//			@Override
//			public String getAccount() {
//				return null;
//			}
//
//			@Override
//			public String getAffiliation() {
//				return "org1.department1";
//			}
//
//			@Override
//			public Enrollment getEnrollment() {
//				return new Enrollment() {
//
//					@Override
//					public PrivateKey getKey() {
//						return ((X509Identity) adminIdentity).getPrivateKey();
//					}
//
//					@Override
//					public String getCert() {
//						return ((X509Identity) adminIdentity).getCertificate().toString();
//					}
//				};
//			}
//
//			@Override
//			public String getMspId() {
//				return "Org1MSP";
//			}
//
//		};
//
//		// Register the user, enroll the user, and import the new identity into the wallet.
//		RegistrationRequest registrationRequest = new RegistrationRequest("appUser");
//		registrationRequest.setAffiliation("org1.department1");
//		registrationRequest.setEnrollmentID("appUser");
//		String enrollmentSecret = caClient.register(registrationRequest, admin);
//		Enrollment enrollment = caClient.enroll("appUser", enrollmentSecret);
//		Identity user = Identities.newX509Identity("Org1MSP", enrollment);
//		wallet.put("appUser", user);
//		System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
//	}
}
