package Pages.EditAccountDetails;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MergeAccountsPage {

	String evaluate = null;
	int divValue = 0;

	@FindBy(xpath = "//div[@class='page-header container-heading clearfix-container']/h3[@class='floatleft']")
	private WebElement get_MergeAccTitle;

	@FindBy(xpath = "//div[text()='STEP ONE: Merge From']/following-sibling::div//span[text()='Account #']")
	private WebElement clk_StepOneMegreFormDropDwn;

	@FindBy(xpath = "//div[text()='STEP TWO: Move To']/following-sibling::div//span[text()='Account #']")
	private WebElement clk_StepTwoMoveToDropDwn;

	@FindBy(xpath = "//a[text()='Submit']")
	private WebElement clk_SubmitBtn;

	public MergeAccountsPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
	}

	public String get_MergeAccTitle() {

		return get_MergeAccTitle.getText().trim();
	}

	public boolean isMergeAccountTitleDisplayed() {

		return get_MergeAccTitle.isDisplayed();
	}

	public void clk_StepOneMegreFormDropDwn() {

		clk_StepOneMegreFormDropDwn.click();
	}

	public void clk_StepTwoMoveToDropDwn() {
		clk_StepTwoMoveToDropDwn.click();
	}

	public void selValFromDropDwn(String expVal, WebDriver driver, int dropDownNumber) throws InterruptedException {

		System.out.println("Account Number before passing to selectDropDown: " + expVal);

		if (dropDownNumber == 1) {
			evaluate = "STEP ONE";
			divValue = 13;
			selectDropDown(expVal, driver, dropDownNumber, divValue, evaluate);
		} else if (dropDownNumber == 2) {
			evaluate = "STEP TWO";
			divValue = 14;
			selectDropDown(expVal, driver, dropDownNumber, divValue, evaluate);
		} else {
			System.out.println("Please Enter Only 1 0r 2");
		}
	}

	public void selectDropDown(String expVal, WebDriver driver, int dropDownNumber, int val, String dnum)
			throws InterruptedException {

		String regex = "\\d+";

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement dropdown = driver.findElement(By.xpath("//div[contains(text(),'" + evaluate
				+ "')]/following-sibling::div/descendant::span[@class='k-dropdown-wrap k-state-default']"));
		dropdown.click();

		Thread.sleep(2000);

		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[" + val + "]/div/ul/li"));

		for (WebElement ele : ListWbEle) {

			String actualWbEleText = ele.getText();

			if (dropDownNumber == 1 && actualWbEleText.matches(regex)) {
				if (Integer.parseInt(actualWbEleText) == Integer.parseInt(expVal)) {
					executor.executeScript("arguments[0].click();", ele);
					break;
				}
			} else if (dropDownNumber == 2 && actualWbEleText.matches(regex)) {
				if (!(Integer.parseInt(actualWbEleText) == Integer.parseInt(expVal))) {
					executor.executeScript("arguments[0].click();", ele);
					break;
				}
			}

		}
	}

	public void clk_SubmitBtn() {
		clk_SubmitBtn.click();
	}

}
