package org.todoapp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MainOperations extends BaseClass{

    private record ReminderData(String name, String iconName) {
    }
    private record AppointmentData(String name, String appointment) {

    }

    @Test(priority = 0)
    public void createListOfReminders(){
        var reminders = List.of(
                new ReminderData("Test1", "heart"),
                new ReminderData("Test2", "calendar"),
                new ReminderData("Test3", "graduationcap")
        );

        for (ReminderData reminder : reminders) {
            createReminder(reminder);
        }
    }

    private void createReminder(ReminderData reminder) {
        mainPage.clickAddButton();
        mainPage.enterName(reminder.name());
        mainPage.selectIcon(reminder.iconName());
        mainPage.clickRemindersButton();
    }

    @Test(priority = 1)
    public void createListOfAppointment() {
        var appointments = List.of(
                new AppointmentData("Test1", "work"),
                new AppointmentData("Test2", "home"),
                new AppointmentData("Test3", "school")
        );
        for (AppointmentData appointment : appointments) {
            createAppointment(appointment);
        }
    }

    private void createAppointment(AppointmentData appointmentData) {
        mainPage.clickCell(appointmentData.name());
        mainPage.createNewAppointment(appointmentData.appointment());
        assert mainPage.getAppointmentCellCreated().equals(appointmentData.appointment());
        mainPage.clickRemindersButton();
    }

    @Test(priority = 2)
    public void editReminder(){
        mainPage.updateCell("Test2");
        assert mainPage.getAppointmentCellCreated().equals("this cell was updated");
        mainPage.clickRemindersButton();
    }

    @Test(priority = 3)
    public void deleteReminder(){
        mainPage.swipeLeftAndDelete("Test2");
        Assert.assertFalse(mainPage.findCell("Test2"));
    }

}
