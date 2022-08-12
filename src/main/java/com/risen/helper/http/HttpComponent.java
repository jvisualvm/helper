package com.risen.helper.http;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/29 20:31
 */
@Component
public abstract class HttpComponent<T, R> {

    private static ConcurrentHashMap<String, HttpComponent> implTree = new ConcurrentHashMap<>();

    @Autowired
    private HttpHelper httpHelper;

    @Autowired
    @Qualifier(value = "trustRestTemplate")
    private RestTemplate trustRestTemplate;


    protected Class<T> response;

    public HttpComponent() {
        implTree.put(uniqueKey(), this);
        response = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract T execute();


    public abstract R createRequest();


    public abstract String url();

    public abstract String uniqueKey();

    public T put(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.request(url(), HttpMethod.PUT, request, headerMap).getBody(), response);
    }

    public T post(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.request(url(), HttpMethod.POST, request, headerMap).getBody(), response);
    }

    public T getByBody(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.request(url(), HttpMethod.GET, request, headerMap).getBody(), response);
    }

    public T getNoBody(Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.getNoBody(url(), headerMap).getBody(), response);
    }


    public T putTrust(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.requestTrust(url(), HttpMethod.PUT, request, headerMap, trustRestTemplate).getBody(), response);
    }

    public T postTrust(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.requestTrust(url(), HttpMethod.POST, request, headerMap, trustRestTemplate).getBody(), response);
    }

    public T getByBodyTrust(Object request, Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.requestTrust(url(), HttpMethod.GET, request, headerMap, trustRestTemplate).getBody(), response);
    }

    public T getNoBodyTrust(Consumer<Map<String, String>> headerMap) {
        return JSON.parseObject(httpHelper.getNoBodyTrust(url(), headerMap, trustRestTemplate).getBody(), response);
    }


    public static HttpComponent getImplTree(String type) {
        return implTree.get(type);
    }

    public static List<HttpComponent> getImplTree() {
        return implTree.values().stream().collect(Collectors.toList());
    }

}
