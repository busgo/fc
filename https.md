## https双向认证客户端配置



####  把pem转为 p12
> openssl pkcs12 -export -out Cert.p12 -in cert.pem -inkey key.pem 
####  p12导出cer证书 
> keytool -export -alias client -keystore client.key.p12 -storetype PKCS12  -rfc -file client.cer
####  将cer导入信任 server.keystore
> keytool -import -file server.cer -keystore server.keystore



1.  将客户端用的SSL cer证书给服务端并添加信任。
2.  将客户端SSL .pem和 .key 证书转为p12证书

>   openssl pkcs12 -export -out bar.com.p12 -in bar.com.pem -inkey bar.com.key

会产生 一个名字为bar.com.p12文件(注意一定要保存好密码)


3.访问服务端地址将服务端的cer证书导出
如 server.cer

4.将server.cer 转为keystore

>   keytool -import -file server.cer -keystore pay.foo.com.keystrore


5.将 bar.com.p12 和 pay.foo.com.keystore 导入项目中。


6.加载客户端私钥(bar.com.p12)

7.加载服务端公钥(pay.foo.com.keystore)信任keystore







```java
package com.qy.trade.payment;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author busgo
 * @date 2020-01-05 16:57
 */
@Component
public class PaymentHttpClient implements InitializingBean {


    private final Logger log = LoggerFactory.getLogger(PaymentHttpClient.class);



    private final static String keyStorePass = "123456";
    private final static String trustStorePass = "123456";

    private static PoolingHttpClientConnectionManager connectionManager = null;


    @Autowired
    private ResourceLoader resourceLoader;


    /**
     * 初始化 配置
     *
     * @throws Exception
     */
    private void init() throws Exception {


        System.err.println("init.....");
        Resource resource = resourceLoader.getResource("classpath:bar.com.p12");
        InputStream ksis = resource.getInputStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(ksis, keyStorePass.toCharArray());

         resource = resourceLoader.getResource("classpath:pay.foo.com.keystore");

        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(resource.getInputStream(),trustStorePass.toCharArray());


        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(ks, "123456".toCharArray())
                .loadTrustMaterial(trustStore, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                })
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1","TLSv1.1","TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();


        for (int i = 0; i < 100000; i++) {

            try {


                HttpGet httpget = new HttpGet("https://pay.foo.com:9444/executeTransaction");

                log.info("Executing request :{}",httpget.getRequestLine());

                CloseableHttpResponse response = httpclient.execute(httpget);
                try {
                    HttpEntity entity = response.getEntity();

                   log.info("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            }catch (Exception e){

                e.printStackTrace();
            }

            Thread.sleep(1000);
        }

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}



```
