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
import org.openqa.selenium.support.events.EventFiringWebDriver;
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
import Pages.CustDashboardPages.ReversePaymentPage;
import Pages.CustDashboardPages.TransactionReversedPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.Authorized_Access_Contacts;
import Pages.Walkin_Reservation_Lease.EmergencyContactData;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfoPage;
import Pages.Walkin_Reservation_Lease.Leasing_EligiblePromotionsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_RentalFeePage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck extends Browser_Factory{
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments","MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck");
	}

	@Test(dataProvider="getCustSearchData")
	public void MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck", "MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck");
			Reporter.log("Test case started: " +testcaseName, true);


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			//Bifrostpop.clickContiDevice();
			Thread.sleep(10000);

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  

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
			driver.navigate().refresh();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title


			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			pmhomepage.clk_findAndLeaseSpace();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS, "find and Lease a space btn is clicked successfully");

			StandardStoragePage stndstorage = new StandardStoragePage(driver);
			logger.log(LogStatus.INFO, "Standard Storage object created");


			stndstorage.click_SqFt50();
			stndstorage.click_SqFt100();
			stndstorage.click_SqFt150();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Selected desired spaces");

			stndstorage.click_Search();
			logger.log(LogStatus.PASS,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);
			Thread.sleep(10000);

			//============Fetching space number and based on that clicking the radio button========================
			JavascriptExecutor jse = (JavascriptExecutor)driver;		
			List <WebElement> norows=driver.findElements(By.xpath("//div[@id='onsiteUnitGrid']//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr"));
			String space=null;
			if(norows.size()>0){
				Thread.sleep(10000);

				space=driver.findElement(By.xpath("//div[@id='onsiteUnitGrid']//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space,true);
			}else{


				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			//String Space = "4008";
			WebElement RdBtn_Space = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
			Thread.sleep(10000);
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS,"Clicked on check box of a space in this location: " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space,true);
			//generics.Page_ScrollDown();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			//====================================================================================

			SpaceDashboard_ThisLoc SpaceDashboard_ThisLoc = new SpaceDashboard_ThisLoc(driver);
			Thread.sleep(10000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			SpaceDashboard_ThisLoc.click_Rent();
			Thread.sleep(6000);
			logger.log(LogStatus.PASS,"Clicked on Rent button");
			Reporter.log("Clicked on Rent button",true);

			Leasing_ConfirmSpace cnfmSpace= new Leasing_ConfirmSpace(driver);
			Thread.sleep(8000);


			//Add another space
			cnfmSpace.click_AddAnotherSpace();
			//Thread.sleep(10000);
			logger.log(LogStatus.PASS,"Clicked on Add another space  button");

			stndstorage.click_SqFt50();
			stndstorage.click_SqFt100();
			stndstorage.click_SqFt150();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Selected desired spaces");

			stndstorage.click_Search();
			logger.log(LogStatus.PASS,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);


			//====================================================================================
			List <WebElement> norows1=driver.findElements(By.xpath("//div[@class='onsite-list']//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr"));
			String space1=null;
			if(norows1.size()>0){
				Thread.sleep(10000);

				space1=driver.findElement(By.xpath("//div[@class='onsite-list']//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space1,true);
			}else{


				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			//String Space = "4008";
			WebElement RdBtn_Space1 = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space1+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space1); 
			Thread.sleep(10000);
			jse.executeScript("arguments[0].click();", RdBtn_Space1);

			logger.log(LogStatus.PASS,"Clicked on check box of a space in this location: " + space1);
			Reporter.log("Clicked on check box of a space in this location: " + space1,true);
			//generics.Page_ScrollDown();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			//======================================================================================
			Thread.sleep(10000);
			//jse.executeScript("window.scrollBy(0,1000)", "");
			EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
			eventFiringWebDriver.executeScript("document.getElementById('chooseSizeDialog').scrollTop =300");
			//Thread.sleep(5000);
			/*swapVechpage.clk_btn_SwapSpace();
			logger.log(LogStatus.INFO, "clicking on the swap space button");*/
			Thread.sleep(8000);
			driver.findElement(By.xpath("//div[@class='OnSite unit-grid-section clearfix']//a[@id='addSpaceButton']")).click();
			logger.log(LogStatus.PASS,"Clicked on Add space  button");

			Thread.sleep(10000);
			cnfmSpace.click_space_radiobtn1();
			Thread.sleep(1000);
			logger.log(LogStatus.PASS,"selected first space");
			//==============================================================

			cnfmSpace.clk_ConfirmwtCust();
			Thread.sleep(10000);

			logger.log(LogStatus.PASS,"Clicked on confirm with customer button");
			Reporter.log("Clicked on confirm with customer button",true);

			//switching to customer screen
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switching  to customer  screen");
			Reporter.log("Switching  to customer screen",true);
			Thread.sleep(10000);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement confirmBtn=driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']"));
			Thread.sleep(10000);
			confirmBtn.click();
			logger.log(LogStatus.PASS,"Clicked on confirm  button");
			Reporter.log("Clicked on confirm button",true);
			Thread.sleep(10000);

			//switching back to wc2 tab
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Switching back to PM  screen");
			Reporter.log("Switching back to PM  screen",true);

			//Filling contact information

			Leasing_ContactInfoPage contactinfo=new  Leasing_ContactInfoPage(driver);
			Thread.sleep(5000);
			//String FN = "AUT" + generics.get_RandmString();
			contactinfo.txt_Fname(tabledata.get("Firstname"));
			//contactinfo.txt_Fname(FN);
			contactinfo.txt_Lname(tabledata.get("LastName"));
			contactinfo.clickContact_State();
			List<WebElement> allstates= driver.findElements(By.xpath("//ul[@id='ContactForm_Identification_StateTypeID_listbox']/li[@class='k-item']"));

			//Identify the WebElement which will appear after scrolling down


			for(WebElement state: allstates)
			{
				Thread.sleep(2000);
				if(tabledata.get("StateCode").equalsIgnoreCase(state.getText()))
				{
					state.click();
					break;
				}
			}

			//contactinfo.select_State(tabledata.get("StateCode"));
			contactinfo.txt_Number(tabledata.get("DrivingLicenseNum"));
			contactinfo.txt_street1(tabledata.get("Street"));
			contactinfo.txt_city(tabledata.get("City"));

			contactinfo.select_State2address();
			List<WebElement> allstatesadd= driver.findElements(By.xpath("//ul[@id='lesseeinfo-address-statecode_listbox']/li[@class='k-item']"));

			for(WebElement state: allstatesadd)
			{
				Thread.sleep(2000);
				if(tabledata.get("StateCode").equalsIgnoreCase(state.getText()))
				{
					state.click();
					break;
				}
			}

			//contactinfo.select_State2(tabledata.get("State"));
			Thread.sleep(3000);
			contactinfo.txt_Zipcode(tabledata.get("Zipcode"));
			contactinfo.select_phoneType1();
			Thread.sleep(3000);
			/*List<WebElement> phonTypes1= driver.findElements(By.xpath("//ul[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID_listbox')]//li[@class='k-item']"));  //div[@id='ContactForm_LesseePhones[_-index-__0]_Phone_PhoneTypeID-list']
			System.out.println(phonTypes1.size());
			for(WebElement type: phonTypes1)
			{
				System.out.println(type.getText());
				System.out.println(tabledata.get("PhoneType"));
				Thread.sleep(3000);
				if(tabledata.get("PhoneType").contains(type.getText().trim()))
				{
					type.click();
					break;
				}
			}*/
			driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(), '"+tabledata.get("PhoneType")+" ')]")).click();
			Thread.sleep(2000);
			contactinfo.txt_AreaCode(tabledata.get("Areacode"));
			Thread.sleep(2000);
			contactinfo.txt_Exchg(tabledata.get("Exchange"));
			Thread.sleep(2000);
			contactinfo.txt_lineNumber(tabledata.get("LineNumber"));
			Thread.sleep(2000);
			contactinfo.txt_email(tabledata.get("Email"));
			Thread.sleep(2000);
			contactinfo.click_CustLookUp();

			Thread.sleep(15000);
			// Click on New Customer button on Choose an Account PopUp
			driver.findElement(By.linkText("Create New Customer")).click();


			// Select "No" Radio button for Military
			Thread.sleep(8000);
			driver.findElement(By.xpath("//label[@id='currently-military-false']/span[1]")).click(); 

			// Click on Verify button
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("Verify")).click();

			Thread.sleep(8000);
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


			Authorized_Access_Contacts auth= new Authorized_Access_Contacts(driver);
			auth.clk_savebutton();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS,"Clicked on save and proceed button successfully");
			Reporter.log("Clicked on save and proceed button successfully",true);


			//verifying Amount data
			Leasing_EligiblePromotionsPage promo= new Leasing_EligiblePromotionsPage(driver);
			promo.clickSavenProceed();
			logger.log(LogStatus.PASS,"Clicked on save and proceed button successfully");
			Reporter.log("Clicked on save and proceed button successfully",true);
			Thread.sleep(8000);

			Leasing_LeaseQuestionairePage leaseQues= new Leasing_LeaseQuestionairePage(driver);

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

			//Insurance is ON

			Thread.sleep(5000);
			leaseQues.clickAddInsuranceNo();
			logger.log(LogStatus.INFO, "clicked No Insurance");


			Thread.sleep(10000);
			leaseQues.clickAccessZone();

			Thread.sleep(10000);

			List<WebElement> allAccessZone= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID_listbox']//li[@class='k-item']"));
			// String acsZone ="01 - Normal - 24HR";
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
				if(keypadZone.getText().contains("0 - Gate 1"))
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
			Thread.sleep(5000);
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

			Thread.sleep(8000);
			review.clickApprove_btn();

			Thread.sleep(8000);
			review.clickSaveproceed_btn();

			Leasing_RentalFeePage rentalfee= new Leasing_RentalFeePage(driver);

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
			Thread.sleep(2000);
			payment.clickPaymenetList();
			logger.log(LogStatus.INFO,"Clicked on payment clk_PayThroughdropdwn dropdown ");
			Thread.sleep(2000);

			List<WebElement> lst=driver.findElements(By.xpath("//div[@class='k-animation-container']//ul//li"));
			Reporter.log("The size is:"+lst.size(),true);
			int Size = lst.size();
			for (int i = 0; i < Size; i++) {
				if (i == 1) {
					lst.get(i).click();
					break;
				}
			}

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

			Thread.sleep(2000);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS,"Switching back to PM screen");
			Reporter.log("Switching back to PM screen",true);

			// switched back to Main screen

			payment.clickPaymenetList();
			Thread.sleep(3000);

			driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-down']/ul/li[text()='Check']")).click();
			logger.log(LogStatus.PASS,"selected check option option");

			Thread.sleep(6000);
			payment.clickmanualentry();
			logger.log(LogStatus.INFO, "Clicking on Manual entry button successfully");

			payment.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			logger.log(LogStatus.INFO, "Entering routing Number successfully");

			payment.Enter_accountNumber(tabledata.get("CheckAccNum"));
			logger.log(LogStatus.INFO, "Entering Account Number successfully");

			payment.Enter_checkNumber(tabledata.get("CheckNum"));
			logger.log(LogStatus.INFO, "Entering Check Number successfully");

			Thread.sleep(5000);
			String amount=driver.findElement(By.xpath("//div[text()='Total Remaining']/following-sibling::div[@ class='payment-row__now']")).getText();

			Thread.sleep(5000);
			payment.enterAmount(amount);

			//payment.Enter_checkAmount(tabledata.get("CheckAmount"));
			logger.log(LogStatus.INFO, "Entering Check amount successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(6000);
			payment.Select_autopaycheckbox();
			logger.log(LogStatus.INFO, "Select Auto pay enable checkbox successfully");

			payment.clickApply_btn();
			Thread.sleep(5000);

			Thread.sleep(6000);
			payment.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect signature button successfully");

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

			driver.findElement(By.id("employeeNumber")).sendKeys("930326");
			Thread.sleep(2000);
			driver.findElement(By.partialLinkText("Ok")).click();
			Thread.sleep(2000);


			Thread.sleep(5000);
			if(driver.findElement(By.xpath("//a[contains(text(),'No')]")).isDisplayed())
			{
				driver.findElement(By.xpath("//a[contains(text(),'No')]")).click();
			}else if(driver.findElement(By.xpath("//a[contains(text(),'Ok')]")).isDisplayed())
			{
				driver.findElement(By.xpath("//a[contains(text(),'Ok')]")).click();

			}

			Thread.sleep(15000);

			pmhomepage.clk_AdvSearchLnk();


			Thread.sleep(8000);
			Advance_Search advSearch= new Advance_Search(driver);

			String sqlQuery="select accountid from account where customerid =(select customerid from customer where contactid = (select contactid from contact where firstname = '"+tabledata.get("FirstName")+"' and lastname = '"+tabledata.get("LastName")+"'))";

							String accNUm=DataBase_JDBC.executeSQLQuery(sqlQuery);

							advSearch.enterAccNum(accNUm);
							
			 
			//advSearch.enterAccNum("45226383");
			 Thread.sleep(5000);
			 logger.log(LogStatus.INFO, "Entered acc num successfully");
			 advSearch.clickSearchAccbtn();
			 logger.log(LogStatus.INFO, "Click on search button successfully");
			 
			 Thread.sleep(8000);
			 
			 Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
				String toatlDueNowAmtAfterMakePymt=cust_accdetails.getTotalDueNow();
				logger.log(LogStatus.PASS, "Total due now amount after making payment in customer dashbaord is:"+toatlDueNowAmtAfterMakePymt);

				Thread.sleep(1000);
				//String nextPymtDueAmtAftermakePymt=cust_accdetails.get_NextPymtDueAmount();
				//logger.log(LogStatus.PASS, "Next Payment due amount after making payment is:"+nextPymtDueAmtAftermakePymt);
			 
			 
			 cust_accdetails.clk_Acc_ActivitiesTab();
			 logger.log(LogStatus.INFO, "Click on Account activities tab successfully");
			 Thread.sleep(10000);
			 
			 cust_accdetails.clk_CheckExpandLink();
				logger.log(LogStatus.INFO, "Clicked on expand button in first row");

				Thread.sleep(6000);

				cust_accdetails.clk_ReversePaymntLnk();
				logger.log(LogStatus.INFO, "Clicked on Reverse Payment link");

				Thread.sleep(8000);
				ReversePaymentPage ReversePayment = new ReversePaymentPage(driver);
				boolean rev=driver.findElement(By.xpath("//h3[contains(text(),'Reverse Payment')]")).isDisplayed();
				if(rev){
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Reverse Payment screen is displayed successfully");
					logger.log(LogStatus.INFO, "Reverse Payment screen is displayed successfully",image);
				}else{

					if(resultFlag.equals("pass"))
						resultFlag="fail";

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Reverse Payment screen is not displayed ");
					logger.log(LogStatus.INFO, "Reverse Payment screen is not displayed ",image);

				}

				//ReversePayment.Clk_RdBtn_Yes();
				//logger.log(LogStatus.INFO, "Clicked on No radio button");

				ReversePayment.clickReason();
				logger.log(LogStatus.INFO, "Clicked on Reason drop down");

				//ReversePayment.SelectValueFromReasonList("Misapplied Payment");
				ReversePayment.SelectValueFromReasonListeason();

				logger.log(LogStatus.INFO, "Select value from Reason dropdown");


				ReversePayment.enter_Note("PM received a Bad check from bank for a customer payment so reversing the payment");
				logger.log(LogStatus.INFO, "Entered note");

				ReversePayment.click_ReverseBtn();
				logger.log(LogStatus.INFO, "Clicked on Reverse button");

				Thread.sleep(6000);

				TransactionReversedPage trnspage=new TransactionReversedPage(driver);
				trnspage.enter_EmployeeId(tabledata.get("UserName"));
				logger.log(LogStatus.INFO, "Entered EmployeeID");

				Thread.sleep(3000);
				trnspage.click_OkBtn();
				logger.log(LogStatus.INFO, "Clicked on OK button");

				Thread.sleep(6000);
				if(cust_accdetails.isCustdbTitleDisplayed()){

					logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");

				}else{

					if(resultFlag.equals("pass"))
						resultFlag="fail";

					logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");

				}


				Thread.sleep(1000);
				String toatlDueNowAmtAfterReversePymt=cust_accdetails.getTotalDueNow();
				logger.log(LogStatus.PASS, "Total due now amount after reverse payment in customer dashbaord is:"+toatlDueNowAmtAfterReversePymt);


				Thread.sleep(1000);
				//String nextPymtDueAmtAfterReversePymt=cust_accdetails.get_NextPymtDueAmount();
				//logger.log(LogStatus.PASS, "Next Payment due amount after reverse payment is:"+nextPymtDueAmtAfterReversePymt);

				/*if(toatlDueNowAmtAfterReversePymt.equals(toatlDueNowAmtBeforeMakePymt)){

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Total due now amount before making and after reversing the payment is same and changes data are reflecting properly in Customer Dashboard sucessfully");
					logger.log(LogStatus.INFO, "Total due now amount before making and after reversing the payment is same and changes data are reflecting properly in Customer Dashboard sucessfully",image);

				}else{

					if(resultFlag.equals("pass"))
						resultFlag="fail";

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Total due now amount before making and after reversing the payment is not same and changes data are not reflecting properly in Customer Dashboard ");
					logger.log(LogStatus.INFO, "Total due now amount before making and after reversing the payment is not same and changes data are not reflecting properly in Customer Dashboard ",image);

				}*/

			 
			 
			 





		}
		catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			Reporter.log("Exception ex: " + ex,true);
			logger.log(LogStatus.FAIL,"Test Script fail due to exception");
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_Individual_NewCustomer_MultipleSpaces_paythroughCheck" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}

