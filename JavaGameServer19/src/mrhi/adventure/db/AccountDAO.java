package mrhi.adventure.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.vo.AccountVO;

public class AccountDAO extends DataDAO {
	private String searchAccount_sql = "select * from t_account where acc_id=?;";
	private String createUser_sql = "insert into t_account values (default, ?, ?, ?, ?, ?);";
	private String login_sql = "select * from t_account where acc_id=? and acc_pw=?";
	private String findID_sql = "select acc_id from t_account where acc_email = ?";
	private String changePassword_sql = "update t_account set acc_pw = ? where acc_id = ?;";

	public void createAccount(AccountVO accountVO)
	{
		rs = executeQuery(searchAccount_sql, accountVO.getAcc_id());
		try {
			if(rs.next())
			{
				System.out.println("�ߺ� ���̵� ����!");
				return;
			}
			executeUpdate(createUser_sql, accountVO.getAcc_id(), accountVO.getAcc_pw()
					, accountVO.getAcc_name(), accountVO.getAcc_email(), accountVO.getAcc_hp());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
	}

	public AccountVO login(AccountVO accountVO)
	{
		AccountVO retAVO = null;
		rs = executeQuery(login_sql, accountVO.getAcc_id(), accountVO.getAcc_pw());
		try {
			if(rs.next()) {
				System.out.println("�α��� ����!");
				retAVO = new AccountVO();
				retAVO.setAcc_idx(rs.getInt("acc_idx"));
				retAVO.setAcc_id(rs.getString("acc_id"));
				retAVO.setAcc_pw(rs.getString("acc_pw"));
				retAVO.setAcc_name(rs.getString("acc_name"));
				retAVO.setAcc_email(rs.getString("acc_email"));
				retAVO.setAcc_hp(rs.getString("acc_hp"));
			} else {
				System.out.println("�α��� ����!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return retAVO;
	}

	public List<AccountVO> findIDList(AccountVO accountVO) {
		List<AccountVO> accountVOList = new ArrayList<>();
		AccountVO retAVO = null;
		rs = executeQuery(findID_sql, accountVO.getAcc_email());
		try {
			while (rs.next()) {
				System.out.println("���̵�ã�⼺��");
				retAVO = new AccountVO();
				retAVO.setAcc_id(rs.getString("acc_id"));
				accountVOList.add(retAVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return accountVOList;
	}
	
	public AccountVO getId(AccountVO accountVO) {
		AccountVO retAVO = null;
		rs = executeQuery(searchAccount_sql, accountVO.getAcc_id());
		System.out.println("getemaukl");
		try {
			if (rs.next()) {
				System.out.println("���̵�ã�Ƽ� �̸��Ϻ���!");
				retAVO = new AccountVO();
				retAVO.setAcc_email(rs.getString("acc_email"));
				retAVO.setAcc_id(rs.getString("acc_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return retAVO;
	}

	public void changePassword(AccountVO accountVO) throws SQLException {
		try {
			executeUpdate(changePassword_sql, accountVO.getAcc_pw(), accountVO.getAcc_id());
			System.out.println("�����Ϸ�");
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
	}

}
