package ru.javawebinar.basejava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

public class MainJDBC {
    private static File PROPS = new File("config\\resumes.properties");
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    private static Gson GSON = new GsonBuilder()
//            .registerTypeAdapter(AbstractSection.class,new JsonSectionAdapter())
            .create();

    public static void main(String[] args) {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }

        SQLHelper helper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));

        helper.execute("" +
                "select r.uuid," +
                "r.full_name," +
                "(select json_object_agg(type,value) from contact where resume_uuid = r.uuid)," +
                "(select json_object_agg(type,content) from section where resume_uuid = r.uuid) " +
                "from resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("---" + rs.getString("uuid"));
                String contacts = rs.getString(3);
                HashMap<String, String> hc = GSON.fromJson(contacts, HashMap.class);
                if (hc != null) {
                    for (String type : hc.keySet()) {
                        System.out.println("" + type + ":" + hc.get(type));
                    }
                }
                String sections = rs.getString(4);
                HashMap<String, String> hs = GSON.fromJson(sections, HashMap.class);
                if (hs != null) {
                    for (String type : hs.keySet()) {
                        System.out.println("" + type + ":" + hs.get(type));
                    }
                }
            }
            return null;
        });
    }
}
