package com.midas.requestor.testcases;


import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.FileUtil;
import com.midas.qa.util.Log;
import com.midas.requestor.pages.FieldConfigurationPageRequestor;
import com.midas.requestor.pages.LoginPageRequestor;

public class RequestorFieldConfigPageTest extends TestBase {
	LoginPageRequestor loginPageRequestor;
	FieldConfigurationPageRequestor fieldConfigPageRequestor;
	SoftAssert sassert;

	public RequestorFieldConfigPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		sassert = new SoftAssert();
		loginPageRequestor = new LoginPageRequestor();
		fieldConfigPageRequestor = new FieldConfigurationPageRequestor();
		loginPageRequestor.requestorSSOLogin(prop.getProperty("username"), prop.getProperty("password"));
		fieldConfigPageRequestor.openConfigurationPage();
		Log.info("Test Starts");			
	}

	@Test()
	public void OpenConfigFormTest(){  		
		fieldConfigPageRequestor.openConfigurationForm();
		Assert.assertEquals(fieldConfigPageRequestor.openConfigurationForm(), "Add Mapping");
	}

	@Test(dataProvider = "FieldmappingData")
	public void FieldMappingFormTest(String fieldName, String requestType, String catugoryName, String location, String uesrType, String isERP, String isManditary, String isReadOnly){  
		fieldConfigPageRequestor.openConfigurationForm();
		fieldConfigPageRequestor.verifyDisplayName(fieldName);
		fieldConfigPageRequestor.selectRequestType(requestType);
		sassert.assertEquals(fieldConfigPageRequestor.getTextCatugoryName(), catugoryName);  
		fieldConfigPageRequestor.selectLocation(location);
		fieldConfigPageRequestor.selectUserType(uesrType);
		if (isERP.equalsIgnoreCase("yes")) {
			fieldConfigPageRequestor.clickIsERP();
		}
		if (isManditary.equalsIgnoreCase("yes")) {
			fieldConfigPageRequestor.clickisManditary();
		}
		if (isERP.equalsIgnoreCase("yes")) {
			fieldConfigPageRequestor.clickIsERP();
		}
		if (isReadOnly.equalsIgnoreCase("yes")) {
			fieldConfigPageRequestor.clickIsReadOnly();
		}
		fieldConfigPageRequestor.clickCreateMapping();
	}

	@Test()
	public void ConfigurationFieldNameSortTest() {
		String columnName = "Field Name";
		fieldConfigPageRequestor.clickcolumn(columnName);	
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "ascending"));
		fieldConfigPageRequestor.clickcolumn(columnName);
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void ConfigurationCategoryNameSortTest() {
		String columnName = "Field Name";
		fieldConfigPageRequestor.clickcolumn(columnName);	
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "ascending"));
		fieldConfigPageRequestor.clickcolumn(columnName);
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void ConfigurationLocationSortTest() {
		String columnName = "Field Name";
		fieldConfigPageRequestor.clickcolumn(columnName);	
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "ascending"));
		fieldConfigPageRequestor.clickcolumn(columnName);
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void ConfigurationRequestTypeSortTest() {
		String columnName = "Field Name";
		fieldConfigPageRequestor.clickcolumn(columnName);	
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "ascending"));
		fieldConfigPageRequestor.clickcolumn(columnName);
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	@Test()
	public void ConfigurationUserTypeSortTest() {
		String columnName = "Field Name";
		fieldConfigPageRequestor.clickcolumn(columnName);	
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "ascending"));
		fieldConfigPageRequestor.clickcolumn(columnName);
		sassert.assertTrue(fieldConfigPageRequestor.validateTableColumnSort(1, "descending"));
		sassert.assertAll();
	}
	
	@Test()
	public void DisplayNameFieldTest(String fieldName){  
		fieldConfigPageRequestor.openConfigurationForm();   
		Assert.assertEquals( fieldConfigPageRequestor.verifyDisplayName(fieldName), fieldName);
	}


	@DataProvider(name = "FieldmappingData")
	public Object[][] getVendorFormData() {
		return FileUtil.getDataBasedOnColumnAvalues("FieldMapping", "mapping");
	}
	
}