package com.example.fileupload.utils;


import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {

    public static String getFileNameSizeAsString(long fileSize) {
        try {
            return org.apache.commons.io.FileUtils.byteCountToDisplaySize(fileSize);
        } catch (Exception e) {
            e.printStackTrace();
            return "0 KB";
        }
    }
}
