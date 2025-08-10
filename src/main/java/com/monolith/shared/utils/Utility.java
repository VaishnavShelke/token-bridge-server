package com.monolith.shared.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {
	
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	
	public static String generateRandomUUID() {
		return UUID.randomUUID().toString();
	}
	
	 public static <T> T parseJsonToObject(String input,Class<T> cls){
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T object = null;
			try {
			    object = mapper.readValue(input, cls);
			} catch (Exception e) {
				logger.error("String to Object Conversion Failed for class : {} {}" , cls,e.getMessage());
			}
			return object;
	 }
	 
	 public static boolean isNullOrEmpty(String str) {
		 if(str == null || str.equals("")) {
			 return true;
		 }
		 return false;
	 }
	 
	 public static String getJsonFromObject(Object obj) {
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Error {}",e.getMessage());
			return null;
		}
	 }
	 
	 
	 public static String UUIDToHexadecimal(String uuid) {
		 String hexValue = uuid.replace("-", "").toLowerCase();
		 hexValue = "0x" + hexValue;
		 return hexValue;
	 }

	 
	 public static String getTransactionTableName() {
		String currentDate = LocalDate.now().toString();
		StringBuilder sb = new StringBuilder();
		String tableName = sb.append("TXN_").append(currentDate.substring(0, 4)).append("_").append(currentDate.substring(5,7)).toString();
		return tableName;
	 }
	 
	 
	public static String generateDateTimeId() {
		SimpleDateFormat sdfTxnId = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateTimeId = sdfTxnId.format(new Date());
		return dateTimeId;
	}
	
    public static String compressURL(String originalURL) {
        byte[] bytes = originalURL.getBytes();
        String compressedURL = Base64.getEncoder().encodeToString(bytes);
        return compressedURL;
    }


	

}
