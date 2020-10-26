package top.rayalto.classSchedule.database;

import top.rayalto.classSchedule.dataTypes.Classmate;
import top.rayalto.classSchedule.dataTypes.Lesson;
import top.rayalto.classSchedule.dataTypes.Schedule;
import top.rayalto.classSchedule.dataTypes.Teacher;

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

    public static void initializeConnectionPool() {
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

    public static boolean login(String username, String password) {
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

    public static List<Schedule> getSchedule(String startDateString, String endDateString) {
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

    public static List<Schedule> getSchedule(String dateString) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE startDate = ?");
            statement.setDate(1, Date.valueOf(dateString));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Schedule schedule = new Schedule(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3),
                        resultSet.getTime(4), resultSet.getTime(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getInt(8));
                schedules.add(schedule);
            }
            System.out.format("got %d results", schedules.size());
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return schedules;
    }

    public static Lesson getLesson(int lessonId) {
        System.out.print("getting connection ... ");
        Lesson lesson = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM lesson WHERE id = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lesson = new Lesson(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getString(11),
                        resultSet.getInt(12));
            }
            if (lesson == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :", lesson.nameZh);
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return lesson;
    }

    public static Classmate getClassmate(String code) {
        System.out.print("getting connection ... ");
        Classmate classmate = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM classmate WHERE code = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                classmate = new Classmate(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7),
                        resultSet.getString(8));
            }
            if (classmate == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :", classmate.classmateName);
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return classmate;
    }

    public static Teacher getTeacher(int id) {
        System.out.print("getting connection ... ");
        Teacher teacher = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teacher = new Teacher(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
                );
            }
            if (teacher == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :", teacher.teacherName);
        } catch (SQLException e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }
        return teacher;
    }
}
