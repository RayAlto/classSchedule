package top.rayalto.classSchedule.dataTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Properties;
import java.util.Set;

public class UserConfig {
    private static String configFileParentDir = System.getProperty("user.home") + File.separator + ".classSchedule";
    private static String configFileDir = configFileParentDir + File.separator + "config.conf";
    private static Path configFileParentDirPath = Paths.get(configFileParentDir);
    private static Path configFileDirPath = Paths.get(configFileDir);
    private static Properties properties = new Properties();
    private static boolean _loaded = false;

    public static boolean logged = false;
    public static Object loginLock = new Object();

    static {
        if (Files.exists(configFileDirPath)) {
            try (FileInputStream configFileInputStream = new FileInputStream(configFileDir)) {
                properties.load(configFileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            _loaded = true;
        } else {
            if (Files.notExists(configFileParentDirPath)) {
                try {
                    Files.createDirectory(configFileParentDirPath);
                    Files.createFile(Path.of(configFileDir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Set<PosixFilePermission> configFilePermissions = PosixFilePermissions.fromString("rw-------");
        Set<PosixFilePermission> configFileParentDirPermissions = PosixFilePermissions.fromString("rwx------");
        try {
            Files.setPosixFilePermissions(configFileParentDirPath, configFileParentDirPermissions);
            Files.setPosixFilePermissions(configFileDirPath, configFilePermissions);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            System.out.println("current operator system does not support POSIX file systems");
        }
    }

    public static void initializeUserConfig() {
        save();
    }

    public static boolean loaded() {
        return _loaded;
    }

    public static void save() {
        try (FileOutputStream configFileOutputStream = new FileOutputStream(configFileDir)) {
            properties.store(configFileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String configName) {
        return properties.getProperty(configName);
    }

    public static void setConfig(String configName, String value) {
        properties.setProperty(configName, value);
    }
}