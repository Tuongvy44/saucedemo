package sample

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword



public class BasePage {
	@Keyword
	def static boolean isSortByTextAscending(WebDriver driver, String locatorString) {
		ArrayList<String> UIList = new ArrayList<String>();
		List<WebElement> nameText = driver.findElements(By.cssSelector(locatorString));
		for (WebElement item : nameText) {
			UIList.add(item.getText());
			
		}
		ArrayList<String> sortList = new ArrayList<String>();
		for (String name : UIList) {
			sortList.add(name);
		}
		Collections.sort(sortList);
		return sortList.equals(UIList);
	}
	
	@Keyword
	def static boolean isSortByNumberAscending(WebDriver driver, String locatorString) {
		ArrayList<Float> UIList = new ArrayList<Float>();
		List<WebElement> nameText = driver.findElements(By.cssSelector(locatorString));
		for (WebElement item : nameText) {
			UIList.add(Float.parseFloat(item.getText().replace("\$", "")));
		}
		ArrayList<Float> sortList = new ArrayList<Float>();
		for (Float name : UIList) {
			sortList.add(name);
		}
		Collections.sort(sortList);
		return sortList.equals(UIList);
	}
	
	@Keyword
	def static boolean isSortByTextDescending(WebDriver driver, String locatorString) {
		ArrayList<String> UIList = new ArrayList<String>();
		List<WebElement> nameText = driver.findElements(By.cssSelector(locatorString));
		for (WebElement item : nameText) {
			UIList.add(item.getText());
		}
		ArrayList<String> sortList = new ArrayList<String>();
		for (String name : UIList) {
			sortList.add(name);
		}
		Collections.sort(sortList);
		Collections.reverse(sortList);
		
		return sortList.equals(UIList);
	}
	
	@Keyword
	def static boolean isSortByNumberDescending(WebDriver driver, String locatorString) {
		ArrayList<Float> UIList = new ArrayList<Float>();
		List<WebElement> nameText = driver.findElements(By.cssSelector(locatorString));
		for (WebElement item : nameText) {
			UIList.add(Float.parseFloat(item.getText().replace("\$", "")));
		}
		ArrayList<Float> sortList = new ArrayList<Float>();
		for (Float name : UIList) {
			sortList.add(name);
		}
		Collections.sort(sortList);
		Collections.reverse(sortList);
		
		return sortList.equals(UIList);
	}
	
	@Keyword
	def static void switchToNextTab(WebDriver driver, String parentId) {
		Set<String> allWindow = driver.getWindowHandles();

		for (String runWindow : allWindow) {
			if (!runWindow.equals(parentId)) {
				driver.switchTo().window(runWindow);
				break;

			}
		}

	}
	
	@Keyword
	def static boolean closeAllWindowWithoutParent(WebDriver driver, String parentId) {
		Set<String> allWindow = driver.getWindowHandles();

		for (String runWindow : allWindow) {
			if (!runWindow.equals(parentId)) {
				driver.switchTo().window(runWindow);
				driver.close();
			}

		}
		driver.switchTo().window(parentId);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;

	}
	
	@Keyword
	def static String getCurrentTimeFormat() {
		def date = new Date()
		DateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss")
		String dates = sdf.format(date)
		return dates
	}
	
	@Keyword
	Long convertDateTime2Number(String dateTimeText) {
		dateTimeText = dateTimeText.replace("-", "")
		dateTimeText = dateTimeText.replace(":", "")
		dateTimeText = dateTimeText.replace(" ", "")

		Long afterConvert = Long.parseLong(dateTimeText)
		return afterConvert
	}
}
