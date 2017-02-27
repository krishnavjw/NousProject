package Pages.PropertyManagementPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.DataBase_JDBC;

public class DownloadCompletePopUp 
{
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']//span[text()='Download Complete']")
	private WebElement download_Title;
	
	@FindBy(xpath="//div[@id='gateAccessCodesDownloaded']/div[contains(text(),'Gate Access Codes')]")
	private WebElement download_msg;
	
	@FindBy(xpath="//div[@id='gateAccessCodesDownloaded']/following-sibling::div//a")
	private WebElement ok_btn;
	
	
	public DownloadCompletePopUp(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyDownloadCompleted_title(){
		return download_Title.isDisplayed();
	}
	
	public String getDownloadedAccessCodeMeg()
	{
		return download_msg.getText();
	}
	
	public String verifyNumOfGateCodes(){
		String query="select count(cg.gatecide)"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" where PS.siteid=90"+
				" and cg.disableaccess=0";
		
			
		String num=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(num);
		
		return num;
	}
	
	public void clickOk_btn()
	{
		ok_btn.click();
	}

}
