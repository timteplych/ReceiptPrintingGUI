package ru.ttv.receiptprintingGUI.service;

import ru.ttv.receiptprintingGUI.api.SettingService;

import java.io.*;

/**
 * @author Timofey Teplykh
 */
public class SettingServiceImpl implements SettingService {
    private static final String COM_PORT_BAGYNBAEV = "COM6";
    private static final String KEY_COM_PORT_BAGYNBAEV = "com_port_bagynbaev";
    private static final String OPERATOR_BAGYNBAEV = "Власова Анна Александровна";
    private static final String KEY_OPERATOR_BAGYNBAEV = "operator_bagynbaev";
    private static final String COM_PORT_SAPAEVA = "COM7";
    private static final String KEY_COM_PORT_SAPAEVA = "com_port_sapaeva";
    private static final String OPERATOR_SAPAEVA = "Сапаева Эркингул Мусаевна";
    private static final String KEY_OPERATOR_SAPAEVA = "operator_sapaeva";
    private static final String KEY_COMMODITY_LIST = "commodity_list";
    private static final String COMMODITY_LIST = "Автоаксессуары,Авточехлы,Автокомпрессоры,Домкраты";

    private String comPortBagynbaev;
    private String comPortSapaeva;
    private String operatorBagynbaev;
    private String operatorSapaeva;
    private String commodityList;

    public void init(String propertiesFileName) throws IOException {
        final File file = new File(propertiesFileName);
        if (!file.exists()) {
            FileWriter fileWriter = new FileWriter(propertiesFileName, true);
            fileWriter.append(KEY_COM_PORT_BAGYNBAEV + "=" + COM_PORT_BAGYNBAEV);
            fileWriter.append("\n");
            fileWriter.append(KEY_COM_PORT_SAPAEVA + "=" + COM_PORT_SAPAEVA);
            fileWriter.append("\n");
            fileWriter.append(KEY_OPERATOR_BAGYNBAEV + "=" + OPERATOR_BAGYNBAEV);
            fileWriter.append("\n");
            fileWriter.append(KEY_OPERATOR_SAPAEVA + "=" + OPERATOR_SAPAEVA);
            fileWriter.append("\n");
            fileWriter.append(KEY_COMMODITY_LIST + "=" + COMMODITY_LIST);
            fileWriter.append("\n");
            fileWriter.flush();

            comPortBagynbaev = COM_PORT_BAGYNBAEV;
            comPortSapaeva = COM_PORT_SAPAEVA;
            operatorBagynbaev = OPERATOR_BAGYNBAEV;
            operatorSapaeva = OPERATOR_SAPAEVA;
            commodityList = COMMODITY_LIST;
            fileWriter.close();
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(inputStreamReader);
        String strLine;
        String[] strParse;
        while ((strLine = br.readLine()) != null) {
            strParse = strLine.split("=");
            if (strParse[0].equals(KEY_COM_PORT_BAGYNBAEV)) {
                comPortBagynbaev = strParse[1];
            }
            if (strParse[0].equals(KEY_COM_PORT_SAPAEVA)) {
                comPortSapaeva = strParse[1];
            }
            if (strParse[0].equals(KEY_OPERATOR_BAGYNBAEV)) {
                operatorBagynbaev = strParse[1];
            }
            if (strParse[0].equals(KEY_OPERATOR_SAPAEVA)) {
                operatorSapaeva = strParse[1];
            }
            if (strParse[0].equals(KEY_COMMODITY_LIST)) {
                commodityList = strParse[1];
            }
        }
        br.close();
        inputStreamReader.close();
    }

    public String getComPortBagynbaev() {
        return comPortBagynbaev;
    }

    public String getComPortSapaeva() {
        return comPortSapaeva;
    }

    public String getOperatorBagynbaev() {
        return operatorBagynbaev;
    }

    public String getOperatorSapaeva() {
        return operatorSapaeva;
    }

    public String getCommodityList() {
        return commodityList;
    }
}
