package com.risen.helper.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 19:14
 */
public class UserClientUtil {

    private static UASparser uasParser = null;

    // 初始化uasParser对象
    static {
        try {
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientDetail getUserClintDetail(HttpServletRequest request) {
        ClientDetail clientDetail = null;
        try {
            UserAgentInfo userAgentInfo = UserClientUtil.uasParser.parse(request.getHeader("user-agent"));
            clientDetail = ClientDetail.builder().
                    browser(userAgentInfo.getUaName()).
                    osName(userAgentInfo.getOsName()).
                    deviceType(userAgentInfo.getDeviceType()).
                    client(userAgentInfo.getType()).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientDetail;
    }


    @Builder
    @Data
    public static class ClientDetail {
        public String osName;
        public String deviceType;
        public String browser;
        public String client;
    }

}
