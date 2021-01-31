package com.JW.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return privateUserEncoding(charSequence);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        String formPaswd = privateUserEncoding(rawPassword);

        return Objects.equals(formPaswd, encodedPassword);
    }


    private String privateUserEncoding(CharSequence rawPassword) {

        try {
            // 1.创建 MessageDigest 对象
            String algorithm = "MD5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 2.获取 rawPassword 的字节数组
            byte[] input = ((String)rawPassword).getBytes();

            // 3.加密
            byte[] output = messageDigest.digest(input);

            // 4.转换为 16 进制数对应的字符
            //toUpperCase()存储为大写
            //  e10adc3949ba59abbe56e057f20f883e
            //  E10ADC3949BA59ABBE56E057F20F883E
            String encoded = new BigInteger(1, output).toString(16).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String s = new MyPasswordEncoder().privateUserEncoding("123456");
        String s1 = new MyPasswordEncoder().privateUserEncoding(s);
        String s2 = new MyPasswordEncoder().privateUserEncoding(s1);
        System.out.println(s2);
    }


    }
