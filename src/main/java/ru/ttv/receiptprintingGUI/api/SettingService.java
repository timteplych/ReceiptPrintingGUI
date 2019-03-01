package ru.ttv.receiptprintingGUI.api;

import java.io.IOException;

/**
 * @author Timofey Teplykh
 */
public interface SettingService {

    void init(String propertiesFileName) throws IOException;

    String getComPortBagynbaev();

    String getComPortSapaeva();

    String getOperatorBagynbaev();

    String getOperatorSapaeva();

    String getCommodityList();

}
