package Scenarios.DTMCalls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.DTMCallsPages.DTMCallscreen;
import Pages.DTMCallsPages.DTMcallcomplition;
import Pages.DTMCallsPages.DTMcalldetails;
import Pages.DTMCallsPages.DTMdashboard;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Pages.VacatePages.VacateConfirmationPopUp;
import Pages.VacatePages.VacateNowPayment;
import Pages.Walkin_Reservation_Lease.CreateReserVation_PopUp;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.Unreservation_popup;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import ProjectSpecificMethods.PS_GenericMethods;
import Scenarios.Browser_Factory;
import VacateNowPages.Vacate_MisChrgespage;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DTM_Commitment_Cal_leftmsg extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	String locationnum = null;

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "DTMCalls", "DTMCalls",
				"DTM_Commitment_Call_leftmsg");
	}
	
	


	@Test(dataProvider = "getLoginData")
	public void DTM_Commitment_Cal_leftmsg_method(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("DTMCalls").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);
		try {
			
			logger = extent.startTest("'DTM_Commitment_Cal_leftmsg", "'DTM_Commitment_Cal_leftmsg");
			
			//============ to change site id ==========================================================

			String ipadd = Generic_Class.getIPAddress();

			String siteid_ipsdd = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd+ "'";
			
			String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid_ipsdd);
			Thread.sleep(5000);
			
			String allocateemtyip = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ siteid_ipsdd_data + "'";
			
			DataBase_JDBC.executeSQLQuery(allocateemtyip);
			
			Thread.sleep(5000);
			
			String newsiteid_missedcall = "select distinct top 1 aoi.siteid from phonecall pc "+
					"join type t on t.typeid=pc.calltypeid "+
					"join accountorderitem aoi on aoi.storageorderitemid=pc.ownertableitemid "+
					"where pc.expirationdate >getutcdate() "+
					"and pc.IsCallCompleted is NULL "+
					"and t.name='Commitment call'";
											
			

			
			String newsiteid_missedcall_db = DataBase_JDBC.executeSQLQuery(newsiteid_missedcall);
			
			System.out.println(" th siteid_ipsdd_data is-----" + newsiteid_missedcall_db);

			
			Thread.sleep(5000);
			
			String allocatedsiteidnewly = "Update siteparameter set paramvalue='"+ipadd+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ newsiteid_missedcall_db + "'";
			
			DataBase_JDBC.executeSQLQuery(allocatedsiteidnewly);
			
			
			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);
			//=============== Navigating to missedcommunication call and clicking=====================================
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			LoginPage login = new LoginPage(driver);
			Thread.sleep(2000);
			//login.login("930326","password");
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);

			Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);

		
			Thread.sleep(2000);
			PM_Homepage homepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "Home Page object is created successfully");
			Thread.sleep(6000);

			DTMdashboard dtmdashbrd= new DTMdashboard(driver);
			Thread.sleep(4000);
			
			
			WebElement callname=dtmdashbrd.Collection_calls1(tabledata.get("Calltype"), driver);
			
			String Commitmentcall_count=dtmdashbrd.Callcount_displaycall();
			
			int Commitmentcall_countconcert=Integer.parseInt(Commitmentcall_count);
			
			if(callname.isDisplayed() && Commitmentcall_countconcert>0){
				
				logger.log(LogStatus.PASS, "The Commitment call is displayed with Total # Queued---"+Commitmentcall_countconcert);
			}else{
				
				resultFlag="Fail";
				logger.log(LogStatus.FAIL, "The Commitment call is not displayed with Total # Queued---"+Commitmentcall_countconcert);
			}
			Thread.sleep(2000);
			callname.click();
			Thread.sleep(6000);
			boolean errormsg=driver.getPageSource().contains("NO ITEMS TO DISPLAY");
			 if(errormsg){

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "message dispayed as--NO ITEMS TO DISPLAY");
					logger.log(LogStatus.INFO, "Error message is displayed",image);
				}else{

					if(resultFlag.equals("pass"))
						resultFlag="fail";

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Error message is not  displayed-- ");
					logger.log(LogStatus.INFO, "Error message is not displayed ",image);
				}
			
			
			 Thread.sleep(6000);
			DTMcalldetails dtmcalldetails= new DTMcalldetails(driver);
			
//===========================Customer vacates the space====================================
			
			String vacateaccount = "select top 1 A.accountnumber,count(A.accountnumber) from phonecall pc "+
								"join type t on t.typeid=pc.calltypeid "+
								"join accountorderitem aoi on aoi.storageorderitemid=pc.ownertableitemid "+
								"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+ 
								"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
								"Join AccountOrder AO with(nolock) on AO.AccountOrderID = AOI.AccountOrderID "+
								"Join Account A with(nolock) on A.AccountID = AO.AccountID "+
								"Join Customer Cu with(nolock) on Cu.CustomerID = A.CustomerID "+ 
								"join contact C with(nolock) on C.contactid=Cu.contactid "+
								"where pc.expirationdate >getutcdate() "+
								"and t.name='Commitment Call' "+ 
								"and soi.vacatedate is null "+
								"and aoi.siteid='"+newsiteid_missedcall_db+"'  group by  A.accountnumber "+
								"Having count(A.accountnumber)>1";
			
			
			ArrayList<String> vacateaccountnumber = DataBase_JDBC.executeSQLQuery_List(vacateaccount);
			
			int accountcount= Integer.parseInt(vacateaccountnumber.get(1));
			
			
			 
			boolean flag=false;
			
			boolean nextbutton=driver.findElement(By.xpath("//a[@title='Go to the next page']")).isEnabled();
			
			while(nextbutton){
				
				List<WebElement> Listofaccountnumber= dtmcalldetails.numberofrows_accountdata(driver);
				
				for (int i=0;i<Listofaccountnumber.size();i++){
					  
			  		if(Listofaccountnumber.get(i).getText().equals(vacateaccountnumber.get(0))){
			  			
			  			flag=true;
			  			Listofaccountnumber.get(i).click();
							logger.log(LogStatus.PASS, "The Account number with Vacate Now option is exist---"+Listofaccountnumber.get(i).getText());
									 
						  
					  }else{
						 
						  logger.log(LogStatus.INFO, "The  account number  removed from Final List");
						  
						  
					  }
			  		
				}
				
				if(flag){
					
					break;
				}else{
					
					driver.findElement(By.xpath("//a[@title='Go to the next page']")).click();
				}
				
		  
	  }
			
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(3000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.PASS, "Clicked On Space Details Tab successfully in customer dashbaord");

			Acc_SpaceDetailsPage spacedetails=new Acc_SpaceDetailsPage(driver);
			spacedetails.click_VacateNow_Btn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked On  Vacate Now button successfully");
			
			
			
			Vacate_MisChrgespage MiscChargePage=new Vacate_MisChrgespage(driver);
			MiscChargePage.clk_continue_Btn();
			Thread.sleep(10000);
			
			VacateNowPayment pymnt = new VacateNowPayment(driver);
			js1.executeScript("window.scrollBy(0,1000)");
			pymnt.click_SubmitButton();
			logger.log(LogStatus.PASS, "Clicked on Submit button successfully ");
			Thread.sleep(5000);

			VacateConfirmationPopUp ConfirmationPopUp=new VacateConfirmationPopUp(driver); 
			ConfirmationPopUp.click_NoRadioButton();
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Clicked on No Radio button successfully ");

			ConfirmationPopUp.enter_Note_TextArea(tabledata.get("Note"));
			logger.log(LogStatus.PASS, "Note Entered successfully ");
			Thread.sleep(2000);

			ConfirmationPopUp.enter_EmpNumber(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "Employee No Entered successfully :" +tabledata.get("UserName"));
			Thread.sleep(2000);

			ConfirmationPopUp.click_ConfirmButton();
			logger.log(LogStatus.PASS, "Clicked on Confirm button successfully ");
			Thread.sleep(15000);
			
			callname.click();
			Thread.sleep(10000);
			
				
		  
			String vacateaccount1 = "select top 1 A.accountnumber,count(A.accountnumber) from phonecall pc "+
					"join type t on t.typeid=pc.calltypeid "+
					"join accountorderitem aoi on aoi.storageorderitemid=pc.ownertableitemid "+
					"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+ 
					"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
					"Join AccountOrder AO with(nolock) on AO.AccountOrderID = AOI.AccountOrderID "+
					"Join Account A with(nolock) on A.AccountID = AO.AccountID "+
					"Join Customer Cu with(nolock) on Cu.CustomerID = A.CustomerID "+ 
					"join contact C with(nolock) on C.contactid=Cu.contactid "+
					"where pc.expirationdate >getutcdate() "+
					"and t.name='Commitment Call' "+ 
					"and soi.vacatedate is null "+
					"and aoi.siteid='"+newsiteid_missedcall_db+"'  group by  A.accountnumber "+
					"Having count(A.accountnumber)>1";


			ArrayList<String> vacateaccountnumber1 = DataBase_JDBC.executeSQLQuery_List(vacateaccount1);

			int accountcount1= Integer.parseInt(vacateaccountnumber.get(1));
			
			if(accountcount1==accountcount-1){
				
				 logger.log(LogStatus.PASS, "The  account number  removed from Final List");
				
			}else{
				
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The  account number  not removed list-----");
				logger.log(LogStatus.INFO, " The  account number  not removed list----", image);
				
			}

			Thread.sleep(6000);
			
			//========================== Verifying call page============================================
			
			
			String Postdue=dtmcalldetails.get_pastdue();
			
		//=============verify sorting of customer name data=============================================
			Thread.sleep(2000);
			 dtmcalldetails.click_spacenum();
			 Thread.sleep(2000);
			  List<WebElement> ListWbElebefore=dtmcalldetails.numberofrows_spacenumber(driver);
				
			  String[] arrayspacenum = new String[ListWbElebefore.size()];
			  
			  	
				  for (int k=0;k<ListWbElebefore.size();k++){
					  
					  arrayspacenum[k]= ListWbElebefore.get(k).getText();
					  System.out.println("inserting into array---"+arrayspacenum[k]);
					  Thread.sleep(1000);
				  }
			  Thread.sleep(2000);			
			
			  dtmcalldetails.click_spacenum();
			 
			  Thread.sleep(5000);
			  List<WebElement> ListWbEleafter=dtmcalldetails.numberofrows_spacenumber(driver);
			 
			  String[] arrayspacenum2 = new String[ListWbEleafter.size()];
			  
			  	
			  for (int k=0;k<ListWbEleafter.size();k++){
				  
				  arrayspacenum2[k]= ListWbEleafter.get(k).getText();
				  System.out.println("inserting into array---"+arrayspacenum2[k]);
				  Thread.sleep(1000);
			  }
			  
			  if(ListWbElebefore.size()>7){
				  for (int i=0,j=ListWbEleafter.size()-1;i<5;i++,j--){
						 // for(int ;j>=0;j--){
							  System.out.println("--actual data------- displayed---"+arrayspacenum[i]+"----actual--"+arrayspacenum2[j]);
						 // System.out.println("--actual data ----------displayed---"+);
							 
						  if(arrayspacenum2[j].contains(arrayspacenum[i])){
							  String scpath = Generic_Class.takeScreenShotPath();
								String image = logger.addScreenCapture(scpath);
							  
								logger.log(LogStatus.PASS, " Space Number  is sorted---"+ListWbEleafter.get(j).getText().toString()+"--actual data displayed---"+arrayspacenum[i]);
								  logger.log(LogStatus.INFO, " AccouSpacent Number data is dorted", image);
							  }else{
								  
								  if(resultFlag.equals("pass"))
										resultFlag="fail";
									String scpath = Generic_Class.takeScreenShotPath();
									String image = logger.addScreenCapture(scpath);
									logger.log(LogStatus.FAIL, " Space Number data is not sorted--"+ListWbEleafter.get(j).getText().toString()+"--actual data displayed---"+arrayspacenum[i]);
									logger.log(LogStatus.INFO, " Space Number data is not dorted", image);
									break;
							  }
					  
				//  }
					  }
			  
			  }else{
				  for (int i=0,j=ListWbEleafter.size()-1;i<ListWbEleafter.size();i++,j--){
					 // for(int ;j>=0;j--){
						  System.out.println("--actual data------- displayed---"+arrayspacenum[i]+"----actual--"+arrayspacenum2[j]);
					 // System.out.println("--actual data ----------displayed---"+);
						 
					  if(arrayspacenum2[j].contains(arrayspacenum[i])){
						  String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
						  
							logger.log(LogStatus.PASS, " Space Number  is sorted---"+ListWbEleafter.get(j).getText().toString()+"--actual data displayed---"+arrayspacenum[i]);
							  logger.log(LogStatus.INFO, " AccouSpacent Number data is dorted", image);
						  }else{
							  
							  if(resultFlag.equals("pass"))
									resultFlag="fail";
								String scpath = Generic_Class.takeScreenShotPath();
								String image = logger.addScreenCapture(scpath);
								logger.log(LogStatus.FAIL, " Space Number data is not sorted--"+ListWbEleafter.get(j).getText().toString()+"--actual data displayed---"+arrayspacenum[i]);
								logger.log(LogStatus.INFO, " Space Number data is not dorted", image);
								break;
						  }
				  
			//  }
				  }
			 }
			  Thread.sleep(5000);
			  
	
			  
		
			//======================= Click on any record in the list=======================================
			 
			 dtmcalldetails.click_firstrow_call();
			 Thread.sleep(5000);
			 //=================Final  screen validation===============================================
			 
			 
			 DTMCallscreen dtmcall= new DTMCallscreen(driver);
			  
			 
			 	Thread.sleep(2000);
			 	dtmcall.clk_ViewBalance();
			 	
			 	Boolean viewbalancedisplay=dtmcall.displayBalancefrommodelwindow();
			 	
			 	String viewbalancefrommodelwindow=dtmcall.getBalancefrommodelwindow();
			 	Thread.sleep(2000);
			 	if (viewbalancedisplay){
			 		String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "viewbalance from model window---------"+viewbalancefrommodelwindow);
					logger.log(LogStatus.INFO, " Showed Balance is matching", image);
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "viewbalance from model window is not displayed---------"+viewbalancefrommodelwindow);
					logger.log(LogStatus.INFO, " Showed Balance is not matching", image);
				}
			 	
			 	
			 
			 	
			 	Thread.sleep(2000);
			 	dtmcall.clk_closebutton();
			 	
			 	Thread.sleep(2000);
			 	
			 	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
				
				String dateInString =driver.findElement(By.xpath("//div[@id='CallTemplate']//div/span[contains(text(),'Paid Through Date')]/..")).getText();
				String[] subdate= dateInString.split("\n");
				
				Date date2 = formatter.parse(subdate[1]);
				
				
			 	long difference = (date.getTime()-date2.getTime())/86400000; 
			 	
			 	String lastdaysdiff = String.valueOf(difference);

				
				String dayslate =driver.findElement(By.xpath("//div[@id='CallTemplate']//div/span[contains(text(),'Days Late')]/..")).getText();
				String[] subdayslate= dayslate.split("\n");
				
				Thread.sleep(2000);
				if (lastdaysdiff.equals(subdayslate[1])){
					logger.log(LogStatus.PASS, "days left are matching---"+lastdaysdiff+"actual days left is UI--"+subdayslate[1]);
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "days left not matching--"+lastdaysdiff+"actual days left is UI--"+subdayslate[1]);
					logger.log(LogStatus.INFO, " days left not matching", image);
				}
				Thread.sleep(2000);
			//===================Select payment Final date and Click Submit  Call(s) button, without to select Phone numbers=============
				
			//================================================
				 js1.executeScript("window.scrollBy(0,800)", "");
				
				Thread.sleep(2000);
				dtmcall.click_home_Pnone_chkbx();
			
				
				Thread.sleep(5000);
				
				//======================== Reached Option script========================================================
				 
			
				 js1.executeScript("window.scrollBy(0,800)", "");
					
					Thread.sleep(2000);
					dtmcall.selectResult_dropdownValues("Left Message");
					Thread.sleep(2000);
					dtmcall.enter_comment_txtfield(tabledata.get("Comment"));
					Thread.sleep(2000);
					js1.executeScript("window.scrollBy(0,800)", "");
					 Thread.sleep(2000);
					dtmcall.clk_submitbutton();
					Thread.sleep(8000);
					
					DTMcallcomplition call_compliton= new DTMcallcomplition(driver);
					
					if (call_compliton.isDisplayedconfirmationmodelwindow()){
						logger.log(LogStatus.PASS, "confirmation model window is displayed");
						
					} else {
						if(resultFlag.equals("pass"))
							resultFlag="fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "confirmation model window is not  displayed");
						logger.log(LogStatus.INFO, "confirmation model window is not displayed", image);
					}
					Thread.sleep(2000);
					String Accountnumber=call_compliton.get_accountnumber();
					
					String spacenumber=call_compliton.get_spacenumber();
					
			String phonenumber=call_compliton.get_phoneumber();
			String subone=phonenumber.substring(1, 4);
			String subtwo=phonenumber.substring(6, 9);
			String subthree=phonenumber.substring(10, 14);
			
					
					String replace=subone.concat(subtwo);
					String replace_phonenumber=replace.concat(subthree);
					
					System.out.println(" the phone number is----"+replace_phonenumber);
					
					Thread.sleep(2000);
					call_compliton.enter_employeenum(tabledata.get("UserName"));
					Thread.sleep(2000);
					call_compliton.click_save();
					
					
					
					Thread.sleep(10000);
					
					 List<WebElement> Listspacenumaftercomplition= dtmcalldetails.numberofrows_spacenumber(driver);

						
					  	if(Listspacenumaftercomplition.size()>7){
						  
						  for (int i=0;i<5;i++){
							  
							  if(Listspacenumaftercomplition.get(i).getText().equals(spacenumber)){
								  if(resultFlag.equals("pass"))
										resultFlag="fail";
									String scpath = Generic_Class.takeScreenShotPath();
									String image = logger.addScreenCapture(scpath);
									logger.log(LogStatus.FAIL, "The Complited call is not removed from Commitment call List");
									logger.log(LogStatus.INFO, "The Complited call is not removed from Commitment call List", image);
								 
								  
							  }else{
								  String scpath = Generic_Class.takeScreenShotPath();
									String image = logger.addScreenCapture(scpath);
								  logger.log(LogStatus.PASS, "The Complited call is  removed from Commitment call List", image);
								  
							  }
							  
							  
						  }  
					  }else{
						  	for (int i=0;i<Listspacenumaftercomplition.size();i++){
							  
						  		if(Listspacenumaftercomplition.get(i).getText().equals(spacenumber)){
									  if(resultFlag.equals("pass"))
											resultFlag="fail";
										String scpath = Generic_Class.takeScreenShotPath();
										String image = logger.addScreenCapture(scpath);
										logger.log(LogStatus.FAIL, "The Complited call is not removed from Commitment call List");
										logger.log(LogStatus.INFO, "The Complited call is not removed from Commitment call List", image);
									 
									  
								  }else{
									  String scpath = Generic_Class.takeScreenShotPath();
										String image = logger.addScreenCapture(scpath);
									  logger.log(LogStatus.PASS, "The Complited call is  removed from Commitment call List", image);
									  
								  }
						  
						  
					  }
					  }
					  	
					  	Thread.sleep(4000);
					  	
					  	
					  	String complition_call = "select top 1 IsCallCompleted from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
						String complition_call_lien = DataBase_JDBC.executeSQLQuery(complition_call);
						
						if (complition_call_lien.equals("1")){
							logger.log(LogStatus.PASS, "Commitment Call is completed value set in DB as--"+complition_call_lien);
							
						} else {
							if(resultFlag.equals("pass"))
								resultFlag="fail";
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Commitment  Call is not  completed value sety in DB is---"+complition_call_lien);
							logger.log(LogStatus.INFO, "Commitment Call is not completed", image);
						}	
						
						
						
						String leftmessage = "select top 1 IsLeftMessage from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
					  	
						String leftmessage1 = DataBase_JDBC.executeSQLQuery(leftmessage);  	
						
						
						Thread.sleep(3000);
					
						if (leftmessage1.equals("1")){
							logger.log(LogStatus.PASS, "Commitment Call left message  set in DB as--"+leftmessage1);
							
						} else {
							if(resultFlag.equals("pass"))
								resultFlag="fail";
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Commitment Call left message not set in DB as--"+leftmessage1);
							logger.log(LogStatus.INFO, "Commitment Call left message  not set in DB as--", image);
						}	
									
						Thread.sleep(4000);
						
						
						
						String phonecallid = "select top 1 PhoneCallID from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
						
						String phonecallid_verify = DataBase_JDBC.executeSQLQuery(phonecallid); 
						
						
						String callreason = "select Name from type where typeid=(select CallResultTypeID from phonecalllog where PhoneCallID='"+phonecallid_verify+"')";
						
						String callreason_verify = DataBase_JDBC.executeSQLQuery(callreason); 
						
						if (callreason_verify.equals("Left Message")){
							logger.log(LogStatus.PASS, "Commitment Call is completed reason--- "+callreason_verify);
							
						} else {
							if(resultFlag.equals("pass"))
								resultFlag="fail";
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Commitment Call is completed reason--- "+callreason_verify);
							logger.log(LogStatus.INFO, "Commitment Call is completed reason", image);
						}	
					
						
						String phonelogcomment = "select Comment from phonecalllog where PhoneCallID='"+phonecallid_verify+"'";
						
						String phonelogfilecreate1 = DataBase_JDBC.executeSQLQuery(phonelogcomment);  	
						
						if (phonelogfilecreate1.equals(tabledata.get("Comment"))){
							logger.log(LogStatus.PASS, "Phonelog table comment entered as----"+phonelogfilecreate1);
							
							
						} else {
							
							if(resultFlag.equals("pass"))
								resultFlag="fail";
							
							logger.log(LogStatus.FAIL, "Phonelog table comment not  entered as----"+phonelogfilecreate1);
							logger.log(LogStatus.INFO, "Phonelog table comment not  entered as");
							
						}	
					
						Thread.sleep(4000);
					  	String phonelogIsBadNumber = "select IsBadNumber from phonecalllog where PhoneCallID='"+phonecallid_verify+"'";
														
						String phonelogIsBadNumber1 = DataBase_JDBC.executeSQLQuery(phonelogIsBadNumber);  	
						
						if (phonelogIsBadNumber1.equals("0")){
							logger.log(LogStatus.PASS, "Phonelog table IsBadNumber entered as----"+phonelogIsBadNumber1);
							
							
						} else {
							
							if(resultFlag.equals("pass"))
								resultFlag="fail";
							
							logger.log(LogStatus.FAIL, "Phonelog table IsBadNumber not  entered as----"+phonelogIsBadNumber1);
							logger.log(LogStatus.INFO, "Phonelog table IsBadNumber not  entered as");
							
						}	
					
						
//						SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//						Date dt = new Date();
//						Calendar c = Calendar.getInstance(); 
//						c.setTime(dt); 
//						c.add(Calendar.DATE, 1);
//						dt = c.getTime();
//						String commitmentdate=formatter1.format(dt);
//						
//						String commitmentdateUpdate = "select top 1 PaymentCommitmentDate from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
//						
//						String commitmentdateUpdate1 = DataBase_JDBC.executeSQLQuery(commitmentdateUpdate); 
//						
//						if (commitmentdateUpdate1.contains(commitmentdate)){
//							logger.log(LogStatus.PASS, "Phonecall table commitment date is updated----"+commitmentdateUpdate1);
//							
//							
//						} else {
//							
//							if(resultFlag.equals("pass"))
//								resultFlag="fail";
//							
//							logger.log(LogStatus.FAIL, "Phonecall table commitment date is not  updated----"+commitmentdateUpdate1);
//							
//						}	
//					
					
					Thread.sleep(5000);
					
				
		} catch (Exception ex) {
			ex.printStackTrace();
			// In the catch block, set the variable resultFlag to “fail”
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	
	@AfterMethod
	public void afterMethod() {

		System.out.println(" In After Method");
		if (resultFlag.equalsIgnoreCase("pass")) {
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTM_Commitment_Call_leftmsg", "Status",
					"Pass");

		} else if (resultFlag.equalsIgnoreCase("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTM_Commitment_Call_leftmsg", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTM_Commitment_Call_leftmsg", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
