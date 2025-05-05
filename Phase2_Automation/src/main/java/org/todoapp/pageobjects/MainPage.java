package org.todoapp.pageobjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class MainPage {

    IOSDriver driver;

    public MainPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @iOSXCUITFindBy(accessibility = "Add Section")
    private WebElement addButton;
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Name\"`]")
    private WebElement nameTextField;
    @iOSXCUITFindBy(className = "XCUIElementTypeSegmentedControl")
    private WebElement iconsSegmentedControl;
    @iOSXCUITFindBy(accessibility = "Reminders")
    private WebElement remindersButton;
    @iOSXCUITFindBy(className = "XCUIElementTypeCell")
    private WebElement reminderCell;
    private static final String cellElement = "**/XCUIElementTypeButton[`name == \"%s, 0\"`]";
    private static final String cellWithAppointment = "**/XCUIElementTypeButton[`name == \"%s, 1\"`]";
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`name == \"New Reminder\"`]")
    private WebElement newReminderButton;
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCollectionView/XCUIElementTypeCell[1]")
    private WebElement firstCellReminder;
    @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    private WebElement appointmentTextField;
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther")
    private WebElement appointmentCellCreated;




    public void clickAddButton() {
        addButton.click();
    }

    public void enterName(String name) {
        nameTextField.sendKeys(name + "\n");
    }

    public void selectIcon(String iconName) {
        WebElement icon = driver.findElement(AppiumBy.iOSClassChain(
                String.format("**/XCUIElementTypeButton[`name == \"%s\"`]", iconName)));
        icon.click();
    }

    public void clickRemindersButton() {
        remindersButton.click();
    }

    public void clickCell(String cellName) {
        WebElement cell = driver.findElement(AppiumBy.iOSClassChain(String.format(cellElement, cellName)));
        cell.click();
    }

    public boolean findCell(String cellName) {
        try {
            WebElement cell = driver.findElement(
                    AppiumBy.iOSClassChain(String.format(cellWithAppointment, cellName))
            );
            return cell.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void createNewAppointment(String appointment){
        newReminderButton.click();
        firstCellReminder.click();
        appointmentTextField.sendKeys(appointment);
    }

    public void updateCell(String cellName) {
        WebElement cell = driver.findElement(AppiumBy.iOSClassChain(String.format(cellWithAppointment, cellName)));
        cell.click();
        appointmentTextField.click();
        appointmentTextField.clear();
        appointmentTextField.sendKeys("this cell was updated" + "\n");
    }

    public String getAppointmentCellCreated() {
        return appointmentTextField.getText();
    }

    public void swipeLeftAndDelete(String cellName) {
        WebElement cell = driver.findElement(AppiumBy.iOSClassChain(String.format(cellWithAppointment, cellName)));
        driver.executeScript("mobile: swipe", Map.of(
            "element", cell,
            "direction", "left"
        ));
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Delete']")).click();
    }
}
