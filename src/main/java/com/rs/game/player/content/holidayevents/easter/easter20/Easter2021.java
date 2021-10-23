package com.rs.game.player.content.holidayevents.easter.easter20;

import com.rs.game.object.GameObject;
import com.rs.game.player.Player;
import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.lib.game.Animation;
import com.rs.lib.game.Item;
import com.rs.lib.game.SpotAnim;
import com.rs.lib.game.WorldTile;
import com.rs.lib.util.Utils;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.annotations.ServerStartupEvent;
import com.rs.plugin.events.ItemClickEvent;
import com.rs.plugin.events.ItemEquipEvent;
import com.rs.plugin.events.ItemOnObjectEvent;
import com.rs.plugin.events.LoginEvent;
import com.rs.plugin.events.ObjectClickEvent;
import com.rs.plugin.handlers.ItemClickHandler;
import com.rs.plugin.handlers.ItemEquipHandler;
import com.rs.plugin.handlers.ItemOnObjectHandler;
import com.rs.plugin.handlers.LoginHandler;
import com.rs.plugin.handlers.ObjectClickHandler;
import com.rs.utils.spawns.NPCSpawn;
import com.rs.utils.spawns.NPCSpawns;
import com.rs.utils.spawns.ObjectSpawn;
import com.rs.utils.spawns.ObjectSpawns;

@PluginEventHandler
public class Easter2021 {
	
	public static String STAGE_KEY = "easter2022";
	
	public static final boolean ENABLED = true;
	
	@ServerStartupEvent
	public static void loadSpawns() {
		if (!ENABLED)
			return;
		ObjectSpawns.add(new ObjectSpawn(23117, 10, 0, new WorldTile(3210, 3424, 0), "Rabbit hole"));
		NPCSpawns.add(new NPCSpawn(9687, new WorldTile(3212, 3425, 0), "Easter Bunny"));
		NPCSpawns.add(new NPCSpawn(9687, new WorldTile(2463, 5355, 0), "Easter Bunny"));
		NPCSpawns.add(new NPCSpawn(7411, new WorldTile(2448, 5357, 0), "Easter Bunny Jr").setCustomName("Easter Bunny Jr (Trent with Easter 2021 Event)"));
		NPCSpawns.add(new NPCSpawn(9686, new WorldTile(2969, 3431, 0), "Charlie the Squirrel"));
		NPCSpawns.add(new NPCSpawn(3283, new WorldTile(2968, 3429, 0), "Squirrel"));
		NPCSpawns.add(new NPCSpawn(3284, new WorldTile(2970, 3429, 0), "Squirrel"));
		NPCSpawns.add(new NPCSpawn(3285, new WorldTile(2969, 3428, 0), "Squirrel"));
		NPCSpawns.add(new NPCSpawn(3285, new WorldTile(2968, 3432, 0), "Squirrel"));
	}
	
	public static ObjectClickHandler handleEnterExit = new ObjectClickHandler(new Object[] { 23117, 30074 }) {
		@Override
		public void handle(ObjectClickEvent e) {
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) <= 0) {
				e.getPlayer().sendMessage("You don't see a need to go down there yet!");
				return;
			}
			e.getPlayer().useLadder(e.getObjectId() == 23117 ? new WorldTile(2483, 5258, 0) : new WorldTile(3212, 3425, 0));
		}
	};

	public static ObjectClickHandler handleRabbitTunnel = new ObjectClickHandler(new Object[] { 30075, 30076 }) {
		@Override
		public void handle(ObjectClickEvent e) {
			useBunnyHole(e.getPlayer(), e.getObject(), e.getPlayer().transform(0, e.getObjectId() == 30075 ? 7 : -7));
		}
	};
	
	public static ItemClickHandler handleChocCapeEmote = new ItemClickHandler(new Object[] { 12645 }, new String[] { "Emote" }) {
		@Override
		public void handle(ItemClickEvent e) {
			e.getPlayer().setNextAnimation(new Animation(8903));
			e.getPlayer().setNextSpotAnim(new SpotAnim(1566));
		}
	};
	
	public static ItemEquipHandler handleEggBasket = new ItemEquipHandler(4565) {
		@Override
		public void handle(ItemEquipEvent e) {
			if (e.dequip()) {
				e.getPlayer().getAppearance().setBAS(-1);
			} else {
				e.getPlayer().getAppearance().setBAS(594);
			}
		}
	};
	
	public static final int COG = 14719;
	public static final int PISTON = 14720;
	public static final int CHIMNEY = 14718;
	
	private static final WorldTile[] COG_LOCATIONS = {
			new WorldTile(2469, 5328, 0),
			new WorldTile(2469, 5321, 0),
			new WorldTile(2454, 5334, 0),
			new WorldTile(2448, 5341, 0)
	};
	
	private static final WorldTile[] PISTON_LOCATIONS = {
			new WorldTile(2468, 5324, 0),
			new WorldTile(2467, 5319, 0),
			new WorldTile(2454, 5335, 0)
	};
	
	private static final WorldTile[] CHIMNEY_LOCATIONS = {
			new WorldTile(2469, 5323, 0),
			new WorldTile(2444, 5329, 0),
			new WorldTile(2449, 5343, 0)
	};
	
	public static LoginHandler loginEaster = new LoginHandler() {
		@Override
		public void handle(LoginEvent e) {
			if (!ENABLED)
				return;
			e.getPlayer().setNSI("easterBirdFood", Utils.random(4));
			e.getPlayer().setNSI("cogLocation", Utils.random(COG_LOCATIONS.length));
			e.getPlayer().setNSI("pistonLocation", Utils.random(PISTON_LOCATIONS.length));
			e.getPlayer().setNSI("chimneyLocation", Utils.random(CHIMNEY_LOCATIONS.length));
			e.getPlayer().getVars().setVarBit(6014, e.getPlayer().getI(Easter2021.STAGE_KEY) >= 3 ? 1 : 0);
			e.getPlayer().getVars().setVarBit(6016, e.getPlayer().getI(Easter2021.STAGE_KEY) >= 6 ? 3: 0);
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) >= 8)
				e.getPlayer().getVars().setVarBit(6014, 85);
		}
	};
	
	public static ObjectClickHandler handleWaterGrab = new ObjectClickHandler(new Object[] { 30083 }) {
		@Override
		public void handle(ObjectClickEvent e) {
			e.getPlayer().getInventory().addItem(1929);
		}
	};
	
	public static ObjectClickHandler handleBirdFoods = new ObjectClickHandler(new Object[] { 30089, 30090, 30091, 30092 }) {
		@Override
		public void handle(ObjectClickEvent e) {
			if (!e.getPlayer().getInventory().hasFreeSlots()) {
				e.getPlayer().sendMessage("You don't have enough inventory space.");
				return;
			}
			Item food = new Item(14714 + (e.getObjectId() - 30089));
			e.getPlayer().sendMessage("You grab some " + food.getName() + ".");
			e.getPlayer().getInventory().addItem(food);
		}
	};
	
	public static ItemOnObjectHandler handleWaterIntoBirdDish = new ItemOnObjectHandler(new Object[] { 42731 }) {
		@Override
		public void handle(ItemOnObjectEvent e) {
			if (e.getItem().getId() != 1929) {
				e.getPlayer().sendMessage("Nothing interesting happens.");
				return;
			}
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) >= 3) {
				e.getPlayer().sendMessage("You've already woken the bird up! It doesn't need any more water.");
				return;
			}
			e.getPlayer().getInventory().deleteItem(1929, 1);
			e.getPlayer().getInventory().addItem(1925, 1);
			e.getPlayer().getVars().setVarBit(6027, 1);
			e.getPlayer().sendMessage("You fill the bird's dish with water.");
			if (e.getPlayer().getVars().getVarBit(6026) == e.getPlayer().getNSI("easterBirdFood")) {
				e.getPlayer().sendMessage("The bird wakes up and begins eating and drinking!");
				e.getPlayer().save(Easter2021.STAGE_KEY, 3);
				e.getPlayer().getVars().setVarBit(6014, 1);
				WorldTasksManager.delay(10, () -> {
					e.getPlayer().getVars().setVarBit(6026, 0);
					e.getPlayer().getVars().setVarBit(6027, 0);
				});
			} else {
				e.getPlayer().sendMessage("The bird still needs the correct food it seems.");
			}
		}
	};
	
	public static ItemOnObjectHandler handleFoodIntoBirdDish = new ItemOnObjectHandler(new Object[] { 42732 }) {
		@Override
		public void handle(ItemOnObjectEvent e) {
			if (e.getItem().getId() < 14714 || e.getItem().getId() > 14717) {
				e.getPlayer().sendMessage("Nothing interesting happens.");
				return;
			}
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) >= 3) {
				e.getPlayer().sendMessage("You've already woken the bird up! It doesn't need any more food.");
				return;
			}
			int foodId = e.getItem().getId() - 14713;
			e.getPlayer().getInventory().deleteItem(e.getItem().getId(), 1);
			e.getPlayer().getVars().setVarBit(6026, foodId);
			e.getPlayer().sendMessage("You fill the bird's dish with " + e.getItem().getName() + ".");
			if (e.getPlayer().getVars().getVarBit(6027) == 0) {
				e.getPlayer().sendMessage("The bird still looks thirsty.");
				return;
			}
			if (e.getPlayer().getVars().getVarBit(6026) == (e.getPlayer().getNSI("easterBirdFood")+1)) {
				e.getPlayer().save(Easter2021.STAGE_KEY, 3);
				e.getPlayer().getVars().setVarBit(6014, 1);
				WorldTasksManager.delay(10, () -> {
					e.getPlayer().getVars().setVarBit(6026, 0);
					e.getPlayer().getVars().setVarBit(6027, 0);
				});
			} else {
				e.getPlayer().sendMessage("That doesn't seem to be the correct food." + e.getPlayer().getNSI("easterBirdFood"));
			}
		}
	};
	
	public static ObjectClickHandler handleCrates = new ObjectClickHandler(new Object[] { 30100, 30101, 30102, 30103, 30104 }) {
		@Override
		public void handle(ObjectClickEvent e) {
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) < 5) {
				e.getPlayer().sendMessage("You don't find anything that looks useful to you right now.");
				return;
			}
			if (COG_LOCATIONS[e.getPlayer().getNSI("cogLocation")].matches(e.getObject()) && !e.getPlayer().getInventory().containsItem(COG)) {
				e.getPlayer().getInventory().addItem(COG);
				e.getPlayer().startConversation(new Conversation(e.getPlayer()).addItem(COG, "You find a cog in the crate!"));
				return;
			}
			if (PISTON_LOCATIONS[e.getPlayer().getNSI("pistonLocation")].matches(e.getObject()) && !e.getPlayer().getInventory().containsItem(PISTON)) {
				e.getPlayer().getInventory().addItem(PISTON);
				e.getPlayer().startConversation(new Conversation(e.getPlayer()).addItem(PISTON, "You find some pistons in the crate!"));
				return;
			}
			if (CHIMNEY_LOCATIONS[e.getPlayer().getNSI("chimneyLocation")].matches(e.getObject()) && !e.getPlayer().getInventory().containsItem(CHIMNEY)) {
				e.getPlayer().getInventory().addItem(CHIMNEY);
				e.getPlayer().startConversation(new Conversation(e.getPlayer()).addItem(CHIMNEY, "You find a chimney in the crate!"));
				return;
			}
			e.getPlayer().sendMessage("You find nothing interesting.");
		}
	};
	
	public static ItemOnObjectHandler handleFixIncubator = new ItemOnObjectHandler(new Object[] { 42733 }) {
		@Override
		public void handle(ItemOnObjectEvent e) {
			if (e.getPlayer().getI(Easter2021.STAGE_KEY) < 5) {
				e.getPlayer().sendMessage("It looks really broken.");
				return;
			}
			switch(e.getItem().getId()) {
			case COG:
				if (e.getPlayer().getVars().getVarBit(6016) <= 0) {
					e.getPlayer().getInventory().deleteItem(e.getItem());
					e.getPlayer().getVars().setVarBit(6016, 1);
					e.getPlayer().sendMessage("You attach the cog back into place.");
				} else {
					e.getPlayer().sendMessage("You already have attached the cog.");
				}
				break;
			case PISTON:
				if (e.getPlayer().getVars().getVarBit(6016) == 1) {
					e.getPlayer().getInventory().deleteItem(e.getItem());
					e.getPlayer().getVars().setVarBit(6016, 2);
					e.getPlayer().sendMessage("You attach the pistons back into place.");
				} else {
					e.getPlayer().sendMessage("That part won't fit quite yet.");
				}
				break;
			case CHIMNEY:
				if (e.getPlayer().getVars().getVarBit(6016) == 2) {
					e.getPlayer().getInventory().deleteItem(e.getItem());
					e.getPlayer().getVars().setVarBit(6016, 4);
					e.getPlayer().sendMessage("You attach the chimney back into place.");
					e.getPlayer().sendMessage("You hear the machine whirr as it turns back on.");
					e.getPlayer().save(Easter2021.STAGE_KEY, 6);
				} else {
					e.getPlayer().sendMessage("That part won't fit quite yet.");
				}
				break;
			default:
				break;
			}
		}
	};
	
	public static void useBunnyHole(Player player, GameObject object, WorldTile toTile) {
		player.lock();
		player.faceObject(object);
		WorldTasksManager.delay(1, () -> {
			player.setNextAnimation(new Animation(8901));
			player.setNextSpotAnim(new SpotAnim(1567));
		});
		WorldTasksManager.delay(13, () -> {
			player.setNextWorldTile(toTile);
			player.setNextAnimation(new Animation(8902));
		});
		WorldTasksManager.delay(22, () -> {
			player.setNextAnimation(new Animation(-1));
			player.unlock();
		});
	}
}
