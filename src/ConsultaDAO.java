import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaDAO {

    public static void personajesPorUsuario() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes " +
                    "FROM Usuarios u " +
                    "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                    "GROUP BY u.id " +
                    "HAVING COUNT(p.id) >= 1";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String usuarioNombre = resultSet.getString("usuario_nombre");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                        System.out.println(usuarioNombre + " (" + cantidadPersonajes + ")");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void numeroPersonajesDeterminadoUsuario(int idUsuario) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes " +
                    "FROM Usuarios u " +
                    "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                    "WHERE u.id = ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setInt(1, idUsuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String usuarioNombre = resultSet.getString("usuario_nombre");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                        System.out.println("Usuario: " + usuarioNombre);
                        System.out.println("Número de personajes: " + cantidadPersonajes);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void personajesDeUsuario(int idUsuario) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT u.nombre AS usuario_nombre, p.nombre AS personaje_nombre, s.nombre AS servidor_nombre "
                    +
                    "FROM Usuarios u " +
                    "JOIN Personajes p ON u.id = p.usuario_id " +
                    "JOIN Servidores s ON u.servidor_id = s.id " +
                    "WHERE u.id = ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setInt(1, idUsuario);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String usuarioNombre = resultSet.getString("usuario_nombre");
                        String personajeNombre = resultSet.getString("personaje_nombre");
                        String servidorNombre = resultSet.getString("servidor_nombre");

                        System.out.println("Usuario: " + usuarioNombre);
                        System.out.println("Personaje: " + personajeNombre);
                        System.out.println("Servidor: " + servidorNombre);
                        System.out.println();
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void personajesPorUsuarioYServidor() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes, s.nombre AS servidor_nombre "
                    +
                    "FROM Usuarios u " +
                    "JOIN Personajes p ON u.id = p.usuario_id " +
                    "JOIN Servidores s ON u.servidor_id = s.id " +
                    "GROUP BY u.id, s.id";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String usuarioNombre = resultSet.getString("usuario_nombre");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");
                        String servidorNombre = resultSet.getString("servidor_nombre");

                        System.out.println("Usuario: " + usuarioNombre);
                        System.out.println("Número de personajes: " + cantidadPersonajes);
                        System.out.println("Servidor: " + servidorNombre);
                        System.out.println();
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void servidoresConMasPersonajes(int cantidad) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT s.nombre AS servidor_nombre, COUNT(p.id) AS cantidad_personajes " +
                    "FROM Servidores s " +
                    "LEFT JOIN Mapas m ON s.id = m.servidor_id " +
                    "LEFT JOIN Zonas z ON m.id = z.mapa_id " +
                    "LEFT JOIN Personajes p ON z.id = p.usuario_id " +
                    "GROUP BY s.id " +
                    "ORDER BY cantidad_personajes DESC " +
                    "LIMIT ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setInt(1, cantidad);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String servidorNombre = resultSet.getString("servidor_nombre");
                        int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                        System.out.println("Servidor: " + servidorNombre);
                        System.out.println("Número de personajes: " + cantidadPersonajes);
                        System.out.println();
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void numeroServidoresDeterminadaRegion(String region) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT COUNT(id) AS cantidad_servidores " +
                    "FROM Servidores " +
                    "WHERE region = ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, region);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int cantidadServidores = resultSet.getInt("cantidad_servidores");

                        System.out.println("Número de servidores en la región " + region + ": " + cantidadServidores);
                    } else {
                        System.out.println("No se encontraron servidores en la región " + region);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void numeroServidoresPorRegion() {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT region, COUNT(id) AS cantidad_servidores " +
                    "FROM Servidores " +
                    "GROUP BY region";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String region = resultSet.getString("region");
                        int cantidadServidores = resultSet.getInt("cantidad_servidores");

                        System.out.println("Número de servidores en la región " + region + ": " + cantidadServidores);
                        System.out.println();
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void zonasDeMapa(int idMapa) {
        try (Connection conexion = Conexion.getConexion().getConnection()) {
            String query = "SELECT nombre, alto, ancho " +
                    "FROM Zonas " +
                    "WHERE mapa_id = ?";

            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setInt(1, idMapa);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String nombreZona = resultSet.getString("nombre");
                        int alto = resultSet.getInt("alto");
                        int ancho = resultSet.getInt("ancho");

                        System.out.println("Nombre de la zona: " + nombreZona);
                        System.out.println("Alto: " + alto);
                        System.out.println("Ancho: " + ancho);
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
