package org.easyxms;


import it.sauronsoftware.base64.*;


/**
 * 加密解密类
 */
class EncryptDecryptPassword {


    /**
     * 加密字符串
     * @param password 要加密的字符串
     * @return 加密后的字符串
     */
    static String Encrypt(String password){
        return Base64.encode(password);
    }


    /**
     * 解密字符串
     * @param encrypt_password 加密过的字符串
     * @return 解密后的字符串
     */
    static String Decrypt(String encrypt_password){
        return Base64.decode(encrypt_password);
    }
}
