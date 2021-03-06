package com.xzyangjnzheng.demo.users;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig config) {
//        String pingPath = "/actuator/health";
//        IPing ping = new PingUrl(false, pingPath);
//        log.info("Configuring ping URI to [{}]", pingPath);
//        return ping;
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
