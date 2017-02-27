package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Hold_PopupPage {
	WebDriver driver;

	@FindBy(xpath="//div[div[@id='employeeNumberEntry']]/preceding-sibling::div/span")
	private WebElement hold_PopupPageTxt;
	
	
	@FindBy(id="employeeNumber")
	private WebElement enter_empNumberTxtfield;
	
	@FindBy(xpath="//div[@id='employeeNumberEntry']/following-sibling::div//a[contains(text(),'OK')]")
	private WebElement clk_OkBtn;
	
	@FindBy(xpath="//div[@id='employeeNumberEntry']/following-sibling::div//a[contains(text(),'Cancel')]")
	private WebElement clk_CancelBtn;
	
	@FindBy(xpath="//div//p[text()='These spaces are being held for: ']/span")
	private WebElement getHeldSpaceTime;
	
	@FindBy(xpath="//div/p[text()='The following spaces have been placed on hold:']/following-sibling::p[@class='spaces']")
	private WebElement getHeldSpacenum;
	
	@FindBy(xpath="//div/p/b[text()='REMEMBER TO:']")
	private WebElement isremberToTitle;
	
	
	
	public Hold_PopupPage(WebDriver driver){
		
		//this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public String get_HoldPopTitle(){
		return hold_PopupPageTxt.getText();
	}
	
	
	public void enter_EmpNumber(String empno){
		
		enter_empNumberTxtfield.sendKeys(empno);
	}
	
	public void clk_OkBtn(){
		
		clk_OkBtn.click();
	}
	
	
  public void clk_CancelBtn(){
		
	clk_CancelBtn.click();
	}
  
  public boolean isHeldSpaceTimeIsDisplayed(){
	  
	  return getHeldSpaceTime.isDisplayed();
  }
  
  public String getHeldSpaceTime(){
	  
	  return getHeldSpaceTime.getText().trim();
  }
  
  public String getHeldSpacenumber(){
	  
	  return getHeldSpacenum.getText().trim();
  }
  
  public boolean isRemeberToTitleDisplayed(){
	  
	  return isremberToTitle.isDisplayed();
  }
	
	
}
