package Scenarios.CustomerSearch;

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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AdvancedSearch_AccountNum extends Browser_Factory{
	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";
	@DataProvider
	public Object[][] getDta() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch",this.getClass().getSimpleName());
	}

	@Test(dataProvider="getDta")	
	public void CustomerSearch_AdvancedSearchusingAccountNum(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the "+this.getClass().getSimpleName()+" test");
		}
		try{

			logger=extent.startTest(this.getClass().getSimpleName(), "Advance search page validations and account number field validations");
			Thread.sleep(5000);
			LoginPage loginPage = new LoginPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username sucessfully");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password sucessfully");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");

			//=================Handling customer facing device========================
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			 //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
		
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_T);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_T); 
	        
	        Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(15000);
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

			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			 // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	        
	        Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			//=================================================================================
			/*
			 * Verifying the pm dashboard page and clicking on adv search link
			 */
			PM_Homepage pmhome = new PM_Homepage(driver);
			String getText = pmhome.get_WlkInCustText().trim();
			if("Walk-In Customer".equalsIgnoreCase(pmhome.get_WlkInCustText().trim())){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "PM  Dashboard is not displayed ",image);
			}
			
			Thread.sleep(3000);
			if(pmhome.isexistingCustomerModelDisplayed()){

				logger.log(LogStatus.PASS, "Existing Customer module is present in the PM DashBoard sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Existing Customer module is not present in the PM DashBoard");
			}

			Thread.sleep(4000);
			if(pmhome.get_findACustomerAtThisLocText().contains("Find a Customer at this Location:")){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a Customer at this Location is displayed successfully");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find a Customer at this Location is not displayed ");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is not displayed ",image);

			}

			Thread.sleep(3000);
			if(pmhome.get_findACustomerText().trim().contains("Find Customer")){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully");
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

			}

			Thread.sleep(3000);
				
			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "clicked on Advance Search link successfully");
			Thread.sleep(5000);

			/*
			 * verifying the advance search page
			 */
			Advance_Search advSearch= new Advance_Search(driver);
			if("Customer Search".equalsIgnoreCase(advSearch.getadvSearchPage_Title())){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Advance search page is displayed sucessfully is displayed successfully");
				logger.log(LogStatus.INFO, "Advance search page is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Advance search page is not displayed ");
				logger.log(LogStatus.INFO, "Advance search page is not displayed ",image);
			}

			/*
			 * verifying search by section
			 */ 
			if(advSearch.getsearchBySection_Title().contains("search by an acc")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "SearchBy Section is displayed sucessfully is displayed successfully");
				logger.log(LogStatus.INFO, "SearchBy Section is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "SearchBy Section is not displayed ");
				logger.log(LogStatus.INFO, "SearchBy Section is not displayed ",image);
			}
			/*
			 * verifying Storage Property Location: section 
			 */
			if(advSearch.getstoragePropertyLocSection_Title().contains("Storage Property Location")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Storage Property Location: Section is displayed sucessfully is displayed successfully");
				logger.log(LogStatus.INFO, "Storage Property Location: Section is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Storage Property Location: Section is not displayed ");
				logger.log(LogStatus.INFO, "Storage Property Location: Section is not displayed ",image);
			}

			/*
			 * Verifying search for section
			 */
			if(advSearch.getsearchForSection_Title().contains("Search For:")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Search For Section is displayed sucessfully is displayed successfully");
				logger.log(LogStatus.INFO, "Search For Section is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Search For Section is not displayed ");
				logger.log(LogStatus.INFO, "Search For Section is not displayed ",image);
			}

			/*
			 * verifying location radio button
			 */
			if(advSearch.isLocationSelected()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Location number radio button is displayed and Selected by default");
				logger.log(LogStatus.INFO, "Location number radio button is displayed and Selected by default",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Location number radio button is not displayed or not Selected by default ");
				logger.log(LogStatus.INFO, "Location number radio button is not displayed or not Selected by default",image);
			}

			/*
			 * verifying account type
			 * Customer radio button must be selected by default
			 */
			if(advSearch.isAccType_Cm()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account type label is displayed and Customer radio button is Selected by default");
				logger.log(LogStatus.INFO, "Account type label is displayed and Customer radio button is Selected by default",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account type label is not displayed or  Customer radio button is not Selected by default");
				logger.log(LogStatus.INFO, "Account type label is not displayed or Customer radio button is not Selected by default",image);
			}

			/*
			 * Verifying status drop down
			 * Currently Renting option must be selected by default
			 */
			if(advSearch.getstatus_DropDown().equalsIgnoreCase(tabledata.get("StatusValue"))){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Status Dropdown is displayed sucessfully and currently renting option is selected by default");
				logger.log(LogStatus.INFO, "Status Dropdown is displayed sucessfully and currently renting option is selected by default",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Status Dropdown is not displayed sucessfully and currently renting option is not selected by default");
				logger.log(LogStatus.INFO, "Status Dropdown is not displayed sucessfully and currently renting option is not selected by default ",image);
			}

			/*
			 * verifying match any search field radio button
			 * match any search field must be displayed by default
			 */
			if(advSearch.ismatch_searchField()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Match any search field radio button is displayed and Selected by default");
				logger.log(LogStatus.INFO, "Match any search field radio button is displayed and Selected by default",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Match any search field radio button is not displayed or not Selected by default ");
				logger.log(LogStatus.INFO, "Match any search field radio button is not displayed or not Selected by default",image);
			}	

			/*
			 * Verifying blank Account number
			 */
			advSearch.clickButton();	
			logger.log(LogStatus.INFO, "clicked on Search button successfully");
			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);
			advSearch.clickButton();	
			logger.log(LogStatus.INFO, "clicked on Search button successfully");
			Thread.sleep(5000);
			Cust_AccDetailsPage custAccDetailsPage=new Cust_AccDetailsPage(driver);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,500)");
			((JavascriptExecutor)driver).executeScript("window.scrollBy(-2000,0)");
			if(advSearch.getnoCriteriaEntered_EM().equalsIgnoreCase("NO CRITERIA WAS ENTERED")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Criteria Entered error message is verified sucessfully");
				logger.log(LogStatus.INFO, "No Criteria Entered error message is verified sucessfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Criteria Entered error message is not verified");
				logger.log(LogStatus.INFO, "No Criteria Entered error message is not verified",image);
			}

			/*
			 * verifying invalid account number
			 */
			advSearch.enterAccNum("1111111111");
			logger.log(LogStatus.INFO, "Entered Account Number in Account field successfully");
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);
			advSearch.clickButton();	
			logger.log(LogStatus.INFO, "clicked on Search button successfully");
			Thread.sleep(5000);
			getText = advSearch.getnoResultsFound_EM();
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,500)");
			((JavascriptExecutor)driver).executeScript("window.scrollBy(-2000,0)");
			if("NO RESULTS FOUND MATCHING YOUR CRITERIA".equalsIgnoreCase(advSearch.getnoResultsFound_EM())){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No results found error message is verified sucessfully");
				logger.log(LogStatus.INFO, "No results found error message is verified sucessfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No results found error message is not verified");
				logger.log(LogStatus.INFO, "No results found error message is not verified",image);
			}

			/*
			 * Entering the Account No in account number field
			 */

			Thread.sleep(4000);
			String SiteNumber=advSearch.getLocationNum();
			logger.log(LogStatus.PASS, "location number is dispalyed in advanch search page and location number is:"+SiteNumber);

		

			String AccountNumber=tabledata.get("AccountNumber");
			Thread.sleep(6000);		
			advSearch.clearsearch_accNo();
			advSearch.enterAccNum(AccountNumber);
			Thread.sleep(3000);	
			logger.log(LogStatus.INFO, "Entered Account Number in Account field successfully");
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			/*
			 * click Search button
			 */
			advSearch.clickButton();	
			logger.log(LogStatus.INFO, "clicked on Search button successfully");
			Thread.sleep(20000);

			/*
			 * Verifying Account number
			 */

			if(custAccDetailsPage.getcustCurrAccno().trim().equals(AccountNumber)){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "After clicking on Search button, navigated to Customer Dashboard");
				logger.log(LogStatus.PASS, "Account Number verified sucessfully in Customer Dashboard");
				logger.log(LogStatus.INFO, "Image",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account Number not matched in Customer Dashboard");
				logger.log(LogStatus.INFO, "Image",image);
			}
			
		}
		catch(Exception e){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed",image);
			e.printStackTrace();
		}
	}
	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag,true);
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();}
}
