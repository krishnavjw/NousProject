package Scenarios.InventoryMerchandise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

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
import Pages.AWB.PropertyManagementPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.InventoryDelivery;
import Scenarios.Browser_Factory;

public class InventoryDeliveryDBValidation extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise", this.getClass().getSimpleName());
	}

	@Test(dataProvider="getData")
	public void inventoryDeliveryDBValidation(Hashtable<String, String> tabledata) throws InterruptedException
	{
		

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("InventoryMerchandise").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, this.getClass().getSimpleName()+" is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{

			//Login to PS Application
			logger=extent.startTest(this.getClass().getSimpleName(),"Inventory Delivery DB Validation");
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();
			Thread.sleep(10000);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)","");
			Thread.sleep(3000);

			PM_Homepage home = new PM_Homepage(driver);
			String location = home.getLocation();
			
			home.clickmanageProp();
			Thread.sleep(5000);

			PropertyManagementPage promgmt=new PropertyManagementPage(driver);
			promgmt.click_InventoryDelivery();
			Thread.sleep(10000);


			InventoryDelivery invDelivery = new InventoryDelivery(driver);
			if(invDelivery.InventoryDelivery_Title_isDisplayed()){
				logger.log(LogStatus.PASS, "Navigated to Inventory Delivery Page");
			}else{
				logger.log(LogStatus.FAIL, "Did not navigated to Inventory Delivery Page");
				resultFlag="fail";
			}
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);


			invDelivery.select_OpenPurchaseOrder();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "Selected the Open Purchase Order");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			String query = "select siteid from site where sitenumber = "+location;
			String siteId = DataBase_JDBC.executeSQLQuery(query);
			String quantityInHand = "";

			ArrayList<String> productIds = invDelivery.getProductIds();
			LinkedHashMap<String, String> productQuantityMap = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> productQuantityMap2 = new LinkedHashMap<String, String>();

			if(productIds.size()>2){
				
				String id = "";
				for(int i=0; i<productIds.size(); i++){
					if(i>2){
						break;
					}
					
					id = productIds.get(i);
					query = "Select ps.quantityonhand from product p join productsite ps on ps.productid=p.productid "+
							" where  p.productnumber = '"+id+"' "+
							" and ps.siteid="+siteId;
					quantityInHand = DataBase_JDBC.executeSQLQuery(query);
					productQuantityMap.put(id, quantityInHand);
				}

				logger.log(LogStatus.INFO, "*** *** *** *** *** *** *** *** ***");
				logger.log(LogStatus.INFO, "Product Id & Quantity on Hand values before the update");
				for(String key : productQuantityMap.keySet()){
					logger.log(LogStatus.INFO, "Product Id - "+key+"  &  Quantity on Hand - "+productQuantityMap.get(key));
				}
				
				WebElement element = driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//div[@class='k-grid-content ps-container']/table//tr[1]/td[5]/input"));
				String vaue =  element.getAttribute("value");
				int oldReceivedQty = Integer.parseInt(vaue);
				int newReceivedQty = oldReceivedQty+5;
				element.clear();
				Thread.sleep(2000);
				element.sendKeys(Integer.toString(newReceivedQty));
				logger.log(LogStatus.INFO, "Updated the recieved quantity for the product - "+productIds.get(0)+" from "+oldReceivedQty+" to "+newReceivedQty);
				productQuantityMap2.put(productIds.get(0), Integer.toString(Integer.parseInt(productQuantityMap.get(productIds.get(0)))+newReceivedQty));
				
				
				element = driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//div[@class='k-grid-content ps-container']/table//tr[2]/td[5]/input"));
				vaue =  element.getAttribute("value");
				int oldReceivedQty2 = Integer.parseInt(vaue);
				int newReceivedQty2 = oldReceivedQty2-1;
				element.clear();
				Thread.sleep(2000);
				element.sendKeys(Integer.toString(newReceivedQty2));
				logger.log(LogStatus.INFO, "Updated the recieved quantity for the product - "+productIds.get(1)+" from "+oldReceivedQty2+" to "+newReceivedQty2);
				productQuantityMap2.put(productIds.get(1), Integer.toString(Integer.parseInt(productQuantityMap.get(productIds.get(1)))+newReceivedQty2));
				
				
				element = driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//div[@class='k-grid-content ps-container']/table//tr[3]/td[5]/input"));
				vaue =  element.getAttribute("value");
				int oldReceivedQty3 = Integer.parseInt(vaue);
				int newReceivedQty3 = 0;
				element.clear();
				Thread.sleep(2000);
				element.sendKeys(Integer.toString(newReceivedQty3));
				logger.log(LogStatus.INFO, "Updated the recieved quantity for the product - "+productIds.get(2)+" from "+oldReceivedQty3+" to "+newReceivedQty3);
				productQuantityMap2.put(productIds.get(2), productQuantityMap.get(productIds.get(2)));
				
				
				invDelivery.enterComments("Test Comment");
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Entered the Comments");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				
				((JavascriptExecutor) driver).executeScript("window.scrollBy(2000,0)","");
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,5000)","");
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,5000)","");
				Thread.sleep(3000);
				
				invDelivery.click_SubmitBtn();
				Thread.sleep(16000);
				invDelivery.enterEmployeeId(tabledata.get("UserName"));
				Thread.sleep(3000);
				logger.log(LogStatus.INFO, "Entered the EmployeeId");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				invDelivery.click_OkBtn();
				Thread.sleep(15000);
				
				home = new PM_Homepage(driver);
				if(home.IsElemPresent_FindReservation()){
					logger.log(LogStatus.PASS, "User re-directed to PM Dashboard");
				}else{
					logger.log(LogStatus.FAIL, "User not re-directed to PM Dashboard");
				}
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				
				Thread.sleep(30000);
				
				query = "Select ps.quantityonhand from product p join productsite ps on ps.productid=p.productid "+
						" where  p.productnumber = '"+productIds.get(0)+"' "+
						" and ps.siteid="+siteId;
				quantityInHand = DataBase_JDBC.executeSQLQuery(query);
				if(quantityInHand.equals(productQuantityMap2.get(productIds.get(0)))){
					logger.log(LogStatus.INFO, "For product - "+productIds.get(0)+", we updated the received quantity from "+oldReceivedQty+" to "+newReceivedQty);
					logger.log(LogStatus.PASS, "And Quantity on hand updated correctly in db from "+productQuantityMap.get(productIds.get(0))+" to "+productQuantityMap2.get(productIds.get(0)));
				}else{
					logger.log(LogStatus.INFO, "For product - "+productIds.get(0)+", we updated the received quantity from "+oldReceivedQty+" to "+newReceivedQty);
					logger.log(LogStatus.FAIL, "And Quantity on hand not updated correctly in db.  Old Value: "+productQuantityMap.get(productIds.get(0))+" New Value: "+productQuantityMap2.get(productIds.get(0)));
					resultFlag = "fail";
				}
				
				
				query = "Select ps.quantityonhand from product p join productsite ps on ps.productid=p.productid "+
						" where  p.productnumber = '"+productIds.get(1)+"' "+
						" and ps.siteid="+siteId;
				quantityInHand = DataBase_JDBC.executeSQLQuery(query);
				if(quantityInHand.equals(productQuantityMap2.get(productIds.get(1)))){
					logger.log(LogStatus.INFO, "For product - "+productIds.get(1)+", we updated the received quantity from "+oldReceivedQty2+" to "+newReceivedQty2);
					logger.log(LogStatus.PASS, "And Quantity on hand updated correctly in db from "+productQuantityMap.get(productIds.get(1))+" to "+productQuantityMap2.get(productIds.get(1)));
				}else{
					logger.log(LogStatus.INFO, "For product - "+productIds.get(1)+", we updated the received quantity from "+oldReceivedQty2+" to "+newReceivedQty2);
					logger.log(LogStatus.FAIL, "And Quantity on hand not updated correctly in db.  Old Value: "+productQuantityMap.get(productIds.get(1))+" New Value: "+productQuantityMap2.get(productIds.get(1)));
					resultFlag = "fail";
				}
				
				
				query = "Select ps.quantityonhand from product p join productsite ps on ps.productid=p.productid "+
						" where  p.productnumber = '"+productIds.get(2)+"' "+
						" and ps.siteid="+siteId;
				quantityInHand = DataBase_JDBC.executeSQLQuery(query);
				if(quantityInHand.equals(productQuantityMap2.get(productIds.get(2)))){
					logger.log(LogStatus.INFO, "For product - "+productIds.get(2)+", we updated the received quantity from "+oldReceivedQty3+" to "+newReceivedQty3);
					logger.log(LogStatus.PASS, "And Quantity on hand updated correctly in db from "+productQuantityMap.get(productIds.get(2))+" to "+productQuantityMap2.get(productIds.get(2)));
				}else{
					logger.log(LogStatus.INFO, "For product - "+productIds.get(2)+", we updated the received quantity from "+oldReceivedQty3+" to "+newReceivedQty3);
					logger.log(LogStatus.FAIL, "And Quantity on hand not updated correctly in db.  Old Value: "+productQuantityMap.get(productIds.get(2))+" New Value: "+productQuantityMap2.get(productIds.get(2)));
					resultFlag = "fail";
				}
				
				
			}else{
				logger.log(LogStatus.FAIL, "Less than 3 Product Id's available in this purchase order. Either select a different purchase order or login to a different location");
				resultFlag="fail";
			}

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Test script failed due to the exception",image);
		}

	}

	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"InventoryMerchandise",this.getClass().getSimpleName(), "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"InventoryMerchandise",this.getClass().getSimpleName(), "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise",this.getClass().getSimpleName() , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
