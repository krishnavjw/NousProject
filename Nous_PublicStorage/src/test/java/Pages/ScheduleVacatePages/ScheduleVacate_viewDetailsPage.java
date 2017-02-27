package Pages.ScheduleVacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleVacate_viewDetailsPage 
{
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[@class='space__identifier']")
	private WebElement space_id;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Monthly Rent')]/following-sibling::div")
	private WebElement monthly_rent;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Total Due Now')]/following-sibling::div")
	private WebElement totlaDue;
	
	@FindBy(xpath="//div[@class='text-align-right']//a[contains(text(),'Print')]")
	private WebElement print_btn;
	
	@FindBy(xpath="//div[@class='text-align-right']//a[contains(text(),'Close')]")
	private WebElement close_btn;
	
	public ScheduleVacate_viewDetailsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	
	}
	
	public String verifySpace_id()
	{
		return space_id.getText();
	}
	
	public String getMonthlyRent()
	{
		return monthly_rent.getText();
	}
	
	public String getTotalDue()
	{
		return totlaDue.getText();
	}
	public void clickPrint_btn()
	{
		print_btn.click();
	}
	public void clickclose_btn()
	{
		close_btn.click();
	}

}
