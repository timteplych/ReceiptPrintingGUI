package ru.ttv.receiptprintingGUI;

import ru.ttv.receiptprintingGUI.gui.AppForm;


/**
 * @author Timofey Teplykh
 */
public class App {
    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        AppForm appForm = new AppForm();
        appForm.init();
    }
}
