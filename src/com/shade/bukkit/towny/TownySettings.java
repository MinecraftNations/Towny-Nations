package com.shade.bukkit.towny;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.shade.util.KeyValueFile;
import com.shade.util.StringMgmt;



public class TownySettings {
	// String[]
	enum StrArr {
		SAVE_DATABASES,
		RESIDENT_COMMANDS,
		TOWN_COMMANDS,
		NATION_COMMANDS,
		PLOT_COMMANDS,
		TOWNY_COMMANDS,
		TOWNY_ADMIN_COMMANDS,
		TOWN_CHAT_COMMANDS,
		NATION_CHAT_COMMANDS
	};
	// String
	enum Str {
		LOAD_DATABASE,
		DEFAULT_TOWN_NAME,
		DEFAULT_MAYOR_PREFIX,
		DEFAULT_MAYOR_POSTFIX,
		DEFAULT_KING_PREFIX,
		DEFAULT_KING_POSTFIX,
		DEFAULT_CAPITAL_PREFIX,
		DEFAULT_CAPITAL_POSTFIX,
		DEFAULT_TOWN_PREFIX,
		DEFAULT_TOWN_POSTFIX,
		DEFAULT_NATION_PREFIX,
		DEFAULT_NATION_POSTFIX,
		UNCLAIMED_ZONE_NAME,
		MSG_REGISTRATION,
		MSG_NEW_TOWN,
		MSG_NEW_NATION,
		MSG_JOIN_TOWN,
		MSG_JOIN_NATION,
		MSG_NEW_MAYOR,
		MSG_NEW_KING,
		MSG_WAR_JOIN,
		MSG_WAR_ELIMINATED,
		MSG_WAR_FORFEITED,
		MSG_WAR_LOSE_BLOCK,
		MSG_NEW_DAY
	};
	// Integer
	enum Int {
		INACTIVE_AFTER_TIME,
		DELETED_AFTER_TIME,
		TOWN_BLOCK_SIZE,
		TOWN_BLOCK_RATIO,
		DEFAULT_MAX_TOWN_BLOCKS,
		PRICE_NEW_TOWN,
		PRICE_NEW_NATION,
		WARTIME_WARNING_DELAY,
		WARTIME_TOWN_BLOCK_HP,
		WARTIME_HOME_BLOCK_HP
	};
	// Boolean
	enum Bool {
		FIRST_RUN,
		FRIENDLY_FIRE,
		TOWN_CREATION_ADMIN_ONLY,
		NATION_CREATION_ADMIN_ONLY,
		UNCLAIMED_ZONE_BUILD,
		UNCLAIMED_ZONE_DESTROY,
		SHOW_TOWN_NOTIFICATIONS,
		USING_ICONOMY,
		MODIFY_CHAT_NAME,
		DELETE_OLD_RESIDENTS,
		DEBUG_MODE
	};
	// Nation Level
	enum NationLevel {
		NAME_PREFIX,
		NAME_POSTFIX,
		CAPITAL_PREFIX,
		CAPITAL_POSTFIX,
		KING_PREFIX,
		KING_POSTFIX
	};
	// Town Level
	enum TownLevel {
		NAME_PREFIX,
		NAME_POSTFIX,
		MAYOR_PREFIX,
		MAYOR_POSTFIX,
		TOWN_BLOCK_LIMIT
	};
	
	
	
	private static final ConcurrentHashMap<TownySettings.StrArr,String[]> configStrArr
		= new ConcurrentHashMap<TownySettings.StrArr,String[]>();
	private static final ConcurrentHashMap<TownySettings.Str,String> configStr
		= new ConcurrentHashMap<TownySettings.Str,String>();
	private static final ConcurrentHashMap<TownySettings.Int,Integer> configInt
		= new ConcurrentHashMap<TownySettings.Int,Integer>();
	private static final ConcurrentHashMap<TownySettings.Bool,Boolean> configBool
		= new ConcurrentHashMap<TownySettings.Bool,Boolean>();
	private static final ConcurrentHashMap<Integer,ConcurrentHashMap<TownySettings.NationLevel,Object>> configNationLevel
		= new ConcurrentHashMap<Integer,ConcurrentHashMap<TownySettings.NationLevel,Object>>();
	private static final ConcurrentHashMap<Integer,ConcurrentHashMap<TownySettings.TownLevel,Object>> configTownLevel
		= new ConcurrentHashMap<Integer,ConcurrentHashMap<TownySettings.TownLevel,Object>>();
	
	static {
		// String[]
		configStrArr.put(TownySettings.StrArr.SAVE_DATABASES, new String[]{"flatfile"});
		configStrArr.put(TownySettings.StrArr.RESIDENT_COMMANDS, new String[]{"/resident","/r","/player"});
		configStrArr.put(TownySettings.StrArr.TOWN_COMMANDS, new String[]{"/town","/t"});
		configStrArr.put(TownySettings.StrArr.NATION_COMMANDS, new String[]{"/nation","/n"});
		configStrArr.put(TownySettings.StrArr.PLOT_COMMANDS, new String[]{"/plot"});
		configStrArr.put(TownySettings.StrArr.TOWNY_COMMANDS, new String[]{"/towny"});
		configStrArr.put(TownySettings.StrArr.TOWNY_ADMIN_COMMANDS, new String[]{"/townyadmin","/ta"});
		configStrArr.put(TownySettings.StrArr.TOWN_CHAT_COMMANDS, new String[]{"/tc"});
		configStrArr.put(TownySettings.StrArr.NATION_CHAT_COMMANDS, new String[]{"/nc"});
		// String
		configStr.put(TownySettings.Str.LOAD_DATABASE, "flatfile");
		configStr.put(TownySettings.Str.DEFAULT_TOWN_NAME, "");
		configStr.put(TownySettings.Str.DEFAULT_MAYOR_PREFIX, "Mayor ");
		configStr.put(TownySettings.Str.DEFAULT_MAYOR_POSTFIX, "");
		configStr.put(TownySettings.Str.DEFAULT_KING_PREFIX, "King ");
		configStr.put(TownySettings.Str.DEFAULT_KING_POSTFIX, "");
		configStr.put(TownySettings.Str.DEFAULT_CAPITAL_PREFIX, "Capital: ");
		configStr.put(TownySettings.Str.DEFAULT_CAPITAL_POSTFIX, " City");
		configStr.put(TownySettings.Str.DEFAULT_TOWN_PREFIX, "");
		configStr.put(TownySettings.Str.DEFAULT_TOWN_POSTFIX, " Town");
		configStr.put(TownySettings.Str.DEFAULT_NATION_PREFIX, "");
		configStr.put(TownySettings.Str.DEFAULT_NATION_POSTFIX, " Nation");
		configStr.put(TownySettings.Str.UNCLAIMED_ZONE_NAME, "Wilderness");
		configStr.put(TownySettings.Str.MSG_REGISTRATION, "&6[Towny] &bWelcome this is your first login.@&6[Towny] &bYou've successfully registered!@&6[Towny] &bTry the /towny command for more help.");
		configStr.put(TownySettings.Str.MSG_NEW_TOWN, "&6[Towny] &b%s created a new town called %s");
		configStr.put(TownySettings.Str.MSG_NEW_NATION, "&6[Towny] &b%s created a new nation called %s");
		configStr.put(TownySettings.Str.MSG_JOIN_TOWN, "&6[Towny] &b%s joined town!");
		configStr.put(TownySettings.Str.MSG_JOIN_NATION, "&6[Towny] &b%s joined the nation!");
		configStr.put(TownySettings.Str.MSG_NEW_MAYOR, "&6[Towny] &b%s is now the mayor!");
		configStr.put(TownySettings.Str.MSG_NEW_KING, "&6[Towny] &b%s is now the king!");
		configStr.put(TownySettings.Str.MSG_WAR_JOIN, "&6[Towny] &b%s joined the fight!");
		configStr.put(TownySettings.Str.MSG_WAR_ELIMINATED, "&6[Towny] &b%s was eliminated from the war.");
		configStr.put(TownySettings.Str.MSG_WAR_FORFEITED, "&6[Towny] &b%s forfeited.");
		configStr.put(TownySettings.Str.MSG_WAR_LOSE_BLOCK, "&6[Towny] &b(%s) belonging to %s has fallen.");
		configStr.put(TownySettings.Str.MSG_NEW_DAY, "&6[Towny] &bA new day is here! Taxes and rent has been collected.");
		// Integer
		configInt.put(TownySettings.Int.INACTIVE_AFTER_TIME, 24 * 60 * 60 * 1000); // 1 Day
		configInt.put(TownySettings.Int.DELETED_AFTER_TIME, 60 * 24 * 60 * 60 * 1000); // Two Months
		configInt.put(TownySettings.Int.TOWN_BLOCK_SIZE, 16);
		configInt.put(TownySettings.Int.TOWN_BLOCK_RATIO, 16);
		configInt.put(TownySettings.Int.DEFAULT_MAX_TOWN_BLOCKS, 64);
		configInt.put(TownySettings.Int.PRICE_NEW_TOWN, 250);
		configInt.put(TownySettings.Int.PRICE_NEW_NATION, 1000);
		configInt.put(TownySettings.Int.WARTIME_WARNING_DELAY, 30); // 30 seconds 
		configInt.put(TownySettings.Int.WARTIME_TOWN_BLOCK_HP, 60); // 1 minute
		configInt.put(TownySettings.Int.WARTIME_HOME_BLOCK_HP, 120); // 2 minutes
		// Boolean
		configBool.put(TownySettings.Bool.FIRST_RUN, true);
		configBool.put(TownySettings.Bool.FRIENDLY_FIRE, false);
		configBool.put(TownySettings.Bool.TOWN_CREATION_ADMIN_ONLY, false);
		configBool.put(TownySettings.Bool.NATION_CREATION_ADMIN_ONLY, false);
		configBool.put(TownySettings.Bool.UNCLAIMED_ZONE_BUILD, false);
		configBool.put(TownySettings.Bool.UNCLAIMED_ZONE_DESTROY, false);
		configBool.put(TownySettings.Bool.SHOW_TOWN_NOTIFICATIONS, true);
		configBool.put(TownySettings.Bool.USING_ICONOMY, true);
		configBool.put(TownySettings.Bool.MODIFY_CHAT_NAME, true);
		configBool.put(TownySettings.Bool.DELETE_OLD_RESIDENTS, false);
		configBool.put(TownySettings.Bool.DEBUG_MODE, true);
		
		newTownLevel(0, "", " Town", "Mayor ", "", 16);
		newNationLevel(0, "", " Nation", "Capital: ", " City", "King ", "");
	}
	
	public static void newTownLevel(int numResidents,
			String namePrefix, String namePostfix,
			String mayorPrefix, String mayorPostfix, 
			int townBlockLimit) {
		ConcurrentHashMap<TownySettings.TownLevel,Object> m = new ConcurrentHashMap<TownySettings.TownLevel,Object>();
		m.put(TownySettings.TownLevel.NAME_PREFIX, namePrefix);
		m.put(TownySettings.TownLevel.NAME_POSTFIX, namePostfix);
		m.put(TownySettings.TownLevel.MAYOR_PREFIX, mayorPrefix);
		m.put(TownySettings.TownLevel.MAYOR_POSTFIX, mayorPostfix);
		m.put(TownySettings.TownLevel.TOWN_BLOCK_LIMIT, townBlockLimit);
		configTownLevel.put(numResidents, m);
	}
	
	public static void newNationLevel(int numResidents, 
			String namePrefix, String namePostfix, 
			String capitalPrefix, String capitalPostfix,
			String kingPrefix, String kingPostfix) {
		ConcurrentHashMap<TownySettings.NationLevel,Object> m = new ConcurrentHashMap<TownySettings.NationLevel,Object>();
		m.put(TownySettings.NationLevel.NAME_PREFIX, namePrefix);
		m.put(TownySettings.NationLevel.NAME_POSTFIX, namePostfix);
		m.put(TownySettings.NationLevel.CAPITAL_PREFIX, capitalPrefix);
		m.put(TownySettings.NationLevel.CAPITAL_POSTFIX, capitalPostfix);
		m.put(TownySettings.NationLevel.KING_PREFIX, kingPrefix);
		m.put(TownySettings.NationLevel.KING_POSTFIX, kingPostfix);
		configNationLevel.put(numResidents, m);
	}
	
	/**
	 * Loads town levels. Level format ignores lines starting with #.
	 * Each line is considered a level. Each level is loaded as such:
	 * 
	 * numResidents:namePrefix:namePostfix:mayorPrefix:mayorPostfix:townBlockLimit
	 * 
	 * townBlockLimit is a required field even if using a calculated ratio.
	 * 
	 * @param filepath
	 * @throws IOException 
	 */
	
	public static void loadTownLevelConfig(String filepath) throws IOException {
		String line;
		String[] tokens;
		BufferedReader fin = new BufferedReader(new FileReader(filepath));
        while ((line = fin.readLine()) != null)
			if (!line.startsWith("#")) { //Ignore comment lines
                tokens = line.split(":");
                if (tokens.length == 6)
					try {
                        int numResidents = Integer.parseInt(tokens[0]);
                        int townBlockLimit = Integer.parseInt(tokens[5]);
                        newTownLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], townBlockLimit);
						if (getDebug())
							System.out.println("[Towny] Added town level: "+numResidents+" "+Arrays.toString(getTownLevel(numResidents).values().toArray()));
                    } catch (Exception e) {
                    	System.out.println("[Towny] Input Error: Town level ignored: " + line);
                    }
            }
        fin.close();
	}
	
	/**
	 * Loads nation levels. Level format ignores lines starting with #.
	 * Each line is considered a level. Each level is loaded as such:
	 * 
	 * numResidents:namePrefix:namePostfix:capitalPrefix:capitalPostfix:kingPrefix:kingPostfix
	 * 
	 * @param filepath
	 * @throws IOException 
	 */
	
	public static void loadNationLevelConfig(String filepath) throws IOException {
		String line;
		String[] tokens;
		BufferedReader fin = new BufferedReader(new FileReader(filepath));
        while ((line = fin.readLine()) != null)
			if (!line.startsWith("#")) { //Ignore comment lines
                tokens = line.split(":");
                if (tokens.length == 7)
					try {
                        int numResidents = Integer.parseInt(tokens[0]);
                        newNationLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
						if (getDebug())
							System.out.println("[Towny] Added town level: "+numResidents+" "+Arrays.toString(getNationLevel(numResidents).values().toArray()));
                    } catch (Exception e) {
                    	System.out.println("[Towny] Input Error: Town level ignored: " + line);
                    }
            }
        fin.close();
	}
	
	public static Map<TownySettings.TownLevel,Object> getTownLevel(int numResidents) {
		return configTownLevel.get(numResidents);
	}
	
	public static Map<TownySettings.NationLevel,Object> getNationLevel(int numResidents) {
		return configNationLevel.get(numResidents);
	}
	
	public static Map<TownySettings.TownLevel,Object> getTownLevel(Town town) {
		return getTownLevel(calcTownLevel(town));
	}
	
	public static Map<TownySettings.NationLevel,Object> getNationLevel(Nation nation) {
		return getNationLevel(calcNationLevel(nation));
	}
	
	//TODO: more efficient way
	public static int calcTownLevel(Town town) {
		int n = town.getNumResidents();
		while (n >= 0) {
            if (configTownLevel.containsKey(n))
                break;
            n--;
        }
        return n;
    }
	
	//TODO: more efficient way
	public static int calcNationLevel(Nation nation) {
		int n = nation.getNumResidents();
		while (n >= 0) {
            if (configNationLevel.containsKey(n))
                break;
            n--;
        }
        return n;
    }
	
	public static HashMap<Object,Object> getMap() {
		HashMap<Object,Object> out = new HashMap<Object,Object>();
		out.putAll(configStrArr);
		out.putAll(configStr);
		out.putAll(configInt);
		out.putAll(configBool);
		return out;
	}
	
	public static void loadConfig(String filepath) throws IOException {
		KeyValueFile kvFile = new KeyValueFile(filepath);
		for (TownySettings.StrArr key : TownySettings.StrArr.values()) {
			String line = kvFile.getString(key.toString().toLowerCase(), StringMgmt.join(getStrArr(key), ","));
			configStrArr.put(key, line.split(","));
		}
		for (TownySettings.Str key : TownySettings.Str.values())
			configStr.put(key, kvFile.getString(key.toString().toLowerCase(), getString(key)));
		for (TownySettings.Int key : TownySettings.Int.values())
			configInt.put(key, kvFile.getInt(key.toString().toLowerCase(), getInt(key)));
		for (TownySettings.Bool key : TownySettings.Bool.values())
			configBool.put(key, kvFile.getBoolean(key.toString().toLowerCase(), getBoolean(key)));
	}
	
	public static Integer getInt(TownySettings.Int key) {
		return configInt.get(key);
	}
	
	public static Boolean getBoolean(TownySettings.Bool key) {
		return configBool.get(key);
	}
	
	public static String getString(TownySettings.Str key) {
		return configStr.get(key);
	}
	
	public static String[] getStrArr(TownySettings.StrArr key) {
		return configStrArr.get(key);
	}
	

	public static String[] parseString(String str) {
		return str.replaceAll("&", "\u00A7").split("@");
	}

	public static String[] getRegistrationMsg() {
		return parseString(getString(TownySettings.Str.MSG_REGISTRATION));
	}

	public static String[] getNewTownMsg(String who, String town) {
		return parseString(String.format(getString(TownySettings.Str.MSG_NEW_TOWN), who, town));
	}

	public static String[] getNewNationMsg(String who, String nation) {
		return parseString(String.format(getString(TownySettings.Str.MSG_NEW_NATION), who, nation));
	}

	public static String[] getJoinTownMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_JOIN_TOWN), who));
	}

	public static String[] getJoinNationMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_JOIN_NATION), who));
	}

	public static String[] getNewMayorMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_NEW_MAYOR), who));
	}

	public static String[] getNewKingMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_NEW_KING), who));
	}
	
	public static List<String> getResidentCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.RESIDENT_COMMANDS));
	}
	
	public static List<String> getTownCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.TOWN_COMMANDS));
	}
	
	public static List<String> getNationCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.NATION_COMMANDS));
	}
	
	public static List<String> getPlotCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.PLOT_COMMANDS));
	}
	
	public static List<String> getTownyCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.TOWNY_COMMANDS));
	}
	
	public static List<String> getTownyAdminCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.TOWNY_ADMIN_COMMANDS));
	}
	
	public static List<String> getTownChatCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.TOWN_CHAT_COMMANDS));
	}
	
	public static List<String> getNationChatCommands() {
		return Arrays.asList(getStrArr(TownySettings.StrArr.NATION_CHAT_COMMANDS));
	}
	
	public static String getFirstCommand(List<String> commands) {
		if (commands.size() > 0)
			return commands.get(0);
		else
			return "/<unknown>";
	}

	public static  int getInactiveAfter() {
		return 24 * 60 * 60 * 1000;
	}

	public static String getKingPrefix(Resident resident) {
		try {
			return (String)getNationLevel(resident.getTown().getNation()).get(TownySettings.NationLevel.KING_PREFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_KING_PREFIX);
		}
	}

	public static String getMayorPrefix(Resident resident) {
		try {
			return (String)getTownLevel(resident.getTown()).get(TownySettings.TownLevel.MAYOR_PREFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_MAYOR_PREFIX);
		}
	}

	public static String getCapitalPostfix(Town town) {
		try {
			return (String)getNationLevel(town.getNation()).get(TownySettings.NationLevel.CAPITAL_POSTFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_CAPITAL_POSTFIX);
		}
	}

	public static String getTownPostfix(Town town) {
		try {
			return (String)getTownLevel(town).get(TownySettings.TownLevel.NAME_PREFIX);
		} catch (Exception e) {
			// Should not reach here
			return getString(TownySettings.Str.DEFAULT_TOWN_POSTFIX);
		}
	}

	public static String getLoadDatabase() {
		return getString(TownySettings.Str.LOAD_DATABASE);
	}

	public static String[] getSaveDatabase() {
		return getStrArr(TownySettings.StrArr.SAVE_DATABASES);
	}

	public static boolean isFirstRun() {
		return getBoolean(TownySettings.Bool.FIRST_RUN);
	}

	public static String getNationPostfix() {
		return getString(TownySettings.Str.DEFAULT_NATION_POSTFIX);
	}

	public static int getMaxTownBlocks(Town town) {
		int ratio = getInt(TownySettings.Int.TOWN_BLOCK_RATIO);
		if (ratio == 0)
			return town.getBonusBlocks() + (Integer)getTownLevel(town).get(TownySettings.TownLevel.TOWN_BLOCK_LIMIT);
		else
			return town.getNumResidents()*ratio;
	}

	public static int getTownBlockSize() {
		return getInt(TownySettings.Int.TOWN_BLOCK_SIZE);
	}

	public static boolean getFriendlyFire() {
		return getBoolean(TownySettings.Bool.FRIENDLY_FIRE);
	}

	public static boolean isTownCreationAdminOnly() {
		return getBoolean(TownySettings.Bool.TOWN_CREATION_ADMIN_ONLY);
	}
	
	public boolean isNationCreationAdminOnly() {
		return getBoolean(TownySettings.Bool.NATION_CREATION_ADMIN_ONLY);
	}

	public static boolean isUsingIConomy() {
		return getBoolean(TownySettings.Bool.USING_ICONOMY);
	}

	public static int getNewTownPrice() {
		return getInt(TownySettings.Int.PRICE_NEW_TOWN);
	}

	public static boolean getUnclaimedZoneBuildRights() {
		return getBoolean(TownySettings.Bool.UNCLAIMED_ZONE_BUILD);
	}
	
	public static boolean getUnclaimedZoneDestroyRights() {
		return getBoolean(TownySettings.Bool.UNCLAIMED_ZONE_DESTROY);
	}

	public static boolean getDebug() {
		return getBoolean(TownySettings.Bool.DEBUG_MODE);
	}

	public static boolean getShowTownNotifications() {
		return getBoolean(TownySettings.Bool.SHOW_TOWN_NOTIFICATIONS);
	}

	public static String getUnclaimedZoneName() {
		return getString(TownySettings.Str.UNCLAIMED_ZONE_NAME);
	}

	public static boolean isUsingChatPrefix() {
		return getBoolean(TownySettings.Bool.MODIFY_CHAT_NAME);
	}

	public static int getMaxInactivePeriod() {
		return getInt(TownySettings.Int.DELETED_AFTER_TIME);
	}

	public static boolean isDeletingOldResidents() {
		return getBoolean(TownySettings.Bool.DELETE_OLD_RESIDENTS);
	}

	public static int getWarTimeWarningDelay() {
		return getInt(TownySettings.Int.WARTIME_WARNING_DELAY);
	}

	public static int getWarzoneTownBlockHealth() {
		return getInt(TownySettings.Int.WARTIME_TOWN_BLOCK_HP);
	}
	
	public static int getWarzoneHomeBlockHealth() {
		return getInt(TownySettings.Int.WARTIME_HOME_BLOCK_HP);
	}

	public static String[] getJoinWarMsg(TownyObject obj) {
		return parseString(String.format(getString(TownySettings.Str.MSG_WAR_JOIN), obj.getName()));
	}

	public static String[] getWarTimeEliminatedMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_WAR_ELIMINATED), who));
	}
	
	public static String[] getWarTimeForfeitMsg(String who) {
		return parseString(String.format(getString(TownySettings.Str.MSG_WAR_FORFEITED), who));
	}

	public static String[] getWarTimeLoseTownBlockMsg(WorldCoord worldCoord) {
		return getWarTimeLoseTownBlockMsg(worldCoord, "");
	}
	
	public static String[] getWarTimeLoseTownBlockMsg(WorldCoord worldCoord, String town) {
		return parseString(String.format(getString(TownySettings.Str.MSG_WAR_LOSE_BLOCK), worldCoord.toString(), town));
	}
	
	public static String[] getNewDayMsg() {
		return parseString(getString(TownySettings.Str.MSG_NEW_DAY));
	}

	public static String getDefaultTownName() {
		return getString(TownySettings.Str.DEFAULT_TOWN_NAME);
	}

	public static String getNationPrefix() {
		return getString(TownySettings.Str.DEFAULT_NATION_PREFIX);
	}
	
	public static String getTownPrefix(Town town) {
		try {
			return (String)getTownLevel(town).get(TownySettings.TownLevel.NAME_PREFIX);
		} catch (Exception e) {
			// Should not reach here
			return getString(TownySettings.Str.DEFAULT_TOWN_PREFIX);
		}
	}
	
	public static String getCapitalPrefix(Town town) {
		try {
			return (String)getNationLevel(town.getNation()).get(TownySettings.NationLevel.CAPITAL_PREFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_CAPITAL_PREFIX);
		}
	}
	
	public static String getKingPostfix(Resident resident) {
		try {
			return (String)getNationLevel(resident.getTown().getNation()).get(TownySettings.NationLevel.KING_POSTFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_KING_POSTFIX);
		}
	}
	
	public static String getMayorPostfix(Resident resident) {
		try {
			return (String)getTownLevel(resident.getTown()).get(TownySettings.TownLevel.MAYOR_POSTFIX);
		} catch (NotRegisteredException e) {
			return getString(TownySettings.Str.DEFAULT_MAYOR_POSTFIX);
		}
	}
	
	
	
	//TODO: better way to set values besides passing the filepath as a param
	
	public static void setBoolean(String filepath, TownySettings.Bool key, Boolean value) {
		KeyValueFile kvFile = new KeyValueFile(filepath);
		configBool.put(key, value);
		kvFile.setBoolean(key.toString().toLowerCase(), getBoolean(key));
	}
	
	/************************************************************/
	
	//TODO:
	
	public static int getWarPointsForTownBlock() {
		return 1;
	}
	
	public static int getWarPointsForTown() {
		return 10;
	}
	
	public static int getWarPointsForNation() {
		return 100;
	}

	public static String[] getWarTimeScoreMsg(Town town, int n) {
		return parseString(String.format("[War] %s scored %d points!", town.getName(), n));
	}
	
	public static int getMinWarHeight() {
		return 63;
	}
}
