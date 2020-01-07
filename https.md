####  把pem转为 p12
> openssl pkcs12 -export -out Cert.p12 -in cert.pem -inkey key.pem 
####  p12导出cer证书 
> keytool -export -alias client -keystore client.key.p12 -storetype PKCS12  -rfc -file client.cer
####  将cer导入信任 server.keystore
> keytool -import -file server.cer -keystore server.keystore


```java

package com.example.server;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientforSSL {


    private final static  String keyStoreType="PKCS12";


    private final static  String keyStoreFilePath = "/Users/apple/Desktop/wp/code/home/server/src/main/resources/client.key.p12";
    private final static  String trustStoreFilePath = "/Users/apple/Desktop/wp/code/home/server/src/main/resources/client.jks";

    private final static String password = "anniu_key_2020";

    public static HttpClientConnectionManager CONNECTION_MANAGER = null;

    /**
     * 初始化 connection manager.
     * @param keyStoreFile
     * @param keyStorePass
     * @param trustStoreFile
     * @param trustStorePass
     * @throws Exception
     */
    public void init(String keyStoreFile, String keyStorePass,
                     String trustStoreFile, String trustStorePass) throws Exception {
        System.out.println("init conection pool...");

        InputStream ksis = new FileInputStream(new File(keyStoreFile));
        InputStream tsis = new FileInputStream(new File(trustStoreFile));

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(ksis, keyStorePass.toCharArray());

//        KeyStore ts = KeyStore.getInstance("JKS");
//        ts.load(tsis, trustStorePass.toCharArray());

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(ks, keyStorePass.toCharArray())
                // 如果有 服务器证书
              //  .loadTrustMaterial(ts, new TrustSelfSignedStrategy())
                // 如果没有服务器证书，可以采用自定义 信任机制
                 .loadTrustMaterial(null, new TrustStrategy() {

                 // 信任所有
                 public boolean isTrusted(X509Certificate[] arg0,
                 String arg1) throws CertificateException {
                 return true;
                 }

                 })
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[] { "TLSv1.1" }, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf).build();
        ksis.close();
        tsis.close();
        CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(registry);

    }

    /**
     * do post
     * @param url
     * @param params
     * @throws Exception
     */
    public void post(String url, String params) throws Exception {
        if (CONNECTION_MANAGER == null) {
            return;
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(CONNECTION_MANAGER).build();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(params,
                ContentType.APPLICATION_JSON));
//
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//
//        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();

//        httpPost.setConfig(requestConfig);

        CloseableHttpResponse resp = httpClient.execute(httpPost);
        System.out.println(resp.getStatusLine());
        InputStream respIs = resp.getEntity().getContent();
        String content = convertStreamToString(respIs);
        System.out.println(content);
        EntityUtils.consume(resp.getEntity());
    }


    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 服务地址
        String url = "https://127.0.0.1:8006/ping";
        // 服务参数，这里接口的参数采用 json 格式传递
        String params = "{\"merchantCode\": \"www.demo.com\","
                + "\"sessionId\": \"10000011\"," + "\"userName\": \"jack\","
                + "\"idNumber\": \"432652515\"," + "\"cardNo\": \"561231321\","
                + "\"phoneNo\": \"\"}";
        // 私钥证书
        String keyStoreFile = keyStoreFilePath;
        String keyStorePass = "123456";

        // 配置信任证书库及密码
        String trustStoreFile = trustStoreFilePath;
        String trustStorePass = "123456";

        HttpClientforSSL obj = new HttpClientforSSL();
        try {
            obj.init(keyStoreFile, keyStorePass, trustStoreFile, trustStorePass);
            for (int i = 0; i < 10; i++) {
                obj.post(url, params);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


```
