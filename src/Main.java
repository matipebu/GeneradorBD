import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\nMenú Principal:");
                System.out.println("1. Operaciones con AnalisisBD");
                System.out.println("2. Operaciones con ConsultaDAO");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción (1-3): ");

                int opcionPrincipal = scanner.nextInt();

                switch (opcionPrincipal) {
                    case 1:
                        menuAnalisisBD();
                        break;
                    case 2:
                        menuConsultaDAO();
                        break;
                    case 3:
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                }
            }
        } catch (Exception e) {
            // Manejar la excepción, si es necesario
            e.printStackTrace();
        } finally {
            // Se ejecutará siempre, independientemente de si hay una excepción o no
            if (scanner != null) {
                scanner.close(); // Cierra el Scanner en el bloque finally
            }
        }
    }

    public static void menuAnalisisBD() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\nMenú AnalisisBD:");
                System.out.println("1. Mostrar los 5 servidores con más personajes");
                System.out.println("2. Listar servidores por región");
                System.out.println("3. Número de personajes por servidor y sus nombres");
                System.out.println("4. Mostrar el número de personajes de un usuario en concreto");
                System.out.println("5. Mostrar el área de un mapa con un ID en concreto");
                System.out.println("6. Volver al menú principal");
                System.out.print("Seleccione una opción (1-6): ");

                int opcionAnalisisBD = scanner.nextInt();

                switch (opcionAnalisisBD) {
                    case 1:
                        AnalisisBD.rankServers();
                        break;
                    case 2:
                        AnalisisBD.listServers();
                        break;
                    case 3:
                        System.out.print("Ingrese el ID del usuario: ");
                        int userId = scanner.nextInt();
                        AnalisisBD.getUserPJ(userId);
                       
                        break;
                    case 4:
                        
                        AnalisisBD.userPJs();
                        
                        break;
                    case 5:
                        System.out.print("Ingrese el ID del mapa: ");
                        int mapaId = scanner.nextInt();
                        AnalisisBD.areaMap(mapaId);
                        break;
                    case 6:
                        return; // Volver al menú principal
                    default:

                }
            }
        } catch (Exception e) {
            // Manejar la excepción, si es necesario
            e.printStackTrace();
        } finally {
            // Se ejecutará siempre, independientemente de si hay una excepción o no
            if (scanner != null) {
                scanner.close(); // Cierra el Scanner en el bloque finally
            }
        }
    }

    public static void menuConsultaDAO() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\nMenú ConsultaDAO:");
                // Agrega aquí las opciones para los métodos de ConsultaDAO
                System.out.println(
                        "1. El número de personajes por usuario, devolviendo el nombre de usuario y número de personajes siempre que el número sea igual o mayor que 1.");
                System.out.println("2. El número de personajes de un usuario X, devolviendo su nombre, y número.");
                System.out.println(
                        "3. Los personajes de un usuario X, devolviendo el nombre del usuario, de cada personaje y el servidor en el que están.");
                System.out.println(
                        "4. El número de personajes de cada usuario en cada servidor devolviendo el nombre de usuario, número de personajes y nombre del servidor.");
                System.out.println(
                        "5. Los X servidores con más personajes ordenados de mayor a menor, devolviendo el nombre y el número. X es el parámetro que determina el número a delimitar, por ejemplo los 3 con más.");
                System.out.println("6. El número de servidores de X región.");
                System.out.println("7. El número de servidores de cada región.");
                System.out.println(
                        "8. Las zonas de un mapa con id X, devolviendo el nombre de la zona, el alto y el ancho.");
                System.out.println("9. Salir");

                System.out.print("Seleccione una opción (1-9): ");

                int opcionConsultaDAO = scanner.nextInt();

                switch (opcionConsultaDAO) {

                    case 1:
                        ConsultaDAO.personajesPorUsuario();
                        break;
                    case 2:
                        System.out.print("Escribe un id de Usuario : ");
                        int nomUsu = scanner.nextInt();
                        ConsultaDAO.numeroPersonajesDeterminadoUsuario(nomUsu);
                        break;
                    case 3:
                        System.out.print("Escribe un nombre de Usuario : ");
                        int nombUsu = scanner.nextInt();
                        ConsultaDAO.personajesDeUsuario(nombUsu);
                        break;
                    case 4:
                        ConsultaDAO.personajesPorUsuarioYServidor();
                        break;
                    case 5:
                        System.out.print("Número de servidores a saber : ");
                        int numServ = scanner.nextInt();
                        ConsultaDAO.servidoresConMasPersonajes(numServ);
                        break;
                    case 6:
                        System.out.print("Ingresa la region: ");
                        scanner.nextLine();
                        String region = scanner.nextLine();
                        ConsultaDAO.numeroServidoresDeterminadaRegion(region);
                        break;
                    
                    case 7:
                        ConsultaDAO.numeroServidoresPorRegion();
                        break;
                    case 8:
                        System.out.print("Ingrese el ID del mapa: ");
                        int mapaId = scanner.nextInt();
                        ConsultaDAO.zonasDeMapa(mapaId);
                        break;
                    case 9:
                        return;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
