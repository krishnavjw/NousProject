package Pages.ScheduleVacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleVacate_ScheduledVacatePage 
{

	@FindBy(xpath="//div[@class='k-window-titlebar k-header']/span[@id='changeScheduledVacateDateDialog_wnd_title']")
	private WebElement scheduledVacate_Title;
	
	@FindBy(xpath="//div[@id='changeScheduledVacateDateDialog']//div[contains(text(),'New Date:')]")
	private WebElement newDate_label;
	
	@FindBy(xpath="//div[@id='changeScheduledVacateDateDialog']//div[contains(text(),'New Date:')]/following-sibling::div//div[@id='schedule-vacate-date']/span")
	private WebElement newDateCalendar_icon;
	
	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody/tr//td[contains(@class,'ui-datepicker-today')]")
	private WebElement currentDate_Disabled;
	
	
	public ScheduleVacate_ScheduledVacatePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public String verifyScheduledVacate_Title() {
		return scheduledVacate_Title.getText();	
	}
	
	public String verifyNewDate_field()
	{
		return newDate_label.getText().trim();
	}
	
	public void clickCalendarIcon()
	{
		newDateCalendar_icon.click();
	}
	
	public String verifyCurrentDate_IsDisabled()
	{
		return currentDate_Disabled.getAttribute("class");
	}
	
	
}
