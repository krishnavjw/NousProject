package Pages.CustInfoPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManagePropertySpaceAccess {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//h3[text()='Manage Property/Space Access']")
	private WebElement pageTitle_ManageSpace;
	
	
	
	@FindBy(xpath = "//a[contains(text(),'Save')]")
	private WebElement save_Btn;
	
	

	
	public ManagePropertySpaceAccess(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}

	public boolean isDisplayed_pageTitle_ManageSpace() {
		return pageTitle_ManageSpace.isDisplayed();
	}
	
	
	
	public void select_AccessZone() throws InterruptedException{
		driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[1]")).click();
		Thread.sleep(3000);
		List<WebElement> listAccessZone = driver.findElements(By.xpath("//ul[@id='SpacesWithGateInformation[0]_Gates[0]_TimeZoneID_listbox']/li"));
		listAccessZone.get(1).click();
		
		
		
	}
	
	
	
	public void select_KeypadZone() throws InterruptedException{
		driver.findElement(By.xpath("(//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'])[2]")).click();
		Thread.sleep(3000);
		List<WebElement> KeypadZone = driver.findElements(By.xpath("//ul[@id='SpacesWithGateInformation[0]_Gates[0]_KeyPadDescription_listbox']/li"));
		KeypadZone.get(3).click();
		
		
		
	}
	
	
	
	public void click_save_Btn(){
		save_Btn.click();
	}
	
	
	
	
	
	
}
