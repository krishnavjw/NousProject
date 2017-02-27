package Pages.AutoPayPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmployeeIDPopUpPage {

	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement enter_EmpId;
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement clk_OkBtn;
	
	
	public EmployeeIDPopUpPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	public void enter_EmpId(String empid){
		enter_EmpId.sendKeys(empid);
		
	}
	
	public void clk_OkBtn(){
		clk_OkBtn.click();
	}
	
}
