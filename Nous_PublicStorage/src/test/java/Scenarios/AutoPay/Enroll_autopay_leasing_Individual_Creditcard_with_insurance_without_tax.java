package Scenarios.AutoPay;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.AutoPayPages.EmployeeIDPopUpPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.Leasing_AuthorizedAccessContactsPage;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfoPage;
import Pages.Walkin_Reservation_Lease.Leasing_EligiblePromotionsPage;
import Pages.Walkin_Reservation_Lease.Leasing_EmergencyConatctsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_RentalFeePage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Pages.Walkin_Reservation_Lease.Leasing_TransactionCompletePage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{
		//String path="./src/main/resources/Resources/PS_TestData.xlsx";

		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay", "Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax");
	}


	@Test(dataProvider="getLoginData")
	public void Enroll_autopay_leasing_Individual_Creditcard_with_ins_without_tax(Hashtable<String, String> tabledata) throws InterruptedException
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax","Enroll Into Autopay Thru Leasing _Individual_WithInsurance_WithoutTax_Check");

			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");


			Generic_Class generics = new Generic_Class();
			driver.manage().timeouts().implicitlyWait(6000, TimeUnit.SECONDS);
			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);
			Thread.sleep(8000);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(6000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.get(generics.getPropertyValue("CustomerScreenPath_QA"));  

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

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));

			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			// Login into PM dashboard 
			PM_Homepage pm_home= new  PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");

			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigate to PM dashboard");
			logger.log(LogStatus.INFO, "Image",image);


			Thread.sleep(8000);
			pm_home.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS,"Clicked on Find And Lease A Space");

			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			Thread.sleep(8000);
			//StandardStoragePage.Clk_ChkBx_AvlSpace();

			List<WebElement> avaSpaecs= driver.findElements(By.xpath("//div[@id='select-space-size']//span[@class='leftalignedinlineblockspan dimensions']/../../preceding-sibling::span"));

			for(WebElement space: avaSpaecs)
			{
				space.click();
			}

			logger.log(LogStatus.PASS,"Clicked available space in StandardStorage");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Selected available spaces");
			logger.log(LogStatus.INFO, "Image",image);


			Thread.sleep(4000);
			StandardStoragePage.click_Search();
			logger.log(LogStatus.PASS,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);

			//selecting one space from grid
			SpaceDashboard_ThisLoc thisSpace= new SpaceDashboard_ThisLoc(driver);
			Thread.sleep(8000);
			//		space.select_GivenSpace(tabledata.get("SpaceNumber"));
			//		logger.log(LogStatus.PASS,"Clicked on radio button-to select one space");
			//		Reporter.log("Clicked on radio button-to select one space",true);

			//============Fetching space number and based on that clicking the radio button========================
			List <WebElement> norows=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']//table//tbody//tr"));
			String avlSpace=null;
			if(norows.size()>0){
				Thread.sleep(5000);

				avlSpace=driver.findElement(By.xpath("//div[@id='onsiteUnitGrid']//table/tbody/tr[1]//td[@class='grid-cell-space']")).getText();
				Reporter.log("space number is:"+avlSpace,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}


			WebElement RdBtn_Space = driver.findElement(By.xpath("//div[@id='onsiteUnitGrid']//table//tbody//tr//td[text()='"+avlSpace+"']/preceding-sibling::td//input"));

			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS,"Clicked on radio button based on the space no: " + avlSpace);
			Reporter.log("Clicked on radio button  based on the space no: " + avlSpace,true);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");


			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			logger.log(LogStatus.INFO, "check the radio button based on the space and click on the  reservation button",image);

			Thread.sleep(5000);
			thisSpace.click_Rent();
			logger.log(LogStatus.PASS,"Clicked on Rent button");
			Reporter.log("Clicked on Rent button",true);

			Leasing_ConfirmSpace confirmSpace= new Leasing_ConfirmSpace(driver);
			Thread.sleep(8000);
			/*if(!driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).isDisplayed()){

				logger.log(LogStatus.INFO, "Bifrost Connection Continue button not displayed");
			}else{
				driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();

				logger.log(LogStatus.INFO, "Clicked on Bifrost Connection Continue button");
			}
			 */
			confirmSpace.clk_ConfirmwtCust();
			Thread.sleep(6000);
			logger.log(LogStatus.PASS,"Clicked on Confirm with Customer button");
			Reporter.log("Clicked on Confirm with Customer button",true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switched to Customer Screen");

			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			Thread.sleep(8000);
			driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']")).click();
			logger.log(LogStatus.PASS,"Clicked on Confirm button in customer screen");
			Reporter.log("Clicked on Confirm button in customer screen",true);

			Thread.sleep(8000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS,"Switching back to PM screen");
			Reporter.log("Switching back to PM screen",true);


			//Filling contact information

			Leasing_ContactInfoPage contactinfo=new  Leasing_ContactInfoPage(driver);
			Thread.sleep(5000);
			String FN = "AUT" + generics.get_RandmString();
			//contactinfo.txt_Fname(tabledata.get("Firstname"));
			contactinfo.txt_Fname(FN);
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
					logger.log(LogStatus.INFO, "Click on State successfully");
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
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			contactinfo.click_CustLookUp();
			logger.log(LogStatus.INFO, "Clicked on Customer Lookup successfully");

			Thread.sleep(15000);
			// Click on New Customer button on Choose an Account PopUp
			driver.findElement(By.linkText("Create New Customer")).click();

			// Select "Yes" Radio button for Military
			Thread.sleep(8000);
			driver.findElement(By.xpath("//label[@id='currently-military-false']/span[1]")).click(); 

			// Click on Verify button
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(6000);
			driver.findElement(By.partialLinkText("Verify")).click();
			logger.log(LogStatus.INFO, "Clicked on Verify button successfully");

			Thread.sleep(8000);
			//if(contactinfo.verify_overrideAddress_Select())
			/*if(driver.findElements(By.xpath("//div[@id='lesseeAddressInput']/div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")).size()!=0)
			{
				contactinfo.click_overrideAddress_Select();
			}*/
			//if(contactinfo.verify_overridePhone_Select())
			/*if(driver.findElements(By.xpath("//ul[@id='phoneList']//div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")).size()!=0)
			{
				contactinfo.click_overridePhone_Select();
			}
			 */
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered the contact info details in Contact Info page");
			logger.log(LogStatus.INFO, "Entered the contact info details in Contact Info page",image);


			driver.findElement(By.id("confirmWithCustomerButton")).click();

			Thread.sleep(5000);
			//Click on Confirm with Cust button

			logger.log(LogStatus.INFO, "clicking on Confirm with cust button successfully");

			// Navigating to Customer screen
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");



			//		driver.findElement(By.id("EmergencyContactForm_EmergencyContacts_0__FirstName")).sendKeys("Ravi");
			//		driver.findElement(By.id("EmergencyContactForm_EmergencyContacts_0__LastName")).sendKeys("M");
			//		driver.findElement(By.xpath("//div[@class='floatleft']/span[@class='k-widget k-dropdown k-header contact-relationship-type']")).click();

			//Entering Emergency Contact details
			Thread.sleep(5000);
			Leasing_EmergencyConatctsPage emergCon= new Leasing_EmergencyConatctsPage(driver);
			logger.log(LogStatus.INFO, "Navigated to Emergency Contact Page");
			emergCon.enterEmergencyFN("Test");
			Thread.sleep(2000);
			emergCon.enterEmergencyLN("G");
			Thread.sleep(2000);
			emergCon.clickRelationship_dropdown();
			//Selecting Relation ship type
			Thread.sleep(5000);
			List<WebElement> allRelations= driver.findElements(By.xpath("//ul[starts-with(@id,'relationshipType')]//li[@class='k-item']"));

			for(WebElement relation: allRelations)
			{
				Thread.sleep(2000);
				if(tabledata.get("Relation").equalsIgnoreCase(relation.getText()))
				{
					relation.click();
					logger.log(LogStatus.INFO, "Selected relation value from dropdown successfully");

					break;
				}
			}
			Thread.sleep(3000);
			emergCon.txt_street1(tabledata.get("Street"));
			Thread.sleep(3000);
			emergCon.txt_city(tabledata.get("City"));
			Thread.sleep(2000);
			emergCon.clickStateList();
			Thread.sleep(5000);
			List<WebElement> allstates1= driver.findElements(By.xpath("//ul[@id='EmergencyContactForm_EmergencyContacts_0__Address_StateCode_listbox']//li[@class='k-item']"));

			for(WebElement state: allstates1)
			{
				Thread.sleep(2000);
				if(tabledata.get("StateCode").equalsIgnoreCase(state.getText()))
				{
					state.click();
					break;
				}
			}

			Thread.sleep(5000);
			emergCon.enterZipCode(tabledata.get("Zipcode"));
			emergCon.clickPhoneType();

			Thread.sleep(5000);
			/*List<WebElement> phonTypes= driver.findElements(By.xpath("//ul[@id='EmergencyContactForm_EmergencyContacts_0__Address_StateCode_listbox')]//li[@class='k-item']"));

			for(WebElement type: phonTypes)
			{
				Thread.sleep(2000);
				if(tabledata.get("PhoneType").contains(type.getText()))
				{
					type.click();
					break;
				}
			}*/

			driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[contains(text(), '"+tabledata.get("PhoneType")+" ')]")).click();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			emergCon.txt_AreaCode(tabledata.get("Areacode"));
			emergCon.txt_Exchg(tabledata.get("Exchange"));
			emergCon.txt_lineNumber(tabledata.get("LineNumber"));
			emergCon.txt_email(tabledata.get("Email"));
			emergCon.clickAuthorizedfor_radio();

			Thread.sleep(6000);
			if(emergCon.verifyCustVerify_btn()){
				emergCon.clickVerify_btn();
				logger.log(LogStatus.INFO, "Clicked on Verify button successfully");

			}
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(8000);
			if(driver.findElements(By.xpath("//div[starts-with(@id,'address')]//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]")).size()!=0){

				emergCon.click_overrideAddress_Select();
				logger.log(LogStatus.INFO, "Clicking on Override Address Select button successfully");
			}

			/*if(driver.findElements(By.xpath("//div[starts-with(@id,'phone')]//div[@class='verificationElement verification-failed margin']//div/a[contains(text(),'Select')]")).size()!=0){

				emergCon.click_overrideAddress_Select();
				logger.log(LogStatus.INFO, "Clicking on Override Address Select button successfully");
			}
			 */
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered the emergency info details in Emergency Info page");
			logger.log(LogStatus.INFO, "Entered the emergency info details in Emergency Info page",image);


			emergCon.clickconfirmWithCust();
			logger.log(LogStatus.INFO, "Clicking on Confirm with Customer Button");
			//			
			//			Thread.sleep(8000);
			//			if(driver.findElement(By.xpath("//div[@class='verificationElement verification-changed margin']//a[contains(text(),'Use Selected Address')]")).isDisplayed())
			//			{
			//				driver.findElement(By.xpath("//div[@class='verificationElement verification-changed margin']//a[contains(text(),'Use Selected Address')]")).click();
			//			}
			//
			//			emergCon.clickconfirmWithCust();

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			Leasing_AuthorizedAccessContactsPage autContact= new Leasing_AuthorizedAccessContactsPage(driver);

			Thread.sleep(8000);
			/*
		   if(driver.findElements(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).size()!=0){

				driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
				logger.log(LogStatus.INFO, "Clicked on Bifrost Connection Continue button");
			}else{
				logger.log(LogStatus.INFO, "Bifrost Connection Continue button not displayed");
			}*/


			/*robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);


			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			 */
			//if(autContact.verifySaveProceed_btn())
			/*if(driver.findElements(By.xpath("//span[@id='customerSaveButton']")).size()!=0)
			{	
				Thread.sleep(6000);
				autContact.enterFirstName("Rudra");

				Thread.sleep(6000);
				autContact.enterLastName("G");

				Thread.sleep(6000);
				autContact.clickPhonelist();

				Thread.sleep(6000);
				autContact.enterAreacode("415");


				Thread.sleep(6000);
				autContact.enterExchange("972");


				Thread.sleep(6000);
				autContact.enterLineNum("6400");


				Thread.sleep(6000);
				autContact.clickSavenProceed();

			}

			else{*/
			autContact.clickConfirmCust();
			//}
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(8000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");


			Thread.sleep(8000);
			Leasing_EligiblePromotionsPage eligpromo= new Leasing_EligiblePromotionsPage(driver);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page");
			logger.log(LogStatus.PASS, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			Thread.sleep(8000);
			eligpromo.clickSavenProceed();

			Thread.sleep(8000);
			Leasing_LeaseQuestionairePage leaseQues= new Leasing_LeaseQuestionairePage(driver);

			leaseQues.clickStorageContent();

			Thread.sleep(5000);

			List<WebElement> allstorage= driver.findElements(By.xpath("//ul[@id='LeaseQuestionnaireUnits_0__RentalUnitContentsTypeID_listbox']//li[@class='k-item']"));

			for(WebElement storage: allstorage)
			{
				if(tabledata.get("StorageContent").equalsIgnoreCase(storage.getText()))
				{
					storage.click();
					break;
				}
			}

			//Insurance is ON

			Thread.sleep(5000);
			leaseQues.clickAddInsuranceYes();

			Thread.sleep(8000);
			leaseQues.clickCoverageList();

			List<WebElement> allcoverage= driver.findElements(By.xpath("//div[@id='LeaseQuestionnaireUnits_0__InsuranceSelection-list']//ul[@id='LeaseQuestionnaireUnits_0__InsuranceSelection_listbox']//li[@class='k-item']"));

			/*for(WebElement coverage: allcoverage)
			{
				if(tabledata.get("InsuranceCoverage").equalsIgnoreCase(coverage.getText()))
				{
					System.out.println(coverage.getText());
					coverage.click();
					break;
				}
			}*/

			for(WebElement coverage: allcoverage)
			{
				Thread.sleep(5000);
				coverage.click();
				logger.log(LogStatus.INFO, "Selected value from Insurance dropdown");

				break;
			}
			Thread.sleep(8000);
			if(driver.findElements(By.xpath("//form[@id='addendumsForm']//div[@class='padding lease-access-keypad-zone']//span[@class='k-widget k-dropdown k-header access-zone']")).size()!=0)
			{

				leaseQues.clickAccessZone();

				Thread.sleep(8000);

				List<WebElement> allAccessZone= driver.findElements(By.xpath("//div[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID-list']//ul[@id='LeaseQuestionnaireUnits_0__GateControllerTimeZoneID_listbox']//li[@class='k-item']"));

				for(WebElement accessZone: allAccessZone)
				{
					Thread.sleep(5000);
					accessZone.click();
					break;
				}

			}
			Thread.sleep(8000);
			if(driver.findElements(By.xpath("//form[@id='addendumsForm']//div[@class='padding lease-access-keypad-zone']//span[@class='k-widget k-dropdown k-header keypad-zone']")).size()!=0)
			{
				leaseQues.clickKeypadZone();
				Thread.sleep(8000);
				List<WebElement> allKaypadZone= driver.findElements(By.xpath("//div[@id='LeaseQuestionnaireUnits_0__KeypadZone-list']//ul[@id='LeaseQuestionnaireUnits_0__KeypadZone_listbox']//li[@class='k-item']"));

				for(WebElement keypadZone: allKaypadZone)
				{
					Thread.sleep(5000);
					keypadZone.click();
					break;
				}
			}
			Thread.sleep(8000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Selected Insurance and gate access code");
			logger.log(LogStatus.INFO, "Selected Insurance and gate access code",image);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);


			if(leaseQues.verifySaveAndProceedBtn()){

				leaseQues.clickSaveAndProceedBtn();
				Thread.sleep(5000);
			}else{

				leaseQues.clickConfirmCust();	
			}
			Thread.sleep(5000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");


			Thread.sleep(10000);

			List<WebElement> allCheckbox=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox)
			{
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(8000);
			/*			List<WebElement> allCheckbox1=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox1)
			{
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(8000);
			List<WebElement> allCheckbox2=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox2)
			{
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(8000);
			List<WebElement> allCheckbox3=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox3)
			{
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();


			Thread.sleep(8000);
			List<WebElement> allCheckbox4=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox4)
			{
				checkbox.click();
			}
			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();

			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();*/

			/*			WebElement signature = driver.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();

			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));*/

			Leasing_ReviewNApprovePage review= new Leasing_ReviewNApprovePage(driver);

			Thread.sleep(8000);
			//review.clickApprove_btn();

			Thread.sleep(8000);
			/*	robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));*/

			List<WebElement> allCheckbox1=driver.findElements(By.xpath("//section[@class='term-group term-group--active']//input[@type='checkbox']"));

			for(WebElement checkbox:allCheckbox1)
			{
				checkbox.click();
			}
			Thread.sleep(5000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);
			WebElement signature1 = driver.findElement(By.xpath("//div[@class='sig sigWrapper']/canvas[@class='pad js-signature-canvas']"));
			Actions actionBuilder1 = new Actions(driver);          
			Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();

			Thread.sleep(5000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(5000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(6000);
			review.clickApprove_btn();
			//review.clickInsuranceApprove_Btn();
			Thread.sleep(3000);

			// Doing page refresh
			/*robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);


			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));*/
			Thread.sleep(3000);
			review.clickSaveproceed_btn();

			Leasing_RentalFeePage rentalfee= new Leasing_RentalFeePage(driver);

			Thread.sleep(15000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Validating rental fee in Rental Fee Pag");
			logger.log(LogStatus.PASS, "Validating rental fee in Rental Fee Page",image1);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			rentalfee.clickConfirmCust_btn();
			Thread.sleep(8000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);

			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");

			Thread.sleep(5000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			Thread.sleep(8000);

			Leasing_PaymentMethodsPage payment= new Leasing_PaymentMethodsPage(driver);

			Thread.sleep(5000);
			String amount=driver.findElement(By.xpath("//div[@id='payment-form']//div[@id='payment-table']//div/span[@class='js-total']")).getText();

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			// Doing Payment through Credit Card
			driver.findElement(By.xpath("//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[contains(@class,'k-list-container k-popup k-group k-reset')]/ul/li[contains(text(),'Credit')]")).click();
			logger.log(LogStatus.INFO, "select the credit card option  from the list and click on the credit card option");

			Thread.sleep(5000);
			payment.clickmanualentry();
			logger.log(LogStatus.INFO, "click on the enter manually tab");
			Thread.sleep(10000);

			// Performing task in Credit card popup //
			if(driver.findElement(By.xpath("//div[@class='k-widget k-window']//span[contains(text(),'Manual Credit Card Entry')]")).isDisplayed()){
				logger.log(LogStatus.INFO, "Credit Card Popup is displayed");
				driver.findElement(By.xpath("//div[@class='k-widget k-window']//span[contains(text(),'Manual Credit Card Entry')]")).click();
			}else{
				logger.log(LogStatus.INFO, "Credit Card Popup is not displayed");
			}

			WebElement frame=driver.findElement(By.xpath("//div[iframe[@id='iframe_Creditcard_info']]/iframe"));
			driver.switchTo().frame(frame);

			//Entering Credit Card Number
			driver.findElement(By.xpath("//input[@id='cardNumber']")).sendKeys("4111111111111111");
			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			//Selecting Expiry Month
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(5000);

			//Selecting Expiry Year
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			Thread.sleep(5000);

			//Clicking on Accept Button
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);


			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered details in Credit Card popup successfully");
			logger.log(LogStatus.PASS, "Entered details in Credit Card popup successfully",image);

			Thread.sleep(10000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			driver.findElement(By.xpath("//input[@id='cardholderName']")).sendKeys("Test");
			Thread.sleep(5000);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
			Thread.sleep(3000);
			payment.clickCCField();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_DELETE);
			robot.keyRelease(KeyEvent.VK_DELETE);

			Thread.sleep(2000);
			payment.enterCCAmount(amount);
			Thread.sleep(5000);

			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Card and amount details entered successfully");
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(4000);

			payment.clickCCAutopayCheckBox();
			logger.log(LogStatus.INFO, "Clicked on Autopay check box successfully");
			Thread.sleep(5000);

			payment.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entering Card holder name and total amount");
			logger.log(LogStatus.PASS, "Entering Card holder name and total amount "+image);

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			payment.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect signature button successfully");

			Thread.sleep(6000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(5000);

			signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			actionBuilder1 = new Actions(driver);          
			drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			payment.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Customer Signature and click on Accept button successfully");

			Thread.sleep(6000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payment.clickApprove_Btn();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");
			Thread.sleep(6000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			Thread.sleep(6000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(5000);

			signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			actionBuilder1 = new Actions(driver);          
			drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			payment.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Customer Signature and click on Accept button successfully");

			Thread.sleep(6000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			//payment.clickApprove_Btn();
			driver.findElement(By.xpath("(//a[contains(text(),'Approve')])[2]")).click();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");
			Thread.sleep(6000);

			Thread.sleep(6000);
			payment.clickSubmit_btn();
			logger.log(LogStatus.INFO, "Click on Submit button successfully");

			Thread.sleep(5000);

			Leasing_TransactionCompletePage transcom= new Leasing_TransactionCompletePage(driver);

			Thread.sleep(5000);
			transcom.enterEmployeeNum(tabledata.get("UserName"));

			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entering Employee ID");
			logger.log(LogStatus.PASS, "Entering Employee ID "+image);
			Thread.sleep(5000);

			transcom.clickOk_btn();

			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating Gate Code Confirmation");
			logger.log(LogStatus.PASS, "Validating Gate Code Confirmation "+image);

			Thread.sleep(18000);

			try{
				driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'No')]")).click();
				Thread.sleep(25000);
			}catch(Exception ex){

			}

			Thread.sleep(30000);

			try{
				Alert alert = driver.switchTo().alert();
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(15000);
			}catch(Exception ex){

			}

			Thread.sleep(15000);

			pm_home.clk_AdvSearchLnk();


			Thread.sleep(8000);
			Advance_Search advSearch= new Advance_Search(driver);

			String sqlQuery="select top 1 accountid from account where customerid = (select customerid from customer where contactid = (select contactid from contact where firstname = '"+FN+"')) order by recorddatetime desc";

			String accNUm=DataBase_JDBC.executeSQLQuery(sqlQuery);

			advSearch.enterAccNum(accNUm);

			Thread.sleep(5000);

			logger.log(LogStatus.PASS, "Account Number Created : " +accNUm);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Account Number entered in Advance Search Page successfully");
			logger.log(LogStatus.PASS, "Account Number entered in Advance Search Page successfully",image);
			Thread.sleep(3000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");

			Thread.sleep(8000);
			Cust_AccDetailsPage cust_db= new Cust_AccDetailsPage(driver);
			String scpath3=Generic_Class.takeScreenShotPath();
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.PASS, "Customer Dashboard page");
			logger.log(LogStatus.PASS, "Navigated to Customer Dashboard",image3);

			cust_db.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details tab successfully");

			Thread.sleep(6000);
			Acc_SpaceDetailsPage spaceTab=new Acc_SpaceDetailsPage(driver);

			if(spaceTab.verifyAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Status is enabled successfully");
				logger.log(LogStatus.PASS, "Autopay Status is enabled successfully",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay Status is Not enabled successfully");
				logger.log(LogStatus.FAIL, "Autopay Status is Not enabled successfully",image);
			}


			Thread.sleep(8000);

			//Verify to see account activity created
			cust_db.click_AccountActivities();
			Thread.sleep(8000);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());


			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Autopay']/..//td/div[contains(text(),'Autopay turned on')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is enabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not enabled - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			Thread.sleep(3000);
			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Emails']/..//td[contains(text(),'Insurance Addendum')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Insurance Addendum is displayed in Account Activities Section");
				logger.log(LogStatus.PASS, "Insurance Addendum is displayed in Account Activities Section",image);
				Thread.sleep(6000);
				driver.findElement(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Emails']/..//td[contains(text(),'Insurance Addendum')]/preceding-sibling::td[@class='k-hierarchy-cell']")).click();
				Thread.sleep(3000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating Insurance Addendum  in Account Activities Section");
				logger.log(LogStatus.PASS, "Validating Insurance Addendum  in Account Activities Section",image);
			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Insurance Addendum is not displayed in Account Activities Section");
				logger.log(LogStatus.FAIL, "Insurance Addendum is not displayed in Account Activities Section",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}


			cust_db.clk_DocumentsTab();
			Thread.sleep(10000);

			// Validating Documents Tab

			if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='"+avlSpace+"']/preceding-sibling::td[text()='Insurance Privacy Policy']")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Insurance Addendum validation in Documents Tab");
				logger.log(LogStatus.PASS, "Insurance Privacy Policy record is successfully displayed in Documents Tab");
				logger.log(LogStatus.INFO, "Image",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Insurance Privacy Policy record is not displayed in Documents Tab");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='"+avlSpace+"']/preceding-sibling::td[text()='Property AutoPay contract']")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Addendum validation in Documents Tab");
				logger.log(LogStatus.PASS, "Propertywalkin AutoPay generated contract record is successfully displayed in Documents Tab");
				logger.log(LogStatus.INFO, "Image",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Propertywalkin AutoPay generated contract record is not displayed in Documents Tab");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}

	}

	@AfterMethod

	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "AutoPay","Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "AutoPay","Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AutoPay","Enroll_autopay_leasing_Individual_Creditcard_with_insurance_without_tax" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
