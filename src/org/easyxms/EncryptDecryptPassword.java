package org.easyxms;

import it.sauronsoftware.base64.*;

class EncryptDecryptPassword {

    /** 加密字符串 */
    static String Encrypt(String password){
        return Base64.encode(password);
    }

    /** 解密字符串 */
    static String Decrypt(String encrypt_password){
        return Base64.decode(encrypt_password);
    }
}
