package org.example;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("WhatsappBOT");
        this.add(new Program(Constants.ZERO,Constants.ZERO,Constants.WIDTH, Constants.HEIGHT));
        this.setVisible(true);

    }
}
