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

public class IMTC12_VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline extends Browser_Factory{
	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "IssueManagement","IssueManagement","VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline");
	}
	@Test(dataProvider="getCustSearchData")
	public void VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline", "VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline");
			Reporter.log("Test case started: " +testcaseName, true);

			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}
			/*
			 * Select any silver /gold  customer who is in DTM lien stage
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
			 * Verify the eligibility is No
			 */
			/*
			 * Click on continue 
			 */
			/*
			 * Select  Decision as "Decline"
			 */
			/*
			 * Select who to call as :"Appropver to call customer"
			 */
			/*
			 * Enter the notes & employeeid 
			 */
			/*
			 * Click on confirm
			 */
			/*
			 * Verify call task is generated for DM to call customer 
			 */
			/*
			 * Login as DM & open the call task 
			 */
			/*
			 * Verify "Record calls(s) made section is displayed
			 */
			/*
			 * Select  phone number 
			 */
			/*
			 * select call result as "Reached" and enter any comments
			 */
			/*
			 * Click on submit calls
			 */
			/*
			 * Verify the task change to completed status
			 */
			/*
			 * Verify no promotion should be applied
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
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","VerifyRentRateRateIncreaseforSilverorGoldcustomer_DTMLienStage_DMDecline" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}

}
