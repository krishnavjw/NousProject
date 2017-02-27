package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard",  "CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID");
	}



	@Test(dataProvider="getLoginData")
	public void propertyManagement_Edit_NonCustomerPropertyAccess(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID","Verify customer info tab for accoutn number, address - all, phone - all, email - all , user name, ID and Military");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));  

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

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			// Login into PM dashboard 
			PM_Homepage pmhomepage= new  PM_Homepage(driver);
			
			
			//screenshot
			 //Verify User should view the module Existing Customer
	        if(pmhomepage.get_existingCustomerText().equalsIgnoreCase("Existing Customer")){

	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);

	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.PASS, "Existing Customer module displayed successfully:"+pmhomepage.get_existingCustomerText());
	                logger.log(LogStatus.INFO, "Existing Customer module displayed successfully",image);
	        }
	        else{

	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                if(resultFlag.equals("pass"))
	                                resultFlag="fail";
	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.FAIL, "Existing Customer module not displayed");
	                logger.log(LogStatus.INFO, "Existing Customer module not displayed",image);

	        }
			
			Thread.sleep(3000);
			

			String custid=DataBase_JDBC.executeSQLQuery("select top 1 A.CustomerId "+
					"from Account A with (nolock) "+
					"Join Customer CU with (nolock)  on A.CustomerID = CU.CustomerID "+
					"join accountorder ao with (nolock)  on ao.accountid=a.accountid "+
					"join accountorderitem aoi with (nolock)  on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi with (nolock)  on soi.storageorderitemid=aoi.storageorderitemid "+
					"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "+
					"Join Site S with (nolock)  on AOI.SiteID = S.SiteID "+
					"where 1=1 "+

					"And SOI.Vacatedate is null "+
					//--And ru.Rentalunitnumber not like 'APT%'
					"And CU.CustomerTypeID=90 "+
					//--And S.SiteNumber = 22326
					"group by A.CustomerID "+ 
					"having Count(distinct A.accountnumber) >1 ");
			
			String query="Select AccountNumber from Account "+
					"where CustomerID =" +custid;
			Thread.sleep(1000);
			String accNum=DataBase_JDBC.executeSQLQuery(query);
			
			
		      pmhomepage.enter_findCustAddrLocation(accNum);
		        logger.log(LogStatus.PASS, "Existing Customer Account Number entered successfully");
		        Thread.sleep(5000);
		        pmhomepage.clk_AdvSearchLnk();;
		       
				
			
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
			
			Advance_Search advsearch=new Advance_Search(driver);
			
			advsearch.enterAccNum(accNum);
			Thread.sleep(2000);
			//advsearch.enterAccNum(tabledata.get("Account Number"));
			logger.log(LogStatus.INFO, "Account number entered" +accNum);
			
			advsearch.clk_SearchAccount();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on search button");
			
			Thread.sleep(10000);
			
			Cust_AccDetailsPage cust=new Cust_AccDetailsPage(driver);
			if(cust.Verify_CustDashboard()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
			    logger.log(LogStatus.PASS, "Customer dashboard is displayed successfully");
			    logger.log(LogStatus.INFO, "Customer dashboard is  displayed successfully",image);
				}
				else{
					
					String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
		            resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.FAIL, "Customer dashboard is  not displayed");
		    	logger.log(LogStatus.INFO, "Customer dashboard is  not displayed",image);

				}
			
			if(cust.verify_custInfo_Tab()){
				 logger.log(LogStatus.PASS, "Customer info tab is displayed successfully");
				 
			}else{
				 logger.log(LogStatus.FAIL, "Customer info tab is not displayed ");
			}
			
			if(accNum.equals(cust.getcustCurrAccno())){
				 logger.log(LogStatus.PASS, "Current account number field is displayed with the selected account number");
			}else{
				 logger.log(LogStatus.FAIL, "Current account number field is not displayed with the selected account number");
			}
			
			Acc_CustomerInfoPage custinfo=new Acc_CustomerInfoPage(driver);
			if(custinfo.verify_otherAccounts()){
				 logger.log(LogStatus.PASS, "Other Account option is present for same customer with multiple accounts");
				 String defaultoption=custinfo.get_otherAccounts();
				 logger.log(LogStatus.PASS, "The default option for the drop-down menu is : "+defaultoption);
				 
			}else{
				 logger.log(LogStatus.FAIL, "Other Account option is not present because customer is holding single account ");
			}
			
			if(custinfo.verify_helpicon()){
				 logger.log(LogStatus.PASS, "Help icon is displayed beside the other accounts dropdown");
			}else{
				 logger.log(LogStatus.FAIL, "Help icon is not displayed beside the other accounts dropdown");
			}
			
			Thread.sleep(3000);
			//verify address,un,ph,email,identification,milstatus
			if(custinfo.verifyAddress() && custinfo.verifyPhoneNumber() && custinfo.verifyEmailId() && custinfo.verifyusername() && custinfo.verifyIdentificationId() && custinfo.verifymilitarystaus())
			{
				
				 logger.log(LogStatus.PASS, "Address,phone, email ,username,identification, Militarystatus fields are displayed");
			}else{
				 logger.log(LogStatus.FAIL, "Address,phone, email ,username,identification, Militarystatus fields are not displayed");
			}
			
			  //screenshot
		     String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String  image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "image",image);
			
			if(cust.verify_EditAccDetails()){
				 logger.log(LogStatus.PASS, "Edit account details button is displayed");
				 cust.click_EditAccDetails();
				 logger.log(LogStatus.PASS, "Clicked on Edit Account details button successfully");
				 Thread.sleep(3000);
				  scpath=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath,true);
					 image=logger.addScreenCapture(scpath);
				    logger.log(LogStatus.PASS, "Edit Account details modal window is displayed successfully");
				    logger.log(LogStatus.INFO, "Edit Account details modal window is displayed ",image);
				 
				 
			}else{
				 logger.log(LogStatus.FAIL, "Edit account details button is not displayed");
			}
			
		
			
				
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			//logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_ViewCustomerInfoTab_ForAccountNumber_AddressPhoneEmail_All_UserNameMilitaryID" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}

	
}
