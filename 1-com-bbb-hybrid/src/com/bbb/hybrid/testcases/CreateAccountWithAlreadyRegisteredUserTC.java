package com.bbb.hybrid.testcases;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.bbb.hybrid.basetest.BaseTest;
import com.bbb.hybrid.util.DataReader;
import com.bbb.hybrid.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CreateAccountWithAlreadyRegisteredUserTC extends BaseTest{
	@Test
	public void CreateAccountWithAlreadyRegisteredUserTestCase(){
		//start report
		report=ExtentManager.getInstance();
		test=report.startTest("Create Account With Already Registered User TestCase");
		test.log(LogStatus.INFO, "Started running Create Account With Already Registered User TestCase");
		
		//load test data in HashMap<String,HashMap<String,String>()
		data=DataReader.loadData();
		
		//load config
		loadConfig();
		
		//launch
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
		
		testinfo("Wait till signIn button is displayed");
		dynamicWait("signIn_xpath",30);
		JSClick("signIn_xpath");
		testinfo("Entering existing user id -"+data.get("Create Account Testcase").get("EmailId"));
		setText("createAccountEmail_xpath", data.get("Create Account Testcase").get("EmailId"));
		JSClick("createAccountButton_xpath");
		
		testinfo("Verify if existing user error message is displayed");
		if(isElementDisplayed("userExists_xpath")){
			testpass("User account already exists!!");
			takescreenshot("User account already exists!!");
		}else{
			testfail("User account already exists!!");
			takescreenshot("User account already exists!!");
		}
		
		report.endTest(test);
		
	}
	
	@AfterMethod
	public void closebrowser(){
		report.flush();
		driver.quit();
	}
	

}
