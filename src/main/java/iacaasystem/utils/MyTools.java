package iacaasystem.utils;


import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

public class MyTools {
    public static String toMd5String(String str){
        //加密
        MessageDigest md5;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            try {
                md5 = MessageDigest.getInstance("MD5");
            }catch (Exception e2){
                e2.printStackTrace();
                return "";
            }
        }
        String slat = "23jke-ahsdj6568%@&&&&__&%5123***&&%%$$#@HSIH";

        String dataStr = str + slat;
        try{
            md5.update(dataStr.getBytes("UTF8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        byte s[] = md5.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
        }
        return result;
    }
}
