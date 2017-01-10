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

public class leadtest extends BaseTest {
	Xls_Reader xls;
	String Testcasename="CreateLeadTest";
	org.testng.asserts.SoftAssert SoftAssert;
	@Test(dataProvider = "getData")
	public void createLeadTest(Hashtable<String,String> data) throws InterruptedException {
	test = rep.startTest("CreateLeadTest");
	test.log(LogStatus.INFO, "Starting the Login test");
	if(!DataUtil.isRunnable(xls,Testcasename) || data.get("Runmode").equals("N")){
		test.log(LogStatus.SKIP, "skipping the test as runmode is N");
		throw new SkipException("skipping the test as runmode is N");
	}
	openBrowser();
	navigate("appurl");
	doLogin(prop.getProperty("Username"),prop.getProperty("Password"));
	click("crm_link");
	click("lead_xpath");
	click("createlead_xpath");
	type("Leadcompany_xpath",data.get("LeadCompany"));
	type("leadlastname_xpath",data.get("LeadLastName"));
	click("leadsavebutton_xpath");
	clickandWait("lead_xpath","createlead_xpath");
	int rNum = getLeadRowNum(data.get("LeadLastName"));
	//validate
	if(rNum ==-1){
		reportFail("lead not found " + data.get("LeadLastName"));
	}
		else
		{
		reportPass("lead found " + data.get("LeadLastName"));
		takeScreenShot();
		test.log(LogStatus.INFO, "test passed");
		}
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