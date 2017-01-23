package com.selenium.gmail.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.selenium.gmail.base.BaseTest;

public class LogoutTests extends BaseTest{

	@Test
	public void LogoutTest() throws Exception {
		init();
		test =rep.startTest("LoginTest");
		test.log(LogStatus.INFO, "Starting the Login Test");
		logout();
		test.log(LogStatus.PASS, "Success");
		takeScreenShot();
		
	}
	
	@AfterClass
	public void exit() throws Exception{
		rep.endTest(test);
		rep.flush();
		driver.close();
	}
	
	
}
