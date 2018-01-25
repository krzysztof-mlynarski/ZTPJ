package pl.zut.edu.wi.ztpj.jdbc.Services;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum HashUtil {

    SHA1("SHA1"), MD5("MD5"), MD2("MD2"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512("SHA-512");

    private final MessageDigest digester;

    HashUtil(String algorithm) {
        try {
            digester = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String hash(byte[] in) {
        return toHexString(digester.digest(in));
    }

    public String hash(String in) {
    	return hash(in.getBytes());
    }

    public String hash(String in, Charset characterSet) {
        return hash(in.getBytes(characterSet));
    }

    public byte[] hashToByteArray(String in) {
        return digester.digest(in.getBytes());
    }

    public byte[] hashToByteArray(String in, Charset characterSet) {
        return digester.digest(in.getBytes(characterSet));
    }

    public byte[] hashToByteArray(byte[] in) {
        return digester.digest(in);
    }

    private String toHexString(byte[] digest) {
        StringBuffer hexStr = new StringBuffer(40);
        for (byte b : digest) {
            hexStr.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return hexStr.toString();
    }
}
