package main;

import javax.swing.*;

import ui.UI;

public class Main {

	public static void main(String[] args) {
		UI ui = new UI();
		ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ui.setVisible(true);
		ui.setBounds(0, 0, 200, 300);
		ui.setResizable(false);
	}

}
