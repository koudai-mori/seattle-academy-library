package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class BulkRegistContoller {
    final static Logger logger = LoggerFactory.getLogger(BulkRegistContoller.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    @RequestMapping(value = "/bulkRegist", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String bulk(Model model) {
        return "bulkRegist";
        }

    /**
     *書籍情報を一括登録する
     * @param locale ロケール情報
     * @param bulk csvfile
     * @param model モデル
     * @return 一括登録画面
     * @throws IOException 
     * @throws ParseException
     * @throes FileNotFoundException
     * 
     */
    @Transactional
    @RequestMapping (value = "/allRegist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String registbook(Locale locale,
            @RequestParam("bulk") MultipartFile bulk,
            Model model) throws IOException {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);
        //全書籍を入れるリスト
        ArrayList<String[]> bookInfos = new ArrayList<String[]>();
        ArrayList<String> errorList = new ArrayList<String>();
        String[] bookData = new String[6];

        try {
            InputStream stream = bulk.getInputStream();
            Reader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                bookData = line.split(",", -1);
                bookInfos.add(bookData);
                //バリデーションチェック
                if (bookData[0].isEmpty() || bookData[1].isEmpty() || bookData[2].isEmpty()) {
                    errorList.add(bookInfos.size() + "行目のタイトル、著者、出版社が適切ではありません");
                }
                boolean isValidIsbn = bookData[4].matches("[0-9]{10}||[0-9]{13}");
                if (!isValidIsbn) {
                    errorList.add(bookInfos.size() + "行目のISBNが適切ではありません");
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
                    sdf.setLenient(false);
                    sdf.parse(bookData[3]);
                   
                } catch (ParseException pe) {
                    errorList.add(bookInfos.size() + "行目の出版日は半角英数のYYYYMMDD形式で入力してください");
                }
                //どっかでファイルのクローズしなあかん    
                //while分の終わり
                }
                br.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
          System.out.println(e);
        }
        if (errorList.size()!=0) {
            model.addAttribute("error1", errorList);
            return "bulkRegist";
        }
        for(int i=0;i<bookInfos.size();i++) {

            BookDetailsInfo bookInfo = new BookDetailsInfo();
            bookInfo.setTitle(bookInfos.get(i)[0]);
            bookInfo.setAuthor(bookInfos.get(i)[1]);
            bookInfo.setPublisher(bookInfos.get(i)[2]);
            bookInfo.setPublishDate(bookInfos.get(i)[3]);
            bookInfo.setIsbn(bookInfos.get(i)[4]);
            bookInfo.setDescription(bookInfos.get(i)[5]);
            
            booksService.registBook(bookInfo);
        }
        model.addAttribute("bulkRegist", "一括登録完了");
        return "bulkRegist";
    }
    }