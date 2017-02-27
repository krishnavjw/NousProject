package Scenarios.AWB;

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

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AWB.AuctionManagementPage;
import Pages.AWB.PropertyManagementPage;
import Pages.AWB.SelectFilterOptions;
import Pages.AWB.UnitDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AuctionWorkBench_Verify_DRA_PropertyFilters extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AuctionWorkBench","AuctionWorkBench","AuctionWorkBench_Verify_DRA_PropertyFilters");
	}

	@Test(dataProvider="getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("AuctionWorkBench_Verify_DRA_PropertyFilters", "AuctionWorkBench_Verify_DRA_PropertyFilters");
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
			Thread.sleep(2000);
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
			logger.log(LogStatus.INFO, "PM Home page object created successfully");
			
			
			

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
				logger.log(LogStatus.PASS, "User navigated to Auction management page successfully");
				logger.log(LogStatus.INFO, "User navigated to Auction management page successfully",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Auction management page is not displayed ");
				logger.log(LogStatus.INFO, "Auction management page is not  displayed ",image);
				}
			
			//verify filter section is not available for pm
		
			try{
				boolean flag=!(auctionmgmt.verify_filterssection());
		
				logger.log(LogStatus.FAIL, "Filter section  is  available for PM");	
			
			}catch(Exception e){
				logger.log(LogStatus.PASS, "Filter section  is not available for PM");
				}
			
			
			if(auctionmgmt.Verify_DRA() && auctionmgmt.verify_property() && auctionmgmt.verify_AuctionDate()
					&& auctionmgmt.verify_Units() && auctionmgmt.verify_DM() && auctionmgmt.verify_RM() &&
					auctionmgmt.verify_Employee() && auctionmgmt.verify_approved()){
				logger.log(LogStatus.PASS, "All the expected columns are available in UI");	
				}
			else{
				logger.log(LogStatus.FAIL, "All the expected columns are not available in UI");	
			}
			
			if(auctionmgmt.Verify_unitDetailsLink()){
				logger.log(LogStatus.PASS, "Unit Deatils link is available for PM");	
			}
		else{
			logger.log(LogStatus.FAIL, "Unit Deatils link is not available for PM");	
			}
			
			//verify new Auction button is not available for pm
			try{
			boolean flag=!(auctionmgmt.verify_Newauction_BTN());
				logger.log(LogStatus.FAIL, "New Auction button is available for PM");	
			}
			catch(Exception e){
			logger.log(LogStatus.PASS, "New Auction button is not available for PM");	
			}
			Thread.sleep(3000);
			
			auctionmgmt.click_BackToDashBoard_Btn();
			logger.log(LogStatus.INFO, "clicked on Auction Management Link successfully");
			Thread.sleep(5000);
			
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			if(resultFlag.equals("pass"))
			logger.log(LogStatus.PASS, "User navigated to PM dashboard ");
			logger.log(LogStatus.INFO, "User navigated to PM dashboard ",image);
			
			jse.executeScript("window.scrollBy(0,1500)", "");
			
			pmhomepage.clk_PM_PropertyManagement_link();
			logger.log(LogStatus.INFO, "clicked on Manage property Link successfully");
			Thread.sleep(3000);
			
			Thread.sleep(2000);
			promgmt.click_AuctionManagementLink();
			logger.log(LogStatus.INFO, "clicked on Auction Management Link successfully");
			Thread.sleep(2000);
			
			auctionmgmt.click_UnitDetails();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS, "Clicked on Unit Deatils link");
			
			UnitDetailsPage unit= new UnitDetailsPage(driver);
			if(unit.verify_filter_toggleBtn()){
				logger.log(LogStatus.PASS, "Filter toogle button is displayed ");	
				unit.click_filter_toggleBtn();
				logger.log(LogStatus.PASS, "Filter toogle button is Clicked successfully ");
				Thread.sleep(3000);
			}
			else{
				logger.log(LogStatus.FAIL, "Filter toogle button page is not displayed ");
			}
			
			SelectFilterOptions sel=new SelectFilterOptions(driver);
			if(sel.verify_ModalWindowTitle())
			{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Select filter options Modal window is displayed successfully");
				logger.log(LogStatus.INFO, "Select filter options Modal window is displayed  successfully",image1);
			}else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Select filter options Modal window is not displayed ");
				logger.log(LogStatus.INFO, "Select filter options Modal window is not displayed",image1);
				}
			Thread.sleep(3000);
			unit.click_windowcancel_Btn();
			Thread.sleep(5000);
					
			jse.executeScript("window.scrollBy(0,1000)", "");
			Thread.sleep(2000);
			jse.executeScript("window.scrollBy(1000,0)", "");
			Thread.sleep(2000);
			
			unit.click_BackToPropertyView_Btn();
			logger.log(LogStatus.INFO, "Clicked on back to property view button suuccessfully ");
			
		Thread.sleep(5000);
		String scpath1=Generic_Class.takeScreenShotPath();
		Reporter.log(scpath1,true);
		String image1=logger.addScreenCapture(scpath1);
		if(resultFlag.equals("pass"))
		logger.log(LogStatus.PASS, "User navigated Auction management page successfully ");
		logger.log(LogStatus.INFO, "User navigated Auction management page successfully ",image1);
		
		auctionmgmt.click_UnitDetails();
		Thread.sleep(10000);
		logger.log(LogStatus.PASS, "Clicked on Unit Deatils link");
		Thread.sleep(3000);
		
		
		unit.click_filter_toggleBtn();
		logger.log(LogStatus.INFO, "Filter toogle button is Clicked successfully ");
		Thread.sleep(3000);
		sel.click_DmNotApproved();
		//sel.click_RmNotApproved();
		//sel.click_InventoryNotComplete();
		Thread.sleep(2000);
		logger.log(LogStatus.INFO, "Selected desired checkboxes ");
		sel.click_savebtn();
		logger.log(LogStatus.INFO, "Clicked on save button successfully ");
		Thread.sleep(5000);
		
		
		String value=driver.findElement(By.xpath("//div[@id='Unitgrid']//thead//th[@data-field='DMApproved']")).getAttribute("data-index");
		
		int indervalue=Integer.parseInt(value);
		int indervalue1=indervalue+2;
		
		List<WebElement> optionvalues=driver.findElements(By.xpath("//div[@id='Unitgrid']//tbody//tr/td["+indervalue1+"]"));
		
		System.out.println(" the value is---"+optionvalues.get(1).getText().toString());
		jse.executeScript("window.scrollBy(1000,0)", "");
		int optioncounnt=0;
		for(int i=0;i<optionvalues.size();i++){
			
			if(optionvalues.get(i).getText().toString().equals("N")){
				
				optioncounnt++;
				
			}else{
				logger.log(LogStatus.FAIL, "The Data is not displayed according to Filter ");
				break;
			}
			jse.executeScript("window.scrollBy(0,1)", "");
		}
		
		if(optioncounnt==optionvalues.size()){
			
			logger.log(LogStatus.PASS, "The DMApproved Data is displayed according to Filter ");
		}else{
			logger.log(LogStatus.FAIL, "The Data is not displayed according to Filter ");
			
		}
		
		
			
			
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
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","AuctionWorkBench_Verify_DRA_PropertyFilters" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","AuctionWorkBench_Verify_DRA_PropertyFilters" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","AuctionWorkBench_Verify_DRA_PropertyFilters" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}