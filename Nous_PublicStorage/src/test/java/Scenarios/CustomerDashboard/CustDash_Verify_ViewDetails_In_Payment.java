package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

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
import com.sun.org.apache.xpath.internal.operations.Bool;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Scenarios.Browser_Factory;

public class CustDash_Verify_ViewDetails_In_Payment extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "CustDash_Verify_ViewDetails_In_Payment");
	}

	@Test(dataProvider="getLoginData")
	public void CustDash_Verify_ViewDetails_In_Payment(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("CustDash_Verify_ViewDetails_In_Payment","Customer DashBoard - Verify View Details In Payment");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum = Bifrostpop.getBiforstNo();
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);			
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
					
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page Sucessfully");
			Thread.sleep(3000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			
			//Verifying PM Dash Board is displayed
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase(tabledata.get("walkInCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User Lands in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "User Lands in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

			}
			
			if (pmhomepage.get_existingCustomerText().trim().equalsIgnoreCase(tabledata.get("ExistingCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Existing Customer is displaying in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "Existing Customer is displaying in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer is not displayed");
				logger.log(LogStatus.INFO, "Existing Customer is not displayed", image);

			}
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search link in PM home page");
			
			
			String qry	="Select Top 1 A.AccountNumber"
                    +" FROM AccountOrderItem AOI with(nolock)"
                    +" Join AccountOrder AO with(nolock) on AO.AccountOrderID = AOI.AccountOrderID"
                    +" Join Account A with(nolock) on A.AccountID = AO.AccountID"
                    +" Join Customer C with(nolock) on C.CustomerID = A.CustomerID" 
                    +" join site s with(nolock) on s.siteid=aoi.siteid"
                    +" join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID"
                    +" join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid"
                    +" join Type T with(nolock) on T.TypeID = C.CustomerTypeID"
                    +" join Type T1 with(nolock) on T1.TypeID = SOI.paymentmethodtypeid"
                    +" join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid"
                    +" where 1 = 1"
                    +" and s.sitenumber='"+SiteNumber+"'"
                    +" and soi.VacateDate is null and c.customertypeid= 90"
                    +" group by a.accountnumber" 
                    +" having sum(clt.amount+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1";

			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
			Thread.sleep(3000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for advance Search page");
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===============================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			logger.log(LogStatus.PASS, "Created object for Customer dash board page");
			
			if(cust_accdetails.isCustdbTitleDisplayed()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed");
				logger.log(LogStatus.INFO, "Customer Dashboard is not displayed",image);
			}
			Thread.sleep(3000);
			
			//Verify the Location number of rental space
			if(cust_accdetails.verify_Spacenum()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Location Number of rental space is displayed successfully : "+SiteNumber);
				logger.log(LogStatus.INFO, "Location Number of rental space is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Location Number of rental space is not displayed");
				logger.log(LogStatus.INFO, "Location Number of rental space is not displayed",image);
			}
			
			Thread.sleep(2000);
			if(cust_accdetails.verify_CustSpaceNum()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Rental space is displayed successfully : "+cust_accdetails.getCustSpaceNum());
				logger.log(LogStatus.INFO, "Rental space is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Rental space is not displayed");
				logger.log(LogStatus.INFO, "Rental space is not displayed",image);
			}
			
			//Verify Total Due Column
			if(cust_accdetails.verifyTotalDueSection()){

				logger.log(LogStatus.PASS, "Total Due Section is displayed successfully");
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Rental space is not displayed");
				logger.log(LogStatus.INFO, "Rental space is not displayed",image);
			}
			
			//Verify Next Payment Due Column
			if(cust_accdetails.verify_NextPaymentDueColumn()){

				logger.log(LogStatus.PASS, "Next Payment Due Section is displayed successfully");
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Rental space is not displayed");
				logger.log(LogStatus.INFO, "Rental space is not displayed",image);
			}
			Thread.sleep(2000);
			
			//Verify Make Payment link
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			if(cust_accdetails.verify_MakePaymentLnk()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Make Payment Link is displayed successfully");
				logger.log(LogStatus.INFO, "Make Payment Link is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Make Payment Link is not displayed");
				logger.log(LogStatus.INFO, "Make Payment Link is not displayed",image);
			}
			
		
			//Clicking on Make payment Link 
			cust_accdetails.clickMakePayment_Btn();
			Thread.sleep(10000);
			Boolean payment1 = driver.findElement(By.xpath("//h3[contains(text(),'Payment')]")).isDisplayed();
			if(payment1){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Make Payment Page is displayed successfully");
				logger.log(LogStatus.INFO, "Make Payment Page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Make Payment Page is not displayed");
				logger.log(LogStatus.INFO, "Make Payment Page is not displayed",image);
			}	
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			driver.findElement(By.xpath("//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Cancel button in Make Payment Page");
			
			//Verify view details link
			if(cust_accdetails.verifyViewDetailsLink()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "View Details Link is displayed successfully");
				logger.log(LogStatus.INFO, "View Details Link is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "View details link is not displayed");
				logger.log(LogStatus.INFO, "View details link is not displayed",image);
			}
			
			cust_accdetails.clickViewDetails();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on View Details Link Successfully");
			
			
			/*logger.log(LogStatus.INFO, "==== Validating inline units ==");
			
			PaymentsPage payment = new PaymentsPage(driver);
			

		String requery="Select GL.Name"    
                          +" From DBO.CLTransactionMaster CLM WITH (NOLOCK)"
                          +" INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID"    
                          +" INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID"   
                          +" INNER JOIN DBO.GLAccount GL WITH(NOLOCK) on clt.GLAccountID=gl.GLAccountID"    
                          +" join account a on a.accountid=clm.accountid"     
                          +" Where 1 = 1"     
                          +" And a.Accountnumber ='"+AccountNumber+"'"     
                          +" group by clt.GLAccountID,GL.Name";

          ArrayList<String> preledgerName = DataBase_JDBC.executeSQLQuery_List(requery);

          String requery1="Select GL.Name,SUM((Amount * Quantity) + DiscountAmount) as Amount"    
                          +" From DBO.CLTransactionMaster CLM WITH (NOLOCK)"
                          +" INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID"    
                          +" INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID"   
                          +" INNER JOIN DBO.GLAccount GL WITH(NOLOCK) on clt.GLAccountID=gl.GLAccountID"    
                          +" join account a on a.accountid=clm.accountid"     
                          +" Where 1 = 1"     
                          +" And a.Accountnumber ='"+AccountNumber+"'"     
                          +" group by clt.GLAccountID,GL.Name";

             ArrayList<String> preledgerValue = DataBase_JDBC.executeSQLQuery_List_SecondCol(requery1);

             HashMap<String,String> ledgerItems= new HashMap();

             for (int i=0; i<preledgerName.size(); i++)
             {
                   ledgerItems.put(preledgerName.get(i), preledgerValue.get(i).substring(0, preledgerValue.get(i).length()-2));
             }



             Thread.sleep(3000);
             logger.log(LogStatus.PASS, " ***********************Pre Payment General Ledger Validation in Payment Page****************");


             //Monthly rent Validation
             if(ledgerItems.get("Rent").equalsIgnoreCase(payment.getMonthlyRent().substring(1))){
                   logger.log(LogStatus.PASS, "Monthly rent in DB  : " + ledgerItems.get("Rent") +" and  Monthly rent in payment page : "+ payment.getMonthlyRent().substring(1) +" are same ");

             }else{
                   logger.log(LogStatus.FAIL, "Monthly rent in DB  : " + ledgerItems.get("Rent") +" and  Monthly rent in payment page : "+ payment.getMonthlyRent().substring(1) +" are not same ");
             }

             //Insurance  Validation

             if(driver.findElements(By.xpath("//div[@id='payment-table']//section[@class='insurance row']")).size()!=0)
             {
                    if(ledgerItems.get("Insurance").equalsIgnoreCase(payment.getInsuranceAmount().substring(1))){
                          logger.log(LogStatus.PASS, " insurance Duenow in DB : " + ledgerItems.get("Insurance")+" and  insurance Duenow payment page : "+ payment.getInsuranceAmount().substring(1)+" are same ");

                   }else{
                          logger.log(LogStatus.FAIL,  " insurance Duenow in DB : " + ledgerItems.get("Insurance")+" and  insurance Duenow payment page : "+ payment.getInsuranceAmount().substring(1)+" are not same ");
                   }
             }else{
                   logger.log(LogStatus.INFO, "Insurance is not applied ");
             }
             //Late Fee validation

             if(driver.findElements(By.xpath("//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Late Fees')]")).size()!=0)
             {
                   String lateFee= driver.findElement(By.xpath("//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Late Fees')]/following-sibling::div/span[@class='js-unit-fee']")).getText().substring(1);
                   if(ledgerItems.get("Late Fees").equalsIgnoreCase(lateFee)){
                          logger.log(LogStatus.PASS, " Late Fees in DB : " + ledgerItems.get("Late Fees")+" and  Late Fees payment page : "+ lateFee+" are same ");

                   }else{
                          logger.log(LogStatus.FAIL,  " Late Fees in DB : " + ledgerItems.get("Late Fees")+" and  Late Fees payment page : "+ lateFee+" are not same ");
                   }
             }


             //Lien Fee Validation

             if(driver.findElements(By.xpath("(//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Lien Fee')])[1]")).size()!=0)
             {
                   String lienFee= driver.findElement(By.xpath("(//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Lien Fee')])[1]/following-sibling::div/span[@class='js-unit-fee']")).getText().substring(1);
                   if(ledgerItems.get("Lien Fee").equalsIgnoreCase(lienFee)){
                          logger.log(LogStatus.PASS, " Lien Fee in DB : " + ledgerItems.get("Lien Fee")+" and  Lien Fee payment page : "+ lienFee+" are same ");

                   }else{
                          logger.log(LogStatus.FAIL,  " Lien Fee in DB : " + ledgerItems.get("Lien Fee")+" and  Lien Fee payment page : "+ lienFee+" are not same ");
                   }
             }
             
             //Lien Fee 2 Validation

             if(driver.findElements(By.xpath("(//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Lien Fee')])[2]")).size()!=0)
             {
                   String lienFee2= driver.findElement(By.xpath("(//div[@id='payment-table']//section[@class='fee row']/div[contains(text(),'Lien Fee')])[2]/following-sibling::div/span[@class='js-unit-fee']")).getText().substring(1);
                   if(ledgerItems.get("Lien Fee 2").equalsIgnoreCase(lienFee2)){
                          logger.log(LogStatus.PASS, " Lien Fee 2 in DB : " + ledgerItems.get("Lien Fee 2")+" and  Lien Fee 2 payment page : "+ lienFee2+" are same ");

                   }else{
                          logger.log(LogStatus.FAIL,  " Lien Fee 2 in DB : " + ledgerItems.get("Lien Fee 2")+" and  Lien Fee 2 payment page : "+ lienFee2+" are not same ");
                   }
             }

*/			
			
			
			
			
			Thread.sleep(3000);
            logger.log(LogStatus.PASS, " ***********************Pre Payment General Ledger Validation in View Details Window****************");

           String  requery="Select GL.Name"    
                         +" From DBO.CLTransactionMaster CLM WITH (NOLOCK)"
                         +" INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID"    
                         +" INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID"   
                         +" INNER JOIN DBO.GLAccount GL WITH(NOLOCK) on clt.GLAccountID=gl.GLAccountID"    
                         +" join account a on a.accountid=clm.accountid"     
                         +" Where 1 = 1"     
                         +" And a.Accountnumber ='"+AccountNumber+"'"     
                         +" group by clt.GLAccountID,GL.Name";

            ArrayList<String> preledgerName = DataBase_JDBC.executeSQLQuery_List(requery);

           String requery1="Select GL.Name,SUM((Amount * Quantity) + DiscountAmount) as Amount"    
                         +" From DBO.CLTransactionMaster CLM WITH (NOLOCK)"
                         +" INNER JOIN DBO.Type CT WITH (NOLOCK)ON CT.TypeID = CLM.CLTransactionTypeID"    
                         +" INNER JOIN DBO.CLTransaction CLT WITH (NOLOCK) ON CLT.CLTransactionMasterID  = CLM.CLTransactionMasterID"   
                         +" INNER JOIN DBO.GLAccount GL WITH(NOLOCK) on clt.GLAccountID=gl.GLAccountID"    
                         +" join account a on a.accountid=clm.accountid"     
                         +" Where 1 = 1"     
                         +" And a.Accountnumber ='"+AccountNumber+"'"     
                         +" group by clt.GLAccountID,GL.Name";

            ArrayList<String> preledgerValue = DataBase_JDBC.executeSQLQuery_List_SecondCol(requery1);

            HashMap<String,String> ledgerItems= new HashMap();

            for (int i=0; i<preledgerName.size(); i++)
            {
                  ledgerItems.put(preledgerName.get(i), preledgerValue.get(i).substring(0, preledgerValue.get(i).length()-2));
            }


            //Late Fees
            if(driver.findElements(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Late Fees')]")).size()!=0)
            {
                  String lateFee=driver.findElement(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Late Fees')]/following-sibling::div[1]")).getText().substring(1).trim();

                  if(ledgerItems.get("Late Fees").equals(lateFee))
                  {
                         logger.log(LogStatus.PASS, " Late Fees in DB:"+ ledgerItems.get("Late Fees")+" and Late Fees in Account Activities :"+lateFee+" are Same ");
                  }else{
                         logger.log(LogStatus.FAIL, " Late Fees in DB:"+ ledgerItems.get("Late Fees")+" and Late Fees in Account Activities :"+lateFee+" are not Same ");
                  }
            }

            //Rent

            if(driver.findElements(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Monthly Rent')]")).size()!=0)
            {
                  String rent=driver.findElement(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Monthly Rent')]/following-sibling::div[1]")).getText().substring(1).trim().replace(",", "");

                  if(ledgerItems.get("Rent").equals(rent))
                  {
                         logger.log(LogStatus.PASS, " Rent in DB:"+ ledgerItems.get("Rent")+" and Rent in Account Activities :"+rent+" are Same ");
                  }else{
                         logger.log(LogStatus.FAIL, " Rent in DB:"+ ledgerItems.get("Rent")+" and Rent in Account Activities :"+rent+" are not Same ");
                  }
            }

            //Insurance:

            if(driver.findElements(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Insurance')]")).size()!=0)
            {
            	
            	String insurance_Duenow=driver.findElement(By.xpath("//div[@class='space__payment-details']//div[contains(text(),'Insurance:']/following-sibling::div[1]")).getText().substring(1).trim();
                  //String insurance=driver.findElement(By.xpath("//table[@role='grid']//tbody//tr//td[text()='Insurance:']/following-sibling::td[2]")).getText().substring(1).trim();

                  if(ledgerItems.get("Insurance").equals(insurance_Duenow))
                  {
                         logger.log(LogStatus.PASS, " Insurance in DB:"+ ledgerItems.get("Insurance")+" and Insurance in Account Activities :"+insurance_Duenow+" are Same ");
                  }else{
                         logger.log(LogStatus.FAIL, " Insurance in DB:"+ ledgerItems.get("Insurance")+" and Insurance in Account Activities :"+insurance_Duenow+" are not Same ");
                  }
            }


            //Lien Fees 

            if(driver.findElements(By.xpath("(//div[@class='space__payment-details']//div[contains(@class,'space__payment-details__row__detail')][contains(text(),'Lien Fee')])[1]")).size()!=0)
            {
                  try{
                         String lienFee=driver.findElement(By.xpath("(//div[@class='space__payment-details']//div[contains(@class,'space__payment-details__row__detail')][contains(text(),'Lien Fee')])[1]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")).getText().trim().substring(1);


                         if(ledgerItems.get("Lien Fee").equals(lienFee))
                         {
                                logger.log(LogStatus.PASS, " Lien Fee in DB:"+ ledgerItems.get("Lien Fee")+" and lien Fee in Account Activities :"+lienFee+" are Same ");
                         }else{
                                logger.log(LogStatus.FAIL, " Lien Fee in DB:"+ ledgerItems.get("Lien Fee")+" and lien Fee in Account Activities :"+lienFee+" are not Same ");
                         }
                  }catch(Exception e){

                  }
            }

            //Lien Fees 2

            if(driver.findElements(By.xpath("(//div[@class='space__payment-details']//div[contains(@class,'space__payment-details__row__detail')][contains(text(),'Lien Fee')])[2]")).size()!=0)
            {
                  try{
                         String lienFee2=driver.findElement(By.xpath("(//div[@class='space__payment-details']//div[contains(@class,'space__payment-details__row__detail')][contains(text(),'Lien Fee')])[2]/following-sibling::div[contains(@class,'space__payment-details__row__detail--due-now')]")).getText().substring(1).trim();

                         if(ledgerItems.get("Lien Fee 2").equals(lienFee2))
                         {
                                logger.log(LogStatus.PASS, " Lien Fee 2 in DB:"+ ledgerItems.get("Lien Fee 2")+" and Lien Fee 2 in Account Activities :"+lienFee2+" are Same ");
                         }else{
                                logger.log(LogStatus.FAIL, " Lien Fee 2 in DB:"+ ledgerItems.get("Lien Fee 2")+" and Lien Fee 2 in Account Activities :"+lienFee2+" are not Same ");
                         }
                  }catch(Exception e){

                  }
            }

			
			
			
			
			Boolean paymentPopup = driver.findElement(By.xpath("//span[contains(text(),'Payment Due Details')]")).isDisplayed();
			if(paymentPopup){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment Due Details Popup is displayed successfully");
				logger.log(LogStatus.INFO, "Payment Due Details Popup is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment Due Details Popup is not displayed");
				logger.log(LogStatus.INFO, "Payment Due Details Popup is not displayed",image);
			}
			
			//Clicking on Cancel button
			driver.findElement(By.xpath("//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Cancel button in Payment Due Details Popup");
			
			//Verify Manage AutoPay
			if(cust_accdetails.verifyManageAutopayLink()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Manage Autopay Link is displayed successfully");
				logger.log(LogStatus.INFO, "Manage Autopay Link is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Manage Autopay Link is not displayed");
				logger.log(LogStatus.INFO, "Manage Autopay Link is not displayed",image);
			}
			
			//Clicking Manage AutoPay
			cust_accdetails.clickOnManageAutoPay_Lnk();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on Manage Autopay Link");
			
			Boolean autopay = driver.findElement(By.xpath("//h3[contains(text(),'AutoPay Preferences')]")).isDisplayed();
			if(autopay){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Preferences is displayed successfully");
				logger.log(LogStatus.INFO, "Autopay Preferences is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay Preferences is not displayed");
				logger.log(LogStatus.INFO, "Autopay Preferences is not displayed",image);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ViewDetails_In_Payment" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ViewDetails_In_Payment" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ViewDetails_In_Payment" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}


}
