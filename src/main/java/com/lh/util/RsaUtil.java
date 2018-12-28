package com.lh.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 
 * @author lixing
 *
 */
public class RsaUtil {

	private static String ALGORITHM = "RSA";

	//private static String primaryKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQj1yAOuyaXpY/useG6HIoZWmJy0HLsetf/ntUSFtZCSPmiVc0RCF48s/o/7kW5gNPHQpUzj90OIKOV4AZqSsnJrfQjLUp1G9XA80ITqgtYVw7WTE/2p/ZOSM6hUKs3ke/5LPIxHKp8pZCRWaBcHKYK7RQfoUqWVWRRGdW/5YHVK+1Rx9YcsAz124CYHra2B19SRDiq743D1CzkMMnZa3zQuD1njn9Xpj+xm5xg33Oi8fx7qwTw9CsWZClVYhnj0RxvGRm6KLADIMzRHYyPjOxuXQkB3vxKYVOQs5pWWDf4SHURfcfQSPZ2oDSdNmw6xnEYt9RhsQkLPXNUD4H8yRFAgMBAAECggEAalWNrftdAt/S7y9F56y/94StKIovQ7G09j6DltdFa8HTQgkoIwfRoR77jipDrj8b1MxTpiV6CMF/Hx/RukOcMtv0wCndfDRJiC3x7Zhc+rY3FjNFfu7yrQgKxB2JfyXdoyLx3xz58Vgz8Bn+R2STqK4vwxKexpbBn80mex7Y2WcRz6lPmgALD9qExhOwCin+6wRiKsFBAV1dHRd4m0G+cPExgB00/HT7KAkiFvn8hj71q3+fE8bsRpZxGqCCdqtyCmo0pQ6sWOyN4gpVVtom6EkQzZ46L+8K8e2EHP44R20gcbZ8x7OdmkoHnkUHDU9XokwS5lzVf3biNEbygGucwQKBgQD52WMQKQ9dWTdj5SsPInCRobgiVU1AdFxiHMnBeWEG6J6SBdhuXXLqj02u34Qg5FA9L8lxweQjFAfzE6qPYzq8N3LM+C5sbIreQV81RYnXn7IVgcDY74OjuedNmSbj64AP0oc4/bdmzB9qbKAgtBrR3OTYfDiFzJvh6yP1ItbFWQKBgQDVscJyTIzrHxtC+Q7xrKbKgV2PcxBoUaydgw1jFPxJ246rQzNUnTlbMYyuttk6YBXJeLkyYxytoR+95GY13tnd4kVO7hyujYrwa8mlfsQ4gKkUU+wEvBpnvVPPjcj+0a1lEOCCuPyauySh6SDKcBHPTOLjZz4/WeCm8afXwWB8zQKBgQDmbdrSfbwA8RaH2EUFOVMuzUMAz0FnT6oghsKA4Mxezc3piOCasS8aK76OqPC4UTm7pMYaV6Nrwr6uLhYhFniGPHFxnvdZ4iWVZB9GB3Ng3ZUmBrxwlz4Gk59DTxuyG3HJpfY21rD+awYSZkqsvs75fMvG6ZJLvHCMRQytukrwSQKBgDIcJQS4frMD8FicHqz9V39VJqNk0AwmWtv0x0bcvOx019fBQmAtHi4MSHkP15Cgx3sfHA58v1TX26Gj1XP8dgpZ7Pa9T2x/NH18SSTNvSWixgNGSqoxkd+Rvf2FugRHFuoEMyF0T4fZKpXtHQtbjFGYLfw1UZt5b/y13i3x4rZhAoGBAPEK1XgxQltNJLAt61Ppx5b70+/HDw0WBM1gjQOi82cnGoeAdhnJ3XP8pdm/cSOkr2SGdV70hTHyX1yBKtaBKvG1rJwxry0EeRpT1CHbDaFqUI6uyxqIKYgJ3XSzl8Cw6GNwZDQNz2L9QmTl4ze9mbfIBel5I8nOgbNCb2wNtyh+";
	
	/*private static String primaryKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQj1yAOuyaXpY/"+
			"useG6HIoZWmJy0HLsetf/ntUSFtZCSPmiVc0RCF48s/o/7kW5gNPHQpUzj90OIKO"+
			"V4AZqSsnJrfQjLUp1G9XA80ITqgtYVw7WTE/2p/ZOSM6hUKs3ke/5LPIxHKp8pZC"+
			"RWaBcHKYK7RQfoUqWVWRRGdW/5YHVK+1Rx9YcsAz124CYHra2B19SRDiq743D1Cz"+
			"kMMnZa3zQuD1njn9Xpj+xm5xg33Oi8fx7qwTw9CsWZClVYhnj0RxvGRm6KLADIMz"+
			"RHYyPjOxuXQkB3vxKYVOQs5pWWDf4SHURfcfQSPZ2oDSdNmw6xnEYt9RhsQkLPXN"+
			"UD4H8yRFAgMBAAECggEAalWNrftdAt/S7y9F56y/94StKIovQ7G09j6DltdFa8HT"+
			"QgkoIwfRoR77jipDrj8b1MxTpiV6CMF/Hx/RukOcMtv0wCndfDRJiC3x7Zhc+rY3"+
			"FjNFfu7yrQgKxB2JfyXdoyLx3xz58Vgz8Bn+R2STqK4vwxKexpbBn80mex7Y2WcR"+
			"z6lPmgALD9qExhOwCin+6wRiKsFBAV1dHRd4m0G+cPExgB00/HT7KAkiFvn8hj71"+
			"q3+fE8bsRpZxGqCCdqtyCmo0pQ6sWOyN4gpVVtom6EkQzZ46L+8K8e2EHP44R20g"+
			"cbZ8x7OdmkoHnkUHDU9XokwS5lzVf3biNEbygGucwQKBgQD52WMQKQ9dWTdj5SsP"+
			"InCRobgiVU1AdFxiHMnBeWEG6J6SBdhuXXLqj02u34Qg5FA9L8lxweQjFAfzE6qP"+
			"Yzq8N3LM+C5sbIreQV81RYnXn7IVgcDY74OjuedNmSbj64AP0oc4/bdmzB9qbKAg"+
			"tBrR3OTYfDiFzJvh6yP1ItbFWQKBgQDVscJyTIzrHxtC+Q7xrKbKgV2PcxBoUayd"+
			"gw1jFPxJ246rQzNUnTlbMYyuttk6YBXJeLkyYxytoR+95GY13tnd4kVO7hyujYrw"+
			"a8mlfsQ4gKkUU+wEvBpnvVPPjcj+0a1lEOCCuPyauySh6SDKcBHPTOLjZz4/WeCm"+
			"8afXwWB8zQKBgQDmbdrSfbwA8RaH2EUFOVMuzUMAz0FnT6oghsKA4Mxezc3piOCa"+
			"sS8aK76OqPC4UTm7pMYaV6Nrwr6uLhYhFniGPHFxnvdZ4iWVZB9GB3Ng3ZUmBrxw"+
			"lz4Gk59DTxuyG3HJpfY21rD+awYSZkqsvs75fMvG6ZJLvHCMRQytukrwSQKBgDIc"+
			"JQS4frMD8FicHqz9V39VJqNk0AwmWtv0x0bcvOx019fBQmAtHi4MSHkP15Cgx3sf"+
			"HA58v1TX26Gj1XP8dgpZ7Pa9T2x/NH18SSTNvSWixgNGSqoxkd+Rvf2FugRHFuoE"+
			"MyF0T4fZKpXtHQtbjFGYLfw1UZt5b/y13i3x4rZhAoGBAPEK1XgxQltNJLAt61Pp"+
			"x5b70+/HDw0WBM1gjQOi82cnGoeAdhnJ3XP8pdm/cSOkr2SGdV70hTHyX1yBKtaB"+
			"KvG1rJwxry0EeRpT1CHbDaFqUI6uyxqIKYgJ3XSzl8Cw6GNwZDQNz2L9QmTl4ze9"+
			"mbfIBel5I8nOgbNCb2wNtyh+";*/

	//private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkEue1AMs5xVcqlnL7TPmxenn+xxZwcsHRTDU5HBBQJ6QvhZTZ2n1+ZbHiOqaIrRUrgg5bqMfuSM7sylKwNop9YR0c1fdow+jPYM/AhS/6kpPFGKIT+FiXZrXf6WT9sz/QJxXNTk2QXP2YDkzP6V5wSz+UAGmxHGwH3642KTsCOARaVnTZoRdvq+/Jnq7Q0IFQbMArxpNc/yBa9iJ34iRDp52b/BjztjYS0f5pJWyGC9duXKzevXkdCVl4decn3M94b6uBmIdqM2MLn6yeWDyknDf9SBRqZum0Ca/qqqW89Foukc8MP+HcMd79vf38vck9wYKlAl9V866RPvPEWxKbwIDAQAB";
	
	private static String primaryKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQj1yAOuyaXpY/useG6HIoZWmJy0HLsetf/ntUSFtZCSPmiVc0RCF48s/o/7kW5gNPHQpUzj90OIKOV4AZqSsnJrfQjLUp1G9XA80ITqgtYVw7WTE/2p/ZOSM6hUKs3ke/5LPIxHKp8pZCRWaBcHKYK7RQfoUqWVWRRGdW/5YHVK+1Rx9YcsAz124CYHra2B19SRDiq743D1CzkMMnZa3zQuD1njn9Xpj+xm5xg33Oi8fx7qwTw9CsWZClVYhnj0RxvGRm6KLADIMzRHYyPjOxuXQkB3vxKYVOQs5pWWDf4SHURfcfQSPZ2oDSdNmw6xnEYt9RhsQkLPXNUD4H8yRFAgMBAAECggEAalWNrftdAt/S7y9F56y/94StKIovQ7G09j6DltdFa8HTQgkoIwfRoR77jipDrj8b1MxTpiV6CMF/Hx/RukOcMtv0wCndfDRJiC3x7Zhc+rY3FjNFfu7yrQgKxB2JfyXdoyLx3xz58Vgz8Bn+R2STqK4vwxKexpbBn80mex7Y2WcRz6lPmgALD9qExhOwCin+6wRiKsFBAV1dHRd4m0G+cPExgB00/HT7KAkiFvn8hj71q3+fE8bsRpZxGqCCdqtyCmo0pQ6sWOyN4gpVVtom6EkQzZ46L+8K8e2EHP44R20gcbZ8x7OdmkoHnkUHDU9XokwS5lzVf3biNEbygGucwQKBgQD52WMQKQ9dWTdj5SsPInCRobgiVU1AdFxiHMnBeWEG6J6SBdhuXXLqj02u34Qg5FA9L8lxweQjFAfzE6qPYzq8N3LM+C5sbIreQV81RYnXn7IVgcDY74OjuedNmSbj64AP0oc4/bdmzB9qbKAgtBrR3OTYfDiFzJvh6yP1ItbFWQKBgQDVscJyTIzrHxtC+Q7xrKbKgV2PcxBoUaydgw1jFPxJ246rQzNUnTlbMYyuttk6YBXJeLkyYxytoR+95GY13tnd4kVO7hyujYrwa8mlfsQ4gKkUU+wEvBpnvVPPjcj+0a1lEOCCuPyauySh6SDKcBHPTOLjZz4/WeCm8afXwWB8zQKBgQDmbdrSfbwA8RaH2EUFOVMuzUMAz0FnT6oghsKA4Mxezc3piOCasS8aK76OqPC4UTm7pMYaV6Nrwr6uLhYhFniGPHFxnvdZ4iWVZB9GB3Ng3ZUmBrxwlz4Gk59DTxuyG3HJpfY21rD+awYSZkqsvs75fMvG6ZJLvHCMRQytukrwSQKBgDIcJQS4frMD8FicHqz9V39VJqNk0AwmWtv0x0bcvOx019fBQmAtHi4MSHkP15Cgx3sfHA58v1TX26Gj1XP8dgpZ7Pa9T2x/NH18SSTNvSWixgNGSqoxkd+Rvf2FugRHFuoEMyF0T4fZKpXtHQtbjFGYLfw1UZt5b/y13i3x4rZhAoGBAPEK1XgxQltNJLAt61Ppx5b70+/HDw0WBM1gjQOi82cnGoeAdhnJ3XP8pdm/cSOkr2SGdV70hTHyX1yBKtaBKvG1rJwxry0EeRpT1CHbDaFqUI6uyxqIKYgJ3XSzl8Cw6GNwZDQNz2L9QmTl4ze9mbfIBel5I8nOgbNCb2wNtyh+";
	
	private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0I9cgDrsml6WP7rHhuhyKGVpictBy7HrX/57VEhbWQkj5olXNEQhePLP6P+5FuYDTx0KVM4/dDiCjleAGakrJya30Iy1KdRvVwPNCE6oLWFcO1kxP9qf2TkjOoVCrN5Hv+SzyMRyqfKWQkVmgXBymCu0UH6FKllVkURnVv+WB1SvtUcfWHLAM9duAmB62tgdfUkQ4qu+Nw9Qs5DDJ2Wt80Lg9Z45/V6Y/sZucYN9zovH8e6sE8PQrFmQpVWIZ49EcbxkZuiiwAyDM0R2Mj4zsbl0JAd78SmFTkLOaVlg3+Eh1EX3H0Ej2dqA0nTZsOsZxGLfUYbEJCz1zVA+B/MkRQIDAQAB";
	
	private static PrivateKey privateKey;
	private static PublicKey publicKey;

	static {
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(primaryKeyStr));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
			keyFactory = KeyFactory.getInstance(ALGORITHM);
			publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String pubkeyEncrypt(String s) throws Exception {
		try {
			Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] result = encryptCipher.doFinal(s.getBytes());
			return new String(Base64.getEncoder().encode(result));
		} catch (Exception e) {
			throw new RuntimeException("rsa加密异常:" + s, e);
		}
	}

	public static String prikeyDecrypt(String s) throws Exception {
		try {
			Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] result = decryptCipher.doFinal(Base64.getDecoder().decode(s));
			return new String(result);
		} catch (Exception e) {
			throw new RuntimeException("rsa解密异常:" + s, e);
		}
	}

	public static void createKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		byte[] publicKeyBytes = rsaPublicKey.getEncoded();
		System.out.println("pubkey:" + new String(Base64.getEncoder().encode(publicKeyBytes)));
		byte[] privateKeyBytes = rsaPrivateKey.getEncoded();
		System.out.println("priKey" + new String(Base64.getEncoder().encode(privateKeyBytes)));
	}

	public static void main(String[] args) throws Exception {
		createKey();
		test("123456");
	}

	public static void test(String pass) throws Exception {
		System.out.println("加密前:" + pass);
		String encryptResult = pubkeyEncrypt(pass);
		String decryptResult = prikeyDecrypt(encryptResult);
		System.out.println("加密后结果:" + encryptResult);
		System.out.println("解密后结果" + decryptResult);
	}
}

