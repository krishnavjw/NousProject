package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import Pages.CustDashboardPages.Cust_AccActivitiesPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerDashboard_VerifyEmailResend_FromActivities extends Browser_Factory{

	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";
	
	@DataProvider
	public Object[][] getDta() 
	{
		return Excel.getCellValue_inlist(path, "CustomerDashBoard","CustomerDashBoard","CustomerDashboard_VerifyEmailResend_FromActivities");
	}



	@Test(dataProvider="getDta")	
	public void CustomerDashboard_VerifyEmailResend_FromActivities(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{

			//Login to the application as PM 
			logger=extent.startTest("CustomerDashboard_VerifyEmailResend_FromActivities","Verify to be able to resend any email from activities");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			LoginPage login= new LoginPage(driver);
			Thread.sleep(3000);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			Thread.sleep(5000);

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();
			Thread.sleep(5000);

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
/*
			String varname1 = ""
					+ "select  top 1 a.accountnumber from contact c with (nolock) "
					+ "join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "
					+ "join accountorder ao with (nolock) on ao.accountid=a.accountid "
					+ "join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "
					+ "join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "
					+ "join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "
					+ "join type t with (nolock) on t.typeid=cu.customertypeid "
					+ "join site s with (nolock) on s.siteid=aoi.siteid "
					+ "left join accountphone ap with (nolock) on ap.accountid=a.accountid "
					+ "left join accountemail ae with (nolock) on ae.accountid=a.accountid "
					+ "left join phone p with (nolock) on p.phoneid=ap.phoneid "
					+ "left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid "
					+ "where  soi.vacatedate is null";*/
			
			String varname1="Select Top 1 A.AccountNumber"
                    +" FROM AccountOrderItem AOI with(nolock)"
                    +" Join AccountOrder AO with(nolock) on AO.AccountOrderID = AOI.AccountOrderID"
                    +" Join Account A with(nolock) on A.AccountID = AO.AccountID"
                    +" Join Customer C with(nolock) on C.CustomerID = A.CustomerID" 
                    +" join site s with(nolock) on s.siteid=aoi.siteid"
                    +" join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID" 
                    +" join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid"
                    +" join emailaddress ea on ea.contactid = c.contactid"
                    +" join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid"
                    +" where 1 = 1 " 
                    //+" and s.sitenumber='"+sitenumber+"'"
                    +" and soi.VacateDate is null"
                    +" and c.customertypeid= 90"
                    +" and ea.email is not null"
                    +" group by a.accountnumber"
                    +" having sum(clt.amount+clt.discountamount) =0 and count(distinct ru.rentalunitid)=1";


			String accNum=DataBase_JDBC.executeSQLQuery(varname1);

			Thread.sleep(1000);


			pmhomepage.clk_AdvSearchLnk();;



			/*
			 * verifying the advance search page
			 */
			Advance_Search advSearch= new Advance_Search(driver);
			/*if("Customer Search".equalsIgnoreCase(advSearch.getadvSearchPage_Title())){
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
*/
			Advance_Search advsearch=new Advance_Search(driver);

			advsearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Account number entered is : " +accNum);
			Thread.sleep(2000);
			
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			advsearch.clk_SearchAccount();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on search button");
			Thread.sleep(5000);

			Cust_AccDetailsPage cust=new Cust_AccDetailsPage(driver);
			Thread.sleep(5000);
			if(cust.Verify_CustDashboard()){
				 scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer dashboard is  displayed successfully",image);
			}
			else{

				 scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer dashboard is  not displayed");
				logger.log(LogStatus.INFO, "Customer dashboard is  not displayed",image);

			}

			cust.click_AccountActivities();
			logger.log(LogStatus.INFO, "Clicked on Account activities tab in the customer dashboard");

			Thread.sleep(10000);
			 scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);


			//mohana
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			Cust_AccActivitiesPage activities=new Cust_AccActivitiesPage(driver);
			activities.click_email_link();
			Thread.sleep(2000);
			jse.executeScript("window.scrollBy(1000,0)", "");

			activities.click_resendEmail_link();


			WebElement mwindow=driver.findElement(By.xpath("//span[text()='Resend Email']"));

			if(mwindow.isDisplayed()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Resend Email modal window is displayed successfully");
				logger.log(LogStatus.INFO, "Resend Email modal window is  displayed successfully",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Resend Email modal window is  not displayed");
				logger.log(LogStatus.INFO, "Resend Email modal window is  not displayed",image);

			}

			driver.findElement(By.xpath("//span[contains(text(),'.com')]/preceding-sibling::span[@class='button']")).click();
			logger.log(LogStatus.INFO, "Selected the email address");
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);

			driver.findElement(By.xpath("//div[@id='resendEmailDialog']/following-sibling::div//a[contains(text(),'Resend')]")).click();
			logger.log(LogStatus.INFO, "Clicked on resend button");
			Thread.sleep(10000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Email has been sent");
			logger.log(LogStatus.INFO, "Email has been sent",image);


/*
			if(driver.findElement(By.xpath("//div[contains(text(),' Failed to resend message')]")).isDisplayed())
			{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message is displayed : Failed to resend message");
				logger.log(LogStatus.INFO, "Error message is displayed : Failed to resend message",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Email has been sent");
				logger.log(LogStatus.INFO, "Email has been sent",image);

			}*/

			Thread.sleep(20000);
			
			try{
				
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(3000);
			}catch(Exception ex){

			}
			
			Thread.sleep(3000);
			String deliveryStatus=driver.findElement(By.xpath("(//div[@class='activity-detail clearfix-container']//div[@class='floatleft status'])[1]")).getText();
            logger.log(LogStatus.INFO, "The deliver status of the Email is :"+deliveryStatus);
           





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
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyEmailResend_FromActivities" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyEmailResend_FromActivities" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyEmailResend_FromActivities" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}

}
