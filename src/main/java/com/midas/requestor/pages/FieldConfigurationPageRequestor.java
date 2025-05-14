package com.midas.requestor.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.midas.qa.base.TestBase;
import com.midas.qa.util.TestUtil;

public class FieldConfigurationPageRequestor extends TestBase{
	private WebDriver driver;

	@FindBy(xpath="//*[@src='assets/icons/heroicons/solid/chevron-double-left.svg']//*[name()='svg']")
	WebElement slider;

	@FindBy(xpath="//a[contains(@class,'tracking-wide focus:outline-hidden ng')][normalize-space()='Configuration']")
	WebElement configDropdown;

	@FindBy(xpath="//a[contains(@class,'inline-block text-background w-full')][normalize-space()='Field Configuration']")
	WebElement fieldConfigOption;

	@FindBy(xpath="//button[normalize-space()='Add Mapping']")
	WebElement addMappingbtn;

	@FindBy(xpath="//span[@class='text-base font-semibold text-primary-900 dark:text-white']")
	WebElement formTitle;

	@FindBy(xpath="//input[@placeholder='Select Field Name']")
	WebElement fieldNameOpen;

	@FindBy(xpath="//input[@id='categoryName']")
	WebElement categoryName;

	@FindBy(xpath="//input[@id='displayName']")
	WebElement displayNameField;

	@FindBy(xpath="//div[contains(text(),'Select Request Type')]")
	WebElement requestTypeField;

	@FindBy(xpath="//div[@class='p-multiselect-label']")
	WebElement locationField;

	@FindBy(xpath="//div[contains(text(),'Select User Type')]")
	WebElement userTypeField;

	@FindBy(xpath="//p-checkbox[@class='ng-valid ng-dirty ng-touched']//input[@value='undefined']")
	WebElement isERPField;

	@FindBy(xpath="//p-checkbox[@formcontrolname='isMandatory']//input[@value='undefined']")
	WebElement isManditary;

	@FindBy(xpath="//p-checkbox[@formcontrolname='isReadOnly']//input[@value='undefined']")
	WebElement isReadOnlyField;

	@FindBy(xpath="//button[contains(@class,'transition p-button p-component')][text()=' Create Mapping ']")
	WebElement creatingMapping;



	//Initializing the Page Objects:
	public FieldConfigurationPageRequestor(){
		this.driver = getDriver(); 
		PageFactory.initElements(driver, this);
	}

	//Actions:
	public String validatePageTitle(){
		return driver.getTitle();
	}

	public void openConfigurationPage(){
		TestUtil.waitAndClickElement(slider);		
		TestUtil.waitAndClickElement(configDropdown);
		fieldConfigOption.click();
	}
	public String openConfigurationForm(){
		addMappingbtn.click();
		return formTitle.getText();
	}
	public void selectFieldName(String fieldName) {
		fieldNameOpen.sendKeys(fieldName);
		driver.findElement(By.xpath("//span[normalize-space()='"+fieldName+"']")).click();
	}
	public void selectRequestType(String requestType) {
		requestTypeField.click();
		driver.findElement(By.xpath("//span[normalize-space()='"+requestType+"']")).click();	
	}
	public String verifyDisplayName(String fieldName) {
		fieldNameOpen.sendKeys(fieldName);
		driver.findElement(By.xpath("//span[normalize-space()='"+fieldName+"']")).click();
		return displayNameField.getText();
	}

	public String getTextCatugoryName() {     
		return categoryName.getText();
	}

	public void selectLocation(String location) {		
		locationField.click();
		String[] companyList = location.split(",");

		for (String company : companyList) {
			String trimmed = company.trim(); // Remove extra spaces
			TestUtil.waitAndClickElementByVisualText(trimmed);
		}
	}

	public void selectUserType(String uesrType) {
		userTypeField.click();
		String[] companyList = uesrType.split(",");
		for (String company : companyList) {
			String trimmed = company.trim(); // Remove extra spaces
			TestUtil.waitAndClickElementByVisualText(trimmed);
		}
	}

	public void clickIsERP() {
		isERPField.click();
	}

	public void clickisManditary() {
		isManditary.click();
	}

	public void clickIsReadOnly() {
		isReadOnlyField.click();
	}

	public void clickCreateMapping() {
		creatingMapping.click();
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

	public boolean resultsValidation(String filterText, String filterType, int columnIndex) {
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
