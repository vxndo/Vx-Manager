package vxndo.manager.util;

import android.os.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;

import java.security.cert.Certificate;

public class ApkSigner {

	public static void method() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		FileInputStream keyFileStream = new FileInputStream("my-release-key.keystore");
		keyStore.load(keyFileStream, "senha_da_chave".toCharArray());
		KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
			"alias_name", new KeyStore.PasswordProtection("senha_da_chave".toCharArray()));
		PrivateKey privateKey = keyEntry.getPrivateKey();
		Certificate[] certificates = keyStore.getCertificateChain("alias_name");
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		FileInputStream apkFileStream = new FileInputStream("my-app.apk");
		BufferedInputStream apkInputStream = new BufferedInputStream(apkFileStream);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = apkInputStream.read(buffer)) != -1) {
			signature.update(buffer, 0, read);
		}
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		CertPath certPath = certificateFactory.generateCertPath(Arrays.asList(certificates));
		/*PKCS7 pkcs7 = new PKCS7(new byte[0], certPath, "SHA256withRSA", null, signature);
		FileOutputStream signedApkStream = new FileOutputStream("my-signed-app.apk");
		signedApkStream.write(pkcs7.getEncoded());
		signedApkStream.close();*/
	}
}
