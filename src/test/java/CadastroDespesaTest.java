import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

    public class CadastroDespesaTest {

        private WebDriver driver;
        private WebDriverWait wait;

        @BeforeEach
        public void setUp() {
            // Criando opções para forçar o Chrome a aceitar a conexão do robô
            org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            // 1. Abrir o navegador com essas permissões
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Aumentei a espera para 15 segundos
            driver.manage().window().maximize();
        }

        @Test
        public void testCadastrarDespesa() {
            // Navegar até a aplicação alvo
            driver.get("https://dev-finance.netlify.app");

            // 2. Navegar até a página de cadastro
            WebElement botaoNovaTransacao = driver.findElement(By.cssSelector("a.button.new"));
            botaoNovaTransacao.click();

            // 3. Preencher os campos
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));

            WebElement inputDescricao = driver.findElement(By.id("description"));
            inputDescricao.sendKeys("Conta de Luz");

            WebElement inputValor = driver.findElement(By.id("amount"));
            inputValor.sendKeys("-150.00");

            WebElement inputData = driver.findElement(By.id("date"));
            inputData.sendKeys("2024-05-15");

            // 4. Clicar no botão "Salvar"
            WebElement botaoSalvar = driver.findElement(By.xpath("//button[text()='Salvar']"));
            botaoSalvar.click();

            // 5. Validar (usando asserts) se o cadastro foi bem-sucedido
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

            boolean transacaoCadastrada = driver.getPageSource().contains("Conta de Luz");

            assertTrue(transacaoCadastrada, "A transação não foi encontrada na tela após o cadastro!");
        }

        @AfterEach
        public void tearDown() {
            // Fechar o navegador após o teste
            if (driver != null) {
                driver.quit();
            }
        }
    }


