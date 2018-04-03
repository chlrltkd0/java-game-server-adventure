package mrhi.adventure.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mrhi.adventure.model.MyLog;
import mrhi.adventure.model.Player;
import mrhi.adventure.model.SendPacket;
import mrhi.adventure.model.Server;
import mrhi.adventure.model.game.Damage;
import mrhi.adventure.model.game.DropItem;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.SItem;
import mrhi.adventure.model.game.SMob;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.AuthenticationVO;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.ChatVO;
import mrhi.adventure.model.vo.IntegerVO;
import mrhi.adventure.model.vo.ItemVO;
import mrhi.adventure.model.vo.MapVO;
import mrhi.adventure.model.vo.MobVO;
import mrhi.adventure.model.vo.OtherCharacterVO;

public class ReceiveHandler implements Runnable{
	private Player player;

	public ReceiveHandler(Player player) {
		super();
		this.player = player;
	}

	@Override
	public void run() {
		AccountVO avo = null;
		CharacterVO cvo = null;
		MapVO mapvo = null;
		MobVO mobvo = null;
		ItemVO itemVO = null;
		ChatVO chatVO = null;
		IntegerVO ivo = null;
		OtherCharacterVO ocvo = null;
		AuthenticationVO authVO = null;

		try {
			while(true) {
				MyPacket packet = null;

				packet = (MyPacket)player.getConnectionManager().receive();
				String ip = player.getConnectionManager().getSocket().getInetAddress().getHostAddress();
				System.out.println(ip + " : " + packet);
				switch(packet.getType())
				{		

				case 10:
					MyLog.log(ip + " 회원가입 요청");
					avo = (AccountVO)packet.getSubObject();
					registerHandler(avo);
					break;

				case 12:
					MyLog.log(ip + " 로그인 요청");
					avo = (AccountVO)packet.getSubObject();
					loginHandler(avo);
					break;

//				case 140: // 아이디찾기 이메일 전송요청
//					MyLog.log(ip + " 아이디찾기 이메일 전송요청");
//					avo = (AccountVO)packet.getSubObject();
//					requestFindIDEmailHandler(avo);
//					break;
//
//				case 150: // 비번찾기 이메일 전송요청
//					MyLog.log(ip + " 비번찾기 이메일 전송요청");
//					avo = (AccountVO)packet.getSubObject();
//					requestFindPWEmailHandler(avo);
//					break;
//
//				case 160: // 회원가입 이메일 전송요청
//					MyLog.log(ip + " 회원가입 이메일 전송요청");
//					avo = (AccountVO)packet.getSubObject();
//					requestRegisterEmailHandler(avo);
//					break;
//
//				case 141:
//					MyLog.log(ip + " 아이디찾기 인증 확인요청");
//					authVO = (AuthenticationVO)packet.getSubObject();
//					requestFindIDAuthHandler(authVO);
//					break;
//
//				case 151:
//					MyLog.log(ip + " 비번찾기 인증 확인요청");
//					authVO = (AuthenticationVO)packet.getSubObject();
//					requestFindPWAuthHandler(authVO);
//					break;
//
//				case 161:
//					MyLog.log(ip + " 가입 인증 확인요청");
//					authVO = (AuthenticationVO)packet.getSubObject();
//					requestRegisterAuthHandler(authVO);
//					break;

				case 17:
					avo = (AccountVO)packet.getSubObject();
					changePassword(avo);
					break;

				case 20:
					MyLog.log(ip + " 케릭터 생성 요청");
					cvo = (CharacterVO)packet.getSubObject();
					createCharacterHandler(cvo);
					break;

				case 21:
					MyLog.log(ip + " 케릭터 delete 요청");
					cvo = (CharacterVO)packet.getSubObject();
					deleteCharacterHandler(this.player.getAccount(), cvo);
					break;

				case 22:
					MyLog.log(ip + " 케릭터 정보 요청");
					cvo = (CharacterVO)packet.getSubObject();
					requestCharacterHandler(cvo);
					break;
				case 24:
					// 다른사람 위치바꼈따!
					ocvo = (OtherCharacterVO)packet.getSubObject();
					positionUpdateHandler(ocvo);
					break;

				case 28:
					MyLog.log(ip + " 케릭터리스트 요청");
					requestCharListHandler();
					break;

				case 30:
					MyLog.log(ip + " 맵 정보 요청");
					mapvo = (MapVO)packet.getSubObject();
					requestMapHandler(mapvo);
					break;

				case 40:
					MyLog.log(ip + " attack mob");
					mobvo = (MobVO)packet.getSubObject();
					attackMobHandler(mobvo);
					break;

				case 44:
					MyLog.log(ip + " gather Item");
					itemVO = (ItemVO)packet.getSubObject();
					gainItemHandler(itemVO);
					break;

				case 50:
					chatVO = (ChatVO)packet.getSubObject();
					chatHandler(chatVO);
					break;
					
				case 70:
					ocvo = (OtherCharacterVO)packet.getSubObject();
					attackUserHandler(ocvo);
					break;

				case 80:
					System.out.println("스텟올려달래");
					ivo = (IntegerVO)packet.getSubObject();
					increaseStatHandler(ivo);
					break;
				}
			}
		} catch (Exception e) {
			Server.getInstance().getPlayerList().remove(this.player);
			try {
				e.printStackTrace();
				if(player.getAccount() != null && player.getCharacter() != null)
					Server.getInstance().getCharacterManager().saveLocation(player);
				System.out.println(player.getConnectionManager().getSocket().getInetAddress().getHostAddress() + "님232이 종료하셨습니다.");
				disconnectCharacter(player);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}

	private void increaseStatHandler(IntegerVO integerVO) {
		int stat = player.getCharacter().getChr_statPoint();
		if(stat>0) {
			player.getCharacter().setChr_statPoint(stat-1);
			switch(integerVO.getValue()) {
			case 1:
				player.getCharacter().setChr_str(player.getCharacter().getChr_str()+1);
				break;
			case 2:
				player.getCharacter().setChr_dex(player.getCharacter().getChr_dex()+1);
				break;
			case 3:
				player.getCharacter().setChr_int(player.getCharacter().getChr_int()+1);
				break;
			case 4:
				player.getCharacter().setChr_pro(player.getCharacter().getChr_pro()+1);
				break;
			}
			CharacterVO charVO = new CharacterVO();
			charVO.setChr_str(player.getCharacter().getChr_str());
			charVO.setChr_dex(player.getCharacter().getChr_dex());
			charVO.setChr_int(player.getCharacter().getChr_int());
			charVO.setChr_pro(player.getCharacter().getChr_pro());
			Server.getInstance().getCharacterManager().updateStat(player, charVO);
		}
	}

	private void attackUserHandler(OtherCharacterVO ocvo) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGameManager().getExistMap().get(mapid);
		int damage = player.getCharacter().getDamage();
		for(Player player : gameMap.getPlayerList()) {
			if(ocvo.getChr_idx()==player.getCharacter().getChr_idx()) {
				if(0>player.getCharacter().getChr_hp()-damage)
					Server.getInstance().getGameManager().dieCharacter(player);
				else
					Server.getInstance().getGameManager().hurtCharacter(player, damage);
			}
		}
	}

	private void chatHandler(ChatVO chatVO) throws Exception {
		//운영자의 명령어
		if(player.getCharacter().getChr_grade()>0 && chatVO.getText().charAt(0)=='!') {
			Server.getInstance().getGameManager().commandHandle(player, chatVO.getText());
		} else { // 그냥 일반 대화
			Server.getInstance().getGameManager().chat(player, chatVO);
		}
	}

	

	private synchronized void gainItemHandler(ItemVO itemVO) {
		Server.getInstance().getGameManager().gainItem(player, itemVO);
	}

	private void deleteCharacterHandler(AccountVO accountVO, CharacterVO charVO) {
		Server.getInstance().getCharacterManager().deleteCharacter(accountVO, charVO);
	}

	private void requestCharListHandler() throws IOException {
		Server.getInstance().getCharacterManager().requestCharacterList(player);
	}

	private void attackMobHandler(MobVO mobVO) throws IOException {
		int mapid = player.getCharacter().getChr_mapid();
		int damage = player.getCharacter().getDamage();
		GameMap gameMap = Server.getInstance().getGameManager().getExistMap().get(mapid);

		SMob mob = gameMap.getMob(mobVO);
		if(mob!=null) {
			if(mob.getMob_hp()>damage) {
				mob.hurt(new Damage(player, damage));
				mob.setMob_hp(mob.getMob_hp()-damage);
			} else {
				mob.hurt(new Damage(player, mob.getMob_hp()));
				mob.setMob_hp(0);
			}

			if(mob.getMob_hp()==0) 
			{
				//경험치나눠주기
				for(Damage dam : mob.getDamageList()) {
					int exp = (int)(mob.getMob_exp()*((double)dam.getDamage()/mob.getMob_maxHp()));
					dam.getPlayer().getCharacter().setChr_exp(dam.getPlayer().getCharacter().getChr_exp()+exp);

					//레벨업
					if(dam.getPlayer().getCharacter().getChr_exp()>=Server.getInstance().getGameManager().getExpMap().get(dam.getPlayer().getCharacter().getChr_level())) {
						dam.getPlayer().getCharacter().setChr_exp(0);
						dam.getPlayer().getCharacter().setChr_statPoint(dam.getPlayer().getCharacter().getChr_statPoint()+1);
						dam.getPlayer().getCharacter().setChr_skillPoint(dam.getPlayer().getCharacter().getChr_skillPoint()+1);
						dam.getPlayer().getCharacter().setChr_level(dam.getPlayer().getCharacter().getChr_level()+1);
					}

					//이미있는걸 안보내고 새로만들어서 보내는 이유는 네트워크 통신이 과부하 걸릴까봐 이렇게 했는데;
					CharacterVO charVO = new CharacterVO();
					charVO.setChr_level(dam.getPlayer().getCharacter().getChr_level());
					charVO.setChr_exp(dam.getPlayer().getCharacter().getChr_exp());
					charVO.setChr_skillPoint(dam.getPlayer().getCharacter().getChr_skillPoint());
					charVO.setChr_statPoint(dam.getPlayer().getCharacter().getChr_statPoint());

					Server.getInstance().getCharacterManager().updateState(player, charVO);
				}
				//몹뒤졌다
				Server.getInstance().getGameManager().deadMob(gameMap, mob);

				//아이템떨구기
				Server.getInstance().getGameManager().mobDropItem(gameMap, mob);
			}
		}
	}

	private void disconnectCharacter(Player player) throws IOException{
		if(player.getCharacter()!=null) {
			Server.getInstance().getGameManager().leaveMap(player);
		}
	}

	private void positionUpdateHandler(OtherCharacterVO infoCharacter) throws IOException {
		Server.getInstance().getCharacterManager().positionUpdate(player, infoCharacter);

	}

	// synchronize 한이유는 createMap을 두개생성해서 겹쳐버릴수가 있다.
	private synchronized void requestMapHandler(MapVO gameMap) throws IOException {
		Map<Integer, GameMap> existMap = Server.getInstance().getGameManager().getExistMap();
		if(!existMap.containsKey(gameMap.getMap_idx()))
			Server.getInstance().getGameManager().createMap(gameMap.getMap_idx());
		Server.getInstance().getGameManager().joinMap(player, gameMap.getMap_idx());
	}

	private void requestCharacterHandler(CharacterVO characterVO) throws IOException {
		Server.getInstance().getCharacterManager().requestCharacter(player, characterVO);
	}

	private void createCharacterHandler(CharacterVO characterVO) {
		Server.getInstance().getCharacterManager().createCharacter(player, characterVO);
	}

	public void loginHandler(AccountVO accountVO) throws IOException {
		Server.getInstance().getAccountManager().login(player, accountVO);
	}

	public void registerHandler(AccountVO accountVO) {
		Server.getInstance().getAccountManager().register(accountVO);
	}

//	public void requestFindIDEmailHandler(AccountVO accountVO) throws IOException {
//		Server.getInstance().getAccountManager().requestFindIDEmail(accountVO);
//	}
//
//	public void requestFindPWEmailHandler(AccountVO accountVO) {
//		Server.getInstance().getAccountManager().requestFindPWEmail(accountVO);
//	}
//
//	public void requestFindIDAuthHandler(AuthenticationVO authenticationVO) throws IOException {
//		Server.getInstance().getAccountManager().requestFindIDAuth(player, authenticationVO);
//	}
//
//	public void requestFindPWAuthHandler(AuthenticationVO authenticationVO) {
//		Server.getInstance().getAccountManager().requestFindPWAuth(player, authenticationVO);
//	}
//
//	public void requestRegisterEmailHandler(AccountVO accountVO) {
//		Server.getInstance().getAccountManager().requestRegisterEmailHandler(accountVO);
//	}
//
//	public void requestRegisterAuthHandler(AuthenticationVO authenticationVO) {
//		Server.getInstance().getAccountManager().requestRegisterAuthHandler(player, authenticationVO);
//	}

	public void changePassword(AccountVO accountVO) {
		Server.getInstance().getAccountManager().changePassword(accountVO);
	}
}

