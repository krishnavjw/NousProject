package Pages.IssueManagementPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssueManagementList {
	
	@FindBy(xpath="//div/h3[@class='margin-top']")
	public WebElement pageTitle;
	
	@FindBy(linkText="Back To Dashboard")
	public WebElement backToDashboardBtn;
	
	@FindBy(xpath="//span[@class='locationInfoLeft']//span[@class='k-select']/span[text()='select']")
	public WebElement filterByDistrict;
	
	@FindBy(xpath="//ul[@id='SelectedSiteID_listbox']/li")
	public List<WebElement> districtsList;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Created Date']/a")
	public WebElement header_CreatedDate;
	
	@FindBy(xpath="//span[@class='locationinfoleft']//span[@class='k-select']/span[text()='select']")
	public WebElement filterByIssueType;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Issue Type']")
	public WebElement column_IssueType;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Issue Number']")
	public WebElement column_IssueNumber;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Customer']")
	public WebElement column_Customer;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Location']")
	public WebElement column_Location;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Space']")
	public WebElement column_Space;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Created Date']")
	public WebElement column_CreatedDate;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Current Due Date']")
	public WebElement column_CurrentDueDate;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th[@data-title='Days Open']")
	public WebElement column_DaysOpen;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th//span[text()='Assigned To']")
	public WebElement dropdown_AssignedTo;
	
	@FindBy(xpath="//div[@id='issues-grid']//table/thead/tr/th//span[text()='Status']")
	public WebElement dropdown_Status;
	
	@FindBy(xpath="//a[text()='Back To Dashboard']")
	private WebElement backToDashboardTab;
	
	@FindBy(xpath="//div[div[table[thead[tr[th[a[contains(text(),'Issue Number')]]]]]]]/following-sibling::div/table/tbody/tr/td[3]")
	private WebElement issueIdList;
	
	
	public IssueManagementList(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public boolean isExists_Column_IssueType(){
		return column_IssueType.isDisplayed();
	}
	
	public boolean isExists_Column_IssueNumber(){
		return column_IssueNumber.isDisplayed();
	}
	
	public boolean isExists_Column_Customer(){
		return column_Customer.isDisplayed();
	}
	
	public boolean isExists_Column_Location(){
		return column_Location.isDisplayed();
	}
	
	public boolean isExists_Column_Space(){
		return column_Space.isDisplayed();
	}
	
	public boolean isExists_Column_CreatedDate(){
		return column_CreatedDate.isDisplayed();
	}
	
	public boolean isExists_Column_CurrentDueDate(){
		return column_CurrentDueDate.isDisplayed();
	}
	
	public boolean isExists_Column_DaysOpen(){
		return column_DaysOpen.isDisplayed();
	}
	
	public boolean isExists_DropDown_AssignedTo(){
		return dropdown_AssignedTo.isDisplayed();
	}
	
	public boolean isExists_DropDown_Status(){
		return dropdown_Status.isDisplayed();
	}
	
	public boolean isExists_BackToDashboard(){
		return backToDashboardBtn.isDisplayed();
	}
	
	public boolean isExists_Dropdown_FilterByDistrict(){
		return filterByDistrict.isDisplayed();
	}
	
	public boolean isExists_Dropdown_FilterByIssueType(){
		return filterByIssueType.isDisplayed();
	}

	public String getPageTitle(){
		return pageTitle.getText();
	}
	
	public void clk_backToDashboardBtn(){
		backToDashboardBtn.click();
	}
	
	public void selectFilter_District(String text) throws InterruptedException{
		filterByDistrict.click();
		Thread.sleep(2000);
		for(WebElement element : districtsList){
			if(element.getText().contains(text)){
				element.click();
				Thread.sleep(5000);
				break;
			}
		}
	}
	
	public void click_Header_CreatedDate(){
		header_CreatedDate.click();
	}
	
	public void clkBackToDashboardTab(){
		backToDashboardTab.click();
	}
	
	
	

}
