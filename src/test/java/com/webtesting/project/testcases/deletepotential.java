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

public class deletepotential extends BaseTest {
	Xls_Reader xls;
	String Testcasename="DeletePotentialTest";
	@Test(dataProvider="getData")
	public void deletePotential(Hashtable<String,String> data){
		test = rep.startTest("DeletePotentialTest");
		if(!DataUtil.isRunnable(xls, Testcasename) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "skipping the test as runmode is N");
			throw new SkipException("skipping the test as runmode is N");
		}
		openBrowser();
		navigate("appurl");
		doLogin(prop.getProperty("Username"),prop.getProperty("Password"));
		click("crm_link");
		click("account_xpath");
		clickOnAccount(data.get("AccountName"));
		int rNum = getLeadRowNum1(data.get("AccountName"));
		if(rNum==-1){
	     reportFail("could not delete the account");
		}
		click("temp_xpath");
		click("deletelead_xpath");
		click("delete_xpath");
			reportPass("potential account");
			takeScreenShot();

	}
		//acceptAlert();
		 
	
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


