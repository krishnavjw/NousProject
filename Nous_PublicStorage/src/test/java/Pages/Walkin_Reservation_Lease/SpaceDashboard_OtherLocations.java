package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class SpaceDashboard_OtherLocations {

	WebDriver driver;

	@FindBy(id = "off-site-submit")
	private WebElement clk_OffSiteSubmitBtn;

	@FindBys(value = { @FindBy(xpath = "//form[@id='frmOffSiteReserveUnits']//div//table/tbody/tr[1]/td[8]/a") })
	private List<WebElement> clk_OffSiteReservation_locnumber;

	@FindBy(xpath = "//*[@id='offsiteUnitGrid']/div[2]/table/tbody/tr[1]/td[1]/label/span[1]")
	private WebElement RdBtn_Space_otherloc;

	public SpaceDashboard_OtherLocations(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public boolean OffSiteReservation_locnumber_count() {
		return clk_OffSiteReservation_locnumber.size() >= 1 ? true : false;
	}

	public void clk_OffSiteSubmitBtn() {
		if (clk_OffSiteSubmitBtn.isEnabled()) {
			clk_OffSiteSubmitBtn.click();
		}
	}

	public String OffSiteReservation_locnumber_gettext() {
		return clk_OffSiteReservation_locnumber.get(0).getText();
	}

	public WebElement RdBtn_Space_otherloc() {
		return RdBtn_Space_otherloc;
	}

	public void SelSpace_BasedOnLocation(String locno) {
		String xpath_start = "//div[@class='k-grid-content ps-container']/table/tbody/tr[";
		String xpath_end = "]/td[8]";
		int i = 1;
		while (isElementPresent(xpath_start + i + xpath_end)) {
			String locNumTxt = driver.findElement(By.xpath(xpath_start + i + xpath_end)).getText();
			if (locno.equalsIgnoreCase(locNumTxt.trim())) {
				String radioBtnXpath = xpath_start + i + xpath_end.replace("td[8]", "td[1]");
				driver.findElement(By.xpath(radioBtnXpath)).click();
				break;

			}
		}
		i++;
	}

	public boolean isElementPresent(String elementXpath) {
		int count = driver.findElements(By.xpath(elementXpath)).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	public boolean isReserveOffSiteDisplayed() {
		return clk_OffSiteSubmitBtn.isDisplayed();
	}

}
