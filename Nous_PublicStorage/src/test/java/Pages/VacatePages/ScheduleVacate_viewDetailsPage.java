package Pages.VacatePages;

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
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Monthly Rent')]")
	private WebElement monthly_rent_label;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Monthly Rent Tax')]")
	private WebElement monthly_rent_tax_label;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Insurance')]")
	private WebElement insurance_label;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Late Fees')]")
	private WebElement late_fees_label;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//section[@class='space__payment-details__row']/following-sibling::section/div[contains(text(),'Total')]")
	private WebElement total;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[contains(text(),'Total Due Now')]")
	private WebElement total_Due_Now;
	
	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[@class='space__identifier']/div[@class='unit']")
	private WebElement space_Num;
	
	
	@FindBy(xpath="//div[contains(text(),' Total Due Now:')]/following-sibling::div")
	private WebElement get_TotalDueNowAmount;
	
	
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
	
	public boolean monthlyRent_Check(){
		return monthly_rent_label.isDisplayed();
	}
	
	public boolean monthlyRentTax_Check(){
		return monthly_rent_tax_label.isDisplayed();
	}
	
	public boolean Insurance_Check(){
		return insurance_label.isDisplayed();
	}
	
	public boolean LateFees_Check(){
		return late_fees_label.isDisplayed();
	}

	
	public boolean total_Check(){
		return total.isDisplayed();
	}
	
	
	public boolean totalDueNow_Check(){
		return total_Due_Now.isDisplayed();
	}
		
	public void get_SpaceNum(){
		space_Num.getText();
	}
	
	public String get_TotalDueNowAmount(){
		
		return get_TotalDueNowAmount.getText();
	}

}
