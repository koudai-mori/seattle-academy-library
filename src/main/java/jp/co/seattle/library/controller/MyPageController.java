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
import jp.co.seattle.library.service.UsersService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class MyPageController {
    final static Logger logger = LoggerFactory.getLogger(MyPageController.class);

    @Autowired
    private UsersService usersService;
    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBooksService rentBooksService;


    /**
     * ハンバーガーメニューからMyPageに遷移するページ
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String transitionMypage(Model model) {
        return "mypage";
    }

    @Transactional
    @RequestMapping(value = "/outPutLog", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("userId") int userId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome RentControler.java! The client locale is {}.", locale);
        //履歴表示のメソッド作る
        model.addAttribute("rentList", rentBooksService.selectRentLog(userId));


        return "mypage";
    }

}