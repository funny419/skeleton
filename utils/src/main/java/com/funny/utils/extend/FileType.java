package com.funny.utils.extend;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class FileType {
    public final static Map<String,String> FILE_TYPE_MAP = new HashMap<String,String>();

    static {
        FILE_TYPE_MAP.put("jpg","FFD8FF");
        FILE_TYPE_MAP.put("png","89504E47");
        FILE_TYPE_MAP.put("gif","47494638");
        FILE_TYPE_MAP.put("tif","49492A00");
        FILE_TYPE_MAP.put("bmp","424D");
        FILE_TYPE_MAP.put("dwg","41433130");
        FILE_TYPE_MAP.put("html","68746D6C3E");
        FILE_TYPE_MAP.put("rtf","7B5C727466");
        FILE_TYPE_MAP.put("xml","3C3F786D6C");
        FILE_TYPE_MAP.put("zip","504B0304");
        FILE_TYPE_MAP.put("rar","52617221");
        FILE_TYPE_MAP.put("psd","38425053");
        FILE_TYPE_MAP.put("eml","44656C69766572792D646174653A");
        FILE_TYPE_MAP.put("dbx","CFAD12FEC5FD746F");
        FILE_TYPE_MAP.put("pst","2142444E");
        FILE_TYPE_MAP.put("xls","D0CF11E0");
        FILE_TYPE_MAP.put("doc","D0CF11E0");
        FILE_TYPE_MAP.put("mdb","5374616E64617264204A");
        FILE_TYPE_MAP.put("wpd","FF575043");
        FILE_TYPE_MAP.put("eps","252150532D41646F6265");
        FILE_TYPE_MAP.put("ps","252150532D41646F6265");
        FILE_TYPE_MAP.put("pdf","255044462D312E");
        FILE_TYPE_MAP.put("qdf","AC9EBD8F");
        FILE_TYPE_MAP.put("pwl","E3828596");
        FILE_TYPE_MAP.put("wav","57415645");
        FILE_TYPE_MAP.put("avi","41564920");
        FILE_TYPE_MAP.put("ram","2E7261FD");
        FILE_TYPE_MAP.put("rm","2E524D46");
        FILE_TYPE_MAP.put("mpg","000001BA");
        FILE_TYPE_MAP.put("mov","6D6F6F76");
        FILE_TYPE_MAP.put("asf","3026B2758E66CF11");
        FILE_TYPE_MAP.put("mid","4D546864");
    }




    public static String getFileType(File file) {
        String fileType = null;
        byte[] bytes = new byte[50];

        try {
            InputStream inputStream = new FileInputStream(file);
            inputStream.read(bytes);

            fileType = getFileTypeByStream(bytes);

            inputStream.close();
        } catch (IOException e) {
            log.error("getFileType error : " + e.getMessage());
        }

        return fileType;
    }


    public static String getFileTypeByStream(byte[] bytes) {
        String filetypeHex = String.valueOf(getFileHexString(bytes));

        for (Map.Entry<String, String> entry : FILE_TYPE_MAP.entrySet()) {
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }

        return null;
    }


    public static String getFileHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            int v = aByte & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }
}
