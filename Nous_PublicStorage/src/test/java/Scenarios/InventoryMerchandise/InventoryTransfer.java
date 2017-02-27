package Scenarios.InventoryMerchandise;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.ConfirmMerchandiseTransferPage;
import Pages.InventoryMerchandise.MerchandiseTransferPage;
import Pages.InventoryMerchandise.PropertyManagementPage;
import Scenarios.Browser_Factory;

public class InventoryTransfer extends Browser_Factory
{
	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise",  "InventoryTransfer");
	}


	@Test(dataProvider="getData")
	public void InventoryMerchandise(Hashtable<String, String> tabledata) throws InterruptedException 
	{



		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("InventoryMerchandise").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{
			logger=extent.startTest("InventoryTransfer", "InventoryTransfer ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			JavascriptExecutor jse = (JavascriptExecutor) driver;

			LoginPage login= new LoginPage(driver);
			String sitenumber=login.get_SiteNumber();

			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");

			Thread.sleep(6000);
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			//driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));  
			driver.get("http://wc2aut.ps.com/CustomerScreen/Mount"); 
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


			PM_Homepage pmHome=new PM_Homepage(driver);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			pmHome.clickmanageProp();
			logger.log(LogStatus.INFO, "Clicked on Manage Property link");
			Thread.sleep(6000);

			PropertyManagementPage propMgmt= new PropertyManagementPage(driver);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			propMgmt.clickInventoryTrans();
			logger.log(LogStatus.INFO, "Clicked on Inventory Transfer link");
			Thread.sleep(10000);
			MerchandiseTransferPage merTrans= new MerchandiseTransferPage(driver);

			if(merTrans.verifyMerchanideTitle())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Merchandise Transfer page is displayed successfully");
				logger.log(LogStatus.PASS, "Image",image);

			}else{
				if(resultFlag=="pass")
					resultFlag="fail";
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Image",image);
			}

			if(merTrans.verifyTransferTofield())
			{
				logger.log(LogStatus.PASS, "Transfer To Location drop down is displayed");
			}else{
				logger.log(LogStatus.FAIL, "Transfer To Location drop down is displayed");
			}

			merTrans.clickTransferTodropdown();
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Actions act = new Actions(driver);

			List<WebElement> transToOptions= driver.findElements(By.xpath("//ul[@id='InventoryTransfer_TransferTo_listbox']/li[@class='k-item']"));
			String transOption="";
			for(WebElement option : transToOptions)
			{
				act.moveToElement(option).build().perform();
				transOption= transOption + "," + option.getText().trim();
			}

			logger.log(LogStatus.PASS, " List of all properties within the district are "+ transOption);

			Thread.sleep(3000);

			for(WebElement option : transToOptions)
			{
				System.out.println("text is "+ option.getText());
				System.out.println(option.getText().length());
				act.moveToElement(option).build().perform();
				Thread.sleep(3000);
				if(option.getText().length()!=0)
				{
					option.click();
					break;
				}
			}

			Thread.sleep(4000);
			String propNum= merTrans.getPropertValue();


			Thread.sleep(4000);
			String query="Select P.ProductNumber"
					+" From Product P"
					+" Join ProductSite PS on PS.ProductID=P.ProductID"
					+" join site s on s.siteid=ps.siteid"
					+" Where 1=1"
					+" And S.Sitenumber='"+propNum+"'"
					+" And P.ProductTypeID=46"
					+" And PS.QuantityOnHand <>0"
					+" order by p.ProductNumber asc";


			ArrayList<String> productNumbers  =DataBase_JDBC.executeSQLQuery_List(query);
			Thread.sleep(4000);

			driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
			Thread.sleep(4000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			List<WebElement> productLists= driver.findElements(By.xpath("//div[@id='ProductNumber-list']/ul[@id='ProductNumber_listbox']/li[@class='k-item']"));

			if(productLists.size()==productNumbers.size())
			{
				logger.log(LogStatus.PASS, "Number of products in DB "+productNumbers.size()+" and Number product in Dropdown "+productLists.size() +"are Same ");
			}else{
				logger.log(LogStatus.FAIL, "Number of products in DB "+productNumbers.size()+" and Number product in Dropdown "+productLists.size() +"are not Same ");
			}


			int cnt=0;
			String productnum= "";
			for(WebElement product: productLists)
			{
				String prodName= product.getText().trim();
				for(int i=0 ; i<productNumbers.size(); i++)
				{
					if(prodName.contains(productNumbers.get(i)))
					{
						logger.log(LogStatus.PASS, " ProductID/SKU's  "+prodName+" is available in DB n UI");
						cnt++;
						break;

					}

				}
				if(cnt==4)
				{
					break;
				}
			}

			logger.log(LogStatus.PASS, " Add and Delete buttons are disable");
			Thread.sleep(4000);
			String productndet="";
			for(WebElement product: productLists)
			{
				productndet=product.getText().trim();

				product.click();
				break;
			}
			Thread.sleep(4000);
			String[] prodName=productndet.split(" ");
			productnum=prodName[0];
			System.out.println(productnum);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			if(driver.findElements(By.xpath("//table/tbody//tr//td/a[text()='Add']")).size()!=0)
			{
				logger.log(LogStatus.PASS, " Add button is Available");
			}
			Thread.sleep(2000);

			//String product= productNumbers.get(0);
			String xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td[3]";
			String lineItemNum= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Line Item Number is starting with  : "+ lineItemNum );
			Thread.sleep(2000);

			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td[5]";
			String itemName= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Item Name : "+ itemName + " is automatically populated in Item Name column");


			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td[6]";

			String quantityOnHand= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Quantity on Hand  : "+ quantityOnHand + " is automatically populated in Quantity on Hand column");

			Thread.sleep(4000);


			Thread.sleep(2000);
			//merTrans.clickSubmitbtn();
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td/a[text()='Add']";
			driver.findElement(By.xpath(xpath)).click();

			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//div[@class='modal-content ']//div[contains(text(),'Quantity must be greater than 0 ')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Alert meassge : Quantity must be greater than 0 & less than 1000 for all Line Items");
				logger.log(LogStatus.PASS, "Image",image);
				Thread.sleep(3000);

				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			}else{
				logger.log(LogStatus.FAIL, "Alert message is not displayed");
			}
			Thread.sleep(4000);
			int quantitysent= Integer.parseInt(quantityOnHand);

			quantitysent=quantitysent+2;
			Thread.sleep(2000);

			//csdfdsfs
			driver.findElement(By.xpath("//table/tbody//tr/td[text()='01']/following-sibling::td[contains(text(),'"+productnum+"')]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
			Thread.sleep(5000);
			productLists= driver.findElements(By.xpath("//div[@id='ProductNumber-list']/ul[@id='ProductNumber_listbox']/li[@class='k-item']"));

			for(WebElement product: productLists)
			{
				productndet=product.getText().trim();

				product.click();
				break;
			}
			Thread.sleep(4000);
			prodName=productndet.split(" ");
			productnum=prodName[0];
			System.out.println(productnum);
			Thread.sleep(2000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/..//td//span/input[@class='k-formatted-value k-input']";			
			//driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(2000);

			for(int i=1 ; i<=quantitysent;i++)
			{
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);
			}


			//driver.findElement(By.xpath(xpath)).sendKeys(Integer.toString(quantitysent));
			Thread.sleep(4000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered  more quantity integer value in the Quantity Sent field "+ quantitysent);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(2000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/..//td[5]";			
			driver.findElement(By.xpath(xpath)).click();
			
			
			
			WebElement add = driver.findElement(By.xpath("//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td/a[text()='Add']"));
			jse.executeScript("arguments[0].click();", add);
			
			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//span[@id='window_wnd_title']")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				String message = driver.findElement(By.xpath("//div[@id='window']//span[contains(text(),'We have ')]")).getText();
				logger.log(LogStatus.PASS, "Alert meassge :"+ message);
				logger.log(LogStatus.PASS, "Image",image);
				Thread.sleep(3000);

			}else{
				logger.log(LogStatus.FAIL, "Alert message is not displayed");
			}
			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).size()!=0)
			{
				driver.findElement(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).click();
			}
			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@id='window']//button[@id='noButton']")).click();
			logger.log(LogStatus.PASS, "Clicked on No button");
			Thread.sleep(4000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(4000);
			 add = driver.findElement(By.xpath("//table/tbody//tr//td[contains(text(),'"+productnum+"')]/../td/a[text()='Add']"));
			jse.executeScript("arguments[0].click();", add);
			logger.log(LogStatus.PASS, "Clicked on Add button");
			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).size()!=0)
			{
				driver.findElement(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).click();
			}
			Thread.sleep(4000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			driver.findElement(By.xpath("//div[@id='window']//button[@id='yesButton']")).click();
			logger.log(LogStatus.PASS, "Clicked on Yes button");

			Thread.sleep(4000);
			
			//2rd record

			driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
			Thread.sleep(4000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			List<WebElement> productLists1= driver.findElements(By.xpath("//div[@id='ProductNumber-list']/ul[@id='ProductNumber_listbox']/li[@class='k-item']"));
			int x=1;
			for(WebElement product: productLists1)
			{
				if(x==2)
				{
					act.moveToElement(product).build().perform();
					Thread.sleep(2000);
					productndet=product.getText().trim();

					product.click();
					break;
				}
				x++;
			}
			Thread.sleep(4000);
			String[] prodName1=productndet.split(" ");
			String productnum1=prodName1[0];
			System.out.println(productnum1);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			Thread.sleep(4000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td[6]";

			quantityOnHand= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Quantity on Hand  : "+ quantityOnHand + " is automatically populated in Quantity on Hand column");
			Thread.sleep(4000);
			quantitysent= Integer.parseInt(quantityOnHand);

			//quantitysent=quantitysent+2;
			Thread.sleep(2000);


			for(int i=1; i<=quantitysent;i++)
			{
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);

			}

			/*xpath="//div[@id='transfer-grid']//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td[7]//span/input[1]";
			driver.findElement(By.xpath(xpath)).sendKeys(String.valueOf(quantitysent));*/

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(2000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/..//td[5]";			
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(2000);
			
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td/a[text()='Add']";
			driver.findElement(By.xpath("//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td/a[text()='Add']")).click();
			logger.log(LogStatus.PASS, "Clicked on Add button");
			
			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).size()!=0)
			{
				driver.findElement(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).click();
			}
			Thread.sleep(4000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(4000);
			//3rd record
			
			if(driver.findElements(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).size()!=0)
			{
				driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
				Thread.sleep(4000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Image",image);
				
			}else{
				
				driver.findElement(By.xpath("//table/tbody//tr//td[text()='03']/following-sibling::td[1]")).click();
				Thread.sleep(4000);
				driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
				Thread.sleep(4000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Image",image);
			}
			
			Thread.sleep(4000);
			
			 productLists1= driver.findElements(By.xpath("//div[@id='ProductNumber-list']/ul[@id='ProductNumber_listbox']/li[@class='k-item']"));
			int y=1;
			for(WebElement product: productLists1)
			{
				if(y==3)
				{
					productndet=product.getText().trim();

					product.click();
					break;
				}
				y++;
			}
			Thread.sleep(4000);
			prodName1=productndet.split(" ");
			 productnum1=prodName1[0];
			System.out.println(productnum1);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			Thread.sleep(4000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td[6]";

			quantityOnHand= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Quantity on Hand  : "+ quantityOnHand + " is automatically populated in Quantity on Hand column");
			Thread.sleep(4000);
			quantitysent= Integer.parseInt(quantityOnHand);

			//quantitysent=quantitysent+2;
			Thread.sleep(2000);


			for(int i=1; i<=quantitysent;i++)
			{
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);

			}
			
			Thread.sleep(4000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			
			Thread.sleep(2000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/..//td[5]";			
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td/a[text()='Delete']")).click();
			logger.log(LogStatus.PASS, "Clicked on Delete button");
			Thread.sleep(4000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(4000);
			
			add = driver.findElement(By.xpath("//table/tbody//tr//td/a[text()='Add']"));
			jse.executeScript("arguments[0].click();", add);
			
			Thread.sleep(4000);
			if(driver.findElements(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).size()!=0)
			{
				driver.findElement(By.xpath("//div[contains(text(),'Use of the back button is disabled in WebChamp.')]/../../following-sibling::div//a[contains(text(),'OK')]")).click();
			}
			Thread.sleep(4000);
			
			if(driver.findElements(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).size()!=0)
			{
				driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
				Thread.sleep(4000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Image",image);
				
			}else{
				
				driver.findElement(By.xpath("//table/tbody//tr//td[text()='03']/following-sibling::td[1]")).click();
				Thread.sleep(4000);
				driver.findElement(By.xpath("//table/tbody//tr//td//span[text()='Select Product']")).click();
				Thread.sleep(4000);
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Image",image);
			}
			
			Thread.sleep(4000);

			 productLists1= driver.findElements(By.xpath("//div[@id='ProductNumber-list']/ul[@id='ProductNumber_listbox']/li[@class='k-item']"));
		     y=1;
			for(WebElement product: productLists1)
			{
				if(y==3)
				{
					productndet=product.getText().trim();

					product.click();
					break;
				}
				y++;
			}
			Thread.sleep(4000);
			prodName1=productndet.split(" ");
			 productnum1=prodName1[0];
			System.out.println(productnum1);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);

			Thread.sleep(4000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum1+"')]/../td[6]";

			quantityOnHand= driver.findElement(By.xpath(xpath)).getText();
			logger.log(LogStatus.PASS, " Quantity on Hand  : "+ quantityOnHand + " is automatically populated in Quantity on Hand column");
			Thread.sleep(4000);
			quantitysent= Integer.parseInt(quantityOnHand);

			//quantitysent=quantitysent+2;
			Thread.sleep(2000);


			for(int i=1; i<=quantitysent;i++)
			{
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);

			}
			
			Thread.sleep(4000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			Thread.sleep(2000);
			xpath="//table/tbody//tr//td[contains(text(),'"+productnum+"')]/..//td[5]";			
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
			Thread.sleep(2000);
			merTrans.clickSubmitbtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button");
			Thread.sleep(6000);
			
			ConfirmMerchandiseTransferPage merTranspop= new ConfirmMerchandiseTransferPage(driver);
			
			if(merTranspop.verifyConfirmMerTitle())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirm Merchandise Transfer PopUp is displayed successfully");
				logger.log(LogStatus.PASS, "Image",image);

			}else{
				if(resultFlag=="pass")
					resultFlag="fail";
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Image",image);
			}
			
			Thread.sleep(2000);
			merTranspop.enterNoteTxt("Test");
			logger.log(LogStatus.PASS, "Entered Note successfully");
			
			Thread.sleep(2000);
			merTranspop.enterEmpld(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "Entered Employee Id successfully");
			
				
			Thread.sleep(2000);
			merTranspop.clickConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm button  successfully");
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Image",image);
			

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Image",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);

		}
	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryTransfer" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryTransfer" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","InventoryTransfer" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();

	}
}
