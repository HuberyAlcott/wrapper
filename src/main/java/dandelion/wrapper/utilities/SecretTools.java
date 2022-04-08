package dandelion.wrapper.utilities;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Dell-PC
 */
@Slf4j
public final class SecretTools {

  private SecretTools() {}

  public static final class Md5 {

    private Md5() {}

    @SneakyThrows
    public static byte[] digest(byte[] content, boolean isMd5) {
      MessageDigest messageDigest;
      String algorithm = isMd5 ? "MD5" : "SHA";
      messageDigest = MessageDigest.getInstance(algorithm);
      return messageDigest.digest(content);
    }

    public static byte[] digest(String content, boolean isMd5) throws Exception {
      MessageDigest messageDigest;
      String algorithm = isMd5 ? "MD5" : "SHA";
      messageDigest = MessageDigest.getInstance(algorithm);
      messageDigest.update(content.getBytes());
      return messageDigest.digest();
    }

    public static String mask(String ss) {
      /*如果为空，则返回""*/
      final var s = ss == null ? "" : ss;
      /*字典*/
      final var hax =
          new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
          };
      try {
        /*获取二进制*/
        final var strTemp = s.getBytes();
        final var mdTemp = MessageDigest.getInstance("MD5");
        /*执行加密*/
        mdTemp.update(strTemp);
        /*加密结果*/
        final var md = mdTemp.digest();
        /*结果长度*/
        final var j = md.length;
        /*字符数组*/
        final var str = new char[j * 2];
        var k = 0;
        /*将二进制加密结果转化为字符*/
        for (var byte0 : md) {
          str[k++] = hax[byte0 >>> 4 & 0xf];
          str[k++] = hax[byte0 & 0xf];
        } /*输出加密后的字符*/
        return new String(str);
      } catch (Exception e) {
        log.error("Md5 is errored when mask '{}'", e.getMessage());
        e.printStackTrace();
        return null;
      }
    }
  }

  public static final class Aes {
    private Aes() {}

    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String generateKey() throws NoSuchAlgorithmException {
      KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
      keygen.init(256, new SecureRandom()); // 16 字节 == 128 bit
      // keygen.init(128, new SecureRandom(seedStr.getBytes())); // 随机因子一样，生成出来的秘钥会一样
      SecretKey secretKey = keygen.generateKey();
      return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private static SecretKeySpec getSecretKeySpec(String secretKeyStr) {
      byte[] secretKey = Base64.getDecoder().decode(secretKeyStr);
      System.out.println(secretKey.length);
      return new SecretKeySpec(secretKey, ALGORITHM);
    }

    public static String encrypt(String content, String secretKey) throws Exception {
      Key key = getSecretKeySpec(secretKey);
      Cipher cipher = Cipher.getInstance(ALGORITHM); // 创建密码器
      cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
      byte[] result = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
      return Base64.getEncoder().encodeToString(result);
    }

    public static String decrypt(String content, String secretKey) throws Exception {
      Key key = getSecretKeySpec(secretKey);
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
      return new String(result);
    }
  }

  public static final class Rsa {

    private Rsa() {}

    @Data
    public static class RsaKeyPair {
      private final String pubKey;
      private final String priKey;

      RsaKeyPair(String pubKey, String priKey) {
        super();
        this.pubKey = pubKey;
        this.priKey = priKey;
      }
    }

    private static final String ALGORITHM = "RSA";
    private static final String ALGORITHMS_SHA1_WITH_RSA = "SHA1WithRSA";
    private static final String ALGORITHMS_SHA256_WITH_RSA = "SHA256WithRSA";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
      KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
      SecureRandom random = new SecureRandom();
      /*SecureRandom random = new SecureRandom(seedStr.getBytes());*/
      /*随机因子一样，生成出来的秘钥会一样*/
      keygen.initialize(2048, random);
      /*生成密钥对*/
      KeyPair keyPair = keygen.generateKeyPair();
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
      String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
      return new RsaKeyPair(publicKeyStr, privateKeyStr);
    }

    private static RSAPublicKey getPublicKey(String publicKey) throws Exception {
      byte[] keyBytes = Base64.getDecoder().decode(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
      return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    private static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
      final var keyBytes = Base64.getDecoder().decode(privateKey);
      final var spec = new PKCS8EncodedKeySpec(keyBytes);
      final var keyFactory = KeyFactory.getInstance(ALGORITHM);
      return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    @SneakyThrows
    public static String encode(RSAPublicKey key, String val) {
      return getKeyFactory(val, key);
    }

    @SneakyThrows
    public static String encode(String pubKey, String val) {
      final var key = getPublicKey(pubKey);
      return getKeyFactory(val, key);
    }

    @SneakyThrows
    public static String decode(RSAPrivateKey key, String val) {
      final var cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key);
      final var result = cipher.doFinal(val.getBytes(DEFAULT_CHARSET));
      return new String(result);
    }

    @SneakyThrows
    public static String decode(String priKey, String val) {
      final var key = getPrivateKey(priKey);
      final var cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key);
      final var result = cipher.doFinal(val.getBytes(DEFAULT_CHARSET));
      return new String(result);
    }

    private static String getKeyFactory(String val, RSAPublicKey key)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
      final var keyFactory = KeyFactory.getInstance(ALGORITHM);
      final var cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(Cipher.ENCRYPT_MODE, key);
      final var result = cipher.doFinal(val.getBytes());
      return Base64.getEncoder().encodeToString(result);
    }
  }
}
