import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class GeneradorBD {

    private static final String[] REGIONES = {"Norte", "Centro", "Sur"};
    private static final String[] NOMBRES_SERVIDORES = {"Avalanche", "Thunder", "Eclipse", "Vortex", "Pinnacle", "Phoenix", "Spectre", "Galaxy", "Apex", "Blizzard"};
    private static final String[] NOMBRES_USUARIOS = {"Player1", "Gamer123", "ProUser", "NoobMaster", "QuestSeeker", "EpicPlayer", "DigitalExplorer", "TheChosenOne", "ShadowWalker", "TechWizard", "GameHero", "CosmicExplorer", "MysticJourney", "PixelPioneer", "QuestMaster", "VirtualVoyager", "CodeCrusher", "EternalGamer", "StarStrider", "CyberPilot", "TimeTraveler", "CrypticRaider", "SkywardSorcerer", "LunarLuminary", "NeonNavigator", "QuantumQuester", "InfinitesimalInquirer", "CosmicCrafter", "GalacticGamer", "SolarSpectator", "PixelPioneer", "TechTrekker", "DigiDungeonMaster", "VirtualVagabond", "CodeConqueror", "EpicEnigma", "QuestQuasar", "MysticMarauder", "BinaryBard", "PuzzlePioneer", "ZeroGravityGamer", "DarkDimensionDaredevil", "RetroRover", "FutureFugitive", "CyberChrononaut", "AnalogAdventurer", "NewUser123"};
    private static final String[] NOMBRES_PERSONAJES = {"Warrior", "Wizard", "Rogue", "Archer", "Sorcerer", "Knight", "Paladin", "Thief", "Assassin", "Hunter", "Mage", "Cleric", "Bard", "Barbarian", "Druid", "Monk", "Necromancer", "Warlock", "Priest", "Ranger", "Samurai", "Ninja", "Alchemist", "Swordsman", "Archeress", "Elementalist", "Sniper", "Berserker", "Enchanter", "Illusionist", "Shaman", "Mystic", "Spellblade", "Witch", "Summoner", "Bard", "Gladiator", "Marksman", "Pirate", "Inquisitor", "BountyHunter", "Crusader", "ShadowDancer", "Swashbuckler", "Archmage", "Sellsword", "Ranger", "Warlock", "Centurion", "Enigma", "Phantom", "Marauder", "Acolyte", "Conqueror", "Shadowblade", "Sorceress", "SerpentMaster", "PlagueDoctor", "Highlander", "Oracle", "Wanderer", "Battlemage", "Chronomancer", "Brawler", "Sentinel", "Jester", "Artificer", "Pyromancer", "Astronomer", "Dreadnought", "Geomancer", "Windwalker", "Duelist", "FrostMage", "SteamPilot", "Thunderlord", "Ironclad", "MoonlitMarauder", "ThunderingTitan", "RadiantRogue", "CelestialSorcerer", "SilentShadow", "FrostyFencer", "TidalTemplar", "CrimsonCrusader", "EtherealEnchantress", "LuminousLycan", "BlazingBard", "HarmonicHuntress", "VorpalVagabond", "EclipsedExecutioner", "SpectralSummoner", "RiftRanger", "ZephyrZodiac", "TimelessTinkerer"};
    private static final String[] NOMBRES_MAPAS = {"PradosMísticos", "PicoDelDragón", "ReinoSombrío", "CataratasHeladas", "EcoEterno", "SantuarioVigilante", "FábulasOlvidadas", "CiudadelaCelestial", "BosquesSusurrantes", "OrillaPlateada", "PicoFénix", "CimaVelada", "CañónCarmesí", "IslaFuerte", "LaberintoLunar", "EdénÍgneo", "AgujaZafiro", "ValleVívido", "AvanzadaObsidiana", "IslaMarfil"};
    private static final String[] NOMBRES_ZONAS = {"ClaroDelBosque", "CuevaOscura", "ArboledaMística", "TundraHelada", "RuinasEncantadas", "CráterVolcánico", "OasisEscondido", "PantanoEmbrujado", "CavernaCristalina", "OasisDesértico", "TierrasAltasCelestiales", "LaberintoSubterráneo", "TemploAntiguo", "PicoTronante", "FosoÍgneo", "ProfundidadesOceánicas", "CiénagaBrumosa", "LlanurasEternas", "CamposDorados", "AcantiladosPlateados"};

    public static void generarDatos() {
        try (Connection conexion = Conexion.getInstancia()) {
            insertarServidores(conexion, 10);
            insertarUsuarios(conexion, 50);
            insertarPersonajes(conexion, 100);
            insertarMapas(conexion, 20);
            insertarZonas(conexion, 20);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarServidores(Connection conexion, int cantidadServidores) throws SQLException {
        String insertServidorQuery = "INSERT INTO Servidores (nombre, region) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(insertServidorQuery)) {
            Random random = new Random();
            for (int i = 1; i <= cantidadServidores; i++) {
                String nombreServidor = NOMBRES_SERVIDORES[random.nextInt(NOMBRES_SERVIDORES.length)];
                statement.setString(1, nombreServidor);
                statement.setString(2, REGIONES[i % REGIONES.length]);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static void insertarUsuarios(Connection conexion, int cantidadUsuarios) throws SQLException {
        String insertUsuarioQuery = "INSERT INTO Usuarios (nombre, codigo, servidor_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(insertUsuarioQuery)) {
            Random random = new Random();
            for (int i = 1; i <= cantidadUsuarios; i++) {
                String nombreUsuario = NOMBRES_USUARIOS[random.nextInt(NOMBRES_USUARIOS.length)];
                statement.setString(1, nombreUsuario);
                statement.setString(2, generarCodigoUsuario());
                statement.setInt(3, i % 10 + 1);  
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static String generarCodigoUsuario() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);
            codigo.append(randomDigit);
        }
        return codigo.toString();
    }

   
    private static void insertarPersonajes(Connection conexion, int cantidadPersonajes) throws SQLException {
        String insertPersonajeQuery = "INSERT INTO Personajes (nombre, usuario_id) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(insertPersonajeQuery)) {
            Random random = new Random();
            for (int i = 1; i <= cantidadPersonajes; i++) {
                String nombrePersonaje = NOMBRES_PERSONAJES[random.nextInt(NOMBRES_PERSONAJES.length)];
                statement.setString(1, nombrePersonaje);
                statement.setInt(2, i % 50 + 1);  
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
    

    private static void insertarMapas(Connection conexion, int cantidadMapas) throws SQLException {
        String insertMapaQuery = "INSERT INTO Mapas (nombre, dificultad, servidor_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(insertMapaQuery)) {
            Random random = new Random();
            for (int i = 1; i <= cantidadMapas; i++) {
                String nombreMapa = NOMBRES_MAPAS[random.nextInt(NOMBRES_MAPAS.length)];
                statement.setString(1, nombreMapa);
                statement.setInt(2, i % 10); 
                statement.setInt(3, i % 10 + 1);  
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static void insertarZonas(Connection conexion, int cantidadZonas) throws SQLException {
        String insertZonaQuery = "INSERT INTO Zonas (nombre, ancho, alto, mapa_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(insertZonaQuery)) {
            Random random = new Random();
            for (int i = 1; i <= cantidadZonas; i++) {
                String nombreZona = NOMBRES_ZONAS[random.nextInt(NOMBRES_ZONAS.length)];
                statement.setString(1, nombreZona);
                statement.setInt(2, random.nextInt(100) + 1); 
                statement.setInt(3, random.nextInt(100) + 1); 
                statement.setInt(4, i % 20 + 1);  
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

}
