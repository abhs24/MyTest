package com.bbb.hybrid.basetest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;



public class BaseTest {
	public WebDriver driver=null;
	public ExtentReports report=null;
	public ExtentTest test=null;
	public FileInputStream fis=null;
	public Properties prop=null;
	public HashMap<String,HashMap<String,String>> data=null;

	//load config.properties
	public void loadConfig(){
		try {
			if(prop==null){  //singleton design pattern. load it once and use it everywhere
				fis=new FileInputStream("config.properties");
				prop=new Properties();
				prop.load(fis);
			}

		} catch (Exception e) {   //error handling - stop execution incase the properties files is not loaded			
			e.printStackTrace();
			testfail(e.getMessage());
			Assert.fail();
		}

	}




	public void launchBrowser(String browserType){
		try{
			if(driver==null){   //singleton design pattern. same driver instance would be used in testcase.class
				if(browserType.equals("Chrome")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
					driver=new ChromeDriver();
				}else if(browserType.equals("FF")){
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
					driver=new FirefoxDriver();
				}else if(browserType.equals("IE")){
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
					driver=new ChromeDriver();
				}else{
					testfail("Invalid "+browserType+"browser specified!!");
					Assert.fail();  //this is hard assert, testng will halt ur execution.
				}

				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			}
		}catch(Exception e){
			e.printStackTrace();  //to print msg on console
			testfail(e.getMessage());
			Assert.fail();  //this is hard assert, testng will halt ur execution.
		}
	}

	public void navigate(String appURL){
		driver.navigate().to(appURL);
	}

	/*
	 * reusable action keywords*	
	 */

	public WebElement getElement(String locatorKey){
		try{
			WebElement elm=null;
			if(locatorKey.endsWith("xpath")){
				elm= driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("id")){
				elm= driver.findElement(By.id(prop.getProperty(locatorKey)));
			}else if(locatorKey.endsWith("name")){
				elm= driver.findElement(By.name(prop.getProperty(locatorKey)));
			}else{
				System.out.println("invalid locator specified!!");
				testfail("invalid locator-"+locatorKey+" specified!!");
				Assert.fail();			
			}
			return elm;
		}catch(Exception e){			
			testfail("no such element: Unable to locate element-"+locatorKey);
			Assert.fail();
			return null;
		}
	}


	public void dynamicWait(String locatorKey,int timeout){
		try{
			WebDriverWait wait=new WebDriverWait(driver, timeout);
			WebElement elm=getElement(locatorKey);
			wait.until(ExpectedConditions.visibilityOf(elm));
		}catch(Exception e){
			testfail("timeout exception-"+locatorKey+" specified!!");
			Assert.fail();
		}
	}

	public void click(String locatorKey){
		WebElement elm=null;
		elm=getElement(locatorKey);
		elm.click();
	}

	public void JSClick(String locatorKey){
		WebElement elm=null;
		elm=getElement(locatorKey);
		JavascriptExecutor jse = (JavascriptExecutor)driver;		
		jse.executeScript("arguments[0].click()", elm); 
	}

	public void setText(String locatorKey,String inputText){
		WebElement elm=null;
		elm=getElement(locatorKey);
		elm.sendKeys(inputText);
	}

	public String  getAttribute(String locatorKey,String attributeName){
		WebElement elm=null;
		elm=getElement(locatorKey);
		return elm.getAttribute("attributeName");
	}

	public String getText(String locatorKey){
		WebElement elm=null;
		elm=getElement(locatorKey);
		return elm.getText();
	}

	public boolean isElementDisplayed(String locatorKey){	
		try{
			boolean flag;
			if(locatorKey.endsWith("xpath")){
				driver.findElement(By.xpath(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else if(locatorKey.endsWith("id")){
				driver.findElement(By.id(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else if(locatorKey.endsWith("name")){
				driver.findElement(By.name(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else{
				flag=false;
			}
			return flag;
		}catch(Exception e){
			return false;
		}
	}


	public void selectMenuItem(){

	}
	public void verityText(){

	}
	public void selectWindow(){

	}
	public void selectFrame(){

	}

	/* reporting keywords*/	
	public void testpass(String message){
		test.log(LogStatus.PASS, message);
	}

	public void testfail(String message){
		test.log(LogStatus.FAIL, message);
		takescreenshot(message);
	}

	public void testinfo(String message){
		test.log(LogStatus.INFO, message);
	}

	public void takescreenshot(String msg){
		try {
			Date d=new Date();
			String TimeStamp=d.toString().replace(" ", "_").replace(":", "_");
			TakesScreenshot screenshot=(TakesScreenshot)driver;
			File f=screenshot.getScreenshotAs(OutputType.FILE);
			String screenshotPath=System.getProperty("user.dir")+"\\screenshots\\screenshot_"+TimeStamp+".png";
			FileUtils.copyFile(f, new File(screenshotPath));

			test.log(LogStatus.INFO,test.addScreenCapture(screenshotPath)+msg);

		} catch (IOException e) {			
			e.printStackTrace();
		}

	}











}
