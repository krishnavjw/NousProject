package Scenarios.AWB;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AWB.AuctionManagementPage;
import Pages.AWB.Auction_modelwindow;
import Pages.AWB.PropertyManagementPage;
import Pages.AWB.SelectFilterOptions;
import Pages.AWB.UnitDetailsPage;
import Pages.DTMCallsPages.DTMCallscreen;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Create_new_auction_validate_date_restrictions extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AuctionWorkBench","AuctionWorkBench","Create_new_auction_validate_date_restrictions");
	}

	@Test(dataProvider="getCustSearchData")
	public void Create_new_auction_validate_date_restrictions(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Create_new_auction_validate_date_restrictions", "Create_new_auction_validate_date_restrictions");
			Reporter.log("Test case started: " +testcaseName, true); 


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AuctionWorkBench").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
		
			
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");
			Thread.sleep(8000);
			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);

			// =================Handling customer facing device========================
					/*	Thread.sleep(2000);
						Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
						logger.log(LogStatus.INFO, "PopUp window object is created successfully");

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
						driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  

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
						logger.log(LogStatus.INFO, "clicked on continue successfully");
						*/
						// =================================================================================

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title
						Thread.sleep(5000);
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "DM Home page object created successfully");
			
			
			

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1500)", "");
			
			pmhomepage.clk_PM_PropertyManagement_link();
			logger.log(LogStatus.INFO, "clicked on Manage property Link successfully");
			Thread.sleep(3000);
			
			Thread.sleep(2000);
			PropertyManagementPage promgmt=new PropertyManagementPage(driver);
			if(promgmt.verify_PageTitle()){

			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "User navigated to property management page successfully");
			logger.log(LogStatus.INFO, "User navigated to property management page successfully",image1);
		}else{
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			logger.log(LogStatus.FAIL, "property management page is not displayed ");
			logger.log(LogStatus.INFO, "property management page is not  displayed ",image);
		}
			
			promgmt.click_AuctionManagementLink();
			logger.log(LogStatus.INFO, "clicked on Auction Management Link successfully");
			Thread.sleep(2000);
			
			AuctionManagementPage auctionmgmt=new AuctionManagementPage(driver);
			if(auctionmgmt.verify_AuctionManagementtitle()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " Auction management Section Displayed successfully");
				logger.log(LogStatus.INFO, "Auction management Section Displayed successfully",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Auction management Section not displayed successfully ");
				logger.log(LogStatus.INFO, "Auction management Section not displayed successfully ",image);
				}
			
			//verify filter section is not available for pm
		
			try{
				boolean flag=!(auctionmgmt.verify_filterssection());
		
				logger.log(LogStatus.PASS, "Filter section  is  available for DM");	
			
			}catch(Exception e){
				logger.log(LogStatus.FAIL, "Filter section  is not available for DM");
				}
			
			
			
			
			if(auctionmgmt.Verify_DRA() && auctionmgmt.verify_property() && auctionmgmt.verify_AuctionDate()
					&& auctionmgmt.verify_Units() && auctionmgmt.verify_DM() && auctionmgmt.verify_RM() &&
					auctionmgmt.verify_Employee() && auctionmgmt.verify_approved()){
				logger.log(LogStatus.PASS, "All the expected columns are available in UI");	
				}
			else{
				logger.log(LogStatus.FAIL, "All the expected columns are not available in UI");	
			}
			
			
			
			
			
			
			
			Thread.sleep(5000);
			jse.executeScript("window.scrollBy(1000,0)", "");
			
			Auction_modelwindow auction_mw= new Auction_modelwindow(driver);
			
			auction_mw.click_btn_newaction();
			
			String MW_title=auction_mw.get_MW_title();
			
			Thread.sleep(3000);
			if(MW_title.contains("Schedule Auction(Auction Management")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Schedule Auction(Auction Management model window displayed successfully");
				logger.log(LogStatus.INFO, "Schedule Auction(Auction Management model window displayed successfully",image1);
				
			}else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Schedule Auction(Auction Management model window not  displayed ");
				logger.log(LogStatus.INFO, "Schedule Auction(Auction Management model window not displayed  ",image);
				
			}
			
			Boolean selectdate=auction_mw.display_selection_date();
			String selectdate_data=auction_mw.get_selection_date();
			
			
			if(selectdate){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "selectdate calender icon displayed successfully------"+selectdate_data);
				logger.log(LogStatus.INFO, "selectdate calender icon displayed successfully",image1);
				
			}else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "selectdate calender icon not displayed successfully ");
				logger.log(LogStatus.INFO, "selectdate calender icon not displayed successfully ",image);
				
			}
			
			
			
			
			
			
			
			Thread.sleep(5000);
			auction_mw.click_calender();
			
			int j=0;
			
			for(j=0;j<7;j++){
				
				SimpleDateFormat formatter1 = new SimpleDateFormat("EEE");
				Date dt = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(dt); 
				c.add(Calendar.DATE, j);
				dt = c.getTime();
				String commitmentdate=formatter1.format(dt);
				
				System.out.println(" the date is----"+commitmentdate);
				
				if(commitmentdate.equalsIgnoreCase("Sat") || commitmentdate.equalsIgnoreCase("Sun")){
					
					break;
				}
			}
			
			System.out.println(" the date is----"+j);
			
			Boolean dateselected=true;
			try{
				
				 dateselected=auction_mw.SelectDateFromCalendar(j);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			
			if(dateselected){
				
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Auction  Model window allowing to select weekend date ");
				logger.log(LogStatus.INFO, "Auction  Model window allowing to select weekend date  ",image);
				
				
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction  Model window not allowing to select weekend date ");
				logger.log(LogStatus.INFO, "Auction  Model window not allowing to select weekend date ",image1);
				
				
			}
			
			
			auction_mw.SelectDateFromCalendarAWB("3");
			
		Thread.sleep(3000);
//			auction_mw.click_calender();
			
			boolean MW_HH=auction_mw.getMW_calender_HH();
			
			
			String MW_hourdate=auction_mw.getcalender_HH();
			
			if(MW_HH){
				
				logger.log(LogStatus.PASS, "Auction  Model window displaying default Hours---- "+MW_hourdate);
				
			}else{
				
				logger.log(LogStatus.FAIL, "Auction  Model window not displaying default hours-----"+MW_hourdate);
				
				
			}
			
			
			
			
			boolean MW_MM=auction_mw.getMW_calender_MM();
			
			String MW_Mimute=auction_mw.getcalender_MM();
			if(MW_MM){
				
				logger.log(LogStatus.PASS, "Auction  Model window displaying default minute----- "+MW_Mimute);
				
				
			}else{
				
				
				logger.log(LogStatus.FAIL, "Auction  Model window not displaying default minute---"+MW_Mimute);
				
				
			}
			
			
			boolean MW_AM_PM=auction_mw.isdisplayMW_calender_PM_AM();
			if(MW_AM_PM){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction  Model window displaying AM and PM radio buttons");
				logger.log(LogStatus.INFO, "Auction  Model window displaying AM and PM radio buttons ",image1);
				
			}else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Auction  Model window not  displaying AM and PM radio buttons ");
				logger.log(LogStatus.INFO, "Auction  Model window not displaying AM and PM radio buttons  ",image);
				
			}
			Thread.sleep(3000);
			
			Boolean cancel_btn=auction_mw.display_MW_cancel();
			
			Boolean save_btn=auction_mw.display_MW_save();
			
			if(save_btn && cancel_btn){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Cancel and Save  Buttons  displayed");
				logger.log(LogStatus.INFO, "Cancel and Save  Buttons  displayed ",image1);
				
			}else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Cancel and Save  Buttons not  displayed ");
				logger.log(LogStatus.INFO, "Cancel and Save  Buttons  not displayed  ",image);
				
			}
			auction_mw.click_MW_cancel();
			
			
			
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(1000,0)", "");
			auction_mw.click_btn_newaction();
			Thread.sleep(3000);
			
				auction_mw.click_calender();
				String k1=Integer.toString(j-1);
				auction_mw.SelectDateFromCalendarAWB(k1);
				
				Thread.sleep(3000);
				
				auction_mw.click_MW_save();	
				
				
				Thread.sleep(5000);
				
				boolean errormsg= driver.getPageSource().contains("Entered Property Hours/Minutes are in outside Property");
			
			if(errormsg){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Warning message is displayedas -'Entered Property Hours/Minutes are in outside Property'");
				logger.log(LogStatus.INFO, "Entered Property Hours/Minutes are in outside Property is displayed ",image1);
						
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Error msg not displayed ");
				logger.log(LogStatus.INFO, "Error msg displayed  ",image);
				
				
				
			}
			
		
		// other validations related to sorting and remove link is validated in Verify ability to remove auction TC	
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			Reporter.log("Exception ex: " + ex,true);
			logger.log(LogStatus.FAIL,"Test Script fail due to exception");
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Create_new_auction_validate_date_restrictions" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Create_new_auction_validate_date_restrictions" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Create_new_auction_validate_date_restrictions" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}