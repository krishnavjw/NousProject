package Pages.AdvSearchPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResCustSearchPage {
	WebDriver driver;
	public ResCustSearchPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
@FindBy(xpath="//input[@id='SearchContract_FirstName']")
private WebElement txt_firstName;

@FindBy(id="SearchForTheThings")
private WebElement btn_search;

@FindBy(id="SearchContract_PropertyNumber")
private WebElement txt_locationNum;

@FindBy(id="TotalQueryResults")
private WebElement NoResultsEM;

public void enter_txt_firstName(String firstName){
	txt_firstName.sendKeys(firstName);
}

public void click_btn_search(){
	btn_search.click();
}

public void enter_txt_locationNum(String locNum) throws InterruptedException{
	txt_locationNum.clear();
	Thread.sleep(1000);
	txt_locationNum.click();
	txt_locationNum.sendKeys(locNum);
}

public String get_txt_locationNum(){
	return txt_locationNum.getAttribute("value");
}

public void clear_enter_txt_firstName(){
	txt_firstName.clear();
}

public String get_NoResultsEM(){
	return NoResultsEM.getText().trim();
}
}
