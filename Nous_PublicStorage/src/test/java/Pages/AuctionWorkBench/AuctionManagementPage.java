package Pages.AuctionWorkBench;

import java.util.List;

import org.apache.bcel.generic.NEW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuctionManagementPage {
	
	WebDriver driver;
	
	@FindBy(xpath="//h2[contains(text(),'Auction Management')]")
	private WebElement pageTitle;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[@data-title='DRA']")
	private WebElement dRA_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[@data-title='Property #']")
	private WebElement property_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th/a[text()='Auction Date']")
	private WebElement auctionDate_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[7][text()='Approved']")
	private WebElement approved_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[6][text()='Units']")
	private WebElement units_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[2]/th[2][text()='DM']")
	private WebElement dM_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[2]/th[3][text()='RM']")
	private WebElement rM_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[8][text()='Employee']")
	private WebElement employee_Column;
	
	@FindBy(xpath="//div[@id='Propertygrid']//table/tbody/tr[1]/td/a[contains(text(),'Unit Details')]")
	private WebElement unitDetailsLink;
	
	@FindBy(partialLinkText="Back to Dashboard")
	private WebElement backToDashBoard_Btn;
	
	@FindBy(xpath="//h3[contains(text(),'Filters')]")
	private WebElement filters_Section;
	
	@FindBy(xpath="//button[@id='ScheduleAuctionMWOpen']")
	private WebElement newAuction_Btn;
	
	
	
	
	
	
		
	public AuctionManagementPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verify_AuctionManagementtitle(){
		return 
				pageTitle.isDisplayed();
	}
	
	public boolean Verify_DRA(){
		return dRA_Column.isDisplayed();
	}
	
	public boolean verify_property(){
		return property_Column.isDisplayed();
	}
	
	public boolean verify_AuctionDate(){
		return auctionDate_Column.isDisplayed();
	}
	
	public boolean verify_Units(){
		return units_Column.isDisplayed();
	}
	
	public boolean verify_DM(){
		return dM_Column.isDisplayed();
	}
	
	public boolean verify_RM(){
		return rM_Column.isDisplayed();
	}
	
	public boolean verify_Employee(){
		return employee_Column.isDisplayed();
	}
	
	public boolean Verify_unitDetailsLink(){
		return unitDetailsLink.isDisplayed();
	}
	
	public void click_unitDetailsLink(){
		unitDetailsLink.click();;
	}
	
	public void click_BackToDashBoard_Btn(){
		backToDashBoard_Btn.click();
	}
	
	public boolean verify_filterssection(){
		return filters_Section.isDisplayed();
	}
	
	public  boolean verify_Newauction_BTN(){
		return newAuction_Btn.isDisplayed();
	}
	
	public  boolean verify_approved(){
		return approved_Column.isDisplayed();
	}
	
	@FindBy(id="dateFilter")
	private WebElement datefield;
	
	public  boolean verify_datefieldr(){
		return datefield.isDisplayed();
	}
	
	@FindBy(xpath="//div[@id='filterSection']//span[text()='to End of Month']/preceding-sibling::span[@class='button']")
	private WebElement endOfMnth_chkbx;
	
	public  boolean verify_endOfMnth_chkbx(){
		return endOfMnth_chkbx.isDisplayed();
	}
	
	@FindBy(xpath="//div[@id='filterSection']//span[text()='include Next Month']/preceding-sibling::span[@class='button']")
	private WebElement includenxtmnth_chkbx;
	
	public  boolean verify_includenxtmnth_chkbx(){
		return includenxtmnth_chkbx.isDisplayed();
	}
	
	@FindBy(xpath="//div[@id='filterSection']//span[text()='include Prior Month']/preceding-sibling::span[@class='button']")
	private WebElement includePriorMnth_chkbx;
	
	public  boolean verify_includePriorMnth_chkbx(){
		return includePriorMnth_chkbx.isDisplayed();
	}
	
	@FindBy(xpath="//input[@name='DRAProp'][@value='DRA']")
	private WebElement dra_radiobtn;
	
	public  boolean verify_dra_radiobtn(){
		return dra_radiobtn.isDisplayed();
	}
	
	@FindBy(xpath="//select[@id='ddl_dra']")
	private WebElement dra_dropdwn;
	
	public  boolean verify_dra_dropdwn(){
		return dra_dropdwn.isDisplayed();
	}
	
	@FindBy(xpath="//input[@name='DRAProp'][@value='Property']")
	private WebElement property_radiobtn;
	
	public  boolean verify_property_radiobtn(){
		return property_radiobtn.isDisplayed();
	}
	
	@FindBy(id="propertyTextbox")
	private WebElement property_txtfield;
	
	public  boolean verify_property_txtfield(){
		return property_txtfield.isDisplayed();
	}
	
	public void click_DRA(){
		 dRA_Column.click();
	}
	
	public void click_AuctionDate(){
		 auctionDate_Column.click();
	}
	
	@FindBy(xpath="//span[@class='k-icon k-i-arrow-n']")
	private WebElement dra_Sorting;
	
	public  boolean verify_dra_Sorting(){
		return dra_Sorting.isDisplayed();
	}
	
	public void click_dra_Sorting(){
		dra_Sorting.click();
	}
	
	
	@FindBy(xpath="//span[@class='k-icon k-i-arrow-n']")
	private WebElement AuctionDate_Sorting;
	
	public  boolean verify_AuctionDate_Sorting(){
		return AuctionDate_Sorting.isDisplayed();
	}
	
	public void click_AuctionDate_Sorting(){
		AuctionDate_Sorting.click();
	}
	
	public void get_data_InDRAcolumn(){
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='Propertygrid']//table//tbody/tr//td[2]"));
		for(WebElement ele:lst){
			System.out.println(ele.getText());
		}
	}
	
	public void get_data_InPropertycolumn(){
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='Propertygrid']//table//tbody/tr//td[4]"));
		for(WebElement ele:lst){
			System.out.println(ele.getText());
		}
	}
	
	@FindBy(id="idremoveAuction")
	private WebElement removelink;
	
	public boolean verify_removelink(){
		return removelink.isDisplayed();
	}
	
	public void click_removelink(){
		removelink.click();
	}


}
