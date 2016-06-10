package com.stl.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class HashingAlgorithm {

	public static void main(String[] args) {
		System.out.println(hash("ranvir","md5"));
		System.out.println(hash("ranvir","sha512"));
	}
	public static String hash(String inputString,String method){
		String hashedString = "" ;
		if(method.equalsIgnoreCase("md5")){
			hashedString = md5Hash(inputString) ;
		}else if(method.equalsIgnoreCase("sha512")){
			hashedString = sha512Hash(inputString) ;
		}/*else if(method.equalsIgnoreCase("sha1")){
			
		}*/
		return hashedString ;
		
	}
	private static String sha512Hash(String inputString) {
		String hashedString = "" ;
		hashedString = DigestUtils.sha512Hex(inputString);
	return hashedString ;
	}//end of sha512Hash
	private static String md5Hash(String inputString) {
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(inputString.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
		
	}//end of md5Hash
}
