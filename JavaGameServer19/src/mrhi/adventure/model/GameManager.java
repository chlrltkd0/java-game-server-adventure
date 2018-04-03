package mrhi.adventure.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhi.adventure.db.AccountDAO;
import mrhi.adventure.db.ChatDAO;
import mrhi.adventure.db.ItemDAO;
import mrhi.adventure.db.ResourceDAO;
import mrhi.adventure.model.game.DropItem;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.SItem;
import mrhi.adventure.model.game.SMap;
import mrhi.adventure.model.game.SMob;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.ChatVO;
import mrhi.adventure.model.vo.IntegerVO;
import mrhi.adventure.model.vo.ItemVO;

public class GameManager {

	public static int itemGenIdx = 0;
	public static int mobGenIdx = 0;
	private ItemDAO itemDAO = new ItemDAO();
	private ChatDAO chatDAO = new ChatDAO();
	private Map<Integer, SMap> protoMapMap = new HashMap<>();
	private Map<Integer, SMob> protoMobMap = new HashMap<>();
	private Map<Integer, SItem> protoItemMap = new HashMap<>();
	private Map<Integer, GameMap> existMap = new HashMap<>();
	private Map<Integer, Integer> expMap = new HashMap<>();

	public GameManager() {	
		ResourceDAO rDAO = new ResourceDAO();
		List<SMap> pMapList = rDAO.getMapInfoList();
		List<SMob> pMobList = rDAO.getMobInfoList();
		List<SItem> pItemList = rDAO.getItemInfoList();
		
		for(int i=1; i<100; i++)
			expMap.put(i, i*10);
		
		for(SMap pMap : pMapList) 
			protoMapMap.put(pMap.getMap_idx(), pMap);
		
		for(SMob pMob : pMobList) 
			protoMobMap.put(pMob.getMob_idx(), pMob);
		
		for(SItem pItem : pItemList) 
			protoItemMap.put(pItem.getItem_idx(), pItem);
	}
	
	public synchronized void createMap(int mapid) {
		GameMap gMap = new GameMap();
		gMap.setMap(protoMapMap.get(mapid).clone());
		existMap.put(mapid, gMap);
		// Mob 만드는 예시코드 나중에는 더 좋은 방법을 사용해야한다. 데이터베이스에서 맵에 해당하는 몹을 가져와서 
		// 그걸 그려줘야한다. 그리고 몬스터를 추가하는 메서드로 뺴던가해야지
	}
	
	public SItem makeItem(int item_idx, int x, int y) {
		SItem item = protoItemMap.get(item_idx).clone();
		item.setX(x);
		item.setY(y);
		item.setGen_idx(itemGenIdx++);
		return item;
	}
	
	public SMob makeMob(int mob_index, int x, int y) {
		SMob jmob = protoMobMap.get(mob_index).clone();
		jmob.setGen_idx(mobGenIdx++);
		jmob.setX(x);
		jmob.setY(y);
		return jmob;
	}
	
	public void deadMob(GameMap gameMap, SMob mob) {
		gameMap.removeMob(mob);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(41, mob.getMobVO()));
		
	}
	
	public void commandHandle(Player player, String cmd) throws Exception {
		List<String> command = new ArrayList<>();
		String tmp = cmd.substring(1);
		int index = 0;

		// 명령어 ' ' 공백별로 나눔
		while(true) {
			index = tmp.indexOf(' ');
			if(index==-1) {
				command.add(tmp.substring(0));
				break;
			} else {
				command.add(tmp.substring(0, index));
			}
			tmp = tmp.substring(index+1);
		}

		
		if(command.get(0).equals("createitem")) {
			int item_idx = Integer.parseInt(command.get(1));
			int map_idx = player.getCharacter().getChr_mapid();
			Server.getInstance().getGameManager().createItem(item_idx, map_idx, player.getCharacter().getChr_x(), player.getCharacter().getChr_y());
		
		} else if(command.get(0).equals("generatemob")) {
			int mob_idx = Integer.parseInt(command.get(1));
			int map_idx = player.getCharacter().getChr_mapid();
			Server.getInstance().getGameManager().generateMob(mob_idx, map_idx, player.getCharacter().getChr_x(), player.getCharacter().getChr_y());
		
		} else if(command.get(0).equals("setlevel")) {
			
			
		} else if(command.get(0).equals("hide")) {
			Server.getInstance().getGameManager().hidePlayer(player);
			
		} else if(command.get(0).equals("show")) {
			Server.getInstance().getGameManager().showPlayer(player);
			
		} else if(command.get(0).equals("ban")) {
			System.out.println("밴");

		} else if(command.get(0).equals("out")) {
			Server.getInstance().getGameManager().outPlayer(command.get(1));
			
		} else if(command.get(0).equals("ipban")) {
			System.out.println("ip밴");

		}
	}

	public void createItem(int item_idx, int map_idx, int x, int y) {
		GameMap gameMap = existMap.get(map_idx);
		SItem item = makeItem(item_idx, x, y);
		gameMap.addItem(item);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(43, item.getItemVO()));
	}

	public void generateMob(int mob_idx, int map_idx, int x, int y) {
		GameMap gameMap = existMap.get(map_idx);
		SMob mob = makeMob(mob_idx, x, y);
		gameMap.addMob(mob);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(42, mob.getMobVO()));
	}

	public void gainItem(Player player, ItemVO itemVO) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = existMap.get(mapid);
		for(SItem item : gameMap.getItemList()) {
			if(item.getGen_idx()==itemVO.getGen_idx()) {
				gameMap.getItemList().remove(item);
				itemDAO.gainItem(player.getCharacter(), item);
				Server.getInstance().addPacket(player, new MyPacket(61, item.getItemVO()));
				Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(45, itemVO));
				break;
			}
		}
	}

	public void joinMap(Player player, int map_idx) {
		GameMap gameMap = existMap.get(map_idx);
		Server.getInstance().addPacket(player, new MyPacket(31, gameMap.getMapVO()));
		System.out.println(player.getCharacter());
		Server.getInstance().addPacket(existMap.get(map_idx).getPlayerList(), new MyPacket(26, player.getCharacter().makeInfoCharacter()));
		gameMap.addPlayer(player);
	}

	public void leaveMap(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = existMap.get(mapid);
		gameMap.removePlayer(player);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(27, player.getCharacter().makeInfoCharacter()));
	}

	public void showPlayer(Player player) {
		GameMap gameMap = existMap.get(player.getCharacter().getChr_mapid());
		gameMap.addPlayer(player);
		Server.getInstance().addPacket(player, new MyPacket(31, gameMap.getMapVO()));
		Server.getInstance().addPacket(existMap.get(player.getCharacter().getChr_mapid()).getPlayerList(), new MyPacket(26, player.getCharacter().makeInfoCharacter()));
	}

	public void hidePlayer(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = existMap.get(mapid);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(27, player.getCharacter().makeInfoCharacter()));
	}
	
	public void dieCharacter(Player player) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = existMap.get(mapid);
		
		player.getCharacter().setDead(true);
		player.getCharacter().setChr_hp(0);
		Server.getInstance().addPacket(player, new MyPacket(72, null));
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(73, player.getCharacter().makeInfoCharacter()));
	}
	
	public void hurtCharacter(Player player, int damage) {
		player.getCharacter().setChr_hp(player.getCharacter().getChr_hp()-damage);
		Server.getInstance().addPacket(player, new MyPacket(71, new IntegerVO(damage)));
	}

	public void outPlayer(String chr_name) {
		for(Player player : Server.getInstance().getPlayerList()) {
			if(player.getCharacter()!=null && player.getCharacter().getChr_name().equals(chr_name))
				player.disconnect();
		}
	}
	
	public void chat(Player player, ChatVO chatVO) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGameManager().getExistMap().get(mapid);
		ChatVO cvo = chatVO;
		System.out.println(cvo.getChr_idx() + " : " + cvo.getText());
		cvo.setChr_idx(player.getCharacter().getChr_idx());
//		chatDAO.generateChatLog(cvo);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(51, cvo));
	}
	
	public void mobDropItem(GameMap gameMap, SMob mob){
		for(DropItem dropItem : itemDAO.getDropItem(mob)) {
			if(Math.random()*10000<dropItem.getProbability())
				Server.getInstance().getGameManager().createItem(dropItem.getItem_idx(), gameMap.getMapVO().getMap_idx(), mob.getX(), mob.getY());
		}
	}
	
	
	public Map<Integer, Integer> getExpMap() {
		return expMap;
	}

	public void setExpMap(Map<Integer, Integer> expMap) {
		this.expMap = expMap;
	}
	
	public Map<Integer, GameMap> getExistMap() {
		return existMap;
	}

	public GameMap requestMap(int mapid) {
		return existMap.get(mapid);
	}

	public Map<Integer, SMap> getProtoMapMap() {
		return protoMapMap;
	}

	public Map<Integer, SMob> getProtoMobMap() {
		return protoMobMap;
	}

	public Map<Integer, SItem> getProtoItemMap() {
		return protoItemMap;
	}
	
}
