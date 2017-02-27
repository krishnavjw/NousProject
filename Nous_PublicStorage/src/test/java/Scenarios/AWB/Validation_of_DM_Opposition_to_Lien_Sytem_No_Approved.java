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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
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

public class Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AuctionWorkBench","AuctionWorkBench","Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved");
	}

	@Test(dataProvider="getCustSearchData")
	public void Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved", "Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved");
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
		
				logger.log(LogStatus.PASS, "Filter section  is  available for DM");	
			
			}catch(Exception e){
				logger.log(LogStatus.FAIL, "Filter section  is not available for DM");
				}
			
			//jse.executeScript("window.scrollBy(1000,0)", "");
			
			auctionmgmt.click_UnitDetails();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS, "Clicked on Unit Deatils link");
			Thread.sleep(3000);
			
			
			
			
			/*String value=driver.findElement(By.xpath("//div[@id='Unitgrid']//thead//th[@data-field='RentalUnitNumber']")).getAttribute("data-index");
			
			int indervalue=Integer.parseInt(value);
			int indervalue1=indervalue+2;
			
			List<WebElement> optionvalues=driver.findElements(By.xpath("//div[@id='Unitgrid']//tbody//tr/td["+indervalue1+"]"));
			System.out.println(" the value is---"+optionvalues.size());
			System.out.println(" the value is---"+optionvalues.get(0).getText().toString());
			
			int optioncounnt=0;
			for(int i=0;i<optionvalues.size();i++){
				
				if(optionvalues.get(i).getText().toString().equals("B108")){
					int j=i+1;
					driver.findElement(By.xpath("//div[@id='Unitgrid']//tbody//tr["+i+"]/td[1]")).click();
					break;
				}else{
					logger.log(LogStatus.FAIL, "The Data is not displayed according to Filter ");
					
				}
				jse.executeScript("window.scrollBy(0,1)", "");
			}*/
			
			driver.findElement(By.xpath("//div[@id='Unitgrid']//tbody//tr[1]/td[1]")).click();
			
			Thread.sleep(4000);
			driver.findElement(By.xpath("//li[@class='Approvals k-item k-state-default']/span/span")).click();
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Auction Approval button is expanded successfully");
			logger.log(LogStatus.INFO, "Auction Approval button is expanded successfully",image1);
			
			
			Thread.sleep(3000);
			
			
	
			WebElement mySelectElement = driver.findElement(By.xpath("//select[@class='ddl_dmApproval']"));
			Select dropdown= new Select(mySelectElement);
			dropdown.selectByVisibleText("Opposition to Lien");
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(1000,0)", "");
			Thread.sleep(2000);
			jse.executeScript("window.scrollBy(0,1000)", "");
			
			driver.findElement(By.xpath("//button[@id='btn_Save']")).click();
			
			
			try{
				driver.findElement(By.xpath("//select[@class='ddl_dmApproval']")).click();
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image2=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "DM approval drop down is not disabled ");
				logger.log(LogStatus.INFO, "DM approval drop down is not disabled ",image2);
				
			}catch(Exception e){
				
				
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image2=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "DM approval drop down is disabled");
				logger.log(LogStatus.INFO, "DM approval drop down is disabled",image2);
				}
			
			
			Thread.sleep(3000);
			

		
			
			Actions act = new Actions(driver);
			driver.findElement(By.xpath(("//div[@id='usernav']"))).click();
			act.moveToElement(driver.findElement(By.xpath(("//div[@id='usernav']")))).perform();
			driver.findElement(By.xpath("//a[contains(text(),'Log Off')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//a[contains(text(), 'Log Off')])[2]")).click();
			Thread.sleep(5000);
			
			
			
			
			loginPage.login("980538","password");
			logger.log(LogStatus.INFO, "Login to Application  successfully");
			Thread.sleep(8000);
			
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);
			
			
			Thread.sleep(5000);
			
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "DM Home page object created successfully");
			
			
			

			
			jse.executeScript("window.scrollBy(0,1500)", "");
			
			pmhomepage.clk_PM_PropertyManagement_link();
			logger.log(LogStatus.INFO, "clicked on Manage property Link successfully");
			Thread.sleep(3000);
			
			Thread.sleep(2000);
			
			
			promgmt.click_AuctionManagementLink();
			logger.log(LogStatus.INFO, "clicked on Auction Management Link successfully");
			Thread.sleep(2000);
			
			
			//verify filter section is not available for pm
		
			try{
				boolean flag=!(auctionmgmt.verify_filterssection());
		
				logger.log(LogStatus.PASS, "Filter section  is  available for DM");	
			
			}catch(Exception e){
				logger.log(LogStatus.FAIL, "Filter section  is not available for DM");
				}
			
			jse.executeScript("window.scrollBy(1000,0)", "");
			
			auctionmgmt.click_UnitDetails();
			Thread.sleep(10000);
			logger.log(LogStatus.PASS, "Clicked on Unit Deatils link");
			Thread.sleep(3000);
			
			
			driver.findElement(By.xpath("//div[@id='Unitgrid']//tbody//tr[1]/td[1]")).click();
			
			Thread.sleep(4000);
			driver.findElement(By.xpath("//li[@class='Approvals k-item k-state-default']/span/span")).click();
			String scpath3=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.PASS, "Auction Approval button is expanded successfully");
			logger.log(LogStatus.INFO, "Auction Approval button is expanded successfully",image3);
			
			
			Thread.sleep(3000);

			
			try{
				driver.findElement(By.xpath("//select[@class='ddl_rmApproval']")).click();
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image2=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "RM approval drop down is not disabled ");
				logger.log(LogStatus.INFO, "RM approval drop down is not disabled ",image2);
				
			}catch(Exception e){
				
				
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image2=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "RM approval drop down is disabled");
				logger.log(LogStatus.INFO, "RM approval drop down is disabled",image2);
				}
			
			
			Thread.sleep(3000);
			
			
			
			
			
			
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
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Validation_of_DM_Opposition_to_Lien_Sytem_No_Approved" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}