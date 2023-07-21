package br.com.dfdevforge.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import br.com.dfdevforge.common.exceptions.BaseException;

public class EncryptUtils {
	protected  EncryptUtils() {}

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

	public String toAes(String encrypt) throws BaseException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		SecretKey secretKey = new SecretKeySpec(System.getenv("SISFIN_BACKEND_AES_SECRET").getBytes(), ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedMessage = cipher.doFinal(encrypt.getBytes("UTF-8"));

		return new String(encryptedMessage);
	}

    public String toBase64(String toEncrypt) {
    	return Base64.getEncoder().encodeToString(toEncrypt.getBytes());
    }
}