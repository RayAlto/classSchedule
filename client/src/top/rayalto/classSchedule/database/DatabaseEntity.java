package top.rayalto.classSchedule.database;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.MariaDbPoolDataSource;

public class DatabaseEntity {

    private static MariaDbPoolDataSource poolDataSource = new MariaDbPoolDataSource();

    static {
        System.out.print("connecting to database ... ");
        try {
            poolDataSource.setServerName("www.rayalto.top");
            poolDataSource.setPortNumber(8306);
            poolDataSource.setDatabaseName("classSchedule");
            poolDataSource.setUser("rayalto");
            poolDataSource.setPassword("WoCaoNiMa123+++mysql");
        } catch (SQLException e) {
            System.err.println("failed, about to exit.");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("done");
    }

    public static boolean login(String username, String password) {
        System.out.format("try to login with username: %s, password: %s%n", username, password);
        String passwordHash = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.print("calculating password sha1 ... ");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            passwordHash = new BigInteger(1, messageDigest.digest("caonima123".getBytes())).toString(16);
            System.out.println(passwordHash);
            System.out.print("checking username and password ... ");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM user WHERE code = ? AND passwordHash = ?");
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            statement.close();
            if (!resultSet.next()){
                System.out.println("does not match");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQL error");
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("failed");
            e.printStackTrace();
            return false;
        }
        System.out.println("match");
        return true;
    }
}
