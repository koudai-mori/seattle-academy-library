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

/**
 * 貸し出しコントローラー
 */
@Controller
public class RentBookControlller {
    final static Logger logger = LoggerFactory.getLogger(RentBookControlller.class);

    @Autowired
    private BooksService booksService;

    /**
     * 
     * @param locale
     * @param bookId
     * @param model
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") int bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome RentControler.java! The client locale is {}.", locale);
        //rentメソッド使う
        booksService.rentBook(bookId);

        BookDetailsInfo newBookDetailsInfo = booksService.getBookInfo(bookId);
        model.addAttribute("bookDetailsInfo", newBookDetailsInfo);
        model.addAttribute("RentingStatus", "貸し出し中");

        return "details";
    }
}
