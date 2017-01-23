package com.selenium.gmail.testcases;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.selenium.gmail.base.BaseTest;
import com.selenium.gmail.utilities.dataUtil;

public class FileUploadTest extends BaseTest {

	@BeforeClass
	public void beforeClass() throws Exception {
		init();
		test =rep.startTest("FileUploadTest");
		test.log(LogStatus.INFO, "Starting the File Upload Test");
		dataUtil.setExcelFile(prop.getProperty("path"),"SendMailTests");
	}

	@DataProvider(name = "FileUploadTests")
	public Object[][] dataProvider() {
		Object[][] testData = dataUtil.getTestData("Attach");
		return testData;
	}

	@Test(dataProvider="FileUploadTests")  
	public void fileUpload(String sendTo, String subject, String filePath ) throws Exception {

		openBrowser();
		navigate("gmail_url");
		doLogin();
		click("btn_compose");
		type("mailbox", sendTo);
		type("subjectbox",subject);
		click("btn_fileInp");

		// Using Robot Class
		StringSelection stringSelection= new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null); // Copy to Clipboard

		Robot robot = new Robot();
		// CMD + Tab  // How are these keys generated ?
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(2000);

		// Goto Window
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_G);

		// Paste the clipboard value
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_V);
		robot.delay(2000);

		// Hit Enter Key
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);

		click("sendbtn");
		test.log(LogStatus.PASS, "Success");
		takeScreenShot();

		// Alternative - Auto IT - Windows

	}

	@AfterClass
	public void afterClass() {
		rep.endTest(test); // End the test
		rep.flush(); // append the test in HTML
		driver.quit();
	}

}
