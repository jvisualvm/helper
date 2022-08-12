package com.risen.helper.http;

import com.alibaba.fastjson.JSON;
import com.risen.helper.exception.BusinessException;
import com.risen.helper.response.Result;
import com.risen.helper.util.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/8 15:43
 */
@Component
@AllArgsConstructor
public class HttpHelper implements Serializable {

    private RestTemplate template;

    /**
     * @param url
     * @param param
     * @return java.lang.String
     * @author: zhangxin
     * @description: TODO
     * @date: 2022/3/8 16:06
     */
    @Deprecated
    public String getWithStr(String url, Map<Object, Object> param) {
        ResponseEntity<String> entity = template.getForEntity(url, String.class, param);
        if (HttpStatus.OK.equals(entity.getStatusCodeValue())) {
            return entity.getBody();
        }
        throw new BusinessException(entity.getStatusCodeValue(), JSON.toJSONString(entity.getBody()));
    }

    /**
     * @param url
     * @param param
     * @return com.risen.reponse.Result
     * @author: zhangxin
     * @description: 如果接口返回的响应格式和此服务定义好的格式一致，则使用此方法
     * @date: 2022/3/8 16:04
     */
    @Deprecated
    public Result getWithBody(String url, Map<String, String> param) {
        ResponseEntity<Result> entity = template.getForEntity(url, Result.class, param);
        if (HttpStatus.OK.equals(entity.getStatusCodeValue())) {
            return entity.getBody();
        }
        throw new BusinessException(entity.getStatusCodeValue(), JSON.toJSONString(entity.getBody()));
    }

    @Deprecated
    public Result post(String url, Object param, Map<String, String> headMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.setAll(headMap);
        HttpEntity httpEntity = new HttpEntity<>(param, headers);
        Result result = template.postForObject(url, httpEntity, Result.class);
        LogUtil.info("post url:{},param:{},header:{},result:{}", url, JSON.toJSONString(param), JSON.toJSONString(headMap), JSON.toJSONString(result));
        return result;
    }


    public ResponseEntity<String> request(String url, HttpMethod method, Object param, Consumer<Map<String, String>> consumer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        Map<String, String> appendHeader = new HashMap<>();
        consumer.accept(appendHeader);
        headers.setAll(appendHeader);
        HttpEntity httpEntity = new HttpEntity<>(param, headers);

        ResponseEntity<String> response = template.exchange(
                url, method, httpEntity, String.class);

        LogUtil.info("request url:{},param:{},header:{},result:{}", url, JSON.toJSONString(param), JSON.toJSONString(appendHeader), JSON.toJSONString(response.getBody()));
        return response;
    }


    public ResponseEntity<String> getNoBody(String url, Consumer<Map<String, String>> param) {
        Map<String, String> appendHeader = new HashMap<>();
        param.accept(appendHeader);
        return template.getForEntity(url, String.class, appendHeader);
    }

    public ResponseEntity<String> getNoBodyTrust(String url, Consumer<Map<String, String>> param, RestTemplate restTemplate) {
        Map<String, String> appendHeader = new HashMap<>();
        param.accept(appendHeader);
        return restTemplate.getForEntity(url, String.class, appendHeader);
    }


    public ResponseEntity<String> getWithBodyTrust(String url, Map<String, String> param, RestTemplate restTemplate) {
        return restTemplate.getForEntity(url, String.class, param);
    }

    public ResponseEntity<String> requestTrust(String url, HttpMethod method, Object param, Consumer<Map<String, String>> consumer, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        Map<String, String> appendHeader = new HashMap<>();
        consumer.accept(appendHeader);
        headers.setAll(appendHeader);
        HttpEntity httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, method, httpEntity, String.class);

        LogUtil.info("request url:{},param:{},header:{},result:{}", url, JSON.toJSONString(param), JSON.toJSONString(appendHeader), JSON.toJSONString(response.getBody()));
        return response;
    }


}
