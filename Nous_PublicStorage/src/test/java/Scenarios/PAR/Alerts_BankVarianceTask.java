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
import Pages.PeerReviewPage.Alerts_BankVarianceCheckListPage;
import Scenarios.Browser_Factory;

public class Alerts_BankVarianceTask extends Browser_Factory {


	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");
	String siteid_tobeset,locationCode;
	String employeeNumber;
	String workflowUri;
	String taskID,assignedTo,taskCreated,taskDue;

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "PAR","PAR", "Alerts_BankVarianceTask");
	}


	@Test(dataProvider="getData")
	public void PeerReviewExistingLease(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("Alerts_BankVarianceTask","Alerts_BankVarianceTask");


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

			String employeeIDQuery="select top 1 AssignedEmployeeId,locationCode,WorkflowUri,ID from task where title like '%Bank Variances - Large%' "
					+ "and taskstatusid=1 "
					+ "and assignedemployeeid is not null "
					+ "and locationcode not like 'D%' order by createdt desc";
			try 
			{
				ArrayList<String> db_Result = DataBase_JDBC.executeSQLQuery_List(employeeIDQuery);

				String employeeID = db_Result.get(0);
				locationCode = db_Result.get(1);
				workflowUri = db_Result.get(2);
				taskID = db_Result.get(3);

				String employeeNumberQuery = "select employeenumber from employee where  employeeid="+employeeID+"";
				employeeNumber = DataBase_JDBC.executeSQLQuery(employeeNumberQuery);
				System.out.println(employeeNumber);

				logger.log(LogStatus.INFO, "Employee Number from DB : " + employeeNumber);
				logger.log(LogStatus.INFO, "Task ID appending to URL: " + taskID);
				logger.log(LogStatus.INFO, "Workflow URI appending to URL : " + workflowUri);


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
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);


			/*driver.findElement(By.xpath("//div[@id='header-logo-container']//a")).click();
			Thread.sleep(5000);
			 */
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
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

			/*((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);

			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			Actions dragger = new Actions(driver);
			WebElement scroll=driver.findElement(By.xpath("//div[@id='task-grid']//div[@class='ps-scrollbar-y']"));		
			int i=1;
			Actions act=new Actions(driver);
			for(WebElement ele:taskList){
				System.out.println(ele.getText());
				if(ele.getText().trim().equalsIgnoreCase("Bank Variances - Large")){
					act.moveToElement(ele).click().build().perform();
					ele.click();
					break;
				}
				dragger.moveToElement(scroll).clickAndHold().moveByOffset(0, 10).release().build().perform();

					
				if(i%3==0){
					dragger.moveToElement(scroll).clickAndHold().moveByOffset(0, i).release().build().perform();
				}
				i+=1;
				Thread.sleep(1000);
			}

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Clicked on Bank Variances - Large task in Task list");
			logger.log(LogStatus.INFO, "Clicked on Bank Variances - Large task in Task list",image);*/

			String getCurrentUrl = driver.getCurrentUrl();
			if(getCurrentUrl.contains("Property")){
				String appUrl = "http://wc2aut.ps.com"+workflowUri+"&taskid="+taskID+"&source=pm";
				//String appUrl = "http://wc2qa.ps.com"+workflowUri+"&taskid="+taskID+"&source=pm";
				System.out.println(appUrl);
				driver.get(appUrl);
			}else if(getCurrentUrl.contains("District")){
				String appUrl = "http://wc2aut.ps.com"+workflowUri+"&taskid="+taskID+"&source=dm";
				//String appUrl = "http://wc2qa.ps.com"+workflowUri+"&taskid="+taskID+"&source=dm";
				System.out.println(appUrl);
				driver.get(appUrl);
			}

			String assignedToQuery="select t.description, c.firstname +' '+ c.lastname as Assignedto, "+
					"convert (datetime,(dbo.fn_sitedatetime ("+siteid_tobeset+",t.CreateDt))) as TaskCreated, "+
					"convert (datetime,(dbo.fn_sitedatetime ("+siteid_tobeset+",t.DueDt))) as TaskDue "+
					"from task t "+
					"left join employee e on t.AssignedEmployeeId=e.employeeid "+
					"left  join contact c on e.contactid=c.contactid "+ //--If Assigned employee null -- will show webchamp system on UI
					"where t.tableid="+taskID+" "; //-- task id

			
			ArrayList<String> db_Values = DataBase_JDBC.executeSQLQuery_List(assignedToQuery);

			String description = db_Values.get(0);
			assignedTo = db_Values.get(1);
			taskCreated = db_Values.get(2);
			taskDue = db_Values.get(3);

			Alerts_BankVarianceCheckListPage alerts_Page = new Alerts_BankVarianceCheckListPage(driver);
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Bank Variances - Large Check List page");
			logger.log(LogStatus.INFO, "Navigated to Bank Variances - Large Check List page",image);


			if(alerts_Page.verifyFirstQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #1 : Why were these units on maintenance too long? is displayed");
				logger.log(LogStatus.PASS, "Respond Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 1 and Respond field is not displayed");
			}

			if(alerts_Page.verifySecondQuesTxtArea())
			{
				logger.log(LogStatus.PASS, "Question #2 : What could you have done differently? is displayed");
				logger.log(LogStatus.PASS, "Respond Text Area field displayed successfully");
			}else
			{
				logger.log(LogStatus.FAIL, "Question 2 and Respond field is not displayed");
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
			Excel.setCellValBasedOnTcname(path,"PAR","Alerts_BankVarianceTask" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","Alerts_BankVarianceTask" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","Alerts_BankVarianceTask" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
