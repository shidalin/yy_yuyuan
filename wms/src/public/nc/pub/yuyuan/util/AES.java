package nc.pub.yuyuan.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	
	/**
	 * 解密方法
	 * @param data
	 * @return
	 */
	public static final String decode(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()),
					"abcdefgabcdefg12"), "UTF8");
		} catch (Exception arg1) {
			arg1.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密方法
	 * @param data
	 * @return
	 */
	public static final String encode(String data) {
		try {
			return byte2hex(encrypt(data.getBytes("UTF8"),
					"abcdefgabcdefg12"));
		} catch (Exception arg1) {
			arg1.printStackTrace();
			return null;
		}
	}

	private static byte[] encrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(1, securekey);
		return cipher.doFinal(src);
	}

	private static byte[] decrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(2, securekey);
		return cipher.doFinal(src);
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 255);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("");
		} else {
			byte[] b2 = new byte[b.length / 2];

			for (int n = 0; n < b.length; n += 2) {
				String item = new String(b, n, 2);
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}

			return b2;
		}
	}
}