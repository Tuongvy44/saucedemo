import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import sample.BasePage

String homePageUrl = GlobalVariable.urlSaucedemo
String userName = GlobalVariable.username
String password = GlobalVariable.password
String blockUserName = GlobalVariable.blockUserName
String performanceUserName = GlobalVariable.performanceGlitchUserName
String problemUser = GlobalVariable.problemUserName

String btnItem = 'div[class="inventory_item_name"]'
String txtItem = 'div[class="inventory_item_desc"]'
String btnBackToProduct = 'button[id="back-to-products"]'
String btnResetAppState = 'a[id="reset_sidebar_link"]'
String btnCloseMenu = 'button[id="react-burger-cross-btn"]'
String itemAdd = '//button[text()="Add to cart"]'
String itemRemove = '//button[text()="Remove"]'
String btnAllItem = 'a[id="inventory_sidebar_link"]'
String errorMessage = 'div[class="error-message-container error"]'
String appLogo = 'div[class="app_logo"]'
String btnCard = 'a[class="shopping_cart_link"]'
String btnContinueShoping = 'button[id="continue-shopping"]'
String btnCheckOut = 'button[id="checkout"]'
String inputFirstName = 'input[id="first-name"]'
String inputLastName = 'input[id="last-name"]'
String inputZipCode = 'input[id="postal-code"]'
String btnContinue = 'input[id="continue"]'

int totalItem = 6

'Login Standard user'
CustomKeywords.'sample.Login.loginUser'(homePageUrl, userName, password)

WebDriver driver = DriverFactory.getWebDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
WebDriverWait explicitWait = new WebDriverWait(driver, 60);

String nameItem1 = driver.findElement(By.cssSelector(btnItem)).getText().trim()
String descItem1 = driver.findElement(By.cssSelector(txtItem)).getText().trim()

'click item 1'
driver.findElements(By.cssSelector(btnItem)).get(0).click()

'CHECK 01: check information item 1'
WebUI.verifyEqual(driver.findElement(By.cssSelector('div[class="inventory_details_name large_size"]')).getText().trim(), nameItem1)
WebUI.verifyEqual(driver.findElement(By.cssSelector('div[class="inventory_details_desc large_size"]')).getText().trim(), descItem1)

'click ADD TO CARD'
driver.findElement(By.xpath(itemAdd)).click()

'CHECK 02: check number 1 at card shoping'
WebUI.verifyEqual(driver.findElement(By.cssSelector('span[class="shopping_cart_badge"]')).getText().trim(), 1)

'click menu and click RESET APP STATE'
WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_burger_menu'), GlobalVariable.waitPresentTimeout)
WebUI.click(findTestObject('Object Repository/Product/btn_burger_menu'))
driver.findElement(By.cssSelector(btnResetAppState)).click()

'CHECK 03: check no number at card shoping, button Add to card visible'
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
WebUI.verifyEqual(driver.findElements(By.cssSelector('span[class="shopping_cart_badge"]')).size(), 0)
WebUI.verifyEqual(driver.findElements(By.xpath(itemAdd)).size()>0, true)
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
'Click to x'
driver.findElement(By.cssSelector(btnCloseMenu)).click()

'click BACK TO PRODUCT'
driver.findElement(By.cssSelector(btnBackToProduct)).click()

'click ADD TO CARD item 1'
driver.findElements(By.xpath(itemAdd)).get(0).click()

'CHECK 04: check number 1 at card shoping'
WebUI.verifyEqual(driver.findElement(By.cssSelector('span[class="shopping_cart_badge"]')).getText().trim(), 1)

'click menu and click RESET APP STATE'
WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_burger_menu'), GlobalVariable.waitPresentTimeout)
WebUI.click(findTestObject('Object Repository/Product/btn_burger_menu'))
driver.findElement(By.cssSelector(btnResetAppState)).click()

'CHECK 05: check no number at card shoping, button Add to card visible'
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
WebUI.verifyEqual(driver.findElements(By.cssSelector('span[class="shopping_cart_badge"]')).size(), 0)
WebUI.verifyEqual(driver.findElements(By.xpath(itemAdd)).size(), totalItem)
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

'Click to x'
driver.findElement(By.cssSelector(btnCloseMenu)).click()

'click item 1'
driver.findElements(By.cssSelector(btnItem)).get(0).click()

'click menu and click ALL ITEMS'
WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_burger_menu'), GlobalVariable.waitPresentTimeout)
WebUI.click(findTestObject('Object Repository/Product/btn_burger_menu'))
driver.findElement(By.cssSelector(btnAllItem)).click()

'CHECK 06: check all product present, screen PRODUCTS present'
WebUI.verifyEqual(driver.findElements(By.cssSelector(btnItem)).size()>0, true)
WebUI.verifyTextPresent("PRODUCTS", true)

CustomKeywords.'sample.Login.logout'()

'Login Block user'
CustomKeywords.'sample.Login.loginUser'(homePageUrl, blockUserName, password)

driver = DriverFactory.getWebDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
explicitWait = new WebDriverWait(driver, 60);

'CHECK 07: check message block user'
WebUI.verifyEqual(driver.findElements(By.cssSelector(errorMessage)).size(), 1)
WebUI.verifyTextPresent("Epic sadface: Sorry, this user has been locked out.", true)

driver.navigate().refresh()

'Login Performance glitch user'
CustomKeywords.'sample.Login.loginUser'(homePageUrl, performanceUserName, password)

driver = DriverFactory.getWebDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
explicitWait = new WebDriverWait(driver, 60);

String curentTime1 = CustomKeywords.'sample.BasePage.convertDateTime2Number'(BasePage.getCurrentTimeFormat())
KeywordUtil.logInfo(curentTime1)
explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(appLogo)));

String curentTime2 = CustomKeywords.'sample.BasePage.convertDateTime2Number'(BasePage.getCurrentTimeFormat())
KeywordUtil.logInfo(curentTime2)

'CHECK 08: check time login performance: if time >5 it had problem'
WebUI.verifyEqual(Long.parseLong(curentTime2)-Long.parseLong(curentTime1)>5, true)

CustomKeywords.'sample.Login.logout'()

'Login problem user'
CustomKeywords.'sample.Login.loginUser'(homePageUrl, problemUser, password)

driver = DriverFactory.getWebDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
explicitWait = new WebDriverWait(driver, 60);

'Click add to card 6 items'
List<WebElement> lstItemAdd = driver.findElements(By.xpath(itemAdd))
for (int i=0;i<2;i++) {
	lstItemAdd.get(i).click()
}

'Click to card'
driver.findElement(By.cssSelector(btnCard)).click()

'Click to CHECKOUT'
driver.findElement(By.cssSelector(btnCheckOut)).click()

'Input information'
driver.findElement(By.cssSelector(inputFirstName)).sendKeys("Nguyen")
driver.findElement(By.cssSelector(inputLastName)).sendKeys("Xavia")
driver.findElement(By.cssSelector(inputZipCode)).sendKeys("111111")

'Click to CONTINUE'
driver.findElement(By.cssSelector(btnContinue)).click()

'CHECK 09: screen CHECKOUT: OVERVIEW present'
WebUI.verifyTextPresent("CHECKOUT: OVERVIEW", true)

WebUI.closeBrowser()

driver.quit()