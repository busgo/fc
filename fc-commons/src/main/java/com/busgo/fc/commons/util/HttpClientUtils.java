package com.busgo.fc.commons.util;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @author busgo
 * @date 2019-11-16 18:26
 */
public class HttpClientUtils {


    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static String doGet(String url, Map<String, String> param) {

        return doGet(url, param, null);
    }


    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();


        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                Iterator var6 = param.keySet().iterator();

                while (var6.hasNext()) {
                    String key = (String) var6.next();
                    builder.addParameter(key, (String) param.get(key));
                }
            }

            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);

            if (headers != null && headers.size() > 0) {

                Set<Map.Entry<String, String>> entries = headers.entrySet();


                for (Map.Entry<String, String> item : entries) {

                    String key = item.getKey();
                    String value = item.getValue();
                    httpGet.addHeader(key.trim(), value.trim());

                }

            }

            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                httpclient.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return resultString;
    }


    public static String getWAWJCookie(String url, Map<String, String> param, Map<String, String> headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();


        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                Iterator var6 = param.keySet().iterator();

                while (var6.hasNext()) {
                    String key = (String) var6.next();
                    builder.addParameter(key, (String) param.get(key));
                }
            }

            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);

            if (headers != null && headers.size() > 0) {

                Set<Map.Entry<String, String>> entries = headers.entrySet();


                for (Map.Entry<String, String> item : entries) {

                    String key = item.getKey();
                    String value = item.getValue();
                    httpGet.addHeader(key.trim(), value.trim());

                }

            }
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

                Header[] headers1 = response.getHeaders("set-cookie");

                return headers1[1].getValue();
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                httpclient.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return resultString;
    }


    public static String doPost(String url) {
        return doPost(url, null);
    }


    public static String doPost(String url, Map<String, String> param) {


        return doPost(url, param, null);

    }


    public static String doPost(String url, Map<String, String> param, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            HttpPost httpPost = new HttpPost(url);
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                Iterator var7 = param.keySet().iterator();

                while (var7.hasNext()) {
                    String key = (String) var7.next();
                    paramList.add(new BasicNameValuePair(key, (String) param.get(key)));
                }

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            if (headers != null && headers.size() > 0) {

                Set<Map.Entry<String, String>> entries = headers.entrySet();


                for (Map.Entry<String, String> item : entries) {

                    String key = item.getKey();
                    String value = item.getValue();
                    httpPost.addHeader(key.trim(), value.trim());

                }

            }
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        return resultString;
    }


}
