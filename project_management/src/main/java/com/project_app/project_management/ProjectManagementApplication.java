package com.project_app.project_management;

import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EntityScan(basePackages = "com.project_app.project_management")
public class ProjectManagementApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(ProjectManagementApplication.class, args);
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSha256");
		keyGen.init(256); // 256-bit key
		SecretKey secretKey = keyGen.generateKey();
		String base64Key = Encoders.BASE64.encode(secretKey.getEncoded());
		System.out.println("Your refresh secret key: " + base64Key);
	}

}
