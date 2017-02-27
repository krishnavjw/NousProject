package Scenarios.InventoryMerchandise;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.AWB.PropertyManagementPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.ConfirmReceiptTransferredMerchandisePopUpPage;
import Pages.InventoryMerchandise.ReceiveTransferredMerchandisePage;
import Scenarios.Browser_Factory;

public class InventoryMerchandise_InventoryReceive extends Browser_Factory {

	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise","InventoryMerchandise_InventoryReceive");
	}

	@Test(dataProvider="getCustSearchData")
	public void InventoryMerchandise_InventoryReceive(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("InventoryMerchandise_InventoryReceive", "Inventory Receive");
			Reporter.log("Test case started: " +testcaseName, true); 


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("InventoryMerchandise").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage loginPage = new LoginPage(driver);
			String sitenumber=loginPage.get_SiteNumber();
			Thread.sleep(2000);
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username ");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password ");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login button");
			logger.log(LogStatus.INFO, "Login to Application as PM successfully");
			Thread.sleep(2000);
			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);


			PM_Homepage pmhomepage = new PM_Homepage(driver);
			if(pmhomepage.isexistingCustomerModelDisplayed()){    
				String scpath=Generic_Class.takeScreenShotPath();
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to PMM DashBaord successfully");
				logger.log(LogStatus.INFO, "img",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User is not navigated to PM DashBaord");
				logger.log(LogStatus.INFO, "img ",image);
			}

			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(0,1500)", "");
			Thread.sleep(1000);
			pmhomepage.clickmanageProp();
			logger.log(LogStatus.PASS, "clicked on the Manage Property link on the PM DashBoard successfully");

			Thread.sleep(4000);
			PropertyManagementPage promgmt=new PropertyManagementPage(driver);
			if(promgmt.verify_PageTitle()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to property management page successfully");
				logger.log(LogStatus.INFO, "img",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "property management page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}


			promgmt.clk_InventoryReceiveLink();
			logger.log(LogStatus.PASS, "clicked on the Inventory Receive link  on the Property Management screen");

			Thread.sleep(3000);
			ReceiveTransferredMerchandisePage recTrnsPage=new ReceiveTransferredMerchandisePage(driver);
			if(recTrnsPage.isReceiveTransferredMerchandiseTitleDiplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to Receive Transferred Merchandise page successfully");
				logger.log(LogStatus.INFO, "img",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User not navigated to Receive Transferred Merchandise page");
				logger.log(LogStatus.INFO, "img",image);
			}

			if(recTrnsPage.isTransferringFromDropDwnDisplayed()){

				logger.log(LogStatus.PASS, "Transferring From drop down is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Transferring From drop down is not displayed");
			}

			recTrnsPage.clk_TransferringFromDropDown();
			logger.log(LogStatus.INFO, "clicked on the Transferring From drop down");
			Thread.sleep(1000);
			WebElement ele=driver.findElement(By.xpath("//ul[@id='InventoryReceive_ReceiveFrom_listbox']/li[4]"));
			ele.click();
			logger.log(LogStatus.INFO, "selected value from drop down");
			Thread.sleep(1000);
			
			String scpath=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image1);

			String productid=driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[3]")).getText().trim();
			logger.log(LogStatus.INFO, "Product ID/SKU on Receive Transferred Merchandise page is---->"+productid);

			String itemName=driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[4]")).getText().trim();
			logger.log(LogStatus.INFO, "Item Name on Receive Transferred Merchandise page is---->"+itemName);

			String QuantitySent=driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[5]")).getText().trim();
			logger.log(LogStatus.INFO, "QuantitySent on Receive Transferred Merchandise page is---->"+QuantitySent);

			String difference=driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[8]")).getText().trim();
			logger.log(LogStatus.INFO, "difference on Receive Transferred Merchandise page is---->"+difference);

			driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[6]/input")).sendKeys(QuantitySent);
			logger.log(LogStatus.INFO, "entered the data in the Quantity Received text box");


			driver.findElement(By.xpath("//div[@id='receive-grid']/div/table/tbody/tr/td[8]/textarea")).sendKeys(tabledata.get("Explanation"));
			logger.log(LogStatus.INFO, "entered the data in the Explanation text box");

			String snap=Generic_Class.takeScreenShotPath();
			String ima=logger.addScreenCapture(snap);
			logger.log(LogStatus.INFO, "img",ima);

			Thread.sleep(1000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(250,0)");
			Thread.sleep(2000);
			recTrnsPage.clk_accptBtn();
			logger.log(LogStatus.PASS, "clicked on the accept button");
			Thread.sleep(2000);

			ConfirmReceiptTransferredMerchandisePopUpPage confirm=new ConfirmReceiptTransferredMerchandisePopUpPage(driver);
			if(confirm.isNotefieldDisplayed()){
				String scpa=Generic_Class.takeScreenShotPath();
				String ima1=logger.addScreenCapture(scpa);
				logger.log(LogStatus.PASS, "Confirm Receipt of Transferred Merchandise page is dispalyed");
				logger.log(LogStatus.INFO, "img",ima1);
			}else{
				String scpa=Generic_Class.takeScreenShotPath();
				String ima1=logger.addScreenCapture(scpa);
				logger.log(LogStatus.FAIL, "Confirm Receipt of Transferred Merchandise page is not dispalyed");
				logger.log(LogStatus.INFO, "img",ima1);
			}
			Thread.sleep(2000);
			confirm.enterNote(tabledata.get("Explanation"));
			logger.log(LogStatus.INFO, "entered note");

			Thread.sleep(1000);
			confirm.enterEmployeeId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "entered employee id"); 

			Thread.sleep(1000);
			confirm.clk_ConfirmBtn();
			logger.log(LogStatus.INFO, "clicked on the confirm button");

			Thread.sleep(10000);
			if(pmhomepage.isexistingCustomerModelDisplayed()){    
				String scpat1=Generic_Class.takeScreenShotPath();
				String ima1=logger.addScreenCapture(scpat1);
				logger.log(LogStatus.PASS, "User navigated back to PM DashBaord successfully");
				logger.log(LogStatus.INFO, "img",ima1);
			}else{
				String scpat1=Generic_Class.takeScreenShotPath();
				String ima1=logger.addScreenCapture(scpat1);
				logger.log(LogStatus.FAIL, "User is not navigated back to PM DashBaord");
				logger.log(LogStatus.INFO, "img ",ima1);
			}
			
			/*
			String query="select siteid from site where sitenumber='"+sitenumber+"' ";
			String siteid=DataBase_JDBC.executeSQLQuery(query);
			*/
			
			
			String query ="Select top 1 PS.QuantityOnHand from InventoryLog IL "
					+ "Join ProductSite PS on PS.ProductID=IL.ProductID "
					+ "Join Product P on PS.ProductID=P.ProductID "
					+ "join site s on s.siteid=il.siteid "
					+ "Where 1=1 "
					+ "And s.sitenumber='"+sitenumber+"' "
					+ "And P.ProductTypeID=46 "
					+ "Order by IL.LastUpdate Desc";
			
			String DbQuanitityOnHand=DataBase_JDBC.executeSQLQuery(query);
			
			if(DbQuanitityOnHand==QuantitySent){
				
				logger.log(LogStatus.PASS, "Quantity recived on UI--->"+QuantitySent+"Quantity recived on database--->"+DbQuanitityOnHand+"   Are matching");
			}else{
				
				logger.log(LogStatus.FAIL, "Quantity recived on UI and Database are not matching");
			}
			
           



		}catch(Exception ex){
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
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryReceive" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryReceive" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryReceive" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}