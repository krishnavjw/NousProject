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

public class IMTC05_ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO extends Browser_Factory{
	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "IssueManagement","IssueManagement","ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO");
	}
	@Test(dataProvider="getCustSearchData")
	public void ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO", "ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO");
			Reporter.log("Test case started: " +testcaseName, true);

			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}
			/*
			 * Search for bronze customer
			 */
			/*
			 * on customer dashboard select create issue from quick links 
			 */
			/*
			 * Select "Move out billing dispute" in issue type MW & click on confirm
			 */
			/*
			 * Select type of dispute as " unused rent in current month"
			 */
			/*
			 * Enter the explanation & click on create issue 
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
			 * Verify the task is not generated for DM  approval
			 */
			/*
			 * Verify the call task is generated for PM to call customer
			 */

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
			Excel.setCellValBasedOnTcname(path, "IssueManagement","ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","ValidateMoveOutBillingDisputeOnUnusedRentForBronzeCustomer_WhenEligibilityisNO" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}
}
