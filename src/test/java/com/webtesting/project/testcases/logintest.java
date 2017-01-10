package com.webtesting.project.testcases;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.webtesting.project.basetest.BaseTest;
import com.webtesting.project.utils.DataUtil;
import com.webtesting.project.utils.Xls_Reader;

public class logintest extends BaseTest {
	
	String Testcasename="LoginTest";
	Xls_Reader xls;
	@Test(dataProvider = "getData")
	public void doLoginTest(Hashtable<String,String> data){
		test = rep.startTest("logintest");
		test.log(LogStatus.INFO, "Starting the Login test");
		if(!DataUtil.isRunnable(xls, Testcasename) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "skipping the test as runmode is N");
			throw new SkipException("skipping the test as runmode is N");			
		}
		openBrowser();
		navigate("appurl");
		 doLogin(data.get("Username"),data.get("Password"));
		 boolean ExpectedResult = false;
		 boolean actualresult = true;
		 if(data.get("ExpectedResult").equals("Y"))
			 ExpectedResult = true;
		 else
			 ExpectedResult = false;
		 if(ExpectedResult!=actualresult)
			 reportFail("test failed");
		 else 
			 reportPass("test passed");
		 
	}
	
	@BeforeMethod
	public void init(){
		SoftAssert = new org.testng.asserts.SoftAssert();
	}
	
	@AfterMethod
	public void quit(){
				try{
					SoftAssert.assertAll();
				}catch(Error e){
					test.log(LogStatus.FAIL, e.getMessage());
				}
				
				rep.endTest(test);
				rep.flush();
				if(driver!=null){
					driver.quit();
				}
			}
	@DataProvider
	public Object[][] getData(){
			   super.init();
				xls = new Xls_Reader(prop.getProperty("xlspath"));
				return DataUtil.getTestData(xls, Testcasename);
			}
			
			
		}
		


