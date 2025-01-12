package utilities;

import model.Produto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScrapingUtil {
    public static List<Produto> rasparDados() {
        WebDriver driver = util.DriverUtil.initializeDriver();
        driver.get("https://www.tesourodireto.com.br/titulos/precos-e-taxas.htm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

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
        
        driver.quit();
        return produtos;
    }
}
