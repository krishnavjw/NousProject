package Pages.AuctionWorkBench;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleAuctionPage {

	private WebDriver driver;;
	
	public ScheduleAuctionPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@id='ScheduleAuctionMW']//label[@class='webchamp-radio-button']/span[text()='PM']/preceding-sibling::span[@class='button']")
	private WebElement PM_radio_btn;
	
	@FindBy(xpath="//button[@id='btnSave']")
	private WebElement saveBtn;
	
	public void click_PMRadio(){
		PM_radio_btn.click();
	}
	
	public void click_SaveBtn(){
		saveBtn.click();
	}
	
}
