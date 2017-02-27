package Scenarios.PropertyManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.media.webkitAudioContext;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.PropertyManagementPages.Edit_NonCustomerPropertyAccesspage;
import Pages.PropertyManagementPages.PropertyAccessManagementPopUp;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Pages.PropertyManagementPages.ViewNon_CustomerPropertyAccessInfoPage;
import Pages.PropertyManagementPages.ViewPropertyMgmtPage;
import Scenarios.Browser_Factory;

public class PropertyManagement_Edit_NonCustomerPropertyAccess extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  "PropertyManagement_Edit_NonCustomerPropertyAccess");
	}



	@Test(dataProvider="getLoginData",priority=1)
	public void propertyManagement_Edit_NonCustomerPropertyAccess(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PropertyManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("PropertyManagement_Edit_NonCustomerPropertyAccess","Property Management Edit noncustomer property access ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
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
			Thread.sleep(5000);
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

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			// Login into PM dashboard 
			PM_Homepage pm_home= new  PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			Thread.sleep(8000);
			pm_home.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");

			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "property management page is displayed ",image);

			Thread.sleep(4000);
			PropertyManagementPage propmgmt= new PropertyManagementPage(driver);
			propmgmt.clickPropAccessMgmt();
			logger.log(LogStatus.INFO,"Clicked on Property Access Mgmt");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "property Access management page is displayed ",image);



			Thread.sleep(8000);
			PropertyAccessManagementPopUp propAccess= new PropertyAccessManagementPopUp(driver);
			propAccess.clickViewPropAccessCodes();
			logger.log(LogStatus.INFO,"Selected View Prop Access Codes radio button");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Selected View property access codes radio button ",image);


			propAccess.clickLaunch_btn();
			logger.log(LogStatus.INFO,"Clicked on Launch button");

			Thread.sleep(5000);

			ViewPropertyMgmtPage viewprop= new ViewPropertyMgmtPage(driver);
			viewprop.clickAccessStatus();
			Thread.sleep(1000);

			viewprop.clickAccessStatus_Active();
			logger.log(LogStatus.INFO,"Selected Active in Access status dropdown");

			viewprop.click_non_customersonly_chkbx();
			logger.log(LogStatus.INFO,"Selected non-customers only");

			viewprop.click_viewAllAccCodes_link();
			logger.log(LogStatus.INFO,"Clicked on view all Access codes link successfully");
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "All the records were displayed ",image);

			//==========================================
			//String gatecode=viewprop.get_GateCode();
		//	System.out.println(gatecode);

			String varname1 = ""
					+ "select case when cg.disableaccess=1 then 'Inactive' else 'Active' end as ActiveStatus, "
					+ "case when cu.customertypeid in (90,91,5056,92) then 'Customer' else 'Non Customer' end as CustomerType,c.lastname, c.firstname, "
					+ "Cu.CompanyName, Ru.rentalunitnumber as Space, Cg.Gatecode, GC.Description as AccessType, GK.Description as AccessZone, gs.siteid "
					+ "from contact c with(nolock) "
					+ " "
					+ "join contactgate cg with(nolock) on cg.contactid=c.contactid "
					+ "left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid "
					+ "left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid "
					+ "left join gatesystem GS with(nolock) on gs.gatesystemid=gc.gatesystemid "
					+ "left join customer cu on cu.contactid=c.contactid "
					+ "left join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid "
					+ "left join productsite PS on ps.productsiteid=ru.productsiteid "
					+ " "
					+ "where 1=1 "
					+ "and gs.siteid=2211 "
					/*+ "--and ru.rentalunitnumber='I960' "
					+ "--and c.lastname like 'johnson%' "*/
				//	+ "and cg.gatecode='"+gatecode+"' "
					+ "and cu.customertypeid not in (90,91,5056,92)";


			ArrayList<String> lst=DataBase_JDBC.executeSQLQuery_List(varname1);

			String status=lst.get(0);
			String customertype=lst.get(1);
			String name=lst.get(2)+","+lst.get(3);
			String company=lst.get(4);

			for(int i=0;i<4;i++){
			List<WebElement> data=driver.findElements(By.xpath("//div[@id='accessItems-grid']//table//tbody/tr[1]/td"));
			if(((data.get(0)).equals(status)) && ((data.get(2)).equals(customertype) ) && ((data.get(3)).equals(name)) &&((data.get(4)).equals(company))){
				System.out.println(status+ "" +customertype+ "" +name);
				
			}
			
			}
			logger.log(LogStatus.INFO, "Before Editing the info --name: "+ name +"Customer type: "+customertype+ " status :" +customertype+ "and Company :"+company);	

			
			//===========================================
			Thread.sleep(1000);

			viewprop.click_view_link();
			logger.log(LogStatus.INFO,"Clicked on view  link successfully");
			Thread.sleep(10000);

			ViewNon_CustomerPropertyAccessInfoPage view= new ViewNon_CustomerPropertyAccessInfoPage(driver);
			if(view.verify_page_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "ViewNon_CustomerPropertyAccessInfoPage modal window is displayed ");
				logger.log(LogStatus.INFO, "ViewNon_CustomerPropertyAccessInfoPage modal window is displayed ",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "ViewNon_CustomerPropertyAccessInfoPage modal window is not displayed ");
				logger.log(LogStatus.INFO, "ViewNon_CustomerPropertyAccessInfoPage modal window is not displayed ",image);

			}


			view.click_edit_btn();
			logger.log(LogStatus.INFO,"Clicked on edit button");
			Thread.sleep(1000);

			Edit_NonCustomerPropertyAccesspage edit=new Edit_NonCustomerPropertyAccesspage(driver);

			if(edit.verify_page_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit_NonCustomerPropertyAccesspage info is displayed ");
				logger.log(LogStatus.INFO, "Edit_NonCustomerPropertyAccesspage info is displayed ",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit_NonCustomerPropertyAccesspage info is not displayed ");
				logger.log(LogStatus.INFO, "Edit_NonCustomerPropertyAccesspage info is not displayed ",image);

			}




			/*if((edit.getFirstname().equals(firstname)) && (edit.getLastname().equals(lastname))){
				 logger.log(LogStatus.PASS, "first and last names are same");
			}else{
				 logger.log(LogStatus.FAIL, "first and last names are not same ");
			}

			logger.log(LogStatus.INFO, "Firstname :" +firstname+"and Lastname :"+lastname+ "before edit Customer info");
			 */

			edit.clearfname();
			edit.enterFirstName(tabledata.get("FirstName"));
			Thread.sleep(1000);
			edit.clearLastname();
			edit.enterLastName(tabledata.get("LastName"));
			Thread.sleep(1000);
			edit.clearcmpnyname();
			edit.enterCompanyName(tabledata.get("CompanyName"));
			Thread.sleep(1000);
			edit.clearemail();
			edit.enterEmail(tabledata.get("Email"));
			Thread.sleep(1000);

			edit.enter_employeeId(tabledata.get("EmployeeId"));
			Thread.sleep(1000);

			logger.log(LogStatus.INFO,"Edited the non customer property access info");

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "After editing info",image);

			edit.click_save();
			logger.log(LogStatus.INFO,"saved the non customer property access info");

			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "User navigated to property management",image);

			Thread.sleep(20000);
			
			viewprop.clickAccessStatus();
			Thread.sleep(1000);
			viewprop.clickAccessStatus_Active();
			logger.log(LogStatus.INFO,"Selected Active in Access status dropdown");
			
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			
			Thread.sleep(2000);
			viewprop.enterFirstName(tabledata.get("FirstName"));
			viewprop.enterCompanyName(tabledata.get("CompanyName"));
			viewprop.clickSearch_btn();
			Thread.sleep(3000);
			
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			
			
			
			//String gatecode=viewprop.get_GateCode();
			Thread.sleep(10000);
			String varname2 = ""
					+ "select case when cg.disableaccess=1 then 'Inactive' else 'Active' end as ActiveStatus, "
					+ "case when cu.customertypeid in (90,91,5056,92) then 'Customer' else 'Non Customer' end as CustomerType,c.lastname, c.firstname, "
					+ "Cu.CompanyName, Ru.rentalunitnumber as Space, Cg.Gatecode, GC.Description as AccessType, GK.Description as AccessZone, gs.siteid "
					+ "from contact c with(nolock) "
					+ " "
					+ "join contactgate cg with(nolock) on cg.contactid=c.contactid "
					+ "left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid "
					+ "left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid "
					+ "left join gatesystem GS with(nolock) on gs.gatesystemid=gc.gatesystemid "
					+ "left join customer cu on cu.contactid=c.contactid "
					+ "left join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid "
					+ "left join productsite PS on ps.productsiteid=ru.productsiteid "
					+ " "
					+ "where 1=1 "
					+ "and gs.siteid=2211 "
					/*+ "--and ru.rentalunitnumber='I960' "
					+ "--and c.lastname like 'johnson%' "*/
				//	+ "and cg.gatecode='"+gatecode+"' "
					+ "and cu.customertypeid not in (90,91,5056,92)";


			ArrayList<String> lst1=DataBase_JDBC.executeSQLQuery_List(varname2);
			Thread.sleep(10000);

			String status1=lst1.get(0);
			String customertype1=lst1.get(1);
			String name1=lst1.get(2)+","+lst1.get(3);
			String company1=lst1.get(4);

			for(int i=0;i<4;i++){
			List<WebElement> data=driver.findElements(By.xpath("//div[@id='accessItems-grid']//table//tbody/tr[1]/td"));
			if(((data.get(0)).equals(status1)) && ((data.get(2)).equals(customertype1) ) && ((data.get(3)).equals(name1)) && ((data.get(4)).equals(company1))){
				System.out.println(status1+ "" +customertype1+ "" +name1);
				
			}}

			logger.log(LogStatus.INFO, "After Editing the info --name: "+ name1 +"Customer type: "+customertype1+ "and status :" +customertype1+ "and Company :"+company1);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			
			
			logger.log(LogStatus.INFO, "After Editing the info ,changesreflected in database  ");


		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			//logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Edit_NonCustomerPropertyAccess" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Edit_NonCustomerPropertyAccess" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Edit_NonCustomerPropertyAccess" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}


}
