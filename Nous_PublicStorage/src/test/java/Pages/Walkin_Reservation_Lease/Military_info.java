package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Military_info {
	
	@FindBy(id="Form_BirthDate")
    private WebElement bithDate;
	
	@FindBy(id="Form_MilitaryIdentificationNumber")
    private WebElement militaryId;
	
	@FindBy(id="Form_DeploymentStartDate")
    private WebElement dutyStartDate;
	
	@FindBy(id="Form_MilitaryUnit")
    private WebElement unitName;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header js-military-branch']")
    private WebElement milBranch_drpDwn;
	
	@FindBy(id="Form_MilitarySupervisorFirstName")
    private WebElement name;
	
	@FindBy(id="Form_MilitarySupervisorLastName")
    private WebElement lName;
	
	@FindBy(id="Form_MilitarySupervisorTitle")
    private WebElement rank;
	
	@FindBy(xpath="//div[@class='editor-field floatleft js-phone-container']//span[text()='select']")
    private WebElement phone_drpDwn;
	
	@FindBy(xpath="//div[@id='Form_MilitarySupervisorPhoneType-list']/..//ul/li[contains(text(), 'Cell')]")
    private WebElement sel_phone_drpDwn;
	
	@FindBy(id="Form_AreaCode")
    private WebElement areaCode;
	
	@FindBy(id="Form_Exchange")
    private WebElement exchange;
	
	@FindBy(id="Form_LineNumber")
    private WebElement lineNumber;
	
	@FindBy(id="confirmWithCustomer")
    private WebElement confmWthCust;
	
	    
    public Military_info (WebDriver driver)
    {
           
           PageFactory.initElements(driver,this);
    }

    public void txt_bithDate(String val){
    	bithDate.sendKeys(val);
    }
    
    public void txt_militaryId(String val){
    	militaryId.sendKeys(val);
    }
    
    public void txt_dutyStartDate(String val){
    	dutyStartDate.sendKeys(val);
    }
    
    public void txt_unitName(String val){
    	unitName.sendKeys(val);
    }
    
    public void clk_milBranch_drpDwn(){
    	milBranch_drpDwn.click();
    }
    
    public void selVal_branch_DrpDwn(WebDriver driver){
    	List<WebElement> militaryBranch=driver.findElements(By.xpath("//div[@id='Form_MilitaryBranchTypeId-list']/..//ul/li"));
        String milBrach="Air Force ";
        
        for(WebElement branch: militaryBranch)
        {
               if((branch.getText()).contains(milBrach))
               	
               {
            	   branch.click();
                      break;
               }
        }
        
       }
    
    public void txt_Name(String val){
    	name.sendKeys(val);
    }
    
    public void txt_lName(String val){
    	lName.sendKeys(val);
    }
    
    public void txt_Rank(String val){
    	rank.sendKeys(val);
    }
    
    public void clk_PhDrpdwn(){
    	phone_drpDwn.click();
    }
    
    public void sel_PhType_Val(){
    	sel_phone_drpDwn.click();
    }
    
    public void txt_AreaCode(String val){
    	areaCode.sendKeys(val);
    }
    
    public void txt_Exchange(String val){
    	exchange.sendKeys(val);
    }
    
    public void txt_LineNum(String val){
    	lineNumber.sendKeys(val);
    }
    
    public void clk_cnfmWtCust(){
    	confmWthCust.click();
    }
    
    //=============Anjana=============
    
	@FindBy(xpath="//h3[contains(text(),'Military Status')]")
    private WebElement pageTitle;
	
	public boolean verify_pagetitle(){
		return pageTitle.isDisplayed();
		
	}
	
	@FindBy(xpath="//h3[contains(text(),'Military Status')]")
    private WebElement dropdown;
	
	
	
	@FindBy(xpath="//ul/li[text()='Inactive ']")
    private WebElement inactive;
	
	
	public void sel_Inactive(){
		inactive.click();;
		
		}

	public void click_DutyTypedrpdown(){
		dropdown.click();;
		
		}
    
}
