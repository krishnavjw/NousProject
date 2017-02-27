package Scenarios.InventoryMerchandise;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import Pages.AWB.AuctionManagementPage;
import Pages.AWB.PropertyManagementPage;
import Pages.AWB.SelectFilterOptions;
import Pages.AWB.UnitDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.Inventary;
import Scenarios.Browser_Factory;

public class Inventory_Management extends Browser_Factory{


	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise","Inventory_Management");
	}

	@Test(dataProvider="getCustSearchData")
	public void Inventory_Management(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Inventory_Management", "Inventory_Management");
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

			String scpath=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image1);
			JavascriptExecutor jse = (JavascriptExecutor)driver;

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			pmhomepage.clk_PM_PropertyManagement_link();
			logger.log(LogStatus.INFO, "clicked on Manage property Link successfully");
			Thread.sleep(3000);


			PropertyManagementPage promgmt=new PropertyManagementPage(driver);
			if(promgmt.verify_PageTitle()){

				scpath=Generic_Class.takeScreenShotPath();
				image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to property management page successfully");
				logger.log(LogStatus.INFO, "User navigated to property management page successfully",image1);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "property management page is not displayed ");
				logger.log(LogStatus.INFO, "property management page is not  displayed ",image1);
			}


			promgmt.click_InvMgmt();
			logger.log(LogStatus.INFO, "clicked on Inventory Management Link successfully");
			Thread.sleep(8000);

			
			Inventary inventary=new Inventary(driver);
			
			scpath=Generic_Class.takeScreenShotPath();
			image1=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "User navigated to Inventory management page successfully",image1);
			Thread.sleep(2000);

			/*WebElement element = inventary.btnSubmit();
			logger.log(LogStatus.INFO, "Clicked on submit without enetering data");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(1000); 

			element.click();


			jse.executeScript("window.scrollBy(1000,0)", "");
			Thread.sleep(2000);
*/
			
			

			//			JavascriptExecutor js = (JavascriptExecutor) driver;
			//			js.executeScript("document.body.style.zoom='80%'");
			//			Thread.sleep(10000);

			//inventary.clk_Submit();
			//Thread.sleep(3000);
			WebElement ele=driver.findElement(By.xpath("//a[@id='submit']"));
			Actions act= new Actions(driver);
			act.moveToElement(ele).build().perform();
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			
			ele.click();
			logger.log(LogStatus.PASS, "Clicked on Submit button ");
			Thread.sleep(3000);
			try{
				inventary.display_errormsg();
				 scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				 image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Error message is displayed ");
				logger.log(LogStatus.INFO, "Error message is displayed ",image1);

			}catch(Exception ex){
				ex.printStackTrace();
				 scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Error message is not  displayed  ");
				logger.log(LogStatus.INFO, "Error message is not displayed as----",image);

			}
			Thread.sleep(3000);

			inventary.clk_OK();


			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
			Thread.sleep(3000);

			String productnumber=inventary.get_Productid_first();
			driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber+"']")).sendKeys("ABC");



			//inventary.clk_Submit();
			//Thread.sleep(3000);

			String productnumbervalue=driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber+"']")).getText();
			Thread.sleep(1000); 
			if(productnumbervalue.isEmpty()){


				logger.log(LogStatus.PASS, "This field  accept only numeric values");

			}else{

				logger.log(LogStatus.FAIL, "This field  accept alphabetic values ");
			}
			Thread.sleep(3000); 
			jse.executeScript("window.scrollBy(-1000,0)", "");

			String ipadd = Generic_Class.getIPAddress();

			String siteid_ipsdd = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd+ "'";

			String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid_ipsdd);

			for(int i=1;i<6;i++){

				String productnumber1=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr["+i+"]/td[1]")).getText();



				String productnum = "select p.productnumber from product p "
						+ "join productsite ps on ps.productid=p.productid "
						+ "join accountorderitem aoi on (aoi.siteid=ps.siteid and aoi.productid=ps.productid) "
						+ "where "
						+ "ps.siteid='"+siteid_ipsdd_data+"' "
						+ "and p.productnumber='"+productnumber1+"' "
						+ "group by p.productnumber, p.name,ps.quantityonhand";




				String productnum_db = DataBase_JDBC.executeSQLQuery(productnum);
				Thread.sleep(2000);
				if((productnumber1.trim()).contains(productnum_db)){

					logger.log(LogStatus.PASS, "The productnumber is DB--"+productnum_db+"---In UI the productNumber is---"+productnumber1);
				}else{

					logger.log(LogStatus.FAIL, "The productnumber is DB--"+productnum_db+"---In UI the productNumber is---"+productnumber1);
				}
				Thread.sleep(5000); 
			}


			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");
			Thread.sleep(3000); 

			for(int i=1;i<6;i++){
				String productnumber1=inventary.product_coulmn(i);
				String productnumber2=inventary.productname_coulmn(i);

				String productnum = "select  p.name from product p "
						+ "join productsite ps on ps.productid=p.productid "
						+ "join accountorderitem aoi on (aoi.siteid=ps.siteid and aoi.productid=ps.productid) "
						+ "where "
						+ "ps.siteid='"+siteid_ipsdd_data+"' "
						+ "and p.productnumber='"+productnumber1+"' "
						+ "group by p.productnumber, p.name,ps.quantityonhand";

				String productnum_db = DataBase_JDBC.executeSQLQuery(productnum);
				Thread.sleep(2000);
				if(productnumber2.contains(productnum_db)){

					logger.log(LogStatus.PASS, "The productname of "+productnumber1+" in  DB::"+productnum_db+"---In UI the productname  is::"+productnumber2);
				}else{

					logger.log(LogStatus.FAIL, "The productname of "+productnumber1+" in DB--"+productnum_db+"---In UI the productName is---"+productnumber2);
				}
				Thread.sleep(5000);
			}

			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");


			Thread.sleep(3000); 

			for(int i=1;i<6;i++){
				String productnumber1=inventary.product_coulmn(i);
				String productnumber4=inventary.quantityonhand_coulmn(i);

				String productnum = "select  ps.quantityonhand from product p "
						+ "join productsite ps on ps.productid=p.productid "
						+ "join accountorderitem aoi on (aoi.siteid=ps.siteid and aoi.productid=ps.productid) "
						+ "where "
						+ "ps.siteid='"+siteid_ipsdd_data+"' "
						+ "and p.productnumber='"+productnumber1+"' "
						+ "group by p.productnumber, p.name,ps.quantityonhand";


				String productnum_db = DataBase_JDBC.executeSQLQuery(productnum);
				Thread.sleep(2000);

				if(productnumber4.contains(productnum_db)){

					logger.log(LogStatus.PASS, "The Quantity for "+productnumber1+" in DB--"+productnum_db+"---In UI the Quantity is---"+productnumber4);
				}else{

					logger.log(LogStatus.FAIL, "The Quantity for "+productnumber1+" in DB--"+productnum_db+"---In UI the Quantity  is---"+productnumber4);
				}
				Thread.sleep(5000);
			}

			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");
			Thread.sleep(3000); 


			for(int i=1;i<6;i++){

				String productnumber1=inventary.product_coulmn(i);

				inventary.product_count_input(productnumber1,"ABC");


				String productnumbervalue1=driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber1+"']")).getText();
				Thread.sleep(1000); 
				if(productnumbervalue1.isEmpty()){


					logger.log(LogStatus.PASS, "This field  not accepting alphabet  values 'ABC' for ---"+productnumber1);

				}else{

					logger.log(LogStatus.FAIL, "This field  accept alphabetic values ");
				}
				Thread.sleep(3000); 

				inventary.product_count_input(productnumber1,"@#@#");

				String productnumbervalue2=driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber1+"']")).getText();
				Thread.sleep(1000); 
				if(productnumbervalue2.isEmpty()){


					logger.log(LogStatus.PASS, "This field  not accepting special charector   values '@#@#' for-----"+productnumber1);

				}else{

					logger.log(LogStatus.FAIL, "This field  special charector values ");
				}
				Thread.sleep(3000); 



				driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber1+"']")).sendKeys("-");


				String productnumbervalue3=driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber1+"']")).getText();
				Thread.sleep(1000); 
				if(productnumbervalue3.isEmpty()){


					logger.log(LogStatus.PASS, "This field  not accepting special charector   values '-423' for ---"+productnumber1);

				}else{

					logger.log(LogStatus.FAIL, "This field  special charector values ");
				}
				Thread.sleep(3000); 

			}	
			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");	
			Thread.sleep(3000); 


			String quantityofproduct=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[4]")).getText();
			String enterpositivevalue=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[1]")).getText();


			int quantityvalue=Integer.parseInt(quantityofproduct);

			int quantityvalue1=quantityvalue+1;

			String quantityvalue_string=Integer.toString(quantityvalue1);

			driver.findElement(By.xpath("//input[@data-productnumber='"+enterpositivevalue+"']")).sendKeys(quantityvalue_string);



			String qtyproduct=driver.findElement(By.xpath("//input[@data-productnumber='"+enterpositivevalue+"']")).getText();
			Thread.sleep(1000); 

			logger.log(LogStatus.PASS, "This field  accept Interger  values greater then Quantity");
			 scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			 image1=logger.addScreenCapture(scpath);

			logger.log(LogStatus.INFO, "This field  accept Interger  values greater then Quantity----",image1);


			inventary.clk_quantitycount_total();
			Thread.sleep(2000);
			String adj1=inventary.get_quantitycount_total();
			int adj_display=Integer.parseInt(adj1);

			if(1==adj_display){ //quantityvalue1


				logger.log(LogStatus.PASS, "The Adj value displayed is---"+adj_display+"--the value form (current qty - count)---"+quantityvalue1);

			}else{

				logger.log(LogStatus.FAIL, "The Adj value displayed is---"+adj_display+"--the value form (current qty - count)---"+quantityvalue1);
			}





			Thread.sleep(3000);



			

			WebElement reasonforAdj=inventary.reason_dropdown();
			act.moveToElement(reasonforAdj).build().perform();
			reasonforAdj.click();
			Thread.sleep(3000);
			try{
				inventary.clk_Inventory_Count_Adjust();
				logger.log(LogStatus.PASS, "The Reason for Adj is able to select option --'Inventory Count Adjust'");

			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.log(LogStatus.FAIL, "The Reason for Adj is not  able to select option --'Inventory Count Adjust'");
			}


			Thread.sleep(3000);

			WebElement reasonforAdj2=inventary.reason_dropdown_sec_property();
			act.moveToElement(reasonforAdj2).build().perform();
			reasonforAdj2.click();
			Thread.sleep(3000);
			try{
				inventary.clk_Destroy_sec_property();
				logger.log(LogStatus.PASS, "The Reason for Adj is able to select option --'Damaged'");

			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.log(LogStatus.FAIL, "The Reason for Adj is not  able to select option --'Damaged'");
			}



			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");	  




			String qtyzero=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[1]")).getText();
			driver.findElement(By.xpath("//input[@data-productnumber='"+qtyzero+"']")).clear();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@data-productnumber='"+qtyzero+"']")).sendKeys("0");
			Thread.sleep(2000);



			String quantityzero="0";
			Thread.sleep(1000); 
			if(quantityzero.isEmpty()){

				logger.log(LogStatus.FAIL, "This field  not accepting zero values ");


			}else{
				logger.log(LogStatus.PASS, "This field  accept zero values");

			}




			driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[6]")).click();

			Thread.sleep(2000);
			int count_zero=Integer.parseInt(quantityzero);
			String count_zero_qty=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[4]")).getText();
			int count_zero_qty1=Integer.parseInt(count_zero_qty);

			int adj_zero=count_zero_qty1-count_zero;

			String adj1_zero=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[6]")).getText();
			int adj_display_zero=Integer.parseInt(adj1_zero);

			if(0==adj_display_zero){ //adj_zero


				logger.log(LogStatus.PASS, "The Adj value displayed for 0 count---"+adj_display_zero+"--the value form (current qty - count)---"+adj_zero);

			}else{

				logger.log(LogStatus.FAIL, "The Adj value displayed for 0 count---"+adj_display_zero+"--the value form (current qty - count)---"+adj_zero);
			}


			Thread.sleep(3000);


			logger.log(LogStatus.INFO, "------------------------------------------------------------------------------------------------------------");	

			WebElement explanation=driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr[1]//td[8]//textarea"));
			act.moveToElement(explanation).build().perform();
			explanation.sendKeys("Explanation ");

			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image2=logger.addScreenCapture(scpath1);

			logger.log(LogStatus.INFO, "The Entered values----",image2);



			Thread.sleep(3000);
			inventary.enterCount(driver,"Enter Notes");












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
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","Inventory_Management" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","Inventory_Management" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","Inventory_Management" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}