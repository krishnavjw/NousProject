package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Search_ResultPage
{
	@FindBy(xpath="//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')]")
	public static List<WebElement> resultGridRow_Values;
	
	
	
	

}
