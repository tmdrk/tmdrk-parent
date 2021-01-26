package org.tmdrk.toturial.arithmetic.encryption.RSA.eleme;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ele
 */
public class RSA2Util {

    public static boolean rsa256Check(String content, String sign, String publicKey, String charset) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(pubKey);
        signature.update(content.getBytes(charset));
        return signature.verify(Base64Utils.decode(sign.getBytes()));
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        io(new InputStreamReader(ins), writer, -1);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64Utils.decode(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }


    public static String rsa256Sign(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPkcs8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64Utils.encode(signed));
        } catch (Exception var6) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, var6);
        }
    }


    public static PrivateKey getPrivateKeyFromPkcs8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = readText(ins).getBytes();
            encodedKey = Base64Utils.decode(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static String readText(InputStream in) throws IOException {
        return readText(in, (String) null, -1);
    }

    public static String readText(InputStream in, String encoding, int bufferSize) throws IOException {
        Reader reader = encoding == null ? new InputStreamReader(in) : new InputStreamReader(in, encoding);
        return readText(reader, bufferSize);
    }


    public static String readText(Reader reader, int bufferSize) throws IOException {
        StringWriter writer = new StringWriter();
        io((Reader) reader, (Writer) writer, bufferSize);
        return writer.toString();
    }

    public static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = 4096;
        }

        char[] buffer = new char[bufferSize];

        int amount;
        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }

    }
}
