package com.bbb.hybrid.util;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class DataReader {
	private static String Path=System.getProperty("user.dir")+"\\TestData\\BBB_TestData.xlsx";
	private static String DataSheet="Sheet1";
	private static FileInputStream fis=null;
	private static XSSFWorkbook wb=null;
	private static XSSFSheet dataSheet=null;	
	private static HashMap<String,HashMap<String,String>> dataMap=null;

	public static void main(String[] args) {
		loadData();
		System.out.println(dataMap.get("Create Account Testcase").get("FirstName"));


	}

	public static HashMap<String,HashMap<String,String>> loadData(){
		XSSFRow row;
		XSSFCell cell;

		try {

			if(dataMap==null){ //load data once
				fis=new FileInputStream(Path);
				wb=new XSSFWorkbook(fis);
				dataSheet=wb.getSheet(DataSheet);

				//Testcase reader
				dataMap=new HashMap<String,HashMap<String,String>>();
				for(int i=0;i<dataSheet.getLastRowNum();i+=3){
					row=dataSheet.getRow(i);
					cell=row.getCell(0);
					String TCName=cell.getStringCellValue();

					//read - tc cols vs data and populate hashmap
					XSSFRow headerRow=dataSheet.getRow(i+1);
					XSSFRow dataRow=dataSheet.getRow(i+2);
					HashMap<String,String> tcDataMap=new HashMap<String,String>();
					for(int j=0;j<headerRow.getLastCellNum();j++){
						String col=getdata(headerRow.getCell(j));
						String value=getdata(dataRow.getCell(j));
						tcDataMap.put(col, value);
					}	
					dataMap.put(TCName, tcDataMap);
				}	

				return dataMap;
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return dataMap;
		}
		return dataMap;
	}


	public static String getdata(XSSFCell c){
		String cellValue;
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue=c.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			cellValue=String.valueOf(c.getNumericCellValue());
			break;
		default:
			cellValue="";
			break;
		}
		return cellValue;
	}
}
