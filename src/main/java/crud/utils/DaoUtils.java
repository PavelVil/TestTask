package crud.utils;

import java.sql.*;

/**
 * Created by Pavel on 05.07.2017.
 */
public class DaoUtils {


    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","root");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }

    public static byte[] getPhoto(int id){
        byte[] photo = null;
        ResultSet resultSet = null;
        try (Connection connection = DaoUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("select photo from testing.user WHERE id=?")) {
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                photo = resultSet.getBytes("photo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return photo;
    }
}
