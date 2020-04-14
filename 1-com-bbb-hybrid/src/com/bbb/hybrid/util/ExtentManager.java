package com.bbb.hybrid.util;

import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports report=null;

	public static ExtentReports getInstance(){
		if(report==null){
			Date d=new Date();
			String TimeStamp=d.toString().replace(" ", "_").replace(":", "_");
			report=new ExtentReports(System.getProperty("user.dir")+"\\reports\\r_"+TimeStamp+".html");	
		}
		return report;
	}




}




