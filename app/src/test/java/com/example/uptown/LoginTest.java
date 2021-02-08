package com.example.uptown;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class LoginTest {
    @Test
    public void testIsEmailValid() {
        String testEmail = "shaakira@gmail.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail), Login.checkEmailForValidity(testEmail), is(true));
    }
}
