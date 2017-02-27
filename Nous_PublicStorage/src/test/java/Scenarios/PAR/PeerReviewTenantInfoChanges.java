package Scenarios.PAR;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.junit.experimental.theories.Theories;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.PeerReviewPage.PeerReviewChecklistPage;
import Scenarios.Browser_Factory;

public class PeerReviewTenantInfoChanges extends Browser_Factory {


	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "PAR","PAR", "PeerReviewTenantInfoChanges");
	}


	@Test(dataProvider="getData")
	public void PeerReviewExistingLease(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("PeerReviewTenantInfoChanges","PeerReviewTenantInfoChanges");


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PAR").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "PeerReviewTenantInfoChanges is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum=Bifrostpop.getBiforstNo();

			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);
			/*Reporter.log(biforstNum+"",true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(8000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));

			// Navigating to CFS
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));  

			List<WebElement> biforstSystem=driver.findElements(By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for(WebElement ele:biforstSystem)
			{
				if(biforstNum.equalsIgnoreCase(ele.getText().trim()))
				{
					Reporter.log(ele.getText()+"",true);
					ele.click();
					break;
				}
			}

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);*/

			PM_Homepage hp= new PM_Homepage(driver);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Clicked on Existing Lease task in Task list");
			logger.log(LogStatus.INFO, "Clicked on Existing Lease task in Task list",image);

			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			Actions dragger = new Actions(driver);
			WebElement scroll=driver.findElement(By.xpath("//div[@id='task-grid']//div[@class='ps-scrollbar-y']"));		
			int i=1;
			for(WebElement ele:taskList){
				if(ele.getText().trim().equalsIgnoreCase("Existing Lease")){
					ele.click();
					break;
				}
				if(i%3==0){
					dragger.moveToElement(scroll).clickAndHold().moveByOffset(0, i).release().build().perform();
				}
				i+=1;
				Thread.sleep(1000);
			}

			PeerReviewChecklistPage peerReviewPage = new PeerReviewChecklistPage(driver);
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Peer Review Existing Lease Check List page");
			logger.log(LogStatus.INFO, "Navigated to Peer Review Existing Lease Check List page",image);

			if(peerReviewPage.verifyAssignedToField()){
				logger.log(LogStatus.PASS, "Assigned to is displayed in Peer Review Existing Lease Check List page");

			}

			List<WebElement> yesRadioButtons = driver.findElements(By.xpath("//div[@id='questions']//div//ul//li//label//span[@class='button']/preceding-sibling::input[@value='Yes']"));
			List<WebElement> NAText = driver.findElements(By.xpath("//div[@class='questionradiobutton']//ul"));

			boolean isDisabled = false;
			for(int j=0;j<yesRadioButtons.size();j++){

				isDisabled=false;
				try {
					yesRadioButtons.get(j).getAttribute("disabled");
					isDisabled=true;
					String getMessage = NAText.get(j).findElement(By.xpath(".//li[3]")).getText();
					if(getMessage.contains("N/A")){
						logger.log(LogStatus.PASS, "'NA' is displayed for the Questions that are not required to be answered on the task");

					}else{
						logger.log(LogStatus.FAIL, "'NA' is displayed for the Questions that are not required to be answered on the task");
					}
					continue;
				} catch (Exception e) {
					System.out.println("Radio Button is Disabled");
				}
				yesRadioButtons.get(j).click();
				Thread.sleep(2000);
			}
			String url=driver.getCurrentUrl();
			String taskid="";
			String arr[]=url.split("taskid=");
			arr = arr[1].split("&");
			taskid=arr[0];

			driver.findElement(By.xpath("//div[@id='questions']//div//ul//li//label//span[text()='No']/preceding-sibling::span[@class='button'])[8]")).click();
			Thread.sleep(3000);
			if(driver.findElement(By.xpath("//span[@class='notification-icon']")).isDisplayed()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Informational Warning is displayed when 'No' option is selected and question is highlighted");
				logger.log(LogStatus.INFO, "Informational Warning is displayed when 'No' option is selected and question is highlighted",image);
			}
			Thread.sleep(3000);
			peerReviewPage.clk_CompletedRadio_Btn();
			Thread.sleep(3000);
			peerReviewPage.clk_Submit_Btn();
			Thread.sleep(3000);
			if(peerReviewPage.verify_OkBtn()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validated the Comments text field when 'No' option is selected");
				logger.log(LogStatus.INFO, "Validated the Comments text field when 'No' option is selected",image);
			}
			Thread.sleep(3000);
			peerReviewPage.clk_OkBtn();
			Thread.sleep(3000);
			peerReviewPage.enter_CommentsTxtField("Review Comments are added");
			driver.findElement(By.xpath("(//div[@id='questions']//div//ul//li//label//span[text()='Yes']/preceding-sibling::span[@class='button'])[8]")).click();
			Thread.sleep(3000);
			peerReviewPage.clk_CompletedRadio_Btn();
			Thread.sleep(3000);
			peerReviewPage.clk_Submit_Btn();
			Thread.sleep(3000);

			String sqlQuery="select taskstatusid,FORMAT(completionDT, 'YYYY-MM-dd') from task where ID ='"+taskid+"'";
			ArrayList<String> query= DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			String tastStatusId=query.get(0);
			String completionDT=query.get(1);

			if(tastStatusId.equals("4")&completionDT.equals(Generic_Class.getCurrentDate())){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Submitted task have completiondt column populated with current timestamp and taskstatusid is displayed as 4 in DB"+ "Task Status ID: "+ tastStatusId+ "CompletionDT" + completionDT);

			}

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Test script failed due to the exception",image);
		}

	}       


	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"PAR","PeerReviewTenantInfoChanges" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","PeerReviewTenantInfoChanges" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","PeerReviewTenantInfoChanges" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
