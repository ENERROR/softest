
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.javascript.host.file.File;

public class Sele {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String fileName;
    private static Map<String,String> map;
    
    public static void read() {
        try {
            
            InputStream inputStream = new FileInputStream("读取的文件");
            Workbook workbook = null;
            
            workbook = new XSSFWorkbook(inputStream);
            
            Sheet sheet = workbook.getSheetAt(0);
            
            map = new HashMap<String,String>();
            
            for(Row r : sheet) {
                r.getCell(0).setCellType(1);
                r.getCell(1).setCellType(1);
                String key = r.getCell(0).getStringCellValue();
                String value = r.getCell(1).getStringCellValue();
                value = value.trim();
                map.put(key, value);
            }
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Before
    public void setUp() throws Exception {
        //    driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/下载的chromedriver的位置/chromedriver");
        driver = new ChromeDriver();
        
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @Test
    public void testSele() throws Exception {
        
        Sele.read();
        
        
        for(Map.Entry<String,String> e : map.entrySet()) {
            driver.get("https://测试网站的网址");
            
            driver.findElement(By.id("username")).click();
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys(e.getKey());
            driver.findElement(By.id("password")).click();
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys(e.getKey().substring(4));
            driver.findElement(By.id("submitButton")).click();
            String a = driver.findElement(By.xpath("//p")).getText();
            Thread.sleep(3000);
            
            //测试
            assertEquals(e.getValue(),a);
            
            if(!e.getValue().equals(a)) {
                System.out.println("ERROR with "+e.getKey());
            }else {
                System.out.println(e.getKey()+" OK");
            }
            
        }
    }
    
    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
    
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

