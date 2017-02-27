package Pages.AWB;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddOtherUnitPage {
	private WebDriver driver;
	
	@FindBy(xpath="//span[@id='OtherUnitsWindow_wnd_title']")
	private WebElement page_title;
	
	@FindBy(id="IdSpace")
	private WebElement spaceTxt;
	
	@FindBy(id="IdLastName")
	private WebElement lastNameTxt;
	
	@FindBy(id="IdFirstName")
	private WebElement firstNameTxt;
	
	
	@FindBy(id="OtherUnitbtnSearch")
	private WebElement searchBtn;
	
	@FindBy(id="OtherUnitbtnReset")
	private WebElement resetBtn;
	
	@FindBy(id="OtherUnitbtnCancel")
	private WebElement cancelBtn;
	
	@FindBy(id="OtherUnitbtnSave")
	private WebElement saveBtn;
	
	@FindBy(xpath="//div[@id='OtherUnitgrid']//table/tbody/tr[1]/td[1]/input")
	private WebElement firstCheckBox;
	
	public AddOtherUnitPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_TitleDisplayed(){
		return page_title.isDisplayed();
	}
	
	public void enterSpace(String space){
		 spaceTxt.sendKeys(space);
	}
	
	public void enterLastName(String lastname){
		lastNameTxt.sendKeys(lastname);
	}
	
	public void enterFirstName(String firstname){
		firstNameTxt.sendKeys(firstname);
	}
	
	
	public void click_SearchBtn(){
		searchBtn.click();
	}
	
	public void click_ResetBtn(){
		resetBtn.click();
	}
	
	public void click_CancelBtn(){
		cancelBtn.click();
	}

	public void click_SaveBtn(){
		saveBtn.click();
	}
	
	public void Click_CheckBox(){
		firstCheckBox.click();
	}
}
