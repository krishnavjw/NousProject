package Pages.TransferSpacePages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class TransferSelectSpacePage {

	WebDriver driver;
	Actions actions;

	@FindBys(value = { @FindBy(xpath = "//span[@class='leftalignedinlineblockspan dimensions']") })
	private List<WebElement> ChkBx_boldAvlSpace;

	@FindBy(xpath = "//a[@id='search-button']")
	private WebElement searchbtn;

	@FindBy(xpath = "//a[@id='transferSpaceButton']")
	private WebElement selectSpacebtn;

	@FindBy(xpath = "//div[@class='sub-command-row clearfix-container']//a[text()='Cancel']")
	private WebElement spaceCancelBtn;

	@FindBys(value = { @FindBy(xpath = "//td[contains(@role,'gridcell')]/input[contains(@type,'radio')]") })
	private List<WebElement> spaceChkbx;

	public TransferSelectSpacePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void Clk_OnAvlSpace() {
		// ChkBx_AvlSpace.get(0).click();

		int availableChkbxSize = ChkBx_boldAvlSpace.size();

		for (int i = 0; i < availableChkbxSize; i++) {
			ChkBx_boldAvlSpace.get(i).click();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void clk_OnSearchBtn() {
		searchbtn.click();
	}

	public void clk_OnSelectSpaceBtn(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectSpacebtn);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectSpacebtn);
	}

	public void clickSpaceChkbx(WebDriver driver) {
		((JavascriptExecutor) (driver)).executeScript("arguments[0].scrollIntoView()", spaceChkbx.get(0));
		((JavascriptExecutor) (driver)).executeScript("arguments[0].click();", spaceChkbx.get(0));
	}

	public void clk_OnCancelBtn() {
		spaceCancelBtn.click();
	}

}
