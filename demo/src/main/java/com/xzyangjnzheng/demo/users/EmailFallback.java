package com.xzyangjnzheng.demo.users;

import org.springframework.stereotype.Component;

@Component
public class EmailFallback implements EmailInfoServiceClient {
    @Override
    public String getEmailInfo(String emailId) {
        return "This is fallback message when email server is down...";
    }
}
