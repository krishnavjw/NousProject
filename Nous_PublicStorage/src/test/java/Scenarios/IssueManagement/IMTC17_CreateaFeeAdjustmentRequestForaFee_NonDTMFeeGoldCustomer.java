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

public class IMTC17_CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer extends Browser_Factory{
	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "IssueManagement","IssueManagement","CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer");
	}
	@Test(dataProvider="getCustSearchData")
	public void CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer", "CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer");
			Reporter.log("Test case started: " +testcaseName, true);

			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}
			/*
			 * Select any Gold customer who is charged abandoned goods fee in last 90 days
			 */
			/*
			 * on customer dashboard select create issue from quick links 
			 */
			/*
			 * Select "Fee adjustment" in issue type MW & click on confirm
			 */
			/*
			 * On the create Fee asjustment issue screen,select any misc fee from the fee listed
			 */
			/*
			 * Enter the explanation & click on create issue 
			 */
			/*
			 * Enter employee id & click on submit
			 */
			/*
			 * Verify  in account activities  Activity "Isue created of type Fee adjustment request" is generated
			 */
			/*
			 * verify the open issue is displayed in important info section on cust dashboard
			 */
			/*
			 * Validate record in the database for created issue 
			 */
			/*
			 * Login as DM & open the task " "Approve issue # Fee adjustment"from  task list  on DM dashboard
			 */
			/*
			 * Click on continue 
			 */
			/*
			 * Select  Decision as :"Decline"
			 */
			/*
			 * Select who to call as :"PM to call customer"
			 */
			/*
			 * Enter the notes & employeeid 
			 */
			/*
			 * Click on confrim
			 */
			/*
			 * Login as PM & open the task "Call customer about issue # fee adjustment" from Task List on PM dashboard
			 */
			/*
			 * Login as PM & open the call task 
			 */
			/*
			 * Select any number & call result
			 */
			/*
			 * Enter the comments & click on submit calls
			 */
			/*
			 * Verify the issue status should be completed
			 */
			/*
			 * Verify the account activity “Adjustment credit” should not be created and also compare the before and after “Balance “  in the account activities. There should be no change in the balance.
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
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_NonDTMFeeGoldCustomer" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}

}
