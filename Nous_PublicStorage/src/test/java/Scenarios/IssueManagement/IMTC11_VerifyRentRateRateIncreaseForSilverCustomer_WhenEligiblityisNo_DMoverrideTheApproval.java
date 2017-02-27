package Scenarios.IssueManagement;

import java.util.Hashtable;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Scenarios.Browser_Factory;

public class IMTC11_VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval extends Browser_Factory{
	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "IssueManagement","IssueManagement","VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval");
	}
	@Test(dataProvider="getCustSearchData")
	public void VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval", "VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval");
			Reporter.log("Test case started: " +testcaseName, true);

			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}
			/*
			 * Select any Silver customer who has the rent increase approved in last 2 years 
			 */
			/*
			 * on customer dashboard select create issue from quick links 
			 */
			/*
			 * Select "rent rate increase objection" in issue type MW & click on confirm
			 */
			/*
			 * On the create rent rate increase objection issue screen,select any reason from the drop down list
			 */
			/*
			 * Enter the explanation & click on create issue 
			 */
			/*
			 * Enter employee id & click on submit
			 */
			/*
			 * Verify the eligibility should be "NO"
			 */
			/*
			 * Verify account activities issue created activity is generated
			 */
			/*
			 * verify the open issue is displayed in important info section on cust dashboard
			 */
			/*
			 * Validate record in the database for created issue 
			 */
			/*
			 * Verify the task is generated for DM  approval
			 */
			/*
			 * Login as DM & open the task from DM task list 
			 */
			/*
			 * Select override concession as "yes" and select any concession from the drop down
			 */
			/*
			 * Click on continue 
			 */
			/*
			 * Select  Decision as :"Approve"
			 */
			/*
			 * Enter the notes & employeeid 
			 */
			/*
			 * Click on confrim
			 */
			/*
			 * Verify the task is created for RM to approve
			 */
			/*
			 * Login as RM & open the task from RM task list 
			 */
			/*
			 * Click on contine & approve the issue 
			 */
			/*
			 * Verify the task is created for Div.M to approve
			 */
/*			Login as  Div.M & open the task 
			Select  Decision as :"Approve"
			Select who to call as :"PM to call customer"
			Enter the notes & employeeid 
			Click on confrim
			Verify call task is generated for PM to call customer 
			Login as PM & open the call task 
			Select any number & call result
			Enter the comments & click on submit calls
			Verify the issue status should be completed
			Verify the approved rent is applied from next month for approved months 
			Click on make payment & verify the promotion is expired correctly*/

		}
		catch(Exception ex){
			ex.printStackTrace();
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
			logger.log(LogStatus.INFO, "Exception",image);
		}
	}
	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag,true);
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseForSilverCustomer_WhenEligiblityisNo_DMoverrideTheApproval" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}

}
