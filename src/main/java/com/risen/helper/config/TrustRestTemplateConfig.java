package com.risen.helper.config;

import com.risen.helper.util.LogUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/22 14:57
 */
@Configuration
public class TrustRestTemplateConfig {

    @Bean("trustRestTemplate")
    public RestTemplate dtRestTemplate() {
        RestTemplate restTemplate = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, disableSslCheck(), new java.security.SecureRandom());
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            RequestConfig requestConfig = RequestConfig.custom()
                    .build();
            CloseableHttpClient sslInsecureClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(sslsf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(sslInsecureClient);
            restTemplate = new RestTemplate(requestFactory);
        } catch (Exception ex) {
            LogUtil.error("restTemplate create failed:" + ex.getMessage());
        }
        return restTemplate;
    }

    private static TrustManager[] disableSslCheck() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }


}
