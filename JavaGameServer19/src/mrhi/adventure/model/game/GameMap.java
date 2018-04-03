package mrhi.adventure.model.game;

import java.util.LinkedList;
import java.util.List;

import mrhi.adventure.model.Player;
import mrhi.adventure.model.vo.MapVO;
import mrhi.adventure.model.vo.MobVO;

public class GameMap {
	private SMap map;
	private List<Player> playerList = new LinkedList<>();
	private List<SMob> mobList = new LinkedList<>();
	private List<SItem> itemList = new LinkedList<>();
	
	public MapVO getMapVO() {
		MapVO mapVO = new MapVO();
		mapVO.setMap_height(map.getMap_height());
		mapVO.setMap_idx(map.getMap_idx());
		mapVO.setMap_name(map.getMap_name());
		mapVO.setMap_type(map.getMap_type());
		mapVO.setMap_width(map.getMap_width());
		
		for(Player player : playerList) {
			System.out.println(player.getCharacter());
			mapVO.getOtherCharList().add(player.getCharacter().makeInfoCharacter());
		}
			
		
		for(SMob mob : mobList)
			mapVO.getMobList().add(mob.getMobVO());
		
		for(SItem item : itemList) 
			mapVO.getItemList().add(item.getItemVO());

		return mapVO;
	}
	
	public void addItem(SItem item) {
		itemList.add(item);
	}
	
	public void removeItem(SItem item) {
		itemList.remove(item);
	}
	
	public void addMob(SMob jmob) {
		mobList.add(jmob);
	}
	
	public void removeMob(SMob jmob) {
		mobList.remove(jmob);
	}
	
	public SMob getMob(MobVO mobVO) {
		for(SMob mob : mobList) {
			if(mob.getGen_idx()==mobVO.getGen_idx()) {
				return mob;
			}
		}
		return null;
	}
		
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		this.playerList.remove(player);
	}

	public SMap getMap() {
		return map;
	}

	public void setMap(SMap map) {
		this.map = map;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public List<SMob> getMobList() {
		return mobList;
	}

	public void setMobList(List<SMob> mobList) {
		this.mobList = mobList;
	}

	public List<SItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SItem> itemList) {
		this.itemList = itemList;
	}
	
}
