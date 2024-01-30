import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalisisBD {

    public static void rankServers() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT s.nombre AS servidor, COUNT(p.id) AS cantidad_personajes " +
                           "FROM Servidores s " +
                           "LEFT JOIN Usuarios u ON s.id = u.servidor_id " +
                           "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                           "GROUP BY s.id " +
                           "ORDER BY cantidad_personajes DESC " +
                           "LIMIT 5";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    System.out.println("Los 5 servidores con más personajes:");
                    while (resultSet.next()) {
                        String nombreServidor = resultSet.getString("servidor");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");
                        System.out.println("El servidor " + nombreServidor + " con " + cantidadPersonajes + " personajes");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listServers() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT region, nombre AS servidor " +
                           "FROM Servidores " +
                           "ORDER BY region, nombre";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    String currentRegion = null;

                    while (resultSet.next()) {
                        String region = resultSet.getString("region");
                        String servidor = resultSet.getString("servidor");

                        if (!region.equals(currentRegion)) {
                            System.out.println("Región " + region+" :");
                            currentRegion = region;
                        }

                        System.out.println(servidor);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getUserPJ(int userId) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT s.nombre AS servidor, p.nombre AS personaje " +
                           "FROM Servidores s " +
                           "JOIN Usuarios u ON s.id = u.servidor_id " +
                           "JOIN Personajes p ON u.id = p.usuario_id " +
                           "WHERE u.id = ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setInt(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    boolean firstRow = true;
                    int totalPersonajes = 0;

                    while (resultSet.next()) {
                        if (firstRow) {
                            String usuarioNombre = resultSet.getString("personaje");
                            System.out.println("Usuario " + usuarioNombre + " (" + userId + ")");

                            firstRow = false;
                        }

                        String servidor = resultSet.getString("servidor");
                        String personaje = resultSet.getString("personaje");
                        totalPersonajes++;

                        System.out.println("("+totalPersonajes+" personajes)");
                        System.out.println("Servidor: "+servidor);
                        System.out.println(personaje);

                        
                    }

                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void userPJs() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT u.id AS usuario_id, u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes " +
                           "FROM Usuarios u " +
                           "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                           "GROUP BY u.id";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    int usuariosPorLinea = 0;

                    while (resultSet.next()) {
                        int usuarioId = resultSet.getInt("usuario_id");
                        String usuarioNombre = resultSet.getString("usuario_nombre");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                        System.out.print(usuarioNombre + " (" + cantidadPersonajes + ")   ");

                        usuariosPorLinea++;
                        if (usuariosPorLinea == 5) {
                            System.out.println(); // Nueva línea después de mostrar 5 usuarios
                            usuariosPorLinea = 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void areaMap(int mapaId) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = Conexion.getConexion().getConnection();
            String query = "SELECT SUM(ancho * alto) AS area " +
                    "FROM Zonas " +
                    "WHERE mapa_id = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, mapaId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int area = resultSet.getInt("area");
                System.out.println("El área del mapa con ID " + mapaId + " es: " + area);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(conexion, statement, resultSet);
        }
    }

    private static void cerrarRecursos(Connection conexion, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
