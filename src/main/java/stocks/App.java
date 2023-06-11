package stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import stocks.service.PrintService;

@EnableWebFlux
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private PrintService printService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        printService.printSymbols();
        printService.printStockQuotes();
    }
}
