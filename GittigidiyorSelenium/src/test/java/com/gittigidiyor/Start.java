package com.gittigidiyor;

import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class Start {

	@Test

			public void shouldAnswer() throws InterruptedException {

		List<WebElement> result;

		System.setProperty("webdriver.chrome.driver","properties/driver/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.gittigidiyor.com");
		Actions action = new Actions(driver);

		WebElement element;
		element=driver.findElement(By.name("profile"));
		driver.manage().window().maximize();
		action.moveToElement(element).build().perform();
		driver.get("https://www.gittigidiyor.com/uye-girisi");

		driver.findElement(By.id("L-UserNameField")).sendKeys("seleniumdenemegg@gmail.com");
		driver.findElement(By.id("L-PasswordField")).sendKeys("112233a");
		driver.findElement(By.id("gg-login-enter")).click();
		driver.findElement(By.xpath("//input[@type='text' and @name='k']")).sendKeys("bilgisayar");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		driver.findElement(By.xpath("//div[@class='pager pt30 hidden-m gg-d-24']//ul//li[2]")).click();

		Random r=new Random();
		result = driver.findElements(By.xpath("//*[@class='blueWrapper clearfix']//*[@class='catalog-view clearfix products-container']//li//a//div//p//img"));
		int linkNo= r.nextInt(result.size());
		result.get(linkNo).click();
		//Thread.sleep(2000);


		String productPriceText;
		String productPriceBasket;
		WebElement productPrice;
		productPrice = driver.findElement(By.id("sp-price-lowPrice"));
		productPriceText=productPrice.getText();
		System.out.println(productPriceText);

		WebElement element2;
		element2=driver.findElement(By.xpath("//button[@type='button' and @id='add-to-basket']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
		element2.click();


		Thread.sleep(3000);


		driver.get("https://www.gittigidiyor.com/sepetim");
		productPriceBasket=driver.findElement(By.xpath("//div[@class='total-price']")).getText();
		System.out.println(productPriceBasket);


		//WebElement strvalue = driver.findElement(element2);
		String expected = productPriceText;
		//String actual = productPriceText.getText();
		System.out.println(productPriceText);
		if(expected.equals(productPriceBasket)){
			System.out.println("Pass");
		}
		else {
			System.out.println("Fail");
		}

		WebElement identifier = driver.findElement(By.xpath("//div[@class='gg-input gg-input-select ']//select[@class='amount']"));
		Select select = new Select(identifier);
		select.selectByVisibleText("2");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='btn-delete btn-update-item']")).click();





	}







	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();


		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");

		
		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();
		
		server.addHandler(bb);

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			while (System.in.available() == 0) {
				Thread.sleep(5000);
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
