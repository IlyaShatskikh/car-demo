добавить сертификат .pem в папку cert
cp /hyperledger_fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem  /car-demo/cert

добавить конфиг в папку config
cp ~/work/hyperledger_fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml  ~/work/car-demo/config