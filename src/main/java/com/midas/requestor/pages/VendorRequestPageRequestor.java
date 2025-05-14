package com.midas.requestor.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.TestUtil;

public class VendorRequestPageRequestor extends TestBase {


	@FindBy(xpath="//*[@src='assets/icons/heroicons/solid/chevron-double-left.svg']//*[name()='svg']")
	WebElement slider;

	@FindBy(xpath="//div[@class='flex flex-col gap-2 py-2']//div[1]//div[2]")
	WebElement vendorDropDown;

	@FindBy(xpath="//a[text()=\" Vendor Request \"]")
	WebElement vendorRequestIcon;

	@FindBy(xpath="//button[@type='button']//span[contains(text(),'Add Vendor')]")
	WebElement addVendorBtn;

	@FindBy(xpath="//span[text()='Add Vendor']")
	WebElement formTitle;

	@FindBy(xpath="//div[@class='p-multiselect-label p-placeholder']")
	WebElement opanSelectCompany;

	@FindBy(xpath="//input[@placeholder='Enter vendor name']")
	WebElement vendorNameField;

	@FindBy(xpath="//input[@placeholder='Enter mobile number']")
	WebElement mobNumField;

	@FindBy(xpath="//input[@placeholder='Enter vendor email']")
	WebElement emailField;

	@FindBy(xpath="//span[@aria-label='Select vendor']")
	WebElement openvendorTypeField;

	@FindBy(xpath="//input[@role='switch']")
	WebElement existingVendorForSimilarProduct;

	@FindBy(xpath="(//button[@type=\"submit\"])[2]")
	WebElement sudmitForm;

	@FindBy(xpath = "//input[@placeholder='Enter search terms']")
	WebElement searchTermField;

	@FindBy(xpath="(//input[@id=\"integeronly\"])[1]")
	WebElement estOrder;

	@FindBy(xpath="(//input[@id=\"integeronly\"])[2]")
	WebElement estSpend;

	@FindBy(xpath="//input[@placeholder='Enter finance forwarding mail']")
	WebElement financeMail;

	@FindBy(xpath="//textarea[@formcontrolname=\"requestReason\"]")
	WebElement requestReason;

	@FindBy(xpath="//span[contains(text(),'Reset')]")
	WebElement reset;

	@FindBy(xpath="//span[normalize-space()='Trash']")
	WebElement trashIcon;

	@FindBy(xpath="//img[@class='h-7 w-7 rounded-md ng-tns-c1517403531-4']")
	WebElement userIcon;

	@FindBy(xpath="//p[@class='text-foreground text-xs font-semibold break-all ng-tns-c1517403531-4']")
	WebElement userID;

	@FindBy(xpath="//img[@class='h-20 w-[150px] rounded-md']")
	WebElement clientLogo;

	@FindBy(xpath="//div[contains(@class, 'p-toast-message-text') or contains(@class, 'ng-star-inserted')]/div[2]")
	WebElement createdMesg;

	@FindBy(xpath="//input[@placeholder='Search']")
	WebElement searchBox;

	@FindBy(xpath="//span[normalize-space()='Search']")
	WebElement searchBtn;

	@FindBy(tagName = "tbody")
	WebElement searchResults;

	@FindBy(xpath="//button[@label=\"Trash\"]")
	WebElement trashBtn;

	@FindBy(xpath="//svg-icon[@svgclass='p-1 w-7 h-7 rounded-md text-gray-500 hover:text-gray-600']")
	WebElement actionsOption;

	@FindBy(xpath="(//button/svg-icon[@src=\"assets/icons/heroicons/outline/more.svg\"])[last()]")
	WebElement openTrashOption;

	@FindBy(xpath="//ul[@id=\"pn_id_77_list\"]/li[1]")
	WebElement restoreFromTrash;

	@FindBy(xpath="//ul[@id=\"pn_id_77_list\"]/li[2]")
	WebElement permenantDelete;

	@FindBy(xpath="//filtericon[contains(@class,\"p-component p-iconwrapper ng-tns-c\")]")
	List<WebElement> filterIcon;

	@FindBy(xpath="//span[normalize-space()='Apply']")
	WebElement applyFilter;

	@FindBy(xpath="//input[@class='p-inputtext p-component ng-star-inserted']")
	List<WebElement> FilterTextBox;

	private WebDriver driver;

	// Initializing the Page Objects:
	public VendorRequestPageRequestor() {
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
	}

	public String getPageTitle() {
		return getDriver().getTitle();
	}

	public void openVendorRequestPage() {
		TestUtil.waitAndClickElement(slider);		
		vendorDropDown.click();
		vendorRequestIcon.click();
	}
	public boolean openVendorForm() {
		TestUtil.waitAndClickElement(addVendorBtn);
		return formTitle.isDisplayed();	
	}
	public boolean validateManditaryField(String name,String mobNum,String email,String searchTerm, String estOrders, String estSpends, String financeEmail) {
		opanSelectCompany.click();
		vendorNameField.sendKeys(name);
		mobNumField.sendKeys(mobNum);
		emailField.sendKeys(email);
		openvendorTypeField.click();

		return false;		
	}
	public void selectCompany(String companies) {
		opanSelectCompany.click();
		String[] companyList = companies.split(",");

		for (String company : companyList) {
			String trimmed = company.trim(); // Remove extra spaces
			TestUtil.waitAndClickElementByVisualText(trimmed);
		}
	}
	public void OpenTrash() {
		trashIcon.click();
	}
	public boolean sudmitBtnEnabledStatus() {
		return sudmitForm.isEnabled();
	}

	public void giveNameToRequestForm(String name) {
		vendorNameField.sendKeys(name);
	}

	public void giveMobNumToRequestForm(String mobNum) {
		mobNumField .sendKeys(mobNum);
	}
	public void giveEmailToRequestForm(String email) {
		emailField.sendKeys(email);
	}
	public void selectVendorType(String text) {
		openvendorTypeField.click();
		TestUtil.waitAndClickElementByVisualText(text);
	}
	public void enableExistingVendorForSimilarProduct() {
		if (!existingVendorForSimilarProduct.isEnabled()) {
			existingVendorForSimilarProduct.click();
		}		
	}
	public void giveSearchTerm(String searchTerm) {
		searchTermField.sendKeys(searchTerm);
	}

	public void giveEstOrders(String estOrders) {
		estOrder.sendKeys(estOrders);
	}
	public void giveEstSpends(String estSpends) {
		estSpend.sendKeys(estSpends);
	}
	public void giveFinanceEmail(String financeEmail) {
		financeMail.sendKeys(financeEmail);
	}

	public String validateUserName() {	
		TestUtil.waitAndClickElement(userIcon);
		return userID.getText();
	}

	public boolean validateClientLogo() {
		return clientLogo.isDisplayed();
	}

	public void giverRequestReason(String requestreason) {
		requestReason.sendKeys(requestreason);
	}

	public void clickSudmit() {
		TestUtil.waitAndClickElement(sudmitForm);		
	}

	public String verifyNotificationText(String text) {
		return TestUtil.waitAndGetText(driver.findElement(By.xpath("//*[contains(text(),'"+text+"')]")));	
	}

	public void clearForm() {
		TestUtil.waitAndClickElement(reset);		
	}

	public boolean searchTest(String searchData) {
		searchBox.sendKeys(searchData);
		searchBtn.click();
		return searchResults.getText().contains(searchData);
	}

	public void openFilter(int i) {
		TestUtil.waitAndClickElement(filterIcon.get(i));
	}

	public void selectFilterMatchType(String selectType) {

		driver.findElement(By.xpath("//*[@aria-label='Match All']")).click();		
		driver.findElement(By.xpath("//li[@aria-label='"+selectType+"']")).click();		
	}

	public void selectFilterCondition(String condition) {
		driver.findElement(By.xpath("//*[@aria-label='Starts with']")).click();
		driver.findElement(By.xpath("//li[@aria-label='"+condition+"']")).click();
	}

	public void giveConditionTextFilterBox(String filterText) {
		FilterTextBox.get(0).sendKeys(filterText);        //the grid column order may change in future based on that change the List order
	}

	public void clickApplyFilter() {
		applyFilter.click();
	}

	public boolean validateTableColumnSort(int columnIndex, String sortOrder) {
	    List<WebElement> columnCells = driver.findElements(By.xpath("//table/tbody/tr/td["+columnIndex+"]"));
	    int size = columnCells.size();

	    for (int i = 0; i < size - 1; i++) {
	        String current = columnCells.get(i).getText().trim().toLowerCase();
	        String next = columnCells.get(i + 1).getText().trim().toLowerCase();

	        if (sortOrder.equalsIgnoreCase("ascending")) {
	            if (current.compareTo(next) > 0) {
	                System.out.println("Ascending sort failed between: '" + current + "' and '" + next + "'");
	                return false;
	            }
	        } else if (sortOrder.equalsIgnoreCase("descending")) {
	            if (current.compareTo(next) < 0) {
	                System.out.println("Descending sort failed between: '" + current + "' and '" + next + "'");
	                return false;
	            }
	        } else {
	            throw new IllegalArgumentException("Unsupported sort order: " + sortOrder);
	        }
	    }
	    return true;
	}

	public boolean FilterResultsValidation(String filterText, String filterType, int columnIndex) {
		List<WebElement> cells = driver.findElements(By.xpath("//table/tbody/tr/td["+columnIndex+"]"));

		for (WebElement cell : cells) {
			String cellText = cell.getText().trim();

			switch (filterType.toLowerCase()) {
			case "contains":
				if (!cellText.contains(filterText)) {
					System.out.println("Validation failed: '" + cellText + "' does not contain '" + filterText + "'");
					return false;
				}
				break;

			case "starts with":
				if (!cellText.startsWith(filterText)) {
					System.out.println("Validation failed: '" + cellText + "' does not start with '" + filterText + "'");
					return false;
				}
				break;

			case "ends with":
				if (!cellText.endsWith(filterText)) {
					System.out.println("Validation failed: '" + cellText + "' does not end with '" + filterText + "'");
					return false;
				}
				break;

			case "equals":
				if (!cellText.equals(filterText)) {
					System.out.println("Validation failed: '" + cellText + "' is not equal to '" + filterText + "'");
					return false;
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported filter type: " + filterType);
			}}
		return true;
	}


	public void clickcolumn(String string) {
		WebElement element = driver.findElement(By.xpath("//*[normalize-space()='"+string+"']"));
		TestUtil.waitAndClickElement(element);
	}

}