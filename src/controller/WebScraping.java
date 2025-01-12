package controller;

import model.Produto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class WebScraping {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        rasparDados();
    }

    public static void rasparDados() {
        Locale.setDefault(Locale.US);

        // definir o caminho do driver
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();

        // previne possiveis erros na execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // escapar detecção de bots do site
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);

        // para ajudar a não ser identificado como bot
        options.addArguments("Window-size=1600,800");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        WebDriver driver = new EdgeDriver(options = options);

        driver.get("https://www.tesourodireto.com.br/titulos/precos-e-taxas.htm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // coletando os dados da pagina e armazenando
        WebElement webElementNomesTitulos = driver.findElement(By.className("td-mercado-titulos__content"));
        String tabela = webElementNomesTitulos.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> nomeDoTitulo = new ArrayList<>();
        List<String> rentabilidadeAnual = new ArrayList<>();
        List<Double> investimentoMinimo = new ArrayList<>();
        List<Double> precoUnitario = new ArrayList<>();
        List<Date> vencimento = new ArrayList<>();
        List<Produto> produtos = new ArrayList<>();

        String[] linhas = tabela.split("\n");
        for (int i = 0; i < linhas.length; i++) {

            // armazenando os nomes dos titulos
            if (linhas[i].startsWith("TESOURO")) {
                if (linhas[i + 1].startsWith("com") || linhas[i + 1].startsWith("aposentadoria")) {
                    nomeDoTitulo.add(linhas[i].concat(" " + linhas[i + 1]));
                } else {
                    nomeDoTitulo.add(linhas[i]);
                }
            }

            // armazenando as rentabilidades, investimento minimo, preco unitario e vencimento
            if (linhas[i].contains("%")) {
                try {

                    rentabilidadeAnual.add(linhas[i].substring(0, linhas[i].indexOf('%') + 1));

                    int tamanhoStrRentabilidadeAnual = linhas[i].substring(0, linhas[i].indexOf('%') + 1).length() + 1;

                    String valoresStrInvestimentoMinimoAtePrecoUnitario = (linhas[i].substring(tamanhoStrRentabilidadeAnual, linhas[i].lastIndexOf(",") + 3));
                    String[] valoresStrSeparados = valoresStrInvestimentoMinimoAtePrecoUnitario.split(" ");

                    String strInvestimentoMinimo = valoresStrSeparados[1].replace(".", "").replace(",", ".");
                    investimentoMinimo.add(Double.parseDouble(strInvestimentoMinimo));

                    String strPrecoUnitario = valoresStrSeparados[3].replace(".", "").replace(",", ".");
                    precoUnitario.add(Double.parseDouble(strPrecoUnitario));

                    int tamanhoStrAteOVencimento = tamanhoStrRentabilidadeAnual + valoresStrInvestimentoMinimoAtePrecoUnitario.length() + 1;
                    String strVencimento = linhas[i].substring(tamanhoStrAteOVencimento, linhas[i].indexOf(" Simule"));
                    vencimento.add(sdf.parse(strVencimento));
                } catch (ParseException parse) {
                    System.out.println(parse.getMessage());
                }
            }

        }

        int tamanho = nomeDoTitulo.size();
        for (int i = 0; i < tamanho; i++) {
            produtos.add(new Produto(nomeDoTitulo.get(i), rentabilidadeAnual.get(i), investimentoMinimo.get(i), precoUnitario.get(i), vencimento.get(i)));
        }

        for (Produto produto : produtos) {
            System.out.println(produto.toString());
            System.out.println();
        }

        driver.quit();
    }

    private static void waitForIt(long tempo) {
        try {
            new Thread().sleep(tempo);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
