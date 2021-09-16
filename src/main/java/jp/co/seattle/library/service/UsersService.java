package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.rowMapper.UserCountRowMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
//APIの入り口 APIとは、他のソフトウェアが外部から自分のソフトウェアへアクセスし利用できるようにしたもの
//ソフトウェアコンポーネントが互いにやりとりするのに使用するインタフェースの仕様
public class UsersService {
    final static Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * ユーザー情報を登録する
     * @param userInfo ユーザー情報
     */
    public void registUser(UserInfo userInfo) {

        // SQL生成
        String sql = "INSERT INTO users (email, password,reg_date,upd_date) VALUES ('"
                + userInfo.getEmail()
                + "','"
                + userInfo.getPassword()
                + "',sysdate(),sysdate()" + ")";

        jdbcTemplate.update(sql);
    }

    /**
     * ユーザー情報取得
     * @param email メールアドレス
     * @param password パスワード
     * @return ユーザー情報
     */
    public UserInfo selectUserInfo(String email, String password) {
        // TODO SQL生成
		String sql = "select id,email,password from users where email='" + email + "'and password='" + password + "'";
		try {
			UserInfo selectedUserInfo = jdbcTemplate.queryForObject(sql, new UserCountRowMapper());
			return selectedUserInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
    }

    /**
     * userIdを元に登録されているemailとpasswordをMySQLから取得し配列に格納
     * @param userId ユーザーID
     * @return userInfomation ユーザー情報
     */
    public String[] pickUpUserInfo(int userId) {
        String sqlEmail = "select email from users where id='" + userId + "';";
        String currentEmail = jdbcTemplate.queryForObject(sqlEmail, String.class);
        String sqlPass = "select password from users where id='" + userId + "';";
        String currentPass = jdbcTemplate.queryForObject(sqlPass, String.class);
        String[] userInfomation = { currentEmail, currentPass };
        return userInfomation;
    }
    
    /**
     * アカウント情報を更新する
     * @param userInfo ユーザー情報
     */
    public void userInfoUpdate(UserInfo userInfo) {
        String sql = "UPDATE users SET email='" + userInfo.getEmail() +
                "',password='" + userInfo.getPassword() +
                "',upd_date = " + "sysdate()" +
                " WHERE id ='" + userInfo.getUserId() + "';";

        jdbcTemplate.update(sql);

    }

    public int returnUserId(String email, String password) {
        String sql = "select id from users where email='" + email + "' and password='" + password + "';";
        int userId = jdbcTemplate.queryForObject(sql, Integer.class);
        return userId;
    }

}
