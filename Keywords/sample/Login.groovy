package sample


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
/*
 * Open browser
 * Navigate to URL -> get to Global variable
 * Provide username and password
 * Click on Login
 * */
/*
 * Will read value from Global Variable
 * */
public class Login {
	@Keyword
	def static void loginUser(String applicationURL,String username,String password){

		WebUI.openBrowser(applicationURL)
		WebUI.waitForPageLoad(GlobalVariable.waitPresentTimeout)
		WebUI.maximizeWindow()

		WebUI.waitForElementVisible(findTestObject('Object Repository/Login/btn_login'), GlobalVariable.waitPresentTimeout)

		WebUI.sendKeys(findTestObject('Object Repository/Login/input_username'), username)
		WebUI.sendKeys(findTestObject('Object Repository/Login/input_password'), password)
		WebUI.click(findTestObject('Object Repository/Login/btn_login'))
	}

	@Keyword
	def static void logout(){
		WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_burger_menu'), GlobalVariable.waitPresentTimeout)
		WebUI.click(findTestObject('Object Repository/Product/btn_burger_menu'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_logout'), GlobalVariable.waitPresentTimeout)
		WebUI.click(findTestObject('Object Repository/Product/btn_logout'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Login/btn_login'), GlobalVariable.waitPresentTimeout)
	}
}
