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


public class convertleadtest extends BaseTest {
	Xls_Reader xls;
	String Testcasename="convertLeadTest";
	@Test(dataProvider="getData")
	public void convertLeadTest(Hashtable<String,String> data){
		test = rep.startTest("convertLeadTest");
		if(!DataUtil.isRunnable(xls,Testcasename) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "skipping the test as runmode is N");
			throw new SkipException("skipping the test as runmode is N");
		}
		openBrowser();
		navigate("appurl");
		doLogin(prop.getProperty("Username"),prop.getProperty("Password"));
		click("crm_link");
		click("lead_xpath");
		clickOnLead(data.get("LeadLastName"));
		click("covertlead_xpath");
	    click("convertleadandsave_xpath");
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
				if(rep!=null){
				rep.endTest(test);
				rep.flush();
				}
				if(driver!=null){
					driver.quit();
					
				}
	}
	
	@DataProvider
	public Object[][] getData(){
			   super.init();
				xls = new Xls_Reader(prop.getProperty("xlspath"));
				return DataUtil.getTestData(xls,Testcasename);
			}

}

