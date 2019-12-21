package com.booking.service;

import cn.hutool.crypto.asymmetric.RSA;
import com.booking.utils.CaptchaInfo;
import com.booking.utils.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface LoginService {
    public ResponseEntity<String> getPublicKey(String email);
    public ResponseEntity<CaptchaInfo> getCaptcha(String email) throws IOException, MessagingException;
    public ResponseEntity<String> login(String password, String email) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    public ResponseEntity<String> register(String password, String email, String captcha, String token) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
