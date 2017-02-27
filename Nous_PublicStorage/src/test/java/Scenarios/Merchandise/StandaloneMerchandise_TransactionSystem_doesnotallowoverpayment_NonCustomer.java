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

	public class StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer extends Browser_Factory{

		public ExtentTest logger;

		String path=Generic_Class.getPropertyValue("Excelpath");
		String resultFlag="pass";

		@DataProvider
		public Object[][] getCustSearchData() 
		{
			return Excel.getCellValue_inlist(path, "Merchandise","Merchandise","StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer");
		}

		@Test(dataProvider="getCustSearchData")
		public void payment(Hashtable<String, String> tabledata) throws InterruptedException 
		{
			try{
				logger=extent.startTest("StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer", "StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer");
				Reporter.log("Test case started: " +testcaseName, true);


				if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Merchandise").equals("Y")))
				{
					resultFlag="skip";
					throw new SkipException("Skipping the test");
				}

				// Login To the Application
				LoginPage login= new LoginPage(driver);
	            login.login(tabledata.get("UserName"),tabledata.get("Password"));
	            logger.log(LogStatus.INFO, "User logged in successfully as PM");

	            Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

	            String biforstNum=Bifrostpop.getBiforstNo();

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
	            Thread.sleep(2000);
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
	            driver.switchTo().window(tabs.get(0));
	            //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
	            robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	            Thread.sleep(5000);
	            driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
	            Thread.sleep(5000);

				//===============================================================================

				//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title

				PM_Homepage pmhomepage=new PM_Homepage(driver);	
				Thread.sleep(3000);
				logger.log(LogStatus.INFO, "PM Home page object created successfully");
				
				String scpath2=Generic_Class.takeScreenShotPath();
				String image2=logger.addScreenCapture(scpath2);
				logger.log(LogStatus.INFO, "PM Dashboard is displayed ",image2);


				pmhomepage.clk_BuyMerchandiseLnk();
				Thread.sleep(5000);
				logger.log(LogStatus.INFO, "Clicked on Buy Merchandise link in the PM Dashboard");
				
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

				buy_Merch.click_AddItemsToCart_btn();
				logger.log(LogStatus.INFO, "Add items from different categories and clicked on Add items to cart button");
				Thread.sleep(5000);

			

				//verify script and customer screen tabs are in the buy Merchandise screen
				if(buy_Merch.verify_Script() && buy_Merch.verify_customerscreen()){
					 scpath2=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath2,true);
					 image2=logger.addScreenCapture(scpath2);
					logger.log(LogStatus.PASS, "The Script and customer screen tabs are displayed in the merchandise screen");
					logger.log(LogStatus.INFO, "The Script and customer screen tabs are displayed in the merchandise screen",image2);
				}else{
					 scpath2=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath2,true);
					 image2=logger.addScreenCapture(scpath2);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					logger.log(LogStatus.FAIL, "The Script and customer screen tabs are  not displayed in the merchandise screen");
					logger.log(LogStatus.INFO, "The Script and customer screen tabs are  not displayed in the merchandise screen",image2);
				
				}
					Thread.sleep(2000);
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
					String scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					String image1=logger.addScreenCapture(scpath1);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					logger.log(LogStatus.FAIL, "Checkout page is not displayed ");
					logger.log(LogStatus.INFO, "Checkout page is not displayed",image1);
				}
				Thread.sleep(2000);

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

				driver.findElement(By.id("confirmButton")).click();
				logger.log(LogStatus.PASS,"Clicked on Accept btn in customer facing screen");
				Thread.sleep(3000);

				// switched back to Main screen
				driver.switchTo().window(tabs.get(0));
				logger.log(LogStatus.PASS,"Switching back to PM screen");
				Reporter.log("Switching back to PM screen",true);

				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,1500)", "");

				//verify editcart and submit buttons are displayed
				if((check.verify_Editcart()) && (check.verify_Submit())){
					logger.log(LogStatus.PASS, "Editcart and submit buttons is displayed");
				}
				else{
					logger.log(LogStatus.FAIL, "Editcart and submit buttons is not displayed");
				}

//verify payment dropdown
				check.verify_payDropdown();
				logger.log(LogStatus.INFO, "Payment methods dropdown is displayed in checkout page");
				
				check.click_payDropdown();
				
				Thread.sleep(2000);
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
								
				check.entermoreAmount();
				logger.log(LogStatus.INFO, "Entered  Amount more than total Amount");
				Thread.sleep(3000);
				
				jse.executeScript("scroll(0, 2000);");

				check.click_Apply();
				logger.log(LogStatus.INFO, "Clicked on Apply btn");
				
				check.click_No_RadioBtn();
				Thread.sleep(2000);
				
				//Verify Overlap message
				if(check.getOverPaymentErrorMessage().contains("Cannot Accept Overpayment")){
					
					String scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					String image1=logger.addScreenCapture(scpath1);
					logger.log(LogStatus.PASS, "Cannot Accept Overpayment using check error message is displayed successfully  ");
					logger.log(LogStatus.INFO, "Cannot Accept Overpayment  using check error message is displayed successfully  ",image1);
				}else{
					String scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					String image1=logger.addScreenCapture(scpath1);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					logger.log(LogStatus.FAIL, "Cannot Accept Overpayment  using check error message is not  displayed  ");
					logger.log(LogStatus.INFO, "Cannot Accept Overpayment  using check error message is not  displayed  ",image1);
				}
				
				check.click_CollectSignature();
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Clicked on Collect signature(s) button");
				
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.INFO, "image",image1);
				
				String info=driver.findElement(By.xpath("//div[contains(text(),'Transaction cannot proceed.')]")).getText();
				logger.log(LogStatus.PASS, "The expected message : "+info+" is displayed");
				
				driver.findElement(By.partialLinkText("OK")).click();
				
				
				check.click_RemoveBtn();
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Clicked on Remove btn");
				
				check.click_payDropdown();
				Thread.sleep(2000);
				check.sel_Cash_fromDropdown();
				logger.log(LogStatus.INFO, "Selected cash option from dropdown");
				Thread.sleep(2000);
				
				String total=driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText().replace("$", "");
				double amount=Double.parseDouble(total)+10;
				String Amount="$"+amount;
				
				driver.findElement(By.id("cashAmount")).clear();
				driver.findElement(By.id("cashAmount")).sendKeys(Amount);
				//check.txt_Amount();
				Thread.sleep(2000);
				
				jse.executeScript("scroll(0, 2000);");

				check.click_Apply();
				logger.log(LogStatus.INFO, "Clicked on Apply btn");
				
				check.click_No_RadioBtn();
				Thread.sleep(2000);
				
				
				//Verify Overlap message
				if(check.getOverPaymentErrorMessage().contains("Cannot Accept Overpayment")){
					
					 scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					 image1=logger.addScreenCapture(scpath1);
					logger.log(LogStatus.PASS, "Cannot Accept Overpayment using cash error message is displayed successfully  ");
					logger.log(LogStatus.INFO, "Cannot Accept Overpayment using cash error message is displayed successfully  ",image1);
				}else{
					 scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					 image1=logger.addScreenCapture(scpath1);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					logger.log(LogStatus.FAIL, "Cannot Accept Overpayment using cash  error message is not  displayed  ");
					logger.log(LogStatus.INFO, "Cannot Accept Overpayment using cash error message is not  displayed  ",image1);
				}
				
				check.click_CollectSignature();
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Clicked on Collect signature(s) button");
				
				 scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				 image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.INFO, "image",image1);
				
				 info=driver.findElement(By.xpath("//div[contains(text(),'Transaction cannot proceed.')]")).getText();
				logger.log(LogStatus.PASS, "The expected message : "+info+" is displayed");
				
				driver.findElement(By.partialLinkText("OK")).click();


			}
			catch(Exception ex){
				ex.printStackTrace();
				String scpath1=Generic_Class.takeScreenShotPath();
				String image1=logger.addScreenCapture(scpath1);
				resultFlag = "fail";
				Reporter.log("Exception ex: " + ex, true);
				logger.log(LogStatus.FAIL, "Test Script fail due to exception");
				logger.log(LogStatus.INFO, "Exception",image1);
							
			}


		}


		@AfterMethod
		public void afterMethod(){

			Reporter.log(resultFlag,true);

			if(resultFlag.equals("pass")){
				Excel.setCellValBasedOnTcname(path, "Merchandise","StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "Merchandise","StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "Merchandise","StandaloneMerchandise_TransactionSystem_doesnotallowoverpayment_NonCustomer" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}
	}



