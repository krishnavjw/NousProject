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
import Pages.Walkin_Reservation_Lease.CreateReserVation_PopUp;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.Unreservation_popup;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import ProjectSpecificMethods.PS_GenericMethods;
import Scenarios.Browser_Factory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DTM_Missed_Call_unreached extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	String locationnum = null;

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "DTMCalls", "DTMCalls",
				"DTM_final_Call_unreached");
	}
	
	


	@Test(dataProvider = "getLoginData")
	public void DTM_final_Call_unreached(Hashtable<String, String> tabledata) throws Exception {
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("DTMCalls").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);
		try {
			
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			logger = extent.startTest("'DTM_final_Call_unreached", "'DTM_final_Call_unreached");
			
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
					"and t.name='final Call'";
											
			

			
			String newsiteid_missedcall_db = DataBase_JDBC.executeSQLQuery(newsiteid_missedcall);
			
			System.out.println(" th siteid_ipsdd_data is-----" + newsiteid_missedcall_db);

			
			Thread.sleep(5000);
			
			String allocatedsiteidnewly = "Update siteparameter set paramvalue='"+ipadd+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"
					+ newsiteid_missedcall_db + "'";
			
			DataBase_JDBC.executeSQLQuery(allocatedsiteidnewly);
			
			
			Thread.sleep(3000);
			driver.navigate().refresh();
			Thread.sleep(5000);
			//=============== Navigating to missedcommunication call and clicking=====================================
			
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
			//dtmdashbrd.Collection_calls1(tabledata.get("Calltype"), driver);
			
			WebElement callname=driver.findElement(By.xpath("//div[@class='collection-list']//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[2]/td[1]/a"));
			
			if(callname.isDisplayed()){
				
				logger.log(LogStatus.PASS, "The lien call is displayed");
			}else{
				
				resultFlag="Fail";
				logger.log(LogStatus.FAIL, "lien call is not scheduled for the day");
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
			
			Thread.sleep(4000);
			
			
			//========================== Verifying call page============================================
			
			DTMcalldetails dtmcalldetails= new DTMcalldetails(driver);
			

			if (dtmcalldetails.isDisplayedCustmername()) {
				logger.log(LogStatus.PASS, " Customer Name dispalyed sucessfully");
				
			} else {
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Name not dispalyed sucessfully");
				logger.log(LogStatus.INFO, "Customer Name not dispalyed sucessfully", image);
			}
			
			
			Thread.sleep(5000);
			
			
			
			
			String Postdue=dtmcalldetails.get_pastdue();
		//=============verify sorting of customer name data=============================================
			
			
			//======================= Click on any record in the list=======================================
			 
			 dtmcalldetails.click_firstrow_call();
			 Thread.sleep(5000);
			 //=================lien  screen validation===============================================
			 
			 
			 DTMCallscreen dtmcall= new DTMCallscreen(driver);
			  
			 
			 	if (dtmcall.isDisplayedCommitmentdate()) {
					logger.log(LogStatus.PASS, " calender to select commitment date is displayed");
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "calender to select commitment date is not displayed");
					logger.log(LogStatus.INFO, " calender to select commitment date is not displayed", image);
				}
			 
			 	Thread.sleep(2000);
			 	
			 	
			 	dtmcall.clk_Commitmentdate();
			 	Thread.sleep(2000);
			 	
			 	if (dtmcall.SelectDateFromCalendar("1")) {
			 		String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, " calender to select commitment date is displayed to select dates");
					logger.log(LogStatus.INFO, " calender to select commitment date is displayed to select dates", image);
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "calender to select commitment date is not displayed to select dats");
					logger.log(LogStatus.INFO, " calender to select commitment date is not displayed to select dates", image);
				}
			 	
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
				System.out.println(formatter.format(date));
				
				
				String dateInString =driver.findElement(By.xpath("//div[@id='CallTemplate']//div/span[contains(text(),'Paid Through Date')]/..")).getText();
				String[] subdate= dateInString.split("\n");
				
				System.out.println("pAID THROUGHT DATE IS----"+subdate[1]);
				
				Date date2 = formatter.parse(subdate[1]);
				
				
			 	long difference = (date.getTime()-date2.getTime())/86400000; 
			 	
			 	String lastdaysdiff = String.valueOf(difference);

				System.out.println("the diff is---"+difference);
				
				String dayslate =driver.findElement(By.xpath("//div[@id='CallTemplate']//div/span[contains(text(),'Days Late')]/..")).getText();
				String[] subdayslate= dayslate.split("\n");
				
				System.out.println("days late----"+subdayslate[1]);
				
			
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
			//===================Select payment Lien date and Click Submit  Call(s) button, without to select Phone numbers=============
				dtmcall.clk_Commitmentdate();
			 	
				
				Thread.sleep(3000);
				dtmcall.SelectDateFromCalendarDTM("1");
				Thread.sleep(2000);
				 js1.executeScript("window.scrollBy(0,800)", "");
				 Thread.sleep(2000);
				dtmcall.clk_submitbutton();
				Thread.sleep(2000);
				String error_msg=dtmcall.error_phonenumber();
				String Expected_error="Please select a number";
				
				if (error_msg.contains(Expected_error)){
					logger.log(LogStatus.PASS, "Error message displayed---"+error_msg+"----Expected error msg in  UI--"+Expected_error);
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Error message not matching displayed---"+error_msg+"----Expected error msg in  UI--"+Expected_error);
					logger.log(LogStatus.INFO, " Error message not matching displayed", image);
				}
				Thread.sleep(5000);
			//================================================
				 js1.executeScript("window.scrollBy(0,800)", "");
				List<WebElement> numberogphones= driver.findElements(By.xpath("//div[@class='call-screen-phone-number-20']/label[@class='webchamp-checkbox']"));
				Thread.sleep(2000);
				for(int i=1;i<=numberogphones.size();i++){
					Thread.sleep(2000);
					boolean checkbox_display=driver.findElement(By.xpath("(//div[@class='call-screen-phone-number-20']/label[@class='webchamp-checkbox'])["+i+"]/span[@class='button']")).isDisplayed();
					Thread.sleep(2000);
					boolean checkbox_selected=driver.findElement(By.xpath("(//div[@class='call-screen-phone-number-20']/label[@class='webchamp-checkbox'])["+i+"]/span[@class='button']")).isSelected();
					Thread.sleep(2000);
					
					if (checkbox_display && !checkbox_selected){
						logger.log(LogStatus.PASS, "Check box is displayed for phone number and Default not selected");
						
					} else {
						if(resultFlag.equals("pass"))
							resultFlag="fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Check box is not displayed for phone number or  Default  selected");
						logger.log(LogStatus.INFO, " Check box is not displayed for phone number or  Default  selected", image);
					}
					
				}
				
				Thread.sleep(2000);
				dtmcall.click_home_Pnone_chkbx();
				Thread.sleep(2000);
				
				if (dtmcall.isdisplay_dropdownValues("Reached")){
					logger.log(LogStatus.PASS, "Reached option is displaying in call type drop down");
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Reached option is not displaying in call type drop down");
					logger.log(LogStatus.INFO, " Reached option is not  displaying in call type drop down", image);
				}
				
				Thread.sleep(8000);
				
				if (dtmcall.isdisplay_dropdownValues("Left")){
					logger.log(LogStatus.PASS, "Left Message option is displaying in call type drop down");
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Left Message option is not displaying in call type drop down");
					logger.log(LogStatus.INFO, " Left Message option is not  displaying in call type drop down", image);
				}	
				
				Thread.sleep(8000);
				
				if (dtmcall.isdisplay_dropdownValues("Unreached")){
					logger.log(LogStatus.PASS, "Unreached option is displaying in call type drop down");
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Unreached option is not displaying in call type drop down");
					logger.log(LogStatus.INFO, " Unreached option is not  displaying in call type drop down", image);
				}
				 
				Thread.sleep(2000);
				dtmcall.selectResult_dropdownValues("Reached");
				Thread.sleep(2000);
				js1.executeScript("window.scrollBy(0,800)", "");
				 Thread.sleep(2000);
				dtmcall.clk_submitbutton();
				Thread.sleep(2000);
				String error_msg_note=dtmcall.error_commentbox();
				String Expected_error_note="Please enter a comment";
				
				if (error_msg_note.contains(Expected_error_note)){
					logger.log(LogStatus.PASS, "Error message displayed for Reached ---"+error_msg_note+"----Expected error msg in  UI--"+Expected_error_note);
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Error message not matching displayed for Reached---"+error_msg_note+"----Expected error msg in  UI--"+Expected_error_note);
					logger.log(LogStatus.INFO, " Error message not matching", image);
				}
				Thread.sleep(2000);
				 js1.executeScript("window.scrollBy(0,800)", "");
				Thread.sleep(2000);
				dtmcall.selectResult_dropdownValues("Left Message");
				Thread.sleep(2000);
				js1.executeScript("window.scrollBy(0,800)", "");
				 Thread.sleep(2000);
				dtmcall.clk_submitbutton();
				Thread.sleep(2000);
				String error_msg_note1=dtmcall.error_commentbox();
				String Expected_error_note1="Please enter a comment";
				
				if (error_msg_note1.contains(Expected_error_note)){
					logger.log(LogStatus.PASS, "Error message displayed for Left Message---"+error_msg_note1+"----Expected error msg in  UI--"+Expected_error_note1);
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Error message not matching displayed Left message ---"+error_msg_note1+"----Expected error msg in  UI--"+Expected_error_note1);
					logger.log(LogStatus.INFO, "Error message not matching", image);
				}
				
				Thread.sleep(2000);
				dtmcall.selectResult_dropdownValues("Unreached");
				Thread.sleep(2000);
				dtmcall.click_badNumber_chkbx();
				Thread.sleep(2000);
				js1.executeScript("window.scrollBy(0,800)", "");
				 Thread.sleep(2000);
				dtmcall.clk_submitbutton();
				Thread.sleep(2000);
				String error_msg_note2=dtmcall.error_badoption();
				String Expected_error_note2="Please select a reason";
				
				if (error_msg_note2.contains(Expected_error_note2)){
					logger.log(LogStatus.PASS, "Error message displayed for bad Number---"+error_msg_note2+"----Expected error msg in  UI--"+Expected_error_note2);
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Error message not matching displayed Left message ---"+error_msg_note2+"----Expected error msg in  UI--"+Expected_error_note2);
					logger.log(LogStatus.INFO, " days left not matching", image);
				}
				 js1.executeScript("window.scrollBy(0,800)", "");
				Thread.sleep(2000);
				if (dtmcall.selectReason("Phone Number Out of")){
					logger.log(LogStatus.PASS, "Phone Number Out of Service option is displaying in reason type drop down");
					
				} else {
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					String scpath = Generic_Class.takeScreenShotPath();
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Phone Number Out of Service option is not displaying in Reason type drop down");
					logger.log(LogStatus.INFO, " Phone Number Out of Service option is not  displaying in Reason type drop down", image);
				}
				 
				Thread.sleep(5000);
				
				//======================== Reached Option script========================================================
				 js1.executeScript("window.scrollBy(0,800)", "");
				dtmcall.click_badNumber_chkbx();
				Thread.sleep(2000);
				dtmcall.selectResult_dropdownValues("Unreached");
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
								logger.log(LogStatus.FAIL, "The Complited call is not removed from Lien List");
								logger.log(LogStatus.INFO, "The Complited call is not removed from Lien List", image);
							 
							  
						  }else{
							  String scpath = Generic_Class.takeScreenShotPath();
								String image = logger.addScreenCapture(scpath);
							  logger.log(LogStatus.PASS, "The Complited call is  removed from Lien List", image);
							  
						  }
						  
						  
					  }  
				  }else{
					  	for (int i=0;i<Listspacenumaftercomplition.size();i++){
						  
					  		if(Listspacenumaftercomplition.get(i).getText().equals(spacenumber)){
								  if(resultFlag.equals("pass"))
										resultFlag="fail";
									String scpath = Generic_Class.takeScreenShotPath();
									String image = logger.addScreenCapture(scpath);
									logger.log(LogStatus.FAIL, "The Complited call is not removed from Lien List");
									logger.log(LogStatus.INFO, "The Complited call is not removed from Lien List", image);
								 
								  
							  }else{
								  String scpath = Generic_Class.takeScreenShotPath();
									String image = logger.addScreenCapture(scpath);
								  logger.log(LogStatus.PASS, "The Complited call is  removed from Lien List", image);
								  
							  }
					  
					  
				  }
				  }
				  	
				  	Thread.sleep(4000);
				  	
				  	
				  	String complition_call = "select top 1 IsCallCompleted from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
					String complition_call_lien = DataBase_JDBC.executeSQLQuery(complition_call);
					
					if (complition_call_lien.equals("1")){
						logger.log(LogStatus.PASS, "Lien Call for Unreached is completed value set in DB as--"+complition_call_lien);
						
					} else {
						if(resultFlag.equals("pass"))
							resultFlag="fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Lien Call for Unreached not  completed value sety in DB is---"+complition_call_lien);
						logger.log(LogStatus.INFO, "Lien Call for Unreached not completed", image);
					}	
					
					
								
					Thread.sleep(4000);
					
					
					
					String phonecallid = "select top 1 PhoneCallID from phonecall where PhoneNumber='"+replace_phonenumber+"' order by LastUpdate desc";
					
					String phonecallid_verify = DataBase_JDBC.executeSQLQuery(phonecallid); 
					
					
					String callreason = "select Name from type where typeid=(select CallResultTypeID from phonecalllog where PhoneCallID='"+phonecallid_verify+"')";
					
					String callreason_verify = DataBase_JDBC.executeSQLQuery(callreason); 
					
					if (callreason_verify.equalsIgnoreCase("Unreached")){
						logger.log(LogStatus.PASS, "Lien Call is completed reason--- "+callreason_verify);
						
					} else {
						if(resultFlag.equals("pass"))
							resultFlag="fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Lien Call is completed reason--- "+callreason_verify);
						logger.log(LogStatus.INFO, "Lien Call is completed reason", image);
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
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTMCalls", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Leasing for Customer verification", image);
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTMCalls", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "DTMCalls", "DTMCalls", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
