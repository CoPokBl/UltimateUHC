package me.CoPokBl.ultimateuhc.Scoreboard;

import me.CoPokBl.ultimateuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    private int taskID;
    public String UhcName = "UHC";

    public void start(Player player) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {

            int count = 0;
            final MainBoard board = new MainBoard(player.getUniqueId());

            @Override
            public void run() {
                if (!board.hasID())
                    board.setID(taskID);
                if (count == 2)
                    count = 0;
                switch(count) {
                    case 0:
                        break;
                    case 1:
                        CreateScoreboard(player);
                        break;
                }
                count++;
            }

        }, 0, 10);
    }

    // create scoreboard
    public void CreateScoreboard(Player player) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard main = manager.getNewScoreboard();
        //title
        Objective obj = main.registerNewObjective("mainuhc-1", "dummy", ChatColor.BLUE + "<<< " + UhcName + " >>>");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        // line
        Score line = obj.getScore(ChatColor.BLACK + "=================");
        line.setScore(9);
        // online players
        Score onlineplayer = obj.getScore(ChatColor.YELLOW + "Players Remaining: " + Main.gameManager.AlivePlayers.size());
        onlineplayer.setScore(8);
        // kills
        Score playerkills = obj.getScore(ChatColor.YELLOW + "Kills: " + player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS));
        playerkills.setScore(7);
        // border
        Score border = obj.getScore(ChatColor.YELLOW + "WorldBorder: " + (int) player.getWorld().getWorldBorder().getSize());
        border.setScore(6);
        // pvp
        String pvpValue;
        if (Main.gameManager.PvpEnabled) {
            pvpValue = "Enabled";
        } else {
            pvpValue = "Enabling in " + (Main.gameManager.TimeToPvp - Main.gameManager.gameLoopTimer) + " seconds";
        }
        Score pvpstatus = obj.getScore(ChatColor.YELLOW + "PVP: " + pvpValue);
        pvpstatus.setScore(5);
        //meetup
        String meetupValue;
        if (Main.gameManager.MeetupEnabled) {
            meetupValue = "Enabled";
        } else {
            meetupValue = "Enabling in " + (Main.gameManager.TimeToMeetup - Main.gameManager.gameLoopTimer) + " seconds";
        }
        Score meetupstatus = obj.getScore(ChatColor.YELLOW + "Meetup: " + meetupValue);
        meetupstatus.setScore(4);
        // dead or alive
        if (Main.gameManager.AlivePlayers.contains(player)) {
            Score doa = obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "You Are Alive");
            doa.setScore(3);
        } else {
            Score doa = obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "You Are Dead");
            doa.setScore(2);
        }
        // done
        player.setScoreboard(main);

    }
}
