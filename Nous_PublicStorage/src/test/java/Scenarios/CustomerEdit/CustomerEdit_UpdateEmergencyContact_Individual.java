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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContactPopup;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.CustDashboardPages.IsCustomerPresent;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_UpdateEmergencyContact_Individual extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = "./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit", this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getData")
	public void addEmergencyContact_Indiv(Hashtable<String, String> tabledata) throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			// Login to PS Application
			logger = extent.startTest(this.getClass().getSimpleName(), "Update Emergency contact - Individual");
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

			PM_Homepage pmhome = new PM_Homepage(driver);
			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "Entered into advance search page");
			Thread.sleep(5000);
			Advance_Search advSearch = new Advance_Search(driver);
			String location = advSearch.getLocationNum();
			// Advance search page


			String query =  "select top 1 u.accountnumber from vw_unitdetails u "
					+ "join storageorderitemcontact soic with(nolock) on soic.storageorderitemid = u.storageorderitemid "
					+ "where u.vacatedate is null "
					+ "and u.customertypeid = 90 "
					+ "and 1 = (select count(soic2.storageorderitemcontactid) from storageorderitemcontact soic2 "
					+ "where soic2.storageorderitemid = u.storageorderitemid "
					+ "and soic2.contacttypeid = 3151)";

			String AccountNumber = DataBase_JDBC.executeSQLQuery(query);


			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.PASS, "Account number entered successfully:" +AccountNumber);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "Image", image2);

			advSearch.clk_SearchAccount();


			Thread.sleep(10000);

			// Taking Screenshot
			scpath2 = Generic_Class.takeScreenShotPath();
			image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
			logger.log(LogStatus.INFO, "Navigated to Customer Dashboard successfully", image2);

			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);




			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(15000);

			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Customer Account Dashboard Page ",image);




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


			//Getting data for Emergency contact  Before Update	



			String EmergencyContactBeforUpdate=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']/div[1]")).getText();


			logger.log(LogStatus.INFO, "Emergency Contact Befor update is :  " +  EmergencyContactBeforUpdate);
			Thread.sleep(4000);
			String snap=Generic_Class.takeScreenShotPath();
			String irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			EmergencyContactPopup emrgncyPop = new EmergencyContactPopup(driver);
			emrgncyPop.click_EditBtn();
			Thread.sleep(5000);

			//Verify that the Customer is present and 'Yes' option is selected in Is Customer Present selection 

			if(driver.findElement(By.xpath("//span[text()='Is the customer present?']")).isDisplayed()){

				logger.log(LogStatus.PASS, "Yes option is selected in Is Customer present ");

			} else {

				logger.log(LogStatus.FAIL, "Yes option is not selected in Is Customer present  ");

				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}








			IsCustomerPresent custPrsnt = new IsCustomerPresent(driver);
			custPrsnt.click_YesBtn();
			Thread.sleep(2000);
			custPrsnt.click_ContinueBtn();


			Thread.sleep(15000);




			/*try {
                String scpath1 = Generic_Class.takeScreenShotPath();
                Reporter.log(scpath1, true);
                String image1 = logger.addScreenCapture(scpath1);
                logger.log(LogStatus.INFO, "Handling Pop Up");
                logger.log(LogStatus.INFO, "Pop Handled", image1);
                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
                Thread.sleep(4000);
          } catch (Exception e) {
                String scpath1 = Generic_Class.takeScreenShotPath();
                Reporter.log(scpath1, true);
                String image1 = logger.addScreenCapture(scpath1);
                logger.log(LogStatus.INFO, "Pop Up Unavailable");
                logger.log(LogStatus.INFO, "Pop Up Not Available", image1);
          }
			 */















			snap=Generic_Class.takeScreenShotPath();
			irm=logger.addScreenCapture(snap);	
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "img",irm);

			EmergncyContctpage emerContact = new EmergncyContctpage(driver);



			//Updating the Emergency Contact


			// Enter First name


			String data = Generic_Class.get_RandmString();
			//int randomNum = 1400 + (int)(Math.random() * 1600); 
			String UpdatedFirstName="Robert"+data +"Mask";

			Thread.sleep(3000);


			String data1 = Generic_Class.get_RandmString();
			String UpdatedLastName=data1+"Jonson";

			Thread.sleep(3000);

			int LineNo = 9000 + (int) (Math.random() * 1400);
			String LineNumber = Integer.toString(LineNo);









			emerContact.enter_FirstName(UpdatedFirstName);
			logger.log(LogStatus.INFO, " First Name Updated successfully as : " + UpdatedFirstName );

			emerContact.enter_LastName(UpdatedLastName);


			logger.log(LogStatus.INFO, " Last Name Updated successfully as : " + UpdatedLastName );

			emerContact.selectRelationship(tabledata.get("Relationship"));
			Thread.sleep(2000);
			emerContact.enter_Address1(tabledata.get("Address1"));
			emerContact.enter_City(tabledata.get("City"));
			emerContact.selectState(tabledata.get("State"));
			Thread.sleep(2000);
			emerContact.enter_Zip(tabledata.get("Zip"));
			emerContact.selectPhoneType("Cell");
			Thread.sleep(2000);
			emerContact.enter_AreaCode(tabledata.get("AreaCode"));
			emerContact.enter_Exchange(tabledata.get("Exchange"));
			Thread.sleep(2000);
			emerContact.enter_LineNumber(LineNumber);




			String number = Generic_Class.get_RandmString();

			String Email=number+"psitqa@gmail.com";


			emerContact.enter_Email(Email);

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
			Thread.sleep(2000);
			//emerContact.click_VerifyBtn();
			//Thread.sleep(4000);
			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
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
			logger.log(LogStatus.INFO, "When clicked on clear button, Customer's signature is cleared");
			Thread.sleep(3000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			actionBuilder = new Actions(driver);
			drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);


			snap=Generic_Class.takeScreenShotPath();
			irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);






			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(3000);
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			emerContact.click_ApproveBtn();

			Thread.sleep(3000);

			boolean isapprv = emerContact.isApproved();
			if (isapprv) {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Signature approved message displayed after clicking on approve button");
				logger.log(LogStatus.INFO, "Image", image);
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
			Thread.sleep(8000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			empIdPage.click_ContinueBtn();
			Thread.sleep(30000);
			

			try {
              
                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
                Thread.sleep(8000);
          } catch (Exception e) {
              
          }

			
			
			
			


			logger.log(LogStatus.PASS,
					"Navigated to customer dashboard after entering the employee id and clicking on continue button");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			cust_accdetails = new Cust_AccDetailsPage(driver);
			cust_accdetails.click_AccountActivities();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, " Clicked on Account Activities Tab");

			String getText = driver.findElement(By.xpath("//table[@role='treegrid']/tbody/tr[1]")).getText();
			if (getText.contains("Edit Emergency Contact")||getText.contains("Emergency contact info changed by")) {
				logger.log(LogStatus.PASS,
						"Emergency Contact Info change transaction found in Account Activities page");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"Emergency Contact Info change transaction not found in Account Activities page");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			//driver.switchTo().window(tabs.get(1));
			Thread.sleep(6000);

			/*if (!(driver.findElements(By.partialLinkText("please print copies of my documents")).size() == 0)) {
				logger.log(LogStatus.PASS,
						"Change of Information form, including applicable communication printout is generated ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.FAIL,
						"Change of Information form, including applicable communication printout is not generated ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}

			if (!(driver.findElements(By.xpath("//div[@id='customerScreenContent']//div[text()='Thank you!']"))
					.size() == 0)) {
				logger.log(LogStatus.PASS, "Thank You screen is displayed in CFS");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Thank You screen is not displayed in CFS");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			 */
			//driver.switchTo().window(tabs.get(0));

			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(8000);
			spaceDtls.click_EmergencyContact();
			Thread.sleep(4000);

			String EmergencyContactAfterUpdate=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']/div[1]")).getText();


			logger.log(LogStatus.INFO, "Emergency Contact After update is :  " +  EmergencyContactAfterUpdate);

			if(EmergencyContactBeforUpdate.equals(EmergencyContactAfterUpdate)){
				logger.log(LogStatus.FAIL, " Emergency Contact is not updated");
			}

			else{
				logger.log(LogStatus.PASS, " Emergency Contact is  updated successfully : Emergency contact Before update is : " +EmergencyContactBeforUpdate +"Emergency Contact After Update is : " + EmergencyContactAfterUpdate );
			}






			// Verify that the changes to authorized access information shall be
			// saved to the database 
			String query1 = "select count(*) from contact where firstName='" + UpdatedFirstName + "'";
			String Record = DataBase_JDBC.executeSQLQuery(query1);
			Thread.sleep(2000);

			int totalRecord = Integer.parseInt(Record);
			if (totalRecord ==1) {
				logger.log(LogStatus.PASS,
						"The changes to Emergency contact  are saved in the database and Updated First name is : " + UpdatedFirstName);
			}else{
				logger.log(LogStatus.FAIL,
						"The changes to Emergency contact for Firstname are not saved in the database ");
			}

			Thread.sleep(2000);
			String query11 = "select count(*) from contact where Lastname='" + UpdatedLastName + "'";
			String Record1 = DataBase_JDBC.executeSQLQuery(query11);
			Thread.sleep(2000);

			int totalRecord1 = Integer.parseInt(Record1);
			if (totalRecord1 ==1 ) {
				logger.log(LogStatus.PASS,
						"The changes to Emergency contact information  are saved in the database and Updated Last Name is : " + UpdatedLastName);
			}else{
				logger.log(LogStatus.FAIL,
						"The changes to Emergency contact for Lastname are not saved in the database ");
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
