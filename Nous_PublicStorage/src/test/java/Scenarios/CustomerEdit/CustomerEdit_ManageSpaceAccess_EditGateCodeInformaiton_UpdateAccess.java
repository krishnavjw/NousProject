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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.CustInfoPages.Cust_OtherStatusesPage;
import Pages.CustInfoPages.ManagePropertySpaceAccess;
import Pages.EditAccountDetails.OtherStatuses;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getCustSearchData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit",
				"CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess");
	}

	@Test(dataProvider = "getCustSearchData")
	public void customerEdit_Individual(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest(this.getClass().getSimpleName(), "CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login to PS Application
		
			//Login To the Application
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
						Thread.sleep(9000);
						// =================================================================================

			PM_Homepage pmhome = new PM_Homepage(driver);
			pmhome.clk_AdvSearchLnk();
		
			Thread.sleep(5000);
			Advance_Search advSearch = new Advance_Search(driver);
			String location = advSearch.getLocationNum();
			// Advance search page
						/*String query ="select top 1 u.accountnumber from vw_unitdetails u with(nolock) "
								+ "join contactgate cg on cg.contactid = u.contactid "
								+ "join site s on s.siteid = u.siteid "
								+ "where u.vacatedate is null "
								+ "and u.customertypeid = 90 "
								+ "and s.sitenumber ='"+location+"' " 
								+ "and cg.disableaccess = 0 "
								+ "and cg.gatecontrollertimezoneid is not null "
								+ "and cg.gatecontrollerkeypadid is not null";*/
						
						
						
						
						String query = "select top 1 u.accountnumber from vw_unitdetails u with(nolock) "
								+ "join contactgate cg on cg.contactid = u.contactid "
								+ "join site s on s.siteid = u.siteid "
								+ "where u.customertypeid = 90 "
								+ "and s.sitenumber ='"+location+"' "
								+ "and cg.disableaccess = 0 "
								+ "and cg.gatecontrollertimezoneid is not null "
								+ "and cg.gatecontrollerkeypadid is not null "
								+ "group by u.accountnumber "
								+ "having count(u.accountorderitemid) = 1";
						
						
						
						  String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
						 

						advSearch.enterAccNum(AccountNumber);
						logger.log(LogStatus.PASS, "Account number entered successfully:" +AccountNumber);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
		
			Thread.sleep(9000);
			
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on search button");

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
			if (cust.Verify_CustDashboard()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer dashboard is  displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer dashboard is  not displayed");
				logger.log(LogStatus.INFO, "Customer dashboard is  not displayed", image);

			}

		

			//js.executeScript("window.scrollBy(1000,0)", "");

			cust.click_EditAccDetails();
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Clicked on Edit Account details link successfully");

			Cust_EditAccountDetailsPage edit = new Cust_EditAccountDetailsPage(driver);
			if (edit.verify_EditAccDetails_Title()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account details Modal window is displayed successfully");
				logger.log(LogStatus.INFO, "Edit Account details Modal window successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account details Modal window not displayed");
				logger.log(LogStatus.INFO, "Edit Account details Modal window not displayed", image);

			}

			Thread.sleep(1000);
			edit.clickYesRadioBtn();
			Thread.sleep(1000);

			edit.clickManagePropertyRadioBtn();
			logger.log(LogStatus.INFO, " Clicked on Manage Property/Space Access' check box ");

			edit.clickLaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Launch button successfully");
			Thread.sleep(7000);

			ManagePropertySpaceAccess manageSpace = new ManagePropertySpaceAccess(driver);
			
			if (manageSpace.isDisplayed_pageTitle_ManageSpace()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " User navigate to Manage Property/Space Access screen");
				logger.log(LogStatus.INFO, "img", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User not navigated to Manage Property/Space Access screen");
				logger.log(LogStatus.INFO, "img", image);

			}
			
			
			String GateCode = driver.findElement(By.xpath("//input[@id='SpacesWithGateInformation_0__Gates_0__Code']")).getAttribute("value");
			
			logger.log(LogStatus.INFO, "Gate code is :" + GateCode );		
			
			//manageSpace.select_AccessZone();
			driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[1]")).click();
			Thread.sleep(3000);
			
			Actions act=new Actions(driver);
			
			
			
			
			List<WebElement> listAccessZone = driver.findElements(By.xpath("//ul[@id='SpacesWithGateInformation[0]_Gates[0]_TimeZoneID_listbox']/li"));
			String accessZone="";
			
			for(WebElement accZone:listAccessZone){
				if(accZone.getText().contains("02-02")){
					accessZone=accZone.getText();
					act.moveToElement(accZone).click().build().perform();
					break;
				}
			}
			
			
			
			
			Thread.sleep(4000);
			logger.log(LogStatus.INFO, " Selectrd Access Zone :" + accessZone );	
			
			driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[2]")).click();
			Thread.sleep(3000);
			
			
			
			
			List<WebElement> listKeypadZone = driver.findElements(By.xpath("//ul[@id='SpacesWithGateInformation[0]_Gates[0]_KeyPadDescription_listbox']/li"));
			String KeypadZoneselected="";
			
			for(WebElement keypadZone:listKeypadZone){
				act.moveToElement(keypadZone).build().perform();
				if(keypadZone.getText().contains("2-Gate 3")){
					KeypadZoneselected=keypadZone.getText();
					act.moveToElement(keypadZone).click().build().perform();
					break;
				}
			}
			
			
			
			
			logger.log(LogStatus.INFO, " Selectrd Keypad Zone :" + KeypadZoneselected );	
			
			
			manageSpace.click_save_Btn();
			
			logger.log(LogStatus.INFO, "Clicked on save button" );	
			
			Thread.sleep(4000);
			
			
			
			if (driver.findElement(By.xpath("//span[text()='Enter Employee ID']")).isDisplayed()) {
				logger.log(LogStatus.PASS, "Enter Employee id modal window is displayed");
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);

				logger.log(LogStatus.INFO, "Enter Employee id modal window is displayed", image);

			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Enter Employee id modal window not  displayed");
				logger.log(LogStatus.INFO, "Enter Employee id modal window not  displayed", image);

			}

			
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			driver.findElement(By.partialLinkText("Continue")).click();
			Thread.sleep(30000);
			logger.log(LogStatus.INFO, "Entered notes and Employee number and clicked on Continue  btn");
			Thread.sleep(12000);
			
         try{
				
			driver.findElement(By.xpath("	//a[contains(text(),'OK')]")).click();
			
			}
			
			catch(Exception ex){
				ex.getMessage();
			}

			
     	Thread.sleep(12000);
			
			

		//Verify the page navigates back to customer account dashboard
     	Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
		if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){
			
		
			logger.log(LogStatus.PASS, " User navigated back to customer account dashboard");
			  String snap = Generic_Class.takeScreenShotPath();
			 String irm = logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);        ;
		
		}

		else {
			
			logger.log(LogStatus.FAIL, "User not  navigated back to customer account dashboard ");
		}
		
		
		
		
		
		
		
		//Verify the BankruptcyDeclaration table for changes made:
		
		/*String sqlQuery1 ="select TZ.Description, KP.Description from contactgate cg "
+ "join gatecontrollertimezone TZ on TZ.gatecontrollertimezoneid = cg.gatecontrollertimezoneid "
+ "join gatecontrollerkeypad KP on KP.gatecontrollerkeypadid = cg.gatecontrollerkeypadid "
+ "where cg.gatecode = '"+GateCode+"'";*/
		
		String sqlQuery1 = "select TZ.Description, KP.Description from contactgate cg "
				+ "join gatecontrollertimezone TZ on TZ.gatecontrollertimezoneid = cg.gatecontrollertimezoneid "
				+ "join gatecontrollerkeypad KP on KP.gatecontrollerkeypadid = cg.gatecontrollerkeypadid "
				+ "join vw_unitdetails u on u.contactid = cg.contactid "
				+ "where u.accountnumber = '"+AccountNumber+"'";
		
		
		

		  ArrayList<String> gateCode = DataBase_JDBC.executeSQLQuery_List(sqlQuery1);
		 String AccessZoneDB = gateCode.get(0);
		 String keypadZoneDB = gateCode.get(1);
		
		
		
		if(accessZone.contains(AccessZoneDB)){
			logger.log(LogStatus.PASS, " Access Zone selected from UI and Fetched from DB are matched :"+AccessZoneDB );
		}
		
    else {
			
			logger.log(LogStatus.FAIL, "Access Zone selected from UI and Fetched from DB are not matched and not updated in DB ");
		}
		
		
		if(KeypadZoneselected.contains(keypadZoneDB)){
			logger.log(LogStatus.PASS, " Keypad Zone selected from UI and Fetched from DB are matched :"+keypadZoneDB );
		}
		
    else {
			
			logger.log(LogStatus.FAIL, "Keypad Zone selected from UI and Fetched from DB are not matched and not updated in DB  ");
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
			Excel.setCellValBasedOnTcname(path, "CustomerEdit",
					"CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit",
					"CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit",
					"CustomerEdit_ManageSpaceAccess_EditGateCodeInformaiton_UpdateAccess", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}
}
