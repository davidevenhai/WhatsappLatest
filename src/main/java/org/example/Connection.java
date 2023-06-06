package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Connection {
    private ChromeDriver chromeDriver;
    private String phoneNumber;
    private String statusMessage;


    public Connection() {
        this.statusMessage = Constants.EMPTY_STRING;
        this.phoneNumber = Constants.EMPTY_STRING;
    }

    public void openChrome() {
        System.setProperty("webdriver.openqa.driver", "C:\\Users\\עומר\\Downloads\\chromedriver_win32");
        this.chromeDriver = new ChromeDriver();
        this.chromeDriver.get("https://web.whatsapp.com/");
        this.chromeDriver.manage().window().maximize();
        WebElement searchBox;
        while (true) {
            try {
                searchBox = this.chromeDriver.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div/div[2]/div/div[1]/p"));
                if (searchBox != null) {
                    break;
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void openConversation(String phoneNumber, String textMessage) {
        if(phoneNumber.length() == 10){
            phoneNumber = updatePhone(phoneNumber);
        }
        if (!this.phoneNumber.equals(phoneNumber)) {
            this.chromeDriver.get("https://web.whatsapp.com/send/?phone=" + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
        WebElement temp = null;
        while (temp == null) {
            try {
                temp = chromeDriver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p"));
                temp.sendKeys(textMessage);
                temp.sendKeys(Keys.ENTER);
            } catch (Exception ignored) {
            }
        }

        new Thread(() -> {
            deliveredMessage(chromeDriver);
        }).start();

    }

    public String getLast(ChromeDriver chromeDriver) {
        String lastMessage = null;
        List<WebElement> list;
        List<WebElement> divElements;
        try {
            divElements = chromeDriver.findElements(By.cssSelector("div[role='row']"));
            WebElement lastDivElement = divElements.get(divElements.size() - Constants.END_OF_ARR);
            list = lastDivElement.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
            lastMessage = list.get(list.size() - Constants.END_OF_ARR).getAccessibleName();
        } catch (Exception e) {
            return lastMessage;
        }
        return lastMessage;
    }

    public void deliveredMessage(ChromeDriver chromeDriver) {
        boolean read = false;
        boolean sent = false;
        while (!read) {
            try {
                String lastMessage = getLast(chromeDriver);
                if (lastMessage == null) {
                    this.statusMessage = "נשלחה";
                } else {
                    if ((lastMessage.contains("נמסרה") ||(lastMessage.contains("Delivered"))) && !sent) {
                        this.statusMessage = "נמסרה";
                        sent = true;
                    } else if (lastMessage.contains("נקראה") || lastMessage.contains("Read")) {
                        this.statusMessage = "נקראה";
                        read = true;
                    }
                }

            } catch (Exception ignored) {
            }
        }
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }
    public String updatePhone(String str){
        String temp = Constants.AREA_CODE + str.substring(2);
        return temp;
    }


}

