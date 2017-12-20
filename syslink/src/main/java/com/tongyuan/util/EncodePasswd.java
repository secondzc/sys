package com.tongyuan.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static com.tongyuan.util.Convert.toHex;


/**
 * Created by yh on 2017/9/26.
 */
public class EncodePasswd {


    public static String getEncryptedPassword(String password, String salt,  int iterations,  int derivedKeyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, derivedKeyLength * 8);

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return toHex(f.generateSecret(spec).getEncoded());
    }



}
