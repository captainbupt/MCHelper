package com.vgomc.mchelper.transmit.file;

import android.content.Context;
import android.os.Environment;

import com.thoughtworks.xstream.XStream;
import com.vgomc.mchelper.Entity.setting.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.EventListener;
import java.util.List;

/**
 * Created by weizhouh on 6/3/2015.
 */
public class FileServiceProvider {

    public static final String SUFFIX = ".xml";

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
        Configuration configuration = (Configuration) new XStream().fromXML(file);
        return configuration;
    }

    public static String getExternalStoragePath() {
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "configurations";
        File directoryFile = new File(directory);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            directoryFile.delete();
            directoryFile.mkdirs();
        }
        return directory;
    }

    public static String[] getConfigurationFileNames() {
        File rootDirectory = new File(getExternalStoragePath());
        return rootDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(SUFFIX);
            }
        });
    }

}
