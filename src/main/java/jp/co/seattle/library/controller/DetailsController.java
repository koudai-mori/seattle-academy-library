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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBooksService;

/**
 * 詳細表示コントローラー
 */
@Controller
public class DetailsController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBooksService rentBooksService;

    /**
     * 詳細画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String detailsBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

        //貸し出し可能かどうかを判断する処理

        //        int rent = booksService.rentCheck(bookId);
        //rentOkStock（貸し出し可能在庫）の数を取得
        int rentCount = rentBooksService.searchRentStock(bookId);

        if (rentCount == 0) {
            model.addAttribute("RentingStatus", "貸し出し中");
        } else {
            model.addAttribute("RentingStatus", "貸し出し可");

        }

        //stock（在庫）の確認
        //        int stock = booksService.searchStock(bookId);
        //        model.addAttribute("stockCount", stock);
        //        model.addAttribute("rentCount", rentCount);

        return "details";
    }
}
