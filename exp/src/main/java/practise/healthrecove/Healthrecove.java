package practise.healthrecove;

import jdk.javadoc.internal.tool.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public final class Healthrecove extends JavaPlugin implements Listener {

    private static Healthrecove main;
    @Override
    public void onEnable() {
        main = this;
        main.saveDefaultConfig();
        System.out.println("started");
        String ip = main.getConfig().getString("SQL.ip");
        String table = main.getConfig().getString("SQL.table");
        String individual = main.getConfig().getString("SQL.individual SQL base");
        String user = main.getConfig().getString("SQL.user");
        String password = main.getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://"+ ip + "/" +table;
        String DB_IND = "jdbc:mysql://"+ ip + "/" +individual;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        Connection conn = null;
        Bukkit.getServer().getPluginManager().registerEvents(new expchangeevent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new playerjoinevent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new respawn(), this);
        try{
            //Register the JDBC driver
            Class.forName(DB_DRIVER);
            //Open the connection
            conn = DriverManager.
                    getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if(conn != null){
                System.out.println("Successfully connected.");
            }else{
                System.out.println("Failed to connect.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try(Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connn.createStatement();
            Statement stmt2 = connn.createStatement();
        ) {
            String exp = "CREATE TABLE playerexp " +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " exp INTEGER, " +
                    " PRIMARY KEY ( id ))";
            String exp2 = "INSERT INTO playerexp VALUES (0, 0, 0)";
            System.out.println("Inserted records into the table...");
            stmt.executeUpdate(exp);
            stmt2.executeUpdate(exp2);
            System.out.println("Created table in given database...");
            System.out.println("Inserting records into the table...");
            System.out.println("Inserted records into the table...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








    @Override
    public void onDisable() {
    }
    public static Healthrecove getPlugin() {
        return new Healthrecove();
    }
    public static Healthrecove getMain(){
        return main;
    }

}


