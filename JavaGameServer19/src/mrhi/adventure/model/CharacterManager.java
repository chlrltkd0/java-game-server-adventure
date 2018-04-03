package mrhi.adventure.model;

import java.io.IOException;
import java.util.List;

import mrhi.adventure.db.CharacterDAO;
import mrhi.adventure.db.ItemDAO;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.SItem;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.OtherCharacterVO;

public class CharacterManager {
	private CharacterDAO characterDAO = new CharacterDAO();
	private ItemDAO itemDAO = new ItemDAO();
	
	public void saveLocation(Player player) {
		characterDAO.saveLocation(player.getCharacter());
	}
	
	public void updateStat(Player player, CharacterVO charVO) {
		characterDAO.saveStat(player.getCharacter());
		Server.getInstance().addPacket(player, new MyPacket(81, charVO));
	}

	public void updateState(Player player, CharacterVO charVO) {
		characterDAO.saveState(player.getCharacter());
		Server.getInstance().addPacket(player, new MyPacket(62, charVO));
	}

	public void deleteCharacter(AccountVO accountVO, CharacterVO charVO) {
		characterDAO.deleteCharacter(accountVO, charVO);
	}
	
	public void createCharacter(Player player, CharacterVO characterVO) {
		CharacterVO charVO = characterVO;
		charVO.setAcc_idx(player.getAccount().getAcc_idx());
		characterDAO.createCharacter(charVO);
	}
	
	public void requestCharacterList(Player player) {
		List<CharacterVO> charList = characterDAO.getCharacterList(player.getAccount());
		for(CharacterVO charVO : charList)
			Server.getInstance().addPacket(player, new MyPacket(29, charVO));
	}
	
	public void requestCharacter(Player player, CharacterVO characterVO) throws IOException {
		CharacterVO charVO = characterVO;
		charVO.setAcc_idx(player.getAccount().getAcc_idx());
		charVO = characterDAO.getCharacter(charVO);
		if(charVO!=null) {
			player.setCharacter(charVO);
			Server.getInstance().addPacket(player, new MyPacket(23, charVO));
			
			List<SItem> itemList = itemDAO.getItemList(player.getCharacter());
			for(SItem item : itemList) {
				Server.getInstance().addPacket(player, new MyPacket(61, item.getItemVO()));
			}
		}
	}
	
	public void positionUpdate(Player player, OtherCharacterVO infoCharacter) {
		int mapid = infoCharacter.getChr_mapid();
		GameMap gameMap = Server.getInstance().getGameManager().getExistMap().get(mapid);

		// 서버에 있는 자기자신의 객체의 위치를 바꾸는 코드
		player.getCharacter().updateCharacter(infoCharacter);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(25, player.getCharacter().makeInfoCharacter()));
	}
}
