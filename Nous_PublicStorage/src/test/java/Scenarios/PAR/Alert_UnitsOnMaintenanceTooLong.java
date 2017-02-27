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
import Pages.PeerReviewPage.UnitsOnMaintenanceTooLongPage;
import Scenarios.Browser_Factory;

public class Alert_UnitsOnMaintenanceTooLong extends Browser_Factory {


	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");
	String siteid_tobeset;
	String locationCode; 
	String employeeNumber;

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "PAR","PAR", "Alert_UnitsOnMaintenanceTooLong");
	}


	@Test(dataProvider="getData")
	public void Alert_UnitsOnMaintenanceTooLong(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("Alert_UnitsOnMaintenanceTooLong","Alert_UnitsOnMaintenanceTooLong");


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PAR").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "Alert_UnitsOnMaintenanceTooLong is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{


			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
			String employeeIDQuery="select top 1 assignedemployeeid,locationcode from task where title like '%Units on Maintenance Too Long%' and taskstatusid=1 and assignedemployeeid is not null order by createdt desc";
			try 
			{
			ArrayList<String> db_Result = DataBase_JDBC.executeSQLQuery_List(employeeIDQuery);
			
			String employeeID = db_Result.get(0);
			 locationCode = db_Result.get(1);

			String employeeNumberQuery = "select employeenumber from employee where  employeeid="+employeeID+"";
			 employeeNumber = DataBase_JDBC.executeSQLQuery(employeeNumberQuery);
			 System.out.println(employeeNumber);

			


			} catch (Exception e) {
				logger.log(LogStatus.INFO, "No Details are fetched from database query");
			}

			siteid_tobeset = DataBase_JDBC.executeSQLQuery("select siteid from site where sitenumber='"+locationCode+"'");

			// Fetching IP Address & changing it accordingly

			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);

			String current_siteid_query = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+ipadd+"'";
			String current_siteid = DataBase_JDBC.executeSQLQuery(current_siteid_query);
			System.out.println("The siteid_ipsdd_data is-----" + current_siteid);

			if (!(current_siteid.equals(siteid_tobeset))) {

				// Make the currently assigned site id to null
				String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+current_siteid+"'";
				DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='"+ipadd+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+siteid_tobeset+"'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			}


			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.enterUserName(employeeNumber);
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			Thread.sleep(15000);

			/*Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum=Bifrostpop.getBiforstNo();

			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);*/
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
			
			
			if(driver.findElements(By.xpath("//h3[text()='Corporate Dashboard']")).size()!=0)
			{
				driver.get("http://wc2qa.ps.com/Dashboard/Property");
			}
			
			Thread.sleep(10000);

			PM_Homepage hp= new PM_Homepage(driver);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);



			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			Actions act = new Actions(driver);

			for(WebElement ele:taskList){
				act.moveToElement(ele).build().perform();
				String val=ele.getText();

				if(ele.getText().trim().equalsIgnoreCase("Units on Maintenance Too Long")){
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "image",image);
					act.moveToElement(ele).click().build().perform();

					break;
				}

			}

			Thread.sleep(5000);
			UnitsOnMaintenanceTooLongPage unitMainPage = new UnitsOnMaintenanceTooLongPage(driver);
			Thread.sleep(5000);

			if(unitMainPage.verifyUnitMaintanceTitle())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Navigated to Unit on Maintenance Too Long page");
				logger.log(LogStatus.INFO, "Navigated to Unit on Maintenance Too Long page",image);

			}


			if(unitMainPage.verifyAssignedToField()){
				
				String user = unitMainPage.getAssignedUser();
				logger.log(LogStatus.PASS, "Task is Assigned to :"+user);

			}else
			{
				logger.log(LogStatus.FAIL, "WebChamp System is not present in Assigned to field");
			}


			if(unitMainPage.verifyFirstQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #1 : Why were these units on maintenance too long? is displayed");
				logger.log(LogStatus.PASS, "Respond Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 1 and Respond field is not displayed");
			}

			if(unitMainPage.verifySecondQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #2 : What could you have done differently? is displayed");
				logger.log(LogStatus.PASS, "Respond Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 2 and Respond field is not displayed");
			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
			Thread.sleep(3000);

			unitMainPage.clk_Submit_Btn();
			Thread.sleep(2000);


			if(unitMainPage.verify_WarningMessage()){				
				logger.log(LogStatus.PASS, "Warning message is displayed when no answer is entered in the answer text field. ");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Warning message is displayed when no answer is entered in the answer text field. ",image);
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when no answer is entered in the answer text field. ");		
			}


			unitMainPage.clk_OkBtn();
			Thread.sleep(2000);

			unitMainPage.enterFirstQuesTxtArea(tabledata.get("answer_lessthan25"));
			Thread.sleep(2000);
			unitMainPage.enterSecondQuesTxtArea(tabledata.get("answer_lessthan25"));
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image ",image);

			Thread.sleep(2000);


			unitMainPage.clk_Submit_Btn();
			Thread.sleep(2000);

			if(unitMainPage.verify_WarningMessage()){
				logger.log(LogStatus.PASS, "Warning message is displayed when less than 25 characters are entered in the answer text field. ");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when less than 25 characters are entered in the answer text field. ");
			}
			
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image ",image);



			unitMainPage.clk_OkBtn();
			Thread.sleep(2000);



			unitMainPage.enterFirstQuesTxtArea(tabledata.get("valid_Answer"));
			Thread.sleep(2000);
			unitMainPage.enterSecondQuesTxtArea(tabledata.get("valid_Answer"));
			
			Thread.sleep(2000);


			unitMainPage.clk_Submit_Btn();
			Thread.sleep(2000);


			if(unitMainPage.verify_WarningMessage()){
				logger.log(LogStatus.PASS, "Warning message is displayed when Certified Check box is checked ");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when Certified check box is not checked ");
			}
			
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image ",image);



			unitMainPage.clk_OkBtn();
			Thread.sleep(2000);



			String url=driver.getCurrentUrl();
			String taskid="";
			String arr[]=url.split("taskid=");
			arr = arr[1].split("&");
			taskid=arr[0];
			System.out.println(taskid);

			unitMainPage.enable_CertifiedCheckbox();
			Thread.sleep(2000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image ",image);



			Thread.sleep(3000);


			unitMainPage.clk_Submit_Btn();
			Thread.sleep(15000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image ",image);

			Thread.sleep(3000);

			String sqlQuery="select taskstatusid,FORMAT(completionDT, 'yyyy-MM-dd') from task where ID ='"+taskid+"'";
			ArrayList<String> query= DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			String tastStatusId=query.get(0);
			String completionDT=query.get(1);
			System.out.println(completionDT);

			if(tastStatusId.equals("4")&&completionDT.equals(Generic_Class.getCurrentDate())){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Submitted task have completiondt column populated with current timestamp and taskstatusid is displayed as 4 in DB"+ "Task Status ID: "+ tastStatusId+ "CompletionDT" + completionDT);

			}else
			{
				logger.log(LogStatus.FAIL, "Submitted task have completiondt column populated with current timestamp and taskstatusid is not displayed as 4 in DB"+ "Task Status ID is : "+ tastStatusId+ " CompletionDT is :" + completionDT);
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
			Excel.setCellValBasedOnTcname(path,"PAR","Alert_UnitsOnMaintenanceTooLong" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","Alert_UnitsOnMaintenanceTooLong" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","Alert_UnitsOnMaintenanceTooLong" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
