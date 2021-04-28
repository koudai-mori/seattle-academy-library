package jp.co.seattle.library.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class EditBooksController {
    final static Logger logger = LoggerFactory.getLogger(EditBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    /**
     * 編集画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */

    @Transactional
    @RequestMapping(value = "/editBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String editbook(Locale locale,
            @RequestParam("bookId") int bookId,
            Model model){
        logger.info("Welcome edit! The client locale is {}.", locale);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        return "editBook";
    }
    

    /**
     * 書籍情報を編集する
     * @param locale ロケール情報
     * @parma bookId bookid
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param publishdate 出版日
     * @param isbn ISBN
     * @param description 説明
     * @param file サムネイルファイル
     * @param model モデル
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,

            Model model) {
        logger.info("Welcome editBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setBookId(bookId);
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setIsbn(isbn);
        bookInfo.setDescription(description);

        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "editBook";
            }
        }

        // 書籍情報を新規登録する

        //ヴァリデーションチェック
        //後ここだけ
        boolean isValidIsbn = isbn.matches("^[0-9]+$");
        boolean check = false;
        //↑１行目でisbnが数字かどうかのチェック、
        StringBuilder sb = new StringBuilder(isbn);
        if (!isValidIsbn||sb.length() != 10 && sb.length() != 13 ) {
            check = true;
            model.addAttribute("errorMsgIsbn", "ISBNの桁数または半角英数が正しくありません");
            
        }
        //日付のチェック
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
            sdf.setLenient(false);
            sdf.parse(publishDate);
           
        } catch (ParseException pe) {
            check = true;
            model.addAttribute("errorMsgDate", "出版日は半角英数のYYYYMMDD形式で入力してください");
        }
        if (check) {
            return "editBook";
        }
        booksService.editbook(bookInfo);
        //int bookDetailsInfo = booksService.();
        //↑IDもらったお
        BookDetailsInfo newBookDetailsInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newBookDetailsInfo);
        
        return "details";
    }

}
