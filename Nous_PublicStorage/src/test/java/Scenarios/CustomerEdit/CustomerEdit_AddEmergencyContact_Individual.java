package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.AuthorizedUsrpage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContactPopup;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.CustDashboardPages.IsCustomerPresent;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class CustomerEdit_AddEmergencyContact_Individual extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit","CustomerEdit_AddEmergencyContact_Individual");
	}

	@Test(dataProvider = "getData")
	public void addEmergencyContact_Indiv(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			logger=extent.startTest("CustomerEdit_AddEmergencyContact_Individual");
			Reporter.log("Test case started: " +testcaseName, true);

			
			//Login To the Application
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			

			// =================Handling customer facing
						// device========================
						Thread.sleep(2000);
						Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
				

						//Bifrostpop.clickContiDevice();


						String biforstNum=Bifrostpop.getBiforstNo();

						Reporter.log(biforstNum+"",true);
						//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_T);
						Thread.sleep(5000);
						ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
						Reporter.log(tabs.size()+"",true);
						Thread.sleep(5000);
						driver.switchTo().window(tabs.get(1));

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

						//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(5000);
						driver.switchTo().window(tabs.get(0));
						Thread.sleep(9000);
						driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click(); 
						Thread.sleep(5000);
					
						// =================================================================================

			PM_Homepage pmhome = new PM_Homepage(driver);
			pmhome.clk_AdvSearchLnk();
		
			Thread.sleep(5000);
			Advance_Search advSearch = new Advance_Search(driver);
			String location = advSearch.getLocationNum();
			// Advance search page
			String query = "select top 1 u.accountnumber from vw_unitdetails u "
					+ "join storageorderitemcontact soic with(nolock) on soic.storageorderitemid = u.storageorderitemid "
					+ "and not exists (select '1' from storageorderitemcontact soic2 "
					+ "where soic2.storageorderitemid = u.storageorderitemid "
					+ "and soic2.contacttypeid = 3151) "
					+ "where u.vacatedate is null "
					+ "and u.customertypeid = 90";
			 
			  String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
			 

			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.PASS, "Account number entered successfully:" +AccountNumber);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
		
			Thread.sleep(9000);
			
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Customer Account Dashboard Page ",image);

			
			Thread.sleep(1000);

			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(15000);

			 scpath = Generic_Class.takeScreenShotPath();
			 image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img",image);

			
			
			
			Acc_SpaceDetailsPage spaceDtls = new Acc_SpaceDetailsPage(driver);
			spaceDtls.click_EmergencyContact();
			logger.log(LogStatus.PASS, "Clickd on Emergency contact link successfully");
			Thread.sleep(5000);
			
			//Emergency contact screen should be displayed
			if(driver.findElement(By.xpath("//span[text()='Emergency Contact']")).isDisplayed()){
				
				logger.log(LogStatus.PASS, "Emergency Contact screen displayed successfully");
				
			} else {
				
				logger.log(LogStatus.FAIL, "Emergency Contact screen not displayed ");
				
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			
			

			String msg=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']")).getText();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, " Before Adding Emergency Contact  displaying  as : "  + msg);
			
			Thread.sleep(2000);
			
			
			String snap=Generic_Class.takeScreenShotPath();
			String irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			
			
			
			
			
			AuthorizedUsrpage AuthorizedUsrpage = new AuthorizedUsrpage(driver);

			
			
			
			
			
			

			EmergencyContactPopup emrgncyPop = new EmergencyContactPopup(driver);
			emrgncyPop.click_EditBtn();
			Thread.sleep(5000);
			
			//Verify that the Customer is present and 'Yes' option is selected in Is Customer Present selection 
			
			if(driver.findElement(By.xpath("//span[text()='Is the customer present?']")).isDisplayed()){
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Yes option is selected in Is Customer present ");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Yes option is not selected in Is Customer present  ");
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			
			
				
		
			
			
			

			IsCustomerPresent custPrsnt = new IsCustomerPresent(driver);
			custPrsnt.click_YesBtn();
			Thread.sleep(2000);
			custPrsnt.click_ContinueBtn();
		
			
			Thread.sleep(15000);
			
			

			
			
			
			
			
			

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Emergency contact Screen");

			EmergncyContctpage emerContact = new EmergncyContctpage(driver);
			emerContact.click_CustomerDecline_Checkbox();
			Thread.sleep(5000);

			boolean firstNameDisplayed = emerContact.isDisplayed_FirstName();
			if (firstNameDisplayed) {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "After unchecking decline checkbox, emergency contact fields displayed");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "After unchecking decline checkbox, emergency contact fields not displayed");
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			/*
			 * emerContact.click_CancelBtn(); Thread.sleep(15000);
			 * logger.log(LogStatus.INFO,
			 * "Navigated back to Customer Dashboard Screen");
			 * 
			 * cust_accdetails = new Cust_AccDetailsPage(driver);
			 * cust_accdetails.clickSpaceDetails_tab(); Thread.sleep(15000);
			 * 
			 * spaceDtls = new Acc_SpaceDetailsPage(driver);
			 * spaceDtls.click_EmergencyContact(); Thread.sleep(10000);
			 * 
			 * emrgncyPop = new EmergencyContactPopup(driver);
			 * emrgncyPop.click_EditBtn(); Thread.sleep(5000);
			 * 
			 * custPrsnt = new IsCustomerPresent(driver);
			 * custPrsnt.click_YesBtn(); Thread.sleep(2000);
			 * custPrsnt.click_ContinueBtn(); Thread.sleep(15000);
			 * 
			 * emerContact = new EmergncyContctpage(driver);
			 * emerContact.click_CustomerDecline_Checkbox(); Thread.sleep(5000);
			 * 
			
			 */

			String data = Generic_Class.get_RandmString();
			//int randomNum = 1400 + (int)(Math.random() * 1600); 
			String FirstName="Robert"+data +"Watson";
			
			Thread.sleep(3000);
			
			
			String data1 = Generic_Class.get_RandmString();
			String LastName=data1+"Jonson";
			
			Thread.sleep(3000);
			
			int LineNo = 9000 + (int) (Math.random() * 1400);
			String LineNumber = Integer.toString(LineNo);

			
			
		
			int randomNum = 1400 + (int)(Math.random() * 1600); 
			String address=randomNum+" Westwood Blvd";
		
			
			
			emerContact.enter_FirstName(FirstName);
			logger.log(LogStatus.INFO, "First Name enterd successfully and First Name is : " + FirstName);
			
			
			Thread.sleep(2000);
			emerContact.enter_LastName(LastName);
			logger.log(LogStatus.INFO, "Last Name enterd successfully  and Last Name is : " + LastName);
			
		
			emerContact.selectRelationship(tabledata.get("Relationship"));
			Thread.sleep(2000);
			emerContact.enter_Address1(tabledata.get("Address1"));
			emerContact.enter_City(tabledata.get("City"));
			emerContact.selectState(tabledata.get("State"));
			Thread.sleep(2000);
			emerContact.enter_Zip(tabledata.get("Zip"));
			emerContact.selectPhoneType(tabledata.get("PhoneType"));
			Thread.sleep(2000);
			emerContact.enter_AreaCode(tabledata.get("AreaCode"));
			emerContact.enter_Exchange(tabledata.get("Exchange"));
			emerContact.enter_LineNumber(LineNumber);
			emerContact.enter_Email(tabledata.get("Email"));
			logger.log(LogStatus.PASS, "Added new Emergency contact of customer");
			
			Thread.sleep(2000);
			
			
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
			emerContact.click_VerifyBtn();
			Thread.sleep(6000);
			try{
				driver.findElement(By.xpath("//div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")).click();
				Thread.sleep(4000);

			}catch(Exception e){

			}
			try{
				emerContact.click_VerifyBtn();
				Thread.sleep(2000);
			}catch(Exception ex){

			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
			Thread.sleep(4000);
			//emerContact.click_VerifyBtn();
			//Thread.sleep(4000);
			
		
			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

			WebElement sig = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions action = new Actions(driver);
			Action Operation = action.moveToElement(sig, 660, 96).click().clickAndHold(sig).moveByOffset(120, 120)
					.moveByOffset(60, 70).moveByOffset(-140, -140).release(sig).build();
			Operation.perform();
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "A signature block is displayed in the customer signature from the CFS");
			driver.findElement(By.xpath("//button[contains(text(),'Clear Signature')]")).click();
			Thread.sleep(3000);
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.INFO, "When clicked on clear button, Customer's signature is cleared");
			Thread.sleep(3000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			WebElement sign = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder1 = new Actions(driver);
			Action Operation1 = actionBuilder1.moveToElement(sign, 660, 96).click().clickAndHold(sign)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(sign).build();
			Operation1.perform();
			Thread.sleep(6000);
			
			
			 snap=Generic_Class.takeScreenShotPath();
			 irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(3000);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");

			emerContact.click_ApproveBtn();
		
			Thread.sleep(3000);
			emerContact.click_SaveBtn();
			
			Thread.sleep(8000);

			EmergencyContact_EmployeeIdPage empIdPage1 = new EmergencyContact_EmployeeIdPage(driver);
			empIdPage1.enter_EmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "when Save button is clicked, it displays the  Employee ID modal window ");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			empIdPage1.click_ContinueBtn();
			Thread.sleep(25000);
			
			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(8000);
			}catch(Exception ex){}
			
			Thread.sleep(9000);


			logger.log(LogStatus.PASS,
					"Navigated to customer dashboard after entering the employee id and clicking on continue button");
			Thread.sleep(5000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO ,"img",image);
			
			
			
			
			
			
			//Click on Space details tab
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(15000);

			
			
			
		
			spaceDtls.click_EmergencyContact();
			
			Thread.sleep(5000);
			
			
			
			
			
			
			

			String msg1=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']")).getText();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, " Emergency Contact added successfully and  displaying  as : "  + msg1);
			
			Thread.sleep(2000);
			
			
			 snap=Generic_Class.takeScreenShotPath();
			 irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);

			
			driver.findElement(By.xpath("//a[contains(text(),'Close')]")).click();
			Thread.sleep(2000);
			
			

			cust_accdetails = new Cust_AccDetailsPage(driver);
			cust_accdetails.click_AccountActivities();
			logger.log(LogStatus.INFO, " Clicked on Account Activities Tab successfully");
			Thread.sleep(20000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			
			
			Thread.sleep(6000);

			
			
			// Click on Customer info tab
			

			Thread.sleep(15000);

			// Verify that the changes to authorized access information shall be
			// saved to the database 
			String query12 = "select count(*) from contact where firstName='" + FirstName + "'";
			String Record = DataBase_JDBC.executeSQLQuery(query12);
			Thread.sleep(2000);

			int totalRecord = Integer.parseInt(Record);
			if (totalRecord == 1) {
				logger.log(LogStatus.PASS,
						"The changes to authorized access information for Firstname are saved in the database : "  + FirstName);
			}

			Thread.sleep(2000);
			String query1 = "select count(*) from contact where Lastname='" + LastName + "'";
			String Record1 = DataBase_JDBC.executeSQLQuery(query1);
			Thread.sleep(2000);

			int totalRecord1 = Integer.parseInt(Record1);
			if (totalRecord1 ==1 ) {
				logger.log(LogStatus.PASS,
						"The changes to authorized access information for Lastname are saved in the database   " + LastName);
			}
			
		
			

			// ---------------Code for deleting the contact--------------------------
			// click on Edit Account Details

			Thread.sleep(8000);
			 cust_accdetails.clickSpaceDetails_tab();
			 Thread.sleep(4000);
			 
			 spaceDtls.click_EmergencyContact();


				Thread.sleep(5000);
				
				//Verify that the Customer is present and 'Yes' option is selected in Is Customer Present selection 
				
				emrgncyPop.click_EditBtn();
				Thread.sleep(5000);
				
					
			
				
				
				

			
				custPrsnt.click_YesBtn();
				Thread.sleep(3000);
				custPrsnt.click_ContinueBtn();
			
				
				Thread.sleep(5000);
				
		
		
			
			

			

			/*
			 * spaceDtls.click_EmergencyContact();
			 * 
			 * emrgncyPop.click_EditBtn(); Thread.sleep(5000);
			 * 
			 * custPrsnt.click_YesBtn(); Thread.sleep(2000);
			 * custPrsnt.click_ContinueBtn();
			 */
			emerContact.click_CustomerDecline_Checkbox();
			Thread.sleep(6000);

			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			

			WebElement Sign = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actions12 = new Actions(driver);
			Action Operations = actions12.moveToElement(Sign, 660, 96).click().clickAndHold(Sign).moveByOffset(120, 120)
					.moveByOffset(60, 70).moveByOffset(-140, -140).release(Sign).build();
			Operations.perform();
			Thread.sleep(6000);
			
			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(3000);
			emerContact.click_ApproveBtn();
			
			Thread.sleep(3000);

			
		
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			emerContact.click_SaveBtn();
			Thread.sleep(8000);

			empIdPage1.enter_EmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "when Save button is clicked, it displays the  Employee ID modal window ");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			empIdPage1.click_ContinueBtn();
			Thread.sleep(30000);
			

			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(8000);
			}catch(Exception ex){}
			
			
			
			
			
			
			
			
			
			//Code for Checking the data is deleted
			
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(15000);

			
		
			spaceDtls.click_EmergencyContact();
		
			Thread.sleep(5000);
			
			
			

			String msgdeletion=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']")).getText();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Emergency contact Deleted successfully and message is displaying as : "   + msgdeletion);
			
			Thread.sleep(2000);
			
			
			 snap=Generic_Class.takeScreenShotPath();
			 irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			
			
			
			

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
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", this.getClass().getSimpleName(), "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", this.getClass().getSimpleName(), "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", this.getClass().getSimpleName(), "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
