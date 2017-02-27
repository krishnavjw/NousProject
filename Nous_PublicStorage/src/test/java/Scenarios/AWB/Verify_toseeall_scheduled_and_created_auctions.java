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

import GenericMethods.DataBase_JDBC;
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

public class Verify_toseeall_scheduled_and_created_auctions extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AuctionWorkBench","AuctionWorkBench","Verify_toseeall_scheduled_and_created_auctions");
	}

	@Test(dataProvider="getCustSearchData")
	public void Verify_toseeall_scheduled_and_created_auctions_method(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Verify_toseeall_scheduled_and_created_auctions", "Verify to see all the scheduled and created auctions are showing up in the auction management section with dates and units and approvals");
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
			
			
		String unitnumber=driver.findElement(By.xpath("//div[@id='Unitgrid']//tbody//tr[1]/td[2]")).getText();
		
		
		
		String ipadd = Generic_Class.getIPAddress();

		String siteid_ipsdd = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
				+ ipadd+ "'";
		
		String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid_ipsdd);
		Thread.sleep(5000);
		
		
		String auctionid = "select top 1 au.auctionid from auction a "+
									"join Auctionunit au on au.auctionid=a.auctionid "+
									"join Storageorderitem soi on soi.Storageorderitemid=au.Storageorderitemid "+
									"join accountorderitem aoi on aoi.Storageorderitemid=soi.Storageorderitemid "+
									"Join Rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
									"left outer JOIN Type t on t.typeid=au.DmApprovalstatustypeid "+
									"where ru.rentalunitnumber='"+unitnumber+"' "+
									"and  aoi.siteid='"+siteid_ipsdd_data+"' "+ 
									"order by au.LastUpdate  desc";
		
		String auctionid_data = DataBase_JDBC.executeSQLQuery(auctionid);
		
		
		String auctionunit = "select count(*) from auctionunit where auctionid ='"+auctionid_data+"' and deleted=0";

		String auctionunit_data = DataBase_JDBC.executeSQLQuery(auctionunit);
		
		
		String unitcount=driver.findElement(By.xpath("//table[@id='grid']//tbody/tr/td[5]")).getText();
		
		
		if(unitcount.equals(auctionunit_data)){
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The count displayed is equal to DB---"+auctionunit_data+"--from UI--"+unitcount);
			logger.log(LogStatus.INFO, "The count displayed is equal",image1);
		}else{
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image2=logger.addScreenCapture(scpath1);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			logger.log(LogStatus.FAIL, "The count displayed is not equal to DB---"+auctionunit_data+"--from UI--"+unitcount);
			logger.log(LogStatus.INFO, "The count displayed is not equal ",image2);
		}
		
		
		String unitcount_approved=driver.findElement(By.xpath("//table[@id='grid']//tbody/tr/td[6]")).getText();
		
		int unitcount_approved_UI=Integer.parseInt(unitcount_approved);
		
		String appruvedunit = " select ru.rentalunitnumber from auction a "+
								"join Auctionunit au on au.auctionid=a.auctionid "+
								"join Storageorderitem soi on soi.Storageorderitemid=au.Storageorderitemid "+
								"join accountorderitem aoi on aoi.Storageorderitemid=soi.Storageorderitemid "+
								"Join Rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
								"left outer JOIN Type t on t.typeid=au.DmApprovalstatustypeid "+
								"where au.auctionid ='"+auctionid_data+"' "+
								 "and au.DMApprovalStatusTypeID=1850 "+
								 "and  aoi.siteid='"+siteid_ipsdd_data+"' "+ 
								  "order by ru.rentalunitnumber asc";
										
		List<String> appruvedunit_data= DataBase_JDBC.executeSQLQuery_List(appruvedunit);
		
		if(appruvedunit_data.size()==unitcount_approved_UI){
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The DM approved Auction count is equal---"+appruvedunit_data.size()+"--from UI--"+unitcount_approved_UI);
			logger.log(LogStatus.INFO, "The DM approved Auction count is equal",image1);
		}else{
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image2=logger.addScreenCapture(scpath1);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			logger.log(LogStatus.FAIL, "The DM approved Auction count is not equal---"+appruvedunit_data.size()+"--from UI--"+unitcount_approved_UI);
			logger.log(LogStatus.INFO, "The DM approved Auction count is not equal ",image2);
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
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Verify_toseeall_scheduled_and_created_auctions" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Verify_toseeall_scheduled_and_created_auctions" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AuctionWorkBench","Verify_toseeall_scheduled_and_created_auctions" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}