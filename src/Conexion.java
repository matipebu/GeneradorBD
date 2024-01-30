import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static String SERVER="localhost";
    private static String PUERTO="3306";
    private static String DATABASE="juego";
    private static String USER="root";
    private static String PASSWORD="root";
    private static Conexion conexionBDInstance;
    private Connection conn;

    private Conexion(){
        abrirConexion();
    }

    public static Conexion getConexion(){
        if (conexionBDInstance == null){
            conexionBDInstance = new Conexion();
        }
        return conexionBDInstance;
    }

    public Connection getConnection(){
        try {
            if(conn.isClosed()||conn==null){
                abrirConexion();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }


    private void abrirConexion(){
        Properties propiedades = new Properties();
        propiedades.put("user",USER);
        propiedades.put("password",PASSWORD);
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PUERTO+"/"+DATABASE,propiedades);
        } catch (SQLException e) {
            System.err.println("Error al abrir conexión");
            e.printStackTrace();
        }
    }

    public void cerrarConexion(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}