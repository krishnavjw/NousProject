package Scenarios.Merchandise;

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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Buy_MerchandisePage;
import Pages.CustDashboardPages.CheckoutPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard extends Browser_Factory{

	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "Merchandise","Merchandise","Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard");
	}

	@Test(dataProvider="getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard", "Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard");
			Reporter.log("Test case started: " +testcaseName, true);


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Merchandise").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			//Bifrostpop.clickContiDevice();
			Thread.sleep(10000);

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

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.navigate().refresh();
			Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			//===============================================================================

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title

			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");
			Thread.sleep(10000);
		
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "PM Dashboard is displayed ",image2);
			
			//Click and verify Buy merchandise page			
			pmhomepage.clk_BuyMerchandiseLnk();
			logger.log(LogStatus.INFO, "Buy Merchandise Link Clicked sucessfully");
			Thread.sleep(1000);
			
			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			

			Buy_MerchandisePage buy_Merch=new Buy_MerchandisePage(driver);

			if(buy_Merch.verify_pagetitle()){
				String scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					String  image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Buy merchandise page is displayed ");
				logger.log(LogStatus.INFO, "Buy merchandise page is displayed ",image1);
			}
			else{
				String  scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					String  image1=logger.addScreenCapture(scpath1);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
				logger.log(LogStatus.FAIL, "Buy merchandise page is not displayed  ");
				logger.log(LogStatus.INFO, "Buy merchandise page is not displayed ",image1);
			} 
		
			//verify on page load by default radio button is checked for "locks"
			if(buy_Merch.getAttribute_LocationRadio()){

				 String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.INFO, "Location Radio button selected by default ");
				logger.log(LogStatus.INFO, "Location Radio button selected by default ",image1);
			}else{
				String  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Location Radio button not selected by default ");
				logger.log(LogStatus.INFO, "Location Radio button not selected by default ",image1);
			}

			//select any category and verify all the item details
			buy_Merch.click_All_radiobtn();
			logger.log(LogStatus.INFO, "All Radio button is selected");
			Thread.sleep(1000);

			if(buy_Merch.verify_itemName() && buy_Merch.verify_itemDesc() && buy_Merch.verify_SKU() && buy_Merch.verify_QuantityOnHand())
			{
				String itemname=buy_Merch.gettxt_itemName();
				String itemDesc=buy_Merch.gettxt_itemDesc();
				String sku=buy_Merch.gettxt_SKU();
				String quantityonHand=buy_Merch.gettxt_QuantityOnHand();
				//String priceperunit=buy_Merch.gettxt_pricePerUnit();

				logger.log(LogStatus.INFO, "The item details are:" +itemname+","+itemDesc+","+sku+","+quantityonHand);

			}
			else{
				logger.log(LogStatus.INFO, "The item details are not available");
			}

			//enter quantity of items 
			buy_Merch.enter_Quantity_item1(tabledata.get("Quantity_item1"));
			buy_Merch.enter_Quantity_item2(tabledata.get("Quantity_item2"));
			logger.log(LogStatus.INFO, "Able to enter amounts in the Quantity field");
			//screenshot
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Item images",image);

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);
			//screenshot
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Items added to the cart is displayed in the cart section",image);

			//verify Ability to adjust current quantity in cart section
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,750)", "");
			buy_Merch.click_add_item();
			//buy_Merch.click_subtarct_item();
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Able to adjust current quantity in the shopping cart successfully");

			//Able to scroll and seeing the items
			Thread.sleep(2000);
			//buy_Merch.scrolling();
			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
			int numberOfPixelsToDragTheScrollbarDown = 5000;
			dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0,numberOfPixelsToDragTheScrollbarDown).release().perform();
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Able to scroll to see all types of items available in Buy Merchandise page");
			
			
			buy_Merch.click_RemoveLink();
			logger.log(LogStatus.INFO, "Able to remove item using the Remove link ");
			Thread.sleep(1000);

			buy_Merch.click_RemoveAllLink();
			logger.log(LogStatus.INFO, "Able to removeAll items using the RemoveAll link ");
			Thread.sleep(1000);

			//enter quantity of items again
			buy_Merch.enter_Quantity_item1(tabledata.get("Quantity_item1"));
			buy_Merch.enter_Quantity_item2(tabledata.get("Quantity_item2"));

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);


			//verify script and customer screen tabs are in the buy Merchandise screen
			if(buy_Merch.verify_Script() && buy_Merch.verify_customerscreen()){

				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.PASS, "The Script and customer screen tabs are displayed in the merchandise screen ");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are displayed in the merchandise screen",image1);
			}
			else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.FAIL, "The Script and customer screen tabs are  not displayed in the merchandise screen");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are  not displayed in the merchandise screen",image1);
			}
			

			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(5000);

			//verify pm lands to checkout page
			CheckoutPage check= new CheckoutPage(driver);
			if(check.verify_pagetitle()){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Checkout page is displayed");
				logger.log(LogStatus.INFO, "Checkout page is displayed ",image1);
			}else{
				String  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Checkout page is not displayed ");
				logger.log(LogStatus.INFO, "Checkout page is not displayed",image1);
			}

			int i=check.get_Countofproducts();
			logger.log(LogStatus.INFO, "The no.of items in checkout is:"+i);

			//verify the total Amount of each product
			double product1_price=Double.parseDouble(check.get_Product1_Price().replace("$", ""));
			int product1_Quantity=Integer.parseInt(check.get_Product1_Quantity());
			double product2_price=Double.parseDouble(check.get_Product2_Price().replace("$", ""));
			int product2_Quantity=Integer.parseInt(check.get_Product2_Quantity());

			String Actualproduct1_total=check.get_Product1_Amt();
			String Actualproduct2_total=check.get_Product2_Amt();

			String expProduct1_total="$"+(product1_price*product1_Quantity);
			String expProduct2_total="$"+(product2_price*product2_Quantity);

			if((Actualproduct1_total.equals(expProduct1_total)) && (Actualproduct2_total.equals(expProduct2_total)))
			{
				logger.log(LogStatus.PASS, "Actual item1 :"+Actualproduct1_total+ " and Expected item1 :"+expProduct1_total+ " values are matching");
				logger.log(LogStatus.PASS, "Actual item2 :" +Actualproduct2_total+" and Expected item2 :"+Actualproduct2_total+ "values are matching");
			}
			else{
				logger.log(LogStatus.FAIL, "Actual item1 :"+Actualproduct1_total+ " and Expected item1 :" +expProduct1_total+" values are not matching");
				logger.log(LogStatus.PASS, "Actual item2 :" +Actualproduct2_total+" and Expected item2 :" +Actualproduct2_total+" values are matching");
			}

			//verify promotion and coupon section should be displayed
			if(check.verify_promotion_Section()){
				logger.log(LogStatus.PASS, "promotion and coupon section is displayed");
			}
			else{
				logger.log(LogStatus.FAIL, "promotion and coupon section is not displayed");
			}

			//click on confirm with customer
			check.click_ConfWithCust();
			logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
			Thread.sleep(6000);

			//switching to customer screen
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switching  to customer  screen");
			Reporter.log("Switching  to customer screen",true);
			Thread.sleep(10000);
			
			//screenshot
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Accept btn is available in CFS",image);


			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS,"Clicked on Accept btn in customer facing screen");
			Thread.sleep(3000);

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS,"Switching back to PM screen");
			Reporter.log("Switching back to PM screen",true);

			
			jse.executeScript("window.scrollBy(0,1500)", "");

			//verify editcart and submit buttons are displayed
			if((check.verify_Editcart()) && (check.verify_Submit())){
				logger.log(LogStatus.PASS, "Editcart and submit buttons is displayed");
			}
			else{
				logger.log(LogStatus.FAIL, "Editcart and submit buttons is not displayed");
			}

			//Edit cart items
			check.click_Editcart();
			logger.log(LogStatus.INFO, "Clicked on edit cart button,to add more items");
			Thread.sleep(5000);

			buy_Merch.click_add_item();
			
			//jse.executeScript("window.scrollBy(0,500)", "");
			Thread.sleep(5000);
			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(5000);

			//click on confirm with customer
			check.click_ConfWithCust();
			logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
			Thread.sleep(6000);

			//switching to customer screen
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS,"Switching  to customer  screen");
			Reporter.log("Switching  to customer screen",true);
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS,"Clicked on Accept btn in customer facing screen");
			Thread.sleep(3000);

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS,"Switching back to PM screen");
			Reporter.log("Switching back to PM screen",true);

			
			//verify payments dropdown
			check.verify_payDropdown();
			logger.log(LogStatus.INFO, "payments dropdown is displayed in checkout page");

			check.click_payDropdown();
			Thread.sleep(2000);
		/*	check.sel_Cash_fromDropdown();
			logger.log(LogStatus.INFO, "Selected cash option from dropdown");
			Thread.sleep(2000);*/
			
			check.sel_MO_fromDropdown();
			logger.log(LogStatus.INFO, "Selected MO option from dropdown");
			Thread.sleep(2000);
			
			check.clickmanualentry();
			logger.log(LogStatus.INFO, "Clicked on manual entry button");
			Thread.sleep(3000);
			
			check.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			check.Enter_CheckAccountNumber(tabledata.get("CheckAccNum"));
			check.Enter_checkNumber(tabledata.get("CheckNum"));
			Thread.sleep(2000);
							
						
			jse.executeScript("scroll(0, 2000);");

		

			//String total=driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText();
			check.enter_checkAmount();
			logger.log(LogStatus.INFO, "Entered the Amount in the amount field");
			Thread.sleep(1000);

			check.click_Apply();
			logger.log(LogStatus.INFO, "Clicked on Apply btn");

			check.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on Submit btn");
			Thread.sleep(5000);
			
			scpath2 = Generic_Class.takeScreenShotPath();
			 image2 = logger.addScreenCapture(scpath2);
				logger.log(LogStatus.PASS, "Transaction complete Modal window is displayed");
				logger.log(LogStatus.INFO, "Transaction complete Modal window is displayed", image2);

			//enter emp id and click ok
			boolean mwindow=driver.findElement(By.xpath("//span[text()='Transaction Complete']")).isDisplayed();
			logger.log(LogStatus.INFO,"Modal window is displayed"+mwindow);
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			driver.findElement(By.partialLinkText("Ok")).click();
			logger.log(LogStatus.INFO,"Entered employee id and clicked on ok btn successfully");
			Thread.sleep(10000);
			
			Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);
    		cust.click_AccountActivities();

			Thread.sleep(8000);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String CurrentDate = sdf.format(date);                
            List<WebElement> resultRows = driver.findElements(By.xpath("//div[@id='activities-grid']//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr"));
              
            int rowSize = resultRows.size();
            for(int i1=1; i1<=rowSize; i1++){
                  
                  String Date = resultRows.get(i1-1).findElement(By.xpath("./td[2]")).getText();
                  System.out.println("date"+Date);
                 
                
              
              if(Date.equals(CurrentDate)){
                  logger.log(LogStatus.INFO, "date verified :"+Date);
                  String Description = resultRows.get(i1-1).findElement(By.xpath("./td[7]")).getText();
                  if(Description.contains("Payment ")) {
                        logger.log(LogStatus.INFO, "Account Activity created successfully :"+Description);
                        break;}
                  
                  }
                  else{
                        if(resultFlag.equals("pass"))
                          resultFlag="fail";    
                          scpath=Generic_Class.takeScreenShotPath();
                          image=logger.addScreenCapture(scpath);
                           logger.log(LogStatus.FAIL, "Account Activity not created successfully");
                           logger.log(LogStatus.INFO, "Account Activity not created successfully",image);
                         }
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
			Excel.setCellValBasedOnTcname(path, "Merchandise","Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Merchandise","Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Merchandise","Noncustomer_Buying_Merchandise_Using_Buy_Merchandise_Link_From_PM_Dashboard" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
