package Scenarios.PAR;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import Pages.PeerReviewPage.Alerts_LateVacateTaskCheckListPage;
import Pages.PeerReviewPage.PeerReviewChecklistPage;
import Scenarios.Browser_Factory;

public class Alert_ExcessiveReversalTask extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");
	String siteid_tobeset, locationCode;
	String employeeNumber;
	String workflowUri;
	String siteID;
	String alertID;
	String empID;
	String descriptionDB;
	String reportDate;
	String taskID, assignedTo, taskCreated, taskDue;

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "PAR","PAR", "Alert_ExcessiveReversalTask");
	}


	@Test(dataProvider="getData")
	public void PeerReviewExistingLease(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("Alert_ExcessiveReversalTask","Alert_ExcessiveReversalTask");


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PAR").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "Alert_ExcessiveReversalTask is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			try {

				String alertIDQuery = "select Top 1 A.AlertID, A.SiteID,A.ReportDate, E.EmployeeNumber, E.EmployeeID from PAR.Alert A "
						+ "join Employee E on A.OrigEmployeeID = E.EmployeeID "
						+ "where A.AlertTypeID = 1900 "
						+ "order by A.LastUpdate Desc";

				ArrayList<String> db_Result = DataBase_JDBC.executeSQLQuery_List(alertIDQuery);

				alertID = db_Result.get(0);
				siteID = db_Result.get(1);
				reportDate = db_Result.get(2);
				employeeNumber = db_Result.get(3);
				empID = db_Result.get(4);

				logger.log(LogStatus.INFO, "AlertID from DB : " + alertID);
				logger.log(LogStatus.INFO, "Employee Number from DB : " + employeeNumber);
				logger.log(LogStatus.INFO, "siteID from DB is  " + siteID);
				logger.log(LogStatus.INFO, "EmpID from DB is  " + empID);

			} catch (Exception e) {
				logger.log(LogStatus.INFO, "No Details are fetched from database query");
			}

			siteid_tobeset = DataBase_JDBC
					.executeSQLQuery("select siteid from site where sitenumber='" + locationCode + "'");

			
			
			
			
			String workflowURLquery = "select t.description, c.firstname +' '+ c.lastname as Assignedto, "
					+ "convert (datetime,(dbo.fn_sitedatetime (90,t.CreateDt))) as TaskCreated, "
					+ "convert (datetime,(dbo.fn_sitedatetime (90,t.DueDt))) as TaskDue, t.workflowuri "
					+ "from task t "
					+ "join employee e on t.AssignedEmployeeId=e.employeeid "
					+ "join contact c on e.contactid=c.contactid "
					+ "where t.tableid='"+alertID+"'";
			
			
			
			ArrayList<String> db_Resultworkflow = DataBase_JDBC.executeSQLQuery_List(workflowURLquery);
			
			descriptionDB= db_Resultworkflow.get(0);
			assignedTo= db_Resultworkflow.get(1);
			taskCreated= db_Resultworkflow.get(2);
			taskDue= db_Resultworkflow.get(3);
			
			workflowUri= db_Resultworkflow.get(4);

			logger.log(LogStatus.INFO, "Workflow url from DB : " + workflowUri);
			
			
			
			

		

			LoginPage login = new LoginPage(driver);
			// String siteNumber = login.get_SiteNumber();
			login.enterUserName(employeeNumber);
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			String biforstNum = Bifrostpop.getBiforstNo();
			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);

			PM_Homepage hp = new PM_Homepage(driver);
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard", image);

			//PM_Homepage hp= new PM_Homepage(driver);
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);


			String getCurrentUrl = driver.getCurrentUrl();
			if (getCurrentUrl.contains("Property")) {
				String appUrl = "http://wc2qa.ps.com" + workflowUri ;
				// String appUrl =
				// "http://wc2qa.ps.com"+workflowUri+"&taskid="+taskID+"&source=pm";
				System.out.println(appUrl);
				driver.get(appUrl);
			} else if (getCurrentUrl.contains("District")) {
				String appUrl = "http://wc2aut.ps.com" + workflowUri ;
				// String appUrl =
				// "http://wc2qa.ps.com"+workflowUri+"&taskid="+taskID+"&source=dm";
				System.out.println(appUrl);
				driver.get(appUrl);
			}

			/*String assignedToQuery = "select t.description, c.firstname +' '+ c.lastname as Assignedto, "
					+ "convert (datetime,(dbo.fn_sitedatetime (" + siteid_tobeset + ",t.CreateDt))) as TaskCreated, " + // --site
																														// id
					"convert (datetime,(dbo.fn_sitedatetime (" + siteid_tobeset + ",t.DueDt))) as TaskDue " + // --site
																												// id
					"from task t " + "join employee e on t.AssignedEmployeeId=e.employeeid "
					+ "join contact c on e.contactid=c.contactid " + "where t.id=" + taskID + " "; // --
																									// task
																									// id

			ArrayList<String> db_Values = DataBase_JDBC.executeSQLQuery_List(assignedToQuery);

			String descriptionDB = db_Values.get(0);
			assignedTo = db_Values.get(1);
			taskCreated = db_Values.get(2);
			taskDue = db_Values.get(3);
			
			*/
			
			
			String[] dateTaskCreated = taskCreated.split(" ");
			String[] dateCreatedDBValue = dateTaskCreated[0].split("-");
			String dateCreatedval = dateCreatedDBValue[1] + "/" + dateCreatedDBValue[2] + "/" + dateCreatedDBValue[0];
			System.out.println(dateCreatedval);

			String[] dateTaskDue = taskDue.split(" ");
			String[] dateTaskDBValue = dateTaskDue[0].split("-");
			String dateTaskDueval = dateTaskDBValue[1] + "/" + dateTaskDBValue[2] + "/" + dateTaskDBValue[0];
			System.out.println(dateTaskDueval);

			PeerReviewChecklistPage peerReviewPage = new PeerReviewChecklistPage(driver);
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Peer Review Existing Lease Check List page");
			logger.log(LogStatus.INFO, "Navigated to Peer Review Existing Lease Check List page", image);

			// New Validations

			String descriptionUIValue = peerReviewPage.getDescriptionValue();
			String assignedToUIValue = peerReviewPage.getAssignedUser();
			String taskCreatedUIValue = peerReviewPage.getTaskCreatedValue();
			String taskDueUIValue = peerReviewPage.getTaskDueValue();

			
			
			

			Alerts_LateVacateTaskCheckListPage alerts_Page = new Alerts_LateVacateTaskCheckListPage(driver);
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Excessive Reversal Task screen ");
			logger.log(LogStatus.INFO, "img",image);



			if(alerts_Page.verify_assignedtoEmployee())
			{
				logger.log(LogStatus.PASS, "Assigned to section on task screen should shows employee name :- " + alerts_Page.assignedto_EmpName());
			}else
			{
				logger.log(LogStatus.FAIL, "Employee Name is not shown in  Assigned to section on Task Screen");
			}



		

			if(alerts_Page.verify_dueDate())
			{
				logger.log(LogStatus.PASS, " Due Date and Time are shown in the Task Screen, Due Date: -  " + alerts_Page.get_DueDate() + " Time:-  " + alerts_Page.get_DueTime());
			}else
			{
				logger.log(LogStatus.FAIL, "Due Date and Time are not displayed in the Task Screen");
			}


			
			
			if (descriptionUIValue.equalsIgnoreCase(descriptionDB)) {

				logger.log(LogStatus.PASS,
						"Description Value in UI " + descriptionUIValue + " & Description Value in DB " + descriptionDB
								+ " --  are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Description Value in UI " + descriptionUIValue + " & Description Value in DB " + descriptionDB
								+ " --  are matching with UI and DB");
			}

			if (assignedToUIValue.contains(assignedTo)) {

				logger.log(LogStatus.PASS,
						"Description Value in UI " + assignedToUIValue + " & Description Value in DB " + assignedTo
								+ " --  are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Description Value in UI " + assignedToUIValue + " & Description Value in DB " + assignedTo
								+ " -- are matching with UI and DB");
			}

			if (taskCreatedUIValue.equalsIgnoreCase(dateCreatedval)) {

				logger.log(LogStatus.PASS,
						"Description Value in UI " + taskCreatedUIValue + " & Description Value in DB " + dateCreatedval
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Description Value in UI " + taskCreatedUIValue + " & Description Value in DB " + dateCreatedval
								+ " --  are matching with UI and DB");
			}

			if (taskDueUIValue.equalsIgnoreCase(dateTaskDueval)) {
				logger.log(LogStatus.PASS, "Description Value in UI " + taskDueUIValue + " & Description Value in DB "
						+ dateTaskDueval + " --  are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL, "Description Value in UI " + taskDueUIValue + " & Description Value in DB "
						+ dateTaskDueval + " --  are matching with UI and DB");
			}

			
			
			
			//Verify  Customer name, space number and payment type
			
			String customerValidationQuery = "select top 1 vw.firstname, vw.lastname, vw.rentalunitnumber, vw.accountid,cltm.Description from vw_unitdetails vw "
					+ "join cltransactionmaster cltm on cltm.accountid = vw.accountid "
					+ "join  CLmasterGroup CLMG on cltm.clmastergroupid = clmg.clmastergroupid "
					+ "where vw.siteid = '"+siteID+"' "
					+ "and cltm.cltransactiontypeid in (161, 160) "
					//+ "and cltm.lastupdate >= '"+reportDate+"' "
					+ "and cltm.lastupdate < '2017-02-23 00:00:00.000' "
					+ "and clmg.employeeid = 8207";
			
			
			
			
          ArrayList<String> db_CustomerDetails = DataBase_JDBC.executeSQLQuery_List(customerValidationQuery);
			
			String CustomerNameDB = db_CustomerDetails.get(0);
		
			String spaceNoDB = db_CustomerDetails.get(2);
			
			String AccountID = db_CustomerDetails.get(3);

			String paymentTypeDB = db_CustomerDetails.get(4);

			logger.log(LogStatus.INFO, "Workflow url from DB : " + workflowUri);
			
			
			
			//Verify Customer Details From UI and DB
			List<WebElement> listCustomerDetails = driver.findElements(By.xpath("//div[@id='excessiveGrid']//table/tbody/tr/td"));
			String CustomerNameUI = listCustomerDetails.get(1).getText();
			
			
			String spaceNoUI = listCustomerDetails.get(2).getText();
			
			String ReversalDateUI = listCustomerDetails.get(3).getText();
			
			
			
			
			String ReversalAmountUI = listCustomerDetails.get(4).getText();
			ReversalAmountUI = ReversalAmountUI.substring(1);
			
			String ReversalAlertsUI = listCustomerDetails.get(8).getText();
			
			
				
			
			
			
			String paymentTypeUI = listCustomerDetails.get(5).getText();
			
			if (CustomerNameUI.contains(CustomerNameDB)) {

				logger.log(LogStatus.PASS,
						"Customer Name in  UI " + CustomerNameUI + " & CustomerName in DB " + CustomerNameDB
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Customer Name in  UI " + CustomerNameUI + " & CustomerName in DB " + CustomerNameDB
								+ " -- are not matching with UI and DB");
			}
			
			
			if (spaceNoUI.contains(spaceNoDB)) {

				logger.log(LogStatus.PASS,
						"Space No in  UI " + spaceNoUI + " & Space No in DB " + spaceNoDB
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Space No in  UI " + spaceNoUI + " & Space No in DB " + spaceNoDB
								+ " -- are not  matching with UI and DB");
			}
			
			
			
			if (paymentTypeUI.equalsIgnoreCase(paymentTypeDB)) {

				logger.log(LogStatus.PASS,
						"Payment Type in  UI " + paymentTypeUI + " & Payment Type  in DB " + paymentTypeDB
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Payment Type in  UI " + paymentTypeUI + " & Payment Type  in DB " + paymentTypeDB
								+ " -- are not matching with UI and DB");
			}
			
			
				
		  //validate the Reversal amount
			
			
			String reversalAmountQuery = "select sum((clt.amount*clt.quantity) + clt.discountamount) from cltransaction clt "
					+ "join cltransactionmaster cltm on clt.cltransactionmasterid = cltm.cltransactionmasterid "
					+ "where cltm.accountid = '"+AccountID+"' "
					//+ "and cltm.lastupdate >= '"+reportDate+"' "
					+ "and cltm.lastupdate < '2017-02-23 00:00:00.000' "
					+ "and cltm.cltransactiontypeid = 160 "
					+ "group by clt.cltransactionmasterid ";
			
			
			
        ArrayList<String> db_ReversalAmount = DataBase_JDBC.executeSQLQuery_List(reversalAmountQuery);
			
			String ReversalAmountDB = db_ReversalAmount.get(0);
			
			if (ReversalAmountDB.contains(ReversalAmountUI)) {

				logger.log(LogStatus.PASS,
						"Reversal Amount in  UI " + ReversalAmountUI + " & Reversal Amount in DB " + ReversalAmountDB
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Reversal Amount in  UI " + ReversalAmountUI + " & Reversal Amount in DB " + ReversalAmountDB
								+ " -- are not matching with UI and DB");
			}
			
			
			
			
			
			//Verify  the reversal date
			
			
			String reversalDateQuery ="select dateadd(d, -1, A.ReportDate) from PAR.Alert A "
                                      + "where A.AlertID = '"+alertID+"'";
			
			
        ArrayList<String> db_ReversalDate = DataBase_JDBC.executeSQLQuery_List(reversalDateQuery);
			
			String ReversalDateDB = db_ReversalDate.get(0);
			
			
			String[] ReveesalDate = ReversalDateDB.split(" ");
			String[] ReveesalDateValue = ReveesalDate[0].split("-");
			String ReveesalDateValueDB = ReveesalDateValue[1] + "/" + ReveesalDateValue[2] + "/" + ReveesalDateValue[0];
			System.out.println(ReveesalDateValueDB);
			
			
			
			
			if (ReversalDateUI.equalsIgnoreCase(ReveesalDateValueDB)) {

				logger.log(LogStatus.PASS,
						"Reversal Date in  UI " + ReversalDateUI + " & Reversal Date in DB " + ReveesalDateValueDB
								+ " -- are matching with UI and DB");
			} else {
				logger.log(LogStatus.FAIL,
						"Reversal Date in  UI " + ReversalDateUI + " & Reversal Date in DB " + ReveesalDateValueDB
								+ " -- are not matching with UI and DB");
			}
			
			
			
			//Verify number of alerts in past 6 months
			
			String db_ReversalAlertQuery = "select count(*) - 1 from PAR.alert (nolock) "
					+ "where alerttypeid = 1900 "
					+ "and siteid = '"+siteID+"' "
					+ "and ReportDate > DATEADD(month, -6, GETDATE())";
			
			
			
	        ArrayList<String> ReversalAlert = DataBase_JDBC.executeSQLQuery_List(db_ReversalAlertQuery);
				
				String ReversalAlertDB = ReversalAlert.get(0);
			
				if (ReversalAlertsUI.equalsIgnoreCase(ReversalAlertDB)) {

					logger.log(LogStatus.PASS,
							"Reversal Alerts Past 6 months  in  UI " + ReversalAlertsUI + " &Reversal Alerts Past 6 months  in DB " + ReversalAlertDB
									+ " -- are matching with UI and DB");
				} else {
					logger.log(LogStatus.FAIL,
							"Reversal Alerts Past 6 months  in  UI " + ReversalAlertsUI + " &Reversal Alerts Past 6 months  in DB " + ReversalAlertDB
									+ " -- are not matching with UI and DB");
				}
				
			
			
			
			
			
			
		}
		
		catch(Exception ex){
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
			Excel.setCellValBasedOnTcname(path,"PAR","Alert_ExcessiveReversalTask" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"PAR","Alert_ExcessiveReversalTask" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PAR","Alert_ExcessiveReversalTask" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

			
}
