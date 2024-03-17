import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static final String SERVER = "localhost";
    private static final String PUERTO = "3306";
    private static final String DATABASE = "juego";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection instancia;


    public static synchronized Connection getInstancia() {
        if (instancia == null) {
            instancia = crearConexion();
        }
        return instancia;
    }

    private static Connection crearConexion() {
        Properties propiedades = new Properties();
        propiedades.put("user", USER);
        propiedades.put("password", PASSWORD);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + SERVER + ":" + PUERTO + "/" + DATABASE + "?rewriteBatchedStatements=true", propiedades);
        } catch (SQLException e) {
            System.err.println("Error al abrir conexión");
            e.printStackTrace();
        }
        return conn;
    }

    public static synchronized void close() {

        try {
            if (instancia != null) {
                instancia.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
