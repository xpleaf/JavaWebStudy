package cn.itcast.crm.action;

import java.sql.SQLException;

public class DBDao {

	public void getConn(){
		try {
			DBUtil.getConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
