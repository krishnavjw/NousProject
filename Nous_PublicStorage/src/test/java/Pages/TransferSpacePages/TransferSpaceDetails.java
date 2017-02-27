package Pages.TransferSpacePages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class TransferSpaceDetails {

	WebDriver driver;
	Actions actions;

	@FindBy(xpath = "//span[@class='additionalCharge']")
	private WebElement additionalCharge;

	@FindBy(xpath = "//span[@class='additionalDays']")
	private WebElement additionalDays;;

	@FindBy(xpath = "//tbody[contains(@role,'rowgroup')]//td[@class='grid-cell-size']//div[@class='bold']")
	private WebElement spaceNumber;

	@FindBy(xpath = "//tbody[contains(@role,'rowgroup')]//div[contains(@class,'grid-cell-rent option-price')]//div[@class='bold']")
	private WebElement monthlyRent;

	@FindBy(xpath = "//a[text()='View Details']")
	private WebElement viewDetailsLink;

	@FindBy(xpath = "//div[@id='PlannedVacateDate']/span")
	private WebElement currentDate;

	@FindBy(xpath = "//div[@id='additionalChargeCalc']//span[@class='padding-left message']")
	private WebElement errorMessage;

	@FindBy(xpath = "//ul[@class='webchamp-radiobutton-list ']//span[text()='Yes']/preceding-sibling::span")
	private WebElement maintenanceYesRadioBtn;

	@FindBy(xpath = "//ul[@class='webchamp-radiobutton-list ']//span[text()='No']/preceding-sibling::span")
	private WebElement maintenanceNoRadioBtn;

	@FindBy(xpath = "//span[text()='For this new space, do you want to keep the sales channel pricing?']/preceding-sibling::span")
	private WebElement salesChannelPricingChkbx;

	@FindBy(xpath = "//a[@id='addSpaceButton']")
	private WebElement findSpaceBtn;

	@FindBy(xpath = "//div[@id='paymentDetailsDialog']/following-sibling::div//a[contains(@class,'cancel-button')]")
	private WebElement closebtn;

	@FindBy(xpath = "//span[text()='Keep Rate']/preceding-sibling::span")
	private WebElement keepRateChkbx;

	@FindBy(xpath = "//*[@id='maintenanceReasonType']//span[contains(@class,'k-dropdown-wrap')]")
	private WebElement issueTypeDropDown;

	@FindBy(xpath = "//a[@id='lnkSubmitTransfer']")
	private WebElement submitBtn;
	
	@FindBy(xpath = "//a[@id='lnkConfirmWithCustomer']")
	private WebElement confirmWithCustomer;

	public TransferSpaceDetails(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public String getAdditionCharge() {
		return additionalCharge.getText();
	}

	public String getAdditionDays() {
		return additionalDays.getText();
	}

	public String getSpaceNumber() {
		return spaceNumber.getText();
	}

	public String getMonthlyRent() {
		return monthlyRent.getText();
	}

	public void clk_viewDetails() {
		viewDetailsLink.click();
	}

	public void clk_viewDetailsCloseBtn() {
		closebtn.click();
	}

	public String getCurrentDate() {
		return currentDate.getText();
	}

	public boolean isErrorMessageAvailable() {
		return errorMessage.isDisplayed();
	}

	public void clk_maintenanceYesRadioBtn() {
		maintenanceYesRadioBtn.click();
	}

	public void clk_maintenanceNoRadioBtn() {
		maintenanceNoRadioBtn.click();
	}

	public void clk_salesPricingChanelChkbx() {
		salesChannelPricingChkbx.click();
	}

	public void clk_findSpaceBtn() {
		findSpaceBtn.click();
	}

	public void clk_keepRateChkbx() {
		keepRateChkbx.click();
	}

	public boolean isKeepRateCheckBoxDisplayed() {
		return keepRateChkbx.isDisplayed();
	}

	public void clk_onSubmitBtn() {
		submitBtn.click();
	}

	public String getCalendarDate() {
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		// cal.add(Calendar.DATE, NumDaysToAdd);
		SimpleDateFormat ft = new SimpleDateFormat("E MM/dd/yyyy");
		return ft.format(cal.getTime());
	}

	public void selectMaintenanceTypeOptions(WebDriver driver, String eoption) {
		issueTypeDropDown.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> options = driver.findElements(By.xpath("//ul[@id='MaintenanceReason_listbox']/li"));
		for (WebElement aoption : options) {
			if (aoption.getText().equals(eoption)) {
				actions = new Actions(driver);
				actions.moveToElement(aoption).click(aoption).build().perform();
				// ((JavascriptExecutor)
				// driver).executeScript("arguments[0].click();", aoption);
			}
		}
	}

	public void selectDateFromCalendar(String data) {
		try {

			int value = Integer.parseInt(data);
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, value);
			SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());
			strTodaysDate = StringUtils.stripStart(strTodaysDate, "0");
			Reporter.log("strTodaysDate: " + strTodaysDate, true);
			Thread.sleep(2000);
			Calendar mCalendar = Calendar.getInstance();

			String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			mCalendar.add(Calendar.MONTH, 1);
			String nmonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			String ExpDay = strTodaysDate;

			String ActMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
			String ActNextMonth = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]")).getText();

			List<WebElement> CurMnthNoDays = driver.findElements(By.xpath(
					"//div[@class='ui-datepicker-group ui-datepicker-group-first']//td[@data-handler='selectDay']"));
			List<WebElement> NxtMnthNoDays = driver.findElements(By.xpath(
					"//div[@class='ui-datepicker-group ui-datepicker-group-last']//td[@data-handler='selectDay']"));

			/*
			 * if(ActMonth.equals(month) && ActNextMonth.equals(nmonth)) {
			 */
			List<WebElement> Ls_Days;

			if (CurMnthNoDays.size() >= value) {
				Ls_Days = CurMnthNoDays;
			} else {
				Ls_Days = NxtMnthNoDays;
			}
			
			Thread.sleep(1000);
			for (int i = 0; i < Ls_Days.size(); i++) {
				String AvailableDay = Ls_Days.get(i).getText();
				if (AvailableDay.equals(ExpDay)) {
					Ls_Days.get(i).click();
					break;
				}
			}
			// }

		} catch (Exception e) {
			Reporter.log("Exception:dateValidaitons" + e, true);
		}
	}
	
	public void clk_confirmWithCustomerLink(){
		confirmWithCustomer.click();
	}

}
