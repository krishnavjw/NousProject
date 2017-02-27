package Pages.VacatePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Vacate_Selspacepage {
	WebDriver driver;

	@FindBy(xpath="//div[@class='container-heading padding']//h3[text()='Vacate Now']")
	private WebElement vacateNowtxt;
	
	@FindBy(xpath="//span[text()='Standard Vacate']/preceding-sibling::span[@class='button']")
	private WebElement stdVacate_Radio;
	
	
	@FindBy(xpath="//span[text()='Skip Vacate']/preceding-sibling::span[@class='button']")
	private WebElement skpVacate_Radio;
	
	@FindBy(xpath="//div[@id='lastAccessDateWrapper']/following-sibling::div[text()='Space(s) to be vacated:']")
	private WebElement spacesToBeVacatedtxt;
	
	@FindBy(xpath="//div[@class='action-container command-row']//a[text()='Back to Dashboard']")
	private WebElement backToDashBoardBtn;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancelBtn;
	
	@FindBy(id="lnkReserve")
	private WebElement continueBtn;
	
	@FindBy(xpath="//div[@class='last-access-container clearfix-container']//div[contains(text(),'Last gate or elevator access:')]")
	private WebElement lastGatetxt;
	
	@FindBy(xpath="//div[@id='lastAccessDate']//span[text()='SELECT']")
	private WebElement selCalender;
	
	@FindBy(id="LastAccessDateHours")
	private WebElement atHourstxt;
	
	@FindBy(id="LastAccessDateMinutes")
	private WebElement atMinutestxt;
	
	@FindBy(id="LastAccessAMPM")
	private WebElement AMradio;
	
	@FindBy(id="LastAccessAMPM")
	private WebElement PMradio;
	
	@FindBy(id="LastAccessDateUnknown")
	private WebElement unknownLastAcceschk;
	
	
	

	public Vacate_Selspacepage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isDisplayed_VacateNowTxt(){
		return vacateNowtxt.isDisplayed();
	}
	
	
	public String get_vacateNowtxt(){
		return vacateNowtxt.getText();
	}
	
	public void clk_StdVacateRadioBtn(){
		stdVacate_Radio.click();
		
	}
	
	public void clk_SkipVacateRadioBtn(){
		skpVacate_Radio.click();
		
	}
	
	public String get_spacesToBeVacatedtxt(){
		return spacesToBeVacatedtxt.getText();
	}
	
	public void clk_BackToDashBoardBtn(){
		if(backToDashBoardBtn.isEnabled()){
			backToDashBoardBtn.click();
		}
		
	}
	
	public void clk_cancelBtn(){
		if(cancelBtn.isEnabled()){
			cancelBtn.click();
		}
		
	}
	
	
	public void clk_continuelBtn(){
		if(continueBtn.isEnabled()){
			continueBtn.click();
		}
		
	}
	
	public String get_lastgatetxt(){
		return lastGatetxt.getText();
	}
	
	
	public void clk_calender(){
		selCalender.click();
	}
	
	public void enter_atHours(String input){
		atHourstxt.sendKeys(input);
	}
	
	public void enter_atMinutes(String input){
		atMinutestxt.sendKeys(input);
	}
	
	public void clk_AMradio(){
		AMradio.click();
	}
	
	public void clk_PMradio(){
		PMradio.click();
	}
	
	public void clk_unknownlastAccess(){
		unknownLastAcceschk.click();
	}
	
	@FindBy(id="lnkReserve")
	private WebElement continue_Btn;
	
	

	public void spaceToVacateTable(String space,String blncDue,String PastDue){
		
		String xpath_start="//div[@class='k-grid-content ps-container']/table/tbody/tr[";
		String xpath_end="]/td[1]";
		
		int i=1;
		
		while(isElementPresent(xpath_start+i+xpath_end)){
			
			String spaceno=driver.findElement(By.xpath(xpath_start+i+xpath_end)).getText();
			if(spaceno.equalsIgnoreCase(spaceno.trim())){
				
			System.out.println("Space no is found:"+spaceno);
			
			String balnceDueXpath=xpath_start+i+xpath_end.replace("td[1]", "td[2]");
			
			String balnceDue =driver.findElement(By.xpath(balnceDueXpath)).getText();
			
			
			if(blncDue.equalsIgnoreCase(balnceDue.trim())){
				
				System.out.println("Balance due amt is found:"+balnceDue);
			}
			
			
			}
					
			i++;
		}
		
}
	
	public boolean isElementPresent(String elementXpath){
		int count=driver.findElements(By.xpath(elementXpath)).size();
		if(count==0)
		return false;
		else
		return true;
		}

	public void click_Continuebutton() {
		continue_Btn.click();
	}
	

}
