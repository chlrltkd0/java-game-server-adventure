package mrhi.adventure.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.game.DropItem;
import mrhi.adventure.model.game.SItem;
import mrhi.adventure.model.game.SMob;
import mrhi.adventure.model.vo.CharacterVO;

public class ItemDAO extends DataDAO {

	private String getDropItem_sql = "select item_idx, probability from drop_item where mob_idx=?";
	private String gainItem_sql = "insert into item_own values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String itemList_sql = "select * from item_own where chr_idx=?;";
	
	public List<DropItem> getDropItem(SMob mob) {
		List<DropItem> dropItemList = new ArrayList<>();	
		
		rs = executeQuery(getDropItem_sql, mob.getMob_idx());
		try {
			while(rs.next()) {
				DropItem dropItem = new DropItem();
				dropItem.setItem_idx(rs.getInt("item_idx"));
				dropItem.setProbability(rs.getInt("probability"));
				dropItemList.add(dropItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dropItemList;
	}
	
	public void gainItem(CharacterVO charVO, SItem item) {
		executeUpdate(gainItem_sql, charVO.getChr_idx(), item.getItem_idx(), item.getItem_name(), item.getItem_type()
				, item.getItem_level(), item.getItem_job(), item.getItem_power(), item.getItem_mPower()
				, item.getItem_str(), item.getItem_dex(), item.getItem_int(), item.getItem_pro()
				, item.getItem_maxHp(), item.getItem_maxMp(), item.getItem_speed());
		DBManager.close(conn, pstmt, rs);
	}
	
	public List<SItem> getItemList(CharacterVO charVO) {
		List<SItem> itemList = new ArrayList<>();	
		rs = executeQuery(itemList_sql, charVO.getChr_idx());
		try {
			while(rs.next()) {
				SItem itemVO = new SItem();
				itemVO.setItem_dex(rs.getInt("item_dex"));
				itemVO.setItem_idx(rs.getInt("item_idx"));
				itemVO.setItem_int(rs.getInt("item_int"));
				itemVO.setItem_job(rs.getInt("item_job"));
				itemVO.setItem_level(rs.getInt("item_level"));
				itemVO.setItem_maxHp(rs.getInt("item_maxHp"));
				itemVO.setItem_maxMp(rs.getInt("item_maxMp"));
				itemVO.setItem_mPower(rs.getInt("item_mPower"));
				itemVO.setItem_name(rs.getString("item_name"));
				itemVO.setItem_power(rs.getInt("item_power"));
				itemVO.setItem_pro(rs.getInt("item_pro"));
				itemVO.setItem_speed(rs.getInt("item_speed"));
				itemVO.setItem_str(rs.getInt("item_str"));
				itemVO.setItem_type(rs.getInt("item_type"));
				itemVO.setOwn_idx(rs.getInt("own_idx"));
				itemList.add(itemVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return itemList;
	}
	
}
