package top.rayalto.classSchedule.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import top.rayalto.classSchedule.dataTypes.Classmate;
import top.rayalto.classSchedule.dataTypes.Department;
import top.rayalto.classSchedule.dataTypes.ExamMode;
import top.rayalto.classSchedule.dataTypes.Lesson;
import top.rayalto.classSchedule.dataTypes.LessonType;
import top.rayalto.classSchedule.dataTypes.Room;
import top.rayalto.classSchedule.dataTypes.Schedule;
import top.rayalto.classSchedule.dataTypes.ScheduleDetail;
import top.rayalto.classSchedule.dataTypes.SchoolClass;
import top.rayalto.classSchedule.dataTypes.Teacher;
import top.rayalto.classSchedule.dataTypes.User;

public class DatabaseEntity {

    private static MariaDbPoolDataSource poolDataSource = new MariaDbPoolDataSource();
    private static Calendar calendar = GregorianCalendar.getInstance();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static final Map<Integer, String> index2WeekString = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(0, "星期一");
            put(1, "星期二");
            put(2, "星期三");
            put(3, "星期四");
            put(4, "星期五");
            put(5, "星期六");
            put(6, "星期日");
        }
    };

    static {
        dateFormat.setLenient(false);
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

    public static java.util.Date isValidDateString(String dateString) {
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException pe) {
            return null;
        }
        return date;
    }

    public static String generateDateString(int year, int month, int day) {
        while (year >= 10000)
            year /= 10;
        while (month >= 100)
            month /= 10;
        while (day >= 100)
            day /= 10;
        return String.format("%04d:%02d:%02d", year, month, day);
    }

    public static String generateTimeString(int hour, int minute) {
        while (hour >= 100)
            hour /= 10;
        while (minute >= 100)
            minute /= 10;
        return String.format("%02d:%02d:00", hour, minute);
    }

    public static String[] getWeekStartEndDateStrings(String currentDateString) {
        try {
            calendar.setTime(dateFormat.parse(currentDateString));
        } catch (ParseException ignore) {
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDateString = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);
        String endDateString = dateFormat.format(calendar.getTime());
        return new String[] { startDateString, endDateString };
    }

    public static String[] getWeekStartEndDateStrings(java.util.Date currentDate) {
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDateString = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);
        String endDateString = dateFormat.format(calendar.getTime());
        return new String[] { startDateString, endDateString };
    }

    public static String[] getCurrentWeekStartEndStrings() {
        return getWeekStartEndDateStrings(
                java.util.Date.from(java.time.LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
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
            System.out.format("got %d results%n", schedules.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
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
            System.out.format("got %d results%n", schedules.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return schedules;
    }

    public static List<ScheduleDetail> getScheduleDetail(String startDateString, String endDateString) {
        List<ScheduleDetail> scheduleDetails = new ArrayList<ScheduleDetail>();
        List<Schedule> schedules = getSchedule(startDateString, endDateString);
        for (Schedule schedule : schedules) {
            Room roomInfo = getRoom(schedule.roomId);
            Lesson lessonInfo = getLesson(schedule.lessonId);
            LessonType lessonTypeInfo = getLessonType(lessonInfo.typeId);
            Department departmentInfo = getDepartment(lessonInfo.departmentId);
            ExamMode examModeInfo = getExamMode(lessonInfo.examModeId);
            List<SchoolClass> classes = getClasses(getClassIdsFromLesson(lessonInfo.id));
            List<Teacher> teachers = getTeachers(getTeachersFromLesson(lessonInfo.id));
            List<Classmate> classmates = getClassmates(getClassmatesFromLesson(lessonInfo.id));
            scheduleDetails.add(new ScheduleDetail(schedule, roomInfo, lessonInfo, lessonTypeInfo, departmentInfo,
                    examModeInfo, classes, teachers, classmates));
        }
        return scheduleDetails;
    }

    public static List<ScheduleDetail> getScheduleDetail(String dateString) {
        List<ScheduleDetail> scheduleDetails = new ArrayList<ScheduleDetail>();
        List<Schedule> schedules = getSchedule(dateString);
        for (Schedule schedule : schedules) {
            Room roomInfo = getRoom(schedule.roomId);
            Lesson lessonInfo = getLesson(schedule.lessonId);
            LessonType lessonTypeInfo = getLessonType(lessonInfo.typeId);
            Department departmentInfo = getDepartment(lessonInfo.departmentId);
            ExamMode examModeInfo = getExamMode(lessonInfo.examModeId);
            List<SchoolClass> classes = getClasses(getClassIdsFromLesson(lessonInfo.id));
            List<Teacher> teachers = getTeachers(getTeachersFromLesson(lessonInfo.id));
            List<Classmate> classmates = getClassmates(getClassmatesFromLesson(lessonInfo.id));
            scheduleDetails.add(new ScheduleDetail(schedule, roomInfo, lessonInfo, lessonTypeInfo, departmentInfo,
                    examModeInfo, classes, teachers, classmates));
        }
        return scheduleDetails;
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
                System.out.format("got 1 result :%s%n", lesson.nameZh);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return lesson;
    }

    public static List<Lesson> getLessons(List<Integer> lessonIds) {
        List<Lesson> lessons = new ArrayList<Lesson>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(lessonIds.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM lesson WHERE id IN (" + String.join(", ", idStrings) + ')';
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                lessons.add(new Lesson(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getString(11),
                        resultSet.getInt(12)));
            }
            System.out.format("got %d results%n", lessons.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return lessons;
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
                System.out.format("got 1 result :%s%n", classmate.classmateName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return classmate;
    }

    public static List<Classmate> getClassmates(List<String> classmateCodes) {
        List<Classmate> classmate = new ArrayList<Classmate>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            String queryString = "SELECT * FROM classmate WHERE code IN ('" + String.join("', '", classmateCodes)
                    + "')";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                classmate.add(new Classmate(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7),
                        resultSet.getString(8)));
            }
            System.out.format("got %d results%n", classmate.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
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
                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7),
                        resultSet.getString(8));
            }
            if (teacher == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", teacher.teacherName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return teacher;
    }

    public static List<Teacher> getTeachers(List<Integer> ids) {
        List<Teacher> teachers = new ArrayList<Teacher>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM teacher WHERE id IN (" + String.join(", ", idStrings) + ')';
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7),
                        resultSet.getString(8)));
            }
            System.out.format("got %d results%n", teachers.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return teachers;
    }

    public static User getUser(String code) {
        System.out.print("getting connection ... ");
        User user = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE code = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10));
            }
            if (user == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", user.realName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return user;
    }

    public static Department getDepartment(int id) {
        System.out.print("getting connection ... ");
        Department department = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM department WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                department = new Department(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            if (department == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", department.departmentName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return department;
    }

    public static List<Department> getDepartments(List<Integer> ids) {
        List<Department> departments = new ArrayList<Department>();
        System.out.print("getting connection ... ");
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM department WHERE id IN (" + String.join(", ", idStrings) + ')';
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                departments.add(new Department(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            System.out.format("got %d results%n", departments.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return departments;
    }

    public static SchoolClass getClass(int id) {
        System.out.print("getting connection ... ");
        SchoolClass schoolClass = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM class WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                schoolClass = new SchoolClass(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5));
            }
            if (schoolClass == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", schoolClass.className);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return schoolClass;
    }

    public static List<SchoolClass> getClasses(List<Integer> ids) {
        System.out.print("getting connection ... ");
        List<SchoolClass> schoolClasses = new ArrayList<SchoolClass>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            Statement statement = connection.createStatement();
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM class WHERE id IN (" + String.join(", ", idStrings) + ')';
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                schoolClasses.add(new SchoolClass(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getInt(5)));
            }
            System.out.format("got %d result%n", schoolClasses.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return schoolClasses;
    }

    public static Room getRoom(int id) {
        System.out.print("getting connection ... ");
        Room room = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM room WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                room = new Room(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            if (room == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", room.roomName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return room;
    }

    public static List<Room> getRooms(List<Integer> ids) {
        System.out.print("getting connection ... ");
        List<Room> rooms = new ArrayList<Room>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            Statement statement = connection.createStatement();
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM room WHERE id IN (" + String.join(", ", idStrings) + ')';
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                rooms.add(new Room(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            System.out.format("got %d result%n", rooms.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return rooms;
    }

    public static LessonType getLessonType(int id) {
        System.out.print("getting connection ... ");
        LessonType lessonType = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessonType WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lessonType = new LessonType(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            if (lessonType == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", lessonType.lessonTypeName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return lessonType;
    }

    public static List<LessonType> getLessonTypes(List<Integer> ids) {
        System.out.print("getting connection ... ");
        List<LessonType> lessonTypes = new ArrayList<LessonType>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            Statement statement = connection.createStatement();
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM lessonType WHERE id IN (" + String.join(", ", idStrings) + ')';
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                lessonTypes.add(new LessonType(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            System.out.format("got %d result%n", lessonTypes.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return lessonTypes;
    }

    public static ExamMode getExamMode(int id) {
        System.out.print("getting connection ... ");
        ExamMode examMode = null;
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM examMode WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                examMode = new ExamMode(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            if (examMode == null)
                System.out.println("no result");
            else
                System.out.format("got 1 result :%s%n", examMode.examModeName);
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return examMode;
    }

    public static List<ExamMode> getExamModes(List<Integer> ids) {
        System.out.print("getting connection ... ");
        List<ExamMode> examModes = new ArrayList<ExamMode>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            Statement statement = connection.createStatement();
            List<String> idStrings = new ArrayList<String>();
            idStrings.addAll(ids.stream().map(String::valueOf).collect(Collectors.toList()));
            String queryString = "SELECT * FROM examMode WHERE id IN (" + String.join(", ", idStrings) + ')';
            ResultSet resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                examModes.add(new ExamMode(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            System.out.format("got %d result%n", examModes.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return examModes;
    }

    public static List<Integer> getClassIdsFromLesson(int lessonId) {
        System.out.print("getting connection ... ");
        List<Integer> schoolClasses = new ArrayList<Integer>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT classId FROM lessonClasses WHERE lessonId = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                schoolClasses.add(resultSet.getInt(1));
            }
            System.out.format("got %d results%n", schoolClasses.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return schoolClasses;
    }

    public static List<Integer> getTeachersFromLesson(int lessonId) {
        System.out.print("getting connection ... ");
        List<Integer> teachers = new ArrayList<Integer>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT teacherId FROM lessonTeachers WHERE lessonId = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(resultSet.getInt(1));
            }
            System.out.format("got %d results%n", teachers.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return teachers;
    }

    public static List<String> getClassmatesFromLesson(int lessonId) {
        System.out.print("getting connection ... ");
        List<String> classmates = new ArrayList<String>();
        try (Connection connection = poolDataSource.getConnection()) {
            System.out.println("done");
            System.out.print("executing query ... ");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT classmateCode FROM lessonClassmates WHERE lessonId = ?");
            statement.setInt(1, lessonId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                classmates.add(resultSet.getString(1));
            }
            System.out.format("got %d results%n", classmates.size());
        } catch (SQLException e) {
            System.err.println("SQL Error");
            e.printStackTrace();
        }
        return classmates;
    }
}