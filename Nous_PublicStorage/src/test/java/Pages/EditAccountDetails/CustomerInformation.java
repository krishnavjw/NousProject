package Pages.EditAccountDetails;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerInformation {

	
	@FindBy(xpath="(//div[@id='lesseeEmailAddresses']/h3)[1]")
	private WebElement officialEmailAddress_txt;

	@FindBy(xpath="//div[@id='lesseeEmailAddresses']//div[@class='unofficial-button js-unofficial-container']/button")
	private WebElement AddUnofficialEmailAddress_Btn;
	
	
	@FindBy(xpath="//div[@id='lesseeEmailAddresses']/h3[starts-with(text(),'Unofficial')]")
	private WebElement UnofficialEmailAddress_Txt;
	
	

	@FindBy(xpath="//ul[@id='officialEmailList']/li[@class='dynListElement']//div//a/span[@class='icon list-add']")
	private WebElement  AddButton;
	
	
	
	
	
	
	public CustomerInformation(WebDriver driver){

		PageFactory.initElements(driver, this);
	}
	
	
	
	public boolean officialEmailAddress_txtDisplayed() {

		return officialEmailAddress_txt.isDisplayed();
	}
	
	
	
	
	
	public boolean UnofficialEmailAddress_TxtDisplayed() {

		return UnofficialEmailAddress_Txt.isDisplayed();
	}
	
	
	
	
	
	
	
	
	
	
}
