package Scenarios.CustomerSearch;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
import Pages.AdvSearchPages.AdvSearch_Reservation;
import Pages.AdvSearchPages.ResCustSearchPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class CustomerSearch_ReservationsearchusingName extends Browser_Factory {

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";
	boolean flag;

	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","CustomerSearch_ReservationsearchusingName");
	}

	@Test(dataProvider="getCustomerSearchData")	
	public void CustomerSearch_ReservationsearchusingName(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the CustomerSearch_ReservationsearchusingName test");
		}

		try{

			logger=extent.startTest("CustomerSearch_ReservationsearchusingName", "Reservation search using customer Name and validating cust info with db");
			Thread.sleep(3000);
			LoginPage loginPage = new LoginPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			loginPage.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "entered username sucessfully");
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password sucessfully");
			loginPage.clickLogin();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");


			//=================Handling customer facing device========================
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();
			Thread.sleep(5000);

			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			String location = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "creating object for the PM home page sucessfully");
			if(pmhomepage.isexistingCustomerModelDisplayed()){

				logger.log(LogStatus.PASS, "Existing Customer module is present in the PM DashBoard sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Existing Customer module is not present in the PM DashBoard");
			}

			Thread.sleep(2000);
			if(pmhomepage.get_findACustomerAtThisLocText().contains(tabledata.get("TextEntryField"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a Customer at this Location is displayed successfully");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find a Customer at this Location is not displayed ");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is not displayed ",image);

			}

			Thread.sleep(2000);
			if(pmhomepage.get_findACustomerText().trim().contains(tabledata.get("CustomerButtonName"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully");
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

			}

			logger.log(LogStatus.INFO, "*** Reservation Name Search ***");
			pmhomepage.enter_NameOrResvtn(tabledata.get("FirstName"));
			Thread.sleep(2000);

			logger.log(LogStatus.INFO, "Entered Name for Reservation search in PM Dashboard");
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			pmhomepage.clk_findReservation();
			Thread.sleep(10000);


			//Fetching the number of records from db
			/*String sqlQuery = "select count(*)"+
					" from reservation r"+
					" join type t with (nolock) on t.typeid=r.reservationstatustypeid"+
					" join phone p on p.phoneid = r.phoneid"+
					" join emailaddress e on e.emailaddressid = r.emailaddressid"+
					" join reservationitem ri on r.reservationid = ri.reservationid"+
					" left join storageorderitem soi on soi.reservationitemid = ri.reservationitemid"+
					" where (r.firstname like '"+tabledata.get("FirstName")+"%' or r.lastname like '"+tabledata.get("FirstName")+"%') and"+
					" r.siteid = (select siteid from site where sitenumber = "+location+")"+
					" and t.name<> 'rented'"+
					" and datediff (dd, r.expirationdate,getutcdate() ) <= 30";*/
			
			String sqlQuery = "select count(*)"+
				     "from reservation r with (nolock) "+
				     "join reservationitem ri with (nolock) on ri.reservationid=r.reservationid "+
				     "join type t with (nolock) on t.typeid=r.reservationstatustypeid "+
				     "join site s with (nolock) on s.siteid=r.siteid "+
				     "left join phone p with (nolock) on p.phoneid=r.phoneid "+
				     "left join emailaddress ea with (nolock) on ea.emailaddressid=r.emailaddressid "+
				     "where 1=1 "+
				     "and t.name<> 'rented' "+
				     "and r.siteid = (select siteid from site where sitenumber = "+location+") "+
				     "and datediff (dd, r.expirationdate,getutcdate() ) <= 30 "+
				     "and (r.firstname like '"+tabledata.get("FirstName")+"%' or r.lastname like '"+tabledata.get("FirstName")+"%') ";
			
			String result = DataBase_JDBC.executeSQLQuery(sqlQuery);
			int dbCount = Integer.parseInt(result);

			if(dbCount == 0){

				logger.log(LogStatus.FAIL, "DB returned 0 records. Please change the Search Criteria");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";

				AdvSearch_Reservation advRes = new AdvSearch_Reservation(driver);
				advRes.click_BackToDashboard();
				Thread.sleep(10000);
			}
			else if (dbCount == 1){

				ViewReservationPage viewRes = new ViewReservationPage(driver);
				boolean viewResPagefound = false;
				if(driver.findElements(By.xpath("//div[@id='reservationPage']//h3[text()='View Reservation']")).size() != 0){
					viewResPagefound = true;
				}
				if(driver.findElements(By.xpath("//div//h3[text()='Cancelled Reservation']")).size() != 0){
					viewResPagefound = true;
				}

				if(viewResPagefound){

					if(driver.findElements(By.xpath("//div[@id='reservationPage']//h3[text()='View Reservation']")).size() != 0){
						logger.log(LogStatus.PASS, "View Reservation page displayed");
					}
					if(driver.findElements(By.xpath("//div//h3[text()='Cancelled Reservation']")).size() != 0){
						logger.log(LogStatus.PASS, "Cancel Reservation page displayed");
					}
					
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);

					sqlQuery = "select r.reservationid, r.lastname, r.firstname, p.phonenumber, e.email"+
							" from reservation r"+
							" join type t with (nolock) on t.typeid=r.reservationstatustypeid"+
							" join phone p on p.phoneid = r.phoneid"+
							" join emailaddress e on e.emailaddressid = r.emailaddressid"+
							" join reservationitem ri on r.reservationid = ri.reservationid"+
							" left join storageorderitem soi on soi.reservationitemid = ri.reservationitemid"+
							" where (r.firstname like '"+tabledata.get("FirstName")+"%' or r.lastname like '"+tabledata.get("FirstName")+"%') and"+
							" r.siteid = (select siteid from site where sitenumber = "+location+")"+
							" and t.name<> 'rented'"+
							" and datediff (dd, r.expirationdate,getutcdate() ) <= 30";
					ArrayList<String> expResValues = DataBase_JDBC.executeSQLQuery_List(sqlQuery);

					String resId = viewRes.getResNum();
					String resName = viewRes.getResName();
					String resPhone = viewRes.getResPhone();
					resPhone = resPhone.replace("-", "");
					String resEmail = viewRes.getResEmail();

					logger.log(LogStatus.INFO,"Comparing the UI and the DB res details");
					logger.log(LogStatus.INFO,"Actual values are fetched from UI  ::  Expected values are fetched from DB");
					if(resId.equals(expResValues.get(0))){
						logger.log(LogStatus.PASS, "Actual Res Id: "+resId+" Expected Res Id: "+expResValues.get(0));
					}else{
						logger.log(LogStatus.FAIL, "Actual Res Id: "+resId+" Expected Res Id: "+expResValues.get(0));
						resultFlag="fail";
					}

					if(resName.equals(expResValues.get(2)+" "+expResValues.get(1))){
						logger.log(LogStatus.PASS, "Actual Name: "+resName+" Expected Name: "+expResValues.get(2)+" "+expResValues.get(1));
					}else{
						logger.log(LogStatus.FAIL, "Actual Name: "+resName+" Expected Name: "+expResValues.get(2)+" "+expResValues.get(1));
						resultFlag="fail";
					}

					if(resPhone.equals(expResValues.get(3))){
						logger.log(LogStatus.PASS, "Actual Phone Num: "+resPhone+" Expected Phone Num: "+expResValues.get(3));
					}else{
						logger.log(LogStatus.FAIL, "Actual Phone Num: "+resPhone+" Expected Phone Num: "+expResValues.get(3));
						resultFlag="fail";
					}

					if(resEmail.equals(expResValues.get(4))){
						logger.log(LogStatus.PASS, "Actual Email: "+resEmail+" Expected Email: "+expResValues.get(4));
					}else{
						logger.log(LogStatus.FAIL, "Actual Email: "+resEmail+" Expected Email: "+expResValues.get(4));
						resultFlag="fail";
					}

				}else{
					logger.log(LogStatus.FAIL, "View Reservation or Cancel Reservation page not displayed");
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
					resultFlag="fail";
				}


				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(2000);
				viewRes.clk_backToDshBoard();
				Thread.sleep(10000);

			}
			else if (dbCount > 1){

				logger.log(LogStatus.INFO, "After clicking on search button, navigated to Reservation Search results page");
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(2000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

				AdvSearch_Reservation advRes = new AdvSearch_Reservation(driver);
				String getText = advRes.getText_records_match().toLowerCase();
				if(getText.contains(dbCount+" records matched")){
					logger.log(LogStatus.INFO, "DB Count: "+dbCount);
					logger.log(LogStatus.INFO, "Web Message: "+getText);
					logger.log(LogStatus.PASS, "Number of records matched between DB and UI");
				}else{
					logger.log(LogStatus.INFO, "DB Count: "+dbCount);
					logger.log(LogStatus.INFO, "Web Message: "+getText);
					logger.log(LogStatus.FAIL, "Number of records not matched between DB and UI");
					resultFlag="fail";
				}

				sqlQuery = "select top 1 r.lastname, r.firstname, p.phonenumber, e.email, r.reservationid"+
						" from reservation r"+
						" join type t with (nolock) on t.typeid=r.reservationstatustypeid"+
						" join phone p on p.phoneid = r.phoneid"+
						" join emailaddress e on e.emailaddressid = r.emailaddressid"+
						" join reservationitem ri on r.reservationid = ri.reservationid"+
						" left join storageorderitem soi on soi.reservationitemid = ri.reservationitemid"+
						" where (r.firstname like '"+tabledata.get("FirstName")+"%' or r.lastname like '"+tabledata.get("FirstName")+"%') and"+
						" r.siteid = (select siteid from site where sitenumber = "+location+")"+
						" and t.name<> 'rented'"+
						" and datediff (dd, r.expirationdate,getutcdate() ) <= 30"+
						"   order by r.lastname asc";
				ArrayList<String> resValues = DataBase_JDBC.executeSQLQuery_List(sqlQuery);

				int count = 0;
				for(int i=0; i<resValues.size(); i++){

					getText = driver.findElement(By.xpath("//div[@class='searchResult k-grid k-widget']/div[@class='k-grid-content ps-container']/table/tbody/tr/td["+(i+2)+"]")).getText().trim();
					if(i==2){
						getText = getText.replaceAll(" ", "");
						getText = getText.replace("(", "");
						getText = getText.replace(")", "");
						getText = getText.replace("-", "");
					}
					if(resValues.get(i) == null){
						if(getText.equals("")){
							count++;
						}
					}else{
						if(resValues.get(i).toString().trim().equalsIgnoreCase(getText)){
							count++;
							logger.log(LogStatus.PASS, "Expected Result (DB value) :"+resValues.get(i).toString().trim()+" and Actual Result (UI value) :"+getText+" in advance search results grid");
						}
					}
				}

				if(count == resValues.size()){
					logger.log(LogStatus.PASS, "Results displayed in the application matched with the db result");
				}else{
					resultFlag="fail";
					logger.log(LogStatus.FAIL, "Results displayed in the application not matched with the db result");
					getText = driver.findElement(By.xpath("//div[@class='searchResult k-grid k-widget']/div[contains(@class,'k-grid-content ps-container')]/table/tbody/tr")).getText().trim();
					logger.log(LogStatus.INFO, "Actual Result (UI value) : "+getText);
					for(int i=0; i<resValues.size(); i++){
						logger.log(LogStatus.INFO, "Expected Result (DB value) : "+resValues.get(i).toString().trim());
					}
				}

				jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(2000);
				advRes.click_BackToDashboard();
				Thread.sleep(10000);

			}

			String noresquery="select Top 1 c.firstname from contact c "+
					"join customer cu on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "+ 
					"join accountorder ao on ao.accountid=a.accountid "+
					"join accountorderitem aoi on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid "+
					"join rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
					"join type t on t.typeid=cu.customertypeid "+
					"join site s on s.siteid=aoi.siteid "+
					"left join accountphone ap on ap.accountid=a.accountid "+
					"left join accountemail ae on ae.accountid=a.accountid "+
					"left join phone p on p.phoneid=ap.phoneid "+
					"left join emailaddress ea on ea.emailaddressid=ae.emailid "+
					"where soi.vacatedate is not null and  sitenumber ="+location+" "+
					"and ap.isprimary=1 ";


			String noResCustname=DataBase_JDBC.executeSQLQuery(noresquery);
			pmhomepage.enter_NameOrResvtn("000");
			Thread.sleep(2000);
			pmhomepage.clk_findReservation();
			Thread.sleep(5000);
			Thread.sleep(8000);
			ResCustSearchPage resCustPage=new ResCustSearchPage(driver);
			resCustPage.enter_txt_firstName(noResCustname);
			scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Entered a Customer name who does not have any reservation");
			logger.log(LogStatus.INFO, "",image);
			resCustPage.click_btn_search();
			Thread.sleep(10000);
			resCustPage.get_NoResultsEM();
			if(resCustPage.get_NoResultsEM().contains("NO RESULTS FOUND MATCHING YOUR CRITERIA")){
				scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer who doesn't have reservation is verified with the expected Error Message");
				logger.log(LogStatus.INFO, "Customer who doesn't have reservation is verified with the expected Error Message",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer who doesn't have reservation is failed to verify with the expected Error Message");
				logger.log(LogStatus.INFO, "Customer who doesn't have reservation is failed to verify with the expected Error Message",image);
			}

		}
		catch(Exception e){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed",image);
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_ReservationsearchusingName" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_ReservationsearchusingName" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_ReservationsearchusingName" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}	
}
