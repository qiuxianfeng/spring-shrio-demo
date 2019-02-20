package com.sping.shiro.demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class createPassword {

	/**
     * 生成测试用的md5加密的密码
     * @param args
     */
    public static void main(String[] args) {
        String hashAlgorithmName = "md5";//加密方式, 散列算法:这里使用MD5算法;
        String credentials = "123456";//密码
        int hashIterations = 2; //加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes("root");//盐,为了即使相同的密码不同的盐加密后的结果也不同
        String  obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toHex();
        System.out.println(obj);
    }
}
