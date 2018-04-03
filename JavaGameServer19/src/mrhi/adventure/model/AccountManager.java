package mrhi.adventure.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import mrhi.adventure.db.AccountDAO;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.AuthenticationVO;

public class AccountManager {
	private AccountDAO accountDAO = new AccountDAO();
	
	public void login(Player player, AccountVO accountVO) throws IOException
	{
		AccountVO avo = accountDAO.login(accountVO);
		if(avo != null)
		{
			// 새로접속한 계정이 기존 접속자랑 같은경우
			for(Player p : Server.getInstance().getPlayerList()) {
				if(p.getAccount()!=null && p.getAccount().getAcc_idx()==avo.getAcc_idx())
					p.disconnect();
			}
			player.setAccount(avo);
			Server.getInstance().addPacket(player, new MyPacket(13, avo));
		}
	}
	
	public void register(AccountVO accountVO) {
		accountDAO.createAccount(accountVO);
	}
	
//	public void requestFindIDEmail(AccountVO accountVO) throws IOException {
//		eMailSender.sendAuthEMail(accountVO);
//	}
//
//	public void requestFindPWEmail(AccountVO accountVO) {
//		//이메일받아오기
//		AccountVO avo = accountDAO.getId(accountVO);
//		eMailSender.sendAuthEMail(avo);
////		Server.getInstance().getSendPacketQueue().add(new SendPacket(player, new MyPacket()));
//	}

//	public void requestFindIDAuth(Player player, AuthenticationVO authenticationVO) throws IOException {
//		AccountVO accountVO = new AccountVO();
//		accountVO.setAcc_email(authenticationVO.getEmail());
//		if(authenticationVO.getAuthNumber().equals(eMailSender.getAuthMap().get(authenticationVO.getEmail()))){
//			List<AccountVO> avo = accountDAO.findIDList(accountVO);
//			for (int i = 0; i < avo.size(); i++) 
//				Server.getInstance().addPacket(player, new MyPacket(142, avo.get(i)));
//		}
//	}

//	public void requestFindPWAuth(Player player, AuthenticationVO authenticationVO) {
//		//정보추가
//		AccountVO accountVO = new AccountVO();
//		accountVO.setAcc_id(authenticationVO.getId());
//		if(authenticationVO.getAuthNumber().equals(eMailSender.getAuthMap().get(authenticationVO.getId()))){
//			Server.getInstance().addPacket(player, new MyPacket(152, accountVO));
//		}
//	}
//
//	public void requestRegisterEmailHandler(AccountVO accountVO) {
//		eMailSender.sendAuthEMail(accountVO);
//	}
//
//	public void requestRegisterAuthHandler(Player player, AuthenticationVO authenticationVO) {
//		AccountVO accountVO = new AccountVO();
//		accountVO.setAcc_email(authenticationVO.getEmail());
//		if(authenticationVO.getAuthNumber().equals(eMailSender.getAuthMap().get(authenticationVO.getEmail()))){
//			AuthenticationVO authVO = new AuthenticationVO();
//			authVO.setConfirm(true);
//			System.out.println("시발보냈따");
//			Server.getInstance().addPacket(player, new MyPacket(162, authVO));
//		}
//	}

	public void changePassword(AccountVO accountVO) {
		try {
			accountDAO.changePassword(accountVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public EMailSender geteMailSender() {
//		return eMailSender;
//	}
}
