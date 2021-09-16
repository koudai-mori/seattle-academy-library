package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 *  @author user
 */
/**
 */

@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select bookId,title,publisher,publish_date,thumbnail_url,author,rentStatus from books order by title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT * FROM books where bookId ="
                + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return bookDetailsInfo;
    }



    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {
        String sql = "INSERT INTO books (title, author,publisher,publish_date,thumbnail_name,thumbnail_url,reg_date,upd_date,isbn,description,stock,rentOkStock,category) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "',"
                + "sysdate(),"
                + "sysdate(),'"
                + bookInfo.getIsbn()+"','"
                + bookInfo.getDescription() + "',"
                + 1 + ","
                + 1 + ",'"
                + bookInfo.getCategory() + "');";
        jdbcTemplate.update(sql);
        }

        public int latestID() {
            String sql = "SELECT Max(bookId) FROM books";
        int bookMaxId = jdbcTemplate.queryForObject(sql, Integer.class);
        return bookMaxId;
    }



    //delete books with SQL
    /**
     * 書籍を削除する
     * 
     * @param bookId　書籍情報
     */
    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE bookId='" + bookId + "';";
        jdbcTemplate.update(sql);

        //おそらくIDを指定できていない、そのためDBの方のデータを消せていない
    }

    /**
     * 書籍を編集するメソッド
     * 
     *  
     */

    public void editbook(BookDetailsInfo bookInfo) {
        String sql = "UPDATE books SET title ='" + bookInfo.getTitle() +
                "',author='" + bookInfo.getAuthor() +
                "',publisher='" + bookInfo.getPublisher() +
                "',publish_date='" + bookInfo.getPublishDate() +
                "',thumbnail_url='" + bookInfo.getThumbnailUrl() +
                "',thumbnail_name='" + bookInfo.getThumbnailName() +
                "',isbn='" + bookInfo.getIsbn() +
                 "',description ='" +bookInfo.getDescription()+
                "',upd_date =" + "sysdate()" +
                "WHERE bookId ='" + bookInfo.getBookId() + "';";
        jdbcTemplate.update(sql);
    }

    /**
     * 本が貸し出し中か調べる
     * @param bookId
     * @return　結果をリターンしてあげる
     */
    public int rentCheck(int bookId) {
        String sql = "SELECT COUNT(*)FROM rentBooks where bookId =" + bookId + ";";
        int rentId = jdbcTemplate.queryForObject(sql, Integer.class);
        return rentId;
    }

    public List<BookInfo> searchLikeBook(String searchText) {
        List<BookInfo> searchedBook = jdbcTemplate.query(
                "SELECT * FROM books where title LIKE '%" + searchText + "%';",
                new BookInfoRowMapper());
        return searchedBook;
    }

    public int searchSameBook(String title, String author) {
        String sql = "SELECT COUNT(*)FROM books WHERE title='" + title + "' AND author='" + author + "';";
        int SameBookCount = jdbcTemplate.queryForObject(sql, Integer.class);
        return SameBookCount;
    }

    public int searchBookId(String title, String author) {
        String sql = "SELECT bookId FROM books WHERE title='" + title + "' AND author='" + author + "';";
        int bookId = jdbcTemplate.queryForObject(sql, Integer.class);
        return bookId;
    }

    public int searchStock(int SameBookId) {
        String sql = "SELECT stock FROM books WHERE bookId=" + SameBookId + ";";
        int stockCount = jdbcTemplate.queryForObject(sql, Integer.class);
        return stockCount;
    }

    public void stockIncrement(int stock, int SameBookId) {
        String sql = "UPDATE books SET stock =" + stock + " ,rentOkStock=" + stock + " WHERE bookId=" + SameBookId
                + ";";
        jdbcTemplate.update(sql);
    }

    public List<BookInfo> categorizeBook(String categoryText) {
        List<BookInfo> Book = jdbcTemplate.query(
                "SELECT * FROM books where category='" + categoryText + "';",
                new BookInfoRowMapper());
        return Book;
    }

}
