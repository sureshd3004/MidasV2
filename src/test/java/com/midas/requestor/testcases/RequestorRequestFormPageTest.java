package com.midas.requestor.testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.FileUtil;
import com.midas.qa.util.Log;
import com.midas.qa.util.MailUtils;
import com.midas.qa.util.TestUtil;
import com.midas.requestor.pages.VendorRequestPageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;

public class RequestorRequestFormPageTest extends TestBase {
	LoginPageRequestor loginPageRequestor;
	VendorRequestPageRequestor vendorRequestFormPage;

	SoftAssert sassert;

	public RequestorRequestFormPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		sassert = new SoftAssert();
		loginPageRequestor = new LoginPageRequestor();
		vendorRequestFormPage = new VendorRequestPageRequestor();
		loginPageRequestor.requestorSSOLogin(prop.getProperty("username"), prop.getProperty("password"));
		vendorRequestFormPage.openVendorRequestPage();
		Log.info("Test Starts");		
	}	

	@Test()
	public void verifyRequetorPageTitleTest(){		
		String homePageTitle = vendorRequestFormPage.getPageTitle();
		Assert.assertEquals(homePageTitle, "Sign-in","Requestor page title not matched");		
	}

	@Test()
	public void verifyuserName(){	
		Assert.assertEquals(vendorRequestFormPage.validateUserName(),prop.getProperty("username"));		
	}   

	@Test()
	public void ClientLogoTest(){
		Assert.assertTrue(vendorRequestFormPage.validateClientLogo());		
	}

	@Test()
	public void openVendorRequestPageTest(){		
		vendorRequestFormPage.openVendorRequestPage();
		boolean formTitle = vendorRequestFormPage.openVendorForm();
		Assert.assertTrue(formTitle);	
	}

	@Test(dataProvider = "vendorRequestData")
	public void checkManditaryFields(String company,String name,String mobNum,String email,String vendorType,String searchTerm,String ExistingVendor, String estOrders, String estSpends, String financeEmail,String RequestReason) {
		vendorRequestFormPage.openVendorForm();
		sassert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
		vendorRequestFormPage.selectCompany(company);
		sassert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
		vendorRequestFormPage.giveNameToRequestForm(name);

		sassert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
		vendorRequestFormPage.giveMobNumToRequestForm(mobNum);
		sassert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
		vendorRequestFormPage.giveEmailToRequestForm(email);
		sassert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
		vendorRequestFormPage.selectVendorType(vendorType);
		sassert.assertTrue(vendorRequestFormPage.sudmitBtnEnabledStatus());
		sassert.assertAll();

	}

	@Test(dataProvider = "vendorRequestData")
	public void ResetRequestFormDataTest(String company,String name,String mobNum,String email,String vendorType,String searchTerm,String ExistingVendor, String estOrders, String estSpends, String financeEmail,String RequestReason) {
		vendorRequestFormPage.openVendorForm();
		vendorRequestFormPage.selectCompany(company);

		vendorRequestFormPage.giveNameToRequestForm( TestUtil.getRandomNumericString(5));	

		vendorRequestFormPage.clearForm();
		vendorRequestFormPage.giveMobNumToRequestForm(mobNum);
		vendorRequestFormPage.clearForm();
		vendorRequestFormPage.giveEmailToRequestForm( TestUtil.getRandomNumericString(5)+"test@123.com");
		vendorRequestFormPage.selectVendorType(vendorType);
		if (ExistingVendor.equalsIgnoreCase("yes")) {
			vendorRequestFormPage.enableExistingVendorForSimilarProduct();
		}
		vendorRequestFormPage.giveSearchTerm(searchTerm);
		vendorRequestFormPage.giveEstOrders(estOrders);
		vendorRequestFormPage.giveEstSpends(estSpends);
		vendorRequestFormPage.giveFinanceEmail(financeEmail);
		vendorRequestFormPage.giverRequestReason(RequestReason);
		vendorRequestFormPage.clearForm();
		Assert.assertFalse(vendorRequestFormPage.sudmitBtnEnabledStatus());
	}
	@Test(dataProvider = "vendorRequestData")
	public void CreatevendorRequestTest(String company,String name,String mobNum,String email,String vendorType,String searchTerm,String ExistingVendor, String estOrders, String estSpends, String financeEmail,String RequestReason) throws IOException {
		vendorRequestFormPage.openVendorForm();
		vendorRequestFormPage.selectCompany(company);
		vendorRequestFormPage.giveNameToRequestForm( TestUtil.getRandomNumericString(5)+"name");	

		vendorRequestFormPage.giveMobNumToRequestForm(mobNum);	
		String mail = MailUtils.createTemporaryEmail();
		vendorRequestFormPage.giveEmailToRequestForm(mail);
		FileUtil.writeDataToProperties("mail",mail);
		vendorRequestFormPage.selectVendorType(vendorType);
		if (ExistingVendor.equalsIgnoreCase("yes")) {
			vendorRequestFormPage.enableExistingVendorForSimilarProduct();
		}
		vendorRequestFormPage.giveSearchTerm(searchTerm);
		vendorRequestFormPage.giveEstOrders(estOrders);
		vendorRequestFormPage.giveEstSpends(estSpends);
		vendorRequestFormPage.giveFinanceEmail(financeEmail);
		vendorRequestFormPage.giverRequestReason(RequestReason);
		vendorRequestFormPage.clickSudmit();
		String text =  vendorRequestFormPage.verifyNotificationText("Created");
		Assert.assertEquals("Created", text);
	}

	@Test(dataProvider = "vendorRequestData")
	public void ManditaryFieldvendorRequestTest(String company,String name,String mobNum,String email,String vendorType,String searchTerm,String ExistingVendor, String estOrders, String estSpends, String financeEmail,String RequestReason) {
		vendorRequestFormPage.openVendorForm();
		vendorRequestFormPage.selectCompany(company);
		vendorRequestFormPage.giveNameToRequestForm(getDriver().findElement(By.xpath("//tbody/tr/td[2]")).getText());	

		vendorRequestFormPage.giveMobNumToRequestForm(mobNum);

		vendorRequestFormPage.giveEmailToRequestForm(getDriver().findElement(By.xpath("//tbody/tr/td[3]")).getText());
		vendorRequestFormPage.selectVendorType(vendorType);
		vendorRequestFormPage.clickSudmit();

		String text = vendorRequestFormPage.verifyNotificationText("Vendor name already");
		sassert.assertEquals("Vendor name already exists", text);
		vendorRequestFormPage.giveNameToRequestForm(TestUtil.getRandomNumericString(5));	
		vendorRequestFormPage.clickSudmit();
		String text1 = vendorRequestFormPage.verifyNotificationText("Vendor email already");
		sassert.assertEquals("Vendor email already exists", text1);
		sassert.assertAll();
	}

	@Test(dataProvider = "SearchData")
	public void VendorSearchFieldTest(String searchData) {
		Assert.assertTrue(vendorRequestFormPage.searchTest(searchData));
	}

	@Test(enabled = false)
	public void VendorRequestTrashTest() {
		vendorRequestFormPage.OpenTrash();
               
	}
	@Test()
	public void VendorRequestGridCompanyNameSortTest() {
		vendorRequestFormPage.openVendorRequestPage();
		vendorRequestFormPage.clickcolumn("Company name");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(1, "ascending"));
		vendorRequestFormPage.clickcolumn("Company name");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	
	@Test()
	public void VendorRequestGridVendorNameSortTest() {
		vendorRequestFormPage.clickcolumn("Vendor name");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(2, "ascending"));
		vendorRequestFormPage.clickcolumn("Vendor name");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(2, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void VendorRequestGridEmailSortTest() {
		vendorRequestFormPage.clickcolumn("Email");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(3, "ascending"));
		vendorRequestFormPage.clickcolumn("Email");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(3, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void VendorRequestGridContactNumberSortTest() {
		vendorRequestFormPage.clickcolumn("Contact number");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(4, "ascending"));
		vendorRequestFormPage.clickcolumn("Contact number");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(4, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void VendorRequestGridVendorTypeSortTest() {
		vendorRequestFormPage.clickcolumn("Vendor Type");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(5, "ascending"));
		vendorRequestFormPage.clickcolumn("Vendor Type");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(5, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void VendorRequestGridStatusSortTest() {
		vendorRequestFormPage.clickcolumn("Status");	
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(6, "ascending"));
		vendorRequestFormPage.clickcolumn("Status");
		sassert.assertTrue(vendorRequestFormPage.validateTableColumnSort(6, "descending"));
		sassert.assertAll();
	}
	
	@Test(dataProvider = "FilterTestData")
	public void CompanyNameFilterTest(String selectType, String condition, String filterText) {		
		int coloumnIndex = 0;
		vendorRequestFormPage.openFilter(coloumnIndex);
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition);
		vendorRequestFormPage.giveConditionTextFilterBox(filterText);

		vendorRequestFormPage.clickApplyFilter();		
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText,condition,coloumnIndex+1));
	}
	@Test(dataProvider = "FilterTestData")
	public void VendorNameNameFilterTest(String selectType, String condition1, String filterText1) {
		int coloumnIndex = 1;
		vendorRequestFormPage.openFilter(coloumnIndex );
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition1);     //ends with 
		vendorRequestFormPage.giveConditionTextFilterBox(filterText1);         //ion

		vendorRequestFormPage.clickApplyFilter();	
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText1,condition1,coloumnIndex+1));
	}
	@Test(dataProvider = "FilterTestData")
	public void EmailFilterTest(String selectType, String condition, String filterText) {		
		int coloumnIndex = 2;
		vendorRequestFormPage.openFilter(coloumnIndex);
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition);
		vendorRequestFormPage.giveConditionTextFilterBox(filterText);

		vendorRequestFormPage.clickApplyFilter();		
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText,condition,coloumnIndex+1));
	}
	
	@Test(dataProvider = "FilterTestData")
	public void ContactNumberFilterTest(String selectType, String condition, String filterText) {		
		int coloumnIndex = 3;
		vendorRequestFormPage.openFilter(coloumnIndex);
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition);
		vendorRequestFormPage.giveConditionTextFilterBox(filterText);

		vendorRequestFormPage.clickApplyFilter();		
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText,condition,coloumnIndex+1));
	}
	@Test(dataProvider = "FilterTestData")
	public void VendorTypeFilterTest(String selectType, String condition, String filterText) {		
		int coloumnIndex = 4;
		vendorRequestFormPage.openFilter(coloumnIndex);
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition);
		vendorRequestFormPage.giveConditionTextFilterBox(filterText);

		vendorRequestFormPage.clickApplyFilter();		
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText,condition,coloumnIndex+1));
	}
	@Test(dataProvider = "FilterTestData")
	public void StatusFilterTest(String selectType, String condition, String filterText) {		
		int coloumnIndex = 5;
		vendorRequestFormPage.openFilter(coloumnIndex);
		vendorRequestFormPage.selectFilterMatchType(selectType);
		vendorRequestFormPage.selectFilterCondition(condition);
		vendorRequestFormPage.giveConditionTextFilterBox(filterText);

		vendorRequestFormPage.clickApplyFilter();		
		Assert.assertTrue(vendorRequestFormPage.FilterResultsValidation(filterText,condition,coloumnIndex+1));
	}
	
	
	
	@DataProvider(name = "SearchData")
	public Object[][] getSearchData() {
		Object[] data = FileUtil.readColumnData("SearchData", "SearchData");

		Object[][] result = new Object[data.length][1];
		for (int i = 0; i < data.length; i++) {
			result[i][0] = String.valueOf(data[i]); // ensures everything is a string
		}
		return result;
	}

	@DataProvider(name = "FilterTestData")
	public Object[][] getFilterData(Method testMethod) { 
		  String filterColumnCriteria = null;

		    if (testMethod.getName().contains("CompanyName")) {
		        filterColumnCriteria = "Company name";
		    } else if (testMethod.getName().contains("Email")) {
		        filterColumnCriteria = "Email";
		    } else if (testMethod.getName().contains("ContactNumber")) {
		        filterColumnCriteria = "Contact Number";
		    } else if (testMethod.getName().contains("VendorName")) {
		        filterColumnCriteria = "Vendor Name";
		    } else if (testMethod.getName().contains("Status")) {
		        filterColumnCriteria = "Status";
		    } else if (testMethod.getName().contains("VendorType")) {
		        filterColumnCriteria = "Vendor Type";
		    }
		    return FileUtil.getDataBasedOnColumnAvalues("FilterData", filterColumnCriteria);
		}

	@DataProvider(name = "vendorRequestData")
	public Object[][] getVendorFormData(Method testMethod) {
		return FileUtil.getDataBasedOnColumnAvalues("RequestData", "Single");
	}

}
