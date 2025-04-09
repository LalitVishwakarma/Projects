package com.radware.akkaAssignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AkkaAssignmentApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		SpringApplication.run(AkkaAssignmentApplication.class, args);

		GetData getData = new GetData();
		getData.getData();
	}



}
