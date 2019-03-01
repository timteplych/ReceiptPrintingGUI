package ru.ttv.receiptprintingGUI.service;

import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;
import ru.ttv.receiptprintingGUI.api.DataService;
import ru.ttv.receiptprintingGUI.api.ReceiptService;

import javax.swing.*;
import java.util.List;

/**
 * @author Timofey Teplykh
 */
public class ReceiptServiceImpl implements ReceiptService {
    private List<String> files;
    private DataService dataService;
    private IFptr fptr;
    private String comPort;

    public void openShift(JTextPane errorMessagePane, String operator) {
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, String.valueOf(IFptr.LIBFPTR_MODEL_ATOL_AUTO));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, String.valueOf(IFptr.LIBFPTR_PORT_COM));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, comPort);
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, String.valueOf(IFptr.LIBFPTR_PORT_BR_115200));
        fptr.applySingleSettings();
        //Установка соединения с ККТ
        fptr.open();
        //Проверка состояния логического соединения
        boolean isOpened = fptr.isOpened();
        //Открытие смены
        fptr.setParam(1021, operator);//"Кассир Иванов И."
        fptr.setParam(1203, "");//ИНН Кассира
        fptr.operatorLogin();
        fptr.openShift();
        String errorString = String.format("%d [%s]", fptr.errorCode(), fptr.errorDescription());
        errorMessagePane.setText(errorString + "\n"+errorMessagePane.getText());

        //Проверка состояния документа
        //ТТВ в теле цикла необходимо выводить запрос пользователю с подтверждением исправления проблемы и продолжения
        //печати
        /*while (fptr.checkDocumentClosed() < 0) {
            // Не удалось проверить состояние документа. Вывести пользователю текст ошибки, попросить устранить неполадку и повторить запрос
            errorMessagePane.setText(fptr.errorDescription() + "\n"+errorMessagePane.getText());
            //System.out.println(fptr.errorDescription());
            continue;
        }*/
    }

    public void closeShift(JTextPane errorMessagePane, String operator) {
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, String.valueOf(IFptr.LIBFPTR_MODEL_ATOL_AUTO));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, String.valueOf(IFptr.LIBFPTR_PORT_COM));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, comPort);
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, String.valueOf(IFptr.LIBFPTR_PORT_BR_115200));
        fptr.applySingleSettings();
        //Установка соединения с ККТ
        fptr.open();
        //Проверка состояния логического соединения
        boolean isOpened = fptr.isOpened();
        fptr.setParam(1021, operator);//"Кассир Иванов И."
        fptr.setParam(1203, "");//ИНН Кассира
        fptr.operatorLogin();
        fptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        fptr.report();
        String errorString = String.format("%d [%s]", fptr.errorCode(), fptr.errorDescription());
        errorMessagePane.setText(errorString + "\n"+errorMessagePane.getText());
    }

    public void printReceipt(JTextPane errorMessagePane, String filePath, String operator, String commodityName, float quantity, float price) {
//        if(filePath==null) {
//            errorMessagePane.setText("Отсутствует путь к файлу! "+" "+filePath + "\n"+errorMessagePane.getText());
//            return;
//        }
//        //errorMessagePane.setText("Printing receipt!"+" "+filePath + "\n"+errorMessagePane.getText());
//        File file = new File(filePath);
//        if (!file.exists()) {
//            errorMessagePane.setText("Файл не обнаружен! "+" "+filePath + "\n"+errorMessagePane.getText());
//            return;
//        }
//        FileInputStream fileInputStream;
//        try{
//            fileInputStream = new FileInputStream(file);
//        }catch (FileNotFoundException e){
//            errorMessagePane.setText("Файл не обнаружен! "+" "+filePath + "\n"+errorMessagePane.getText());
//            return;
//        }
//        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//        BufferedReader br = new BufferedReader(inputStreamReader);
//        List<String[]> receiptPositions = new ArrayList<>();
//        String strLine;
//        String[] strParse;
//        try{
//            while ((strLine = br.readLine()) != null){
//                strParse = strLine.split(";");
//                receiptPositions.add(strParse);
//                System.out.println(strLine);
//            }
//        }catch (IOException e){
//            errorMessagePane.setText("Ошибка чтения файла! "+" "+filePath + "\n"+errorMessagePane.getText());
//            return;
//        }
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, String.valueOf(IFptr.LIBFPTR_MODEL_ATOL_AUTO));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, String.valueOf(IFptr.LIBFPTR_PORT_COM));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, comPort);
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, String.valueOf(IFptr.LIBFPTR_PORT_BR_115200));
        fptr.applySingleSettings();
        //Установка соединения с ККТ
        fptr.open();

        boolean isOpened = fptr.isOpened();
        fptr.setParam(1021, operator);//"Кассир Иванов И."
        fptr.setParam(1203, "");//ИНН Кассира
        fptr.operatorLogin();
        fptr.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL);
        fptr.openReceipt();
        float payment = quantity*price;
//        float payment = 0.0F;
//        for (int i = 0; i < receiptPositions.size()-1; i++) {
//            String[] printStr = receiptPositions.get(i);
//            float price = Float.parseFloat(printStr[1]);
//            float quantity = Float.parseFloat(printStr[2]);
//            payment += price*quantity;
//            fptr.setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, printStr[0]);//Наименование товара
//            fptr.setParam(IFptr.LIBFPTR_PARAM_PRICE, price);
//            fptr.setParam(IFptr.LIBFPTR_PARAM_QUANTITY,quantity);
//            fptr.setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);
//            fptr.registration();
//        }
        fptr.setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, commodityName);//Наименование товара
        fptr.setParam(IFptr.LIBFPTR_PARAM_PRICE, price);
        fptr.setParam(IFptr.LIBFPTR_PARAM_QUANTITY,quantity);
        fptr.setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);
        fptr.setParam(1212,1);//Признак предмета расчета 1-Товар
        fptr.setParam(1214, 4);//Признак способа расчета 4-Полный расчет
        fptr.registration();

        fptr.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_CASH);
        fptr.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_SUM, payment);
        fptr.payment();
        fptr.closeReceipt();

//        //Проверка состояния документа
//        while (fptr.checkDocumentClosed() < 0) {
//            // Не удалось проверить состояние документа. Вывести пользователю текст ошибки, попросить устранить неполадку и повторить запрос
//            errorMessagePane.setText(fptr.errorDescription() + "\n"+errorMessagePane.getText());
//            //System.out.println(fptr.errorDescription());
//            continue;
//        }
//
//        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
//            // Документ не закрылся. Требуется его отменить (если это чек) и сформировать заново
//            fptr.cancelReceipt();
//            return;
//        }
//
//        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
//            // Можно сразу вызвать метод допечатывания документа, он завершится с ошибкой, если это невозможно
//            while (fptr.continuePrint() < 0) {
//                // Если не удалось допечатать документ - показать пользователю ошибку и попробовать еще раз.
//                System.out.println(String.format("Не удалось напечатать документ (Ошибка \"%s\"). Устраните неполадку и повторите.", fptr.errorDescription()));
//                continue;
//            }
//        }
    }

    public void init() {
        dataService = new FileDataService();
        files = dataService.getFileList();
        System.loadLibrary("fptr10");
        fptr = new Fptr();
        setComPort("COM6");
    }

    public void setComPort(String port){
        comPort = port;
    }

    @Override
    public long getShiftState() {
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, String.valueOf(IFptr.LIBFPTR_MODEL_ATOL_AUTO));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, String.valueOf(IFptr.LIBFPTR_PORT_COM));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, comPort);
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, String.valueOf(IFptr.LIBFPTR_PORT_BR_115200));
        fptr.applySingleSettings();
        //Установка соединения с ККТ
        fptr.open();
        //Проверка состояния логического соединения
        boolean isOpened = fptr.isOpened();
        fptr.setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);
        fptr.queryData();
        return fptr.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE);
    }

    @Override
    public void checkConnectionIsOpen(JTextPane errorMessagePane) {
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_MODEL, String.valueOf(IFptr.LIBFPTR_MODEL_ATOL_AUTO));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_PORT, String.valueOf(IFptr.LIBFPTR_PORT_COM));
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_COM_FILE, comPort);
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_BAUDRATE, String.valueOf(IFptr.LIBFPTR_PORT_BR_115200));
        fptr.applySingleSettings();
        //Установка соединения с ККТ
        fptr.open();
        //Проверка состояния логического соединения
        if (fptr.isOpened()){
            errorMessagePane.setText("Соединение установлено!" + "\n"+errorMessagePane.getText());
        }else {
            errorMessagePane.setText("Соединение не установлено!" + "\n"+errorMessagePane.getText());
        }
    }

    @Override
    public void closeConnection() {
        fptr.close();
    }

    public List<String> getFileList() {
        return files;
    }
}
