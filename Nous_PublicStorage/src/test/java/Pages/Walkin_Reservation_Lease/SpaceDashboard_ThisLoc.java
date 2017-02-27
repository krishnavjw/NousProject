package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpaceDashboard_ThisLoc {

	@FindBy(xpath = "//span[@class='k-link' and contains(text(),'Other Locations')]")
	private WebElement other_locations;
	
	@FindBy(xpath = "//span[@class='k-link' and contains(text(),'At This Location')]")
	private WebElement thislocations;
	

	@FindBy(linkText = "Rent")
	private WebElement link_rent;

	@FindBy(linkText = "Reserve")
	private WebElement link_reserve;

	@FindBy(linkText = "Hold")
	private WebElement link_hold;

	@FindBy(xpath = "//div[@id='onsiteUnitGrid']//table/tbody/tr")
	private List<WebElement> rows_displayed;

	@FindBy(xpath = "//span[@class='count']")
	private WebElement Num_Spcavailable;

	@FindBy(xpath = "//a[@id='off-site-submit']")
	private WebElement Btn_ReserveOffsiteUnits;

	@FindBy(xpath = "//div//li//span[contains(text(),'Other Locations')]")
	private WebElement clk_Other_locations;
	
	@FindBy(xpath="(//span[@class='margin-right'])[1]")
	private WebElement getNotRentReadyMaintenanceRequiredTxt; 
	
	@FindBy(xpath="(//span[@class='margin-right'])[2]")
	private WebElement getDepositReqtxt;

	public SpaceDashboard_ThisLoc(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void click_Rent() {
		link_rent.click();
	}

	public void click_Reserve() {
		link_reserve.click();
	}

	public void click_Hold() {
		link_hold.click();
	}

	public void click_otherLocations() {
		other_locations.click();
	}
	
	
	public void click_thisLocations() {
		thislocations.click();
	}

	public boolean select_GivenSpace(String spaceNum) {
		String getSpace = "";
		boolean spaceFound = false;
		for (WebElement eledata : rows_displayed) {
			getSpace = eledata.findElement(By.xpath(".//td[4]")).getText();
			if (getSpace.equals(spaceNum)) {
				eledata.findElement(By.xpath(".//td[1]/label/span[@class='button']")).click();
				spaceFound = true;
				break;
			}
		}
		return spaceFound;
	}

	public boolean Number_rows(List<WebElement> element) {
		if (element.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean Num_spaceAvalable(List<WebElement> element) {
		if (element.size() == Integer.parseInt(Num_Spcavailable.getText())) {
			return true;
		}
		return false;
	}

	public void click_ReserveOffsiteUnits() {
		Btn_ReserveOffsiteUnits.click();
	}

	public void clk_OtherLocations() {
		clk_Other_locations.click();
	}

	
	
	
	public boolean isRentButtonDisplayed() {
		return link_rent.isDisplayed();
	}

	public boolean isReserveButtonDisplayed() {
		return link_reserve.isDisplayed();
	}

	public boolean isHoldButtonDisplayed() {
		return link_hold.isDisplayed();
	}

	public boolean isDisplayed_Rentbutton() {
		return link_rent.isDisplayed();
	}

	public boolean isDisplayed_Reservebutton() {
		return link_reserve.isDisplayed();
	}

	public boolean isDisplayed_Holdbutton() {
		return link_hold.isDisplayed();
	}
	
	public String getNotRentReadyMaintenanceRequiredText(){
		
		return getNotRentReadyMaintenanceRequiredTxt.getText().trim();
	}
	
	public String getDepositReqForThisSpaceText(){
		
		return getDepositReqtxt.getText().trim();
	}

}
