package Pages.Walkin_Reservation_Lease;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_AddNote {

	@FindBy(xpath="//div[@id='notesDialog']//textarea[@id='noteText']")
	private WebElement notes_Txt;
	
	@FindBy(xpath="//div[@id='notesDialog']//div//a[contains(text(),'Create Note')]")
	private WebElement createNoteBtn;
	
	@FindBy(xpath="//div[@id='notesDialog']//div//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn;

	public Leasing_AddNote(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enter_Notes(String text){
		notes_Txt.sendKeys(text);
	}
	
	public void clk_CreateNoteBtn(){
		createNoteBtn.click();
	}
	
	public void clk_CancelBtn(){
		cancelBtn.click();
	}
	
}

