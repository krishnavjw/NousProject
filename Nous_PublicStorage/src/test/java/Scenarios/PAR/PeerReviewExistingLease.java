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

public class PeerReviewExistingLease extends Browser_Factory {

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
		return Excel.getCellValue_inlist(path, "PAR","PAR", "PeerReviewExistingLease");
	}


	@Test(dataProvider="getData")
	public void PeerReviewExistingLease(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("PeerReviewExistingLease","PeerReviewExistingLease");


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

			String employeeIDQuery="select top 1 AssignedEmployeeId,locationCode,WorkflowUri,ID from task where title like 'Existing Lease' "
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
			System.out.println("The siteid_ipsdd_data is-----: " + current_siteid);

			if (!(current_siteid.equals(siteid_tobeset))) {

				// Make the currently assigned site id to null
				String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+current_siteid+"'";
				DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='"+ipadd+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+siteid_tobeset+"'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			}


			LoginPage login= new LoginPage(driver);
			//String siteNumber = login.get_SiteNumber();
			login.enterUserName(employeeNumber);
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			String biforstNum=Bifrostpop.getBiforstNo();
			Bifrostpop.clickContiDevice(); 
			Thread.sleep(5000);

			PM_Homepage hp= new PM_Homepage(driver);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			/*((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Clicked on Existing Lease task in Task list");
			logger.log(LogStatus.INFO, "Clicked on Existing Lease task in Task list",image);

			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			Actions act = new Actions(driver);

			for(WebElement ele:taskList){
				act.moveToElement(ele).build().perform();
				String val=ele.getText();

				if(ele.getText().trim().equalsIgnoreCase("Existing Lease")){
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "image",image);
					act.moveToElement(ele).click().build().perform();

					break;
				}

			}
			 */

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
					"convert (datetime,(dbo.fn_sitedatetime ("+siteid_tobeset+",t.CreateDt))) as TaskCreated, "+//--site id
					"convert (datetime,(dbo.fn_sitedatetime ("+siteid_tobeset+",t.DueDt))) as TaskDue "+ //--site id
					"from task t "+
					"join employee e on t.AssignedEmployeeId=e.employeeid "+
					"join contact c on e.contactid=c.contactid "+
					"where t.id="+taskID+" "; //-- task id

			ArrayList<String> db_Values = DataBase_JDBC.executeSQLQuery_List(assignedToQuery);

			String descriptionDB = db_Values.get(0);
			assignedTo = db_Values.get(1);
			taskCreated = db_Values.get(2);
			taskDue = db_Values.get(3);

			String[] dateTaskCreated=taskCreated.split(" ");
			String[] dateCreatedDBValue=dateTaskCreated[0].split("-");
			String dateCreatedval=dateCreatedDBValue[1]+"/"+dateCreatedDBValue[2]+"/"+dateCreatedDBValue[0];
			System.out.println(dateCreatedval);

			String[] dateTaskDue=taskDue.split(" ");
			String[] dateTaskDBValue=dateTaskDue[0].split("-");
			String dateTaskDueval=dateTaskDBValue[1]+"/"+dateTaskDBValue[2]+"/"+dateTaskDBValue[0];
			System.out.println(dateTaskDueval);


			PeerReviewChecklistPage peerReviewPage = new PeerReviewChecklistPage(driver);
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Peer Review Existing Lease Check List page");
			logger.log(LogStatus.INFO, "Navigated to Peer Review Existing Lease Check List page",image);

			// New Validations

			String descriptionUIValue= peerReviewPage.getDescriptionValue();
			String assignedToUIValue= peerReviewPage.getAssignedUser();
			String taskCreatedUIValue= peerReviewPage.getTaskCreatedValue();
			String taskDueUIValue= peerReviewPage.getTaskDueValue();

			if(descriptionUIValue.equalsIgnoreCase(descriptionDB)){

				logger.log(LogStatus.PASS, "Description Value in UI "+descriptionUIValue+" & Description Value in DB "+descriptionDB+" -- Description Values are matching with UI and DB");
			}else{
				logger.log(LogStatus.FAIL, "Description Value in UI "+descriptionUIValue+" & Description Value in DB "+descriptionDB+" -- Description Values are matching with UI and DB");
			}

			if(assignedToUIValue.equalsIgnoreCase(assignedTo)){

				logger.log(LogStatus.PASS, "Description Value in UI "+assignedToUIValue+" & Description Value in DB "+assignedTo+" -- Description Values are matching with UI and DB");
			}else{
				logger.log(LogStatus.FAIL, "Description Value in UI "+assignedToUIValue+" & Description Value in DB "+assignedTo+" -- Description Values are matching with UI and DB");
			}

			if(taskCreatedUIValue.equalsIgnoreCase(dateCreatedval)){

				logger.log(LogStatus.PASS, "Description Value in UI "+taskCreatedUIValue+" & Description Value in DB "+dateCreatedval+" -- Description Values are matching with UI and DB");
			}else{
				logger.log(LogStatus.FAIL, "Description Value in UI "+taskCreatedUIValue+" & Description Value in DB "+dateCreatedval+" -- Description Values are matching with UI and DB");
			}

			if(taskDueUIValue.equalsIgnoreCase(dateTaskDueval)){
				logger.log(LogStatus.PASS, "Description Value in UI "+taskDueUIValue+" & Description Value in DB "+dateTaskDueval+" -- Description Values are matching with UI and DB");
			}else{
				logger.log(LogStatus.FAIL, "Description Value in UI "+taskDueUIValue+" & Description Value in DB "+dateTaskDueval+" -- Description Values are matching with UI and DB");
			}

			List<WebElement> totalQuestions = driver.findElements(By.xpath("//div[@id='questions']//div[@class='quetionrow']"));
			List<WebElement> NAText = driver.findElements(By.xpath("//div[@id='questions']//input[@value='Yes'][@disabled='disabled']"));
			logger.log(LogStatus.INFO, "Total number of Questions " + totalQuestions.size());
			logger.log(LogStatus.INFO, "Total number of NA Questions " + NAText.size());

			List<WebElement> noRadioButtons= driver.findElements(By.xpath("//div[@id='questions']//div[@class='quetionrow']//div//ul//li//span[text()='No']/preceding-sibling::span[@class='button']"));

			for(WebElement btns: noRadioButtons){
				if(btns.isEnabled()){
					btns.click();
				}
			}
			
			/*String url=driver.getCurrentUrl();
			String taskid="";
			String arr[]=url.split("taskid=");
			arr = arr[1].split("&");
			taskid=arr[0];*/

			//driver.findElement(By.xpath("//div[@id='questions']//div//ul//li//label//span[text()='No']/preceding-sibling::span[@class='button']")).click();
			Thread.sleep(3000);
			if(driver.findElement(By.xpath("//span[@class='notification-icon']")).isDisplayed()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Informational Warning is displayed when 'No' option is selected and question is highlighted");
				logger.log(LogStatus.INFO, "Informational Warning is displayed when 'No' option is selected and question is highlighted",image);
			}
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);

			if(peerReviewPage.verify_CompletedRadio_Btn()){
				peerReviewPage.clk_CompletedRadio_Btn();
				Thread.sleep(3000);
			}else{
				peerReviewPage.clk_Submit_Btn();
				Thread.sleep(3000);
			}
			if(peerReviewPage.verify_OkBtn()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validated the Comments text field alert when 'No' option is selected");
				logger.log(LogStatus.INFO, "Validated the Comments text field alert when 'No' option is selected",image);
			}
			Thread.sleep(3000);
			peerReviewPage.clk_OkBtn();
			Thread.sleep(3000);
			peerReviewPage.enter_CommentsTxtField("Review Comments are added");
			//driver.findElement(By.xpath("(//div[@id='questions']//div//ul//li//label//span[text()='Yes']/preceding-sibling::span[@class='button'])[8]")).click();
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Review Comments added in the text field");
			logger.log(LogStatus.INFO, "Review Comments added in the text field",image);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);

			if(peerReviewPage.verify_CompletedRadio_Btn()){
				peerReviewPage.clk_CompletedRadio_Btn();
				Thread.sleep(3000);
			}else{
				peerReviewPage.clk_Submit_Btn();

			}
			Thread.sleep(18000);
			String sqlQuery1="select taskstatusid,FORMAT(completionDT, 'yyyy-MM-dd') from task where ID ='"+taskID+"'";
			ArrayList<String> query= DataBase_JDBC.executeSQLQuery_List(sqlQuery1);
			String tastStatusId=query.get(0);
			String completionDT=query.get(1);

			if(tastStatusId.equals("4")&completionDT.equals(Generic_Class.getCurrentDate())){
				logger.log(LogStatus.PASS, "Submitted task have completiondt column populated with current timestamp and taskstatusid is displayed in DB is : "+ "Task Status ID: " + tastStatusId+ " CompletionDT " + completionDT);
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img",image);

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
			Excel.setCellValBasedOnTcname(path,"PAR","PeerReviewExistingLease" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","PeerReviewExistingLease" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","PeerReviewExistingLease" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
