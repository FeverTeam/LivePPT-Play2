package com.fever.liveppt.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-27
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class Md5Util {
    /**
     * 根据输入的字符串生成固定的32位MD5码
     *
     * @param str
     *            输入的字符串
     * @return MD5码
     */
    public final static String getMd5(String str) {
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mdInst.update(str.getBytes());// 使用指定的字节更新摘要
        byte[] md = mdInst.digest();// 获得密文
        System.out.println("md5 outcome:"+md.toString());
        return md.toString();
       // return StrConvertUtil.byteArrToHexStr(md);
    }
}
