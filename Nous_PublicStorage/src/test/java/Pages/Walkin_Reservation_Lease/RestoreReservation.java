package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RestoreReservation {

	 public RestoreReservation(WebDriver driver){
			
			PageFactory.initElements(driver, this);
		}
	 
	 
	 @FindBy(xpath="//div[@id='restoreReservationDialog']/div")
	 private WebElement restoreReservation;
	 
	 @FindBy(xpath="//div[@class='k-window-content k-content']//div[@class='command-row clearfix-container']//a[contains(text(),'Yes')]")
	 private WebElement yes_btn;
	 
	 @FindBy(xpath="//div[@class='k-window-content k-content']//div[@class='command-row clearfix-container']//a[contains(text(),'Cancel')]")
	 private WebElement cancel_btn;
	 
	 @FindBy(xpath="//div[@id='restoreReservationDialog']//input[@id='employeeNumber']")
	 private WebElement employeenum;
	 
	 
	 public String getText_restoreRes(){
		 return restoreReservation.getText();
	 }
	 
	 public boolean isYesBtnDisplayed(){
		 return yes_btn.isDisplayed();
	 }
	 
	 public boolean isCancelBtnDisplayed(){
		 return cancel_btn.isDisplayed();
	 }
	 
	 public void click_Yes(){
		 yes_btn.click();
	 }
	 
	 public void click_Cancel(){
		 cancel_btn.click();
	 }
	 
	 public boolean isDisplayed_EmpNUm(){
		 return employeenum.isDisplayed();
	 }
	 
	 public void enter_EmployeeId(String text){
		 employeenum.sendKeys(text);
	 }
	 
	 public boolean isDisplayed_RestoreRes(){
		 return restoreReservation.isDisplayed();
	 }
	 
}
