package ru.ttv.receiptprintingGUI.service;

import ru.ttv.receiptprintingGUI.api.DataService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Timofey Teplykh
 */
public class FileDataService implements DataService {
    public List<String> getFileList() {
        final File folder = new File("C:\\receipt_data");
        //final String[] files = folder.list((current, name) -> new File(current,name).isFile());
        final String[] files = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir,name).isFile();
            }
        });
        final List<File> fileList = new ArrayList<>();
        if(files == null) return Collections.emptyList();
        return Arrays.asList(files);
    }
}
