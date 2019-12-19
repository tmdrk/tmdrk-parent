package org.tmdrk.toturial.arithmetic.encryption.RSA;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @ClassName RSA
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/15 12:04
 * @Version 1.0
 **/
public class RSA {
    private static Logger mLogger = LoggerFactory.getLogger(RSA.class);
    //		private static String publicKey = "";
    //		private static String privateKey = ""
//    protected String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBA6Rl2zhuBtQSEvkVpqWQwTnhwe2uGUSpIufFP2yLH0sbMPAjTkycQk9XGQnwsnjJ3V/ptbjGom2ILTd7hHJMtb9hhfcFRcOIKTMaMO3XD9nYI+R3xO2jhMu2bPVBLKTarKvmfklr8pvNjvetOaCbng3rwkkjcjv68kLZlhy3YQIDAQAB";
//    protected String privateKey = "MIICXAIBAAKBgQDBA6Rl2zhuBtQSEvkVpqWQwTnhwe2uGUSpIufFP2yLH0sbMPAjTkycQk9XGQnwsnjJ3V/ptbjGom2ILTd7hHJMtb9hhfcFRcOIKTMaMO3XD9nYI+R3xO2jhMu2bPVBLKTarKvmfklr8pvNjvetOaCbng3rwkkjcjv68kLZlhy3YQIDAQABAoGAB6QfxYKEvOJbUe3bW46R3mWv526YfLx2WeXOXCIzJ1zRSd3Jm/Q1FziO0Ilmudcu7frsGaH+kyqKAIqduC+ZoLsQgeT4cAotzNGGZRn0fANsE6fxEgxt7AcWCODnIWbEUsOHbeVFyjF/7SadVhO/+dJnmX/LGM/yw2RS/3QjanMCQQD8sJIAWk0KE7R17ZcRkaTrejf2lWuWVs3S4KykTrrLivpfPglDkVrS8PwR0DXli55c9TXtFLMbWgH//D1IXN4LAkEAw4rxHw+ew8TYDxzMWcO0P3+8MpC2ryjSVuN+Uc0f0lo0x13Wc1Zi/c/BZs5+94YoMAjI1mEgQ5XQlOUyElzfwwJBAKWXBCZtBp066oB5UQ03V07kybWyl01u1vSBPUFzQl/OVGKDociAgXdIarc1rYweYYnjOxKBBRpAcp0Q7Av2p58CQBqWdsihaBX4WuRbJxIBgS2tIZrCgIR6iXcVAaT/vhbs+wYspS8TjOwz5nkjFLJ1RFubpis4E5n88dp8+3zxsd8CQFFdPrYtaR8NaK/iPHk2NcvAdS5FPtOUu0FZJDy8X32agfADgFoaAp4r/6NPU2KQt6OvT8go9q//t3DI+U6NxdY=";
    protected String publicKey =   "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWDOUKP/Q1Bm9DleOhjGd7r+zxm3qoprQB1gFHxYm4vAktYHv7e5lKYBktMuECmqk2AX4ZnjCl/9i3gLcSBZXYUlDRwvYPpR9YtvZxyw8My8WP+0o5Kpkud5Omj+Ay3FCOAcFXBle3i91VuvKho3tzAbDhcSFBv1YczhbkK9dZ2wIDAQAB";
    protected String privateKey =   "MIICXAIBAAKBgQCWDOUKP/Q1Bm9DleOhjGd7r+zxm3qoprQB1gFHxYm4vAktYHv7e5lKYBktMuECmqk2AX4ZnjCl/9i3gLcSBZXYUlDRwvYPpR9YtvZxyw8My8WP+0o5Kpkud5Omj+Ay3FCOAcFXBle3i91VuvKho3tzAbDhcSFBv1YczhbkK9dZ2wIDAQABAoGAKWfIgjIQrNsoM9G2PTt1yNtMeoVJbSWHMaSAuPHpJZl5vNRNkiFStQPcnqzWC3ZrMBRsXBxbV0tI3aY+nlH9PYaDkflvRUDHv/TXB2TlxyYil83aTh0o7QyiVokHJgjhpSxftA5eXIigd/GGPLNFvevYMwal45tnPx+Stl9t+OUCQQC8JNt3k6vZz7ZyupwRXqRWDlZSBvCMCCcJ16zMnvW+1a61Or0cZ3Fr1/w+u2x0xHjcCdaB8mHMVP6vDTB0Gn2nAkEAzCrku/KSHHOvbE1FfkZfpFsFwoPiTU/U5+Zwx3wcl4OphyklVT2PCNc+Q4a9HsTjt058bisrYZWPzwvE//oQrQJAfLdl7/8otMq245iF6hG1mAILAHbvL7QbxK9+MWnR9v2IwUO9pj1/9wAGisMM4t52S04OLrewUpWo+fshOmVxEwJBALkS6OW2rdw8wfha03P0Nx61pDL/elS62TSeH/208/Yk07hCBCWQ9+f7teVgbm9F62ZvLzxuxZRgD0yQTrP76sECQAi4ySc+QpV/VmEOmuI/vDODhaMFxTmaXwCQTkl7uFoVrILcTpV+46KGRenp1BSdMkuL+pIR9wWzh0+U7FuxotE=";
    //	protected static String chiperMode = "RSA/None/NoPadding";
    protected static String chiperMode = "RSA";
    //	public static int key_bit = 2048;
    protected int key_bit = 1024;
    protected int key_len = key_bit / 8;

	static {
		Security.addProvider(new BouncyCastleProvider()); //需要引入bcprov-jdk16(1.46)jar包
	}
	/***************** 两个方式都可以 ****************/
//    static{
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);//需要引入bcprov-jdk16(1.46)jar包
//    }

    public RSA(String publicKey, String privateKey, int key_bit) {
        super();
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.key_bit = key_bit;
        this.key_len = this.key_bit / 8;
        init();
    }

    RSAPrivateKey mPvKey = null;
    RSAPublicKey mPubKey = null;

    public RSA() {
        init();
    }

    private void init() {
        try {
            mPubKey = (RSAPublicKey)getPublicRSAKey(publicKey);
            mPvKey = (RSAPrivateKey)getPrivateRSAKey(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String mMsg = "62105300";
        //		String mMsg = "62105300发送的看来附近拉撒的减肥了阿斯加德了放空精神了困就山东矿机拉萨大街拉伸拉伸爱神的箭发四六级拉伸宽带缴费拉丝机的发大立科技死啦肯德基发送垃圾袋拉伸控件的拉丝机卡三等奖速度快垃圾拉丝机拉烧烤就阿萨德阿斯蒂芬洒阿萨德是啊"
        //				+ "62105300发送的看来附近拉撒的减肥了阿斯加德了放空精神了困就山东矿机拉萨大街拉伸拉伸爱神的箭发四六级拉伸宽带缴费拉丝机的发大立科技死啦肯德基发送垃圾袋拉伸控件的拉丝机卡三等奖速度快垃圾拉丝机拉烧烤就阿萨德阿斯蒂芬洒阿萨德是啊"
        //				+ "62105300发送的看来附近拉撒的减肥了阿斯加德了放空精神了困就山东矿机拉萨大街拉伸拉伸爱神的箭发四六级拉伸宽带缴费拉丝机的发大立科技死啦肯德基发送垃圾袋拉伸控件的拉丝机卡三等奖速度快垃圾拉丝机拉烧烤就阿萨德阿斯蒂芬洒阿萨德是啊"
        //				+ "62105300发送的看来附近拉撒的减肥了阿斯加德了放空精神了困就山东矿机拉萨大街拉伸拉伸爱神的箭发四六级拉伸宽带缴费拉丝机的发大立科技死啦肯德基发送垃圾袋拉伸控件的拉丝机卡三等奖速度快垃圾拉丝机拉烧烤就阿萨德阿斯蒂芬洒阿萨德是啊"
        //				+ "62105300发送的看来附近拉撒的减肥了阿斯加德了放空精神了困就山东矿机拉萨大街拉伸拉伸爱神的箭发四六级拉伸宽带缴费拉丝机的发大立科技死啦肯德基发送垃圾袋拉伸控件的拉丝机卡三等奖速度快垃圾拉丝机拉烧烤就阿萨德阿斯蒂芬洒阿萨德是啊";
        RSA rasE = new RSA();
        try {
            //			String resp = rasE.encrypt(mMsg);
            //			String deSrc = rasE.decrypt(resp);
            String ciphertext = rasE.encrypt(mMsg);
            String plaintext = rasE.decrypt(ciphertext);
            mLogger.info("isMatch={}", plaintext.equals(mMsg));
            mLogger.info("ciphertext={}\nplaintext={}", ciphertext, plaintext);
            //			mLogger.info(String.format("src=%s,\nrsaRes =%s，\ndeSrc=%s", mMsg, deSrc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String decrypt(String msg)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(chiperMode);
        cipher.init(Cipher.DECRYPT_MODE, mPvKey);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] bytes = base64DecodeToByte(msg);
        int dataLen = bytes.length;
        int maxLength = key_len;
        int i = 0;
        while (i < dataLen) {
            int len = maxLength;
            if (i + maxLength >= dataLen) {
                len = dataLen - i;
            }
            byte[] tBytes = new byte[len];
            System.arraycopy(bytes, i, tBytes, 0, len);
            byte[] newBytes = cipher.doFinal(tBytes);
            try {
                bao.write(newBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i += maxLength;
        }
        final String data = new String(bao.toByteArray());
        return data;
    }

    public String encrypt(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        //RSA加密
        Cipher cipher = Cipher.getInstance(chiperMode);
        cipher.init(Cipher.ENCRYPT_MODE, mPubKey);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] bytes = msg.getBytes();
        int dataLen = bytes.length;
        int maxLength = key_len - 11;
        int i = 0;
        while (i < dataLen) {
            int len = maxLength;
            if (i + maxLength >= dataLen) {
                len = dataLen - i;
            }
            byte[] tBytes = new byte[len];
            System.arraycopy(bytes, i, tBytes, 0, len);
            byte[] newBytes = cipher.doFinal(tBytes);
            try {
                bao.write(newBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i += maxLength;
        }
        String outStr = base64Encode(bao.toByteArray());
        return outStr;
    }

    private static String base64Encode(byte[] data) {
        String base64 = new String(Base64.getEncoder().encode(data));
        return base64;
    }

    private static String base64Decode(String bytes) {
        byte[] msg = Base64.getDecoder().decode(bytes.getBytes());
        return new String(msg);
    }

    private static byte[] base64DecodeToByte(String bytes) {
        byte[] msg = Base64.getDecoder().decode(bytes.getBytes());
        return msg;
    }

    private static byte[] base64EncodeToByte(String bytes) {
        byte[] msg = Base64.getEncoder().encode(bytes.getBytes());
        return msg;
    }

    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = base64DecodeToByte(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = base64DecodeToByte(key);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey publicKey = keyFactory.generatePrivate(keySpec);
        return publicKey;
    }

    public static PublicKey getPublicRSAKey(String key) throws Exception {
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(base64DecodeToByte(key));
        KeyFactory kf = KeyFactory.getInstance(chiperMode);
        return kf.generatePublic(x509);
    }

    public static PrivateKey getPrivateRSAKey(String key) throws Exception {
        PKCS8EncodedKeySpec pkgs8 = new PKCS8EncodedKeySpec(base64DecodeToByte(key));
        KeyFactory kf = KeyFactory.getInstance(chiperMode);
        return kf.generatePrivate(pkgs8);
    }
}
