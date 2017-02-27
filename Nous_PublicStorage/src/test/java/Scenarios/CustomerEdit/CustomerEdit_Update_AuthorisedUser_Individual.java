package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import Pages.AuthorizedUsersDetails.AuthorizedUsers;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.AuthorizedUsers_IsCustomerPresent;
import Pages.CustDashboardPages.AuthorizedUsrpage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContactPopup;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.CustDashboardPages.IsCustomerPresent;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_Update_AuthorisedUser_Individual extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getCustomerData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit",
				"CustomerEdit_Update_AuthorisedUser_Individual");
	}

	@Test(dataProvider = "getCustomerData")
	public void CustomerEdit_Update_AuthorisedUser_Individual(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {
			logger = extent.startTest("CustomerEdit_Update_AuthorisedUser_Individual",
					"CustomerEdit AuthorisedUser update starts ");

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

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


			/*
			 * * Dashboard_BifrostHostPopUp Bifrostpop= new
			 * Dashboard_BifrostHostPopUp(driver); logger.log(LogStatus.INFO,
			 * "PopUp window object is created successfully");
			 * 
			 * Bifrostpop.clickContiDevice();
			 */

			PM_Homepage pmhome = new PM_Homepage(driver);

			// String location = pmhome.getLocation();

			pmhome.clk_AdvSearchLnk();
		
			Thread.sleep(5000);
			Advance_Search advSearch = new Advance_Search(driver);
			String location = advSearch.getLocationNum();
			// Advance search page

			String query = "select top 1 u.accountnumber from vw_unitdetails u "
					+ "join storageorderitemcontact soic with(nolock) on soic.storageorderitemid = u.storageorderitemid "
					+ "where u.vacatedate is null "
					+ "and u.customertypeid = 90 "
					+ "and 1 = (select count(soic2.storageorderitemcontactid) from storageorderitemcontact soic2 "
					+ "where soic2.storageorderitemid = u.storageorderitemid "
					+ " and soic2.contacttypeid = 3152)";

			String AccountNumber = DataBase_JDBC.executeSQLQuery(query);

			advSearch.enterAccNum(AccountNumber);
			// advSearch.enterAccNum(tabledata.get("AccountNumber"));
			logger.log(LogStatus.INFO, "Account number entered successfully:" + AccountNumber);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "Image", image2);

			advSearch.clk_SearchAccount();

			Thread.sleep(2000);
		
			Thread.sleep(10000);

			// Taking Screenshot
			scpath2 = Generic_Class.takeScreenShotPath();
		image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
			logger.log(LogStatus.INFO, "Navigated to Customer Dashboard successfully", image2);

			// Customer Dashboard
			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			if (cust_accdetails.getCustDashboardTitle()
					.contains("Confirm Customer's Identity and Contact Information")) {

			
				logger.log(LogStatus.PASS,
						"Customer Account Dashboard displayed successfully for an Individual Custome :"
								+ cust_accdetails.getCustDashboardTitle());
				
			}

			else {
				
				logger.log(LogStatus.FAIL, "Customer Account Dashboard not  displayed successfully ");
				
			}
			
		

			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Clicked on Space details tab");
			
			String scpath12 = Generic_Class.takeScreenShotPath();
			String image12 = logger.addScreenCapture(scpath12);
			logger.log(LogStatus.PASS, " img ",image12);

			
			
			

			Acc_SpaceDetailsPage Acc_SpaceDetailsPage = new Acc_SpaceDetailsPage(driver);
			
			/* mohana */
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
		

			AuthorizedUsrpage AuthorizedUsrpage = new AuthorizedUsrpage(driver);

			if (AuthorizedUsrpage.verify_AuthorizedAccessPopupIsDisplayed()) {

				
				logger.log(LogStatus.PASS, "Authorized Users screen  displayed successfully");
				
			}

			else {
				
				logger.log(LogStatus.FAIL, "Authorized Users screen not displayed successfully");
				

			}
			
			
			//Getting the Record Before update
			
			
			String AuthorizedContactBeforeUpdate=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']/div[1]")).getText();
			Thread.sleep(2000);
			

			logger.log(LogStatus.INFO, "Authorized Contact Brfore Update is :" + AuthorizedContactBeforeUpdate);
			
			Thread.sleep(2000);
			String snap=Generic_Class.takeScreenShotPath();
			String irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);

			
			
			
			driver.findElement(By.xpath("//a[contains(text(),'Close')]")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//span[text()='Customer Info']")).click();
			
			Thread.sleep(2000);
			
			Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			EditAccountDetails editAccountDetails = new EditAccountDetails(driver);
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			// Click on Other Customer Status Radio Button

		  driver.findElement(By.xpath("//span[text()='Authorized Access']")).click();
			logger.log(LogStatus.PASS, "Clicked on Authorized Access radio button successfully ");
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//span[text()='Yes']")).click();
			
			//span[text()='Yes']

			editAccountDetails.clk_LaunchBtn();
			logger.log(LogStatus.PASS, "Clicked on Launch button successfully ");
			Thread.sleep(4000);

			
		
			
			
			
			

			
			AuthorizedUsers_IsCustomerPresent AuthorizedUsers_IsCustomerPresent = new AuthorizedUsers_IsCustomerPresent(
					driver);

			
			

			AuthorizedUsers authUsers = new AuthorizedUsers(driver);
			authUsers.clk_Clear();
			Thread.sleep(4000);
			
			
			
			//Update the Information
			
			// Enter First name
			
			
			String data = Generic_Class.get_RandmString();
			//int randomNum = 1400 + (int)(Math.random() * 1600); 
			String UpdatedFirstName="Robert"+data +"Mask";
			
			Thread.sleep(3000);
			
			
			String data1 = Generic_Class.get_RandmString();
			String UpdatedLastName=data1+"Jonson";
			
			Thread.sleep(3000);
			
			int LineNo = 8000 + (int) (Math.random() * 1600);
			String LineNumber = Integer.toString(LineNo);

			
			
			
			
			
			
			
			
			
			
			authUsers.enter_FirstName(UpdatedFirstName);
			logger.log(LogStatus.PASS, "First Name Entered Successfully and Name is : " + UpdatedFirstName);

			// Enter Last name
			authUsers.enter_LastName(UpdatedLastName);
			logger.log(LogStatus.PASS, "Last Name Updated Successfully : " + UpdatedLastName);

			// Enter Phone No
			
			authUsers.selectPhoneType(driver, tabledata.get("PhoneType"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Phone Type Selected Successfully");
			
			
			authUsers.enter_PhoneAreaCode(tabledata.get("AreaCode"));
			logger.log(LogStatus.PASS, "AreaCode Entered Successfully :" + tabledata.get("AreaCode"));
			authUsers.enter_PhoneExchange(tabledata.get("Exchange"));
			logger.log(LogStatus.PASS, "Exchange Entered Successfully :" + tabledata.get("Exchange"));

			authUsers.enter_PhoneLineNumber(LineNumber);
			logger.log(LogStatus.PASS, "LineNumber Entered Successfully :" + LineNumber);
			String scpath = Generic_Class.takeScreenShotPath();
            String image = logger.addScreenCapture(scpath);
            logger.log(LogStatus.INFO, "Image", image);

			authUsers.clk_SpaceCheckBox();

			EmergncyContctpage emerContact = new EmergncyContctpage(driver);
			
			/* mohana */
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

			WebElement signature = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);
			Action drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "A signature block is displayed in the customer signature from the CFS");
			driver.findElement(By.xpath("//button[contains(text(),'Clear Signature')]")).click();
			Thread.sleep(3000);
			driver.switchTo().window(tabs.get(0));
		
			Thread.sleep(3000);
			
			driver.switchTo().window(tabs.get(1));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
		
			signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			actionBuilder = new Actions(driver);
			drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			
			
			
			 scpath = Generic_Class.takeScreenShotPath();
	           image = logger.addScreenCapture(scpath);
	            logger.log(LogStatus.INFO, "Image", image);
			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			 scpath = Generic_Class.takeScreenShotPath();
	           image = logger.addScreenCapture(scpath);
	            logger.log(LogStatus.INFO, "Image", image);
			emerContact.click_ApproveBtn();
		
			Thread.sleep(3000);
			 scpath = Generic_Class.takeScreenShotPath();
	           image = logger.addScreenCapture(scpath);
	            logger.log(LogStatus.INFO, "Image", image);

			boolean isapprv = emerContact.isApproved();
			if (isapprv) {
				String scpath1 = Generic_Class.takeScreenShotPath();
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Signature approved message displayed after clicking on approve button");
				logger.log(LogStatus.INFO, "", image1);
			} else {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Signature approved message not displayed after clicking on approve button");
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			emerContact.click_SaveBtn();
		
			Thread.sleep(8000);

			EmergencyContact_EmployeeIdPage empIdPage = new EmergencyContact_EmployeeIdPage(driver);
			empIdPage.enter_EmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "when Save button is clicked, it displays the  Employee ID modal window ");
			
			
			 scpath = Generic_Class.takeScreenShotPath();
	           image = logger.addScreenCapture(scpath);
	            logger.log(LogStatus.INFO, "Image", image);

			empIdPage.click_ContinueBtn();
			Thread.sleep(40000);

			logger.log(LogStatus.PASS,
					"Navigated to customer dashboard after entering the employee id and clicking on continue button");
			
			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(10000);
			}
			
			catch(Exception ex){
				
			}

			
			
			driver.switchTo().window(tabs.get(1));
			
			Thread.sleep(8000);
			if (driver.findElement(By.xpath("//div[text()='Thank you!']")).isDisplayed()) {
				
				logger.log(LogStatus.PASS, "Thank You screen is displayed successfully  ");
				
			}

			else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath1 = Generic_Class.takeScreenShotPath();
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.FAIL, "Thank You screen not  displayed successfully");
				logger.log(LogStatus.FAIL, "Thank You screen not  displayed successfully ");
			}

			driver.switchTo().window(tabs.get(0)); 
			Thread.sleep(5000);
			/*
			try{
				driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click(); 
			}catch(Exception ex){
				System.out.println("Bifrost popup didnt encountered");
			}*/
			
			
						

			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(4000);
			
			/* mohana */
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
			Thread.sleep(4000);
			logger.log(LogStatus.INFO, "Clicked on authorised users link");
			
			
			
			

           //Getting the Record After update
			
			
			String AuthorizedContactAfterUpdate=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']/div[1]")).getText();
			Thread.sleep(2000);
			

			logger.log(LogStatus.INFO, "Authorized Contact After Update is :" + AuthorizedContactAfterUpdate);
			
			
			if(AuthorizedContactBeforeUpdate.equals(AuthorizedContactAfterUpdate)){
				logger.log(LogStatus.FAIL, "Authorized Contact  not updated ");
			}
			
			else{
				logger.log(LogStatus.PASS, "Authorized Contact  updated successfully: Authorized Contact Before update is : "  + AuthorizedContactBeforeUpdate  + "Authorized Contact After update is : " + AuthorizedContactAfterUpdate);
			}
			
			
			
			
			
			Thread.sleep(2000);
			snap=Generic_Class.takeScreenShotPath();
		    irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);

			
			
			
			
			
			
			

			// Verify that the changes to authorized access information shall be
			// saved to the database 
			String query1 = "select count(*) from contact where firstName='" + UpdatedFirstName + "'";
			String Record = DataBase_JDBC.executeSQLQuery(query1);
			Thread.sleep(5000);

			int totalRecord = Integer.parseInt(Record);
			if (totalRecord > 0) {
				logger.log(LogStatus.PASS,
						"The changes to authorized access information for Firstname updated and saved in the database : " + UpdatedFirstName);
			}else{
				logger.log(LogStatus.FAIL,
						"The changes to authorized access information for Firstname are not updated and saved in the database  ");
			}

			Thread.sleep(2000);
			String query11 = "select count(*) from contact where Lastname='" + UpdatedLastName + "'";
			String Record1 = DataBase_JDBC.executeSQLQuery(query11);
			Thread.sleep(5000);

			int totalRecord1 = Integer.parseInt(Record1);
			if (totalRecord1 > 0) {
				logger.log(LogStatus.PASS,
						"The changes to authorized access information for Lastname updated and  saved in the database :" + UpdatedLastName );
			}
			
			
				

			

		}

		catch (Exception ex) {
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
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Update_AuthorisedUser_Individual",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Update_AuthorisedUser_Individual",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Update_AuthorisedUser_Individual",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}
}
