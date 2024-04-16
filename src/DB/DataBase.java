package DB;

import com.mysql.cj.jdbc.Driver;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
//Основной класс для взаимодействия с базой данных

public class DataBase  {
    private static final String url = "jdbc:mysql://localhost/workstatdb";
    private static final String user = "root";
    private static final String password = "root";
    private static final Logger LOGGER = Logger.getLogger(DataBase.class.getName());
//    Проверка подключения к БД
    public void connect () {
        try {
            Driver driver = new Driver();
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                LOGGER.info("Connection to Store DB succesfull!");
            }
        } catch (Exception e) {
            LOGGER.severe("Connection failed... " + e);
        }
    }
//    Создание новой сделки в боте
    public Boolean newWork (long chatId) {
        try {
            Driver driver = new Driver();
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                Statement statement = conn.createStatement();
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LOGGER.info("INSERT INTO stats (id, user, chatstat, date) VALUES (NULL, " + chatId + ", 0, \"" + date.format(formatter) + "\")");
                statement.executeUpdate("INSERT INTO stats (id, user, chatstat, date) VALUES (NULL, " + chatId + ", 0, \"" + date.format(formatter) + "\")");
                return true;
            }
        } catch (Exception e) {
            LOGGER.severe("Connection failed... " + e);
            return false;
        }
    }
//    Увеличение колличества чатов в сделке
    public String addChat (int count) {
        try {
            Driver driver = new Driver();
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT chatstat, id FROM stats ORDER BY id DESC LIMIT 1;");
                resultSet.next();
                int chatCount = resultSet.getInt("chatstat") + count;
                System.out.println(resultSet.getInt("chatstat") + " + " + count + " = " + chatCount);
                int id = resultSet.getInt("id");
                statement.executeUpdate("UPDATE stats SET chatstat = " + chatCount + " WHERE stats.id = " + id);
                return "Обработано чатов: " + chatCount + " (" + chatCount*75 + " руб.)";
            }
        } catch (Exception e) {
            LOGGER.severe("Connection failed... " + e);
            return "Произошла ошибка, попробуй позже";
        }
    }
//    Вывод статистики сделок
    public String getStat (long chatId) {
        StringBuilder res = new StringBuilder();
        try {
            Driver driver = new Driver();
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT date, chatstat FROM `stats` WHERE user = 902064437");
                if (resultSet.next()) {
                    while (resultSet.next()) {
                        int chatCount = resultSet.getInt("chatstat");
                        Date date = resultSet.getDate("date");
                        res.append(date.toString()).append(": заработано ").append(chatCount * 75).append(" руб. (").append(chatCount).append(" чатов)").append("\n");
                    }
                } else {
                    return "Пока что нечего показывать";
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Connection failed... " + e);
            return "Ошибка выгрузки статистики, попробуй позже";
        }
        return res.toString();
    }
}
