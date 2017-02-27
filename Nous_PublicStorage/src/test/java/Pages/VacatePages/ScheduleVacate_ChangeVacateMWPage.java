package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleVacate_ChangeVacateMWPage {
	public ScheduleVacate_ChangeVacateMWPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
@FindBy(id="changeScheduledVacateDateDialog_wnd_title")
private WebElement title;

public String getMWTitle(){
	return title.getText();
}
	
@FindBy(id="cancel-vacate-button")
private WebElement cancel_Btn;

public boolean iscancel_Btn(){
	return cancel_Btn.isDisplayed();
}

@FindBy(id="remove-vacate-button")
private WebElement remove_Btn;

public boolean isremove_Btn(){
	return remove_Btn.isDisplayed();
}

public void clk_remove_Btn(){
	remove_Btn.click();
}

@FindBy(id="update-vacate-button")
private WebElement updateVacate_Btn;

public boolean isupdateVacate_Btn(){
	return updateVacate_Btn.isDisplayed();
}

@FindBy(id="employeeNumber")
private WebElement empId_Edt;

public void enter_empId_Edt(String empId){
	empId_Edt.sendKeys(empId);
}

public boolean isempId_Edt(){
	return empId_Edt.isDisplayed();
}

@FindBy(id="noteText")
private WebElement note_Edt;

public void enter_note_Edt(String note){
	note_Edt.sendKeys(note);
}

public boolean isnote_Edt(){
	return note_Edt.isDisplayed();
}

@FindBy(xpath="//input[@id='PrintReceipt']/following-sibling::span[1]")
private WebElement printEstimateReceipt_chkBox;

public boolean isprintEstimateReceipt_chkBox(){
	return printEstimateReceipt_chkBox.isDisplayed();
}

@FindBy(xpath="//div[contains(text(),'Current Dat')]/following-sibling::div")
private WebElement currentDate_Info;

public String getCurrentDate_Info(){
	return currentDate_Info.getText();
}

@FindBy(xpath="//span[@class='field-validation-valid employee-number-message-container']")
private WebElement empIdErrorMsg;

public String getempIdErrorMsg(){
	return empIdErrorMsg.getText();
}
}
