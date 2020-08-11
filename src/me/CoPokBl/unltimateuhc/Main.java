package me.CoPokBl.unltimateuhc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener{
	// hi adam
	private Boolean pvp;
	private Boolean ingame;
	private Boolean meetupvar;
	public static Boolean skyhigh;
	public static Boolean zombies;
	public static Boolean halfores;
	public static Boolean goldp;
	public static Boolean chicken;
	public static Boolean ender;
	public static Boolean stacked;
	public static Boolean fallout;
	public static Boolean doubledamage;
	public static Boolean onetable;
	private String uhcname;
	private final List<Player> alive = new ArrayList<>();
	private final List<Player> hasmadetable = new ArrayList<>();
	private int taskID;
	private World world;

	@Override
	public void onEnable() {
		// On startup code
		this.getCommand("uhcscenario").setExecutor(new scenarios());
		this.getCommand("uhcscenario").setTabCompleter(new scenariotab());
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.addRecipe(getrhead());
		Bukkit.addRecipe(getrarrows());
		Bukkit.addRecipe(getrstringw());
		Bukkit.addRecipe(getrstringb());
		Bukkit.addRecipe(getrstringbr());
		pvp = false;
		ingame = false;
		meetupvar = false;
		uhcname = "UHC";
		skyhigh = false;
		zombies = false;
		goldp = false;
		halfores = false;
		chicken = false;
		ender = false;
		stacked = false;
		fallout = false;
		doubledamage = false;
		onetable = false;
		ifuhcdelete();
		// has if because i want to add config
		//if (true) {
		//	startloop();
		//}
		WorldCreator wc = new WorldCreator("uhc");
		wc.environment(World.Environment.NORMAL);
		wc.type(WorldType.NORMAL);
		wc.createWorld();
		if (!(Bukkit.getOnlinePlayers().isEmpty())) 
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (online.isOp()) {
					online.sendMessage(ChatColor.RED + "Reloading the server breaks UltimateUHC, restart server now!");
					online.sendMessage(ChatColor.RED + "UltimateUHC is now disabling...");
					getServer().getPluginManager().disablePlugin(this); 
				}
			}
	}
	
	@Override
	public void onDisable() {
		// when plugin stops
		pvp = false;
		ingame = false;
		if (!(Bukkit.getOnlinePlayers().isEmpty())) 
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (online.isOp()) {
					online.sendMessage(ChatColor.RED + "UltimateUHC has been disabled.");
				}
			}
	}
	
	public static double getRandomnum(double min, double max){

	    double x = (Math.random()*((max-min)+1))+min;

	    return x;

	}
	
	public void startloop() {
		while(true) {
			
			BukkitScheduler scheduler = getServer().getScheduler();
	        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	        	@Override
	            public void run() {
	                if (pc())
	                	((BukkitRunnable) scheduler).cancel();
	            }
	        	
	        }, 20L, 20L);
			Bukkit.broadcastMessage(t("&1[&rUHC&1] &aUHC will start in 30 seconds!"));
			BukkitRunnable startuhc = new BukkitRunnable() {
				//pvp
		        @Override
		        public void run() {
		        	Bukkit.broadcastMessage(t("&1[&rUHC&1] &aScattering Players..."));
		        	for(Player all : Bukkit.getServer().getOnlinePlayers())
		        	{
		        	    sendplayer(all);
		        	}
		        	startuhc();
		        }
		    };
		    startuhc.runTaskLater(this, 20 * 30);
		    while(ingame) {
		    	
		    }
		}
	}
	
	public void startuhc() {
		BukkitRunnable acstart = new BukkitRunnable() {
			//pvp
	        @Override
	        public void run() {
	        	Bukkit.broadcastMessage(t("&1[&rUHC&1] &aStarting UHC..."));
	        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "uhcstart");
	        }
	    };
	    acstart.runTaskLater(this, 20 * 5);
	    return;
	}
	
	public String t(String mes) {
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	
	public Boolean pc() {
		if (Bukkit.getOnlinePlayers().size() > 1)
			return true;
		return false;
	}
	
	public void ifuhcdelete() {
		if (Bukkit.getWorld("uhc") == null)
			return;
		unloadWorld(Bukkit.getWorld("uhc"));
		deleteworld("uhc");
		return;
	}	
	public void unloadWorld(World world) {
	    this.world = Bukkit.getWorld("");
	    if(!world.equals(null)) {
	        Bukkit.getServer().unloadWorld(world, true);
	    }
	}
	
	public void deleteworld(String worldname) {
		World delete = Bukkit.getWorld(worldname);
		File deleteFolder = delete.getWorldFolder();
		deleteWorld2(deleteFolder);
		return;
	}
	
	public boolean deleteWorld2(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld2(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	//recipies
	public ShapedRecipe getrhead() {
			
			ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Golden Head");
			item.setItemMeta(meta);
			NamespacedKey key = new NamespacedKey(this, "golden_apple");
			
			ShapedRecipe recipe = new ShapedRecipe(key, item);
			
			recipe.shape("ggg", "ghg", "ggg");
			recipe.setIngredient('g', Material.GOLD_INGOT);
			recipe.setIngredient('h', Material.PLAYER_HEAD);
			
			return recipe;
		}
	public ShapedRecipe getrarrows() {
		ItemStack item = new ItemStack(Material.ARROW);
		NamespacedKey key = new NamespacedKey(this, "arrow");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("g");
		recipe.setIngredient('g', Material.GRAVEL);
		return recipe;
	}
	
	public ShapedRecipe getrstringw() {
		ItemStack item = new ItemStack(Material.STRING);
		NamespacedKey key = new NamespacedKey(this, "stringw");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("ww", "ww");
		recipe.setIngredient('w', Material.WHITE_WOOL);
		return recipe;
	}
	public ShapedRecipe getrstringb() {
		ItemStack item = new ItemStack(Material.STRING);
		NamespacedKey key = new NamespacedKey(this, "stringb");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("ww", "ww");
		recipe.setIngredient('w', Material.BLACK_WOOL);
		return recipe;
	}
	public ShapedRecipe getrstringbr() {
		ItemStack item = new ItemStack(Material.STRING);
		NamespacedKey key = new NamespacedKey(this, "stringbr");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("ww", "ww");
		recipe.setIngredient('w', Material.BROWN_WOOL);
		return recipe;
	}
	
	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
		BukkitRunnable spec = new BukkitRunnable() {
			@Override
	        public void run() {
				if (zombies == true) {
					Player p = e.getPlayer();
			        p.setGameMode(GameMode.ADVENTURE);
				} else {
					Player p = e.getPlayer();
			        p.setGameMode(GameMode.SPECTATOR);	
				}
	        }
		};
		spec.runTaskLater(this, 20 * 2);
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		// Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:overworld run tp " + e.getPlayer().getName() + " 0 100 0");
	}
	
	public void sendplayer(Player p) {
		createscoreboard(p);
		start(p);
		if (ingame == false) {
    		alive.add(p);
        	p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 255)));
        	p.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255)));
        	p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW, 99999, 255)));
        	p.addPotionEffect((new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 255)));
        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run tp " + p.getName() + " " + getRandomnum(-250, 250) + " 200 " + getRandomnum(-250, 250));
    	}
    	else {
    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run tp " + p.getName() + " 0 100 0");
    		p.setGameMode(GameMode.SPECTATOR);
    		p.sendMessage(ChatColor.RED + "You Joined In The Middle Of A Game! You Can't Play.");
    	}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (alive.contains(event.getPlayer()))
			alive.remove(event.getPlayer());
		MainBoard board = new MainBoard(event.getPlayer().getUniqueId());
		if (board.hasID())
			board.stop();
	}
	
	public void start(Player player) {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			int count = 0;
			MainBoard board = new MainBoard(player.getUniqueId());
			
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
					createscoreboard(player);
					break;
				}
				count++;
			}
			
		}, 0, 10);
	}
	
	// create scoreboard
	public void createscoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard main = manager.getNewScoreboard();
		//title
		Objective obj = main.registerNewObjective("mainuhc-1", "dummy", ChatColor.BLUE + "<<< " + uhcname + " >>>");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		// line
		Score line = obj.getScore(ChatColor.BLACK + "=================");
		line.setScore(9);
		// online players
		Score onlineplayer = obj.getScore(ChatColor.YELLOW + "Players Remaining: " + alive.size());
		onlineplayer.setScore(8);
		// kills
		Score playerkills = obj.getScore(ChatColor.YELLOW + "Kills: " + player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS));
		playerkills.setScore(7);
		// border
		Score border = obj.getScore(ChatColor.YELLOW + "WorldBorder: " + (int) player.getWorld().getWorldBorder().getSize());
		border.setScore(6);
		// pvp
		Score pvpstatus = obj.getScore(ChatColor.YELLOW + "PVP: " + pvp);
		pvpstatus.setScore(5);
		//meetup
		Score meetupstatus = obj.getScore(ChatColor.YELLOW + "Meetup: " + meetupvar);
		meetupstatus.setScore(4);
		// dead or alive
		if (alive.contains(player)) {
			Score doa = obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "You Are Alive");
			doa.setScore(3);
		} else {
			Score doa = obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "You Are Dead");
			doa.setScore(2);
		}
		// done
		player.setScoreboard(main);
		
	}
	
	//lightning
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		alive.remove(e.getEntity().getPlayer());
		if (alive.size() == 1 && ingame == true) {
			// run win
			final Player winner = alive.get(0);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"" + winner.getName() + " has won the UHC!!!!\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + winner.getName() + " title {\"color\":\"blue\", \"text\":\"You Have Won The UHC!\"}");
			ingame = false;
		}
		Player p = (Player)e.getEntity().getPlayer();
		p.getWorld().strikeLightningEffect(p.getLocation());
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		World world = p.getWorld();
		world.dropItemNaturally(p.getLocation(), head);
		if (goldp) {
			ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
			ItemStack goldes = new ItemStack(Material.GOLD_INGOT, 5);
			world.dropItemNaturally(p.getLocation(), diamonds);
			world.dropItemNaturally(p.getLocation(), goldes);
		}
		// Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getKiller().getName() + " minecraft:player_head{SkullOwner:\"" + p.getName() + "\"}");
		
	}
	
	@EventHandler
	public void onPlayerEatFood(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		Player p = (Player) event.getPlayer();
		if (event.getItem().getType().equals(Material.GOLDEN_APPLE) && item.getItemMeta().getDisplayName().contains("Golden Head")) {
			// Player ate golden head
			p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1)));
		}
	}
	
	 @EventHandler
	    public void onBlockBreak(BlockBreakEvent event){
		 Player p = event.getPlayer();
		 Location loc = event.getBlock().getLocation();
		 int ran = 2;
		 		if (halfores) {
		 			ran = (int) getRandomnum(0, 2);
		 			if (ran == 1) {
		 				event.setDropItems(false);
		 			}
		 		}
		 		if (goldp) {
		 			if (event.getBlock().getType().equals(Material.DIAMOND_ORE))
		 				event.setDropItems(false);
		 			if (event.getBlock().getType().equals(Material.GOLD_ORE))
		 				event.setDropItems(false);
		 		}
		 		if (stacked) {
		 			World world = p.getWorld();
		 			event.setDropItems(false);
		 			int dia = (int) getRandomnum(1, 2);
		 			int iro = (int) getRandomnum(1, 10);
		 			int gol = (int) getRandomnum(1, 5);
		 			int woo = (int) getRandomnum(1, 20);
		 			int apl = (int) getRandomnum(1, 10);
		 			int lap = (int) getRandomnum(1, 3);
		 			int obs = (int) getRandomnum(1, 2);
		 			ItemStack diam = new ItemStack(Material.DIAMOND, dia);
		 			ItemStack iron = new ItemStack(Material.IRON_INGOT, iro);
		 			ItemStack gold = new ItemStack(Material.GOLD_INGOT, gol);
		 			ItemStack wood = new ItemStack(Material.OAK_LOG, woo);
		 			ItemStack appl = new ItemStack(Material.APPLE, apl);
		 			ItemStack lapi = new ItemStack(Material.LAPIS_LAZULI, lap);
		 			ItemStack obsi = new ItemStack(Material.OBSIDIAN, obs);
		 			world.dropItemNaturally(loc, diam);
		 			world.dropItemNaturally(loc, iron);
		 			world.dropItemNaturally(loc, gold);
		 			world.dropItemNaturally(loc, wood);
		 			world.dropItemNaturally(loc, appl);
		 			world.dropItemNaturally(loc, lapi);
		 			world.dropItemNaturally(loc, obsi);
		 			return;
		 		}
	            Material block = event.getBlock().getType();
	            if (block.equals(Material.OAK_LEAVES) || block.equals(Material.SPRUCE_LEAVES) || block.equals(Material.DARK_OAK_LEAVES) || block.equals(Material.BIRCH_LEAVES) || block.equals(Material.ACACIA_LEAVES) || block.equals(Material.JUNGLE_LEAVES)) {
	            	Player player = event.getPlayer();
	            	World world = player.getWorld();
	            	ItemStack appleitem = new ItemStack(Material.APPLE);
	            	world.dropItemNaturally(event.getBlock().getLocation(), appleitem);
	            	// Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " apple 1");
	            }
	            
	            //auto smelt
	            if (event.getBlock().getType().equals(Material.IRON_ORE) && ran == 2) {
	            	//iron
	            	event.setDropItems(false);
	            	World world = event.getPlayer().getWorld();
	            	ItemStack irondrop = new ItemStack(Material.IRON_INGOT);
	            	world.dropItemNaturally(event.getBlock().getLocation(), irondrop);
	            }
	            if (event.getBlock().getType().equals(Material.GOLD_ORE) && ran == 2 && !goldp) {
	            	//gold
	            	event.setDropItems(false);
	            	World world = event.getPlayer().getWorld();
	            	ItemStack golddrop = new ItemStack(Material.GOLD_INGOT);
	            	world.dropItemNaturally(event.getBlock().getLocation(), golddrop);
	            }
	            
	            
	    }
	
	
	@EventHandler
    public void onTestEntityDamage(EntityDamageByEntityEvent event) {
		if (pvp == false) {
	        if (event.getDamager() instanceof Player){
	            if (event.getEntity() instanceof Player) {
	                event.setCancelled(true);
	            }
	        }
        } else {
        	if (doubledamage) {
        		if (event.getDamager() instanceof Player){
    	            if (event.getEntity() instanceof Player) {
    	            	Player p = (Player) event.getEntity();
    	            	p.damage(event.getDamage());
    	            }
    	        }
        	}
        }
    }
	
	//no enderpearl damage
    @EventHandler
    public void onEntityDamage(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
 
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            if (ender) {
            	ItemStack enderperl = new ItemStack(Material.ENDER_PEARL);
            	event.getPlayer().getInventory().addItem(enderperl);
            }
            player.teleport(event.getTo());
        }
    }
	
    @EventHandler
    public void playercraft(CraftItemEvent e) {
    	if (!onetable)
    		return;
        ItemStack[] item = e.getInventory().getMatrix();
        Player p = (Player) e.getWhoClicked();
        Material itemType = e.getRecipe().getResult().getType();
        
        Material table = Material.CRAFTING_TABLE;
        if (!(hasmadetable.contains(p)) && itemType.equals(table)) {
        	hasmadetable.add(p);
        	p.sendMessage(ChatColor.GREEN + "You Can Only Make One Crafting Table, That Was You Last One!");
        	return;
        }
        if(itemType.equals(table)) {
             e.setCancelled(true);
             p.sendMessage(ChatColor.RED + "You Have Already Made A Crafting Table!");
         }
    }
    
	@EventHandler
    public void onPlayerLogin(final PlayerLoginEvent e) {
            
    }
	
	public void setupPlayer(Player player) {
		if (ender) {
        	ItemStack enderperl = new ItemStack(Material.ENDER_PEARL);
        	player.getInventory().addItem(enderperl);
        }
		if (chicken) {
			player.setHealth(1);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give @a minecraft:enchanted_golden_apple 1");
		}
		ItemStack book = new ItemStack(Material.BOOK);
		player.getInventory().addItem(book);
		return;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("uhcstart")) {
			Player player = (Player) sender;
			if (!(player.hasPermission("uhc.start"))) {
				player.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
				return false;
			}
			for (Player online : Bukkit.getOnlinePlayers()) {
				setupPlayer(online);
			}
			ingame = true;
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run gamerule naturalRegeneration false");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run difficulty peaceful");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder center 0 0");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder set 500");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run effect clear @a");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"blue\", \"text\":\"The UHC Has Begun!\"}");
			BukkitRunnable enablepvp = new BukkitRunnable() {
				//pvp
		        @Override
		        public void run() {
		        	pvp = true;
		        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"red\", \"text\":\"PVP is Now Enabled!\"}");
		        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run worldborder set 20 1800");
		        	
		        }
		    };
		    // Run the task on this plugin in 3 seconds (60 ticks)
		    enablepvp.runTaskLater(this, 20 * 600);
		    
		    BukkitRunnable meetup = new BukkitRunnable() {
		    	//meetup
		    	@Override
		    	public void run() {
		    		meetupvar = true;
		    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle {\"text\":\"Goto 0, 0! You Must Stay Above Ground!\"}");
		    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"It Is Now Meetup!\",\"color\":\"dark_red\"}");
		    		
		    	}
		    };
		    meetup.runTaskLater(this, 20 * 1900);
		    
		    // skyhigh and fallout
		    BukkitScheduler scheduler = getServer().getScheduler();
	        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	        	@Override
	            public void run() {
	        		//skyhigh
	        		if (skyhigh == true) {
	        			for (Player targetp : Bukkit.getOnlinePlayers()) {
	        				if (targetp.getLocation().getY() < 100) {
	        					targetp.damage(1);
	        				}
	        			}
	        		}
	        		//fallout
	        		if (fallout == true) {
	        			for (Player targetp : Bukkit.getOnlinePlayers()) {
	        				if (targetp.getLocation().getY() > 60) {
	        					targetp.damage(1);
	        				}
	        			}
	        		}
	        	}
	        }, 38000L, 40L);
		}
		
		if (label.equalsIgnoreCase("uhcjoin")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			Player p = (Player) sender;
			if (alive.contains(p)) {
				p.sendMessage(ChatColor.RED + "You Are Already In The Game!");
				return true;
			}
			sendplayer(p);
			return true;
		}
		
		if (label.equalsIgnoreCase("uhcleave")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			Player p = (Player) sender;
			if (!alive.contains(p)) {
				p.sendMessage(ChatColor.RED + "You Are Not In The Game!");
				return true;
			}
			alive.remove(p);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + p.getPlayer() + " world");
			return true;
		}
		
		//debug
		if (label.equalsIgnoreCase("uhcdebug")) {
			if (!(sender.hasPermission("uhc.debug"))) 
				return true;
			sender.sendMessage("This command is for CoPokBl only.");
			if (!(sender.getName().equals("CoPokBl")))
				return true;
			if (args[0].equalsIgnoreCase("addalive")) {
				alive.add(Bukkit.getPlayer(args[1]));
			}
		}
		
		if (label.equalsIgnoreCase("uhcname")) {
			if (sender.hasPermission("uhc.name")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Usage: /uhcname [NEW NAME]");
					return true;
				}
				String name = "";
				for (int i = 0;i < args.length;i++) {
				    name += args[i] + " ";
				}
				uhcname = name;
				sender.sendMessage(ChatColor.GREEN + "Set the name of the UHC to " + uhcname);
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
			return true;
		}
		
		// scenarios enable disable command
		
		
		//head command
				if (label.equalsIgnoreCase("givehead")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage("You Don't Have Hands!");
						return true;
					}
					Player player = (Player) sender;
					if (player.hasPermission("uhc.givehead")) {
						if (!(args.length == 1)) {
							sender.sendMessage(ChatColor.RED + "Usage: /givehead <player>");
						}
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + sender.getName() + " minecraft:player_head{SkullOwner:\"" + args[0] + "\"}");
						return true;
					}
					sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
				}
		
		if (label.equalsIgnoreCase("uhcstatus")) {
			sender.sendMessage(ChatColor.GREEN + "Current UHC game status:");
			sender.sendMessage(ChatColor.RED + "In Game: " + ingame);
			sender.sendMessage(ChatColor.AQUA + "Is Meetup: " + meetupvar);
			sender.sendMessage(ChatColor.GOLD + "PVP allowed: " + pvp);
			sender.sendMessage(ChatColor.BLACK + "People Alive: " + alive.size());
			if (!(sender instanceof Player))
				return true;
			Player p = (Player) sender;
			sender.sendMessage(ChatColor.BLUE + "WorldBorder: " + (int)p.getWorld().getWorldBorder().getSize());
			if (alive.contains(p)) {
				sender.sendMessage(ChatColor.GREEN + "You Are Alive!!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Are Dead");
			return true;
		}
		
		if (label.equalsIgnoreCase("uhcend")) {
			if (!(args[1].equals("confirm"))) {
				sender.sendMessage(ChatColor.RED + "This command is not needed because when there is one player left it will do it by itself, if you are sure you want to do this type /uhcend <player> confirm");
				return true;
			}
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (!(sender.hasPermission("uhc.end"))) {
				sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
				return false;
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"" + args[0] + " has won the UHC!!!!\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + target.getName() + " title {\"color\":\"blue\", \"text\":\"You Have Won The UHC!\"}");
			sender.sendMessage("Ended UHC.");
			sender.sendMessage("Thanks For Using UltimateUHC by CoPokBl!");
			return true;
		}
		
		if (label.equalsIgnoreCase("uhcsethealth")) {
            if (!(sender.hasPermission("uhc.sethealth"))) {
                sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
                return true;
            }
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                int healthtoset;
                try {
                    healthtoset = Integer.parseInt(args[1]);
                } catch(Exception e) {
                    sender.sendMessage(ChatColor.RED + "Usage: /uhcsethealth <player> (health)");
                    return true;
                }
                target.setHealth(healthtoset);
                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s health to " + args[1]);
            }
            else {
                sender.sendMessage(ChatColor.RED + "Usage: /uhcsethealth <player> (health)");
            }
        }
		
		if (label.equalsIgnoreCase("uhcpvp")) {
			if (sender.hasPermission("uhc.pvp")) {
    			if (args[0].equals("true")) {
					pvp = true;
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"color\":\"red\", \"text\":\"PVP is Now Enabled!\"}");
					return true;
				}
    			else if (args[0].equals("false")) {
					pvp = false;
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Usage: /uhcpvp (true/false");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("uhcserverend")) {
			Player player = (Player) sender;
			if (!(player.hasPermission("uhc.stopserver"))) {
				player.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
				return false;
			}
			player.sendMessage("Closing Server...");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a {\"color\":\"green\",\"text\":\"The Server Will Shutdown In 1 Minute\"}");
			BukkitRunnable closeserver = new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.shutdown();
				}
			};
			closeserver.runTaskLater(this, 20 * 60);
		}
		
		if (label.equalsIgnoreCase("uhcinfo")) {
			sender.sendMessage(ChatColor.BLUE + "UHC info:");
			sender.sendMessage("You will join and get teleported to a random location with the effects slowness, blindness, mining fatugue and resistence.");
			sender.sendMessage("Until the UHC starts you will be invincible. When it starts all the effects will be removed and a title will show.");
			sender.sendMessage("PVP will be disabled until it tells you it has been enabled. During the time before meetup you have time to mine and get resourses.");
			sender.sendMessage("During meetup you are not allowed to be underground or skybasing. You must head to 0, 0. If you don't you will be kicked from the UHC.");
			sender.sendMessage("Have Fun!! Plugin By CoPokBl.");
			return true;
		}
		
		//scatter
		if (label.equalsIgnoreCase("scatter")) {
			Player target = Bukkit.getPlayer(args[0]);
			if (sender.hasPermission("uhc.scatter")) {
				target.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255)));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:uhc run tp " + args[0] + " " + getRandomnum(-250, 250) + " 200 " + getRandomnum(-250, 250));
				sender.sendMessage(ChatColor.GREEN + "Scattered " + args[0]);
				return true;
			}
			else {
				sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
				return false;
			}
		}
		
		// respawn
		if (label.equalsIgnoreCase("uhcrespawn")) {
			if (sender.hasPermission("uhc.respawn")) {
				try {
					Player target = Bukkit.getPlayer(args[0]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "That player does not exist!");
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				Bukkit.dispatchCommand(sender, "scatter " + target.getName());
				if (alive.contains(target))
					alive.remove(target);
				alive.add(target);
				setupPlayer(target);
				target.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(ChatColor.GREEN + "You Have Respawned " + target.getName() + "!");
				target.sendMessage(ChatColor.GREEN + "You Have Been Respawned!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Don't Have Permission To Do That!");
			return false;
		}
		
		// /uhchealth command
		if (label.equalsIgnoreCase("uhchealth")) {
			if (args.length == 0) {
                sender.sendMessage("Usage: /uhchealth <player>");
                return true;
            }
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			sender.sendMessage(target.getName() + "'s health is: " + target.getHealth());
            return true;
		}
		
		return false;
	}
	
	
	
	
}