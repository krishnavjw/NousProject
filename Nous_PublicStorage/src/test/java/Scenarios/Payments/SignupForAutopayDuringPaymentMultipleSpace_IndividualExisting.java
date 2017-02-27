package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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

import com.gargoylesoftware.htmlunit.javascript.host.BroadcastChannel;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.AutoPayPages.StopAutoPayPopupPage;
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments", "SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting");
	}


	@Test(dataProvider="getData")
	public void SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting","Signup For Autopay During Payment One Space");

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			String siteNumber=login.get_SiteNumber();
			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
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

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);


			PM_Homepage hp= new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Home Page object is created successfully");

			hp.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "clicking on Advance Search link successfully");
			Thread.sleep(5000);

			Advance_Search advSearch= new Advance_Search(driver);

			String query = "Select  distinct top 1 A.Accountnumber from Account A"
					+ " join Accountorder AO on AO.Accountid = A.Accountid"
					+ " join AccountOrderItem AOI on AOI.AccountOrderID = AO.AccountOrderID"
					+ " join AutoPay AP on AP.StorageOrderItemID = AOI.StorageOrderItemID"
					+ " join site s on s.siteid=AOI.siteId"
					+ " join CLTransaction CLT on CLT.accountorderitemid=AOI.accountorderitemId"
					+ " join customer cu on cu.customerid=a.customerid" 
					//+ " join accountcheck ac on ac.accountcheckid=ap.accountcheckid -- check"
					//--join Accountcreditcard accc on accc.accountcreditcardid=ap.accountcreditcardid -- Credit card
					+ " Where AP. IsActive = 1 --AutoPay Status OFF"
					+ " and s.sitenumber='"+siteNumber+"'" 
					+ " and CLT.amount>0.00" 
					+ " and cu.customertypeid=90 -- for individual"
					//--and cu.customertypeid=91--for Business"
					// --AP. IsActive = 1 --AutoPay Status OFF
					+ " order by a.accountnumber desc";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			advSearch.enterAccNum(accNum);

			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully");

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicked on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer Account Dashboard displayed successfully for an Individual Customer");
			logger.log(LogStatus.INFO, "Image",image);

			if(cust_accdetails.verifyTotalDueSection())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total Due Section is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Total Due Section is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total Due Section is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Total Due Section is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyNextPaymentDueSection())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Next Payment Due Section is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Next Payment Due Section is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Next Payment Due Section is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Next Payment Due Section is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyMakePaymentLink())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Make Payments link is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Make Payments link is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Make Payments link is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Make Payments link is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyViewDetailsLink())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "View Details link is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "View Details link is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "View Details link is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "View Details link is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}


			if(cust_accdetails.verifyManageAutopayLink())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Manage Autopay link is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Manage Autopay link is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Manage Autopay link is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Manage Autopay link is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyImportantInformationSection())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Important Information Section is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Important Information Section is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Important Information Section is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Important Information Section is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyNoteButton())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Notes is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Notes link is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Notes is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Notes is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyCustomerInfoTab())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Info Tab is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Customer Info Tab is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Info Tab is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Customer Info Tab is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifySpaceDetailsTab())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Space Details tab is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Space Details tab is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Space Details tab is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Space Details tab is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verifyAccountActivitiesTab())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account Activities tab is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Account Activities tab is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account Activities tab is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Account Activities tab is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(cust_accdetails.verify_DocumentsTab())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Documents tab is Displayed in Customer Dashboard");
				logger.log(LogStatus.PASS, "Documents tab is Displayed in Customer Dashboard",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Documents tab is not Displayed in Customer Dashboard");
				logger.log(LogStatus.FAIL, "Documents tab is not Displayed in Customer Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.INFO, "clicking on Make Payment button successfully");
			Thread.sleep(20000);

			PaymentsPage payments= new PaymentsPage(driver);
			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "clicked on Confirm with customer button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			Thread.sleep(3000);
			payments.selectPaymentMethod("Check", driver);
			logger.log(LogStatus.INFO, "Select the Check option from Payment dropdown successfully");

			Thread.sleep(6000);
			payments.clickmanualentry();
			logger.log(LogStatus.INFO, "Clicking on Manual entry button successfully");

			payments.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			logger.log(LogStatus.INFO, "Entering routing Number successfully");

			payments.Enter_accountNumber(tabledata.get("CheckAccNum"));
			logger.log(LogStatus.INFO, "Entering Account Number successfully");

			payments.Enter_checkNumber(tabledata.get("CheckNum"));
			logger.log(LogStatus.INFO, "Entering Check Number successfully");

			payments.Enter_checkAmount(tabledata.get("CheckAmount"));
			logger.log(LogStatus.INFO, "Entering Check amount successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(6000);
			payments.Select_autopaycheckbox();
			logger.log(LogStatus.INFO, "Select Auto pay enable checkbox successfully");

			Thread.sleep(6000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			Thread.sleep(6000);
			payments.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect signature button successfully");

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			payments.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");
			logger.log(LogStatus.PASS, "Payment made using check");

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payments.clickApprove_Btn();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");

			Thread.sleep(6000);
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Click on Submit button successfully");

			Thread.sleep(5000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);

			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter Employee Id  successfully");

			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.INFO, "Click on Ok button successfully");

			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details Tab successfully");

			Thread.sleep(6000);
			Acc_SpaceDetailsPage spaceTab=new Acc_SpaceDetailsPage(driver);

			if(spaceTab.verifyAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Status is ON in Space Details Tab");
				logger.log(LogStatus.PASS, "Autopay Status is ON in Space Details Tab",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay Status is NOT ON in Space Details Tab");
				logger.log(LogStatus.FAIL, "Autopay Status is NOT ON in Space Details Tab",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}


			String dbQuery ="select ea.email,ad.addressline1,ad.city,ad.statecode,ad.postalcode,ci.last4  from account a "
					+" join accountaddress aa on a.accountid = aa.accountid"
					+" join customer cu on cu.customerid=a.customerid"
					+" join contactidentification ci on ci.contactid=cu.contactid"
					+" join emailaddress ea on ea.contactid=cu.contactid"
					+" join address ad on ad.addressid=aa.addressid"
					+" where a.accountnumber ='"+accNum+"'";

			ArrayList<String> DB_Data =  DataBase_JDBC.executeSQLQuery_List(dbQuery);
			Thread.sleep(8000);

			Acc_CustomerInfoPage custInfo = new Acc_CustomerInfoPage(driver); 
			custInfo.clk_CustomerInfoTab();
			Thread.sleep(6000);
			String addressUI=custInfo.getAddress();
			String emailIdUI=custInfo.getEmailId();
			String identificationUI=custInfo.getIdentificationId();
			
			String addLine1=DB_Data.get(1);
			String addLine2=DB_Data.get(2)+", "+DB_Data.get(3)+" "+DB_Data.get(4);
			

			if(emailIdUI.contains(DB_Data.get(0))&addressUI.contains(addLine1)&addressUI.contains(addLine2)&identificationUI.contains(DB_Data.get(5))){

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,"Customer Info Details are matching with UI and DB");
				logger.log(LogStatus.PASS, "Customer Info Details are matching with UI and DB",image);
				logger.log(LogStatus.PASS, "Expected Values: Email-" + emailIdUI + " Expected Values: Address-" + addressUI );
				logger.log(LogStatus.PASS, "Actual Values: Email-" + DB_Data.get(0) + " Actual Values: Address-" + addLine1 + addLine2 +" Actual Values: identification-" + DB_Data.get(5));

			}
			else
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,"Customer Info Details are not matching with UI and DB");
				logger.log(LogStatus.FAIL,"Customer Info Details are not matching with UI and DB",image);
				logger.log(LogStatus.FAIL, "Expected Values: Email-" + emailIdUI + " Expected Values: Address-" + addressUI );
				logger.log(LogStatus.FAIL, "Actual Values: Email-" + DB_Data.get(0) + " Actual Values: Address-" + addLine1 + addLine2 +" Actual Values: identification-" + DB_Data.get(5));
			}
			Thread.sleep(8000);

			

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
		}

	}       


	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"Payments","SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"Payments","SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","SignupForAutopayDuringPaymentMultipleSpace_IndividualExisting" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
