package Scenarios.CustomerEdit;

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
import Pages.AuthorizedUsersDetails.AuthorizedUsers;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.AuthorizedUsers_IsCustomerPresent;
import Pages.CustDashboardPages.AuthorizedUsrpage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerEdit_Add_Delete_AuthorisedUser_Business extends Browser_Factory{

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getCustomerDtaData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit", "CustomerEdit_Add_Delete_AuthorisedUser_Business");
	}

	@Test(dataProvider = "getCustomerDtaData")
	public void CustomerEdit_Add_Delete_AuthorisedUser_Business(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest("CustomerEdit_Add_Delete_AuthorisedUser_Business", "CustomerEdit InsuranceUpdate starts ");

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

			// =================Handling customer facing
						// device========================
						Thread.sleep(2000);
						Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
					

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

driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));

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
						Thread.sleep(9000);
					
						// =================================================================================


						PM_Homepage pmhome = new PM_Homepage(driver);

						// String location = pmhome.getLocation();

						pmhome.clk_AdvSearchLnk();
					
						Thread.sleep(5000);
						Advance_Search advSearch = new Advance_Search(driver);
						String location = advSearch.getLocationNum();
						// Advance search page

						String query = "select top 1 u.accountnumber from vw_unitdetails u "
								+ "join storageorderitemcontact soic with(nolock) on soic.storageorderitemid = u.storageorderitemid "
								+ "and not exists (select '1' from storageorderitemcontact soic2 "
								+ "where soic2.storageorderitemid = u.storageorderitemid "
								+ "and soic2.contacttypeid = 3152) "
								+ "where u.vacatedate is null "
								+ "and u.customertypeid = 91";

						String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
						
												
						
						
						String queryID =  "select top 1 u.accountid from vw_unitdetails u "
								+ "join storageorderitemcontact soic with(nolock) on soic.storageorderitemid = u.storageorderitemid "
								//+ "join account aa on aa.accountnumber = u.accountnumber "
								+ "and not exists (select '1' from storageorderitemcontact soic2 "
								+ "where soic2.storageorderitemid = u.storageorderitemid "
								+ "and soic2.contacttypeid = 3152) "
								+ "where u.vacatedate is null "
								+ "and u.customertypeid = 91";

						String AccountIDDB = DataBase_JDBC.executeSQLQuery(queryID);
						
						
						

						advSearch.enterAccNum(AccountNumber);
						
						logger.log(LogStatus.INFO, "Account number entered successfully:" + AccountNumber);

						((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
						Thread.sleep(1000);

						advSearch.clk_SearchAccount();
					
						Thread.sleep(2000);
						logger.log(LogStatus.INFO, "Entered Account No and clicked on Search button successfully");
						Thread.sleep(1000);

						// Taking Screenshot
						String scpath2 = Generic_Class.takeScreenShotPath();
						String image2 = logger.addScreenCapture(scpath2);
						logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
						logger.log(LogStatus.INFO, "Navigated to Customer Dashboard successfully", image2);

						// Customer Dashboard
						Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
						if (cust_accdetails.getCustDashboardTitle()
								.contains("Confirm Customer's Identity and Contact Information")) {

							Thread.sleep(8000);
							
							logger.log(LogStatus.PASS,
									"Customer Account Dashboard displayed successfully for an Individual Customer :"
											+ cust_accdetails.getCustDashboardTitle());
							
						}

						else {
							
							logger.log(LogStatus.FAIL, "Customer Account Dashboard not  displayed successfully ");
							
						}

						cust_accdetails.clickSpaceDetails_tab();
						logger.log(LogStatus.INFO, "Clicked on Space details tab successfully ");
						
						String scpath12 = Generic_Class.takeScreenShotPath();
						String image12 = logger.addScreenCapture(scpath12);
						logger.log(LogStatus.PASS, "img",image12);

						/* mohana */
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
						

						Acc_SpaceDetailsPage Acc_SpaceDetailsPage = new Acc_SpaceDetailsPage(driver);
						Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
						logger.log(LogStatus.INFO, "Clicked on authorised users link");
						
						
						
						
						String msg=driver.findElement(By.xpath("//div[contains(text(),'No authorized access contacts.')]")).getText();
						Thread.sleep(2000);
						AuthorizedUsrpage AuthorizedUsrpage = new AuthorizedUsrpage(driver);

						if (AuthorizedUsrpage.verify_AuthorizedAccessPopupIsDisplayed()) {

							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "There is no Authorized access contacts and it is displaying as :"  +  msg);
							logger.log(LogStatus.INFO, "Image", image);
						}

						else {
							Thread.sleep(8000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Authorized Users screen not displayed successfully");
							logger.log(LogStatus.INFO, "Image", image);

						}

						
						
						
						driver.findElement(By.xpath("//a[contains(text(),'Close')]")).click();
						Thread.sleep(2000);
						
						driver.findElement(By.xpath("//span[text()='Customer Info']")).click();
						
						Thread.sleep(2000);
						
						Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
						AccDetails.clk_editAcc_btn();
						logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

						// Verify Edit Account Details Modal window displayed
						EditAccountDetails editAccountDetails = new EditAccountDetails(driver);
						if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
							logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
						}

						else {
							Thread.sleep(2000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
							logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
						}

						// Click on Other Customer Status Radio Button

					  driver.findElement(By.xpath("//span[text()='Authorized Access']")).click();
						logger.log(LogStatus.PASS, "Clicked on Authorized Access radio button successfully ");
						Thread.sleep(2000);
						
						driver.findElement(By.xpath("//span[text()='Yes']")).click();
						
						//span[text()='Yes']

						editAccountDetails.clk_LaunchBtn();
						logger.log(LogStatus.PASS, "Clicked on Launch button successfully ");
						Thread.sleep(4000);

						
					
						
						
						
						
						
						/*AuthorizedUsrpage.clk_EditBtn();
						Thread.sleep(2000);
						logger.log(LogStatus.INFO, "Clicked on Edit button");
						
			          //Verify that the Customer is present and 'Yes' option is selected in Is Customer Present selection 
						
						if(driver.findElement(By.xpath("//span[text()='Is the customer present?']")).isDisplayed()){
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "Yes option is selected in Is Customer present ");
							logger.log(LogStatus.INFO, "Image", image);
						} else {
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Yes option is not selected in Is Customer present  ");
							logger.log(LogStatus.INFO, "Image", image);
							if (resultFlag.equals("pass"))
								resultFlag = "fail";
						}
						
						*/

						AuthorizedUsers_IsCustomerPresent AuthorizedUsers_IsCustomerPresent = new AuthorizedUsers_IsCustomerPresent(
								driver);

						/*AuthorizedUsers_IsCustomerPresent.clk_YESRdButton();

						logger.log(LogStatus.INFO, "Clicked on YES radio button in 'Is Customer present' popup");

						AuthorizedUsers_IsCustomerPresent.clk_ContButton();
						Thread.sleep(6000);
						logger.log(LogStatus.INFO, "Clicked on continue button");*/
						
						
						/*try {
			                String scpath = Generic_Class.takeScreenShotPath();
			                Reporter.log(scpath, true);
			                String image = logger.addScreenCapture(scpath);
			                logger.log(LogStatus.INFO, "Handling Pop Up");
			                logger.log(LogStatus.INFO, "Pop Handled", image);
			                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			                Thread.sleep(4000);
			          } catch (Exception e) {
			                String scpath = Generic_Class.takeScreenShotPath();
			                Reporter.log(scpath, true);
			                String image = logger.addScreenCapture(scpath);
			                logger.log(LogStatus.INFO, "Pop Up Unavailable");
			                logger.log(LogStatus.INFO, "Pop Up Not Available", image);
			          }

						*/
						
						
						
						
						
						
						
						

						AuthorizedUsers authUsers = new AuthorizedUsers(driver);
						authUsers.clk_Clear();
						Thread.sleep(4000);
						
						
						
						
						
						
						
						
						
						
						
						
						// Enter First name
						
						
						String data = Generic_Class.get_RandmString();
						//int randomNum = 1400 + (int)(Math.random() * 1600); 
						String FirstName="Robert"+data +"Watson";
						
						Thread.sleep(3000);
						
						
						String data1 = Generic_Class.get_RandmString();
						String LastName=data1+"Mandela";
						
						Thread.sleep(3000);
						
						int LineNo = 8000 + (int) (Math.random() * 1600);
						String LineNumber = Integer.toString(LineNo);

						
						
						
						
						
						
						
						
						
						
						authUsers.enter_FirstName(FirstName);
						logger.log(LogStatus.PASS, "First Name Entered Successfully and Name is : " + FirstName);

						// Enter Last name
						authUsers.enter_LastName(LastName);
						logger.log(LogStatus.PASS, "Last Name Entered Successfully : " + LastName);

						// Enter Phone No
						
						authUsers.selectPhoneType(driver, tabledata.get("PhoneType"));
						Thread.sleep(2000);
						logger.log(LogStatus.PASS, "Phone Type Selected Successfully");
						
						
						authUsers.enter_PhoneAreaCode(tabledata.get("AreaCode"));
						logger.log(LogStatus.PASS, "AreaCode Entered Successfully :" + tabledata.get("AreaCode"));
						authUsers.enter_PhoneExchange(tabledata.get("Exchange"));
						logger.log(LogStatus.PASS, "Exchange Entered Successfully :" + tabledata.get("Exchange"));

						authUsers.enter_PhoneLineNumber(LineNumber);
						logger.log(LogStatus.PASS, "LineNumber Entered Successfully :" + LineNumber);
						
						authUsers.clk_SpaceCheckBox();
						
						
						
						
						
						
						EmergncyContctpage emerContact = new EmergncyContctpage(driver);
						
						//mohana
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

						emerContact.click_ConfirmBtn();
						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(1));
					
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(6000);
						logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");

						WebElement signature = driver
								.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
						Actions actionBuilder = new Actions(driver);
						Action drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
								.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
						drawAction.perform();
						Thread.sleep(6000);
						
						Thread.sleep(3000);
						driver.switchTo().window(tabs.get(0));
					
						Thread.sleep(8000);
						driver.switchTo().window(tabs.get(1));
						
						Thread.sleep(6000);
						signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
						actionBuilder = new Actions(driver);
						drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
								.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
						drawAction.perform();
						Thread.sleep(4000);
						

			           String snap=Generic_Class.takeScreenShotPath();
						String irm=logger.addScreenCapture(snap);	
						logger.log(LogStatus.INFO, "img",irm);



						
						
						Thread.sleep(6000);
						driver.findElement(By.xpath("//button[text()='Accept']")).click();

						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(0));
						
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(3000);

						emerContact.click_ApproveBtn();
						
						Thread.sleep(3000);

						boolean isapprv = emerContact.isApproved();
						if (isapprv) {
							String scpath1 = Generic_Class.takeScreenShotPath();
							String image1 = logger.addScreenCapture(scpath1);
							logger.log(LogStatus.PASS, "Signature approved message displayed after clicking on approve button");
							logger.log(LogStatus.INFO, "", image1);
						} else {
							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Signature approved message not displayed after clicking on approve button");
							logger.log(LogStatus.INFO, "Image", image);
							if (resultFlag.equals("pass"))
								resultFlag = "fail";
						}
						emerContact.click_SaveBtn();
						
						Thread.sleep(8000);

						EmergencyContact_EmployeeIdPage empIdPage = new EmergencyContact_EmployeeIdPage(driver);
						empIdPage.enter_EmployeeId(tabledata.get("UserName"));
						logger.log(LogStatus.PASS, "when Save button is clicked, it displays the  Employee ID modal window ");
						Thread.sleep(4000);
						
						  snap=Generic_Class.takeScreenShotPath();
							 irm=logger.addScreenCapture(snap);	
							logger.log(LogStatus.INFO, "img",irm);



						
						
						
						empIdPage.click_ContinueBtn();
						Thread.sleep(10000);
						
						
						

						try {
			              
			                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click(); 
			                Thread.sleep(8000);
			          } catch (Exception e) {
			              
			          }

						

						logger.log(LogStatus.PASS,
								"Navigated to customer dashboard after entering the employee id and clicking on continue button");
						
						
						
						
						cust_accdetails.clickSpaceDetails_tab();
						
						Thread.sleep(8000);
						
						//mohana
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
					
						Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
						Thread.sleep(3000);
						
						
						String msg1=driver.findElement(By.xpath("//div[@class='modal-content ']/div[@class='text-align-left clearfix-container double-padding']/div[1]")).getText();
						Thread.sleep(2000);
						

						if (AuthorizedUsrpage.verify_AuthorizedAccessPopupIsDisplayed()) {

							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "Authorized access contacts added in Customer dashboard and Contact Name is  :"  +  msg1);
							logger.log(LogStatus.INFO, "Image", image);
						}

						else {
							Thread.sleep(8000);
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Authorized access contacts not  added in Customer dashboard ");
							logger.log(LogStatus.INFO, "Image", image);

						}
						
						
						Thread.sleep(4000);
						
						driver.findElement(By.xpath("//a[contains(text(),'Close')]")).click();
						
						Thread.sleep(4000);
						
						
						
						
						
						
						
						//Click on Account activities tab
						cust_accdetails.click_AccountActivities();
						Thread.sleep(25000);
				
						
						//Verify that a transaction should be created in the Account Activities section of the Customer Account Dashboard, 
						
			            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			            Date date = new Date();
			            String CurrentDate = sdf.format(date);                
			            List<WebElement> resultRows = driver.findElements(By.xpath("//div[@id='activities-grid']//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr"));
			              
			            int rowSize = resultRows.size();
			            for(int i=1; i<=rowSize; i++){
			                  
			                  String Date = resultRows.get(i-1).findElement(By.xpath("./td[2]")).getText();
			                  System.out.println("date"+Date);
			                 
			                
			              
			              if(Date.equals(CurrentDate)){
			               
			                  String Description = resultRows.get(i-1).findElement(By.xpath("./td[7]")).getText();
			                  if(Description.contains("Authorized access info changed ")){
			                	  
			                	  
			                        logger.log(LogStatus.PASS, "Transaction  created successfully in Account activities :"+Description);
			                      
			                        break;}
			                  
			                  }
			                  else{
			                        if(resultFlag.equals("pass"))
			                          resultFlag="fail";    
			                        
			                           logger.log(LogStatus.FAIL, "Transaction not  created successfully in Account activities :");
			                          
			                         }
			                  }
			            
			            
			            snap=Generic_Class.takeScreenShotPath();
						 irm=logger.addScreenCapture(snap);	
						logger.log(LogStatus.INFO, "img",irm);

			            
			            
			            
			            
			           
						  
			            
			                   // Verify that the changes to authorized access information shall be
			         			// saved to the database 
			         			String query12 = "select count(*) from contact where firstName='" + FirstName + "'";
			         			String Record = DataBase_JDBC.executeSQLQuery(query12);
			         			Thread.sleep(2000);

			         			int totalRecord = Integer.parseInt(Record);
			         			if (totalRecord > 0) {
			         				logger.log(LogStatus.PASS,
			         						"The changes to authorized access information for Firstname are saved in the database and the FirstName is : " + FirstName );
			         			}

			         			Thread.sleep(2000);
			         			String query1 = "select count(*) from contact where Lastname='" + LastName + "'";
			         			String Record1 = DataBase_JDBC.executeSQLQuery(query1);
			         			Thread.sleep(2000);

			         			int totalRecord1 = Integer.parseInt(Record1);
			         			if (totalRecord1 > 0) {
			         				logger.log(LogStatus.PASS,
			         						"The changes to authorized access information for Lastname are saved in the database and the LastName is : " + LastName );
			         			}
			         			
			         			logger.log(LogStatus.PASS, "Contact updated with the values entered  " );
			         			
			         
			         			
			         	      //Verify the tables CLTransactionMaster and CLActivity 
			         			
			         			
			         			String query2 = " select top 1 accountid from CLTransactionMaster order by lastupdate desc";
			         			String AccountID = DataBase_JDBC.executeSQLQuery(query2);
			         			Thread.sleep(5000);
			         			
			         						         			
			         			if (AccountID.equals(AccountIDDB)) {
			         				logger.log(LogStatus.PASS,
			         						"Listed tables  updated with the values entered for Authorised user in CLTransactionMaster ");
			         			}
			         			
			         			else{
			         				logger.log(LogStatus.FAIL,
			         						"Listed tables not  updated with the values entered for Authorised user ");
			         			}
			         			
			         			
			         			
			         			Thread.sleep(6000);	
						
					
						
						//Code for deleting the Authorized users
						//driver.switchTo().window(tabs.get(0));
						
						
						Thread.sleep(9000);
						
						cust_accdetails.clickSpaceDetails_tab();
						Thread.sleep(9000);
						//mohana
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
						Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
						
						Thread.sleep(5000);
						AuthorizedUsrpage.clk_EditBtn();
						Thread.sleep(4000);
					

					

						AuthorizedUsers_IsCustomerPresent.clk_YESRdButton();

						Thread.sleep(3000);

						AuthorizedUsers_IsCustomerPresent.clk_ContButton();
						Thread.sleep(6000);
						
						
						
						
						
						/*try {
			                String scpath = Generic_Class.takeScreenShotPath();
			                Reporter.log(scpath, true);
			                String image = logger.addScreenCapture(scpath);
			           
			                logger.log(LogStatus.INFO, "img", image);
			                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			                Thread.sleep(4000);
			          } catch (Exception e) {
			                String scpath = Generic_Class.takeScreenShotPath();
			                Reporter.log(scpath, true);
			                String image = logger.addScreenCapture(scpath);
			             
			                logger.log(LogStatus.INFO, "img", image);
			          }
						
						*/
					
						
						
						
						
						
						
						authUsers.clk_Clear();
						Thread.sleep(4000);
						
						//mohana
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
						
						emerContact.click_ConfirmBtn();
						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(1));
					
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(6000);
						logger.log(LogStatus.PASS, "New/updated information is sent to the CFS for customer approval");
						
						WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
						Actions actionBuilder1 = new Actions(driver);          
						Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
								.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
						drawAction1.perform();
						Thread.sleep(6000);
						
						
						


			               snap=Generic_Class.takeScreenShotPath();
						  irm=logger.addScreenCapture(snap);	
						logger.log(LogStatus.INFO, "img",irm);
						
						driver.findElement(By.xpath("//button[text()='Accept']")).click();
						
						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(0));
						driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(3000);
						emerContact.click_ApproveBtn();
					
						Thread.sleep(3000);
						
						boolean isapprv1 = emerContact.isApproved();
						
						
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.INFO, "Image",image);
						emerContact.click_SaveBtn();
						Thread.sleep(8000);
						
						//EmergencyContact_EmployeeIdPage empIdPage = new EmergencyContact_EmployeeIdPage(driver);
						empIdPage.enter_EmployeeId(tabledata.get("UserName"));
						
						scpath=Generic_Class.takeScreenShotPath();
						image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.INFO, "Image",image);
						empIdPage.click_ContinueBtn();
						Thread.sleep(30000);
						

						try {
			              
			                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			                Thread.sleep(8000);
			          } catch (Exception e) {
			              
			          }

						
						
						
						
				      cust_accdetails.clickSpaceDetails_tab();
						
						Thread.sleep(8000);
						
						//mohana
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
						
						Acc_SpaceDetailsPage.clk_AuthorisedUsersLink();
						Thread.sleep(3000);
						
						
						
						String msg2=driver.findElement(By.xpath("//div[contains(text(),'No authorized access contacts.')]")).getText();
						Thread.sleep(5000);
						
						
						if (AuthorizedUsrpage.verify_AuthorizedAccessPopupIsDisplayed()) {

							
							logger.log(LogStatus.PASS, "Authorized access contacts Deleted from Customer dashboard  and the message is :"  +  msg2);
							
						}

						else {
							
							logger.log(LogStatus.FAIL, "Authorized access contacts not  Deleted from Customer dashboard ");
							

						}
						
						Thread.sleep(2000);
						
						
					
						snap=Generic_Class.takeScreenShotPath();
						  irm=logger.addScreenCapture(snap);	
						logger.log(LogStatus.INFO, "img",irm);
					
						
						
						
						
					}

					catch (Exception ex) {
						resultFlag = "fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
						logger.log(LogStatus.FAIL, "------------- page is not displayed"+ ex);
						ex.printStackTrace();
					}

				}


	

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Add_Delete_AuthorisedUser_Business", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Add_Delete_AuthorisedUser_Business", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Add_Delete_AuthorisedUser_Business", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}
	
	
	
}
