package Pages.HomePages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class LoginPage {

	WebDriver driver;

	@FindBy(xpath = "//input[@name='username']")
	private WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement Pwd;

	@FindBy(xpath = "//input[@class='psbutton-priority']")
	private WebElement login_btn;

	@FindBy(xpath = "//span[contains(@class,'header-location-nickname')]")
	private WebElement Txt_SiteNo;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void enterUserName(String uN) {
		userName.sendKeys(uN);
	}

	public void enterPassword(String password) {
		Pwd.sendKeys(password);
	}

	public void clickLogin() {
		login_btn.click();
	}

	public void login(String uname, String password) {

		userName.sendKeys(uname);
		Pwd.sendKeys(password);
		login_btn.click();
	}

	/**
	 * 
	 * 
	 * Basavaraj/Rekha
	 */
	public void clk_CustomerApproval() throws Exception {

		Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);

		String biforstNum = Bifrostpop.getBiforstNo();

		Reporter.log(biforstNum + "", true);

		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		Reporter.log(tabs.size() + "", true);
		driver.switchTo().window(tabs.get(1));
		driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");

		List<WebElement> biforstSystem = driver.findElements(
				By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
		for (WebElement ele : biforstSystem) {
			if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
				Reporter.log(ele.getText() + "", true);
				ele.click();
				break;
			}
		}

		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
		Thread.sleep(5000);
		driver.switchTo().window(tabs.get(0));
		driver.navigate().refresh();
		Thread.sleep(5000);
		driver.findElement(By
				.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"))
				.click();
		Thread.sleep(5000);
	}

	public String get_SiteNumber() {
		String[] arr = Txt_SiteNo.getText().split("-");
		Reporter.log("Location number is: " + arr[0].trim(), true);
		return arr[0].trim();
	}

}
