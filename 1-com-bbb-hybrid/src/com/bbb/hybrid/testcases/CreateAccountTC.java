package com.bbb.hybrid.testcases;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.bbb.hybrid.basetest.BaseTest;
import com.bbb.hybrid.util.DataReader;
import com.bbb.hybrid.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class CreateAccountTC extends BaseTest{
	@Test
	public void CreateAccountTestCase(){
		report=ExtentManager.getInstance();
		test=report.startTest("CreateAccountTestCase");	
		
		testinfo("Started running testcase CreateAccountTestCase");
		
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
		testinfo("Entering new user email-id -"+data.get("Create Account Testcase").get("EmailId"));
		setText("createAccountEmail_xpath", data.get("Create Account Testcase").get("EmailId"));
		JSClick("createAccountButton_xpath");
		
		testinfo("Entering user details on create account form");
		setText("firstName_xpath", data.get("Create Account Testcase").get("FirstName"));
		setText("lastName_xpath", data.get("Create Account Testcase").get("LastName"));
		
		testpass("Account created successfully");
		takescreenshot("Account created successfully");
				
		report.endTest(test);		
	}
	
	@AfterMethod
	public void closebrowser(){
		report.flush();
		driver.quit();
	}

}
