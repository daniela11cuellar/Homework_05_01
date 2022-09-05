package Test;


import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Test_05_01 {


    private WebDriver driver;
    private String baseUrl;
    private WebElement elementUser;
    private WebElement elementPass;
    private WebElement title;
    private WebElement description;

    @BeforeMethod
    public void setUp () throws IOException {
        InputStream file = new FileInputStream("resources/config.properties");
        Properties properties = new Properties();
        properties.load(file);

        //System.setProperty(properties.getProperty("PROPERTY_FIRE"), properties.getProperty("PATH_FIRE"));
        //driver = new FirefoxDriver();

        //System.setProperty(properties.getProperty("PROPERTY_EDGE"), properties.getProperty("PATH_EDGE"));
        //driver = new EdgeDriver();

        System.setProperty(properties.getProperty("PROPERTY_CHROME"), properties.getProperty("PATH_CHROME"));
        driver = new ChromeDriver();

        baseUrl = "https://rahulshettyacademy.com/AutomationPractice/";
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    @Test
    public void testOne () throws InterruptedException {

        //random
        int min = 1;
        int max = 3;

        int index = ThreadLocalRandom.current().nextInt(min, max+1);
        System.out.println("random: "+index);

        //get radios
        List<WebElement> options = driver.findElements(By.name("radioButton")) ;
        System.out.println("options: "+options.size());

        options.get(index-1).click();

        for (int i=0; i<options.size(); i++ ) {
            String value = options.get(i).getAttribute("value");
            if (options.get(i).isSelected()) {
                System.out.println("Selected RadioButton: " + value);
            } else {
                System.out.println("NOT Selected RadioButton: " + value);
            }
        }
        }
        @Test
        public void testTwo () throws InterruptedException {
        WebElement input = driver.findElement(By.id("autocomplete"));
        String placeholder = input.getAttribute("placeholder");
        System.out.println(placeholder);
        input.sendKeys("El Sal");

        WebElement country = driver.findElement(By.className("ui-menu-item-wrapper"));
        country.click();
        String nameCountry = input.getAttribute("value");
        System.out.println(nameCountry);
        }

        @Test
        public void testThree () throws InterruptedException {
         Select select = new Select(driver.findElement(By.id("dropdown-class-example")));
         select.selectByValue("option2");
         System.out.println(select.getAllSelectedOptions().get(0).getAttribute("value"));
         WebElement otherOption = select.getOptions().get(3);
         String option3 = otherOption.getAttribute("value");
         System.out.println(option3);
         otherOption.click();

    }
    @Test
        public void testFour() throws InterruptedException {
        WebElement header = driver.findElement(By.xpath("/html/body/header"));
        List<WebElement> botons = header.findElements(By.className("btn-primary"));
        System.out.println("botons: "+botons.size());


        for(int i=0; i<botons.size(); i++){

            botons.get(i).click();
            String url = driver.getCurrentUrl();
            System.out.println(url);

            if(url.equals(baseUrl)){
                System.out.println("Same URL - Btn: " + botons.get(i).getAttribute("textContent"));
            }else{
                driver.navigate().back();
                header = driver.findElement(By.xpath("/html/body/header"));
                botons = header.findElements(By.className("btn-primary"));
                System.out.println("Different URL - Btn: " + botons.get(i).getAttribute("textContent"));
            }
        }

        Thread.sleep(3000);
    }

    @Test
    public void testFive() throws InterruptedException {
        WebElement header = driver.findElement(By.xpath("/html/body/header"));
        List<WebElement> botons = header.findElements(By.className("btn-primary"));
        System.out.println("botons: "+botons.size());
        int totalTabs= 0;

        for(int i=0; i<botons.size(); i++){
            botons.get(i).click();
            String url = driver.getCurrentUrl();
            System.out.println(url);

            if(!url.equals(baseUrl)){
                driver.navigate().back();
                while(totalTabs<8){
                    ((JavascriptExecutor)driver).executeScript("window.open()");
                    totalTabs++;

                    ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
                    driver.switchTo().window(tabs.get(totalTabs));
                    driver.get(url);
                    Thread.sleep(1000);
                    driver.switchTo().window(tabs.get(0));

                }
                System.out.println("Tabs: " + driver.getWindowHandles().size());
                break;
            }
        }

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }


}
