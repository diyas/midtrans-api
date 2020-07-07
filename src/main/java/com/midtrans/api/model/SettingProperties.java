package com.midtrans.api.model;

import lombok.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "midtrans")
@Data
@Order
public class SettingProperties {

    private Api api = new Api();

    public Path getPathAction(String action){
        System.out.println(action);
        if (action.equalsIgnoreCase("status"))
            return this.getApi().getPath().get(1);
        else if(action.equalsIgnoreCase("cancel"))
            return this.getApi().getPath().get(2);
        else if(action.equalsIgnoreCase("qr"))
            return this.getApi().getPath().get(3);
        return null;
    }
    @Data
    public class Api {
        private String credential;
        private List<String> baseurl = new ArrayList<>();
        private List<Path> path = new ArrayList<>();
        private String notifurl;
        private String logdir;
    }
    @Data
    public static class Path {
        private String link;
        private HttpMethod method;
    }
}
