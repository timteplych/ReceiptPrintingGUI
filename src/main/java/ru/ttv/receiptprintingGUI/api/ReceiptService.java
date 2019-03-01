package ru.ttv.receiptprintingGUI.api;

import javax.swing.*;
import java.util.List;

/**
 * @author Timofey Teplykh
 */
public interface ReceiptService {
    void openShift(JTextPane errorMessagePane, String operator);

    void closeShift(JTextPane errorMessagePane, String operator);

    void printReceipt(JTextPane errorMessagePane, String filePath, String operator, String commodityName, float quantity, float price);

    void init();

    long getShiftState();

    void checkConnectionIsOpen(JTextPane errorMessagePane);

    List<String> getFileList();

    void closeConnection();
}
