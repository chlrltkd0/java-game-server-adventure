package mrhi.adventure.model.game;

public class SCharacter {
	private int chr_idx;
	private int acc_idx;
	private String chr_name;
    private int chr_type;
	private int chr_job;
	private int chr_level;
	private int chr_exp;
	private int chr_money; // 나중에 long으로 바꾸자
	private int chr_str;
	private int chr_dex;
	private int chr_int;
	private int chr_pro;
	private int chr_hp;
	private int chr_mp;
	private int chr_maxHp;
	private int chr_maxMp;
	private int chr_speed;
	private int chr_statPoint;
	private int chr_skillPoint;
	private int chr_mapid;
	private int chr_x;
	private int chr_y;
	private int chr_grade;
	private int direction;
	private boolean isWalking;
	private boolean isAttacking;
	private boolean dead;
	/**
	 * @return the chr_idx
	 */
	public int getChr_idx() {
		return chr_idx;
	}
	/**
	 * @param chr_idx the chr_idx to set
	 */
	public void setChr_idx(int chr_idx) {
		this.chr_idx = chr_idx;
	}
	/**
	 * @return the acc_idx
	 */
	public int getAcc_idx() {
		return acc_idx;
	}
	/**
	 * @param acc_idx the acc_idx to set
	 */
	public void setAcc_idx(int acc_idx) {
		this.acc_idx = acc_idx;
	}
	/**
	 * @return the chr_name
	 */
	public String getChr_name() {
		return chr_name;
	}
	/**
	 * @param chr_name the chr_name to set
	 */
	public void setChr_name(String chr_name) {
		this.chr_name = chr_name;
	}
	/**
	 * @return the chr_type
	 */
	public int getChr_type() {
		return chr_type;
	}
	/**
	 * @param chr_type the chr_type to set
	 */
	public void setChr_type(int chr_type) {
		this.chr_type = chr_type;
	}
	/**
	 * @return the chr_job
	 */
	public int getChr_job() {
		return chr_job;
	}
	/**
	 * @param chr_job the chr_job to set
	 */
	public void setChr_job(int chr_job) {
		this.chr_job = chr_job;
	}
	/**
	 * @return the chr_level
	 */
	public int getChr_level() {
		return chr_level;
	}
	/**
	 * @param chr_level the chr_level to set
	 */
	public void setChr_level(int chr_level) {
		this.chr_level = chr_level;
	}
	/**
	 * @return the chr_exp
	 */
	public int getChr_exp() {
		return chr_exp;
	}
	/**
	 * @param chr_exp the chr_exp to set
	 */
	public void setChr_exp(int chr_exp) {
		this.chr_exp = chr_exp;
	}
	/**
	 * @return the chr_money
	 */
	public int getChr_money() {
		return chr_money;
	}
	/**
	 * @param chr_money the chr_money to set
	 */
	public void setChr_money(int chr_money) {
		this.chr_money = chr_money;
	}
	/**
	 * @return the chr_str
	 */
	public int getChr_str() {
		return chr_str;
	}
	/**
	 * @param chr_str the chr_str to set
	 */
	public void setChr_str(int chr_str) {
		this.chr_str = chr_str;
	}
	/**
	 * @return the chr_dex
	 */
	public int getChr_dex() {
		return chr_dex;
	}
	/**
	 * @param chr_dex the chr_dex to set
	 */
	public void setChr_dex(int chr_dex) {
		this.chr_dex = chr_dex;
	}
	/**
	 * @return the chr_int
	 */
	public int getChr_int() {
		return chr_int;
	}
	/**
	 * @param chr_int the chr_int to set
	 */
	public void setChr_int(int chr_int) {
		this.chr_int = chr_int;
	}
	/**
	 * @return the chr_pro
	 */
	public int getChr_pro() {
		return chr_pro;
	}
	/**
	 * @param chr_pro the chr_pro to set
	 */
	public void setChr_pro(int chr_pro) {
		this.chr_pro = chr_pro;
	}
	/**
	 * @return the chr_hp
	 */
	public int getChr_hp() {
		return chr_hp;
	}
	/**
	 * @param chr_hp the chr_hp to set
	 */
	public void setChr_hp(int chr_hp) {
		this.chr_hp = chr_hp;
	}
	/**
	 * @return the chr_mp
	 */
	public int getChr_mp() {
		return chr_mp;
	}
	/**
	 * @param chr_mp the chr_mp to set
	 */
	public void setChr_mp(int chr_mp) {
		this.chr_mp = chr_mp;
	}
	/**
	 * @return the chr_maxHp
	 */
	public int getChr_maxHp() {
		return chr_maxHp;
	}
	/**
	 * @param chr_maxHp the chr_maxHp to set
	 */
	public void setChr_maxHp(int chr_maxHp) {
		this.chr_maxHp = chr_maxHp;
	}
	/**
	 * @return the chr_maxMp
	 */
	public int getChr_maxMp() {
		return chr_maxMp;
	}
	/**
	 * @param chr_maxMp the chr_maxMp to set
	 */
	public void setChr_maxMp(int chr_maxMp) {
		this.chr_maxMp = chr_maxMp;
	}
	/**
	 * @return the chr_speed
	 */
	public int getChr_speed() {
		return chr_speed;
	}
	/**
	 * @param chr_speed the chr_speed to set
	 */
	public void setChr_speed(int chr_speed) {
		this.chr_speed = chr_speed;
	}
	/**
	 * @return the chr_statPoint
	 */
	public int getChr_statPoint() {
		return chr_statPoint;
	}
	/**
	 * @param chr_statPoint the chr_statPoint to set
	 */
	public void setChr_statPoint(int chr_statPoint) {
		this.chr_statPoint = chr_statPoint;
	}
	public int getChr_skillPoint() {
		return chr_skillPoint;
	}
	public void setChr_skillPoint(int chr_skillPoint) {
		this.chr_skillPoint = chr_skillPoint;
	}
	/**
	 * @return the chr_mapid
	 */
	public int getChr_mapid() {
		return chr_mapid;
	}
	/**
	 * @param chr_mapid the chr_mapid to set
	 */
	public void setChr_mapid(int chr_mapid) {
		this.chr_mapid = chr_mapid;
	}
	public int getChr_x() {
		return chr_x;
	}
	public void setChr_x(int chr_x) {
		this.chr_x = chr_x;
	}
	public int getChr_y() {
		return chr_y;
	}
	public void setChr_y(int chr_y) {
		this.chr_y = chr_y;
	}
	public int getChr_grade() {
		return chr_grade;
	}
	public void setChr_grade(int chr_grade) {
		this.chr_grade = chr_grade;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public boolean isWalking() {
		return isWalking;
	}
	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}
	public boolean isAttacking() {
		return isAttacking;
	}
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
