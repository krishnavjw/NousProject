package Scenarios;

import java.awt.AWTException;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

public class Test {
	
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		
		WebDriver driver=null;
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver","./src/main/resources/BrowserDrivers/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://way2automation.com/demo.html");
		
		
		Thread.sleep(5000);
		Actions act= new Actions(driver);
		Thread.sleep(1000);
		act.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Interaction')]"))).perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[contains(text(),'Draggable')]")).click();
		Thread.sleep(3000);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		Reporter.log(tabs.size() + "", true);
		driver.switchTo().window(tabs.get(1));
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='name']")).sendKeys("staging");
		
		driver.findElement(By.xpath("//div[@id='sidebar-wrapper']//a[contains(text(),'Add')]")).click();
		
		
	}

}
