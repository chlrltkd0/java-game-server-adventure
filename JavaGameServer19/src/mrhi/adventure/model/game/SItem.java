package mrhi.adventure.model.game;

import mrhi.adventure.model.vo.ItemVO;

public class SItem implements Cloneable{
	private int item_idx;
	private int own_idx;
	private int gen_idx;
	private int item_type;
	private String item_name;
	private int item_job;
	private int item_level;
	private int item_power;
	private int item_mPower;
	private int item_str;
	private int item_dex;
	private int item_int;
	private int item_pro;
	private int item_maxHp;
	private int item_maxMp;
	private int item_speed;
	private int x;
	private int y;
	
	public SItem() {
		super();
	}
	
	public SItem(SItem jItem) {
		item_idx = jItem.getItem_idx();
		own_idx = jItem.getOwn_idx();
		gen_idx = jItem.getGen_idx();
		item_type = jItem.getItem_type();
		item_name = jItem.getItem_name();
		item_job = jItem.getItem_job();
		item_level = jItem.getItem_level();
		item_power = jItem.getItem_power();
		item_mPower = jItem.getItem_mPower();
		item_str = jItem.getItem_str();
		item_dex = jItem.getItem_dex();
		item_int = jItem.getItem_int();
		item_pro = jItem.getItem_pro();
		item_maxHp = jItem.getItem_maxHp();
		item_maxMp = jItem.getItem_maxMp();
		item_speed = jItem.getItem_speed();
	}
	
	public ItemVO getItemVO() {
		ItemVO itemVO = new ItemVO();
		itemVO.setItem_dex(item_dex);
		itemVO.setItem_idx(item_idx);
		itemVO.setGen_idx(gen_idx);
		itemVO.setItem_int(item_int);
		itemVO.setItem_job(item_job);
		itemVO.setItem_level(item_level);
		itemVO.setItem_maxHp(item_maxHp);
		itemVO.setItem_maxMp(item_maxMp);
		itemVO.setItem_mPower(item_mPower);
		itemVO.setItem_name(item_name);
		itemVO.setItem_power(item_power);
		itemVO.setItem_pro(item_pro);
		itemVO.setItem_speed(item_speed);
		itemVO.setItem_str(item_str);
		itemVO.setItem_type(item_type);
		itemVO.setOwn_idx(own_idx);
		itemVO.setX(x);
		itemVO.setY(y);
		return itemVO;
	}
	
	public int getGen_idx() {
		return gen_idx;
	}
	public void setGen_idx(int gen_idx) {
		this.gen_idx = gen_idx;
	}
	public int getItem_idx() {
		return item_idx;
	}
	public void setItem_idx(int item_idx) {
		this.item_idx = item_idx;
	}
	public int getOwn_idx() {
		return own_idx;
	}
	public void setOwn_idx(int own_idx) {
		this.own_idx = own_idx;
	}
	public int getItem_type() {
		return item_type;
	}
	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getItem_job() {
		return item_job;
	}
	public void setItem_job(int item_job) {
		this.item_job = item_job;
	}
	public int getItem_level() {
		return item_level;
	}
	public void setItem_level(int item_level) {
		this.item_level = item_level;
	}
	public int getItem_power() {
		return item_power;
	}
	public void setItem_power(int item_power) {
		this.item_power = item_power;
	}
	public int getItem_mPower() {
		return item_mPower;
	}
	public void setItem_mPower(int item_mPower) {
		this.item_mPower = item_mPower;
	}
	public int getItem_str() {
		return item_str;
	}
	public void setItem_str(int item_str) {
		this.item_str = item_str;
	}
	public int getItem_dex() {
		return item_dex;
	}
	public void setItem_dex(int item_dex) {
		this.item_dex = item_dex;
	}
	public int getItem_int() {
		return item_int;
	}
	public void setItem_int(int item_int) {
		this.item_int = item_int;
	}
	public int getItem_pro() {
		return item_pro;
	}
	public void setItem_pro(int item_pro) {
		this.item_pro = item_pro;
	}
	public int getItem_maxHp() {
		return item_maxHp;
	}
	public void setItem_maxHp(int item_maxHp) {
		this.item_maxHp = item_maxHp;
	}
	public int getItem_maxMp() {
		return item_maxMp;
	}
	public void setItem_maxMp(int item_maxMp) {
		this.item_maxMp = item_maxMp;
	}
	public int getItem_speed() {
		return item_speed;
	}
	public void setItem_speed(int item_speed) {
		this.item_speed = item_speed;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public SItem clone() {
		return new SItem(this);
	}
}