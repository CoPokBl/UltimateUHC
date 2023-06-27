package me.CoPokBl.ultimateuhc.Scoreboard;

import me.CoPokBl.ultimateuhc.Main;
import me.CoPokBl.ultimateuhc.Utils;
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
        Objective obj = main.registerNewObjective(ChatColor.BLUE + "<<< " + UhcName + " >>>", "mainuhc");
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
        // elapsed
        Score meetupStatus = obj.getScore(ChatColor.YELLOW + "Time Elapsed: " + Utils.GetTime(Main.gameManager.GameLoopTimer));
        meetupStatus.setScore(4);
        // next event
        String nextEventText;
        if (Main.gameManager.Events.length == 0) {
            nextEventText = "No More Events";
        } else {
            if (Main.gameManager.NextEvent == null) {
                nextEventText = "No More Events";
            } else {
                nextEventText = Main.gameManager.NextEvent.name + " in " + Utils.GetTime(Main.gameManager.NextEvent.time - Main.gameManager.GameLoopTimer) + " seconds";
            }
        }
        Score pvpStatus = obj.getScore(ChatColor.YELLOW + nextEventText);
        pvpStatus.setScore(5);
        // dead or alive
        if (Utils.IsPlayerAlive(player)) {
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
