package com.bbb.hybrid.practice;

import java.util.HashMap;

public class Hashtable_1 {

	public static void main(String[] args) {
		/*	
		Create Account Testcase		--key
			EmailId	FirstName	LastName
			seleniumtrainer2012@gmail.com	Virat	Mehta
		 */

		/*Create Account With Already Registered User -key
			EmailId
			seleniumtrainer2012@gmail.com
		 */
		
		HashMap<String,HashMap<String,String>> testData=new HashMap<String,HashMap<String,String>>();
		
		//child hashmap having testcase data
		HashMap<String,String> tc1=new HashMap<String,String>();
		tc1.put("EmailId", "seleniumtrainer2012@gmail.com");
		tc1.put("FirstName", "Virat");
		tc1.put("LastName", "Mehta");
		
		//child hashmap having testcase data
		HashMap<String,String> tc2=new HashMap<String,String>();
		tc2.put("EmailId", "seleniumtrainer2012@gmail.com");
		
		
		//popuate parent hashmap with all child tc's hashmamp
		testData.put("Create Account Testcase", tc1);
		testData.put("Create Account With Already Registered User", tc2);
		
		System.out.println(testData.get("Create Account Testcase").get("LastName"));
		
		
		
		





	}




}
