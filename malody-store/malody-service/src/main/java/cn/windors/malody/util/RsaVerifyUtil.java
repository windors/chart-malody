package cn.windors.malody.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;


/**
 * @author Wind_Yuan
 * TODO 校验
 * C# 公钥转Java: https://blog.csdn.net/yupu56/article/details/72624229
 * 版权声明：本文为CSDN博主「HFUT_qianyang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 *     原文链接：https://blog.csdn.net/qy20115549/article/details/83105736
 *
 *
 *     ————————————————
 *     版权声明：本文为CSDN博主「飘逝的落叶纷飞」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 *     原文链接：https://blog.csdn.net/dslinmy/article/details/37362661
 *
 */
@Component
public class RsaVerifyUtil {
    private static final PublicKey PUBLIC_KEY = getPublicKey("sgQb7aIukw8OqyqveicRQe75C11EA0QLpMGXtS0QCbVaid1zICJeyIhiYBmCm05ygsFkfoh+qahey/8NtU51NvJByBGe3CpgSTiaH9uhAdsLI4LttVqhUYQDJpI0NbRZ4FpTMAd9rcPwV7p4N3K8oHaKaFLbffyd1i9Pl001RXk=",
            "AQAB");

    public static void main(String[] args) throws Exception {
        // key从url中获取
        String key = "";
        String uid = "";
        boolean b = verifySign(uid.getBytes(StandardCharsets.US_ASCII), PUBLIC_KEY, Base64.getUrlDecoder().decode(key));
    }

    /**
     * 返回RSA公钥
     * @param modulus c# 文件中的modules
     * @param exponent C# 文件中的 exponent
     * @return
     */
    public static PublicKey getPublicKey(String modulus, String exponent) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] m = decoder.decode(modulus);
            byte[] e = decoder.decode(exponent);
            BigInteger b1 = new BigInteger(1, m);
            BigInteger b2 = new BigInteger(1, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证签名
     * @param msg 待验证签名字符串
     * @param pubKey 公钥
     * @param sign 待验证签名值解码后的byte数组
     * @return 是否
     * @throws Exception
     */
    private static boolean verifySign(byte[] msg, PublicKey pubKey, byte[] sign)
            throws Exception {
        //初始化算法SHA256
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(pubKey);
        signature.update(msg);
        return signature.verify(sign);
    }

    public static boolean verifySign(String msg, String sign) throws Exception {
        return msg != null && sign != null && verifySign(msg.getBytes(StandardCharsets.US_ASCII), PUBLIC_KEY, Base64.getUrlDecoder().decode(sign));
    }
}
