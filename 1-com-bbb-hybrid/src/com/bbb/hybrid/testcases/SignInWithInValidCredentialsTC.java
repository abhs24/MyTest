package com.bbb.hybrid.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.bbb.hybrid.basetest.BaseTest;
import com.bbb.hybrid.util.DataReader;
import com.bbb.hybrid.util.ExtentManager;

public class SignInWithInValidCredentialsTC extends BaseTest{
	@Test
	public void SignInWithInValidCrdentialsTestcase(){
		report=ExtentManager.getInstance();
		test=report.startTest("Sign In With InValid Credentials Testcase");	
		testinfo("Started running-Sign In With InValid Credentials Testcase");

		//load test data in HashMap<String,HashMap<String,String>()
		data=DataReader.loadData();

		//load config.properties 
		loadConfig();

		//date is read from config.properties at runtime
		launchBrowser(prop.getProperty("exeBrowser"));
		navigate(prop.getProperty("appURL"));

		/*
		 * selenium test flow
		 * 
		 */

		testinfo("Click on pop-up if exists");
		if(isElementDisplayed("closeButton_xpath")){
			click("closeButton_xpath");
		};
		testinfo("Click on signIn button");
		dynamicWait("signIn_xpath",30);
		JSClick("signIn_xpath");
		testinfo("Entering existing user email-id & pwd-"+data.get("Sign In With InValid Credentials Testcase").get("EmailId"));

		setText("signInEmail_id", data.get("Sign In With InValid Credentials Testcase").get("EmailId"));
		setText("signInPWD_id", data.get("Sign In With InValid Credentials Testcase").get("PWD"));
		JSClick("signInButton_xpath");
		
		if(isElementDisplayed("invalidUserPwdMessage_xpath")){
			testpass("User with invalid credentials could not login, error message shown to end user.");	
		}else{
			testfail("User with invalid credentials could login, validation failed!!");
		}
		takescreenshot("Account created successfully");				
		report.endTest(test);		
	}

	@AfterMethod
	public void closebrowser(){
		report.flush();
		driver.quit();
	}

}
