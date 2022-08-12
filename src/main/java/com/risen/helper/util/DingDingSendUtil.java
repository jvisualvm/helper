package com.risen.helper.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.risen.helper.constant.DingDingMsgTypeEnum;
import com.risen.helper.constant.Symbol;
import com.risen.helper.consumer.ConditionConsumer;
import com.risen.helper.response.DingDingResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/1 9:29
 */
public class DingDingSendUtil {

    /**
     * 群里面发送消息
     *
     * @param content
     * @throws Exception
     */

    public static DingDingResponse sendMsgText(String content, String secret, String webhook, boolean isAtAll, List<String> mobileList, List<String> userIdList) {
        return sendMsg(content, null, DingDingMsgTypeEnum.TEXT, secret, webhook, isAtAll, mobileList, userIdList);
    }


    public static DingDingResponse sendMarkdown(String content, String title, String secret, String webhook, boolean isAtAll, List<String> mobileList, List<String> userIdList) {
        return sendMsg(content, title, DingDingMsgTypeEnum.MARKDOWN, secret, webhook, isAtAll, mobileList, userIdList);
    }


    private static DingDingResponse sendMsg(String content, String title, DingDingMsgTypeEnum dingMsgTypeEnum, String secret, String webhook, boolean isAtAll, List<String> mobileList, List<String> userIdList) {
        DingDingResponse dingDingResponse = null;
        try {
            Long timestamp = System.currentTimeMillis();
            //拼接
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append(timestamp).append("\n").append(secret);
            //使用HmacSHA256算法计算签名
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(signBuilder.toString().getBytes("UTF-8"));
            //进行Base64 encode 得到最后的sign，可以拼接进url里
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            StringBuilder dingUrlBuilder = new StringBuilder();
            dingUrlBuilder.append(webhook);
            dingUrlBuilder.append("&timestamp=");
            dingUrlBuilder.append(timestamp);
            dingUrlBuilder.append("&sign=");
            dingUrlBuilder.append(sign);
            try {
                //组装请求内容
                AtomicReference<String> reqStr = new AtomicReference<>();
                ConditionConsumer ifConsumer = () -> {
                    reqStr.set(buildReqText(content, isAtAll, mobileList));
                };
                ConditionConsumer ifElseConsumer = () -> {
                    StringBuilder builder = new StringBuilder();
                    builder.append(content);
                    for (String moblie : mobileList) {
                        builder.append(Symbol.SYMBOL_AT);
                        builder.append(moblie);
                    }
                    reqStr.set(buildReqMarkdown(builder.toString(), title, isAtAll, mobileList, userIdList));
                };
                ConditionConsumer elseConsumer = () -> {

                };
                IfUtil.goIf(ifConsumer, ifElseConsumer, elseConsumer, dingMsgTypeEnum.equals(DingDingMsgTypeEnum.TEXT), dingMsgTypeEnum.equals(DingDingMsgTypeEnum.MARKDOWN));

                //推送消息（http请求）
                String result = HttpUtil.post(dingUrlBuilder.toString(), reqStr.get());
                LogUtil.info("sendMsg req:{} \n result:{}", JSON.toJSONString(reqStr), JSON.toJSONString(result));
                dingDingResponse = JSON.parseObject(result, DingDingResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dingDingResponse;
    }


    /**
     * 组装请求报文
     *
     * @param content
     * @return
     */
    private static String buildReqMarkdown(String content, String title, boolean isAtAll, List<String> mobileList, List<String> userIdList) {
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("title", title);
        contentMap.put("text", content);
        Map<String, Object> atMap = Maps.newHashMap();
        atMap.put("isAtAll", isAtAll);
        if (CollectionUtils.isEmpty(mobileList)) {
            mobileList = new ArrayList<>();
        }
        atMap.put("atMobiles", mobileList);
        atMap.put("atUserIds", userIdList);
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("msgtype", DingDingMsgTypeEnum.MARKDOWN.getType());
        reqMap.put("at", atMap);
        reqMap.put("markdown", contentMap);
        return JSON.toJSONString(reqMap);
    }


    /**
     * 组装请求报文
     *
     * @param content
     * @return
     */
    private static String buildReqText(String content, boolean isAtAll, List<String> mobileList) {
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("content", content);
        Map<String, Object> atMap = Maps.newHashMap();
        atMap.put("isAtAll", isAtAll);
        if (CollectionUtils.isEmpty(mobileList)) {
            mobileList = new ArrayList<>();
        }
        atMap.put("atMobiles", mobileList);
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("msgtype", DingDingMsgTypeEnum.TEXT.getType());
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);
        return JSON.toJSONString(reqMap);
    }
}
