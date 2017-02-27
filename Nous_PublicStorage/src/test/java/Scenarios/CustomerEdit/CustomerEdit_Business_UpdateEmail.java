package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class CustomerEdit_Business_UpdateEmail extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag = "pass";
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";
	 String scpath="";
	  String image="";

	@DataProvider
	public Object[][] getCustomerEdit() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit", "CustomerEdit_Business_UpdateEmail");
	}

	@Test(dataProvider = "getCustomerEdit")
	public void CustomerEditBusinessEmail(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			// Login to PS Application
			logger = extent.startTest("CustomerEdit_Business_UpdateEmail", "Customer Edit Business - Email");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);

			String biforstNum = Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum + "", true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(5000);

			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(2000);

driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			driver.switchTo().window(tabs.get(0));
			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(10000);
			driver.findElement(By
					.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"))
					.click();
			Thread.sleep(5000);

			PM_Homepage pmhomepage = new PM_Homepage(driver);

			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
			Thread.sleep(5000);

			// Advance search page
			Advance_Search advSearch = new Advance_Search(driver);
			String location = advSearch.getLocationNum();

			  String query = "Select Top 1 A.accountnumber " +
					  "From AccountOrderItem AOI " +
					  "INNER JOIN Site S ON S.SiteID = AOI.SiteID " +
					  "INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID "
					  + "INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID "
					  + "INNER JOIN Account A ON A.AccountID = AO.AccountID " +
					  "join rentalunit ru on ru.rentalunitid=soi.rentalunitid " +
					  "join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "
					  + "Where soi.VacateDate is null " +
					  "and soi.vacatenoticedate is null " + "and s.sitenumber='" +
					  location + "' " + "and soi.StorageOrderItemTypeID=4301 " +
					  "group by  A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate "
					  + "having sum(clt.amount + clt.discountamount)>0 " +
					  "order by 1 ";
					
					 String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
			
			
			
					 advSearch.enterAccNum(AccountNumber);
			
			
			
			//advSearch.enterAccNum(tabledata.get("AccountNumber"));
			logger.log(LogStatus.INFO, "Account number entered successfully :" + AccountNumber);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);
			advSearch.clickButton();
			Thread.sleep(5000);

			// Verify Tabs are present in Customer Account dashboard
			Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);

			if (AccDetails.verify_CustomerInfoTab()) {

				Thread.sleep(2000);

				logger.log(LogStatus.PASS, "Customer Info tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);

				logger.log(LogStatus.FAIL, "Customer Info tab is not displayed");

			}

			if (AccDetails.verify_SpaceDetailsTab()) {

				logger.log(LogStatus.PASS, "Space Details tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);

				logger.log(LogStatus.FAIL, "Space Details tab is not displayed");

			}

			if (AccDetails.verify_AccountActivitiesTab()) {

				logger.log(LogStatus.PASS, "Account Activities tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);

				logger.log(LogStatus.FAIL, "Account Activities tab is not displayed");

			}

			if (AccDetails.verify_DocumentsTab()) {

				logger.log(LogStatus.PASS, "Documents tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);

				logger.log(LogStatus.FAIL, "Documents tab is not displayed");

			}

			Thread.sleep(3000);

			// Getting Email Before Update
			String emailBeforeUpdate = driver
					.findElement(By
							.xpath("//div[@class='emails-container padding-bottom clearfix-container']/div[contains(text(),'Email:')]/following-sibling::div/div/div/span"))
					.getText();

			String snap = Generic_Class.takeScreenShotPath();
			String irm = logger.addScreenCapture(snap);
			logger.log(LogStatus.INFO, "Email Before Update is :" + emailBeforeUpdate);
			logger.log(LogStatus.INFO, "img", irm);

			// div[@class='addresses-container
			// clearfix-container']/div[contains(text(),'
			// Address:')]//following-sibling::div/div/div

			Thread.sleep(3000);

			// Click on Edit Account Details Link
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);
			Cust_AccDetailsPage detailpage = new Cust_AccDetailsPage(driver);

			detailpage.clk_EditAccDetails();
			logger.log(LogStatus.INFO, "Clicked on Edit Account Details Button");
			Thread.sleep(8000);

			// "Edit Account Details" modal window should be displayed

			EditAccountDetails editpage = new EditAccountDetails(driver);

			if (editpage.isEditAccDetailsPopUpDisplayed()) {

				logger.log(LogStatus.PASS, "Edit Account Details page is displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Edit Account Details page is not displayed");

			}

			if (editpage.isSelectWorkflowtxtDisplayed()) {
				logger.log(LogStatus.PASS,
						"Select Workflow list text is dispalyed sucessfully in edit account deatils page");
			} else {
				logger.log(LogStatus.FAIL, "Select Workflow list text is not dispalyed  in edit account deatils page");
			}

			String sn = Generic_Class.takeScreenShotPath();
			String ir = logger.addScreenCapture(sn);
			logger.log(LogStatus.INFO, "img", ir);

			// Click on Customer Information Radio button
			Cust_EditAccountDetailsPage editdetails = new Cust_EditAccountDetailsPage(driver);

			editdetails.clickCustInfoRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Customer Information Radio button");
			Thread.sleep(3000);

			// Verify Yes and No button displayed in the screen
			if (editdetails.isDisplayedYesRadioBtn()) {

				logger.log(LogStatus.PASS, "Yes radio button is displayed");

			} else {

				logger.log(LogStatus.FAIL, "Yes radio button is not displayed");

			}

			if (editdetails.isDisplayedNoRadioBtn()) {

				logger.log(LogStatus.PASS, "No radio button is displayed");

			} else {

				logger.log(LogStatus.FAIL, "No radio button is not displayed");

			}

			// Clicking on Yes radio button and Click Launch button
			editdetails.clickYesRadioBtn();
			Thread.sleep(3000);

			editdetails.clickLaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Yes Radio button and  Launch Button successfully");
			Thread.sleep(6000);

			// Customer Information" screen should be displayed

			if (driver.findElement(By.xpath("//h3[text()='Customer Information']")).isDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Information screen displayed successfully");
				logger.log(LogStatus.INFO, "Customer Information screen", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Information screen displayed ");
				logger.log(LogStatus.INFO, "Customer Information screen displayed ", image);

			}
			
			
			

			driver.findElement(By.xpath("//input[@id='BusinessInformationModel_ContactPosition']")).clear();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//input[@id='BusinessInformationModel_ContactPosition']")).sendKeys("QAManager");
			
			try{
				
				if(driver.findElement(By.xpath("//span[@class='field-validation-valid identWrongFormatMessage wrong-id-format']")).isDisplayed()){
					driver.findElement(By.xpath("//input[@id='BusinessInformationModel_IdentificationNumber']")).clear();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//input[@id='BusinessInformationModel_IdentificationNumber']")).sendKeys("L1234596");
				}
		
			}
			
			
            catch(Exception ex){
					
				}      
			
			
			
			
			String data = Generic_Class.get_RandmString();
		
			String Email=data+"psitqa@gmail.com";
			
			
			
			Cust_CustomerInfoPage infopage = new Cust_CustomerInfoPage(driver);
		

			// Editing details in Email Address field
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			infopage.enterEmail(Email);
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Edited Email  successfully " + Email);
			Thread.sleep(5000);
			
			
			
			
			
			
			
			
			
			
			
			

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);

			//infopage.clk_NoEmailBtn();
			Thread.sleep(3000);
			infopage.clk_verifyBtn();
			Thread.sleep(8000);

			// driver.findElement(By.xpath("//a[text()='Select']")).click();

			infopage.clk_ConfirmBtn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with customer button");
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
			Thread.sleep(6000);

			// Collect Signature and click on Accept button

			String snap1 = Generic_Class.takeScreenShotPath();
			String irm1 = logger.addScreenCapture(snap1);
			logger.log(LogStatus.INFO, "img", irm1);

			WebElement signature = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);
			Action drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);

			driver.findElement(By.xpath("//button[contains(text(),'Accept')]")).click();
			logger.log(LogStatus.INFO, "Clicked on Accept button Successfully");
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);

			Thread.sleep(8000);

			// Click Approve button
			infopage.clk_ApproveBtn();
			Thread.sleep(2000);

			infopage.clk_SaveBtn();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on Approve button and Save button Successfully");

			// Enter Employee Number and click on Ok button
			TransactionCompletePopup transPopup = new TransactionCompletePopup(driver);

			Thread.sleep(3000);
			transPopup.enterEmpNum(tabledata.get("UserName"));

			Thread.sleep(6000);
			transPopup.clickContinueBtn();
			logger.log(LogStatus.PASS, "Entered EmpID and Clicked on Continue button successfully");
			Thread.sleep(10000);

			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(15000);
			}

			catch (Exception ex) {
				ex.printStackTrace();

			}

			String emailAfterUpdate = driver
					.findElement(By
							.xpath("//div[@class='emails-container padding-bottom clearfix-container']/div[contains(text(),'Email:')]/following-sibling::div/div/div/span"))
					.getText();

			Thread.sleep(15000);

			if (emailBeforeUpdate.equals(emailAfterUpdate)) {

				logger.log(LogStatus.FAIL, "Email not Updated  : Email Before Update is :" + emailBeforeUpdate
						+ " Email After update is : " + emailAfterUpdate);
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.PASS,
						"Email  Updated successfully in Customer Dashboard : Email Before Update is :"
								+ emailBeforeUpdate + " Email After update is : " + emailAfterUpdate);
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			Thread.sleep(15000);

			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			cust_accdetails.click_AccountActivities();
			logger.log(LogStatus.INFO, " Clicked on Account Activities Tab successfully");
			Thread.sleep(15000);
			
			
			
			

			 Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
            String strTodaysDate = df.format(cal.getTime());


            
            Thread.sleep(25000);
            
            // Validating Account activities Tab
            
      




if(driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Customer Information Change']")).size()!=0)
{
 
 logger.log(LogStatus.PASS, "Customer Information Change is displayed in Account Activities Tab");

Thread.sleep(4000); 

 scpath=Generic_Class.takeScreenShotPath();
 image=logger.addScreenCapture(scpath);
 
 logger.log(LogStatus.INFO, "Image",image);

}else{

 scpath=Generic_Class.takeScreenShotPath();
 image=logger.addScreenCapture(scpath);
 logger.log(LogStatus.FAIL, "Customer Information Change is not  displayed in Account Activities Tab");
 logger.log(LogStatus.INFO, "Image",image);
 if(resultFlag.equals("pass"))
        resultFlag="fail";
}





driver.findElement(By.xpath("//span[text()='Documents']")).click();
Thread.sleep(25000);

logger.log(LogStatus.INFO, "Clicked on Document tab");



if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Contact Information Changed')]")).size()!=0)
{
     
     logger.log(LogStatus.PASS, "Customer Information Change  is displayed in Document tab");
   
    Thread.sleep(4000); 
    
     scpath=Generic_Class.takeScreenShotPath();
     image=logger.addScreenCapture(scpath);
     
     logger.log(LogStatus.INFO, "Image",image);

}else{

     scpath=Generic_Class.takeScreenShotPath();
     image=logger.addScreenCapture(scpath);
     logger.log(LogStatus.FAIL, "Customer Information Change  is not  displayed in Document tab");
     logger.log(LogStatus.INFO, "Image",image);
     if(resultFlag.equals("pass"))
            resultFlag="fail";
}


		

			// Verify in the customer Dashboard with the database queries

			String sqlQuery = "select em.email from account a "+
                    "join accountemail ae on ae.accountid =  a.accountid "+
                    "join emailaddress em on em.emailaddressid = ae.emailid "+
                    "where a.accountid = '"+AccountNumber+"' and isofficial = 1";
               String dbEmail = DataBase_JDBC.executeSQLQuery(sqlQuery);




			if (Email.equals(dbEmail)) {
				logger.log(LogStatus.PASS, " Email is updated in DB : Updated Email is : " + dbEmail);
			} else {
				logger.log(LogStatus.FAIL, " Email is not updated in DB ");
			}

		} catch (Exception ex) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
			logger.log(LogStatus.FAIL, "------------- page is not displayed"+ ex);
			ex.printStackTrace();
		}

	}
	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Business_UpdateEmail", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Business_UpdateEmail", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Business_UpdateEmail", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

}
