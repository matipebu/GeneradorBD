import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreadorBD {
    public static void main(String[] args) {
        crearTablas();
        GeneradorBD.generarDatos();
    }
    
    public static void crearTablas() {
        try (Connection conexion = Conexion.getInstancia()) {
            try (Statement statement = conexion.createStatement()) {
                statement.executeUpdate(CREAR_TABLAS_QUERY_SERVIDORES);
                statement.executeUpdate(CREAR_TABLAS_QUERY_USUARIOS);
                statement.executeUpdate(CREAR_TABLAS_QUERY_PERSONAJES);
                statement.executeUpdate(CREAR_TABLAS_QUERY_MAPAS);
                statement.executeUpdate(CREAR_TABLAS_QUERY_ZONAS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static final String CREAR_TABLAS_QUERY_SERVIDORES = "CREATE TABLE IF NOT EXISTS Servidores (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "nombre VARCHAR(255), " +
            "region VARCHAR(255)" +
            ");";
    
    private static final String CREAR_TABLAS_QUERY_USUARIOS = "CREATE TABLE IF NOT EXISTS Usuarios (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "nombre VARCHAR(255), " +
            "codigo VARCHAR(4), " +
            "servidor_id INT, " +
            "FOREIGN KEY (servidor_id) REFERENCES Servidores(id)" +
            ");";
    
    private static final String CREAR_TABLAS_QUERY_PERSONAJES = "CREATE TABLE IF NOT EXISTS Personajes (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "nombre VARCHAR(255), " +
            "usuario_id INT, " +
            "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)" +
            ");";
    
    private static final String CREAR_TABLAS_QUERY_MAPAS = "CREATE TABLE IF NOT EXISTS Mapas (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "nombre VARCHAR(255), " +
            "dificultad INT, " +
            "servidor_id INT, " +
            "FOREIGN KEY (servidor_id) REFERENCES Servidores(id)" +
            ");";
    
    private static final String CREAR_TABLAS_QUERY_ZONAS = "CREATE TABLE IF NOT EXISTS Zonas (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "nombre VARCHAR(255), " +
            "ancho INT, " +
            "alto INT, " +
            "mapa_id INT, " +
            "FOREIGN KEY (mapa_id) REFERENCES Mapas(id)" +
            ");";
}
