package practise.healthrecove;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class playerjoinevent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        System.out.println("started");
        String ip = Healthrecove.getMain().getConfig().getString("SQL.ip");
        String table = Healthrecove.getMain().getConfig().getString("SQL.table");
        String individual = Healthrecove.getMain().getConfig().getString("SQL.individual SQL base");
        String user =Healthrecove.getMain().getConfig().getString("SQL.user");
        String password = Healthrecove.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://"+ ip + "/" +table;
        String DB_IND = "jdbc:mysql://"+ ip + "/" +individual;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        Connection conn = null;
        Player player = event.getPlayer();
        int totalExperience = player.getTotalExperience();
        String ID = String.valueOf(player);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        if(!player.hasPlayedBefore()){
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt1 = con.createStatement();
             Statement stmt2 = con.createStatement();
             ResultSet idMax = stmt1.executeQuery("SELECT MAX(id) FROM playerexp");
        ) {
            idMax.next();
            int intmaxid = idMax.getInt(1);
            intmaxid += 1;
            String exp = "INSERT INTO playerexp (id, name, exp) VALUES (" + intmaxid + ", '" + q3 + "', '" + totalExperience + "')";
            stmt2.executeUpdate(exp);
        } catch (SQLException ed) {
            ed.printStackTrace();
        }
}}
}
