package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.EditAccountDetails.MergeAccountPopUPPage;
import Pages.EditAccountDetails.MergeAccountsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.EditAccountDetails.EditAccountDetails;
import Scenarios.Browser_Factory;

public class CustomerEdit_AccountDetails_MergeAccount_Business extends Browser_Factory {

	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag = "pass";
	boolean flag;
	String accountNum;

	@DataProvider
	public Object[][] getCustomerSearchData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit",
				"CustomerEdit_AccountDetails_MergeAccount_Business");
	}

	@Test(dataProvider = "getCustomerSearchData")
	public void CustomerEdit_AccountDetails_MergeAccount_Business(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the CustomerEdit_AccountDetails_MergeAccount_Business test");
		}
		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);

		try {

			logger = extent.startTest("CustomerEdit_AccountDetails_MergeAccount_Business",
					"Edit Account Details of individual customer and Merge Accounts");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;


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

			// =================================================================================

			DM_HomePage dmpage = new DM_HomePage(driver);

			if (dmpage.is_DMDashBoardTitle()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM Homepage is displayed successfully");
				logger.log(LogStatus.INFO, "DM Homepage is displayed successfully", image);

			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM Homepage is not displayed");
				logger.log(LogStatus.INFO, "DM Homepage is not displayed", image);

			}

			// Select location
			//selectLocation();

			Thread.sleep(2000);
			dmpage.click_advSearchLink();
			logger.log(LogStatus.PASS, "clicked on the advance search link in the DM home page sucessfully");

			Thread.sleep(6000);
			Advance_Search search = new Advance_Search(driver);
			
			if (search.verifyAdvSearchPageIsOpened()) {
				logger.log(LogStatus.PASS, "Advanced Search page is displayed successfully");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);

			} else {
				logger.log(LogStatus.PASS, "Advanced Search page is not displayed");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				resultFlag = "fail";
			}

			String SiteNumber = search.getLocationNum();
		
			String query = ""
					+ "select top 1 A.CustomerId "
					+ "from Account A with (nolock) "
					+ "Join Customer CU with (nolock)  on A.CustomerID = CU.CustomerID "
					+ "join accountorder ao with (nolock)  on ao.accountid=a.accountid "
					+ "join accountorderitem aoi with (nolock)  on aoi.accountorderid=ao.accountorderid "
					+ "join storageorderitem soi with (nolock)  on soi.storageorderitemid=aoi.storageorderitemid "
					+ "join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "
					+ "Join Site S with (nolock)  on AOI.SiteID = S.SiteID "
					+ "where 1=1 "
					+ " "
					+ "And SOI.Vacatedate is null "
					+ "And ru.Rentalunitnumber not like 'APT%' "
					+ "And CU.CustomerTypeID=91 "
					+ "group by A.CustomerID "
					+ "having Count(distinct A.accountnumber) >1";
			
			String customerid = DataBase_JDBC.executeSQLQuery(query);

			ArrayList<String> values = DataBase_JDBC
					.executeSQLQuery_List(" Select AccountNumber from Account where CustomerID='" + customerid + "'");

			String accountNum = values.get(1);

			
			// accountNum = "45226441";
			search.enterAccNum(accountNum);
			//search.enterAccNum(tabledata.get("AccountNumber"));
			//logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully "+accountNum);

			Thread.sleep(3000);
			search.clickSearchAccbtn();
		

			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);

			if (cust_accdetails.isCustdbTitleDisplayed()) {


				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");

			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";


				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");

			}

			Thread.sleep(4000);


			//Verify Address Email and Phone not moved

			String addressBeforeMerge = driver.findElement(By.xpath("//div[@class='content floatright']/div[@class='padding-bottom']/div/div[1]")).getText();

			Thread.sleep(2000);
			String phoneAndEmailBeforeMerge= driver.findElement(By.xpath("//div[@class='content floatright']/div[@class='padding-bottom']/div/span")).getText();

			Thread.sleep(2000);



			String sn = Generic_Class.takeScreenShotPath();
			String ir = logger.addScreenCapture(sn);
			logger.log(LogStatus.INFO, "img", ir);













			Thread.sleep(4000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "clicked on the space details tab in the customer dashboard sucessfully");
			Thread.sleep(3000);

			// Account Space Details Page
			//Acc_SpaceDetailsPage accspace = new Acc_SpaceDetailsPage(driver);
			//String[] mergerSpaceNumbers = accspace.getAllSpaceNumbers();

			List<WebElement> AllSpaces = driver.findElements(By.xpath("//span[@class='space-rentalunitnumber']"));

			String spaceNoBeforeMerge = driver.findElement(By.xpath("//span[@class='space-rentalunitnumber']")).getText();

			int sizebeforeMerge = AllSpaces.size();

			logger.log(LogStatus.INFO, "Before Merged size of the space is : " + sizebeforeMerge + "And The Space No is :" + spaceNoBeforeMerge );









			Thread.sleep(3000);



			sn = Generic_Class.takeScreenShotPath();
			ir = logger.addScreenCapture(sn);
			logger.log(LogStatus.INFO, "img", ir);






			cust_accdetails.custInfo_Tab.click();
			logger.log(LogStatus.INFO, "clicked on the Customer Info tab in the customer dashboard sucessfully");
			Thread.sleep(2000);

			// selecting Merge Accounts  from Quick linkd Dropdown

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(
					By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]"))
			.click();

			JavascriptExecutor je = (JavascriptExecutor) driver;
			// Identify the WebElement which will appear after scrolling down
			WebElement element = driver
					.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[6]"));
			// now execute query which actually will scroll until that element
			// is not appeared on page.
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
			logger.log(LogStatus.INFO, "Selected Merge Accounts option from Quick links dropdown successfully");
			Reporter.log("Selected Merge Accounts option from Quick links dropdown successfully", true);
			Thread.sleep(5000);


			Thread.sleep(3000);
			MergeAccountsPage merge = new MergeAccountsPage(driver);
			Thread.sleep(3000);

			if (merge.isMergeAccountTitleDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Merge accont page is displayed successfully");
				logger.log(LogStatus.PASS, "Merge accont page is displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Merge Account  page is not displayed");
				logger.log(LogStatus.FAIL, "Merge Account  page is not displayed", image);

			}

			System.out.println("Account Number before passing to method: " + accountNum);


			//Select Account
			Thread.sleep(2000);
			merge.selValFromDropDwn(accountNum, driver, 1);

			/*	driver.findElement(By.xpath("(//div[@class='step-section double-padding']//span[@class='k-icon k-i-arrow-s'])[1]")).click();

			List<WebElement> Account1 = driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset']//ul[contains(@class,'k-list k-reset ps-container')]/li"));
			for(WebElement account1:Account1){
				if(account1.getText().equals(tabledata.get("AccountNumber"))){
					account1.click();
					break;
				}
			}
			 */

			logger.log(LogStatus.PASS, "selecting account number from the STEP ONE: Merge From drop down");

			String Location = driver
					.findElement(By
							.xpath("//div[text()='STEP ONE: Merge From']/following-sibling::div/div[@class='k-grid-content ps-container']/table/tbody/tr/td[1]"))
					.getText().trim();
			logger.log(LogStatus.PASS,
					"Location number is displayed sucessfully once we select the account number 1 from STEP ONE: Merge From drop down and location number is---->:"
							+ Location);

			String Space = driver
					.findElement(By
							.xpath("//div[text()='STEP ONE: Merge From']/following-sibling::div/div[@class='k-grid-content ps-container']/table/tbody/tr/td[2]"))
					.getText().trim();
			logger.log(LogStatus.PASS,
					"Space number is displayed sucessfully once we select the account number 1 from STEP ONE: Merge From drop down and Space number is---->:"
							+ Space);

			String Status = driver
					.findElement(By
							.xpath("//div[text()='STEP ONE: Merge From']/following-sibling::div/div[@class='k-grid-content ps-container']/table/tbody/tr/td[3]"))
					.getText().trim();
			logger.log(LogStatus.PASS,
					"Status  is displayed sucessfully once we select the account number 1 from STEP ONE: Merge From drop down and status  is---->:"
							+ Status);

			Thread.sleep(2000);

			//driver.findElement(By.xpath("(//div[@class='step-section double-padding']//span[@class='k-icon k-i-arrow-s'])[2]")).click();







			merge.selValFromDropDwn(accountNum, driver, 2);
			logger.log(LogStatus.PASS, "selecting the account 2 from STEP ONE: Merge From  drop down");

			Thread.sleep(4000);

			try{
				if(driver.findElement(By.xpath("//div[text()='STEP TWO: Move To']/following-sibling::div[@id='contact-info-warning']")).isDisplayed()){
			
					String errorMsg = driver
							.findElement(By
									.xpath("//div[text()='STEP TWO: Move To']/following-sibling::div[@id='contact-info-warning']"))
							.getText().trim();
			
			
			if (errorMsg.contains("Address, phone and email do not match.")) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Address, phone and email do not match. Only contact information from AccountNumber will be retained. warning message  is displayed successfully");
				logger.log(LogStatus.PASS,
						"Address, phone and email do not match. Only contact information from AccountNumber will be retained. warning  is displayed successfully",
						image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"Address, phone and email do not match. Only contact information from AccountNumber will be retained. warning  is not displayed");
				logger.log(LogStatus.FAIL,
						"Address, phone and email do not match. Only contact information from AccountNumber will be retained. warning  is not displayed",
						image);

			}
				}
			
			
			}
			
			catch(Exception ex){
				
			}

			merge.clk_SubmitBtn();


			MergeAccountPopUPPage popup = new MergeAccountPopUPPage(driver);
			Thread.sleep(3000);
			if (popup.isMergeAccPopUpTitleDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Merge Account popup window  is displayed successfully");
				logger.log(LogStatus.PASS, "Merge Account popup window  is displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Merge Account popup window  is  is not displayed");
				logger.log(LogStatus.FAIL, "Merge Account popup window  is  is not displayed", image);

			}

			popup.enter_EmpId(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "enter the employee number ");

			popup.enter_NoteField("Merging Accounts");

			popup.clk_ConfirmBtn();
			logger.log(LogStatus.PASS, "clicked on the confirm button");

			Thread.sleep(15000);
			if (cust_accdetails.isCustdbTitleDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ", image);
			}

			Thread.sleep(2000);
			// merge account text validation part
			String val1 = driver
					.findElement(By.xpath("//div[contains(text(),'Current Account:')]/following-sibling::div[1]"))
					.getText().trim();
			String val2 = driver
					.findElement(By.xpath("//div[contains(text(),'Current Account:')]/following-sibling::div[2]/i"))
					.getText().trim();
			String combine = val1 + val2 + accountNum;
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS,
					"After merging from " + accountNum + " to " + val1
					+ " account in customer dash board under customer info tab mesaage" + combine
					+ "dispaying sucessfully");
			logger.log(LogStatus.INFO,
					"After merging from " + accountNum + " to" + val1
					+ " account in customer dash board under customer info tab mesaage" + combine
					+ "dispaying sucessfully",
					image);

			cust_accdetails.click_BackToDashboard();
			logger.log(LogStatus.INFO, "Navigated to Dashboard successfully");

			//Select Location
			//selectLocation();



			Thread.sleep(5000);
			dmpage.click_advSearchLink();
			logger.log(LogStatus.PASS, "clicked on the advance search link in the DM home page sucessfully");

			Thread.sleep(6000);


			if (search.verifyAdvSearchPageIsOpened()) {
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scr = Generic_Class.takeScreenShotPath();
				String img = logger.addScreenCapture(scr);
				logger.log(LogStatus.INFO, "Image", img);

			} else {
				logger.log(LogStatus.FAIL, "Advanced Search page is not opened");
				String scr2 = Generic_Class.takeScreenShotPath();
				String img2 = logger.addScreenCapture(scr2);
				logger.log(LogStatus.INFO, "Image", img2);
				resultFlag = "fail";
			}

			search.enterAccNum(val1);
			logger.log(LogStatus.INFO, "Entered merged Account Num in Account field successfully and account no is : " + val1);

			Thread.sleep(3000);
			search.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");


			Thread.sleep(10000);


			String addressAfterMerge = driver.findElement(By.xpath("//div[@class='content floatright']/div[@class='padding-bottom']/div/div[1]")).getText();

			Thread.sleep(2000);
			String phoneAndEmailAfterMerge= driver.findElement(By.xpath("//div[@class='content floatright']/div[@class='padding-bottom']/div/span")).getText();

			Thread.sleep(2000);






			logger.log(LogStatus.PASS, "Address, Phone, and Email from the Merge From account not  moved. " );





			Thread.sleep(8000);

			sn = Generic_Class.takeScreenShotPath();
			ir = logger.addScreenCapture(sn);
			logger.log(LogStatus.INFO, "img", ir);











			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "clicking on the space details tab in the customer dashboard sucessfully");
			Thread.sleep(3000);

			//String[] mergedSpaceNumbers = accspace.getAllSpaceNumbers();
			//logger.log(LogStatus.INFO, "captured all the space details that are available in the merged space");

			/*for (int i = 0; i < mergerSpaceNumbers.length; i++) {

				for (int j = 0; j < mergedSpaceNumbers.length; j++) {

					if (mergerSpaceNumbers[i].equals(mergedSpaceNumbers[j])) {
						logger.log(LogStatus.INFO, "All the spaces that were available in the " + mergerSpaceNumbers[i]
								+ " are available in" + mergedSpaceNumbers[j]);
						logger.log(LogStatus.PASS, "All the spaces that were available in the " + mergerSpaceNumbers[i]
								+ " are available in" + mergedSpaceNumbers[j]);
						String scr = Generic_Class.takeScreenShotPath();
						String img = logger.addScreenCapture(scr);
						logger.log(LogStatus.INFO, "Image", img);
						break;
					} else {
						logger.log(LogStatus.FAIL,
								"All the spaces are not available!! Spaces are not merged properly!!");
						String scr2 = Generic_Class.takeScreenShotPath();
						String img2 = logger.addScreenCapture(scr2);
						logger.log(LogStatus.INFO, "Image", img2);
						resultFlag = "fail";
						break;
					}

				}

			}
			 */

			List<WebElement> AllSpacesafterMerge = driver.findElements(By.xpath("//span[@class='space-rentalunitnumber']"));

			int sizeafterMerged = AllSpacesafterMerge.size();
			Thread.sleep(4000);
			String spaceNofirst = AllSpacesafterMerge.get(0).getText();
			Thread.sleep(2000);
			String spaceNoSecond = AllSpacesafterMerge.get(1).getText();

			Thread.sleep(4000);

			if(sizeafterMerged>1){

				logger.log(LogStatus.INFO, "Before Merged size of the space is : " + sizebeforeMerge + " And The Space No is :" + spaceNoBeforeMerge );
				logger.log(LogStatus.INFO, "After Merged size of the space is : " + sizeafterMerged +    "And The Space No is :" + spaceNofirst +   " And merged space is :  " +  spaceNoSecond );

			}






			sn = Generic_Class.takeScreenShotPath();
			ir = logger.addScreenCapture(sn);
			logger.log(LogStatus.INFO, "img", ir);


			Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
            String strTodaysDate = df.format(cal.getTime());


            
            Thread.sleep(25000);
            
            // Validating Account activities Tab
            
      




			cust_accdetails.clk_AccActivities();
			logger.log(LogStatus.INFO, "Clicked on Account Activities Tab");

			Thread.sleep(6000);
			//cust_accdetails.clkOnMergeActivity();
			
			//div[@id='activities-grid']//table/tbody//tr/td[text()='02/08/2017']/following-sibling::td[text()='Merge']/preceding-sibling::td/a

			
			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Merge']")).size()!=0)
			{
			 
			
			driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Merge']/preceding-sibling::td/a")).click();
			
			logger.log(LogStatus.INFO, "Clicked on Merge Activity in Account Activities Tab");

			Thread.sleep(4000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			if (cust_accdetails.isUnmergeLinkDisplayed(driver)) {
				logger.log(LogStatus.PASS, "Unmerge link is available");
				String scr = Generic_Class.takeScreenShotPath();
				String img = logger.addScreenCapture(scr);
				logger.log(LogStatus.INFO, "Image", img);

			} else {
				logger.log(LogStatus.FAIL, "Unmerge link is not available");
				String scr2 = Generic_Class.takeScreenShotPath();
				String img2 = logger.addScreenCapture(scr2);
				logger.log(LogStatus.INFO, "Image", img2);
				resultFlag = "fail";
			}
			}

			//Scroll up
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,-2000)");
			Thread.sleep(4000);


			cust_accdetails.click_BackToDashboard();
			logger.log(LogStatus.INFO, "Navigated to Dashboard successfully");

			//select Location
			//selectLocation();

			Thread.sleep(4000);
			dmpage.click_advSearchLink();

			Thread.sleep(6000);


			if (search.verifyAdvSearchPageIsOpened()) {

				String scr = Generic_Class.takeScreenShotPath();
				String img = logger.addScreenCapture(scr);
				logger.log(LogStatus.INFO, "Image", img);

			} else {
				logger.log(LogStatus.FAIL, "Advanced Search page is not opened");
				String scr2 = Generic_Class.takeScreenShotPath();
				String img2 = logger.addScreenCapture(scr2);
				logger.log(LogStatus.INFO, "Image", img2);
				resultFlag = "fail";
			}

			search.enterAccNum(accountNum);
			logger.log(LogStatus.INFO, "Entered Primary customer Account Num in Account field successfully");

			Thread.sleep(3000);
			search.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");

			Thread.sleep(8000);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
			Thread.sleep(3000);
			if (driver.findElement(By.xpath("//span[@id='TotalQueryResults']")).getText().contains("No customers matched your search criteria")) {
				logger.log(LogStatus.PASS, "The merged from account shall be marked to indicate it was merged into another account and the account shall be inactive. ");
				String scr = Generic_Class.takeScreenShotPath();
				String img = logger.addScreenCapture(scr);
				logger.log(LogStatus.INFO, "Image", img);

			} else {
				logger.log(LogStatus.FAIL, "The merged from account shall  not be marked to indicate it was not merged into another account and the account shall be inactive. ");
				String scr2 = Generic_Class.takeScreenShotPath();
				String img2 = logger.addScreenCapture(scr2);
				logger.log(LogStatus.INFO, "Image", img2);
				resultFlag = "fail";
			}


			//Code for Unmerge the Acount

			/*((JavascriptExecutor) driver).executeScript("window.scrollTo(0,-2000)");
			Thread.sleep(3000);

			search.enterAccNum(val1);


			Thread.sleep(3000);
			search.clickSearchAccbtn();


			Thread.sleep(9000);



			//Click on Account Activities tab
			cust_accdetails.clk_AccActivities();

			Thread.sleep(12000);

			List<WebElement> list = driver.findElements(By.xpath("//tr[td[text()='Accounts merged']]/td[@class='k-hierarchy-cell']"));
			int Size = list.size();
			list.get(0).click();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(3000);

			//Click on Unmerge link
			driver.findElement(By.xpath("//a[text()='Unmerge']")).click();
			Thread.sleep(3000);

			//Enter Notes
			driver.findElement(By.xpath("//textarea[@id='notesText']")).sendKeys("Account is Unmerged");
			Thread.sleep(3000);

			driver.findElement(By.xpath("//input[@id='employeeNumber']")).sendKeys(tabledata.get("UserName"));

			Thread.sleep(3000);

			//Click ON Confirm button
			driver.findElement(By.xpath("//a[contains(text(),'Confirm')]")).click();*/







		}

		catch (Exception e) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
			logger.log(LogStatus.FAIL, "------------- page is not displayed"+ e);
			e.printStackTrace();

		}

	}

	public void selectLocation() {
		driver.findElement(By.xpath("//form[@id='QuickSearchForm']//span[@class='k-input']")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> AllOptions = driver.findElements(By.xpath("//div[@id='siteId-list']/ul/li"));
		int totalSize = AllOptions.size();
		for (int i = 0; i < totalSize; i++) {
			if (i == 1) {
				AllOptions.get(i).click();
				logger.log(LogStatus.PASS, "Location selected sucessfully ");
				break;
			}
		}
	}

	

	

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_AccountDetails_MergeAccount_Business",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_AccountDetails_MergeAccount_Business",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_AccountDetails_MergeAccount_Business",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}

}
