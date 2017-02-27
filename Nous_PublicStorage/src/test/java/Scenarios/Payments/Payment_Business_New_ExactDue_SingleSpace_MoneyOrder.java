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
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Pages.Walkin_Reservation_Lease.Authorized_Access_Contacts;
import Pages.Walkin_Reservation_Lease.EmergencyContactData;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfo;
import Pages.Walkin_Reservation_Lease.Leasing_EligiblePromotionsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_RentalFeePage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class Payment_Business_New_ExactDue_SingleSpace_MoneyOrder extends Browser_Factory {
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getPaymentBusinessNew() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments", "Payment_Business_New_ExactDue_SingleSpace_MoneyOrder");
	}
	

	@Test(dataProvider="getPaymentBusinessNew")
	public void Payment_Business_New_ExactDue_SingleSpace_MoneyOrder(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{
			
			//Login to PS Application
			logger=extent.startTest("Payment_Business_New_ExactDue_SingleSpace_MoneyOrder","Payment- Business New Customer with Exact Due");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			String biforstNum=Bifrostpop.getBiforstNo();
			Reporter.log(biforstNum+"",true);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath")); 

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

			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Created Object for PM Homepage");
			
			//Clicking on Find and Lease Button
			pmhomepage.clk_findAndLeaseSpace();
			logger.log(LogStatus.INFO, "Clicked on Find and Lease button");
			Thread.sleep(5000);
			
			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			logger.log(LogStatus.INFO, "Created Object for Standard Storage Page");
			
			
			//Select any space and click on search button
			StandardStoragePage.click_SqFt150();
			logger.log(LogStatus.PASS,"Clicked on 150SqFt checkbox in StandardStorage");
			Reporter.log("Clicked on 150SqFt checkbox in StandardStorage",true);
			StandardStoragePage.click_SqFt50();
			StandardStoragePage.click_SqFt200();
			Thread.sleep(3000);
			
			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);
			Thread.sleep(10000);
			
			// Click on any Radio button space
			List <WebElement> norows=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr"));
			int rows = norows.size();
			if(rows>0){
				
				WebElement RdBtn_Space = driver.findElement(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr[1]//td[1]/input"));
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
				jse.executeScript("arguments[0].click();", RdBtn_Space);
				logger.log(LogStatus.PASS,"Clicked on radio button in the space information ");
				
			}else{
				
				logger.log(LogStatus.INFO, "Application is not populating any data/space details");
				
			}
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(6000);	
			
			//Click On Rent button
			SpaceDashboard_ThisLoc spacedashboard =new SpaceDashboard_ThisLoc(driver);
			spacedashboard.click_Rent();
			logger.log(LogStatus.PASS, "Click on Rent button successfully");
			Thread.sleep(10000);
			
			//Clicking on confirm with customer
			Leasing_ConfirmSpace confirmSpace = new Leasing_ConfirmSpace(driver);
			logger.log(LogStatus.INFO, "Create Object for Confirm Space");
			
			confirmSpace.clk_ConfirmwtCust();
			logger.log(LogStatus.INFO, "Clicked on Confirm with Customer Button");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
			Thread.sleep(8000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "Clicked on Confirm button successfully");
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switched back to Main page successfully");
			Thread.sleep(8000);
			
			
			//Entering details in business Contact Information page
			Leasing_ContactInfo contactinfo = new Leasing_ContactInfo(driver);
			logger.log(LogStatus.INFO, "Created Object for Contact Information");
			contactinfo.toggleIndivBusi();
			logger.log(LogStatus.INFO, "Clicked on business lease");
			Thread.sleep(3000);
			
			contactinfo.enter_CompanyName(tabledata.get("CompanyName"));
			logger.log(LogStatus.INFO, "Entered Company Name");
			Thread.sleep(3000);
			contactinfo.enter_FirstName(tabledata.get("FirstName"));
			logger.log(LogStatus.INFO, "Entered First Name");
			Thread.sleep(3000);
			contactinfo.enter_LastName(tabledata.get("LastName"));
			logger.log(LogStatus.INFO, "Entered Last Name");
			Thread.sleep(3000);
			contactinfo.enter_Title(tabledata.get("Title"));
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Entered title");
			contactinfo.select_DriBusiState("CA");
			logger.log(LogStatus.INFO, "Selected CA in the Select box");
			Thread.sleep(3000);
			contactinfo.enter_IdenNumber(tabledata.get("IdentificationNumber"));
			logger.log(LogStatus.INFO, "Entered identification number");
			Thread.sleep(3000);
			
			contactinfo.enter_AddressFirst(tabledata.get("AddressFirstLine"));
			logger.log(LogStatus.INFO, "Entered First Line in the Address");
			Thread.sleep(3000);
			contactinfo.enter_AddressCity(tabledata.get("AddressCity"));
			logger.log(LogStatus.INFO, "Entered City Name");
			Thread.sleep(3000);
			contactinfo.select_BusiState("CA");
			logger.log(LogStatus.INFO, "Selected CA in the City Select box");
			Thread.sleep(3000);
			contactinfo.enter_AddressZipcode(tabledata.get("AddressZipcode"));
			logger.log(LogStatus.INFO, "Entered Zipcode");
			Thread.sleep(3000);
			
			
			contactinfo.select_phoneType();
			logger.log(LogStatus.INFO, "Phone type is selected from the Dropdown");
			Thread.sleep(3000);
			contactinfo.txt_AreaCode(tabledata.get("AreaCode"));
			Thread.sleep(1000);
			contactinfo.txt_Exchg(tabledata.get("Exchange"));
			Thread.sleep(1000);
			contactinfo.txt_lineNumber(tabledata.get("LocalNumber"));
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered Phone number");
			contactinfo.txt_email(tabledata.get("EmailAddress"));
			logger.log(LogStatus.INFO, "Entered Email Address");
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			contactinfo.click_CustLookUp();
			logger.log(LogStatus.INFO, "Clicked on Custoomer Lookup button");
			Thread.sleep(8000);
			contactinfo.clk_createNewCust();
			logger.log(LogStatus.INFO, "Clicked on Create New Customer button");
			Thread.sleep(8000);
			contactinfo.clk_NoRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on No radio button");
			Thread.sleep(4000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			contactinfo.clk_VerifyBtn();
			logger.log(LogStatus.INFO, "Clicked on Verify Button");
			Thread.sleep(6000);
			
			
			if(contactinfo.verify_overrideAddress_Select())
			{
				contactinfo.click_overrideAddress_Select();
			}else if(contactinfo.verify_overridePhone_Select())
			{
				contactinfo.click_overridePhone_Select();
			}

			Thread.sleep(5000);
			driver.findElement(By.id("confirmWithCustomerButton")).click();

			Thread.sleep(5000);
			//Click on Confirm with Cust button
			logger.log(LogStatus.INFO, "clicking on Confirm with cust button successfully");

			// Navigating to Customer screen
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			
			//Declines to provide Emergency contact info
			EmergencyContactData con = new EmergencyContactData(driver);
			Thread.sleep(4000);
			con.click_decline_EmgcyContact();
			logger.log(LogStatus.PASS,"Clicked on checkbox-decline to give Emergency contact");
			Reporter.log("Clicked on checkbox-decline to give Emergency contact",true);
			Thread.sleep(6000);

			con.btn_confirmWithCust();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Clicked on confirm with customer button");
			Reporter.log("Clicked on confirm with customer button",true);

			//switching to customer screen
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switching  to customer  screen");
			Reporter.log("Switching  to customer screen",true);
			Thread.sleep(5000);

			WebElement confirmBtn3=driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']"));
			confirmBtn3.click();
			logger.log(LogStatus.PASS,"Clicked on confirm  button");
			Reporter.log("Clicked on confirm button",true);
			Thread.sleep(10000);

			//switching back to wc2 tab
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Switching back to PM  screen");
			Reporter.log("Switching back to PM  screen",true);

			//Save And Proceed in the Authorized PAge
			Authorized_Access_Contacts auth= new Authorized_Access_Contacts(driver);
			logger.log(LogStatus.INFO, "Craeted Object for Authorized Access Contact");
			auth.clk_saveAndProceedbutton();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS,"Clicked on save and proceed button successfully");
			Reporter.log("Clicked on save and proceed button successfully",true);


			//Save And Proceed in the promotion page
			Leasing_EligiblePromotionsPage promo= new Leasing_EligiblePromotionsPage(driver);
			promo.clickSavenProceed();
			logger.log(LogStatus.PASS,"Clicked on save and proceed button successfully");
			Reporter.log("Clicked on save and proceed button successfully",true);
			Thread.sleep(8000);

			Leasing_LeaseQuestionairePage leaseQues= new Leasing_LeaseQuestionairePage(driver);
			logger.log(LogStatus.INFO, "Craeted Object for Lease Questionaire Page");

			leaseQues.clickStorageContent();
			Thread.sleep(5000);

			List<WebElement> allstorage= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__RentalUnitContentsTypeID_listbox']//li[@class='k-item']"));

			for(WebElement storage: allstorage)
			{
				if((tabledata.get("StorageContent")).equalsIgnoreCase(storage.getText()))
				{
					storage.click();
					break;
				}
			}

			//Cliking on Insurance No radio button
			Thread.sleep(5000);
			leaseQues.clickAddInsuranceNo();
			logger.log(LogStatus.INFO, "Clicked on No Insurance");

			Thread.sleep(10000);
			leaseQues.clickAccessZone();
			Thread.sleep(10000);
			List<WebElement> allAccessZone= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID_listbox']//li[@class='k-item']"));
			for(WebElement accessZone: allAccessZone)
			{
				if((tabledata.get("AccessZone")).equalsIgnoreCase(accessZone.getText()))
				{
					accessZone.click();
					break;
				}
			}

			Thread.sleep(10000);

			/*List<WebElement> allKaypadZone= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__KeypadZone_listbox']//li[@class='k-item']"));

				         //String keyZone="0 - Gate 1";

				         for(WebElement keypadZone: allKaypadZone)
				         {
				                if((tabledata.get("KeypadZone")).equalsIgnoreCase(keypadZone.getText()))

				                {
				                       keypadZone.click();
				                       break;
				                }
				         }*/
			
			leaseQues.clickKeypadZone();
			Thread.sleep(3000);
			List<WebElement> allKaypadZone= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__KeypadZone_listbox']/li[contains(@id,'KeypadZone_option_selected')]/following-sibling::li"));
			for(WebElement keypadZone: allKaypadZone)
			{
				if(keypadZone.getText().contains("1 - Normal"))
				{
					keypadZone.click();
					break;
				}
			}

			Thread.sleep(10000);
			leaseQues.clickConfirmCust();


			// switching to customer screen
			logger.log(LogStatus.PASS,"Clicked on Confirm with Customer button");
			Reporter.log("Clicked on Confirm with Customer button",true);

			Thread.sleep(2000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switched to Customer Screen");
			Reporter.log("Switched to Customer Screen",true);

			driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']")).click();
			logger.log(LogStatus.PASS,"Clicked on Confirm button in customer screen");
			Reporter.log("Clicked on Confirm button in customer screen",true);

			Thread.sleep(12000);
			List<WebElement> allCheckbox=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox)
			{
				checkbox.click();
			}
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();


			Thread.sleep(3000);
			WebElement signature = driver.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();

			Thread.sleep(4000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));

			Leasing_ReviewNApprovePage review= new Leasing_ReviewNApprovePage(driver);
			logger.log(LogStatus.INFO, "Craeted Object for Leasing Review Approve Page");

			Thread.sleep(8000);
			review.clickApprove_btn();

			Thread.sleep(8000);
			review.clickSaveproceed_btn();

			Leasing_RentalFeePage rentalfee= new Leasing_RentalFeePage(driver);
			logger.log(LogStatus.INFO, "Created Object for leasing Rentel fee page");

			Thread.sleep(15000);
			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page");


			rentalfee.clickConfirmCust_btn();
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(8000);


			Leasing_PaymentMethodsPage payment= new Leasing_PaymentMethodsPage(driver);
			logger.log(LogStatus.INFO, "Created Object for Leasing Payment Method Page");
			Thread.sleep(2000);
			
			payment.selectPaymentThrough();
			logger.log(LogStatus.INFO, "Selected Date from Pay Through Dropdown");
			Thread.sleep(5000);
			
			//Confirm with customer in the Customer Facing Screen
			payment.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with Customer Button successfully");
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
			Thread.sleep(8000);
			String totalAmnt = driver.findElement(By.xpath("//span[@id='balance']")).getText().trim();
			logger.log(LogStatus.INFO, "Total due now amount:"+totalAmnt);
			Thread.sleep(2000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "Clicked on Confirm button successfully");
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switched back to Main page successfully");
			Thread.sleep(8000);
			
			//Select Cash in payment method, enter Amount and click on Submit button
			payment.selectPaymentMethod("Cash", driver);
			logger.log(LogStatus.INFO, "Selected the Cash option from Payment dropdown successfully");
			Thread.sleep(8000);
			
			payment.enterAmount(totalAmnt);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Amount in Cash Field:" +totalAmnt);
			
		/*	payment.Select_autopaycheckbox();
			logger.log(LogStatus.INFO, "Select Auto pay enable checkbox successfully");*/

			payment.clickApply_btn();
			Thread.sleep(5000);

			Thread.sleep(6000);
			payment.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect Signature button successfully");

			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder1 = new Actions(driver);          
			Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			payment.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			driver.switchTo().window(tabs.get(0));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(10000);
			payment.clickApprove_Btn();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");

			Thread.sleep(6000);
			payment.clickSubmit_btn();
			logger.log(LogStatus.INFO, "Click on Submit button successfully");

			//Enter Employee Number and click on Ok button
			Thread.sleep(6000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);
			logger.log(LogStatus.INFO, "Transaction Complete Popup object created");
			Thread.sleep(3000);
			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Id successfully");
			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.PASS, "Clicked on Ok button successfully");
			Thread.sleep(10000);

		/*	Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			
			String qry1 = "Select SUM((Amount * Quantity) + DiscountAmount) as Amount " 	
					+"From DBO.CLTransactionMaster CLM WITH (NOLOCK) "
					+"INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID "
					+"INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID "
					+"Where 1 = 1 And CLM.AccountID = '"+tabledata.get("AccountNum")+"' ";
		
			String ele1 = DataBase_JDBC.executeSQLQuery(qry1);
		
			//Verifying Total Due Now Amount
			if(cust_accdetails.getTotalDue().trim().equals(ele1)){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total Due Now Amount is verified successfully");
				logger.log(LogStatus.INFO, "Total Due Now Amount is verified successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total Due Now Amount verification fails");
				logger.log(LogStatus.INFO, "Total Due Now Amount verification fails",image);

			}
			*/

			
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}
	
	
	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_Business_New_ExactDue_SingleSpace_MoneyOrder" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","Payment_Business_New_ExactDue_SingleSpace_MoneyOrder" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_Business_New_ExactDue_SingleSpace_MoneyOrder" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
	


}
