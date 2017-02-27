package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

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
import Pages.CustDashboardPages.EmergncyContctpage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class CustomerEdit_Business_UpdateAddress extends Browser_Factory {
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	 String scpath="";
	  String image="";

	@DataProvider
	public Object[][] getCustomerEdit() 
	{
		return Excel.getCellValue_inlist(path, "CustomerEdit","CustomerEdit", "CustomerEdit_Business_UpdateAddress");
	}
	

	@Test(dataProvider="getCustomerEdit")
	public void CustomerEditBusinessAddress(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{
			
			// Login to PS Application
						logger = extent.startTest("CustomerEdit_Business_UpdateAddress", "Customer Edit Business Update - Address");
						testcaseName = tabledata.get("TestCases");
						Reporter.log("Test case started: " + testcaseName, true);

						testcaseName = tabledata.get("TestCases");
						Reporter.log("Test case started: " + testcaseName, true);

						LoginPage login = new LoginPage(driver);
						login.login(tabledata.get("UserName"), tabledata.get("Password"));
						logger.log(LogStatus.INFO, "User logged in successfully as PM");

						Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);

						String biforstNum = Bifrostpop.getBiforstNo();

						Reporter.log(biforstNum + "", true);

						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_T);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_T);
						Thread.sleep(5000);

						ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
						Reporter.log(tabs.size() + "", true);
						driver.switchTo().window(tabs.get(1));
						Thread.sleep(2000);

driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));

						List<WebElement> biforstSystem = driver.findElements(
								By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
						for (WebElement ele : biforstSystem) {
							if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
								Reporter.log(ele.getText() + "", true);
								ele.click();
								break;
							}
						}
						driver.switchTo().window(tabs.get(0));
						// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						Thread.sleep(10000);
						driver.findElement(By
								.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"))
								.click();
						Thread.sleep(5000);

						PM_Homepage pmhomepage = new PM_Homepage(driver);
						

						pmhomepage.clk_AdvSearchLnk();
						logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
						Thread.sleep(5000);
						
						Advance_Search advSearch = new Advance_Search(driver);
						String location = advSearch.getLocationNum();
						
						
						  String query = "Select Top 1 A.accountnumber " +
								  "From AccountOrderItem AOI " +
								  "INNER JOIN Site S ON S.SiteID = AOI.SiteID " +
								  "INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID "
								  + "INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID "
								  + "INNER JOIN Account A ON A.AccountID = AO.AccountID " +
								  "join rentalunit ru on ru.rentalunitid=soi.rentalunitid " +
								  "join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "
								  + "Where soi.VacateDate is null " +
								  "and soi.vacatenoticedate is null " + "and s.sitenumber='" +
								  location + "' " + "and soi.StorageOrderItemTypeID=4301 " +
								  "group by  A.AccountID,A.accountnumber, S.SiteID, SOI.vacatedate, SOI.rentalunitid, s.sitenumber, SOI.vacatenoticedate "
								  + "having sum(clt.amount + clt.discountamount)>0 " +
								  "order by 1 ";
								
								 String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
						
						
						
								 advSearch.enterAccNum(AccountNumber);
						
						
						
						//advSearch.enterAccNum(tabledata.get("AccountNumber"));
						logger.log(LogStatus.INFO, "Account number entered successfully :" + AccountNumber);
						((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
						Thread.sleep(5000);
						advSearch.clickButton();
						Thread.sleep(5000);
						

						// Verify Tabs are present in Customer Account dashboard
						Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
					
						if (AccDetails.verify_CustomerInfoTab()) {

							Thread.sleep(2000);
							
							logger.log(LogStatus.PASS, "Customer Info tab is displayed successfully");
							
						}

						else {
							Thread.sleep(2000);
							
							logger.log(LogStatus.FAIL, "Customer Info tab is not displayed");
							
						}

						if (AccDetails.verify_SpaceDetailsTab()) {

							
							logger.log(LogStatus.PASS, "Space Details tab is displayed successfully");
							
						}

						else {
							Thread.sleep(2000);
							
							logger.log(LogStatus.FAIL, "Space Details tab is not displayed");
							
						}

						if (AccDetails.verify_AccountActivitiesTab()) {

							
							logger.log(LogStatus.PASS, "Account Activities tab is displayed successfully");
							
						}

						else {
							Thread.sleep(2000);
							
							logger.log(LogStatus.FAIL, "Account Activities tab is not displayed");
							
						}

						if (AccDetails.verify_DocumentsTab()) {

							
							logger.log(LogStatus.PASS, "Documents tab is displayed successfully");
						
						}

						else {
							Thread.sleep(2000);
							
							logger.log(LogStatus.FAIL, "Documents tab is not displayed");
							
						}
						
						Thread.sleep(3000);

			        

						
						//Getting Address Before Update
						String addressBeforeUpdate = driver.findElement(By.xpath("//div[@class='addresses-container clearfix-container']/div[contains(text(),' Address:')]//following-sibling::div/div/div")).getText();
						
						
						
						   String snap=Generic_Class.takeScreenShotPath();
							String irm=logger.addScreenCapture(snap);
							logger.log(LogStatus.INFO, "Address Before Update is :"  + addressBeforeUpdate );
							logger.log(LogStatus.INFO, "img",irm);
							
						
						
						//div[@class='addresses-container clearfix-container']/div[contains(text(),' Address:')]//following-sibling::div/div/div
						
						

						Thread.sleep(3000);

						// Click on Edit Account Details Link
						((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
						Thread.sleep(5000);
						Cust_AccDetailsPage detailpage = new Cust_AccDetailsPage(driver);
					
						detailpage.clk_EditAccDetails();
						logger.log(LogStatus.INFO, "Clicked on Edit Account Details Button");
						Thread.sleep(8000);

						// "Edit Account Details" modal window should be displayed

						EditAccountDetails editpage = new EditAccountDetails(driver);

						if (editpage.isEditAccDetailsPopUpDisplayed()) {

							
							logger.log(LogStatus.PASS, "Edit Account Details page is displayed successfully");
							
						} else {

							logger.log(LogStatus.FAIL, "Edit Account Details page is not displayed");
							
						}

						if (editpage.isSelectWorkflowtxtDisplayed()) {
							logger.log(LogStatus.PASS,
									"Select Workflow list text is dispalyed sucessfully in edit account deatils page");
						} else {
							logger.log(LogStatus.FAIL, "Select Workflow list text is not dispalyed  in edit account deatils page");
						}

						
						
						
						
						String sn=Generic_Class.takeScreenShotPath();
						String ir=logger.addScreenCapture(sn);	
						logger.log(LogStatus.INFO, "img",ir);


						
						
						
						
						

						// Click on Customer Information Radio button
						Cust_EditAccountDetailsPage editdetails = new Cust_EditAccountDetailsPage(driver);
					
						editdetails.clickCustInfoRadioBtn();
						logger.log(LogStatus.INFO, "Clicked on Customer Information Radio button");
						Thread.sleep(3000);

						// Verify Yes and No button displayed in the screen
						if (editdetails.isDisplayedYesRadioBtn()) {
						
							logger.log(LogStatus.PASS, "Yes radio button is displayed");
							
						} else {

							logger.log(LogStatus.FAIL, "Yes radio button is not displayed");
							

						}

						if (editdetails.isDisplayedNoRadioBtn()) {
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "No radio button is displayed");
							logger.log(LogStatus.INFO, "No radio button is displayed", image);
						} else {

							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							if (resultFlag.equals("pass"))
								resultFlag = "fail";
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "No radio button is not displayed");
							logger.log(LogStatus.INFO, "No radio button is not displayed", image);

						}

						// Clicking on Yes radio button and Click Launch button
						editdetails.clickYesRadioBtn();
						Thread.sleep(3000);
						logger.log(LogStatus.INFO, "Clicked on Yes Radio button");
						editdetails.clickLaunchBtn();
						logger.log(LogStatus.INFO, "Clicked on Yes Radio button and  Launch Button successfully");
						Thread.sleep(6000);
						
						
					
						
						// Customer Information" screen should be displayed

						if (driver.findElement(By.xpath("//h3[text()='Customer Information']")).isDisplayed()) {
							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "Customer Information screen displayed successfully");
							logger.log(LogStatus.INFO, "Customer Information screen", image);
						} else {

							String scpath = Generic_Class.takeScreenShotPath();
							Reporter.log(scpath, true);
							if (resultFlag.equals("pass"))
								resultFlag = "fail";
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "Customer Information screen displayed ");
							logger.log(LogStatus.INFO, "Customer Information screen displayed ", image);

						}

						// Editing details in Address field
						
				
						driver.findElement(By.xpath("//input[@id='BusinessInformationModel_ContactPosition']")).clear();
						Thread.sleep(4000);
						driver.findElement(By.xpath("//input[@id='BusinessInformationModel_ContactPosition']")).sendKeys("QAManager");
					
						
						

						Cust_CustomerInfoPage infopage = new Cust_CustomerInfoPage(driver);
						Actions act=new Actions(driver);
					
						/*try{
						if(driver.findElement(By.xpath("//span[@class='field-validation-valid identWrongFormatMessage wrong-id-format']")).isDisplayed()){
							
							
							driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[2]")).click();
							Thread.sleep(7000);
							
							try
					        {  
								
								WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
					            Actions dragger = new Actions(driver);
					            // drag downwards
					           // int numberOfPixelsToDragTheScrollbarDown = 10;
					            for (int i = 50; i > 0; i--)
					            {
					               // dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release(webelement).build().perform();
					            	dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -50).release().build().perform();
			                        Thread.sleep(2000);

					            }
					            Thread.sleep(500);
					          
					            
					            //dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release(webelement).build().perform();
				            	dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -300).release().build().perform();
			                    Thread.sleep(5000);    
					            
					            
					            
					            
					        }
					        catch (Exception e)
					        {
					            e.printStackTrace();
					         
					        }
							
							
							
							
							Thread.sleep(6000);
							
							List<WebElement> stateList = driver.findElements(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_StateTypeID_listbox']/li"));
							
							WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
				            Actions dragger = new Actions(driver);
				            // drag downwards
							
							for(int j=1;j<stateList.size();j++){
								WebElement stzate=driver.findElement(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_StateTypeID_listbox']/li["+j+"]"));
								act.moveToElement(stzate).build().perform();
								System.out.println(" the name are---"+stzate.getText());
								
								if(stzate.getText().equals("CA")){
									//act.moveToElement(stzate).click().build().perform();
									stzate.click();
									break;
								}else{
										dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, 5).release().build().perform();
				                        Thread.sleep(2000);
										Thread.sleep(200);
						            
								}
							}
							
							
							
							
							driver.findElement(By.xpath("//input[@id='IndividualInformationModel_ContactIdentification_EncryptedItem_EncryptedIdDisplay']")).clear();
							Thread.sleep(3000);
							driver.findElement(By.xpath("//input[@id='IndividualInformationModel_ContactIdentification_EncryptedItem_EncryptedIdDisplay']")).sendKeys("L1234595");
						}
						
						
						}
						
						
						catch(Exception ex){
							
						}
						*/
						try{
						
						if(driver.findElement(By.xpath("//span[@class='field-validation-valid identWrongFormatMessage wrong-id-format']")).isDisplayed()){
							
							/*driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[4]")).click();
							Thread.sleep(4000);
							
							
				List<WebElement> stateList = driver.findElements(By.xpath("//ul[@id='Addresses_OfficialAddress_State_listbox']/li"));
							
							//WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
				            Actions dragger = new Actions(driver);
				            // drag downwards
							
							for(int j=1;j<stateList.size();j++){
								WebElement stzate=driver.findElement(By.xpath("//ul[@id='Addresses_OfficialAddress_State_listbox']/li["+j+"]"));
								act.moveToElement(stzate).build().perform();
								System.out.println(" the name are---"+stzate.getText());
								
								if(stzate.getText().equals("CA")){
									//act.moveToElement(stzate).click().build().perform();
									stzate.click();
									break;
								}else{
										dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, 5).release().build().perform();
				                        Thread.sleep(2000);
										Thread.sleep(200);
						            
								}
							}
							*/
							
							
							
							driver.findElement(By.xpath("//input[@id='BusinessInformationModel_IdentificationNumber']")).clear();
							Thread.sleep(3000);
							driver.findElement(By.xpath("//input[@id='BusinessInformationModel_IdentificationNumber']")).sendKeys("L1234596");	
							
							
							
						}
							
						}
						
						
                       catch(Exception ex){
							
						}         
						
							
						
						
						
						
						
						Thread.sleep(6000);
						String data = Generic_Class.get_RandmString();
						int randomNum = 1400 + (int)(Math.random() * 1600); 
						String address="701 Western Ave";
						
						Thread.sleep(3000);
						
						
						String data1 = Generic_Class.get_RandmString();
						String address2="HouseNo10"+data1 ;
						
						Thread.sleep(3000);
						
						
					

						//infopage.enterAddress(tabledata.get("Address1"));
						
						infopage.enterAddress(address);
						
						Thread.sleep(2000);
						//infopage.enterAddress2(address2);
						logger.log(LogStatus.INFO, "Address Updated  successfully " + address );
						Thread.sleep(2000);

						// Clicking on Verify button and Confirm with customer

						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
						Thread.sleep(3000);

						//infopage.clk_NoEmailBtn();
						
						String dataEmail= Generic_Class.get_RandmString();
						
						String Email=dataEmail+"psitqa@gmail.com";
						
						
						
						infopage.enterEmail(Email);
						Thread.sleep(5000);
						
						infopage.clk_verifyBtn();
						Thread.sleep(5000);
						
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
						Thread.sleep(4000);
						
						infopage.clk_verifyBtn();
						Thread.sleep(5000);
						
						EmergncyContctpage emerContact = new EmergncyContctpage(driver);
						
						Thread.sleep(6000);
						try{
							driver.findElement(By.xpath("//div[@class='verificationElement verification-failed margin']//a[contains(text(),'Select')]")).click();
							Thread.sleep(4000);
							
							}catch(Exception e){
								
							}
						try{
							emerContact.click_VerifyBtn();
							Thread.sleep(2000);
						}catch(Exception ex){
							
						}
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
						Thread.sleep(4000);
						
						
						
						
						//driver.findElement(By.xpath("//a[text()='Select']")).click();
						
						

					
						infopage.clk_ConfirmBtn();
						logger.log(LogStatus.INFO, "Clicked on Confirm with customer button");
						Thread.sleep(8000);
						driver.switchTo().window(tabs.get(1));
						Thread.sleep(8000);
						//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
						logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
						Thread.sleep(6000);
						
						

						// Collect Signature and click on Accept button
						
						
						String snap1=Generic_Class.takeScreenShotPath();
						String irm1=logger.addScreenCapture(snap1);	
						logger.log(LogStatus.INFO, "img",irm1);
						
						
						
						
						WebElement signature = driver
								.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
						Actions actionBuilder = new Actions(driver);
						Action drawAction = actionBuilder.moveToElement(signature, 660, 96).click().clickAndHold(signature)
								.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature).build();
						drawAction.perform();
						Thread.sleep(8000);
						
						
						//button[@class='psbutton-priority vertical-center floatright approve-button double-margin-right']

						driver.findElement(By.xpath("//button[@class='psbutton-priority vertical-center floatright approve-button double-margin-right']")).click();
						
						//driver.findElement(By.xpath("//button[contains(text(),'Accept')]")).click();
						logger.log(LogStatus.INFO, "Clicked on Accept button Successfully");
						Thread.sleep(6000);
						driver.switchTo().window(tabs.get(0));
						Thread.sleep(6000);
						//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
					
						Thread.sleep(8000);
						
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0,2000)");
						Thread.sleep(3000);
						
						
						

						// Click Approve button
						infopage.clk_ApproveBtn();
						Thread.sleep(2000);
					
						infopage.clk_SaveBtn();
						Thread.sleep(5000);
						logger.log(LogStatus.INFO, "Clicked on Approve button and Save button Successfully");

						// Enter Employee Number and click on Ok button
						TransactionCompletePopup transPopup = new TransactionCompletePopup(driver);
					
						Thread.sleep(3000);
						transPopup.enterEmpNum(tabledata.get("UserName"));
					
						Thread.sleep(6000);
						transPopup.clickContinueBtn();
						logger.log(LogStatus.PASS, "Entered EmpID and Clicked on Continue button successfully");
						Thread.sleep(40000);
						
						//Need to Handle Alert
						
			try{
							
						
							
						driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
						
						}
						
						catch(Exception ex){
							ex.getMessage();
						}
						
						
					
			       Thread.sleep(15000);
						
						
						
						String addressAfterUpdate = driver.findElement(By.xpath("//div[@class='addresses-container clearfix-container']/div[contains(text(),' Address:')]//following-sibling::div/div/div")).getText();
						
						Thread.sleep(15000);
						
						if(addressBeforeUpdate.equals(addressAfterUpdate)){
							
						
								logger.log(LogStatus.FAIL, "Address not Updated  : Address Before Update is :" + addressBeforeUpdate  +"Address After update is : " + addressAfterUpdate );
								String scpath = Generic_Class.takeScreenShotPath();
								String image = logger.addScreenCapture(scpath);
								logger.log(LogStatus.INFO, "Image", image);
							} else {
								logger.log(LogStatus.PASS, "Address  Updated successfully in Customer Dashboard : Address Before Update is :" + addressBeforeUpdate  +"Address After update is : " + addressAfterUpdate );
								String scpath = Generic_Class.takeScreenShotPath();
								String image = logger.addScreenCapture(scpath);
								logger.log(LogStatus.INFO, "Image", image);
								if (resultFlag.equals("pass"))
									resultFlag = "fail";
							}
						
						Thread.sleep(15000);
						
						

					       Calendar cal = Calendar.getInstance();
					                     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
					                     String strTodaysDate = df.format(cal.getTime());

			        
					                     
					                     Thread.sleep(25000);
					                     
					                     // Validating Account activities Tab
					                     
					                    
										
						

						Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
						cust_accdetails.click_AccountActivities();
						logger.log(LogStatus.INFO, " Clicked on Account Activities Tab successfully");
						Thread.sleep(15000);

					
						
						//div[@id='activities-grid']//table/tbody//tr/td[text()='01/23/2017']/following-sibling::td[text()='Customer Information Change']
						
						
						if(driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Customer Information Change']")).size()!=0)
			            {
			                  
			                  logger.log(LogStatus.PASS, "Customer Information Change is displayed in Account Activities Tab");
			                
			                 Thread.sleep(4000); 
			                 
			                  scpath=Generic_Class.takeScreenShotPath();
			                  image=logger.addScreenCapture(scpath);
			                  
			                  logger.log(LogStatus.INFO, "Image",image);

			            }else{

			                  scpath=Generic_Class.takeScreenShotPath();
			                  image=logger.addScreenCapture(scpath);
			                  logger.log(LogStatus.FAIL, "Customer Information Change is not  displayed in Account Activities Tab");
			                  logger.log(LogStatus.INFO, "Image",image);
			                  if(resultFlag.equals("pass"))
			                         resultFlag="fail";
			            }

						
						
					
						
						driver.findElement(By.xpath("//span[text()='Documents']")).click();
						Thread.sleep(25000);
						
						 logger.log(LogStatus.INFO, "Clicked on Document tab");
						
						
						 
						 if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Contact Information Changed')]")).size()!=0)
				            {
				                  
				                  logger.log(LogStatus.PASS, "Customer Information Change  is displayed in Document tab");
				                
				                 Thread.sleep(4000); 
				                 
				                  scpath=Generic_Class.takeScreenShotPath();
				                  image=logger.addScreenCapture(scpath);
				                  
				                  logger.log(LogStatus.INFO, "Image",image);

				            }else{

				                  scpath=Generic_Class.takeScreenShotPath();
				                  image=logger.addScreenCapture(scpath);
				                  logger.log(LogStatus.FAIL, "Customer Information Change  is not  displayed in Document tab");
				                  logger.log(LogStatus.INFO, "Image",image);
				                  if(resultFlag.equals("pass"))
				                         resultFlag="fail";
				            }

						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						
						
						/*String description=driver.findElement(By.xpath("//div[@id='documents-grid']//table/tbody/tr[1]/td[3]")).getText();
						if(description.contains("Official Contact Information Changed")){
						 
							 logger.log(LogStatus.PASS, "Change customer Info Document is displayed in Document tab section  :"+description);
				            
				            snap=Generic_Class.takeScreenShotPath();
							 irm=logger.addScreenCapture(snap);	
							logger.log(LogStatus.INFO, "img",irm);
						
						}
						
						 else{
			                 if(resultFlag.equals("pass"))
			                   resultFlag="fail";  
			                 scpath=Generic_Class.takeScreenShotPath();
			                 image=logger.addScreenCapture(scpath);
			                 
			                    logger.log(LogStatus.FAIL, "Change customer Info Document is not displayed  in Document tab section:");
			                    logger.log(LogStatus.INFO, "Image",image);
			                  }
						*/
				            
				          

						

						// Verify in the customer Dashboard with the database queries

					       String sqlQuery = "select addr.addressline1, addr.addressline2, city, statecode, postalcode from account a "+
			                       "join accountaddress aa on aa.accountid =  a.accountid "+
			                       "join address addr on addr.addressid = aa.addressid "+
			                       "where a.accountid = '"+AccountNumber+"' and isofficial = 1";
			          ArrayList<String> dbAddress = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			          
			         String addressfromDB = dbAddress.get(0);
			         if(address.equals(addressfromDB)){
			        	 logger.log(LogStatus.PASS, " Address is updated in DB : Updated Address is : " +  addressfromDB); 
			         }
			         else{
			        	 logger.log(LogStatus.FAIL, " Address is not updated in DB ");
			         }
			       


					} catch (Exception ex) {
						resultFlag = "fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
						logger.log(LogStatus.FAIL, "------------- page is not displayed"+ ex);
						ex.printStackTrace();
					}
				}
				
				
				

	
	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Business_UpdateAddress" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Business_UpdateAddress" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","CustomerEdit_Business_UpdateAddress" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}




}
