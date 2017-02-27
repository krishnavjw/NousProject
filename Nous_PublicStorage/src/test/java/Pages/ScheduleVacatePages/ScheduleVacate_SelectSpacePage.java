package Pages.ScheduleVacatePages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class ScheduleVacate_SelectSpacePage 
{
	public WebDriver driver;
	@FindBy(xpath="//div[@class='container-heading padding']/h3[text()='Schedule Vacate']")
	private WebElement schVacate_Title;
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr")
	private List<WebElement> vacateSpace_gridVals;
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDash;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancel_btn;
	
	@FindBy(id="lnkReserve")
	private WebElement continue_btn;
	
	@FindBy(xpath="//div[@id='dualDatePicker']//table/tbody//tr//td[contains(@class,' ui-datepicker-today')]")
	private WebElement vacate_currentDate;
	
	public ScheduleVacate_SelectSpacePage(WebDriver driver)
	{
		driver=this.driver;
		PageFactory.initElements(driver,this);
	}
	
	public String verifySchVacate_Title()
	{
		return schVacate_Title.getText();
	}
	
	public void clickCancel_btn()
	{
		cancel_btn.click();
	}
	
	public void clickContinue_btn()
	{
		continue_btn.click();
	}
	
	public void clickBackBashboard_btn()
	{
		backToDash.click();
	}
	
	public void clickViewDetails_lk(String space)
	{
		for(WebElement ele: vacateSpace_gridVals)
		{
			if(space.equalsIgnoreCase(ele.getText().trim()))
			{
				String xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='"+space+"']/following-sibling::td/a[text()='View Details']";
				driver.findElement(By.xpath(xpath)).click();
			}
		}
	}
	
	////div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='A412']/following-sibling::td//div[@id='schedule-vacate-date-42569961']
	public void clickCalendar_Field(String space)
	{
		for(WebElement ele: vacateSpace_gridVals)
		{
			if(space.equalsIgnoreCase(ele.getText().trim()))
			{
				String xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td[text()='"+space+"']/following-sibling::td//div[@id='schedule-vacate-date-42569961']";
				driver.findElement(By.xpath(xpath)).click();
			}
		
	     }
	
	
	}

}
