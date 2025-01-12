package controller;

import model.Produto;
import utilities.ScrapingUtil;
import java.util.List;

public class WebScraping {
    public static void executar() {
        List<Produto> produtos = ScrapingUtil.rasparDados();
    }
}
