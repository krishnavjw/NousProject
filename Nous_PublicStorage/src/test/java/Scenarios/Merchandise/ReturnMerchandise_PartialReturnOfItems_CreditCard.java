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

import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.MerchandiseReturnPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class ReturnMerchandise_PartialReturnOfItems_CreditCard extends Browser_Factory{


	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "Merchandise","Merchandise","ReturnMerchandise_PartialReturnOfItems_CreditCard");
	}

	@Test(dataProvider="getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("ReturnMerchandise_PartialReturnOfItems_CreditCard", "ReturnMerchandise_PartialReturnOfItems_CreditCard");
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

			//================================================================================

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title

			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");
			
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "PM Dashboard is displayed ",image2);

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1500)", "");
			
			pmhomepage.clk_Merchand_RetLink();
			logger.log(LogStatus.INFO, "clicked on MerchandiseReturns Link successfully");
			Thread.sleep(30000);
			
		String Query="Select top 1 max(X.ReceiptNumber) as receiptnumber,X.accountorderid, X.accountnumber, sum(X.MerchandiseReturn) "+
					",  sum(X.Creditcard), sum(X.Cash),Sum(X.Cheque) From "+
					"(select A.accountnumber, AO.accountorderid, case when t.name='Merchandise Sale' then CLM.CLmastergroupID end as ReceiptNumber,  "+
					"t.name as CltransactionType, t1.name as paymenttype, clt.amount+Clt.discountamount as Amount, "+
					"case when t1.name='Credit Card' then 1 else 0 end as Creditcard, "+
					"case when t1.name='Cash' then 1 else 0 end as Cash,"+
					"case when t1.name='Check' then 1 else 0 end as Cheque, "+
					"Case when t.name='Merchandise Return' then 1 else 0 end MerchandiseReturn "+

					"from Account A with(nolock) "+
					"join accountorder AO with(nolock) on AO.accountid=A.accountid and A.accountid > 1 "+
					"join Accountorderitem AOI with(nolock) on AOi.accountorderid=AO.accountorderid and AOI.storageorderitemid is null "+
					"join Cltransaction CLT with(nolock) on CLt.Accountorderitemid=AOI.accountorderitemid "+
					"join Cltransactionmaster CLM with(nolock) on CLM.Cltransactionmasterid=clt.Cltransactionmasterid "+
					"join type T with(nolock) on t.typeid=CLM.Cltransactiontypeid "+
					"left join clpayment CLP with(nolock) on CLP.Cltransactionmasterid=CLM.Cltransactionmasterid "+
					"left join type T1 with(nolock) on T1.typeid=CLP.PaymentSourceTypeID "+
					"where aoi.storageorderitemid is null and aoi.Quantity > 0 and Datediff (dd, clm.posteddatetime, getutcdate())<30) X "+

					"group by X.accountorderid, X.accountnumber "+
					"having sum(X.MerchandiseReturn)=0  "+
					"and sum(X.Creditcard)>=1  "+
					"and sum(X.Cash)=0 "+
					"and Sum(X.Cheque)=0  ";

		System.out.println(Query);
			ArrayList<String> receiptnumber=DataBase_JDBC.executeSQLQuery_List(Query);
			Thread.sleep(60000);
			String number=receiptnumber.get(0);
			System.out.println(number);
			pmhomepage.enter_Receiptnumber(number);
			pmhomepage.enter_Receiptnumber(tabledata.get("Receiptnumber"));
			
			//pmhomepage.enter_Receiptnumber("610990638");
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered receipt number successfully");
			
			 String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.INFO, "Image",image1);
			
			pmhomepage.clk_Find_btn();
			logger.log(LogStatus.INFO, "Clicked on Find button successfully");
			Thread.sleep(3000);
			
			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "cust dashboard is displayed",image);


		/*	Thread.sleep(3000);
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

			trans.click_dropdown();
			logger.log(LogStatus.INFO, "Clicked on dropdown successfully");
			Thread.sleep(1000);

			trans.click_option_dropdown();
			logger.log(LogStatus.INFO, "Selected option from the dropdown successfully");
			Thread.sleep(1000);

			trans.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on submit button");
			Thread.sleep(5000);*/

			//verify the page elements in the merchandise return screen
			MerchandiseReturnPage merchreturn=new MerchandiseReturnPage(driver);

			if(merchreturn.verify_pagetitle()){
				
				  scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					 image1=logger.addScreenCapture(scpath1);
					logger.log(LogStatus.INFO, "Image",image1);
				logger.log(LogStatus.PASS, " merchandise return page is displayed ");
			}
			else{
				
				  scpath1=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1,true);
					 image1=logger.addScreenCapture(scpath1);
					logger.log(LogStatus.INFO, "Image",image1);
				logger.log(LogStatus.FAIL, "merchandise return page is not displayed  ");
			}

			//verify script and customer screen tabs are available
			if(merchreturn.verify_Script() && merchreturn.verify_customerscreen()){

				 scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				 image1=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.PASS, "The Script and customer screen tabs are displayed in the merchandise screen ");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are displayed in the merchandise screen",image1);
			}
			else{
				  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				  image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.FAIL, "The Script and customer screen tabs are  not displayed in the merchandise screen");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are  not displayed in the merchandise screen",image1);
			}
			//select reason for return
			//select reason for return
			merchreturn.click_dropdown();
			Thread.sleep(1000);
			merchreturn.click_option_Other();
			logger.log(LogStatus.INFO, "Selected reason for return");
			
			logger.log(LogStatus.INFO, "Verifying all the line items for the products");
			
			String productname=merchreturn.verify_Productname();
			logger.log(LogStatus.INFO, "The product name is :"+productname);
			
			boolean ImageCount=merchreturn.verify_ImageCount();
			logger.log(LogStatus.INFO, "The Image count is :"+ImageCount);
			
			String price=merchreturn.verify_Price();
			logger.log(LogStatus.INFO, "The price is :"+price);
			
			String quantity=merchreturn.verify_quantity();
			logger.log(LogStatus.INFO, "The quantity is :"+quantity);
			
			String purchasesubtotal= merchreturn.verify_purchaseSubtotal();
			logger.log(LogStatus.INFO, "The purchase subtotal is :"+purchasesubtotal);
			
			String returnQuantity= merchreturn.verify_returnQuantity();
			logger.log(LogStatus.INFO, "The return quantity  is :"+returnQuantity);
			
			boolean adust_AddBtn=merchreturn.verify_Adjust_addBtn();
			logger.log(LogStatus.INFO, "The Add adjust btn is available  :"+adust_AddBtn);
			
			boolean adjustSub_Btn=merchreturn.verify_Adjust_subBtn();
			logger.log(LogStatus.INFO, "The remove adjust btn is available :"+adjustSub_Btn);
			
		/*	String quant=driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[5]")).getText();
			int quantity=Integer.parseInt(quant);
			System.out.println(quantity);
			for(int i=0;i<=quantity;i++){
				merchreturn.click_addQuantity();
			}*/
			
			//screenshot
			 scpath1=Generic_Class.takeScreenShotPath();
			 image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Quantity added successfully",image1);
			

			
			//add return quantity
			merchreturn.click_addQuantity();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "selected required return quantity");
			
			//JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,250)", "");

			merchreturn.click_calculatebtn();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "After selecting the quantity clicked on calculate btn to get total");

			String ActretSubtotal1=merchreturn.get_returnSub_AftCalculate().replace("$", "");
			String ActretSubtotal="$"+ActretSubtotal1;
			logger.log(LogStatus.INFO, "ActualSubtotal:"+ActretSubtotal);
			String returnQuantityAft_Calculate=merchreturn.get_retQuantity_After_Calculate();
			//double ExpRetSubtotal=	(Double.parseDouble(ActretSubtotal1))*(Integer.parseInt(returnQuantityAft_Calculate));
			double ExpRetSubtotal=	(Double.parseDouble(ActretSubtotal1));
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
			
			double salestax_DB=Math.ceil(salestaxAmount);
			System.out.println(salestax_DB);
			String saletax=""+salestax_DB;
			
			if(saletax.equals(salesTax)){
				 scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				 image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are same",image1);
			}
			else{
				  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				 image1=logger.addScreenCapture(scpath1);
				resultFlag="fail";
				logger.log(LogStatus.FAIL, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are not same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salesTax+" and DB : "+saletax+" are not same",image1);
			}
				
			
			
			//==========================================================

			//String salesTax=merchreturn.get_salesTaxReturn().replace("$", "");

			
			String ActreturnTotal1=merchreturn.get_totalReturn().replace("$", "");
			String ActreturnTotal=("$"+ActreturnTotal1).substring(0, 3);
			logger.log(LogStatus.INFO, "Actual return total:"+ActreturnTotal);
			double expRetTotal=(Double.parseDouble(salesTax))+(Double.parseDouble(ActretSubtotal1));
			String expTotalret=("$"+expRetTotal).substring(0, 3);;
			System.out.println(expTotalret);
			logger.log(LogStatus.INFO, "expreturntotal:"+expTotalret);
			if(ActreturnTotal.contains(expTotalret)){
				logger.log(LogStatus.PASS, "Actual:"+ActreturnTotal+ " and Expected values "+expTotalret+  "are matching");
			}
			else{
				logger.log(LogStatus.FAIL, "Actual:"+ActreturnTotal+ " and Expected values "+expTotalret+ " are not matching");
			}

			try{
			/*	WebElement alert=driver.findElement(By.xpath("//a[contains(text(),'OK')]"));
				if(alert.isDisplayed()){*/
					driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				
		}catch(Exception e)
         {
			
		}
			
			
			
						
		//click on confirm with customer
		jse.executeScript("window.scrollBy(0,3000)", "");
		Thread.sleep(5000);
		//screenshot
		String scpath11=Generic_Class.takeScreenShotPath();
		String image11=logger.addScreenCapture(scpath11);
		logger.log(LogStatus.PASS, "Quantity added successfully",image11);
		
		
		merchreturn.click_ConfWithCust();
		logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
		Thread.sleep(6000);

	
        
      //switching to customer screen
		 driver.switchTo().window(tabs.get(1));
		 logger.log(LogStatus.PASS,"Switching  to customer  screen");
		 Reporter.log("Switching  to customer screen",true);
	     Thread.sleep(10000);
	     
	     WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-container']/canvas[@class='signature-pad']"));
         
		Actions actionBuilder1 = new Actions(driver);
		Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
							.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
		drawAction1.perform();
		Thread.sleep(6000);

		jse.executeScript("window.scrollBy(0,1500)", "");
		 driver.findElement(By.xpath("//button[text()='Accept']")).click();
		 logger.log(LogStatus.PASS,"Clicked on Accept btn in customer facing screen");
		 Thread.sleep(3000);
		 
	    // switched back to Main screen
		driver.switchTo().window(tabs.get(0));
       logger.log(LogStatus.PASS,"Switching back to PM screen");
       Reporter.log("Switching back to PM screen",true);
        
       jse.executeScript("window.scrollBy(0,1500)", "");
        merchreturn.click_Approve();
        logger.log(LogStatus.INFO,"Clicked on approve btn successfully");
        
        merchreturn.click_Submit();
        logger.log(LogStatus.INFO,"Clicked on Submit btn successfully");
    
        //enter emp id and click ok
        driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
        driver.findElement(By.partialLinkText("Ok")).click();
        logger.log(LogStatus.INFO,"Entered empid and clicked on ok btn successfully");

        Thread.sleep(4000);
        
        try{
        	if(driver.findElement(By.xpath("//span[contains(text(),'Error')]")).isDisplayed()){
             scpath1=Generic_Class.takeScreenShotPath();
     		Reporter.log(scpath1,true);
     		 image1=logger.addScreenCapture(scpath1);
     		 logger.log(LogStatus.FAIL, "payment failure error msg is displayed,payment failed");
     		logger.log(LogStatus.INFO, "payment failure error msg is displayed,payment failed",image1);}
            }catch(Exception ex){}
        
        
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
		
		Cust_AccDetailsPage cust=new Cust_AccDetailsPage(driver);
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
		
		
		
		

	} catch (Exception ex) {
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
			Excel.setCellValBasedOnTcname(path, "Merchandise","ReturnMerchandise_PartialReturnOfItems_CreditCard" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Merchandise","ReturnMerchandise_PartialReturnOfItems_CreditCard" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Merchandise","ReturnMerchandise_PartialReturnOfItems_CreditCard" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
