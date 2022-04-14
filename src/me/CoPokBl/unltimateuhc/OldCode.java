package me.CoPokBl.unltimateuhc;

public class OldCode {

//    switch (args[0].toLowerCase()) {
//				case "skyhigh" -> {
//					if (args[1].equals("on")) {
//						SkyHigh skyHigh = new SkyHigh();
//						skyHigh.Enable();
//						gameManager.Scenarios.add(skyHigh);
//						sender.sendMessage(ChatColor.GREEN + "Skyhigh Has Been Enabled!");
//					} else {
//						// get the index of the SkyHigh Scenario
//						int index = gameManager.Scenarios.indexOf(new SkyHigh());
//						gameManager.Scenarios.get(index).Disable();
//						gameManager.Scenarios.remove(index);
//						sender.sendMessage(ChatColor.GREEN + "Skyhigh Has Been Disabled!");
//					}
//					return true;
//				}
//				case "zombies" -> {
//					if (args[1].equals("on")) {
//						Main.zombies = true;
//						sender.sendMessage(ChatColor.GREEN + "Zombies Has Been Enabled!");
//					} else {
//						Main.zombies = false;
//						sender.sendMessage(ChatColor.GREEN + "Zombies Has Been Disabled!");
//					}
//					return true;
//				}
//				case "halfores" -> {
//					if (args[1].equals("on")) {
//						Main.halfOres = true;
//						sender.sendMessage(ChatColor.GREEN + "HalfOres Has Been Enabled!");
//					} else {
//						Main.halfOres = false;
//						sender.sendMessage(ChatColor.GREEN + "HalfOres Has Been Disabled!");
//					}
//					return true;
//				}
//				case "goldenplayers" -> {
//					if (args[1].equals("on")) {
//						Main.goldP = true;
//						sender.sendMessage(ChatColor.GREEN + "Golden PLayers Has Been Enabled!");
//					} else {
//						Main.goldP = false;
//						sender.sendMessage(ChatColor.GREEN + "Golden Players Has Been Disabled!");
//					}
//					return true;
//				}
//				case "chicken" -> {
//					if (args[1].equals("on")) {
//						Main.chicken = true;
//						sender.sendMessage(ChatColor.GREEN + "Chicken Has Been Enabled!");
//					} else {
//						Main.chicken = false;
//						sender.sendMessage(ChatColor.GREEN + "Chicken Has Been Disabled!");
//					}
//					return true;
//				}
//				case "enderman" -> {
//					if (args[1].equals("on")) {
//						Main.ender = true;
//						sender.sendMessage(ChatColor.GREEN + "Endermen Has Been Enabled!");
//					} else {
//						Main.ender = false;
//						sender.sendMessage(ChatColor.GREEN + "Endermen Has Been Disabled!");
//					}
//					return true;
//				}
//				case "stacked" -> {
//					if (args[1].equals("on")) {
//						Main.stacked = true;
//						sender.sendMessage(ChatColor.GREEN + "Stacked Has Been Enabled!");
//					} else {
//						Main.stacked = false;
//						sender.sendMessage(ChatColor.GREEN + "Stacked Has Been Disabled!");
//					}
//					return true;
//				}
//				case "fallout" -> {
//					if (args[1].equals("on")) {
//						Main.fallout = true;
//						sender.sendMessage(ChatColor.GREEN + "Fallout Has Been Enabled!");
//					} else {
//						Main.fallout = false;
//						sender.sendMessage(ChatColor.GREEN + "Fallout Has Been Disabled!");
//					}
//					return true;
//				}
//				case "doubledamage" -> {
//					if (args[1].equals("on")) {
//						Main.doubleDamage = true;
//						sender.sendMessage(ChatColor.GREEN + "DoubleDamage Has Been Enabled!");
//					} else {
//						Main.doubleDamage = false;
//						sender.sendMessage(ChatColor.GREEN + "DoubleDamage Has Been Disabled!");
//					}
//					return true;
//				}
//				case "onetable" -> {
//					if (args[1].equals("on")) {
//						Main.oneTable = true;
//						sender.sendMessage(ChatColor.GREEN + "OneTable Has Been Enabled!");
//					} else {
//						Main.oneTable = false;
//						sender.sendMessage(ChatColor.GREEN + "OneTable Has Been Disabled!");
//					}
//					return true;
//				}
//			}

    //	public void startloop() {
//		while (true) {
//
//			BukkitScheduler scheduler = getServer().getScheduler();
//	        scheduler.scheduleSyncRepeatingTask(this, () -> {
//				if (pc())
//					((BukkitRunnable) scheduler).cancel();
//			}, 20L, 20L);
//			Bukkit.broadcastMessage(t("&1[&rUHC&1] &aUHC will start in 30 seconds!"));
//			BukkitRunnable startuhc = new BukkitRunnable() {
//				//pvp
//		        @Override
//		        public void run() {
//		        	Bukkit.broadcastMessage(t("&1[&rUHC&1] &aScattering Players..."));
//		        	for (Player all : Bukkit.getServer().getOnlinePlayers()) {
//		        	    sendPlayer(all);
//		        	}
//		        	startuhc();
//		        }
//		    };
//		    startuhc.runTaskLater(this, 20 * 30);
//		    while (inGame) {
//
//		    }
//		}
//	}

//	public void startuhc() {
//		BukkitRunnable acstart = new BukkitRunnable() {
//			//pvp
//	        @Override
//	        public void run() {
//	        	Bukkit.broadcastMessage(t("&1[&rUHC&1] &aStarting UHC..."));
//	        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "uhcstart");
//	        }
//	    };
//	    acstart.runTaskLater(this, 20 * 5);
//	    return;
//	}


}
