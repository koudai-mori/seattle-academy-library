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
 * 返却コントローラー
 */
@Controller
public class ReturnBookControlller {
    final static Logger logger = LoggerFactory.getLogger(ReturnBookControlller.class);

    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBooksService rentBooksService;

    /**
     * 
     * @param locale
     * @param bookId
     * @param model
     */
    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("userId") int userId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome ReturnBookControler.java! The client locale is {}.", locale);
        //returnメソッド使う(rentテーブルからデータをremove)
        //rentBooksService.returnBook(bookId, userId);(rentBooksテーブルを使用しない)

        //bookIdを元にbooksテーブルからrentOkStockカラムのデータを取得
        int rentCount = rentBooksService.searchRentStock(bookId);

        //rentCountから1冊分インリメントし、booksテーブルのrentOkStockにセットする
        rentCount += 1;
        rentBooksService.insertRentStock(rentCount, bookId);
        //insertReturntLogを使う（rentLogテーブルに返した日付を追加）
        rentBooksService.insertReturnLog(bookId, userId);

        if (rentCount == 0) {
            model.addAttribute("RentingStatus", "貸し出し中");
            //booksテーブルのrentStatusに「貸し出し不可」を挿入
            rentBooksService.insertNgRentStatus(bookId);
        } else {
            model.addAttribute("RentingStatus", "貸し出し可");
            //booksテーブルのrentStatusに「貸し出し可」を挿入(デフォルトで貸し出し可なため省いてもOK)
            rentBooksService.insertOkRentStatus(bookId);
        }

        BookDetailsInfo newBookDetailsInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newBookDetailsInfo);
        //        model.addAttribute("stockCount", booksService.searchStock(bookId));
        //        model.addAttribute("rentCount", rentCount);

        return "details";
    }
}
