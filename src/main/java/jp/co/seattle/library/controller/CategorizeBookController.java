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
public class CategorizeBookController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;

    /**
     * @param locale
     * @param categoy 検索ワード
     * @param model
     * @return　ホーム画面に検索結果を戻す
     */
    @Transactional
    @RequestMapping(value = "/categorizeBook", method = RequestMethod.POST)
    public String categorizeBook(Locale locale,
            @RequestParam("category") int categoryNum,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome seachBokkControler.java! The client locale is {}.", locale);
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
        //カテゴリー別で検索した結果をhome画面に送ってあげる
        model.addAttribute("bookList", booksService.categorizeBook(categoryText));
        return "home";
    }
}
