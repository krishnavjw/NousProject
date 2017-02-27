package Pages.AuctionWorkBench;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectFilterOptions {

WebDriver driver;
	
@FindBy(id="filterModalWindow_wnd_title")
private WebElement modalWindowTitle;

@FindBy(id="btn_ApplyFilters")
private WebElement save_Btn;

@FindBy(xpath="//button[text()='Cancel']")
private WebElement cancel_Btn;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='noFilter']")
private WebElement chkbx_NoFilter;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='dmNotApproved']")
private WebElement chkbx_dmNotApproved;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='rmNotApproved']")
private WebElement chkbx_rmNotApproved;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='inventoryNotComplete']")
private WebElement chkbx_InventoryNotComplete;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='ad1NotComplete']")
private WebElement chkbx_Advertise1NotEntered;

@FindBy(xpath="//div[@id='filterModalWindow']/ul//li//input[@id='ad2NotComplete']")
private WebElement chkbx_Advertise2NotEntered;


public SelectFilterOptions(WebDriver driver){
       this.driver=driver;
       PageFactory.initElements(driver, this);
}

public boolean verify_ModalWindowTitle(){
       return modalWindowTitle.isDisplayed();
}

public void get_filterOptions(){
       List<WebElement> list=driver.findElements(By.xpath("//div[@id='filterModalWindow']/ul/li/label"));
       System.out.println(list.size());
       for(WebElement options:list){
              String filteroptions=options.getText().trim();
              System.out.println(filteroptions);
       }
}

public void click_NoFilter(){
       chkbx_NoFilter.click();
}

public void click_DmNotApproved(){
       chkbx_dmNotApproved.click();
}

public void click_RmNotApproved(){
       chkbx_rmNotApproved.click();
}

public void click_InventoryNotComplete(){
       chkbx_InventoryNotComplete.click();
}

public void click_Advertise1NotEntered(){
       chkbx_Advertise1NotEntered.click();
}

public void click_Advertise2NotEntered(){
       chkbx_Advertise2NotEntered.click();
}


public void click_savebtn(){
       save_Btn.click();
}

public void click_cancelbtn(){
       cancel_Btn.click();
}

}
