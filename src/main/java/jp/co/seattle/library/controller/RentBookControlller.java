package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBooksService;

/**
 * 貸し出しコントローラー
 */
@Controller
public class RentBookControlller {
    final static Logger logger = LoggerFactory.getLogger(RentBookControlller.class);

    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBooksService rentBooksService;

    /**
     * 
     * @param locale
     * @param bookId
     * @param userId
     * @param model
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("userId") int userId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome RentControler.java! The client locale is {}.", locale);
        //bookIdを元にbooksテーブルからrentOkStockカラムのデータを取得(ボタンを推せてるので0以上となる)
        int rentCount = rentBooksService.searchRentStock(bookId);
        //rentメソッド使う(rentテーブルにデータを追加)(rentBooksテーブルをしようしない)
        //rentBooksService.rentBook(bookId, userId);

        //rentCountから1冊分デクリメントし、booksテーブルのrentOkStockにセットする
        if (rentCount > 0) {
        rentCount -= 1;
        rentBooksService.insertRentStock(rentCount, bookId);
    }
        //bookIdを元に書籍名をbooksテーブルから取得
        String title = rentBooksService.searchTitle(bookId);
        //insertRentLogを使う（rentLogテーブルに借りた日付、タイトル、ユーザーを追加）
        rentBooksService.insertRentLog(bookId, title, userId);



        //rentOkStock（貸し出し可能在庫）の数を取得
        if (rentCount == 0) {
            model.addAttribute("RentingStatus", "貸し出し中");
            //booksテーブルのrentStatusに「貸し出し不可」を挿入
            rentBooksService.insertNgRentStatus(bookId);
        } else {
            model.addAttribute("RentingStatus", "貸し出し可");
            //booksテーブルのrentStatusに「貸し出し可」を挿入(デフォルトで貸し出し可なため省いてもOK)
            rentBooksService.insertOkRentStatus(bookId);
        }

        model.addAttribute("stockCount", booksService.searchStock(bookId));
        //        model.addAttribute("rentCount", rentCount);
        //        model.addAttribute("stockCount", booksService.searchStock(bookId));
        BookDetailsInfo newBookDetailsInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newBookDetailsInfo);

        return "details";
    }
}
