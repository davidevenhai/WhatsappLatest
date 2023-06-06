package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatClass extends JPanel {
    private JTextArea messageArea;
    private JTextField phoneNumberTF;
    private String phoneNumber;
    private String textMessage;
    private Connection connection;
    private JLabel status;

    public ChatClass(int x, int y, int width, int height, Connection connection) {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setLayout(null);
        this.connection = connection;
        createStatus();
        createBox();
        createSendMessage();

        this.setVisible(false);
    }

    private void createBox() {
        this.setLayout(null);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Phone number and message"),
                BorderFactory.createEmptyBorder(10, 5, 2, 5)));
        JLabel message = new JLabel("Message: ");
        message.setBounds(20, 20, 120, 15);
        message.setFont(new Font("Enter Phone Number", Font.BOLD, 12));
        this.add(message);

        this.messageArea = new JTextArea();
        this.messageArea.setBounds(20, 50, 150, 100);
        this.add(this.messageArea);
        this.messageArea.setLineWrap(true);
        this.messageArea.setWrapStyleWord(true);

        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        phoneNumberLabel.setBounds(20, 165, 120, 15);
        this.add(phoneNumberLabel);

        this.phoneNumberTF = new JTextField();
        this.phoneNumberTF.setBounds(20, 190, 150, 20);
        this.add(this.phoneNumberTF);

        phoneNumberTF.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    String text = phoneNumberTF.getText();
                    long value = Long.parseLong(phoneNumberTF.getText()); //It's grey but we are using it to in order to get exception!
                }catch (NumberFormatException exception) {
                    phoneNumberTF.setText("");
                }

            }

        });


    }

    private void createSendMessage() {
        JButton sendMessage = new JButton("Send Message");
        sendMessage.setBounds(40, 240, 120, 30);
        this.add(sendMessage);
        sendMessage.addActionListener(e -> {

            if (validPhone(phoneNumberTF.getText())) {
                if (!this.messageArea.getText().isEmpty()) {
                    System.out.println("phoneNumber : " + phoneNumberTF.getText());
                    System.out.println("message : " + this.messageArea.getText());
                    this.phoneNumber = phoneNumberTF.getText();
                    this.textMessage = this.messageArea.getText();
                    this.connection.openConversation(this.phoneNumber, this.textMessage);
                    updateStatus();

                } else {
                    popMessage("You need to enter text ");
                }
            } else {
                if(this.messageArea.getText().isEmpty()){
                    popMessage("The number you entered is invalid, Also please enter a text");
                }else{
                    popMessage("The number you entered is invalid ");

                }
                phoneNumberTF.setText("");
            }
        });
    }

    public boolean validPhone(String phoneNumber) {
        boolean isValid = false;
        long length = phoneNumber.length();
        if (length == Constants.AREA_CODE_LENGTH && phoneNumber.startsWith(Constants.AREA_CODE)) {
            isValid = true;
        }
        else if (length == Constants.REG_PHONE_LENGTH && phoneNumber.startsWith(Constants.ISRAELI_PHONE)) {
            isValid =  true;
        }
        return isValid;
    }

    private void createStatus() {
        this.status = new JLabel(Constants.MESSAGE_STATUS);
        this.status.setBounds(40, 220, Constants.WIDTH/4, Constants.WIDTH/4);
        this.status.setFont(new Font("", Font.BOLD, 12));
        this.status.setForeground(Color.ORANGE);
        this.add(this.status);
    }

    private void updateStatus() {
        new Thread(() -> {
            while (true) {
                this.status.setText(Constants.MESSAGE_STATUS+
                        this.connection.getStatusMessage());
            }
        }).start();

    }
    private void popMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }


}
