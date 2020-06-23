package com.xzyangjnzheng.demo.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "email", fallback = EmailFallback.class)
public interface EmailInfoServiceClient {

    @GetMapping(path = "/emails/{emailId}")
    public String getEmailInfo(@PathVariable("emailId") String emailId);
}
