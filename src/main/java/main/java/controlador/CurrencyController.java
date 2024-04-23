package main.java.controlador;


import main.java.modelo.ExchangeRateService;
import main.java.modelo.exceptions.InvalidCurrencyExcpetion;
import main.java.vista.ViewConversor;

public class CurrencyController {
    private final ExchangeRateService exchangeRateService;
    private final ViewConversor viewConversor;
    private Thread taskThread;

    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
        this.viewConversor = new ViewConversor(this, exchangeRateService);
    }

    public void convertCurrency(String source, String target, int amount) {
        taskThread = new Thread(() -> {
            try {
                viewConversor.stopWaitingStatus();
                exchangeRateService.convertCurrency(source, target, amount);
                viewConversor.startWaitingStatus();
            } catch (InvalidCurrencyExcpetion e) {
                viewConversor.showError(e.getMessage());
                viewConversor.startWaitingStatus();
            }
        });
        taskThread.start();
    }
}
