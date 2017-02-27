package Pages.EmailPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InboxPage {

	public InboxPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[@id='newmessage']")
	private WebElement createNewMessage;
	
	public void click_CreateNewMessage(){
		createNewMessage.click();
	}
	
	
}
