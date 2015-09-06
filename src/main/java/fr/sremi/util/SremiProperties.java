package fr.sremi.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class SremiProperties {

    private static SremiProperties util = null;
    private static Properties properties = null;
    private static final String PROPERTIES_FILE = "/resources/sremi.properties";

    private SremiProperties() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext();
        Resource resource = appContext.getResource("classpath:sremi.properties");

        try {
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // FileInputStream propFile = null;
        // try {
        // propFile = new FileInputStream(PROPERTIES_FILE);
        // properties = new Properties(System.getProperties());
        // properties.load(propFile);
        // } catch (IOException e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // if (propFile != null) {
        // propFile.close();
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
    }

    public static SremiProperties getInstance() {
        if (util == null) {
            util = new SremiProperties();
        }
        return util;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(PROPERTIES_FILE);
            properties.store(fos, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }
}
