package Pages.AWB;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MoveUnitPage {
	private WebDriver driver;
	
	@FindBy(xpath="//span[@id='MoveAuctionWindow_wnd_title']")
	private WebElement pageTitle;
	
	@FindBy(id="ddl_move_auction")
	private WebElement moveToDropdown;
	
	@FindBy(id="ddl_move_reasonType")
	private WebElement resonTypeDropdown;
	
	@FindBy(id="txt_move_reason")
	private WebElement reasonTxtBox;
	
	@FindBy(xpath="//p[text()='Moving a unit a new auction will reset the approvals']")
	private WebElement infoText;

	@FindBy(id="txt_move_employeeID")
	private WebElement employeId;
	
	@FindBy(id="MoveBtnCancel")
	private WebElement cancelBtn;
	
	@FindBy(id="btn_move_auctionUnit")
	private WebElement saveBtn;
	
	public MoveUnitPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_PageTitle(){
		return pageTitle.isDisplayed();
	}
	
	public boolean verify_infoText(){
		return infoText.isDisplayed();
	}
	
	public void enterReason(String reason){
		reasonTxtBox.sendKeys(reason);
	}
	
	public void enterEmployeeId(String emp){
		employeId.sendKeys(emp);
	}
	
	public void click_CancelBtn(){
		cancelBtn.click();
	}
	
	public void click_SaveBtn(){
		saveBtn.click();
	}

}
