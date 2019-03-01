package ru.ttv.receiptprintingGUI.gui;

import ru.atol.drivers10.fptr.IFptr;
import ru.ttv.receiptprintingGUI.api.SettingService;
import ru.ttv.receiptprintingGUI.service.SettingServiceImpl;
import ru.ttv.receiptprintingGUI.service.ReceiptServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * @author Timofey Teplykh
 */
public class AppForm extends Component {
    private JLabel shiftStateLabel;
    private JLabel operatorLabel;
    private JTextField textFieldOperator;
    private JButton openShiftButton;
    private JButton closeShiftButton;
    private JList fileList;
    private JButton printReceipt;
    private JTextPane errorMessageTextPane;
    private JPanel panel1;
    private JButton checkConnectButton;
    private JComboBox commodityNameComboBox;
    private JTextField quantityTextField;
    private JTextField priceTextField;
    private JLabel commodityNameLabel;
    private JLabel quantityLabel;
    private JLabel priceLabel;
    private JComboBox enterpriseComboBox;
    private static final String BAGYNBAEV = "ИП Багынбаев";
    private static final String SAPAEVA = "ИП Сапаева";
    private static final String VERSION = "1.1.1";
    private static final String PROPERTIES_FILE = "properties.conf";
    private static final String SHIFT_OPEN_STR = "Смена открыта";
    private static final String SHIFT_24HOURS_UP_STR = "Смена 24 часа истекли";
    private static final String SHIFT_CLOSED_STR = "Смена закрыта";
    private ReceiptServiceImpl receiptService;

    public void init(){
        SettingService settingService = new SettingServiceImpl();
        try {
            settingService.init(PROPERTIES_FILE);
        } catch (IOException e) {
            errorMessageTextPane.setText(e.getLocalizedMessage()+ "\n"+errorMessageTextPane.getText());
            return;
        }
        receiptService = new ReceiptServiceImpl();
        receiptService.init();
        fileList.setListData(receiptService.getFileList().toArray());
        textFieldOperator.setText(((SettingServiceImpl) settingService).getOperatorBagynbaev());
        updateLabelShiftState();
        String[] enterprises = {BAGYNBAEV, SAPAEVA};
        enterpriseComboBox.setModel(new DefaultComboBoxModel<String>(enterprises));
        String[] commodityItems = settingService.getCommodityList().split(",");//{"Автоаксессуары","Авточехлы","Автокомпрессоры","Домкраты"};
        commodityNameComboBox.setModel(new DefaultComboBoxModel<String>(commodityItems));
        quantityTextField.setText("1");
        priceTextField.setText("1.00");
        openShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiptService.openShift(errorMessageTextPane, textFieldOperator.getText());
                updateLabelShiftState();
            }
        });
        closeShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiptService.closeShift(errorMessageTextPane, textFieldOperator.getText());
                updateLabelShiftState();
            }
        });
        printReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final Object elem = fileList.getSelectedValue();
                String selectedFile = null;
                if(elem != null){
                    selectedFile = elem.toString();
                }
                String commodityName = (String) commodityNameComboBox.getSelectedItem();
                float quantity = Float.parseFloat(quantityTextField.getText());
                float price = Float.parseFloat(priceTextField.getText());

                receiptService.printReceipt(errorMessageTextPane, selectedFile, textFieldOperator.getText(), commodityName, quantity, price);
            }
        });
        checkConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptService.checkConnectionIsOpen(errorMessageTextPane);
            }
        });

        enterpriseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String enterprise = (String)cb.getSelectedItem();
                if(enterprise.equals(SAPAEVA)){
                    receiptService.setComPort(settingService.getComPortSapaeva());
                    textFieldOperator.setText(settingService.getOperatorSapaeva());
                }else if(enterprise.equals(BAGYNBAEV)){
                    receiptService.setComPort(settingService.getComPortBagynbaev());
                    textFieldOperator.setText(settingService.getOperatorBagynbaev());
                }
                updateLabelShiftState();
            }
        });
        JFrame frame = new JFrame("Печать чеков ТТВ ver."+VERSION);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                receiptService.closeConnection();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void updateLabelShiftState(){
        long shiftState = receiptService.getShiftState();
        if(shiftState == IFptr.LIBFPTR_SS_OPENED){
            shiftStateLabel.setText(SHIFT_OPEN_STR);
        }else if(shiftState == IFptr.LIBFPTR_SS_EXPIRED){
            shiftStateLabel.setText(SHIFT_24HOURS_UP_STR);
        }else if(shiftState == IFptr.LIBFPTR_SS_CLOSED){
            shiftStateLabel.setText(SHIFT_CLOSED_STR);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
