package top.rayalto.classSchedule.database;

import top.rayalto.classSchedule.dataTypes.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.MariaDbPoolDataSource;

public class DatabaseEntity {

    private static MariaDbPoolDataSource poolDataSource = new MariaDbPoolDataSource();

    static {
        try {
            poolDataSource.setServerName("www.rayalto.top");
            poolDataSource.setPortNumber(8306);
            poolDataSource.setDatabaseName("classSchedule");
            poolDataSource.setUser("rayalto");
            poolDataSource.setPassword("WoCaoNiMa123+++mysql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    synchronized public static void initializeConnectionPool() {
        System.out.println("initialize connection pool ... ");
        try {
            poolDataSource.initialize();
        } catch (SQLException e) {
            System.err.println("failed, about to exit");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("connection pool initialized");
    }

    synchronized public static boolean login(String username, String password) {
        System.out.format("try to login with username: %s, password: %s%n", username, password);
        String passwordHash = null;
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
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
            if (!resultSet.next()) {
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

    synchronized public static List<Schedule> getSchedule(String startDateString, String endDateString) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM schedule WHERE startDate BETWEEN ? AND ?");
            statement.setDate(1, Date.valueOf(startDateString));
            statement.setDate(2, Date.valueOf(endDateString));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.lessonId = resultSet.getInt(1);
                schedule.periods = resultSet.getInt(2);
                schedule.startDate = resultSet.getDate(3);
                schedule.startTime = resultSet.getTime(4);
                schedule.endTime = resultSet.getTime(5);
                schedule.startWeekday = resultSet.getInt(6);
                schedule.weekIndex = resultSet.getInt(7);
                schedule.roomId = resultSet.getInt(8);
                schedules.add(schedule);
            }
            System.out.format("got %d results", schedules.size());
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return schedules;
    }

    synchronized public static List<Schedule> getSchedule(String dateString) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE startDate = ?");
            statement.setDate(1, Date.valueOf(dateString));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.lessonId = resultSet.getInt(1);
                schedule.periods = resultSet.getInt(2);
                schedule.startDate = resultSet.getDate(3);
                schedule.startTime = resultSet.getTime(4);
                schedule.endTime = resultSet.getTime(5);
                schedule.startWeekday = resultSet.getInt(6);
                schedule.weekIndex = resultSet.getInt(7);
                schedule.roomId = resultSet.getInt(8);
                schedules.add(schedule);
            }
            System.out.format("got %d results", schedules.size());
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return schedules;
    }
}
