package GenericMethods;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Generic_Class 
{

	//WebDriver driver;
	public static void SelectCheckbox(WebDriver driver, String label)
	{
		String xpath="//span[text()='"+label+"']/following-sibling::span[@class='button']";
		driver.findElement(By.xpath(xpath)).click();		

	}

	public static void SelectRadiobtn(WebDriver driver, String label)
	{
		String xpath="//span[text()='"+label+"']/following-sibling::span[@class='button']";
		driver.findElement(By.xpath(xpath)).click();		

	}

	//read data from Property file

	public static String getPropertyValue(String propKey) 
	{

		Properties config = null;
		try {
			config = new Properties();
			FileInputStream ip = new FileInputStream("./src/main/resources/Resources/config.properties");

			config.load(ip);
		} catch (Exception e) {


		}

		return config.getProperty(propKey);

	}

	//-----------------------------------------------------------------------------------------------
	//Method : takeScreenShot
	//Description : This method is used to capture the screen shot 
	//Author : Testing Team

	public static String takeScreenShotPath(){

		String current = System.getProperty("user.dir");

		String scrPath=".\\src\\main\\resources\\ScreenShots\\"+"screenshot_"+System.currentTimeMillis()+".jpg";


		try {
			Robot rob=new Robot();
			Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRect=new Rectangle(d);
			BufferedImage img=rob.createScreenCapture(screenRect);

			ImageIO.write(img, "jpg", new File(scrPath));

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return current+(scrPath.replaceFirst(".", ""));

	}


	/*This method is used to get the double value.
	 * Input:String val
	 * Output:Double val
	 * Author:Automation Team
	 */

	public static int getAmount(String inputValue) {

		String addValue = "";

		char[] c = inputValue.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ',' || c[i]=='$') {
				c[i] = ' ';
			} else if (Character.getNumericValue(c[i]) >= 0 && Character.getNumericValue(c[i]) <= 9 || c[i] == '.') {
				addValue = addValue + c[i];
			} else {
				System.out.println("Not able to handle");
			}
		}
		// return Double.parseDouble(addValue);

		BigDecimal bd = new BigDecimal(Double.parseDouble(addValue));
		bd = bd.setScale(2, RoundingMode.HALF_UP);

		int val = (int)bd.doubleValue();
		return val;


	}
	
	
	
	
	public static double getDoubleAmount(String inputValue) {

        String addValue = "";

        char[] c = inputValue.toCharArray();
        for (int i = 0; i < c.length; i++) {
               if (c[i] == ',' || c[i] == '$') {
                     c[i] = ' ';
               } else if (Character.getNumericValue(c[i]) >= 0 && Character.getNumericValue(c[i]) <= 9 || c[i] == '.'
                            || c[i] == '-') {
                     addValue = addValue + c[i];
               } else {
                     System.out.println("Not able to handle");
               }
        }
        return Double.parseDouble(addValue);

 }




//-----------------------------------------------------------------------------------------------
//Method : input
//Description : Method inputs data to a text field.
//Author : Testing Team

public static void input(WebElement element, String inputValue,WebDriver driver){



	new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(element));		
	element.sendKeys(inputValue);		

}




//-----------------------------------------------------------------------------------------------
//Method : clickButton
//Description : Method clicks on clickable elements in page.
//Author : Testing Team

public static void click(WebElement element,WebDriver driver){


	new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(element));	
	element.click();

}	

//-----------------------------------------------------------------------------------------------
//Method : mouseOver
//Description : Method clicks on clickable elements in page.
//Author : Testing Team

//		public static void mouseOver(WebElement element,WebElement element1,WebDriver driver){
//
//			
//				
//				Actions act = new Actions(driver);
//				act.moveToElement(element).build().perform();
//				act.moveToElement(element1).click().perform();		
//
//
//		}

//-----------------------------------------------------------------------------------------------
//Method : Read text 
//Description : Method clicks on clickable elements in page.
//Author :Testing Team 

public static String readText_Webelement(WebElement element, WebDriver driver){


	new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(element));
	return element.getText();

}


//-----------------------------------------------------------------------------------------------
//Method : selectDropdown
//Description : Method to select required option in the dropdown.
//Author :Testing Team 
public static void list_SelVisbleText(WebElement element, String inputValue){


	Select option= new Select(element);
	option.selectByVisibleText(inputValue.trim());


}		

//-----------------------------------------------------------------------------------------------
//Method : selectDropdown
//Description : Method to select required option in the dropdown.
//Author : Testing Team
public static  void list_SelByIndex(WebElement element, int index){


	Select option= new Select(element);
	option.selectByIndex(index);;


}	


//-----------------------------------------------------------------------------------------------
//Method : selectDropdown
//Description : Method to select required option in the dropdown.
//Author : Testing Team
public static void list_SelByVal(WebElement element, String inputValue){


	Select option= new Select(element);
	option.selectByValue(inputValue);


}	

//-----------------------------------------------------------------------------------------------
//Method : verifyMessages
//Description : Method to verify any message.
//Author : Testing Team
public static void verifyMessages(WebElement element, String inputValue){

	//soft Assert
	Assert.assertEquals(inputValue, element.getText());

}	


//-----------------------------------------------------------------------------------------------
//Method : refreshPage
//Description : Method to search anything in search field.
//Author : 

public static void refreshPage(WebDriver driver){

	driver.getCurrentUrl();

}



//-----------------------------------------------------------------------------------------------
//Method : windowHandler
//Description : Method to switch between browser windows.
//Author : Testing Team

public static void windowHandler(WebDriver driver,WebElement element,String inputvalue){




}


//-----------------------------------------------------------------------------------------------
//Method : convertIntToString
//Description : Method to covert int to string.
//Author : Testing Team

public static void convertIntToString(WebElement element, int inputValue){


	//element.sendKeys(inputValue.toString());
}


//--------------------------------------------------------------------------------------------------------
//Method : clearTextFields
//Description : Method to clear text in input field.
//Author : Testing Team


public static void clearTextFields(WebElement element){

	element.clear();

}


//------------------------------------------------------------------------------------------------------------------------
//Method : checkAndClick
//Description : Method to check functionality and click if needed
//Author : Testing Team

public static void checkAndClick(WebElement element,WebDriver driver,String inputValue){



	new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(element));

	String value=element.getText();
	if(value.equalsIgnoreCase(inputValue)){

		element.click();


	}
}


//-----------------------------------------------------------------------------------------------
//Method : uploadFile
//Description : Method inputs data to a upload a file.
//Author : Testing Team
//Date : 

public static void uploadFile(WebElement element,WebDriver driver,String inputValue){


	new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(element));

	element.sendKeys(inputValue);

}

//-----------------------------------------------------------------------------------------------
//Method : unSelCheckBox
//Description : Deselct only checked checkbox
//Author : Testing Team
//Date :

//		public static void unSelCheckBox(WebElement element){
//			
//			
//			
//			if(element.isSelected()){
//				element.click();
//			}
//			
//			
//		}


//-----------------------------------------------------------------------------------------------
//Method : acceptAlert
//Description : Handling Alert popup
//Author : Testing Team
//Date :

public static void acceptAlert(WebDriver driver){

	driver.switchTo().alert().accept();



}

//-----------------------------------------------------------------------------------------------
//Method : cancelAlert
//Description : Handling Alert popup
//Author : Testing Team
//Date :

public static void cancelAlert(WebDriver driver){

	driver.switchTo().alert().dismiss();

}

//-----------------------------------------------------------------------------------------------
/*Method :getAlertText
		Description : Used to get the text from alert window
		 Author : Testing Team
	     Date :
 */
public static void getAlertText(WebDriver driver){

	driver.switchTo().alert().getText();




}

//-----------------------------------------------------------------------------------------------
/*Method :getAlertText
		Description : Used to scroll down based on the x and y axis
		Author : Testing Team
		Date :
 */

// This will scroll down the page 
/*		
	public void scroll_Down(){

		 JavascriptExecutor js = (JavascriptExecutor)driver;
	 js.executeScript("scroll(0,350)");

	}

 */
//-----------------------------------------------------------------------------------------------
/*Method :UploadFile
	Description : This method is used to upload the file using Robot class 
	Author : Testing Team
	Date :
 */

public static void uploadFileUsingrobot(String filepath){

	// Specify the file location with extension
	StringSelection selection = new StringSelection(filepath);

	// Copy to clipboard
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,null);

	try {
		// Create object of Robot class
		Robot robot = new Robot();
		Thread.sleep(1000);

		// Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_V);

		// Release CTRL+V  
		robot.keyRelease(KeyEvent.VK_CONTROL);


		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		// press Enter      
		robot.keyPress(KeyEvent.VK_ENTER);

		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);

	} catch (Exception e) {

		e.getMessage();
	}

}

/**
 * 
 * Basavaraj/Rekha
 */
/*public void Page_ScrollDown()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}*/


/*	 
	 public String getPageURL()
	 {
	  String URL = driver.getCurrentUrl();
	  return URL;
	 }

 */

public static String GetCurrentDateAndTime() throws InterruptedException {


	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("ddMMYYYYssss",Locale.getDefault());
	String strTodaysDate = df.format(cal.getTime());
	Reporter.log("strTodaysDate: "+ strTodaysDate,true);
	return strTodaysDate;

}


public static String get_RandmString() {


	char[] chars = "AUTTest".toCharArray();
	StringBuilder sb = new StringBuilder();
	Random random = new Random();
	for (int i = 0; i < 6; i++) {
		char c = chars[random.nextInt(chars.length)];
		sb.append(c);
	}

	return sb.toString();
}




//=========fetch Ip address of the machine========================= 

public static String getIPAddress() {
	String s = null;
	try {
		InetAddress address = InetAddress.getLocalHost();
		String ipaddr = address.toString();
		String[] spStr = ipaddr.split("/");
		s = spStr[1].trim();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return s;
}

/*This method is used to get the double value.
 * Input:String val
 * Output:Double val
 * Author:Automation Team
 */

public static double getAmountDouble(String inputValue) {

		String addValue = "";

		char[] c = inputValue.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ',' || c[i]=='$') {
				c[i] = ' ';
			} else if (Character.getNumericValue(c[i]) >= 0 && Character.getNumericValue(c[i]) <= 9 || c[i] == '.') {
				addValue = addValue + c[i];
			} else {
				System.out.println("Not able to handle");
			}
		}
		 return Double.parseDouble(addValue);
/*
		BigDecimal bd = new BigDecimal(Double.parseDouble(addValue));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		
		double val = (double)bd.doubleValue();
		return val;*/
		

	}

public static String getCurrentDate(){
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd",Locale.getDefault());
	String strTodaysDate = df.format(cal.getTime());
	Reporter.log("strTodaysDate: "+ strTodaysDate,true);
	return strTodaysDate;
}


}
