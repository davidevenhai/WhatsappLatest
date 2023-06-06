package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Program extends JPanel {
    private BufferedImage background;
    private JButton loginButton;
    private Connection connection;
    private ChatClass chatClass;
    private JLabel success;

    public Program(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        addByLine();
        addBackgroundPicture();
        loginProcess();

    }

    private void addBackgroundPicture() {
        try {
            this.background = ImageIO.read(new File("src/main/resources/background.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loginProcess() {
        this.loginButton = new JButton("Log on WhatApp");
        this.loginButton.setBounds(545, 80, 175, 50);
        this.loginButton.setVisible(true);
        this.add(loginButton);
        this.loginButton.addActionListener(e -> {
            this.connection = new Connection();
            this.connection.openChrome();
            addSuccessLogin();
            this.loginButton.setVisible(false);
            this.chatClass = new ChatClass(535,60,200,400,this.connection);
            this.add(this.chatClass);
            this.chatClass.setVisible(true);

        });
    }
    public void addSuccessLogin() {
        success = new JLabel();
        success.setForeground(Color.GREEN);
        success.setBounds(575, 460, Constants.WIDTH, 40);
        success.setFont(new Font("Arial", Font.BOLD, 14));
        success.setVisible(true);
        this.add(success);
        this.success.setText("Login Succeed!");

    }
    public void addByLine() {
        JLabel by = new JLabel("@By Avihay Navon, David Even-Haim, Omer Hayoon, Avihay Ben-Ami,Idan Zakheym AAC-CS 2023");
        by.setBounds(3, 530, Constants.WIDTH, 40);
        by.setFont(new Font("Arial", Font.BOLD, 14));
        by.setVisible(true);
        by.setForeground(Color.WHITE);
        this.add(by);
    }



    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(this.background, Constants.ZERO, Constants.ZERO, Constants.WIDTH, Constants.HEIGHT, null);

    }
}
