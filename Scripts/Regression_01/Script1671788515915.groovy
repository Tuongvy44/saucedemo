import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import sample.BasePage

String homePageUrl = GlobalVariable.urlSaucedemo
String userName = GlobalVariable.username
String password = GlobalVariable.password

String btnAllItem = 'a[id="inventory_sidebar_link"]'
String btnAbout = 'a[id="about_sidebar_link"]'
String btnResetAppState = 'a[id="reset_sidebar_link"]'
String btnCloseMenu = 'button[id="react-burger-cross-btn"]'
String appLogo = 'div[class="app_logo"]'
String selectSort = 'select[class="product_sort_container"]'
String btnCard = 'a[class="shopping_cart_link"]'
String btnContinueShoping = 'button[id="continue-shopping"]'
String btnCheckOut = 'button[id="checkout"]'
String inputFirstName = 'input[id="first-name"]'
String inputLastName = 'input[id="last-name"]'
String inputZipCode = 'input[id="postal-code"]'
String btnCancel = 'button[id="cancel"]'
String btnContinue = 'input[id="continue"]'
String btnFinish = 'button[name="finish"]'
String btnBackHome = 'button[id="back-to-products"]'
String itemAdd = '//button[text()="Add to cart"]'
String itemRemove = '//button[text()="Remove"]'
String priceProduct = 'div[class="inventory_item_price"]'
String nameProduct = 'div[class="inventory_item_name"]'
String btnTwitter = 'li[class="social_twitter"]>a'
String btnFacebook = 'li[class="social_facebook"]>a'
String btnLinkerdin = 'li[class="social_linkedin"]>a'

int totalItem = 6

'Login Standard user'
CustomKeywords.'sample.Login.loginUser'(homePageUrl, userName, password)

WebDriver driver = DriverFactory.getWebDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

'Click add to card 6 items'
List<WebElement> lstItemAdd = driver.findElements(By.xpath(itemAdd))
for (int i=0;i<6;i++) {
	lstItemAdd.get(i).click()
}

'Scroll to top'
JavascriptExecutor jsexecutor = (JavascriptExecutor) driver;
jsexecutor.executeScript("window.scrollTo(0,0)");

'CHECK 01: check number 6 at card item'
WebUI.verifyEqual(driver.findElement(By.cssSelector('span[class="shopping_cart_badge"]')).getText().trim(), totalItem.toString())

'Click remove 2 items'
List<WebElement> lstItemRemove = driver.findElements(By.xpath(itemRemove))
for (int i=0;i<2;i++) {
	lstItemRemove.get(i).click()
}

totalItem = totalItem -2

'CHECK 02: check number 4 at card item'
WebUI.verifyEqual(driver.findElement(By.cssSelector('span[class="shopping_cart_badge"]')).getText().trim(), totalItem.toString())

'Click to card'
driver.findElement(By.cssSelector(btnCard)).click()

'CHECK 03: screen YOUR CARD present'
WebUI.verifyTextPresent("YOUR CART", true)

'Click remove 1 item'
driver.findElements(By.xpath(itemRemove)).get(0).click()

totalItem = totalItem -1

'CHECK 04: check decrease 1 item'
WebUI.verifyEqual(driver.findElements(By.xpath(itemRemove)).size(), totalItem)

'Click btn CONTINUE SHOPPING'
driver.findElement(By.cssSelector(btnContinueShoping)).click()

'Scroll to top'
jsexecutor.executeScript("window.scrollTo(0,0)");

'Click to card'
driver.findElement(By.cssSelector(btnCard)).click()

'Click to CHECKOUT'
driver.findElement(By.cssSelector(btnCheckOut)).click()

'CHECK 05: screen CHECKOUT: YOUR INFORMATION present'
WebUI.verifyTextPresent("CHECKOUT: YOUR INFORMATION", true)

'Click to CANCEL'
driver.findElement(By.cssSelector(btnCancel)).click()

'CHECK: screen YOUR CARD present'
WebUI.verifyTextPresent("YOUR CART", true)

'Click to CHECKOUT'
driver.findElement(By.cssSelector(btnCheckOut)).click()

'Input information'
driver.findElement(By.cssSelector(inputFirstName)).sendKeys("Nguyen")
driver.findElement(By.cssSelector(inputLastName)).sendKeys("Xavia")
driver.findElement(By.cssSelector(inputZipCode)).sendKeys("111111")

'Click to CONTINUE'
driver.findElement(By.cssSelector(btnContinue)).click()

'CHECK 06: screen CHECKOUT: OVERVIEW present'
WebUI.verifyTextPresent("CHECKOUT: OVERVIEW", true)

'CHECK 07: present 3 item and total price'
WebUI.verifyEqual(driver.findElements(By.cssSelector('div[class="cart_quantity"]')).size(), totalItem)

float totalPrice = 0.0

List<WebElement> lstPrice = driver.findElements(By.cssSelector('div[class="inventory_item_price"]'))
for (int i=0; i<totalItem;i++) {
	String priceS = lstPrice.get(i).getText()
	String price = priceS.replace("\$", "")
	totalPrice += Float.parseFloat(price)
}

WebUI.verifyEqual(driver.findElement(By.cssSelector('div[class="summary_subtotal_label"]')).getText().contains(totalPrice.toString()), true)

'Click to CANCEL'
driver.findElement(By.cssSelector(btnCancel)).click()

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

'Click to FINISH'
driver.findElement(By.cssSelector(btnFinish)).click()

'CHECK 08: screen CHECKOUT: COMPLETE!'
WebUI.verifyTextPresent("CHECKOUT: COMPLETE!", true)

WebUI.verifyTextPresent("THANK YOU FOR YOUR ORDER", true)

'Click to BACK HOME'
driver.findElement(By.cssSelector(btnBackHome)).click()

Select slSort = new Select(driver.findElement(By.cssSelector(selectSort)))
slSort.selectByValue("lohi")

'CHECK 09: price low to high'
WebUI.verifyEqual(CustomKeywords.'sample.BasePage.isSortByNumberAscending'(driver, priceProduct), true)

slSort = new Select(driver.findElement(By.cssSelector(selectSort)))
slSort.selectByValue("hilo")

'CHECK 10: price high to low'
WebUI.verifyEqual(CustomKeywords.'sample.BasePage.isSortByNumberDescending'(driver, priceProduct), true)

slSort = new Select(driver.findElement(By.cssSelector(selectSort)))
slSort.selectByValue("az")

'CHECK 11: price A to Z'
WebUI.verifyEqual(CustomKeywords.'sample.BasePage.isSortByTextAscending'(driver, nameProduct), true)

slSort = new Select(driver.findElement(By.cssSelector(selectSort)))
slSort.selectByValue("za")

'CHECK 12: price Z to A'
WebUI.verifyEqual(CustomKeywords.'sample.BasePage.isSortByTextDescending'(driver, nameProduct), true)

String parentId = driver.getWindowHandle();

'Click to Twitter'
driver.findElement(By.cssSelector(btnTwitter)).click()
BasePage.switchToNextTab(driver, parentId)

'CHECK 13: screen Twitter present'
WebUI.verifyEqual(driver.getCurrentUrl().contains("twitter.com"), true)

BasePage.closeAllWindowWithoutParent(driver, parentId)

WebUI.delay(1)

'Click to Facebook'
driver.findElement(By.cssSelector(btnFacebook)).click()
BasePage.switchToNextTab(driver, parentId)

'CHECK 14: screen Facebook present'
WebUI.verifyEqual(driver.getCurrentUrl().contains("facebook.com"), true)

BasePage.closeAllWindowWithoutParent(driver, parentId)

WebUI.delay(1)

'Click to linkedin'
driver.findElement(By.cssSelector(btnLinkerdin)).click()
BasePage.switchToNextTab(driver, parentId)

'CHECK 15: screen linkedin present'
WebUI.verifyEqual(driver.getCurrentUrl().contains("linkedin.com"), true)

BasePage.closeAllWindowWithoutParent(driver, parentId)

WebUI.delay(1)

'Click btn ABOUT'
WebUI.waitForElementPresent(findTestObject('Object Repository/Product/btn_burger_menu'), GlobalVariable.waitPresentTimeout)
WebUI.click(findTestObject('Object Repository/Product/btn_burger_menu'))
driver.findElement(By.cssSelector(btnAbout)).click()

'CHECK 16: screen saucelabs present'
WebUI.verifyEqual(driver.getCurrentUrl().contains("saucelabs.com"), true)

driver.navigate().back()

'CHECK 17: screen saucedemo present'
WebUI.verifyEqual(driver.getCurrentUrl().contains("saucedemo.com"), true)

'Click to x'
driver.findElement(By.cssSelector(btnCloseMenu)).click()

CustomKeywords.'sample.Login.logout'()

WebUI.closeBrowser()

driver.quit()