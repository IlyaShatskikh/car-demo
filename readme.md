добавить сертификат .pem в папку cert
cp /hyperledger_fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem /car-demo/cert

добавить конфиг в папку config
cp /hyperledger_fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml /car-demo/config


docker build -t fabcar/car-demo .
docker run -p 8080:8080 -t fabcar/car-demo