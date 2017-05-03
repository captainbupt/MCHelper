package com.vgomc.mchelper.transmit.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.thoughtworks.xstream.XStream;
import com.vgomc.mchelper.entity.setting.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by weizhouh on 6/3/2015.
 */
public class FileServiceProvider {

    public static final String SUFFIX_CONFIGURATION = ".xml";
    public static final String SUFFIX_RECORD = ".csv";
    public static final String SUFFIX_PHOTO = ".jpg";

    public static boolean writeObjectToFile(Configuration configuration, String path) {
        XStream xstream = new XStream();
        String xml = xstream.toXML(configuration);
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(xml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Configuration readObjectFromFile(String path) {
        File file = new File(path);
        if (file.isDirectory())
            return null;
        Configuration configuration = (Configuration) new XStream().fromXML(file);
        return configuration;
    }

    public static String getExternalPath(Context context) {
        String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (!TextUtils.isEmpty(directoryPath)) {
            directoryPath += File.separator + "MCHelper";
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                directory.delete();
                directory.mkdirs();
            }
        } else {
            directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        }
        return directoryPath;
    }

    public static String getExternalLogPath(Context context) {
        String filePath = getExternalPath(context) + File.separator + "debug.txt";
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            file.delete();
        }
        return filePath;
    }


    public static String getExternalConfigurationPath(Context context) {
        String directory = getExternalPath(context) + File.separator + "configurations";
        File directoryFile = new File(directory);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            directoryFile.delete();
            System.out.println("create dirs");
            directoryFile.mkdirs();
        }
        return directory;
    }

    public static String getExternalPhotoPath(Context context) {
        String directory = getExternalPath(context) + File.separator + "photos";
        File directoryFile = new File(directory);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            directoryFile.delete();
            directoryFile.mkdirs();
        }
        return directory;
    }

    public static String[] getConfigurationFileNames(Context context) {
        File rootDirectory = new File(getExternalConfigurationPath(context));
        return rootDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(SUFFIX_CONFIGURATION);
            }
        });
    }

    public static void saveRecord(Context context, String fileName, String record) throws IOException {
        File file = new File(getExternalRecordPath(context) + File.separator + fileName + SUFFIX_RECORD);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file, true);//true表示在文件末尾追加
        fos.write((record + "\r\n").getBytes());
        fos.close();//流要及时关闭
    }

    public static String getExternalRecordPath(Context context) {
        String directory = getExternalPath(context) + File.separator + "record";
        File directoryFile = new File(directory);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            directoryFile.delete();
            directoryFile.mkdirs();
        }
        return directory;
    }

}
