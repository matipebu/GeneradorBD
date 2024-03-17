import java.sql.SQLException;

public class AnalisisBD {

    public static void rankServers() {
        ConsultaDAO consultaDAO = null;
        try {
            consultaDAO = new ConsultaDAO();
            consultaDAO.rankServers();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consultaDAO != null) {
            }
        }
    }

    public static void listServers() {
        ConsultaDAO consultaDAO = null;
        try {
            consultaDAO = new ConsultaDAO();
            consultaDAO.listServers();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consultaDAO != null) {
            }
        }
    }

    public static void getUserPJ(int userId) {
        ConsultaDAO consultaDAO = null;
        try {
            consultaDAO = new ConsultaDAO();
            consultaDAO.getUserPJ(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consultaDAO != null) {
            }
        }
    }

    public static void userPJs() {
        ConsultaDAO consultaDAO = null;
        try {
            consultaDAO = new ConsultaDAO();
            consultaDAO.userPJs();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consultaDAO != null) {
            }
        }
    }

    public static void areaMap(int mapaId) {
        ConsultaDAO consultaDAO = null;
        try {
            consultaDAO = new ConsultaDAO();
            consultaDAO.areaMap(mapaId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consultaDAO != null) {
            }
        }
    }
}
