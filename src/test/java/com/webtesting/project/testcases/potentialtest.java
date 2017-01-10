package com.webtesting.project.testcases;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.webtesting.project.basetest.BaseTest;
import com.webtesting.project.utils.DataUtil;
import com.webtesting.project.utils.Xls_Reader;
public class potentialtest extends BaseTest {
	String Testcasename="CreatePotentialTest";
	Xls_Reader xls;
	
	@Test(dataProvider="getData")
	public void createPotentialTest(Hashtable<String,String> data){
		test = rep.startTest("CreatePotentialTest");
		test.log(LogStatus.INFO, "Starting the createPotential test");
		if(!DataUtil.isRunnable(xls, Testcasename) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "skipping the test as runmode is N");
			throw new SkipException("skipping the test as runmode is N");
		}
			openBrowser();
			navigate("appurl");
			doLogin(prop.getProperty("Username"),prop.getProperty("Password"));
			click("crm_link");
			click("createPotential_xpath");
			click("CreateNewPotential_xpath");
			type("potential_name",data.get("PotentialName"));
			type("account_name",data.get("AccountName"));
			selectdate(data.get("ClosingDate"));
			selectstage();
			//driver.findElement(By.xpath("//*[@id='select2-Crm_Potentials_STAGE-container']")).click();
	    	//driver.findElement(By.xpath("//*[@id='select2-Crm_Potentials_STAGE-result-dxaj-40:Value Proposition']")).click();
            //click("savepotential_xpath");
			
		
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
				//if(driver!=null){
					//driver.quit();
					
				//}
	}
	
	@DataProvider
	public Object[][] getData(){
			   super.init();
				xls = new Xls_Reader(prop.getProperty("xlspath"));
				return DataUtil.getTestData(xls,Testcasename);
			}
	

}
