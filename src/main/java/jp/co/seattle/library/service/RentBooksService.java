package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.RentInfo;
import jp.co.seattle.library.rowMapper.RentInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 *  @author user
 */
/**
 */

@Service
public class RentBooksService {
    final static Logger logger = LoggerFactory.getLogger(RentBooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍を借りる
     * 
     * @param bookId　書籍情報
     */
    public void rentBook(int bookId, int userId) {
        String sql = "INSERT INTO rentBooks(bookId,userId) VALUES ("
                + bookId + "," + userId + ");";
        jdbcTemplate.update(sql);
    }

    /**
     * 借りた日付をrentLogに挿入する
     * 
     * @param bookId
     * @param userId
     */
    public void insertRentLog(int bookId, String title, int userId) {
        String sql = "INSERT INTO rentLog(bookId,title,userId,rent_Log) VALUES ("
                + bookId + ",'" + title + "'," + userId + "," + "sysdate()" + ");";
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を返却する（MySQLからデータを消す）
     * @param bookId
     */
    public void returnBook(int bookId, int userId) {
        String sql = "DELETE FROM rentBooks WHERE bookId=" + bookId + " AND userId =" + userId + ";";
        jdbcTemplate.update(sql);
    }

    public void insertReturnLog(int bookId, int userId) {
        String sql = "UPDATE rentLog SET return_log = sysdate() WHERE bookId=" + bookId + " AND userId ="
                + userId + ";";

        jdbcTemplate.update(sql);
    }

    public List<RentInfo> selectRentLog(int userId) {
        List<RentInfo> resultLog = jdbcTemplate.query(
                "SELECT * FROM rentLog where userId =" + userId + ";",
                new RentInfoRowMapper());
        return resultLog;
    }

    public String searchTitle(int bookId) {
        String sql = "SELECT title FROM books WHERE bookId=" + bookId + ";";
        String title = jdbcTemplate.queryForObject(sql, String.class);

        return title;
    }

    public void insertNgRentStatus(int bookId) {
        String sql = "UPDATE books SET rentStatus='貸し出し不可' WHERE bookId=" + bookId + ";";
        jdbcTemplate.update(sql);
    }

    public void insertOkRentStatus(int bookId) {
        String sql = "UPDATE books SET rentStatus='貸し出し可' WHERE bookId=" + bookId + ";";
        jdbcTemplate.update(sql);
    }
    public int searchRentStock(int bookId) {
        String sql = "SELECT rentOkStock FROM books WHERE bookId="+bookId+";";
        int rentCount = jdbcTemplate.queryForObject(sql, Integer.class);
        return rentCount;
    }
    public void insertRentStock(int rentCount,int bookId) {
        String sql = "UPDATE books SET rentOkStock="+rentCount+" WHERE bookId="+bookId+";";
        jdbcTemplate.update(sql);
    }

}
