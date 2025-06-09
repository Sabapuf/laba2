import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class DemoQATest {
    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/FORLABS/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:/FORLABS/chrome-win64/chrome.exe");


        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://demoqa.com");
    }

    @Test
    @DisplayName("1. Проверка заголовка страницы")
    public void testPageTitle() {
        String expectedTitle = "DEMOQA";
        String actualTitle = driver.getTitle();
        assertTrue(actualTitle.contains(expectedTitle),
                "Заголовок страницы не соответствует ожидаемому");
    }


    @Test
    @DisplayName("2. Тестирование текстовых полей формы")
    public void testTextFields() {
        driver.get("https://demoqa.com/text-box");

        WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("userName")));
        fullNameField.sendKeys("Иван Иванов");

        WebElement emailField = driver.findElement(By.id("userEmail"));
        emailField.sendKeys("test@example.com");

        assertEquals("Иван Иванов", fullNameField.getAttribute("value"),
                "Текст в поле не соответствует введенному");
        assertEquals("test@example.com", emailField.getAttribute("value"),
                "Email в поле не соответствует введенному");
    }

    @Test
    @DisplayName("3. Тестирование кнопок")
    public void testButtons() {
        driver.get("https://demoqa.com/buttons");

        WebElement clickMeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Click Me']")));
        clickMeButton.click();

        WebElement dynamicClickMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("dynamicClickMessage")));
        assertTrue(dynamicClickMessage.isDisplayed(),
                "Сообщение после клика не отображается");
    }

    @Test
    @DisplayName("4. Тестирование чекбоксов")
    public void testCheckboxes() {
        driver.get("https://demoqa.com/checkbox");

        WebElement expandAllButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[title='Expand all']")));
        expandAllButton.click();

        WebElement desktopCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='tree-node-desktop']")));
        desktopCheckbox.click();

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("result")));
        assertTrue(result.getText().contains("desktop"),
                "Чекбокс не был выбран или результат не отображается");
    }

    @Test
    @DisplayName("5. Тестирование радиокнопок")
    public void testRadioButtons() {
        driver.get("https://demoqa.com/radio-button");

        WebElement impressiveRadio = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='Impressive']")));
        impressiveRadio.click();

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".text-success")));
        assertEquals("Impressive", result.getText(),
                "Выбранная радиокнопка не соответствует ожидаемой");
    }

    @Test
    @DisplayName("6. Тестирование выпадающих списков")
    public void testDropdown() {
        driver.get("https://demoqa.com/select-menu");

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("oldSelectMenu")));
        dropdown.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//option[text()='Purple']")));
        option.click();

        assertEquals("4", dropdown.getAttribute("value"),
                "Выбранное значение в dropdown не соответствует ожидаемому");
    }

    @Test
    @DisplayName("7. Тестирование загрузки файлов")
    public void testFileUpload() {
        driver.get("https://demoqa.com/upload-download");

        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("uploadFile")));
        uploadInput.sendKeys(System.getProperty("user.dir") + "/pom.xml");

        WebElement uploadResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("uploadedFilePath")));
        assertTrue(uploadResult.getText().contains("pom.xml"),
                "Файл не был успешно загружен");
    }


    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}