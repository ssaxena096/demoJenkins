//Create a TestNg base project for amazon (at least create test cases for the homepage, search page, and add to cart) with Assertions 
package com.demo.amazon;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class q3 {
	WebDriver driver;
	
	
	@BeforeMethod
	public void launch() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Shudham Saxena\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
	}
	
  @Test
  public void testHomePage() {
	  String expectedTitle = "Amazon";
	  String actualTitle = driver.getTitle();
	  System.out.println(actualTitle);
	  Assert.assertTrue(actualTitle.contains(expectedTitle)); // asserting that title contains amazon
	  
	  String expectedURL = "https://www.amazon.in/";
	  String actualURL = driver.getCurrentUrl();
	  System.out.println(actualURL);
	  Assert.assertTrue(actualURL.contains(expectedURL)); // asserting that we land on right url
	  
	  Assert.assertTrue(driver.findElement(By.id("nav-logo")).isDisplayed()); // assert that main logo is dsiplayed
  }
  
  @Test
  public void testSearchPage() {
	    String searchTerm = "toothpaste";
	    WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
	    System.out.println(searchBox.getCssValue("color"));
	    searchBox.sendKeys(searchTerm);
	    searchBox.submit();
	    String expectedTitle = "Amazon.in : " + searchTerm;
	    String actualTitle = driver.getTitle();
	    Assert.assertEquals(actualTitle, expectedTitle); // assert that title is same as searched item
	    
	    String expectedURL = "https://www.amazon.in/s?k=toothpaste&ref=nb_sb_noss";
		String actualURL = driver.getCurrentUrl();
		System.out.println(actualURL);
		Assert.assertTrue(actualURL.contains(expectedURL)); // asserting that we land on right url
	    
	    String expected = "RESULTS";
	    String actual = driver.findElement(By.xpath("//span[text()=\"RESULTS\"]")).getText();
	    Assert.assertEquals(actual, expected); // asserting that result page is shwoing a heading 'RESULTS'
	  }

  
  @Test
  public void testCart() throws InterruptedException{
	  WebElement cartbutton = driver.findElement(By.id("nav-cart-count-container"));
	  cartbutton.click();
	  
	  String expectedURL = "https://www.amazon.in/gp/cart/view.html?ref_=nav_cart";
	  String actualURL = driver.getCurrentUrl();
	  System.out.println(actualURL);
	  Assert.assertTrue(actualURL.contains(expectedURL)); // asserting that we land on right url 
	  
	  String expectedmessage = "Your Amazon Cart is empty";
	  String actualmessage = driver.findElement(By.xpath("//*[@id=\"sc-active-cart\"]/div/div/div[2]/div[1]/h2")).getText();
	  Assert.assertEquals(actualmessage, expectedmessage); // asserting that before adding anything cart is empty
	  
	  driver.navigate().back(); // back to homepage
	  
	  String searchTerm = "toothpaste";
	  WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
	  searchBox.sendKeys(searchTerm);
	  searchBox.submit();
	  
	  driver.findElement(By.xpath("//h2[1]//a[1]")).click();
	  Set<String> allhandles = driver.getWindowHandles();
	  driver.switchTo().window(allhandles.stream().toList().get(1)); //switching to the new tab
	  
	  driver.findElement(By.xpath("(//input[@id='add-to-cart-button'])[1]")).click();
	  
	  String expectedmessage1 = "Added to Cart";
	  String actualmessage1 = driver.findElement(By.xpath("//*[@id=\"NATC_SMART_WAGON_CONF_MSG_SUCCESS\"]/span")).getText();
	  Assert.assertEquals(actualmessage1, expectedmessage1); // asserting that item is added message is there
	  
	  driver.findElement(By.id("nav-cart-count-container")).click();// going back to cart
	  
	  Assert.assertTrue(driver.findElement(By.xpath("//form[@id='activeCartViewForm']")).isDisplayed()); // assert that there are some items in cart
	  
	  Thread.sleep(10000);
	  
  }
  
  @AfterMethod
  public void close() {
	  driver.quit();
  }

}
