package com.selenium.gmail.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.selenium.gmail.base.BaseTest;
import com.selenium.gmail.utilities.dataUtil;

public class SearchMailTest extends BaseTest {

	@BeforeClass
	public void beforeClass() throws Exception {
		init();
		test =rep.startTest("SearchMailTest");
		test.log(LogStatus.INFO, "Starting the Search Mail Test");
		dataUtil.setExcelFile(prop.getProperty("path"),"Searchname");
	}
	@DataProvider(name = "Searchname")
	public Object[][] dataProvider() {
		Object[][] testData = dataUtil.getTestData("Invalid_Login");
		return testData;
	}

	@Test(dataProvider="Searchname")
	public void searchButtonTest(String Searchname,String a) throws Exception {
		openBrowser();
		navigate("gmail_url");
		doLogin();
		type("searchname",Searchname);
		click("btn_search");

		test.log(LogStatus.PASS, "Success");
		takeScreenShot();
	}

	@AfterClass
	public void afterClass() {
		rep.endTest(test); 
		rep.flush();
		driver.quit();
	}
}