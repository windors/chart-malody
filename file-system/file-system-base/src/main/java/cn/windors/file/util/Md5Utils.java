package cn.windors.file.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Windor
 */
@Slf4j
public class Md5Utils {

    public static String getMd5(File file) throws FileNotFoundException {
        if(file == null) {
            log.warn("file为null");
            return null;
        }
        return getMd5(new FileInputStream(file));
    }
    /**
     * 计算输入流文件的md5值，如果输入流不为空，则会在计算完毕后关闭输入流
     */
    public static String getMd5(InputStream is) {
        if (is == null) {
            log.warn("输入流为null!");
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.warn("输入流为null!");
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // who cares =v=
            }
        }
        return null;
    }
}
