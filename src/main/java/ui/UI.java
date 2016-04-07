package ui;

import enums.Operations;
import objects.Fraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField numerator = new JTextField();
    private JTextField denominator = new JTextField();
    private JTextField prevNumerator = new JTextField();
    private JTextField prevDenominator = new JTextField();

    private boolean isNumerator = true;

    private Color defaultColor;

    private Operations currentOperation = null;
    private Fraction firstFraction = new Fraction();

    public UI() {
        List<JButton> numberButtons = new ArrayList<JButton>();
        List<JButton> operationsButtons = new ArrayList<JButton>();
        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(new UseOperation());
        JButton cleanButton = new JButton("C");
        cleanButton.addActionListener(new CleanFields());

        for (int i = 0; i < 10; i++) {
            JButton tmp = new JButton(String.valueOf(i));
            tmp.addActionListener(new PutDigit());
            numberButtons.add(tmp);
        }

        for (Operations operation : Operations.values()) {
            JButton tmp = new JButton(operation.toString());
            tmp.addActionListener(new UseOperation());
            operationsButtons.add(tmp);
        }

        this.numerator.setEditable(false);
        this.defaultColor = this.numerator.getBackground();
        this.numerator.setBackground(Color.yellow);
        this.prevNumerator.setEditable(false);
        JPanel numeratorPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        numeratorPanel.add(this.prevNumerator);
        numeratorPanel.add(this.numerator);

        this.denominator.setEditable(false);
        this.prevDenominator.setEditable(false);
        JPanel denominatorPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        denominatorPanel.add(this.prevDenominator);
        denominatorPanel.add(this.denominator);

        this.numerator.addMouseListener(new SwitchRadio());
        this.denominator.addMouseListener(new SwitchRadio());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 1));
        fieldsPanel.add(numeratorPanel);
        fieldsPanel.add(denominatorPanel);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(fieldsPanel, BorderLayout.CENTER);
        displayPanel.setBorder(BorderFactory.createTitledBorder("numerator/denominator"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 4, 2, 2));
        int numberBtnsCount = 9;
        for (int k = 0; k < 3; k++) {
            for (int i = 2; i >= 0; i--)
                buttonsPanel.add(numberButtons.get(numberBtnsCount - (k * 3 + i)));
            buttonsPanel.add(operationsButtons.get(k));
        }
        buttonsPanel.add(numberButtons.get(0));

        buttonsPanel.add(equalsButton);
        buttonsPanel.add(cleanButton);
        buttonsPanel.add(operationsButtons.get(operationsButtons.size() - 1)); // :

        this.setLayout(new BorderLayout());
        this.add(displayPanel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.CENTER);

        this.cleanFields();
    }

    private class SwitchRadio implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            isNumerator = e.getSource().equals(numerator);
            if (isNumerator) {
                numerator.setBackground(Color.yellow);
                denominator.setBackground(defaultColor);
            } else {
                numerator.setBackground(defaultColor);
                denominator.setBackground(Color.yellow);
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    private class PutDigit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isNumerator)
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
            currentOperation = null;
            firstFraction = new Fraction();
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

            if (operations != null) {
                if (currentOperation == null) {
                    currentOperation = operations;
                    firstFraction = rightOperand;
                } else {
                    firstFraction = Fraction.doOperation(firstFraction, currentOperation, rightOperand);
                    currentOperation = operations;
                }
                cleanFields();
                displayLeft(firstFraction);
            } else {
                if (currentOperation != null) {

                    firstFraction = Fraction.doOperation(firstFraction, currentOperation, rightOperand);
                    cleanFields();
                    displayRight(firstFraction);
                    currentOperation = null;
                    firstFraction = new Fraction();
                }
            }
        }
    }


}
