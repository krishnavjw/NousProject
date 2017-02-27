package Pages.EmailPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage {

	public ContactPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']/span[text()='Contact']")
	private WebElement header_Contact;
	
	@FindBy(xpath="//div[@id='SelectEmailDialog']/div[contains(@class,'address-book-grid')]//table/tbody/tr[1]/td/a")
	private WebElement expand_ContactButton;
	
	@FindBy(xpath="//div[@id='SelectEmailDialog']//button[text()='Save']")
	private WebElement save_button;
	
	
	public boolean exists_ContactHeader(){
		return header_Contact.isDisplayed();
	}
	
	public void click_ExpandContactButton(){
		expand_ContactButton.click();
	}
	
	public void click_SaveBtn(){
		save_button.click();
	}
}
