package Pages.AuctionWorkBench;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddAvailableUnits {

	@FindBy(xpath="//span[contains(text(),'Add Available Units')]")
	private WebElement pageTitle;
	
	@FindBy(xpath="//button[@id='AvailableUnitbtnSave']")
	private WebElement saveBtn;
	
	public void click_SaveBtn(){
		saveBtn.click();
	}
	
	@FindBy(id="AvailableUnitbtnSave")
	private WebElement save_btn;
	
	@FindBy(id="AvailableUnitbtnCancel")
	private WebElement cancel_btn;
	
	@FindBy(xpath="//div[@id='AvailableUnitgrid']//table//tbody//tr[1]/td[1]/input[@class='availableunits']")
	private WebElement chkbox;
	
	@FindBy(xpath="//div[@id='AvailableUnitgrid']//table//tbody//tr[1]/td[2]")
	private WebElement spacenum;
	
	public AddAvailableUnits(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_pageTitle(){
		return pageTitle.isDisplayed();
	}
	
	public boolean verify_save(){
		return save_btn.isDisplayed();
	}
	
	public boolean verify_cancel(){
		return cancel_btn.isDisplayed();
	}
	
	public void click_chkbox(){
		chkbox.click();
	}
	
	
	public void click_save_btn(){
		save_btn.click();
	}
	
	public String get_Spacenum(){
		return spacenum.getText();
	}

	
	
	
}
