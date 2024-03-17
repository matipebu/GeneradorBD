import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaDAO {
    // Atributos para los PreparedStatements
    private PreparedStatement personajesPorUsuarioStatement;
    private PreparedStatement numeroPersonajesDeterminadoUsuarioStatement;
    private PreparedStatement personajesDeUsuarioStatement;
    private PreparedStatement personajesPorUsuarioYServidorStatement;
    private PreparedStatement servidoresConMasPersonajesStatement;
    private PreparedStatement numeroServidoresDeterminadaRegionStatement;
    private PreparedStatement numeroServidoresPorRegionStatement;
    private PreparedStatement zonasDeMapaStatement;
    private PreparedStatement rankServers;
    private PreparedStatement listServers;
    private PreparedStatement getUserPJ;
    private PreparedStatement userPJs;
    private PreparedStatement areaMap;

    // Constructor para inicializar los PreparedStatements
    public ConsultaDAO() throws SQLException {
        Connection conexion = Conexion.getInstancia();
        // Preparar los PreparedStatements
        personajesPorUsuarioStatement = conexion.prepareStatement("SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes FROM Usuarios u LEFT JOIN Personajes p ON u.id = p.usuario_id GROUP BY u.id HAVING COUNT(p.id) >= 1");
        numeroPersonajesDeterminadoUsuarioStatement = conexion.prepareStatement("SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes FROM Usuarios u LEFT JOIN Personajes p ON u.id = p.usuario_id WHERE u.id = ?");
        personajesDeUsuarioStatement = conexion.prepareStatement("SELECT u.nombre AS usuario_nombre, p.nombre AS personaje_nombre, s.nombre AS servidor_nombre FROM Usuarios u JOIN Personajes p ON u.id = p.usuario_id JOIN Servidores s ON u.servidor_id = s.id WHERE u.id = ?");
        personajesPorUsuarioYServidorStatement = conexion.prepareStatement("SELECT u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes, s.nombre AS servidor_nombre FROM Usuarios u JOIN Personajes p ON u.id = p.usuario_id JOIN Servidores s ON u.servidor_id = s.id GROUP BY u.id, s.id");
        servidoresConMasPersonajesStatement = conexion.prepareStatement("SELECT s.nombre AS servidor_nombre, COUNT(p.id) AS cantidad_personajes FROM Servidores s LEFT JOIN Mapas m ON s.id = m.servidor_id LEFT JOIN Zonas z ON m.id = z.mapa_id LEFT JOIN Personajes p ON z.id = p.usuario_id GROUP BY s.id ORDER BY cantidad_personajes DESC LIMIT ?");
        numeroServidoresDeterminadaRegionStatement = conexion.prepareStatement("SELECT COUNT(id) AS cantidad_servidores FROM Servidores WHERE region = ?");
        numeroServidoresPorRegionStatement = conexion.prepareStatement("SELECT region, COUNT(id) AS cantidad_servidores FROM Servidores GROUP BY region");
        zonasDeMapaStatement = conexion.prepareStatement("SELECT nombre, alto, ancho FROM Zonas WHERE mapa_id = ?");
        rankServers = conexion.prepareStatement("SELECT s.nombre AS servidor, COUNT(p.id) AS cantidad_personajes FROM Servidores s LEFT JOIN Usuarios u ON s.id = u.servidor_id LEFT JOIN Personajes p ON u.id = p.usuario_id GROUP BY s.id ORDER BY cantidad_personajes DESC LIMIT 5");
        listServers = conexion.prepareStatement( "SELECT region, nombre AS servidor FROM Servidores ORDER BY region, nombre");
        getUserPJ = conexion.prepareStatement("SELECT s.nombre AS servidor, p.nombre AS personaje FROM Servidores s JOIN Usuarios u ON s.id = u.servidor_id JOIN Personajes p ON u.id = p.usuario_id WHERE u.id = ?");
        userPJs = conexion.prepareStatement( "SELECT u.id AS usuario_id, u.nombre AS usuario_nombre, COUNT(p.id) AS cantidad_personajes " +
        "FROM Usuarios u " +
        "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
        "GROUP BY u.id");
        areaMap = conexion.prepareStatement("SELECT SUM(ancho * alto) AS area " +
        "FROM Zonas " +
        "WHERE mapa_id = ?");

    }

    // Métodos para ejecutar las consultas
    public void personajesPorUsuario() throws SQLException {
        try (ResultSet resultSet = personajesPorUsuarioStatement.executeQuery()) {
            while (resultSet.next()) {
                String usuarioNombre = resultSet.getString("usuario_nombre");
                int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                System.out.println(usuarioNombre + " (" + cantidadPersonajes + ")");
            }
        }
    }

    public void numeroPersonajesDeterminadoUsuario(int idUsuario) throws SQLException {
        numeroPersonajesDeterminadoUsuarioStatement.setInt(1, idUsuario);
        try (ResultSet resultSet = numeroPersonajesDeterminadoUsuarioStatement.executeQuery()) {
            if (resultSet.next()) {
                String usuarioNombre = resultSet.getString("usuario_nombre");
                int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                System.out.println("Usuario: " + usuarioNombre);
                System.out.println("Número de personajes: " + cantidadPersonajes);
            }
        }
    }

    public void personajesDeUsuario(int idUsuario) throws SQLException {
        personajesDeUsuarioStatement.setInt(1, idUsuario);
        try (ResultSet resultSet = personajesDeUsuarioStatement.executeQuery()) {
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

    public void personajesPorUsuarioYServidor() throws SQLException {
        try (ResultSet resultSet = personajesPorUsuarioYServidorStatement.executeQuery()) {
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

    public void servidoresConMasPersonajes(int cantidad) throws SQLException {
        servidoresConMasPersonajesStatement.setInt(1, cantidad);
        try (ResultSet resultSet = servidoresConMasPersonajesStatement.executeQuery()) {
            while (resultSet.next()) {
                String servidorNombre = resultSet.getString("servidor_nombre");
                int cantidadPersonajes = resultSet.getInt("cantidad_personajes");

                System.out.println("Servidor: " + servidorNombre);
                System.out.println("Número de personajes: " + cantidadPersonajes);
                System.out.println();
            }
        }
    }

    public void numeroServidoresDeterminadaRegion(String region) throws SQLException {
        numeroServidoresDeterminadaRegionStatement.setString(1, region);
        try (ResultSet resultSet = numeroServidoresDeterminadaRegionStatement.executeQuery()) {
            if (resultSet.next()) {
                int cantidadServidores = resultSet.getInt("cantidad_servidores");

                System.out.println("Número de servidores en la región " + region + ": " + cantidadServidores);
            } else {
                System.out.println("No se encontraron servidores en la región " + region);
            }
        }
    }

    public void numeroServidoresPorRegion() throws SQLException {
        try (ResultSet resultSet = numeroServidoresPorRegionStatement.executeQuery()) {
            while (resultSet.next()) {
                String region = resultSet.getString("region");
                int cantidadServidores = resultSet.getInt("cantidad_servidores");

                System.out.println("Número de servidores en la región " + region + ": " + cantidadServidores);
                System.out.println();
            }
        }
    }

    public void zonasDeMapa(int idMapa) throws SQLException {
        zonasDeMapaStatement.setInt(1, idMapa);
        try (ResultSet resultSet = zonasDeMapaStatement.executeQuery()) {
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
    public void rankServers() throws SQLException {
        try (ResultSet resultSet = rankServers.executeQuery()) {
            System.out.println("Los 5 servidores con más personajes:");
            while (resultSet.next()) {
                String nombreServidor = resultSet.getString("servidor");
                int cantidadPersonajes = resultSet.getInt("cantidad_personajes");
                System.out.println("El servidor " + nombreServidor + " con " + cantidadPersonajes + " personajes");
            }
        }
    }

    public void listServers() throws SQLException {
        try (ResultSet resultSet = listServers.executeQuery()) {
            String currentRegion = null;
            while (resultSet.next()) {
                String region = resultSet.getString("region");
                String servidor = resultSet.getString("servidor");

                if (!region.equals(currentRegion)) {
                    System.out.println("Región " + region + " :");
                    currentRegion = region;
                }

                System.out.println(servidor);
            }
        }
    }

    public void getUserPJ(int userId) throws SQLException {
        getUserPJ.setInt(1, userId);
        try (ResultSet resultSet = getUserPJ.executeQuery()) {
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

                System.out.println("(" + totalPersonajes + " personajes)");
                System.out.println("Servidor: " + servidor);
                System.out.println(personaje);
            }
        }
    }

    public void userPJs() throws SQLException {
        try (ResultSet resultSet = userPJs.executeQuery()) {
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

    public void areaMap(int mapaId) throws SQLException {
        areaMap.setInt(1, mapaId);
        try (ResultSet resultSet = areaMap.executeQuery()) {
            while (resultSet.next()) {
                int area = resultSet.getInt("area");
                System.out.println("El área del mapa con ID " + mapaId + " es: " + area);
            }
        }
    }
}