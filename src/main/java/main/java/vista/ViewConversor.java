package main.java.vista;

import main.java.controlador.CurrencyController;
import main.java.modelo.LoadModelListener;
import main.java.modelo.ExchangeRateService;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewConversor {

    private JFrame frame;
    private JPanel content;
    private JComboBox<String> inRates;
    private JComboBox<String> outRates;
    private JButton convertButton;
    private JFormattedTextField amountToConvert;
    private JTextArea resultConvert;

    private CurrencyController currencyController;
    private ExchangeRateService exchangeRateService;

    public ViewConversor(CurrencyController currencyController, ExchangeRateService exchangeRateService) {
        this.currencyController = currencyController;
        this.exchangeRateService = exchangeRateService;
        initConfig();
        initListeners();
    }

    private void initConfig() {
        frame = new JFrame("Conversor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);

        amountToConvert = new JFormattedTextField(formatter);
        amountToConvert.setColumns(10);
        topPanel.add(amountToConvert);

        convertButton = new JButton("Convertir");
        topPanel.add(convertButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        inRates = new JComboBox<>(exchangeRateService.getRates().toArray(new String[0]));
        List<String> sourceRates = new ArrayList<>(exchangeRateService.getRates().stream().toList());
        Collections.shuffle(sourceRates);
        outRates = new JComboBox<>(sourceRates.toArray(new String[0]));

        middlePanel.add(inRates);
        middlePanel.add(outRates);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        resultConvert = new JTextArea(1, 20);
        resultConvert.setEditable(false);
        resultConvert.setOpaque(true);
        bottomPanel.add(resultConvert);

        content.add(topPanel, BorderLayout.NORTH);
        content.add(middlePanel, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(content);
        frame.pack();
        frame.setVisible(true);
    }

    public void startWaitingStatus() {
        convertButton.setEnabled(true);
        inRates.setEnabled(true);
        outRates.setEnabled(true);
    }

    public void stopWaitingStatus() {
        convertButton.setEnabled(false);
        inRates.setEnabled(false);
        outRates.setEnabled(false);
    }

    public void initListeners() {
        convertButton.addActionListener(e -> {
            String source = (String) inRates.getSelectedItem();
            String target = (String) outRates.getSelectedItem();
            int amount = (int) amountToConvert.getValue();
            currencyController.convertCurrency(source, target, amount);
        });

        exchangeRateService.addListener(new LoadModelListener() {
            @Override
            public void loadModelFinished() {
                showConvertResult();
            }
        });
    }

    public void showConvertResult() {
        double conversion = Math.round(exchangeRateService.getLastResult() * 100.0) / 100.0;
        resultConvert.setText(String.valueOf(conversion));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
