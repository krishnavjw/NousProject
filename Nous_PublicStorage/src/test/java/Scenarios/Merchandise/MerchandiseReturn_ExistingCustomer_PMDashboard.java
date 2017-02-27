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
import org.openqa.selenium.interactions.Action;
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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.MerchandiseReturnPage;
import Pages.CustDashboardPages.Merchandise_TransactionHistory;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class MerchandiseReturn_ExistingCustomer_PMDashboard extends Browser_Factory{

	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	/*String query="select  top 1 a.accountnumber, soi.vacatedate, soi.StorageOrderItemTypeID, AOI.notax NoTax, T.description as Customer_Type, TS.Description As Authorized_Access"
			+" from contact c"+
			" join customer cu on cu.contactid=c.contactid"+
			" join account a on a.customerid=cu.customerid"+
			" join accountorder ao on ao.accountid=a.accountid"+
			" join accountorderitem aoi on aoi.accountorderid=ao.accountorderid"+
			" join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid"+
			" join type t on t.typeid=soi.StorageOrderItemTypeID"+
			" inner join CLTransactionMaster CTM on CTM.AccountID = A.AccountID"+
			" inner join CLPayment CLP on CLP.CLTransactionMasterID = CTM.CLTransactionMasterID"+
			" left join StorageOrderItemContact SOIC on SOIC.StorageOrderItemID = SOI.StorageOrderItemID "+
			" left join Type TS on TS.TypeID = SOIC.ContactTypeID"+
			" left join AccountOrderItemProductOption AOIP on AOIP.AccountOrderItemID = AOI.AccountOrderItemID"+
			" where AOI.notax = 0 and soi.vacatedate is null and soi.StorageOrderItemTypeID=4300"+
			" and AOI.notax = 0 and CLP.PaymentSourceTypeID = 258 and soic.contacttypeid = 3152 "+
			" AND aoip.accountorderitemproductoptionid is null";*/
	
	
	String query="Select top 1  A.AccountNumber  from CLTransactionMaster CLTM with (nolock)" 
    +" Join Account A with (nolock) on CLTM.AccountID = A.AccountID  where 1=1 "
    +" And CLTM.CLTransactionTypeID = 239 "
    +" And datediff(dd, CLTM.RecordDateTime, DATEADD(day, DATEDIFF(day, 0, GETDATE()), 0)) <30 " 
    +" order by CLTM.RecordDateTime  desc";

	
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "Merchandise","Merchandise","MerchandiseReturn_ExistingCustomer_PMDashboard");
	}

	@Test(dataProvider="getCustSearchData")
	public void MerchandiseReturn_ExistingCustomer_PMDashboard(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("MerchandiseReturn_ExistingCustomer_PMDashboard", "MerchandiseReturn_ExistingCustomer_PMDashboard");
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
			            Thread.sleep(8000);
			            driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			            Thread.sleep(5000);

			 
			//================================================================================

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title

			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");


			Thread.sleep(10000);
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on Advance search link successfully");

			Advance_Search advsearch= new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Adv search object created successfully");

			//Fetching Acc num through Quer who is having Balance due
			
			//String query=" select top 1 accountid from cltransactionmaster where ipaddress='"+tabledata.get("ipaddress")+"' and cltransactiontypeid=239";
			String accNum=DataBase_JDBC.executeSQLQuery(query);
			advsearch.enterAccNum(accNum);
			//advsearch.enterAccNum("6791186");
			//advsearch.enterAccNum(tabledata.get("Account Number"));
			advsearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Entered account number and clicked on search btn successfully"+accNum);
			Reporter.log("click on serach button", true); 
			Thread.sleep(10000);

			// js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "customer dashboard is displayed",image);


			Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);

			//selecting Return Merchandise from Quick linkd Dropdown
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]")).click();

			JavascriptExecutor je = (JavascriptExecutor) driver;
			//Identify the WebElement which will appear after scrolling down
			WebElement element =driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[4]"));
			// now execute query which actually will scroll until that element is not appeared on page.
			je.executeScript("arguments[0].scrollIntoView(true);",element);
			element.click();
			logger.log(LogStatus.INFO, "Selected Return Merchandise option from Quick links dropdown successfully");
			Reporter.log("Selected  Return Merchandise option from Quick links dropdown successfully",true);

			Thread.sleep(3000);
			Merchandise_TransactionHistory trans= new Merchandise_TransactionHistory(driver);

			if(trans.verify_pagetitle()){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Merchandise_TransactionHistory page is displayed ");
				logger.log(LogStatus.INFO, "Merchandise_TransactionHistory page is displayed ",image1);
			}else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Merchandise_TransactionHistory page is displayed ");
				logger.log(LogStatus.INFO, "Merchandise_TransactionHistory page is displayed ",image1);
			}
			Thread.sleep(3000);

			trans.click_dropdown();
			logger.log(LogStatus.INFO, "Clicked on dropdown successfully");
			Thread.sleep(1000);

			trans.click_option_dropdown();
			logger.log(LogStatus.INFO, "Selected option from the dropdown successfully");
			Thread.sleep(1000);

			trans.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on submit button");
			Thread.sleep(5000);

			//verify the page elements in the merchandise return screen
			MerchandiseReturnPage merchreturn=new MerchandiseReturnPage(driver);

			if(merchreturn.verify_pagetitle()){
				logger.log(LogStatus.PASS, " merchandise return page is displayed ");
			}
			else{
				logger.log(LogStatus.FAIL, "merchandise return page is not displayed  ");
			}

			//verify script and customer screen tabs are available
			if(merchreturn.verify_Script() && merchreturn.verify_customerscreen()){

				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "The Script and customer screen tabs are displayed in the merchandise screen ");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are displayed in the merchandise screen",image1);
			}
			else{
				String  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				resultFlag="fail";
				logger.log(LogStatus.FAIL, "The Script and customer screen tabs are  not displayed in the merchandise screen");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are  not displayed in the merchandise screen",image1);
			}
			
			// Verify the page elements on the merchandise retun screen 
						if (driver.findElement(By.xpath("//span[text()='Date:']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Date Column displayed successfully");

						} else {

							logger.log(LogStatus.FAIL, "Date Column not displayed ");

						}

						if (driver.findElement(By.xpath("//span[text()='Time:']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Time Column displayed successfully");

						} else {

							logger.log(LogStatus.FAIL, "Time Column not displayed ");

						}

						if (driver.findElement(By.xpath("//span[text()='Receipt:']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Receipt Column displayed successfully");

						} else {

							logger.log(LogStatus.FAIL, "Receipt Column not displayed ");

						}

						if (driver.findElement(By.xpath("//span[text()='Payment Type:']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Payment Type Column displayed successfully");

						} else {

							logger.log(LogStatus.FAIL, "Payment Type Column not displayed ");

						}
						
						if (driver.findElement(By.xpath("//span[text()='Reason for Return:']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Reason for Return Column displayed successfully");

						} else {

							logger.log(LogStatus.FAIL, "Reason for Return Column not displayed ");

						}

						if (driver.findElement(By.xpath("//span[text()='SELECT']")).isDisplayed()) {

							logger.log(LogStatus.PASS, "Reason for Return: by default selected as SELECT");

						} else {

							logger.log(LogStatus.FAIL, "Reason for Return: by default not selected as SELECT ");

						}

			//select reason for return
			merchreturn.click_dropdown();
			Thread.sleep(1000);
			merchreturn.click_option_Other();
			logger.log(LogStatus.INFO, "Selected reason for return");
			
			logger.log(LogStatus.INFO, "Verifying all the line items for the products");

			String productname=merchreturn.verify_Productname();
			logger.log(LogStatus.INFO, "The product name is :"+productname);
			
			boolean ImageCount=merchreturn.verify_ImageCount();
			logger.log(LogStatus.INFO, "The product name is :"+ImageCount);
			
			String price=merchreturn.verify_Price();
			logger.log(LogStatus.INFO, "The product name is :"+price);
			
			String quantity=merchreturn.verify_quantity();
			logger.log(LogStatus.INFO, "The product name is :"+quantity);
			
			String purchasesubtotal= merchreturn.verify_purchaseSubtotal();
			logger.log(LogStatus.INFO, "The product name is :"+purchasesubtotal);
			
			String returnQuantity= merchreturn.verify_returnQuantity();
			logger.log(LogStatus.INFO, "The product name is :"+returnQuantity);
			
			boolean adust_AddBtn=merchreturn.verify_Adjust_addBtn();
			logger.log(LogStatus.INFO, "The product name is :"+adust_AddBtn);
			
			boolean adjustSub_Btn=merchreturn.verify_Adjust_subBtn();
			logger.log(LogStatus.INFO, "The product name is :"+adjustSub_Btn);

			//add return quantity
			merchreturn.click_addQuantity();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "selected required return quantity");
			
			//JavascriptExecutor jse = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,250)", "");

			merchreturn.click_calculatebtn();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "After selecting the quantity clicked on calculate btn to get total");

			//verifying the return total
			logger.log(LogStatus.INFO, "Return total amount is: "+merchreturn.get_totalReturn());
			
			String ActretSubtotal1=merchreturn.get_returnSub_AftCalculate().replace("$", "");
			String ActretSubtotal="$"+ActretSubtotal1;
			logger.log(LogStatus.INFO, "ActualSubtotal:"+ActretSubtotal);
			String returnQuantityAft_Calculate=merchreturn.get_retQuantity_After_Calculate();
			double ExpRetSubtotal=	(Double.parseDouble(ActretSubtotal1))*(Integer.parseInt(returnQuantityAft_Calculate));
			String expSubtotal="$"+ExpRetSubtotal;
			logger.log(LogStatus.INFO, "expSubtotal:"+expSubtotal);
			System.out.println(expSubtotal);
			if(ActretSubtotal.equals(expSubtotal)){
				logger.log(LogStatus.PASS, "Actual:"+ActretSubtotal+ " and Expected values "+expSubtotal+  "are matching");
			}
			else{
				logger.log(LogStatus.FAIL, "Actual:"+ActretSubtotal+ " and Expected values " +expSubtotal+ " are not matching");
			}
			
			//============================================================
			//Fetching salestax from DB and comparing from UI
			String salesTax=merchreturn.get_salesTaxReturn().replace("$", "");
			String taxquery="select s.taxpercent, s.* from sitetax s "+
				  "	join type t on s.taxtypeid=t.typeid  "+
				  " where s.siteid="+tabledata.get("SiteId")+ "and t.name='Merchandise' and s.expirationdate is null ";
			ArrayList<String> salestaxpercent=DataBase_JDBC.executeSQLQuery_List(taxquery);
			String taxpercent=salestaxpercent.get(0);
			System.out.println("The salestax percentage is : "+taxpercent );
			logger.log(LogStatus.INFO,"The salestax percentage is : "+taxpercent );
			
			double salestaxAmount=((Double.parseDouble(ActretSubtotal1))*(Double.parseDouble(taxpercent)))/100;
			
			//double salestax_DB=Math.ceil(salestaxAmount);
			double salestax_DB = Math.ceil(salestaxAmount*100)/100;
			System.out.println("The sales tax amount is: "+salestax_DB);
			String saletax=""+salestax_DB;
			
			if(saletax.equals(salesTax)){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are same",image1);
			}
			else{
				String  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				resultFlag="fail";
				logger.log(LogStatus.FAIL, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are not same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are not same",image1);
			}
				
			
			
			//==========================================================
			

			//String salesTax=merchreturn.get_salesTaxReturn().replace("$", "");
			//String ActreturnTotal1=merchreturn.get_totalReturn().replace("$", "");
			Double ActreturnTotal1=Double.parseDouble(merchreturn.get_totalReturn().replace("$", ""));
			String ActreturnTotal="$"+ActreturnTotal1;
			logger.log(LogStatus.INFO, "Actual return total:"+ActreturnTotal);
			double expRetTotal=(Double.parseDouble(salesTax))+(Double.parseDouble(ActretSubtotal1));
			String expTotalret="$"+expRetTotal;
			System.out.println(expTotalret);
			logger.log(LogStatus.INFO, "expreturntotal:"+expTotalret);
			if(ActreturnTotal.equals(expTotalret)){
				logger.log(LogStatus.PASS, "Actual:"+ActreturnTotal+ " and Expected values "+expTotalret+  "are matching");
			}
			else{
				logger.log(LogStatus.FAIL, "Actual:"+ActreturnTotal+ " and Expected values "+expTotalret+ " are not matching");
			}

			//verify enough cash on hand Question
			if(merchreturn.verify_enoughCashOnHand_Question()){
				logger.log(LogStatus.PASS, "Is enough cash on hand to refund this transaction? is displaying");
			}
			else{
				logger.log(LogStatus.FAIL, "Is enough cash on hand to refund this transaction? is not displaying");
			}

			//click on yes radiobtn
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(4000);
			merchreturn.click_yes_Radiobtn();
			logger.log(LogStatus.INFO, "Clicked yes radio btn");
			
			//click on confirm with customer
			merchreturn.click_ConfWithCust();
			logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
			Thread.sleep(6000);

		
            
          //switching to customer screen
			 driver.switchTo().window(tabs.get(1));
			 logger.log(LogStatus.PASS,"Switching  to customer  screen");
			 Reporter.log("Switching  to customer screen",true);
		     Thread.sleep(10000);
		     
		     robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	            Thread.sleep(8000);
		     
				// Verify the return details displayed in CFS 
		     
		    // screenshot
		     String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "customer name,Receipt number are displayed in CFS",
						image1);
				//Thread.sleep(15000);
		     
		     
		     if (driver.findElement(By.xpath("//th[text()='Product']")).isDisplayed()
						&& driver.findElement(By.xpath("//th[text()='Price']")).isDisplayed()
						&& driver.findElement(By.xpath("//th[text()='Return Quantity']")).isDisplayed()
						&& driver.findElement(By.xpath("//th[text()='Return Subtotal']")).isDisplayed()) {
					
					logger.log(LogStatus.PASS,
							"Return details Product, Price,Quantity ,Subtotal coulmn displayed successfully in CFS screen");

				}

				else {

					 logger.log(LogStatus.FAIL,
							"Return details Product, Price,Quantity ,Subtotal coulmn not displayed  in CFS screen");
				}
				String ReturnSubTotalinCFS = driver.findElement(By.xpath("//table/tbody/tr/td[4]/span")).getText();
				if (ActretSubtotal.contains(ReturnSubTotalinCFS)) {

					logger.log(LogStatus.PASS, "Return details displayed in CFS and Merchandise Return screen are same ");

				}

				else {

					 scpath1 = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1, true);
					 image1 = logger.addScreenCapture(scpath1);

					logger.log(LogStatus.FAIL,
							"Return details displayed in CFS and Merchandise Return screen are not  same");
				}

				
				
		     
		     WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-container']/canvas[@class='signature-pad']"));
	         
    		Actions actionBuilder1 = new Actions(driver);
    		Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
    							.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
    		drawAction1.perform();
    		Thread.sleep(6000);
    		
    		
    		js.executeScript("window.scrollBy(0,2000)", "");
    		 scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				 image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "image ",
						image1);
    		
    		 driver.findElement(By.xpath("//button[text()='Accept']")).click();
    		 logger.log(LogStatus.PASS,"Clicked on Accept btn in customer facing screen");
    		 Thread.sleep(3000);
    		 
    	    // switched back to Main screen
    		driver.switchTo().window(tabs.get(0));
           logger.log(LogStatus.PASS,"Switching back to PM screen");
           Reporter.log("Switching back to PM screen",true);
           
           Thread.sleep(4000);
           js.executeScript("window.scrollBy(0,2000)", "");
            merchreturn.click_Approve();
            logger.log(LogStatus.INFO,"Clicked on approve btn successfully");
            Thread.sleep(3000);
            merchreturn.click_Submit();
            logger.log(LogStatus.INFO,"Clicked on Submit btn successfully");
        
            //enter emp id and click ok
            driver.findElement(By.xpath("//span[text()='I have given cash to the customer']/preceding-sibling::span[@class='button']")).click();
            driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
            driver.findElement(By.partialLinkText("Ok")).click();
            logger.log(LogStatus.INFO,"Entered empid and clicked on ok btn successfully");

            Thread.sleep(8000);
            
            
           //verify new record should be created in db
            
            String name_DB=DataBase_JDBC.executeSQLQuery("select name from type where typeid="+
               		" (select top 1 CLTransactionTypeID from cltransactionmaster where  "
               		+ "ipaddress='"+tabledata.get("ipaddress")+"'"
               				+ " and description='"+tabledata.get("Description")+"')");
            logger.log(LogStatus.INFO, "New Record of refund has been created in db"+name_DB);
           
            Thread.sleep(8000);
    		try{
    			driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
    			Thread.sleep(2000);
    		}catch(Exception ex){}
    		
    		try{
    			driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
    			Thread.sleep(2000);
    		}catch(Exception ex){}
    		
    		Thread.sleep(5000);
    		
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
                  if(Description.contains("Refund ")) {
                        logger.log(LogStatus.INFO, "Account Activity created successfully :"+Description);
                        break;}
                  
                  }
                  else{
                        if(resultFlag.equals("pass"))
                          resultFlag="fail";    
                          scpath1=Generic_Class.takeScreenShotPath();
                          image1=logger.addScreenCapture(scpath1);
                           logger.log(LogStatus.FAIL, "Account Activity not created successfully");
                           logger.log(LogStatus.INFO, "Account Activity not created successfully",image1);
                         }
                  }
    		
    		

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
			Excel.setCellValBasedOnTcname(path, "Merchandise","MerchandiseReturn_ExistingCustomer_PMDashboard" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Merchandise","MerchandiseReturn_ExistingCustomer_PMDashboard" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Merchandise","MerchandiseReturn_ExistingCustomer_PMDashboard" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
