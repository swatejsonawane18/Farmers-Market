import java.sql.*;

public abstract class Database implements AutoCloseable{
    static Connection connection = null;

//    static final String username = "31165";
//    static final String password = "31165@mysql";
//    static final String url = "jdbc:mysql://localhost:3306/UniversityEnrollmentSystem";

    static final String url = "jdbc:mysql://localhost:3306/farmarket_schema";
    static final  String name = "root";
    static final String pass = "$w@teJ1800";

    static {
        try {
            connection = DriverManager.getConnection(url,name,pass);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private Database(){
        System.out.println("private constructor");
    }

    public static ResultSet executeQuery(String sql){
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int executeUpdate(String sql){
        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static boolean execute(String sql){
        try {
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select version()");
            System.out.println("Database connected");
            while(resultSet.next())
                System.out.println("MySQL: "+resultSet.getString(1));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}