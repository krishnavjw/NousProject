


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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_Update_CommPreferences extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getCustomerEdit() 
	{
		return Excel.getCellValue_inlist(path, "CustomerEdit","CustomerEdit", "CustomerEdit_Update_CommPreferences");
	}


	@Test(dataProvider="getCustomerEdit")
	public void CustomerEditEmail(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{

			//Login to PS Application
			logger=extent.startTest("CustomerEdit_Update_CommPreferences","Customer Edit Individual - Add,Update,Delete Comm Preferences");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

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

			PM_Homepage pmhomepage = new PM_Homepage(driver);


			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
			Thread.sleep(5000);			


			// Advance search page
			Advance_Search advSearch = new Advance_Search(driver);

			String query ="select top 1 u.accountnumber, t.name from vw_unitdetails u with(nolock) "
					+ "join EmailOptInProfile E on e.accountid = u.accountid "
					+ "join type t on t.typeid = e.notificationtypeid "
					+ "where u.vacatedate is null "
					+ "and u.customertypeid = 90 "
					+ "and e.notificationtypeid = 1531 "
					+ "and e.optout = 0";

			String AccountNumber = DataBase_JDBC.executeSQLQuery(query);




			String query1 ="select AccountID from account where accountNumber='"+AccountNumber+"'";

			String AccountID = DataBase_JDBC.executeSQLQuery(query1);






			advSearch.enterAccNum(AccountNumber);



			//advSearch.enterAccNum(tabledata.get("AccountNumber"));
			logger.log(LogStatus.INFO, "Account number entered successfully :" + AccountNumber);




			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);
			advSearch.clickButton();
			Thread.sleep(20000);
			logger.log(LogStatus.INFO, "Clicked Search button successfully");

			// Verify Tabs are present in Customer Account dashboard
			Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);

			if (AccDetails.verify_CustomerInfoTab()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " User Navigate to Customer Account dashboard");
				logger.log(LogStatus.INFO, "img", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User not Navigate to Customer Account dashboard");
				logger.log(LogStatus.INFO, "img", image);
			}

			if (AccDetails.verify_SpaceDetailsTab()) {

				Thread.sleep(2000);

				logger.log(LogStatus.PASS, "Space Details tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Space Details tab is not displayed");
				logger.log(LogStatus.INFO, "Space Details tab is not displayed", image);
			}

			if (AccDetails.verify_AccountActivitiesTab()) {

				Thread.sleep(2000);

				logger.log(LogStatus.PASS, "Account Activities tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account Activities tab is not displayed");
				logger.log(LogStatus.INFO, "Account Activities tab is not displayed", image);
			}

			if (AccDetails.verify_DocumentsTab()) {



				logger.log(LogStatus.PASS, "Documents tab is displayed successfully");

			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Documents tab is not displayed");
				logger.log(LogStatus.INFO, "Documents tab is not displayed", image);
			}

			Thread.sleep(3000);

			// Click on Edit Account Details Link
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);
			Cust_AccDetailsPage detailpage = new Cust_AccDetailsPage(driver);

			detailpage.clk_EditAccDetails();
			logger.log(LogStatus.INFO, "Clicked on Edit Account Details Button");
			Thread.sleep(6000);


			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "The window to choose the workflow  poped up", image);




			driver.findElement(By.xpath("//span[text()='Email Communication Preferences']/preceding-sibling::span")).click();

			logger.log(LogStatus.INFO, "Clicked on Email Communication Preferences radio Button");


			driver.findElement(By.xpath("//a[contains(text(),'Launch')]")).click();
			Thread.sleep(6000);


			/*	
						driver.findElement(By.xpath("(//label[@id='IsOptOut']/span[@class='button'])[2]")).click();


						driver.findElement(By.xpath("(//label[@id='IsOptOut']/span[@class='button'])[3]")).click();



						logger.log(LogStatus.PASS, "Click the 'Opt out' Check box for Email type 'Past Due reminder' and 'monthly invoice'.");
			 */

			/*
						EmergncyContctpage emerContact = new EmergncyContctpage(driver);


						emerContact.click_ConfirmBtn();
						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(1));
						driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
						Thread.sleep(6000);
						logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

						WebElement sig = driver
								.findElement(By.xpath("//div[@id='customerScreenContent']//div[@class='signature-area']//canvas[@class='signature-pad']"));
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


						 String snap = Generic_Class.takeScreenShotPath();
						 String irm = logger.addScreenCapture(snap);	
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



	                       //Verify the page navigates back to customer account dashboard

						if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){


							logger.log(LogStatus.PASS, " User navigated back to customer account dashboard");
							  snap=Generic_Class.takeScreenShotPath();
							 irm=logger.addScreenCapture(snap);	
							logger.log(LogStatus.INFO, "img",irm);        ;

						}

						else {

							logger.log(LogStatus.FAIL, "User not  navigated back to customer account dashboard ");
						}*/

			//driver.get("http://wc2dev.ps.com/Customer/EditEmailPreferences/"+AccountNumber+"");


			Thread.sleep(6000);

			driver.findElement(By.xpath("(//label[@id='IsOptOut']/span[@class='button'])[1]")).click();


			logger.log(LogStatus.INFO, "Opt out Checkbox is selected for Autopay receipt");

			Thread.sleep(2000);

			Thread.sleep(6000);


			EmergncyContctpage emerContact = new EmergncyContctpage(driver);


			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

			WebElement sig = driver
					.findElement(By.xpath("//div[@id='customerScreenContent']//div[@class='signature-area']//canvas[@class='signature-pad']"));
			Actions action = new Actions(driver);
			Action Operation = action.moveToElement(sig, 660, 96).click().clickAndHold(sig).moveByOffset(120, 120)
					.moveByOffset(60, 70).moveByOffset(-140, -140).release(sig).build();
			Operation.perform();
			/*Thread.sleep(6000);
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
			 */

			String snap = Generic_Class.takeScreenShotPath();
			String irm = logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		
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



			//Verify the page navigates back to customer account dashboard

			if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){


				logger.log(LogStatus.PASS, " User navigated back to customer account dashboard");
				snap=Generic_Class.takeScreenShotPath();
				irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);        ;

			}

			else {

				logger.log(LogStatus.FAIL, "User not  navigated back to customer account dashboard ");
			}


			String varname1 = ""
					+ "select t.name,e.OptOut from EmailOptInProfile e join type t on e.notificationtypeid=t.typeid where e.accountid='"+AccountID+"' order by t.name";

			ArrayList<String> lst1=DataBase_JDBC.executeSQLQuery_List(varname1);
			String optOutValue1=lst1.get(1);




			if(optOutValue1.equals("1")){
				logger.log(LogStatus.PASS, " After making the optIn for Autopay, the OptOut value is set to "+optOutValue1+"in DB" );
			}

			else {

				logger.log(LogStatus.FAIL, "After making the optIn for Autopay , the OptOut value is not set to "+optOutValue1+"in DB" );
			}




			/*				


						//Verify the EmailOptInProfile table for changes:

						String sqlQuery1 =  "select t.name,e.OptOut from EmailOptInProfile e join type t on e.notificationtypeid=t.typeid where e.accountid='"+AccountID+"'";

						 ArrayList<String> emailPreferences = DataBase_JDBC.executeSQLQuery_List(sqlQuery1);




						 System.out.println("============="+emailPreferences.get(0));
						 System.out.println(emailPreferences.get(5));
						 System.out.println(emailPreferences.get(9));


						if(emailPreferences.get(5).equals("1") && emailPreferences.get(9).equals("1")){
							logger.log(LogStatus.PASS, " OptOut' field holds value 1 for Type Past Due reminder and monthly invoice in DB");
						}

			else {

							logger.log(LogStatus.FAIL, "OptOut' field not holds value 1 for Type Past Due reminder and monthly invoice in DB  ");
						}
			 */



			String query2 = ""
					+ "select top 1 u.accountnumber, t.name from vw_unitdetails u with(nolock) "
					+ "join EmailOptInProfile E on e.accountid = u.accountid "
					+ "join type t on t.typeid = e.notificationtypeid "
					+ "where u.vacatedate is null "
					+ "and u.customertypeid = 90 "
					+ "and e.notificationtypeid = 1532 "
					+ "and e.optout = 1";

			ArrayList<String> lst=DataBase_JDBC.executeSQLQuery_List(query2);

			String accnum=lst.get(0);
			
			String accid=DataBase_JDBC.executeSQLQuery("select accountid from account where accountnumber='"+accnum+"'");
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "The account number which is not opted in for Past Due Remainder is: "+accnum);

			driver.get("http://wc2dev.ps.com/Customer/EditEmailPreferences/"+accnum+"");
			Thread.sleep(6000);

			/*emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

			WebElement sig1 = driver
					.findElement(By.xpath("//div[@id='customerScreenContent']//div[@class='signature-area']//canvas[@class='signature-pad']"));
			Actions action1 = new Actions(driver);
			Action Operation1 = action1.moveToElement(sig, 660, 96).click().clickAndHold(sig1).moveByOffset(120, 120)
					.moveByOffset(60, 70).moveByOffset(-140, -140).release(sig1).build();
			Operation1.perform();
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
			 

			snap = Generic_Class.takeScreenShotPath();
			irm = logger.addScreenCapture(snap);	
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



			//Verify the page navigates back to customer account dashboard

			if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){


				logger.log(LogStatus.PASS, " User navigated back to customer account dashboard");
				snap=Generic_Class.takeScreenShotPath();
				irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);        ;

			}

			else {

				logger.log(LogStatus.FAIL, "User not  navigated back to customer account dashboard ");
			}

*/
			//driver.get("http://wc2dev.ps.com/Customer/EditEmailPreferences/"+accnum+"");
			Thread.sleep(5000);

			driver.findElement(By.xpath("(//label[@id='IsOptIn']/span[@class='button'])[2]")).click();

			logger.log(LogStatus.INFO, "Opt in Checkbox is selected for Past Due Remainder");

			Thread.sleep(2000);

			Thread.sleep(6000);

			emerContact.click_ConfirmBtn();
			Thread.sleep(6000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
		
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");
			
			WebElement sig1 = driver
					.findElement(By.xpath("//div[@id='customerScreenContent']//div[@class='signature-area']//canvas[@class='signature-pad']"));
			Actions action1 = new Actions(driver);
			Action Operation1 = action1.moveToElement(sig1, 660, 96).click().clickAndHold(sig1).moveByOffset(120, 120)
					.moveByOffset(60, 70).moveByOffset(-140, -140).release(sig1).build();
			Operation1.perform();
			/*Thread.sleep(6000);
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
			 */

			snap = Generic_Class.takeScreenShotPath();
			irm = logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			driver.findElement(By.xpath("//button[text()='Accept']")).click();

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		
			Thread.sleep(3000);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");

			emerContact.click_ApproveBtn();

			Thread.sleep(3000);
			emerContact.click_SaveBtn();

			Thread.sleep(8000);


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



			//Verify the page navigates back to customer account dashboard

			if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){


				logger.log(LogStatus.PASS, " User navigated back to customer account dashboard");
				snap=Generic_Class.takeScreenShotPath();
				irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);        

			}

			else {

				logger.log(LogStatus.FAIL, "User not  navigated back to customer account dashboard ");
			}




			String varname11 = ""
					+ "select t.name,e.OptOut from EmailOptInProfile e join type t on e.notificationtypeid=t.typeid where e.accountid='"+accid+"'";

			ArrayList<String> lst11=DataBase_JDBC.executeSQLQuery_List(varname11);
			String optOutValue=lst11.get(1);





			if(optOutValue.equals("0")){
				logger.log(LogStatus.PASS, " After making the optIn for PastDue Remainder, the OptOut value is set to "+optOutValue+"in DB" );
			}

			else {

				logger.log(LogStatus.FAIL, "After making the optIn for PastDue Remainder, the OptOut value is not set to "+optOutValue+"in DB" );
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
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Update_CommPreferences" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Update_CommPreferences" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Update_CommPreferences" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}



}
