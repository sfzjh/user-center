package com.sfzjh.common;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.sfzjh.exception.BusinessException;

/**
 * SM4加解密工具类
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:42
 */
public class Sm4Utils {

    //key必须是16字节，即128位
    final static String key = "lIIl0.*.o0o0lIIl";

    //指明加密算法和秘钥
    static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());

    /**
     * 加密为16进制，也可以加密成base64/字节数组
     *
     * @param plaintext 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String encryptSm4(String plaintext) {
        if (StrUtil.isBlank(plaintext)) {
            return "";
        }
        try {
            return sm4.encryptHex(plaintext);
        } catch (Exception exception) {
            throw new BusinessException("密码加密失败", 500, null);
        }
    }

    /**
     * 解密
     *
     * @param ciphertext 需要解密的字符串
     * @return 返回解密后的字符串
     */
    public static String decryptSm4(String ciphertext) {
        if (StrUtil.isBlank(ciphertext)) {
            return "";
        }
        try {
            return sm4.decryptStr(ciphertext);
        } catch (Exception exception) {
            throw new BusinessException("密码解密失败", 500, null);
        }
    }
}
