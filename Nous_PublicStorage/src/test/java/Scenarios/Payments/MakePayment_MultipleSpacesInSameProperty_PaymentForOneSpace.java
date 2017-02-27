package Scenarios.Payments;

import java.util.ArrayList;
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

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.SelectSpacesPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace extends Browser_Factory{
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments","MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace");
	}

	@Test(dataProvider="getCustSearchData")
	public void MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("MakePayment_OneSpaceInAnotherProperty_OneSpaceInSameProperty", "Make a payment for a customer account with space in another property + one space in your property ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
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

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
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

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.navigate().refresh();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Entered into advance search page");

			/*String query="Select top 1 A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, "
			   		+" SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate, "
			   		+" sum(clt.amount + clt.discountamount) "
			   		+" From AccountOrderItem AOI "
			   		+" INNER JOIN Site S ON S.SiteID = AOI.SiteID "
			   		+" INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID"
			   		+" INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID"
			   		+" INNER JOIN Account A ON A.AccountID = AO.AccountID"
			   		+" join rentalunit ru on ru.rentalunitid=soi.rentalunitid"
			   		+" join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid"
			   		+" Where soi.VacateDate is null"
			   		+" and soi.StorageOrderItemTypeID=4301"
			   		+" and s.sitenumber='24510'"
			   		+" and soi.vacatenoticedate is null"
			   		+" group by  A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate"
			   		+" having sum(clt.amount + clt.discountamount)=0 "
			   		+" order by 1";

			   ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(query);

			   String accnum=list.get(1);
			   System.out.println(accnum);*/



			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);
			//advSearch.enterAccNum(accnum);
			advSearch.enterAccNum(tabledata.get("Account Number"));
			logger.log(LogStatus.INFO, "Entered Account number");

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,-2000)", "");
			Thread.sleep(5000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");

			Thread.sleep(10000);

			//verify navigated to customer dashboard
			Cust_AccDetailsPage custDetails = new Cust_AccDetailsPage(driver);
			Thread.sleep(10000);
			custDetails.Verify_CustDashboard();
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to customer dashboard");
			logger.log(LogStatus.INFO, "Navigated to customer dashboard",image);

			//click on space details tab
			custDetails.clickSpaceDetails_tab();
			Thread.sleep(5000);

			jse.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(5000);

			//verify spaces
			List<WebElement> spacelist=driver.findElements(By.xpath("//div[@id='space-details-grid']//table/tbody/tr/td//span[@class='space-rentalunitnumber']"));
			Reporter.log("Spaces available is:"+spacelist.size(),true);
			for(int i=0;i<spacelist.size();i++){
				String space=spacelist.get(i).getText();
				System.out.println(space);
				//Reporter.log("the space list:"+space);
				//i++;
			}
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Space list is displayed");
			logger.log(LogStatus.INFO, "Space list is displayed",image1);

			Thread.sleep(5000);

			jse.executeScript("window.scrollBy(0,-2000)", "");
			Thread.sleep(5000);



			//Verify amounts and dates under Total Due Now and Next Payment Due section on customer Dashboard
			String totalDue=custDetails.txt_TotalDueNowAmt();
			String NextDue=custDetails.txt_NextpaymentDueAmt();
			if(custDetails.verify_TotalDueNowAmt() && custDetails.verify_NextpaymentDueDate() && custDetails.verify_NextpaymentDueAmt()){
				//String totalDue=custDetails.txt_TotalDueNowAmt();
				Reporter.log("Total Due Now is:"+totalDue,true);
				logger.log(LogStatus.INFO, "Total Due Now is:"+totalDue);


				String NextDueDate=custDetails.txt_NextpaymentDueDate();
				Reporter.log("Next payment due date is:"+NextDueDate,true);
				logger.log(LogStatus.INFO, "Next payment due date is:"+NextDueDate);

				//String NextDue=custDetails.txt_NextpaymentDueAmt();
				Reporter.log("Next payment due is:"+NextDue,true);
				logger.log(LogStatus.INFO, "Next payment due is:"+NextDue);
			}


			else{
				logger.log(LogStatus.FAIL, "Total payment due,Next payment due,due dates not displayed");
				logger.log(LogStatus.INFO, "Total payment due,Next payment due,due dates not displayed");

			}
			String scpath2=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath2,true);
			String image2=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Desired data is displayed");
			logger.log(LogStatus.INFO, "Desired data is displayed",image2);

			Thread.sleep(6000);

			//click make payment option
			custDetails.clickMakePayment_Btn();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on makepayment link");

			//verify Select Multiple Spaces screen should be displayed 
			SelectSpacesPage sel= new SelectSpacesPage(driver);
			if(sel.verify_PageTitle()){

				String getTitle=sel.txt_PageTitle();
				logger.log(LogStatus.INFO, "page title is:"+getTitle);
				String scpath3=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2,true);
				String image3=logger.addScreenCapture(scpath3);
				logger.log(LogStatus.PASS, "Select space is  displayed");
				logger.log(LogStatus.INFO, "Select space is displayed",image3);
			}
			else{
				logger.log(LogStatus.FAIL, "Select space is not displayed");
				logger.log(LogStatus.INFO, "Select space is not displayed");

			}

			//Verify the list of spaces available in the make payment page
			List<WebElement> spacelist1=driver.findElements(By.xpath("//div[@class='js-unit unitDiv row-group']/section[@class='unit-subtotal alt row']/div[@class='first col']"));
			for(int i=0;i<spacelist1.size();i++){
				String spa=spacelist1.get(i).getText();
				String spaces=spa.replace(" Subtotal", "");
				//System.out.println(spaces);
				Reporter.log("Spaces list is:"+spaces,true);
				logger.log(LogStatus.INFO, "Spaces list is:"+spaces);
			}

			//verify  all the check boxes are there
			List<WebElement> chkbox=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='button']"));
			for(WebElement cb:chkbox){
				boolean chked=cb.isEnabled();
				System.out.println(chked);
				if(chked){
					String scpath3=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath2,true);
					String image3=logger.addScreenCapture(scpath3);
					logger.log(LogStatus.PASS, "Checkboxes are selected by default");
					logger.log(LogStatus.INFO, "Checkboxes are selected by default",image3);
				}
				else{
					logger.log(LogStatus.FAIL, "Checkboxes are not selected by default");
					logger.log(LogStatus.INFO, "Checkboxes are not selected by default");
				}
			}

			//Clear select all checkbox

			sel.click_SelectAll();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Select all check box is selected");
			String scpath3=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath2,true);
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.PASS, "Checkboxes are cleared by default");
			logger.log(LogStatus.INFO, "Checkboxes are cleared by default",image3);

			//verify all the details in the page
			List<WebElement> LocNum=driver.findElements(By.xpath("//form[@id='select-spaces-form']//a[@class='location-details']"));
			for(WebElement Location:LocNum){
				String LocationNumbers=Location.getText();
				logger.log(LogStatus.INFO, "Location num list is:"+LocationNumbers);
			}

			List<WebElement> spaceDetail=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='js-space-identifier']"));
			for(WebElement sp:spaceDetail){
				String spaceDetails=sp.getText();
				logger.log(LogStatus.INFO, "Space details is:"+spaceDetails);
			}	


			/*	Thread.sleep(1000);
			try{
			sel.verify_PastDue();
			logger.log(LogStatus.INFO, "past due text is displayed");}
			catch{

			}*/

			//verify monthly due dates
			List<WebElement> duenow=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='due-now-date']"));
			for(WebElement dueDate:duenow){
				String dueNowDate=dueDate.getText();
				logger.log(LogStatus.INFO, "monthly due dates is:"+dueNowDate);
			}	

			//verify next payment dues
			List<WebElement> dueNext=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='displayRentDueDate js-rent-due-date']"));
			for(WebElement nextDue:dueNext){
				String nextDueDate=nextDue.getText();
				logger.log(LogStatus.INFO, "Next due dates is:"+nextDueDate);
			}

			//total due now for all the spaces
			List<WebElement> dueAmt=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='js-unit-rent']"));
			for(WebElement duerent:dueAmt){
				String monthlyDueNowAmt=duerent.getText();
				logger.log(LogStatus.INFO, "Monthly due amount:"+monthlyDueNowAmt);
			}

			//verifying subtotals of monthly rent
			List<WebElement> dueAmtSub=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='js-unit-subtotal']"));
			for(WebElement subtotal:dueAmtSub)	{
				String monthlyDueNowAmtSubtotal=subtotal.getText();
				logger.log(LogStatus.INFO, "Subtotal due now is:"+monthlyDueNowAmtSubtotal);
			}


			//verifying Next due Amounts
			List<WebElement> dueAmtNext=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='js-next-unit-rent']"));
			for(WebElement nextDueAmt:dueAmtNext){
				String monthlynextDueAmt=nextDueAmt.getText();
				logger.log(LogStatus.INFO, "Next payment due:"+monthlynextDueAmt);
			}

			//verifying Next due subtotal Amounts
			List<WebElement> dueAmtNextSub=driver.findElements(By.xpath("//form[@id='select-spaces-form']//span[@class='js-next-unit-subtotal']"));
			for(WebElement nextDueSubtotal:dueAmtNextSub){
				String monthlynextDueSubtotal=nextDueSubtotal.getText();
				logger.log(LogStatus.INFO, "Next payment subtotal amount :"+monthlynextDueSubtotal);
			}

			//verify Insurance coverage,if so verify the due amounts
			if(sel.verify_monthlyInsurDue() && sel.verify_monthlyInsureNextDue()){
				String InsureDue=sel.txt_monthlyInsurDue();
				String insureNxtDue= sel.txt_monthlyInsurNextDue();
				logger.log(LogStatus.PASS, "Insurance dues are :"+InsureDue+","+insureNxtDue);
				logger.log(LogStatus.INFO, "Insurance dues are :"+InsureDue+","+insureNxtDue);
			}

			else{
				logger.log(LogStatus.FAIL, "Insurance coverage is not active");
				logger.log(LogStatus.INFO, "Insurance coverage is not active");
			}

			Thread.sleep(10000);
			//verify total due and next due in customer dashboard and select spaces page
			String totalDueNow=sel.txt_TotalDueNow();
			String totalNextDue=sel.txt_TotalNextDue();
			Thread.sleep(10000);

			//verify dashboard and space page amount
			if((totalDueNow.equals(totalDue)) && totalNextDue.equals(NextDue)){
				logger.log(LogStatus.PASS, "Total and next payment due details are matched in Dashboard and space page");
				logger.log(LogStatus.INFO, "Total and next payment due details are matched in Dashboard and space page");
			}

			else{
				logger.log(LogStatus.FAIL, "Total and next payment due details are not matched in Dashboard and space page");
				logger.log(LogStatus.INFO, "Total and next payment due details are not matched in Dashboard and space page");
			}

			//verify subtotals and total due amounts

			//=========total due=adding all subtotal amounts
			for(int i=0;i<dueAmtSub.size();i++){					
				String space1=dueAmtSub.get(0).getText().replace("$", "");
				String space2=dueAmtSub.get(1).getText().replace("$", "");
				String space3=dueAmtSub.get(2).getText().replace("$", "");

				double 	totaldueSum = (Double.parseDouble(space1))+(Double.parseDouble(space2))+(Double.parseDouble(space3));
				System.out.println(totaldueSum);
				String totaldueSumVal="$"+totaldueSum;
				//String totalDueValue= Double.toString(totaldueSum).concat("$");

				if((totalDue).equals(totaldueSum)){
					logger.log(LogStatus.PASS, "Total due details are matched in the selected space page");
					logger.log(LogStatus.INFO, "Total  due details are matched in the selected space page");
				}

				else{
					logger.log(LogStatus.FAIL, "Total  due details are not matchein the selected space page");
					logger.log(LogStatus.INFO, "Total  due details are not matched in the selected space page");
				}

			}

			//==========total next due= adding all next due amounts

			for(int i=0;i<dueAmtNext.size();i++){					
				String space1=dueAmtNext.get(0).getText().replace("$", "");
				String space2=dueAmtNext.get(1).getText().replace("$", "");
				String space3=dueAmtNext.get(2).getText().replace("$", "");

				double 	totalnextDueSum = (Double.parseDouble(space1))+(Double.parseDouble(space2))+(Double.parseDouble(space3));
				System.out.println(totalnextDueSum);
				String totalnextDueSumVal="$"+totalnextDueSum;
				//String totalDueNextValue= Double.toString(totalnextDueSum).concat("$");
				if((totalNextDue).equals(totalnextDueSum)){
					logger.log(LogStatus.PASS, "Next payment due details are matched in the selected space page");
					logger.log(LogStatus.INFO, "Next payment due details are matched in the selected space page");
				}

				else{
					logger.log(LogStatus.FAIL, "Next payment due details are not matchein the selected space page");
					logger.log(LogStatus.INFO, "Next payment due details are not matched in the selected space page");
				}
			}


			//verify continue btn and if there click without selecting any space
			if(sel.verify_continue_Btn()){
				sel.click_continue_Btn();
				logger.log(LogStatus.INFO, "Clicked on continue btn");
			}

			//verify the error message "atleast one space should be selected"
			boolean  errMsg=driver.getPageSource().equals("At least one space must be selected ");
			if(errMsg){
				String scpath4=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2,true);
				String image4=logger.addScreenCapture(scpath4);
				logger.log(LogStatus.PASS, "Error message is displayed");
				logger.log(LogStatus.INFO, "Error message is displayed",image4);
			}

			else{
				logger.log(LogStatus.FAIL, "Error message is  not displayed");
				logger.log(LogStatus.INFO, "Error message is  not displayed");
			}


			//select any space and click on continue
			sel.click_Chbx1();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected  spaces");

			sel.click_continue_Btn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on continue btn successfully");

			//verify payment screen
			PaymentsPage payments= new PaymentsPage(driver);
			if(payments.verify_PageTitle()){
				String scpath4=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2,true);
				String image4=logger.addScreenCapture(scpath4);
				logger.log(LogStatus.PASS, "payment page is displayed");
				logger.log(LogStatus.INFO, "payment page is displayed",image4);
			}

			else{
				logger.log(LogStatus.FAIL, "payment page is  not displayed");
				logger.log(LogStatus.INFO, "payment page is  not displayed");
			}

			//verifying spaces in payments screen
			String location=payments.txt_location();
			logger.log(LogStatus.INFO, "Location num  is:"+location);


			//verifying locdetails in payments screen
			String spacedetails=payments.txt_spacedetails();
			logger.log(LogStatus.INFO, "space details list is:"+spacedetails);


			//verifying next payment due dates in  in payments screen
			String nextDuedate=payments.txt_nextDueDate();
			logger.log(LogStatus.INFO, "Next due dates is:"+nextDuedate);


			//verifying Monthly rent due amounts in  in payments screen
			String totaldueamt=payments.txt_totalDuenow();
			logger.log(LogStatus.INFO, "Monthly due now amounts:"+totaldueamt);


			//verifying Next payment due amounts due amounts in  in payments screen
			String nextDueAmt=payments.txt_nextDueamt();
			logger.log(LogStatus.INFO, "Next payment due amounts:"+nextDueAmt);



			//verifying Monthly rent due amount  subtotals in  in payments screen
			String totaldue_subtotal=payments.txt_totalDuenow_sub();
			logger.log(LogStatus.INFO, "Monthly due now amounts-subtotal:"+totaldue_subtotal);


			//verifying Next payment due amounts due amount subtotals in  in payments screen
			String nextDue_Subtotal=payments.txt_nextDueamt_sub();
			logger.log(LogStatus.INFO, "Next payment due amounts-subtotals:"+nextDue_Subtotal);



			//verify the total amount
			String totalAmount= payments.getTotalDueNowInPaymentSection();
			logger.log(LogStatus.INFO, "Total Amount is:"+totalAmount);

			//verify the total amount in the dashboard and payment screen
			if(totalAmount.equals(totalDue)){
				logger.log(LogStatus.PASS,"Dashboard and payments total amounts are matching");
			}
			else{
				logger.log(LogStatus.FAIL,"Dashboard and payments total amounts are not matching");
			}

			payments.selectPaymentThrough();
			Thread.sleep(10000);

			String subtotaldue= driver.findElement(By.xpath("//div[@id='payment-form']//span[@class='js-unit-subtotal']")).getText().replace("$", "");
			String nextpaymentdue=driver.findElement(By.xpath("//div[@id='payment-form']//span[@class='js-next-unit-subtotal']")).getText().replace("$", "");
			Double ActualgetdueNow=(Double.parseDouble(subtotaldue))+Double.parseDouble(nextpaymentdue);
			logger.log(LogStatus.INFO, "Total due is:" +ActualgetdueNow);
			
			Thread.sleep(10000);
			//click on confirm with customer
			payments.clickOnConfirmWithCustomer_Btn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on confirm with customer");

			driver.switchTo().window(tabs.get(1));
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "Navigated to customer screen");
			Thread.sleep(5000);

			WebElement confirmBtn1=driver.findElement(By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']"));
			confirmBtn1.click();
			logger.log(LogStatus.PASS,"Clicked on confirm  button");
			Reporter.log("Clicked on confirm button",true);
			Thread.sleep(10000);


			//switching back to wc2 tab
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Switching back to PM  screen");
			Reporter.log("Switching back to PM  screen",true);

			//click on payment dropdown
			payments.Select_dropdown();
			Thread.sleep(10000);

			Thread.sleep(3000);
			payments.selectPaymentMethod("Check", driver);
			logger.log(LogStatus.INFO, "Select the Check option from Payment dropdown successfully");

			Thread.sleep(6000);
			payments.clickmanualentry();
			logger.log(LogStatus.INFO, "Clicking on Manual entry button successfully");

			payments.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			logger.log(LogStatus.INFO, "Entering routing Number successfully");

			payments.Enter_accountNumber(tabledata.get("CheckAccNum"));
			logger.log(LogStatus.INFO, "Entering Account Number successfully");

			payments.Enter_checkNumber(tabledata.get("CheckNum"));
			logger.log(LogStatus.INFO, "Entering Check Number successfully");

			payments.Enter_checkAmount(tabledata.get("CheckAmount"));
			logger.log(LogStatus.INFO, "Entering Check amount successfully");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(6000);
			payments.Select_autopaycheckbox();
			logger.log(LogStatus.INFO, "Select Auto pay enable checkbox successfully");

			Thread.sleep(6000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			Thread.sleep(6000);
			payments.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect signature button successfully");

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			payments.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Main page successfully");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payments.clickApprove_Btn();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");

			Thread.sleep(6000);
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Click on Submit button successfully");

			Thread.sleep(5000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);

			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter Employee Id  successfully");

			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.INFO, "Click on Ok button successfully");


			Thread.sleep(10000);
			String totalDue1=custDetails.txt_TotalDueNowAmt();
			Reporter.log("Total Due Now is:"+totalDue1,true);
			logger.log(LogStatus.INFO, "Total Due Now is:"+totalDue1);


			String NextDueDatepresent=custDetails.txt_NextpaymentDueDate();
			Reporter.log("Next payment due date is:"+NextDueDatepresent,true);
			logger.log(LogStatus.INFO, "Next payment due date is:"+NextDueDatepresent);

			String NextDuePresent=custDetails.txt_NextpaymentDueAmt();
			Reporter.log("Next payment due is:"+NextDuePresent,true);
			logger.log(LogStatus.INFO, "Next payment due is:"+NextDuePresent);





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
			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","MakePayment_MultipleSpacesInSameProperty_PaymentForOneSpace" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}