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

/**
 * 検索コントローラー
 */
@Controller
public class SearchBookController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;

    /**
     * @param locale
     * @param searchText 検索ワード
     * @param model
     * @return　ホーム画面に検索結果を戻す
     */
    @Transactional
    @RequestMapping(value = "/searchLikeBook", method = RequestMethod.POST)
    public String searchBook(Locale locale,
            @RequestParam("search") String searchText,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome seachBokkControler.java! The client locale is {}.", locale);
        model.addAttribute("bookList", booksService.searchLikeBook(searchText));
        return "home";
    }
}
