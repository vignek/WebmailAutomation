package com.webtesting.project.testcases;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.webtesting.project.basetest.BaseTest;
import com.webtesting.project.utils.DataUtil;
import com.webtesting.project.utils.Xls_Reader;

public class deleteleadtest extends BaseTest {
	Xls_Reader xls;
	String Testcasename="DeleteLeadAccountTest";
	@Test(dataProvider="getData")
	public void deleteLeadTestAccount(Hashtable<String,String> data){
		test = rep.startTest("DeleteLeadAccountTest");
		if(!DataUtil.isRunnable(xls, Testcasename) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "skipping the test as runmode is N");
			throw new SkipException("skipping the test as runmode is N");
		}
		openBrowser();
		navigate("appurl");
		doLogin(prop.getProperty("Username"),prop.getProperty("Password"));
		click("crm_link");
		click("lead_xpath");
		clickOnLead(data.get("LeadLastName"));
		int rNum = getLeadRowNum(data.get("LeadLastName"));
		if(rNum==-1){
			reportFail("could not delete the lead");		
		}
		click("temp_xpath");
		click("deletelead_xpath");
		click("delete_xpath");
		//acceptAlert();		
		reportPass("lead deleted");
		takeScreenShot();
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

