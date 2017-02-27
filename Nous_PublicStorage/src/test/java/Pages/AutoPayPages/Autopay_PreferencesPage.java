package Pages.AutoPayPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Autopay_PreferencesPage {
	
	@FindBy(xpath="//table[@role='grid']/tbody/tr/td[5]/a")
	private WebElement Approve;
	
	@FindBy(linkText="Cancel")
	private WebElement cancel_btn;
	
	@FindBy(xpath="//div[@id='notes-header']/h3[@class='floatleft']")
	private WebElement pageTitle;
	
	public Autopay_PreferencesPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickCancelbtn()
	{
		cancel_btn.click();
	}
	
	public String getPageTitle(){
		return pageTitle.getText();
		
	}
}
