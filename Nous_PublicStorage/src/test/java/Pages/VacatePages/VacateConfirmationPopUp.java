package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VacateConfirmationPopUp {

	WebDriver driver;
	public VacateConfirmationPopUp(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//label[@id='HasUnitBeenInspected_No']//span[@class='button']
	
	@FindBy(xpath="//label[@id='HasUnitBeenInspected_No']//span[@class='button']")
	private WebElement NoRadioButton;
	
	@FindBy(xpath="//textarea[@id='noteText']")
	private WebElement Note_TextArea;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeNumber;
	
	
	@FindBy(xpath="(//a[contains(text(),'Confirm')])[2]")
	private WebElement ConfirmButton;
	
	
	
	public void click_NoRadioButton(){
		NoRadioButton.click();
	}
	
	public void enter_Note_TextArea(String Note){
		Note_TextArea.sendKeys(Note);
	}
	
	public void enter_EmpNumber(String EmpNumber){
		employeeNumber.sendKeys(EmpNumber);
	}
	
	public void click_ConfirmButton(){
		ConfirmButton.click();
	}
	
	
}
