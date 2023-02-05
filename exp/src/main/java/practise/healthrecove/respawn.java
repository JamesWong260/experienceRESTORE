package practise.healthrecove;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class respawn implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent q){
        Player p = q.getPlayer();
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
        String ID = String.valueOf(p);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt1 = con.createStatement();
             ResultSet exp = stmt1.executeQuery("SELECT * FROM playerexp WHERE name in ('"+q3+"')");
        ) {
            exp.next();
            int intexp = exp.getInt("exp");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Healthrecove.getMain(), new Runnable() {
                public void run() {
                    float progress = Float.parseFloat(String.valueOf(intexp));
                    p.giveExp((int) progress);
                }}, 3L);

        } catch (SQLException ed) {
            ed.printStackTrace();
        }
}
}
