package com.bth.demo.Util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

public class RSAUtil
{
	public static final String RSA = "RSA";
	public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	public static final String SHA256 = "SHA256withRSA";
	public static final String UTF_8 = "UTF-8";
	public static final String PUB_KMTD = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlHI6DzF6fFG51b0Qm+9FysO0kWz9x1bbJYYB39phWTPPBrJUnm8emaY8xZKOL5COIqtixff4aEGVNsBmlfpjxqWSdcmNeQuvvLAIcakZWQQ4ntgdT6Svga4+aLPRJnNGk/iwKsbp7cuWiSQx/jmw/mbPX+IDdUgTtHWvs1m3Qtx3YAbmRk+x7/3CbmqvVTG7IBwLWXEEBMrhYA3XA5VMisCrqOn38tfOZ0U7bqa5nDuFS4A0Uy1DOTcjNwoeQj30GIE/8rAeN9EOIBAHmeXEP0Qel+7LPsiDEaFLWLafhmxKtHqvoWl/YsfcLUM+hyffiIeipsU89DZYd5AZK6X7zwIDAQAB";
	public static final String PRI_KMTD = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUcjoPMXp8UbnVvRCb70XKw7SRbP3HVtslhgHf2mFZM88GslSebx6ZpjzFko4vkI4iq2LF9/hoQZU2wGaV+mPGpZJ1yY15C6+8sAhxqRlZBDie2B1PpK+Brj5os9Emc0aT+LAqxunty5aJJDH+ObD+Zs9f4gN1SBO0da+zWbdC3HdgBuZGT7Hv/cJuaq9VMbsgHAtZcQQEyuFgDdcDlUyKwKuo6ffy185nRTtuprmcO4VLgDRTLUM5NyM3Ch5CPfQYgT/ysB430Q4gEAeZ5cQ/RB6X7ss+yIMRoUtYtp+GbEq0eq+haX9ix9wtQz6HJ9+Ih6KmxTz0Nlh3kBkrpfvPAgMBAAECggEALuAvM1MFgbJxA2yTjRouqMvJ/k4ziToFM8EwYFbefMOecVfo3qrqN8zFzgHsglqcd5g1NZyLRajIDKUO//c0gKsSJ73AveOR1/yiffnO9kwd8RL/loHDpii2rmtx9TlbpShXwPpVLnEmlSHn44amPH+MppOBUgyzpD2xgOoos6TZww3X4xtlDO/xGUgrCIK951rtNrfUCMEMDDfYOD+/YP+OR/GdhUP/E0n/CekGbSwM4eHZwkYDnyCv2VwnVv7Sd5eVzp+uYWi8DzkEqXC2XTliDyDmqCBpHUh3amFhEKKrhPiGUJmxkA7wLDoUt+5wNKv6mJ4REVdW0HzIW03o0QKBgQD64S2A4ZVbjJ4ST++KJuErxnAfK73v7AqFPvHnzhN06DsIv/rOIF+g0cSjSARQRs804qbl88eoUiWvckOMVD7QpBN5Eim2o5L5k5u3l01QHdly6gMqkraWGK5wp82a6wPwQvbAPk4A9E1eBp5CTaNIGeV9mMLLBUSlhV0RYD5NlwKBgQCXedgcPLHTKWH2sByCgVVd59f6W+0cIUxUl7cf7w9GU3PxkL+xgvwjcwae5/qYEMzQGE9ohYZDd/4uCaC4m/ffGstNcv5oQiDLWuYLhfyK93KrrEzotypKt5cL/Oo2XzfC8DwJTRBPKxeXNffKqEpjahQxsIuClBrtP6qHJN/6iQKBgDpb2Sbq0sCKFFc0KwebBbQWJAzMMxG1ebkHLGR8xn/iaaEeb2w1kakqQrCs1vwJjLfDpYlGTQJ1oXiPfvEMw9Pq5vC57eQjZtQRpx5s3rotR2D42KW1nS2LLkDcd/J6it7/5MBRffmw2rJtnbDUuI/UHlM6Ds4FOrO6My0Qk02fAoGAZkoBhg/2hYIMhWmn1vk/jIpjbs1k8PoLcZSCNS5aQkNYqGf3CwhI/CdQ8T32G3o81MmV1h9U63q8Spp1zjSEzkRpxYNCCWWef7SCZIW2ZKF07jkMNpp6FLqlZZMFFnJ2VU2lPSKtQMj4xUPMcS30De40dNmjXCpnhI7ZuSlNlwECgYEAkn+0otnWdo31bRkSJZBEJrMt70awBNN/0JNOTX48Fgprx0etGtR38XM517K9LQXbARGwXlwsXs4YtIoeOsAwEGZEIIl80GMcQkZ5/DGZ9+UvTQDWBSvxH/eCuWyj4ZfsvpENG6OuadBEYOHYceshK1XzoMlp65M9BJbMFue5Z5o=";
	private static final Logger LOG = Logger.getLogger(RSAUtil.class);

	private RSAPublicKey createPublicKey(String key)
	{
		byte[] keyBytes = Base64.decodeBase64(key);

		KeyFactory keyFactory = null;
		try
		{
			keyFactory = KeyFactory.getInstance(RSA);
		}
		catch (NoSuchAlgorithmException ex)
		{
		}
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
		try
		{
			return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		}
		catch (InvalidKeySpecException ex)
		{
		}
		return null;
	}

	public String encryptData(String data)
	{
		PublicKey key = createPublicKey(PUB_KMTD);
		String token = encryptDataKMTD(key, data);
		return token;
	}

	private String encryptDataKMTD(PublicKey publicKey, String data)
	{
		try
		{
			if (publicKey == null)
			{
				return null;
			}
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] inputArray = data.getBytes(UTF_8);
			int inputLength = inputArray.length;
			int MAX_ENCRYPT_BLOCK = 245;
			int offSet = 0;
			byte[] resultBytes = {};
			byte[] cache = {};
			while (inputLength - offSet > 0)
			{
				if (inputLength - offSet > MAX_ENCRYPT_BLOCK)
				{
					cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
					offSet += MAX_ENCRYPT_BLOCK;
				}
				else
				{
					cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
					offSet = inputLength;
				}
				resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
				System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
			}
			return Base64.encodeBase64String(resultBytes);
		}
		catch (Exception ex)
		{

		}
		return "";
	}

	public static RSAPrivateKey createPrivateKey(String key)
	{
		byte[] keyBytes = Base64.decodeBase64(key);

		KeyFactory keyFactory = null;
		try
		{
			keyFactory = KeyFactory.getInstance(RSA);
		}
		catch (NoSuchAlgorithmException ex)
		{
		}
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try
		{
			return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		}
		catch (InvalidKeySpecException ex)
		{
		}
		return null;
	}

	public static String decryptData(PrivateKey privateKey, String data)
	{
		try
		{
			if (privateKey == null)
			{
				return null;
			}
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decoded = Base64.decodeBase64(data);
			byte[] cache = new byte[2048];
			byte[] resultBytes = {};
			int inputLength = decoded.length;
			int MAX_ENCRYPT_BLOCK = 256;
			int offSet = 0;
			if (inputLength > 256)
			{
				while (inputLength - offSet > 0)
				{
					if (inputLength - offSet > MAX_ENCRYPT_BLOCK)
					{
						cache = cipher.doFinal(Base64.decodeBase64(data.getBytes()),
								offSet, MAX_ENCRYPT_BLOCK);
						offSet += MAX_ENCRYPT_BLOCK;
					}
					else
					{
						cache = cipher.doFinal(Base64.decodeBase64(data.getBytes()),
								offSet, inputLength - offSet);
						offSet = inputLength;
					}
					resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
					System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
				}
			}
			else
			{
				resultBytes = cipher.doFinal(decoded);
			}
			Charset UTF8_CHARSET = Charset.forName(UTF_8);
			String re = new String(resultBytes, UTF8_CHARSET);
			return re;
		}
		catch (Exception ex)
		{
			LOG.info("Error" + ex.toString());

		}
		return "";
	}


	public JsonObject decryptToken(String token)
	{
		PrivateKey key = createPrivateKey(PRI_KMTD);
		String data = decryptData(key, token);
		JsonObject jsonObject = CommonUtil.createJsonObject(data);
		return jsonObject;
	}

	public String sign(String plainText)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException
	{
		PrivateKey privateKey = createPrivateKey(PRI_KMTD);
		Signature signature = Signature.getInstance(SHA256);
		signature.initSign(privateKey);
		signature.update(plainText.getBytes(UTF_8));
		byte[] bytes = signature.sign();
		return Base64.encodeBase64String(bytes);

	}

	public boolean verify(String plainText, String signature)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException
	{
		PublicKey publicKey = createPublicKey(PUB_KMTD);
		Signature pubSignature = Signature.getInstance(SHA256);
		pubSignature.initVerify(publicKey);
		pubSignature.update(plainText.getBytes(UTF_8));
		byte[] signatureBytes = Base64.decodeBase64(signature);
		return pubSignature.verify(signatureBytes);

	}
	public static void main(String[] args)
	{
		RSAUtil rsaUtil = new RSAUtil();
		String token = "";
		String signature = "";
		String CIF = "005136222";
		String Channel = "MB";
		String UserName = "0933581138";
		String CreateTime = "20220830150800";
		String dataSignature = CIF + Channel + UserName + CreateTime;
		JsonObject data = new JsonObject();
		JsonObject jsonData = new JsonObject();
		jsonData.addProperty("CIF", CIF);
		jsonData.addProperty("Channel", Channel);
		jsonData.addProperty("UserName", UserName);
		jsonData.addProperty("CreateTime", CreateTime);
		String str = "";
		boolean check = false;
		String strSignature = "";
		try
		{
			signature = rsaUtil.sign(dataSignature);
			jsonData.addProperty("signature", signature);
			str = jsonData.toString();
			System.out.println("data : " + str);
			token = rsaUtil.encryptData(str);
			data = rsaUtil.decryptToken(token);
			strSignature = data.get("signature").getAsString();
			check = rsaUtil.verify(dataSignature, strSignature);
		}
		catch (Exception e)
		{

		}
		System.out.println("token : " + token);
		System.out.println("data : " + data.toString());
		System.out.println("signature : " + signature);
		System.out.println("check : " + check);

	}
}
