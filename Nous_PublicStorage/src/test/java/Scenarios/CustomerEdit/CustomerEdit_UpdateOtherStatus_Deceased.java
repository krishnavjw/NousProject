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
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.EditAccountDetails.OtherStatuses;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_UpdateOtherStatus_Deceased extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getCustomerEditData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit",
				"CustomerEdit_UpdateOtherStatus_Deceased");
	}

	@Test(dataProvider = "getCustomerEditData")
	public void CustomerEdit_UpdateOtherStatus_Deceased(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {
			logger = extent.startTest("CustomerEdit_UpdateOtherStatus_Deceased",
					"CustomerEdit_UpdateOtherStatus_Deceased starts ");

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login To the Application
						LoginPage loginPage = new LoginPage(driver);
						loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
						logger.log(LogStatus.INFO, "Login to Application  with DM  successfully");


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
						  Dashboard_BifrostHostPopUp Bifrostpop= new
						  Dashboard_BifrostHostPopUp(driver); logger.log(LogStatus.INFO,
						  "PopUp window object is created successfully");
						  
						  Bifrostpop.clickContiDevice();*/
						 

						DM_HomePage dmhome = new DM_HomePage(driver);
						if (dmhome.is_DMDashBoardTitle()) {

							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "DM  Dashboard displayed successfully ");
							logger.log(LogStatus.PASS, "Image", image);
						}

						else {
							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "DM  Dashboard not displayed successfully  ");
							logger.log(LogStatus.FAIL, "DM  Dashboard not displayed successfully ", image);
						}

						PM_Homepage pmhome = new PM_Homepage(driver);
						Thread.sleep(1000);
						
						
						//Select location
						/*try{
							
						
						driver.findElement(By.xpath("//form[@id='QuickSearchForm']//span[@class='k-input']")).click();
						Thread.sleep(2000);
						
						List<WebElement> AllOptions = driver.findElements(By.xpath("//div[@id='siteId-list']/ul/li"));
						int totalSize = AllOptions.size();
						for(int i=0;i<totalSize;i++ ){
							if(i==1){
								AllOptions.get(i).click();
								break;
							}
						}
						
						}
						
						catch(Exception ex){
							
						}*/
						
						
						// Click on Advance Search
						pmhome.clk_DMAdvSearchLnk();
						String location = pmhome.getLocation();
						logger.log(LogStatus.INFO, "Entered into advance search page");

						// Advance search page
						Advance_Search advSearch = new Advance_Search(driver);

						String sqlQuery = "Select  top 1 A.AccountNumber from Account A with(nolock) "
								+ "Join AccountAddress AA with(nolock) on AA.AccountID = A.AccountID "
								+ "Join Address Ad with(nolock) on AA.AddressID = Ad.AddressID "
								+ "Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "
								+ "Join Contact C with(nolock) on CU.ContactID = C.ContactID "
								+ "Join CustomerClass CC with(nolock) on CU.CustomerClassID = CC.CustomerClassID "
								+ "Join AccountOrder AO with(nolock) on A.AccountID = AO.AccountID "
								+ "Join AccountOrderItem AOI with(nolock) on AO.AccountOrderID  = AOI.AccountOrderID "
								+ "Join StorageOrderItem SOI with(nolock)on AOI.StorageOrderItemID = SOI.StorageOrderItemID "
								+ "Join Type T with(nolock) on T.TypeID = SOI.StorageOrderItemTypeID "
								+ "left join customermilitaryinfo cmi with(nolock) on cmi.customerid=Cu.customerid "
								+ "left join Type T1 with(nolock) on T1.typeid=cmi.militarytypeid "
								+ "Where 1=1 "
								+ "And SOI.VacateDate is Null "
								+ "And CU.CustomerTypeID=90 "
								+ "And CU.IsDeceased = 0 "
								+ "And t1.name='Active Duty'  and cmi.isactive=1";

						String AccountNumber = DataBase_JDBC.executeSQLQuery(sqlQuery);
						
					

						advSearch.enterAccNum(AccountNumber);
					

						logger.log(LogStatus.INFO, "Account number entered successfully:" + AccountNumber);


						((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
						Thread.sleep(1000);

						advSearch.clk_SearchAccount();
					
						Thread.sleep(2000);
				
						Thread.sleep(1000);
						
						//Verify Option in Customer Account dashboard
						Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
						if(AccDetails.getCustomerInfoTabTxt().contains("Customer Info")){
							
						
							logger.log(LogStatus.PASS, "Customer Info tab displayed successfully: " + AccDetails.getCustomerInfoTabTxt());
						
						}

						else {
							
							logger.log(LogStatus.FAIL, "Customer Info tab not  displayed successfully: ");
						}
						
						
			          if(AccDetails.getSpaceDetailsTabTxt().contains("Space Details")){
							
							
							logger.log(LogStatus.PASS, "Space Details tab displayed successfully: " + AccDetails.getSpaceDetailsTabTxt());
							
						}

						else {
							
							logger.log(LogStatus.FAIL, "Space Detailstab not  displayed successfully: ");
							
						}
			          
			          
			          if(AccDetails.getAccountActivitiesTabTxt().contains("Account Activities")){
							
							
							logger.log(LogStatus.PASS, "Account Activitiestab displayed successfully: " + AccDetails.getAccountActivitiesTabTxt());
							
						}

						else {
							Thread.sleep(2000);
							
							logger.log(LogStatus.FAIL, "Account Activities not  displayed successfully: ");
							
						}
			          
			          
			          if(AccDetails.getDocumentsTabTxt().contains("Documents")){
							
							
						
							logger.log(LogStatus.PASS, "Documents tab  displayed successfully: " + AccDetails.getDocumentsTabTxt());
							
						}

						else {
							
							logger.log(LogStatus.FAIL, "Documents tab not  displayed successfully: ");
							
						}
						
						
			          Thread.sleep(2000);
						
						
						AccDetails.clk_editAcc_btn();
						logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");
						
						
						//Verify  Edit Account Details Modal window displayed
						EditAccountDetails editAccountDetails=new EditAccountDetails(driver); 
						if(editAccountDetails.isEditAccDetailsPopUpDisplayed()){
						
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
					
						
						//Click on Other Customer Status Radio Button
						
						//editAccountDetails.clk_OtherCustomerStatusRadioBtn();
						
						driver.findElement(By.xpath("//span[text()='Deceased Status']/preceding-sibling::span")).click();
						logger.log(LogStatus.PASS, "Clicked on Deceased Status radio button successfully ");
						Thread.sleep(2000);
						
						editAccountDetails.clk_LaunchBtn();
						logger.log(LogStatus.PASS, "Clicked on Launch button successfully ");
						Thread.sleep(4000);
						
						OtherStatuses otherStatuses=new OtherStatuses(driver);
						if(otherStatuses.OtherStatusesTitleDisplayed() && otherStatuses.Bankruptcy_txtDisplayed() &&  otherStatuses.DoNotRent_txtDisplayed()){
						
							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "Other Statuses Title ,Bankruptcy ,Do Not Rent displayed successfully ");
							logger.log(LogStatus.PASS, " img ", image);
						}

						else {
							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "All the Details not displayed successfully ");
							logger.log(LogStatus.FAIL, "img ", image);
						}
						
						
						//Click on Declared checkbox
						otherStatuses.click_NotifiedCheckbox();
						logger.log(LogStatus.PASS, "Clicked on Notified checkbox successfully ");
						
						Thread.sleep(4000);
						
					
						//Verify  Bankruptcy Note field displayed
						
						if(otherStatuses.BankruptcyNote_txtAreaDisplayed()){
							
							logger.log(LogStatus.PASS, "BankruptcyNote txt area displayed successfully ");
							
						}

						else {
							
							logger.log(LogStatus.FAIL, "BankruptcyNote txt area not displayed successfully ");
							
						}
						
						
						//Verify Scan button is displayed
						if(otherStatuses.Scan_buttonDisplayed()){
							
							logger.log(LogStatus.PASS, "Scan button displayed successfully ");
						
						}

						else {
							
							logger.log(LogStatus.FAIL, "Scan button not displayed successfully ");
							
						}
						
						//Verify Info message is displayed 
						if(otherStatuses.get_InfoMessageTxt().contains("Access to space is allowed regardless of payment status")){
							
							logger.log(LogStatus.PASS, "Access to space is allowed regardless of payment status message: message is displayed  successfully ");
							
						}

						else {
							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Access to space is allowed regardless of payment status message: message not  displayed  successfully ");
							logger.log(LogStatus.FAIL, "Access to space is allowed regardless of payment status message: message not  displayed  successfully ", image);
						}
						
						
						
						Thread.sleep(2000);

			           String snap=Generic_Class.takeScreenShotPath();
						String irm=logger.addScreenCapture(snap);	
						logger.log(LogStatus.INFO, "img",irm);              
						
						
						driver.findElement(By.xpath("//textarea[@id='DeceasedNote']")).sendKeys("Deceased Note");
						Thread.sleep(2000);
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
						Thread.sleep(2000);
						
						driver.findElement(By.xpath("//a[@id='submitButton']")).click();
						
						driver.findElement(By.xpath("//input[@id='employeeNumber']")).sendKeys(tabledata.get("UserName"));
						Thread.sleep(2000);
						
						driver.findElement(By.xpath("//a[contains(text(),'Continue')]")).click();
						Thread.sleep(25000);
						logger.log(LogStatus.PASS, "Entered employee ID and click on Continue button ");
						
						
						try {
							//driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
							driver.findElement(By.xpath("//div/a[@class='psbutton-priority margin-left ok-button']")).click();
							Thread.sleep(8000);
						}

						catch (Exception ex) {

						}
						
						
						
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
						
						
						
						
						
						
						
						//Verify the BankruptcyDeclaration table for changes made:
						
						String sqlQuery1 = "select  c.isdeceased from vw_unitdetails u "
                         + "join customer c on c.customerid = u.customerid "
                          + "where  u.accountnumber = '"+AccountNumber+"'";

						 String BankruptcyStatus = DataBase_JDBC.executeSQLQuery(sqlQuery1);

						
						
						if(BankruptcyStatus.equals("1")){
							logger.log(LogStatus.PASS, " isdeceased Column value fetched from DB and ther value is :" + BankruptcyStatus);
						}
						
			else {
							
							logger.log(LogStatus.FAIL, "isdeceased Column value not  fetched from DB  ");
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
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_UpdateOtherStatus_Deceased", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_UpdateOtherStatus_Deceased", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_UpdateOtherStatus_Deceased", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}
}
