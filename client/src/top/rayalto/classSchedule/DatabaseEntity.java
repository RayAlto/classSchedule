package top.rayalto.classSchedule;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.MariaDbPoolDataSource;

public class DatabaseEntity {

    private MariaDbPoolDataSource poolDataSource = new MariaDbPoolDataSource();

    public DatabaseEntity() {
        System.out.print("connecting to database ... ");
        try {
            poolDataSource.setServerName("www.rayalto.top");
            poolDataSource.setPortNumber(8306);
            poolDataSource.setDatabaseName("classSchedule");
            poolDataSource.setUser("rayalto");
            poolDataSource.setPassword("WoCaoNiMa123+++mysql");
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("done");
    }

    public boolean login(String username, String password) {
        String passwordHash = null;
        try (Connection connection = poolDataSource.getConnection()) {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            passwordHash = new BigInteger(1, messageDigest.digest("caonima123".getBytes())).toString(16);
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM user WHERE code = ? AND passwordHash = ?");
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            statement.close();
            if (!resultSet.next())
                return false;
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return true;
    }
}
