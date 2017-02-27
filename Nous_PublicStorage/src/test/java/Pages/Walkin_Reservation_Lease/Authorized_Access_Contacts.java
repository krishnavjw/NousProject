package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Authorized_Access_Contacts {

	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__FirstName")
	private WebElement authoriseduser_firstname;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__LastName")
	private WebElement authoriseduser_lastname;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_AreaCode")
	private WebElement areaCode;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_Exchange")
	private WebElement phone_Exchg;
	
	@FindBy(id="AuthorizedUsersForm_AuthorizedUsers__-index-__0__Phone_LineNumber")
	private WebElement phone_LineNumber;
	
	@FindBy(id="customerSaveButton")
	private WebElement save_button;
	
	
	
	public Authorized_Access_Contacts(WebDriver driver)
	{
		
		PageFactory.initElements(driver,this);
	}
	
	public void clk_saveAndProceedbutton(){
		save_button.click();
	}
	
	public void txt_Fname(String fname){
		authoriseduser_firstname.sendKeys(fname);
	}
	
	public void clk_savebutton(){
		save_button.click();
	}
		
		public void txt_Lname(String lname){
			authoriseduser_lastname.sendKeys(lname);
	}
		
	public void txt_AreaCode(String areacode){
		areaCode.sendKeys(areacode);
		
	}
	
	
	public void txt_Exchg(String exchg){
		phone_Exchg.sendKeys(exchg);
		
	}
	
	
	public void txt_lineNumber(String lineNum){
		phone_LineNumber.sendKeys(lineNum);
		
	}
	
}
