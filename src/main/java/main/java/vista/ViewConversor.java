package main.java.vista;

import main.java.controlador.ControladorDivisas;
import main.java.modelo.LoadModeloListener;
import main.java.modelo.ServicioTasaCambio;

import javax.swing.*;
import java.awt.*;

public class ViewConversor {

    private JFrame frame;
    private JPanel content;
    private JComboBox<String> in;
    private JComboBox<String> out;
    private JButton convertButton;
    private JTextField termToConvert;
    private JTextArea resultConvert;

    private ControladorDivisas controladorDivisas;
    private ServicioTasaCambio servicioTasaCambio;

    public ViewConversor(ControladorDivisas controladorDivisas, ServicioTasaCambio servicioTasaCambio) {
        this.controladorDivisas = controladorDivisas;
        this.servicioTasaCambio = servicioTasaCambio;
        initConfig();
        initListeners();
    }

    private void initConfig() {
        frame = new JFrame("Conversor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        termToConvert = new JTextField(10);
        topPanel.add(termToConvert);

        convertButton = new JButton("Convertir");
        topPanel.add(convertButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        in = new JComboBox<>(servicioTasaCambio.getTasas().toArray(new String[0]));
        out = new JComboBox<>(servicioTasaCambio.getTasas().toArray(new String[0]));

        middlePanel.add(in);
        middlePanel.add(out);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        resultConvert = new JTextArea(5, 20);
        resultConvert.setEditable(false);
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
    }

    public void stopWaitingStatus() {
        convertButton.setEnabled(false);
    }

    public void initListeners() {
        convertButton.addActionListener(e -> {
            String origen = (String) in.getSelectedItem();
            String destino = (String) out.getSelectedItem();
            int cantidad = Integer.parseInt(termToConvert.getText());
            controladorDivisas.convertirDivisa(origen, destino, cantidad);
        });

        servicioTasaCambio.addListener( new LoadModeloListener() {
            @Override
            public void loadModeloFinished() {
                showConvertResult();
            }
        });
    }

    public void showConvertResult() {
        resultConvert.setText(String.valueOf(servicioTasaCambio.getLastResult()));
    }
}
