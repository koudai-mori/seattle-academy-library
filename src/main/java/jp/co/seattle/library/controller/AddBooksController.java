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
import jp.co.seattle.library.service.RentBooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class AddBooksController {
    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private RentBooksService rentBooksService;

    @Autowired
    private ThumbnailService thumbnailService;

    @RequestMapping(value = "/addBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "addBook";
    }

    /**
     * 書籍情報を登録する
     * @param locale ロケール情報
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
    @RequestMapping(value = "/insertBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,
            @RequestParam("category") int categoryNum,

            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        //RequestParamで受け取った番号をもとにカテゴリー名を付与する
        String categoryText = null;
        if (categoryNum == 1) {
            categoryText = "小説";
        } else if (categoryNum == 2) {
            categoryText = "随筆";
        } else if (categoryNum == 3) {
            categoryText = "啓蒙";
        } else if (categoryNum == 4) {
            categoryText = "漫画";
        } else if (categoryNum == 5) {
            categoryText = "図鑑";
        } else if (categoryNum == 6) {
            categoryText = "芸術関係";
        } else if (categoryNum == 7) {
            categoryText = "その他";
        }
        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setIsbn(isbn);
        bookInfo.setDescription(description);
        bookInfo.setCategory(categoryText);

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
                return "addBook";
            }
        }
        //必須項目が空だった場合、通らないようにする
        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {

        }
        // TODO 書籍情報を新規登録する

        //ヴァリデーションチェック
        boolean check = false;
        boolean isValidIsbn = isbn.matches("[0-9]{10}||[0-9]{13}");
        if (!isValidIsbn) {
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
            return "addBook";
        }

        //同じ本があるかチェックする、存在した場合stockカラムにインクリメント、存在しないbooksテーブルにデータを追加
        int SameBookCount = booksService.searchSameBook(title, author);
        int stock = 1;
        if (SameBookCount != 0) {
            int bookId = booksService.searchBookId(title, author);
            stock = booksService.searchStock(bookId);
            stock += 1;
            booksService.stockIncrement(stock, bookId);
            //stock(在庫数)とrentOkStock(貸し出し可能在庫)をjspに送信してあげる
            model.addAttribute("stockCount", stock);
            model.addAttribute("rentCount", rentBooksService.searchRentStock(bookId));
        } else if (SameBookCount == 0) {
            booksService.registBook(bookInfo);

        }

        model.addAttribute("resultMessage", "登録完了");

        // TODO 登録した書籍の詳細情報を表示するように実装
        //最新のbookIdを取得（最後に登録された情報＝上記で登録した情報となる）
        int AddBookId = booksService.latestID();
        //stock(在庫数)とrentOkStock(貸し出し可能在庫)をjspに送信してあげる
        model.addAttribute("stockCount", stock);
        model.addAttribute("rentCount", rentBooksService.searchRentStock(AddBookId));
        //上記で取得した情報を元にbooksテーブルのrentStatusに「貸し出し可」を挿入
        rentBooksService.insertOkRentStatus(AddBookId);

        BookDetailsInfo details = booksService.getBookInfo(AddBookId);

        model.addAttribute("bookDetailsInfo", details);
        //model.addAttribute("RentingStatus", "貸し出し可");

        //Bookdetaiklsinfoから取得して出力？
        //detail.jspを使うのでは
        //  詳細画面に遷移する
        return "details";
    }

}
