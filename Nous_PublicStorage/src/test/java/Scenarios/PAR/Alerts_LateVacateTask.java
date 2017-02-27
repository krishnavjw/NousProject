package Scenarios.PAR;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.PeerReviewPage.Alerts_LateVacateTaskCheckListPage;
import Pages.PeerReviewPage.DM_viewallLateTasksPage;
import Scenarios.Browser_Factory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Alerts_LateVacateTask extends Browser_Factory {


	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "PAR","PAR", "Alerts_LateVacateTask");
	}


	@Test(dataProvider="getData")
	public void PeerReviewExistingLease(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("Alerts_LateVacateTask","Alerts_LateVacateTask");


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PAR").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "PeerReviewExistingLease is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			String locationCode,employeeNumber;

			String employeeIDQuery="select top 1 assignedemployeeid,locationcode from task where title like '%Late Vacates%' and taskstatusid=1 and assignedemployeeid is not null order by createdt desc";
			//String employeeIDQuery="select top 1 assignedemployeeid,locationcode from task where title like '%Units on Maintenance Too Long%' and taskstatusid=1 and assignedemployeeid is not null order by createdt desc";
			ArrayList<String> db_Result = DataBase_JDBC.executeSQLQuery_List(employeeIDQuery);

			Thread.sleep(2000);


			String employeeID = db_Result.get(0);
			locationCode = db_Result.get(1);
			System.out.println("location code" + locationCode);

			String employeeNumberQuery = "select employeenumber from employee where  employeeid="+employeeID+"";
			employeeNumber = DataBase_JDBC.executeSQLQuery(employeeNumberQuery);
			System.out.println("employee number" + employeeNumber);




			String query_siteId="select siteid from site where sitenumber="+locationCode;
			String siteid_tobeset=DataBase_JDBC.executeSQLQuery(query_siteId);
			System.out.println("Site id to be set" + siteid_tobeset);

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

				//Update paramcode of the new site id to null
				String allocate_newsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid="+siteid_tobeset+"'";
				DataBase_JDBC.executeSQLQuery(allocate_newsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='"+ipadd+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+siteid_tobeset+"'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			}

			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);

			System.out.println("user name to login " + employeeNumber);

			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			//login.enterUserName(tabledata.get("UserName"));
			login.enterUserName(employeeNumber);
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);


			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			String biforstNum=Bifrostpop.getBiforstNo();

			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);


			/*	driver.findElement(By.xpath("//div[@id='header-logo-container']//a")).click();
			Thread.sleep(5000);*/

			/*
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
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
			Actions act=new Actions(driver);
			for(WebElement ele:taskList)
			{
				act.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().equalsIgnoreCase("Late vacates"))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}

				Thread.sleep(1000);
			}

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Clicked on Late vacates task in Task list");
			logger.log(LogStatus.INFO, "Clicked on Late vacates task in Task list",image);

			Alerts_LateVacateTaskCheckListPage alerts_Page = new Alerts_LateVacateTaskCheckListPage(driver);
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Late vacates Check List page");
			logger.log(LogStatus.INFO, "Navigated to Late vacates Large Check List page",image);



			if(alerts_Page.verify_assignedtoEmployee())
			{
				logger.log(LogStatus.PASS, "Assigned to section on task screen should shows employee name :- " + alerts_Page.assignedto_EmpName());
			}else
			{
				logger.log(LogStatus.FAIL, "Employee Name is not shown in  Assigned to section on Task Screen");
			}



			if(alerts_Page.verify_lateIndicator())
			{
				logger.log(LogStatus.PASS, "Task screen is showing Late indicator based on the duedate " );
			}else
			{
				logger.log(LogStatus.FAIL, "Late Indicator is not shown in on Task Screen");
			}


			if(alerts_Page.verify_dueDate())
			{
				logger.log(LogStatus.PASS, " Due Date and Time are shown in the Task Screen, Due Date: -  " + alerts_Page.get_DueDate() + " Time:-  " + alerts_Page.get_DueTime());
			}else
			{
				logger.log(LogStatus.FAIL, "Due Date and Time are not displayed in the Task Screen");
			}




			//hp.log_off(driver);




			String query_homeJob="select homejob from employee where employeenumber='"+employeeNumber+"'";
			String home_Job=DataBase_JDBC.executeSQLQuery(query_homeJob);

			String query_dmuserName="select employeenumber from employee where HomeJob='"+home_Job+"' and JobTitleCode='AM'";
			String dm_userName=DataBase_JDBC.executeSQLQuery(query_dmuserName);



			System.out.println(dm_userName);

			login.enterUserName(dm_userName);
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			/*driver.findElement(By.xpath("//div[@id='header-logo-container']//a")).click();
			Thread.sleep(5000);*/


			Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			biforstNum=Bifrostpop.getBiforstNo();

			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);




			DM_HomePage dm_home= new DM_HomePage(driver);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to DM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to DM Dashboard",image);



			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);


			//dm_home.click_ViewAllMissedIssues();
			logger.log(LogStatus.INFO, "Clicked on View Issues button from Missed Property Task List");

			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to View All New and In Progress Tasks Page");
			logger.log(LogStatus.INFO, "Navigated to View All New and In Progress Tasks Page",image);


			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
			Thread.sleep(3000);

			DM_viewallLateTasksPage dm_viewall=new DM_viewallLateTasksPage(driver);
			dm_viewall.moveto_LateTask(driver);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,100)");
			Thread.sleep(3000);

			dm_viewall.click_AssignTaskbutton(driver);

			Thread.sleep(5000);



			dm_viewall.enterEmployeeID_ToReassign(tabledata.get("UserName"));


			dm_viewall.enterOptionalNotes("Reassigning to another employee");

			dm_viewall.enterEmployeeID(employeeNumber);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered employee ID :- "+tabledata.get("UserName") + " to Reassign the task");
			logger.log(LogStatus.INFO, "Entered employee ID to Reassign the task",image);


			dm_viewall.click_ConfirmButton();
			logger.log(LogStatus.INFO, "Clicked on Confirm Button ",image);


			//hp.log_off(driver);



			login.enterUserName(tabledata.get("UserName"));
			login.enterPassword(tabledata.get("Password"));
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);


			driver.findElement(By.xpath("//div[@id='header-logo-container']//a")).click();
			Thread.sleep(5000);


			Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			biforstNum=Bifrostpop.getBiforstNo();

			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);



			hp= new PM_Homepage(driver);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);



			taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			act=new Actions(driver);
			for(WebElement ele:taskList)
			{
				act.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().equalsIgnoreCase("Late vacates"))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}

				Thread.sleep(1000);
			}

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Clicked on Late vacates task in Task list");
			logger.log(LogStatus.INFO, "Clicked on Late vacates task in Task list",image);



			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Late vacates Check List page");
			logger.log(LogStatus.INFO, "Navigated to Late vacates Large Check List page",image);



			if(alerts_Page.verify_assignedtoEmployee())
			{
				logger.log(LogStatus.PASS, "Assigned to section on task screen should shows employee name :- " + alerts_Page.assignedto_EmpName());
			}else
			{
				logger.log(LogStatus.FAIL, "Employee Name is not shown in  Assigned to section on Task Screen");
			}



			if(alerts_Page.verify_lateIndicator())
			{
				logger.log(LogStatus.PASS, "Task screen is showing Late indicator based on the duedate " );
			}else
			{
				logger.log(LogStatus.FAIL, "Late Indicator is not shown in on Task Screen");
			}


			if(alerts_Page.verify_dueDate())
			{
				logger.log(LogStatus.PASS, " Due Date and Time are shown in the Task Screen, Due Date: -  " + alerts_Page.get_DueDate() + " Time:-  " + alerts_Page.get_DueTime());
			}else
			{
				logger.log(LogStatus.FAIL, "Due Date and Time are not displayed in the Task Screen");
			}




			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);









			if(alerts_Page.verifyFirstQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #1: When do you believe the customer PHYSICALLY moved-out and how did you determine that date?");
				logger.log(LogStatus.PASS, "Response Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 1 and Response field are not displayed");
			}

			if(alerts_Page.verifySecondQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #2: Explain what specific actions you took and what steps remain to be done (if any).");
				logger.log(LogStatus.PASS, "Response Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 2 and Response field are not displayed");
			}

			if(alerts_Page.verifyThirdQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #3 : What could you have done differently? is displayed");
				logger.log(LogStatus.PASS, "Response Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 3 and Response field are not displayed");
			}


			alerts_Page.clk_Submit_Btn();
			Thread.sleep(2000);


			if(alerts_Page.verify_WarningMessage()){
				logger.log(LogStatus.PASS, "Warning message is displayed when no answer is entered in the answer text field. ");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when no answer is entered in the answer text field. ");
			}


			alerts_Page.clk_OkBtn();
			Thread.sleep(2000);

			alerts_Page.enter_response_Text(tabledata.get("answer_lessthan25"));


			alerts_Page.clk_Submit_Btn();
			Thread.sleep(2000);

			if(alerts_Page.verify_WarningMessage()){
				logger.log(LogStatus.PASS, "Warning message is displayed when less than 25 characters are entered in the answer text field. ");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when less than 25 characters are entered in the answer text field. ");
			}


			alerts_Page.clk_OkBtn();
			Thread.sleep(2000);



			alerts_Page.enter_response_Text(tabledata.get("valid_Answer"));

			alerts_Page.clk_Submit_Btn();
			Thread.sleep(2000);


			if(alerts_Page.verify_WarningMessage()){
				logger.log(LogStatus.PASS, "Warning message is displayed when Certified Check box is not checked ");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Warning message is not displayed when Certified check box is not checked ");
			}


			alerts_Page.clk_OkBtn();
			Thread.sleep(2000);



			String url=driver.getCurrentUrl();
			String taskid="";
			String arr[]=url.split("taskid=");
			arr = arr[1].split("&");
			taskid=arr[0];


			logger.log(LogStatus.INFO, "Selected Task ID is " + taskid);





			alerts_Page.enable_CertifiedCheckbox();
			Thread.sleep(2000);


			Thread.sleep(3000);
			alerts_Page.clk_Submit_Btn();
			Thread.sleep(3000);

			String sqlQuery2="select taskstatusid,FORMAT(completionDT, 'YYYY-MM-dd') from task where ID ='"+taskid+"'";
			ArrayList<String> squery= DataBase_JDBC.executeSQLQuery_List(sqlQuery2);
			String tastStatusId=squery.get(0);
			String completionDT=squery.get(1);

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
			Excel.setCellValBasedOnTcname(path,"PAR","Alerts_LateVacateTask" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","Alerts_LateVacateTask" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","Alerts_LateVacateTask" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}


}
