package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.Operations;
import objects.Fraction;

public class UI extends JFrame {

    private static final long serialVersionUID = 1L;
    private List<JButton> numberButtons = new ArrayList<JButton>();
    private List<JButton> operationsButtons = new ArrayList<JButton>();
    private JButton equalsButton = new JButton("=");
    private JButton cleanButton = new JButton("C");

    private JTextField numerator = new JTextField();
    private JTextField denominator = new JTextField();
    private JTextField prevNumerator = new JTextField();
    private JTextField prevDenominator = new JTextField();

    private JCheckBox isNumerator = new JCheckBox();
    private JCheckBox isDenominator = new JCheckBox();

    private Operations currentOperation = null;
    private Fraction resultFraction = new Fraction();

    public UI() {
        this.cleanButton.addActionListener(new CleanFields());

        for (int i = 0; i < 10; i++) {
            JButton tmp = new JButton(String.valueOf(i));
            tmp.addActionListener(new PutDigit());
            this.numberButtons.add(tmp);
        }

        for (Operations operation : Operations.values()) {
            JButton tmp = new JButton(operation.toString());
            tmp.addActionListener(new UseOperation());
            this.operationsButtons.add(tmp);
        }


        this.numerator.setEditable(false);
        this.prevNumerator.setEditable(false);
        JPanel numeratorPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        numeratorPanel.add(this.prevNumerator);
        numeratorPanel.add(this.numerator);

        this.denominator.setEditable(false);
        this.prevDenominator.setEditable(false);
        JPanel denominatorPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        denominatorPanel.add(this.prevDenominator);
        denominatorPanel.add(this.denominator);

        this.isNumerator.addActionListener(new SwitchRadio());
        this.isDenominator.addActionListener(new SwitchRadio());
        this.isNumerator.setSelected(true);
        JPanel radioPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        radioPanel.add(this.isNumerator);
        radioPanel.add(this.isDenominator);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 1));
        fieldsPanel.add(numeratorPanel);
        fieldsPanel.add(denominatorPanel);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(fieldsPanel, BorderLayout.CENTER);
        displayPanel.add(radioPanel, BorderLayout.EAST);
        displayPanel.setBorder(BorderFactory.createTitledBorder("numerator/denominator"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 4, 2, 2));
        int numberBtnsCount = 9;
        for (int k = 0; k < 3; k++) {
            for (int i = 2; i >= 0; i--)
                buttonsPanel.add(this.numberButtons.get(numberBtnsCount - (k * 3 + i)));
            buttonsPanel.add(this.operationsButtons.get(k));
        }
        buttonsPanel.add(this.numberButtons.get(0));
        buttonsPanel.add(this.equalsButton);
        buttonsPanel.add(this.cleanButton);
        buttonsPanel.add(this.operationsButtons.get(operationsButtons.size() - 1)); // :

        this.setLayout(new BorderLayout());
        this.add(displayPanel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);

        displayLeft(new Fraction());
    }

    private class SwitchRadio implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(isNumerator))
                isDenominator.setSelected(!isNumerator.isSelected());
            else
                isNumerator.setSelected(!isDenominator.isSelected());
        }
    }

    private class PutDigit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isNumerator.isSelected())
                numerator.setText(numerator.getText() + ((JButton) e.getSource()).getText());
            else
                denominator.setText(denominator.getText() + ((JButton) e.getSource()).getText());
        }
    }

    private void cleanFields() {
        numerator.setText("");
        denominator.setText("");
        prevNumerator.setText("");
        prevDenominator.setText("");
    }

    private class CleanFields implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cleanFields();
        }
    }

    private Fraction getFraction() {
        try {
            long numerator = Long.valueOf(this.numerator.getText());
            long denominator = Long.valueOf(this.denominator.getText());
            return new Fraction(numerator, denominator);
        } catch (NumberFormatException e) {
            return new Fraction();
        }
    }

    private void displayLeft(Fraction f) {
        this.prevNumerator.setText(String.valueOf(f.getNumerator()));
        this.prevDenominator.setText(String.valueOf(f.getDenominator()));
    }

    private void displayRight(Fraction f) {
        this.numerator.setText(String.valueOf(f.getNumerator()));
        this.denominator.setText(String.valueOf(f.getDenominator()));
    }

    private class UseOperation implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Operations operations = Operations.toOperation(((JButton) e.getSource()).getText());
            Fraction rightOperand = getFraction();
            cleanFields();

            if (operations != null) {
                if (currentOperation == null)
                    currentOperation = operations;
                resultFraction.doOperation(currentOperation, rightOperand);
                displayLeft(resultFraction);
                return;
            }
            resultFraction.doOperation(currentOperation, getFraction());
            resultFraction = new Fraction();
            displayRight(resultFraction);

        }
    }


}
