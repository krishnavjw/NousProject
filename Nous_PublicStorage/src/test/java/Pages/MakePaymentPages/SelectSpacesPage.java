package Pages.MakePaymentPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectSpacesPage {

	@FindBy(xpath="//div[@class='container-heading']/h3")
	private WebElement get_SelSpacesText;

	@FindBy(id="submitButton")
	private WebElement clk_Continue;

	@FindBy(xpath="//span[text()='Past Due']/preceding-sibling::span[@class='js-space-identifier']")
	private List<WebElement> listOfAllSpacesHasPastDue;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='unit-info first col']//span[@class='button'])[1]")
	private WebElement chkbx_SelectAll;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='unit-info first col']//span[@class='button'])[2]")
	private WebElement chkbx_1;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='unit-info first col']//span[@class='button'])[3]")
	private WebElement chkbx_2;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='unit-info first col']//span[@class='button'])[4]")
	private WebElement chkbx_3;

	@FindBy(xpath="//div[@id='lease-payment']//h3[contains(text(),'Select Spaces')]")
	private WebElement selectSpaces_Title;

	@FindBy(xpath="//form[@id='select-spaces-form']//span[text()='Past Due']")
	private WebElement pastDue;
	
	@FindBy(xpath="//form[@id='select-spaces-form']//span[@class='js-unit-insurance js-unit-product-option']")
	private WebElement monthlyInsurDue;
	
	@FindBy(xpath="//form[@id='select-spaces-form']//span[@class='js-next-unit-insurance']")
	private WebElement monthlyInsureNextDue;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='due-now second col'])[2]")
	private WebElement totalDueAmt;
	
	@FindBy(xpath="(//form[@id='select-spaces-form']//div[@class='nextmonthdueheader third col'])[2]")
	private WebElement NextDueAmt;
	
	@FindBy(xpath="//form[@id='select-spaces-form']//button[@id='submitButton']")
	private WebElement continue_Btn;
	
	@FindBy(xpath="//form[@id='select-spaces-form']//span[@class='js-space-identifier']")
	private List<WebElement> Spaces;
	
	public SelectSpacesPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public boolean isSelSpacesTextDisplayed(){

		return get_SelSpacesText.isDisplayed();
	}

	public void clk_Continue(){

		clk_Continue.click();
	}

	public String get_SpaceNumber(){

		String space=null;
		for(WebElement ele:listOfAllSpacesHasPastDue){

			space=ele.getText().trim();
			break;
		}
		return space;

	}
	public boolean verify_PageTitle(){
		return selectSpaces_Title.isDisplayed();
	}

	public String txt_PageTitle(){
		return selectSpaces_Title.getText();
	}
	
	
	public void click_SelectAll() {
		chkbx_SelectAll.click();
	}
	
	public void click_Chbx1() {
		chkbx_1.click();
	}
	
	public void click_chbx2() {
		chkbx_2.click();
	}
	
	public void click_chbx3() {
		chkbx_3.click();
	}
	
	public boolean verify_PastDue(){
		return pastDue.isDisplayed();
	}
	
	public boolean verify_monthlyInsurDue(){
		return monthlyInsurDue.isDisplayed();
	}
	
	public String txt_monthlyInsurDue(){
		return monthlyInsurDue.getText();
	}
	
	public boolean verify_monthlyInsureNextDue(){
		return monthlyInsureNextDue.isDisplayed();
	}
	
	public String txt_monthlyInsurNextDue(){
		return monthlyInsureNextDue.getText();
	}
	
	public String txt_TotalDueNow(){
		return totalDueAmt.getText();
	}
	
	public String txt_TotalNextDue(){
		return NextDueAmt.getText();
	}
	
	public boolean verify_continue_Btn(){
		return continue_Btn.isDisplayed();
	}
	
	public void click_continue_Btn() {
		continue_Btn.click();
	}
	
	public List<WebElement> multipleSpaces()
	{
		return Spaces;
	}

}
