package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/sys?useSSL=false&serverTimezone=UTC";
    private static final String USER = "tril";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Ошибка при подключении к базе данных: " + e.getMessage(), e);
            }
        }
        return connection;
    }


    public static List<ClothingItem> getClothingItems(String type) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT name, sizes, color, price, image_path FROM my_clothing WHERE type = ?";
        Connection conn = Database.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, type);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                items.add(new ClothingItem(
                        resultSet.getString("name"),
                        resultSet.getString("sizes"),
                        resultSet.getString("color"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_path")));
            }
        }
        return items;
    }
}