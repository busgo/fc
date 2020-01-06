####  把pem转为 p12
> openssl pkcs12 -export -out Cert.p12 -in cert.pem -inkey key.pem 
####  p12导出cer证书 
> keytool -export -alias client -keystore client.key.p12 -storetype PKCS12  -rfc -file client.cer
####  将cer导入信任 server.keystore
> keytool -import -v -file client.cer  -keystore server.keystore
