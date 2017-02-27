package Scenarios.InventoryMerchandise;

import java.util.Hashtable;import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


import Pages.AWB.UnitDetailsPage;

import Pages.AWB.UnitDetailsPage;

import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.InventoryDelivery;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Scenarios.Browser_Factory;


public class InventoryMerchandise_InventoryDelivery extends Browser_Factory{

	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise","InventoryMerchandise_InventoryDelivery");
	}

	@Test(dataProvider="getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("InventoryMerchandise_InventoryDelivery", "InventoryMerchandise_InventoryDelivery");
			Reporter.log("Test case started: " +testcaseName, true); 


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("InventoryMerchandise").equals("Y")))
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
			
			if(pmhomepage.get_WlkInCustText().contains("Walk-In Customer")){
				

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to  the PM dashboard successfully");
				logger.log(LogStatus.INFO, "User navigated to  the PM dashboard successfully",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "User not navigated to  the PM dashboard  ");
				logger.log(LogStatus.INFO, "User not navigated to  the PM dashboard  ",image);
			}
			
			
			
			//Navigate to Dashboard>Manage Property>Inventory Delivery


			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1500)", "");
			
			pmhomepage.clickmanageProp();
			logger.log(LogStatus.INFO, "clicked on Manage property Link successfully");
			Thread.sleep(3000);
			
		
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
			
			
			
			promgmt.click_InventoryDelivery_Btn();
			
			logger.log(LogStatus.PASS, " User clicked on Inventory Delivery link successfully");
			Thread.sleep(4000);
			
			// Verify User should be able to navigate to Inventory Delivery Screen
			
			InventoryDelivery  delivery=new InventoryDelivery(driver);
			
			if(delivery.InventoryDelivery_Title_isDisplayed()){
				

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User  navigated to Inventory Delivery Screen successfully");
				logger.log(LogStatus.INFO, "User  navigated to Inventory Delivery Screen successfully",image1);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "User not navigated to Inventory Delivery Screen  ");
				logger.log(LogStatus.INFO, "User not navigated to Inventory Delivery Screen  ",image);
			}
				
			
			
			//Verify the "Open Purchase Orders" field
			if(delivery.select_PurchaseOrder_defaultValue_isDisplayed()){
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, " Dropdown is having default value as : Select Purchase Order ");
			logger.log(LogStatus.INFO, "Dropdown is having default value as : Select Purchase Order",image1);
		}else{
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			logger.log(LogStatus.FAIL, "Dropdown is not  having default value as : Select Purchase Order  ");
			logger.log(LogStatus.INFO, "Dropdown is not  having default value as : Select Purchase Order  ",image);
		}
			
			
			
		
			
			//Select a Open Purchase Orders from the "Transferring From" dropdown
			
			
			
			
			Thread.sleep(5000);
			delivery.select_OpenPurchaseOrder();
			logger.log(LogStatus.PASS, " User is able to select Purchase Order from dropdown ");

			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "The receive merchandise entry screen   displayed indicating the various product ID / SKUs of items being transferred into the property.");
			logger.log(LogStatus.INFO, "The receive merchandise entry screen   displayed indicating the various product ID / SKUs of items being transferred into the property.",image1);
			
			
			Thread.sleep(5000);
			if(delivery.LineItem_Header_isDisplayed()&& delivery.ProductID_Header_isDisplayed() && delivery.ItemName_Header_isDisplayed() && delivery.OrderdQty_Header_isDisplayed() && delivery.ReceivedQty_Header_isDisplayed()){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image11=logger.addScreenCapture(scpath1);
				
				
				logger.log(LogStatus.PASS, "All the options Line Item,Product ID ,Item Name,Orders Qty and Received Qty displayed and  display the list of items for the selected Purchase Order");	
				logger.log(LogStatus.INFO, "All the options Line Item,Product ID ,Item Name,Orders Qty and Received Qty displayed and  display the list of items for the selected Purchase Order",image11  );
				
				
			}
			
			
			
			else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, " Not displaying  the list of items for the selected Purchase Order  ");
				logger.log(LogStatus.INFO, "Not displaying  the list of items for the selected Purchase Order ",image);
			}
			
			
			//For some Productid/SKU, enter a positive interger value in received qty field which is greater than Ordered Qty

			String ReceivedQtyBeforeUpdate = delivery.getQtyValBeforeUpdate();
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image11=logger.addScreenCapture(scpath1);
			
			
			logger.log(LogStatus.PASS, "Received Qty befor update is : " + ReceivedQtyBeforeUpdate);	
			logger.log(LogStatus.INFO, "Received Qty befor update is :" + ReceivedQtyBeforeUpdate,image11  );
		
			
			
			Thread.sleep(5000);
			
			
			
			String ReceivedQty = delivery.get_ProductQty();
			
			int Qty = Integer.parseInt(ReceivedQty);
			
			
			int AddQtyVal = Qty + 2;
			 String UpdatedGreaterQty=Integer.toString(AddQtyVal);
			
			 delivery.clear_ReceivedQtyField();
			Thread.sleep(3000);
			 
			 delivery.EnterQty(UpdatedGreaterQty);
			
		
			
			//delivery.EnterUpdatedQtyVal_getVal();
			Thread.sleep(4000);
			
			String scpath11=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath11,true);
			String image111=logger.addScreenCapture(scpath11);
			
			
			logger.log(LogStatus.PASS, "User is able to enter quantity greater than Orderd qty ");	
			logger.log(LogStatus.INFO, "User is able to enter quantity greater than Orderd qty ",image111  );
		
			
			//Clear the values in the Received Qty field
			
			delivery.clear_ReceivedQtyField();
			Thread.sleep(3000);
			
			
			delivery.enterComments("Update Purchase order");
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(3000);
			
			delivery.click_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button successfully");	
			
			
			String ErrorMessage = delivery.get_ErrorMessage();
			
			
			
			//Screenshot
			
			String scpathErrormsg=Generic_Class.takeScreenShotPath();
			Reporter.log(scpathErrormsg,true);
			String imageErrormsg=logger.addScreenCapture(scpathErrormsg);
			
			logger.log(LogStatus.PASS, "Error message displayed successfully as :" + ErrorMessage );	
			logger.log(LogStatus.INFO, "Error message displayed successfully as ",imageErrormsg );
			
			
			
			
			
			
			
			delivery.click_OKbtn_ErrorMsg();
			
			logger.log(LogStatus.PASS, "Clicked on OK button successfully");	
			
			
			
			//For some Productid/SKU, enter a positive interger value in received qty field which is lower than Ordered Qty
			
			
          // String ReceivedQty1 = delivery.get_ProductQty();
           
           String ReceivedQty1=driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr/td[4]")).getText();
           
           
           
			
			int Qty1 = Integer.parseInt(ReceivedQty1);
			
			
			int updatedQtyVal = Qty1 - 1;
			
			 String UpdatedLesserQty=Integer.toString(updatedQtyVal);
			
			 //String UpdatedLesserQty="updatedQtyVal";
			
			 delivery.EnterQty(UpdatedLesserQty);
				
			
			
			
			
			
			
			//delivery.EnterLessQtyVal_getVal();
			
			String scpath111=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath111,true);
			String image1111=logger.addScreenCapture(scpath111);
			
			
			logger.log(LogStatus.PASS, "User is able to Enter quantity value less than Purchased order ");	
			logger.log(LogStatus.INFO, "User is able to Enter quantity value less than Purchased order ",image1111  );
		
			//For some Productid/SKU, enter a "0" in the received qty field
			delivery.clear_ReceivedQtyField();
			Thread.sleep(3000);
			
			delivery.EnterQty(tabledata.get("Quantity"));
			logger.log(LogStatus.PASS, "User is able to enter value as  : 0 ");	
			delivery.ClearComments();
			
			
			
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(3000);
			
			delivery.click_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button successfully");	
			
			
			String ErrorMessage1 = delivery.get_ErrorMessage_Comments();
			
			
			String scpath1111=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1111,true);
			String image11111=logger.addScreenCapture(scpath1111);
			
			
			logger.log(LogStatus.PASS, "Error message displayed successfully as :" + ErrorMessage1);	
			logger.log(LogStatus.PASS, "Error message displayed successfully as :" + ErrorMessage1 ,image11111);	
			
			
			
			
			
			delivery.click_OKbtn_ErrorMsg();
			
			logger.log(LogStatus.PASS, "Clicked on OK button successfully");	
			
			
			
			delivery.enterComments("Update Purchase order");
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(3000);
			
			delivery.click_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button successfully");
			
			
			//Verify EmployeeID modal window should be displayed
			
			if(delivery.InventoryDeliveryComplete_Header_IsDisplayed()){
				String scpathModalWin=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathModalWin,true);
				String imageModalWin=logger.addScreenCapture(scpathModalWin);
				
				
				logger.log(LogStatus.PASS, "Employee ID modal window displayed successfully");	
				logger.log(LogStatus.INFO, "Employee ID modal window displayed successfully",imageModalWin  );
				
				
			}
			
			
			
			else{
				String scpathModalWin=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathModalWin,true);
				String imageModalWin=logger.addScreenCapture(scpathModalWin);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, " Employee ID modal window not  displayed  ");
				logger.log(LogStatus.INFO, "Employee ID modal window not displayed ",imageModalWin);
			}
			
			delivery.click_Cancel_Btn();
			logger.log(LogStatus.PASS, "Clicked on Cancel  successfully");	
			
			//Verify User should be taken back to Inventory Delivery screen

            if(delivery.InventoryDelivery_Title_isDisplayed()){
				

				String scpathInventoryDelScreen=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathInventoryDelScreen,true);
				String imageInventoryDelScreen=logger.addScreenCapture(scpathInventoryDelScreen);
				logger.log(LogStatus.PASS, "User  navigated back  to Inventory Delivery Screen successfully");
				logger.log(LogStatus.INFO, "User  navigated back to Inventory Delivery Screen successfully",imageInventoryDelScreen);
			}else{
				String scpathInventoryDelScreen=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathInventoryDelScreen,true);
				String image=logger.addScreenCapture(scpathInventoryDelScreen);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "User not navigated back to Inventory Delivery Screen  ");
				logger.log(LogStatus.INFO, "User not navigated back to Inventory Delivery Screen  ",image);
			}
			
			
			
			
            
            
			
			
            delivery.click_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button again successfully");	
			
			
			
			
			
			delivery.enter_EmployeeID(tabledata.get("InvalidUserName"));
			logger.log(LogStatus.PASS, "Entered invalid Employee ID successfully :" +tabledata.get("InvalidUserName" ));	
			Thread.sleep(4000); 
			delivery.click_OK_btn();
			Thread.sleep(5000); 
			logger.log(LogStatus.PASS, "Clicked on OK  button  successfully");	
			
			String EmpIDErrorMessage = delivery.get_EMPID_Errormessage();
			
			if(EmpIDErrorMessage.contains("Please enter an employee number.")){
				

				String scpathInventoryDelScreen=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathInventoryDelScreen,true);
				String imageInventoryDelScreen=logger.addScreenCapture(scpathInventoryDelScreen);
				logger.log(LogStatus.PASS, "Error message displayed successfully as :" + EmpIDErrorMessage );
				logger.log(LogStatus.INFO, "Error message displayed successfully ",imageInventoryDelScreen);
			}else{
				String scpathInventoryDelScreen=Generic_Class.takeScreenShotPath();
				Reporter.log(scpathInventoryDelScreen,true);
				String image=logger.addScreenCapture(scpathInventoryDelScreen);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Error message not displayed ");
				logger.log(LogStatus.INFO, "Error message not displayed  ",image);
			}
			
			
			delivery.clear_EmployeeID();
			//Enter a valid employeeid in the employeeid field
			
			delivery.enter_EmployeeID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "Entered valid Employee ID successfully :" +tabledata.get("UserName" ));	
			Thread.sleep(4000); 
			delivery.click_OK_btn();
			logger.log(LogStatus.PASS, "Clicked on OK  button  successfully");	
			
			
			
			
		
			Thread.sleep(15000);  
	        // Verify User should be taken back to PM Dashboard
  
          if(pmhomepage.get_WlkInCustText().contains("Walk-In Customer")){
				

				String scpathPMDashboard=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String imagePMDashboard=logger.addScreenCapture(scpathPMDashboard);
				logger.log(LogStatus.PASS, "User navigated to  the PM dashboard successfully");
				logger.log(LogStatus.INFO, "User navigated to  the PM dashboard successfully",image1);
			}else{
				String scpathPMDashboard=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String imagePMDashboard=logger.addScreenCapture(scpathPMDashboard);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "User not navigated to  the PM dashboard  ");
				logger.log(LogStatus.INFO, "User not navigated to  the PM dashboard  ",imagePMDashboard);
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
				Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryDelivery" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryDelivery" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryMerchandise_InventoryDelivery" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}
	
	
	
	
	
	
}
