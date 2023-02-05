package practise.healthrecove;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class expchangeevent implements Listener {

    @EventHandler
    public void onPlayer(PlayerExpChangeEvent e) {
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
        Player player = e.getPlayer();
        int totalExperience = player.getTotalExperience();
        String ID = String.valueOf(player);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        System.out.println(totalExperience);
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt2 = con.createStatement();
        ) {
            String sqldata = "UPDATE playerexp " +
                    "SET exp = '"+totalExperience+"' WHERE name in ('"+q3+"')";
            stmt2.executeUpdate(sqldata);
        } catch (SQLException ed) {
            ed.printStackTrace();
        }
    }
}
