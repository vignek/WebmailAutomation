package com.webtesting.project.basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.webtesting.project.utils.ExtentManager;


public class BaseTest {
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public WebDriver driver;
	public Properties prop;
	public org.testng.asserts.SoftAssert SoftAssert = new org.testng.asserts.SoftAssert();

	//initialize the properties.
	public void init(){
		if(prop ==null)
		{
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				try {
					prop.load(fs);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}
	}
	//code to open the browser.
	public void openBrowser() {
		test.log(LogStatus.INFO, "open broswer");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "browser name : mozilla");

	}

	//code to navigate to the zoho.com
	public void navigate(String urlkey){
		test.log(LogStatus.INFO, "Navigating to " + prop.getProperty(urlkey));
		driver.get(prop.getProperty(urlkey));
		test.log(LogStatus.INFO, "site got opened");
	}

	public void click(String xpathEleKey){
		test.log(LogStatus.INFO, "click " + xpathEleKey );
		driver.findElement(By.xpath(prop.getProperty(xpathEleKey))).click();
	}


	public void type(String xpathElekey ,String data){
		//test.log(LogStatus.INFO, "type " + prop.getProperty(data));
		driver.findElement(By.xpath((prop.getProperty(xpathElekey)))).sendKeys(data);;
	}

	public void clickandWait(String locator_clicked,String locator_present) throws InterruptedException{
		test.log(LogStatus.INFO, "clicking and waiting on " + locator_clicked );
		int count =5;
		for(int i= 0;i<count;i++){
			driver.findElement(By.xpath(prop.getProperty(locator_clicked))).click();
			Thread.sleep(2);
			if(isElementPresent(locator_present))
				break;
		}



	}
	public String getText(String locatorkey){
		test.log(LogStatus.INFO, "getting text from" + locatorkey);
		return driver.findElement(By.xpath(prop.getProperty(locatorkey))).getText();
	}

	/************ Validation functions *************/

	public boolean verifyTitle(){
		return false;
	}
	public boolean isElementPresent(String keylocator){
		List<WebElement>elementlist =null;
		if(keylocator.endsWith("_xpath")){
			elementlist = driver.findElements(By.xpath(keylocator));
		}

		if(elementlist.size()==0){
			return false;		
		}
		else 
			return true;
	}

	public boolean verifyText(String locatorkey,String expectedTextkey){
		String actualtext=driver.findElement(By.xpath(prop.getProperty(locatorkey))).getText().trim();
		String expectedtext = prop.getProperty(expectedTextkey);
		if(actualtext.equals(expectedtext))
			return true;
		else
			return false;


	}
	public String getElement(String locatorkey) {
		// TODO Auto-generated method stub
		return null;
	}

	/************ Reporting functions *************/
	public void reportPass(String msg){

		test.log(LogStatus.PASS, msg);
	}
	public void reportFail(String msg){
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();

	}
	//takes screenshot .
	public void takeScreenShot(){
		Date d = new Date();
		String filename = d.toString().replace(":","_").replace(" ","_")+".png";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try
		{
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+filename));
		}
		catch(Exception e)
		{

		}

		test.log(LogStatus.INFO,"Screenshot->"+test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+filename));
	}

	public void acceptAlert(){
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}




	/******************************App Functions**************************************/
	//login method
	public boolean doLogin(String username,String password){
		click("loginlink_xpath");
		test.log(LogStatus.INFO, "username entered"+" " +username +" "+"password entered"+" " +password );
		driver.switchTo().frame(0);
		type("Login_xpath",username);
		type("Password_xpath",password);
		click("signin_xpath");
		return false;
	}
	//for leads
	public int getLeadRowNum(String LeadName)
	{
		test.log(LogStatus.INFO, "finding the lead name " + LeadName);
		List<WebElement> leadNames= driver.findElements(By.xpath(prop.getProperty("leadNamesCol_xpath")));
		for(int i=0;i<leadNames.size();i++)
		{
			System.out.println(leadNames.get(i).getText());
			if(leadNames.get(i).getText().trim().equals(LeadName)){
				test.log(LogStatus.INFO, "Lead found on row number" + (i+1));
				return (i+1);
			}
		}
		//test.log(LogStatus.INFO, "lead found");
		return -1;

	}
	//for accounts
	public int getLeadRowNum1(String LeadName)
	{
		test.log(LogStatus.INFO, "finding the lead name " + LeadName);
		List<WebElement> leadNames= driver.findElements(By.xpath(prop.getProperty("AccountNameCol_xpath")));
		for(int i=0;i<leadNames.size();i++)
		{
			System.out.println(leadNames.get(i).getText());
			if(leadNames.get(i).getText().trim().equals(LeadName)){
				test.log(LogStatus.INFO, "Lead found on row number" + (i+1));
				return (i+1);	    			
			}
		}

		return -1;

	}
	//selects lead name
	public void clickOnLead(String LeadName){
		test.log(LogStatus.INFO, "clicking on the lead name " + LeadName);
		int rNum = getLeadRowNum(LeadName);
		System.out.println(rNum);
		driver.findElement(By.xpath("//table[@id='listViewTable']/tbody/tr[1]/td[4]")).click();
		//if(rNum==1)
		//test.log(LogStatus.INFO, "lead found");
	}
	//selects account name
	public void clickOnAccount(String LeadName){
		test.log(LogStatus.INFO, "clicking on the lead name " + LeadName);
		int rNum = getLeadRowNum(LeadName);
		System.out.println(rNum);
		driver.findElement(By.xpath("//table[@id='listViewTable']/tbody/tr[1]/td[3]")).click();
	}
	//selects date
	public void selectdate(String date){
		test.log(LogStatus.INFO, "date to be selected " + date);
		click("clickdatefield_xpath");
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		try {
			Date datetobeselected = sdf.parse(date);
			Date currentdate = new Date();
			sdf=new SimpleDateFormat("MMMM");
			String monthtobeselected = sdf.format(datetobeselected);
			System.out.println(monthtobeselected);
			sdf=new SimpleDateFormat("yyyy");
			String yeartobeselected = sdf.format(datetobeselected);
			sdf=new SimpleDateFormat("dd");
			String daytobeselected = sdf.format(datetobeselected);
			System.out.println(daytobeselected);
			String monthyeartobeselected = monthtobeselected+ " "+yeartobeselected;
			while(true){
				if(currentdate.compareTo(datetobeselected)==1){
					click("forward_xpath");

				}else if(currentdate.compareTo(datetobeselected)==-1){

					click("back_xpath");
				}
				if(monthyeartobeselected.equals(getText("Date_xpath")));
				break;
			}
			driver.findElement(By.xpath("//td[text()='"+daytobeselected+"']")).click();
			test.log(LogStatus.INFO, "date selected " + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectstage(){

		driver.findElement(By.xpath("//*[@id='select2-Crm_Potentials_STAGE-container']")).click();    	

	}
}


