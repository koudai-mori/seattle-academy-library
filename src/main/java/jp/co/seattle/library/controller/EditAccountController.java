package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.UsersService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class EditAccountController {
    final static Logger logger = LoggerFactory.getLogger(EditAccountController.class);

    @Autowired
    private UsersService usersService;
    @Autowired
    private BooksService booksService;

    /**
     * Homeボタンから認証に遷移するページ
     * @param model
     * @return
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String transitionAccountCheck(Model model) {
        return "accountCheck";
    }

    /**
     * アカウント認証画面に遷移
     * @param email メールアドレス
     * @param password パスワード
     * @param model
     * @return accountCheck アカウント認証画面
     */
    @RequestMapping(value = "/accountCheck", method = RequestMethod.POST)
    public String transitionEditAccount(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        //userIdも含めid,email,passwordをとってくる
        UserInfo selectedUserInfo = usersService.selectUserInfo(email, password);

        // TODO パスワードとメールアドレスの組み合わせ存在チェック実装
        //if文を使って上のselectedUserInfoで存在してなかったら認証アカウントに返す
        if (selectedUserInfo == null) {
            model.addAttribute("errorMessage", "アカウントが存在しません");
            return "accountCheck";
        }
        //認証時に打ってもらったメアドとパスを初期値として送ってあげる
        //userserviceで上記のメアド
        model.addAttribute("userInfo", selectedUserInfo);
        return "editAccount";
    }

    //アカウント情報を変更処理
    /**
     * アカウント修正画面に遷移
     * アカウント修正
     * @param userId ユーザーID
     * @param email メールアドレス
     * @param password パスワード
     * @param passwordForCheck 確認用パスワード
     * @param model
     * @return editAccount アカウント修正画面
     */
    @RequestMapping(value = "/editAccount", method = RequestMethod.POST)
    public String editAccount(
            @RequestParam("userId") int userId,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("passwordForCheck") String passwordForCheck,
            Model model) {
        UserInfo userInfo = new UserInfo();

        //バリデーションチェック（両方前回のものと同じではないか）
        String[] userInfomation = usersService.pickUpUserInfo(userId);
        if (email.equals(userInfomation[0]) && password.equals(userInfomation[1])) {
            model.addAttribute("att3", "登録されている情報と同じです");
            return "editAccount";
        }

        // TODO バリデーションチェック(正しい文字かどうか)、パスワード一致チェック実装
        boolean isValidEmail = email
                .matches("^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$");
        boolean isValidPass = password.matches("^[A-Za-z0-9]+$");
        boolean isValidPassCH = passwordForCheck.matches("^[A-Za-z0-9]+$");

        if (!isValidEmail || !isValidPass || !isValidPassCH) {
            model.addAttribute("att1", "半角文字ではないものが入っています");
            return "editAccount";
        }
        //パスワードが再パスと一緒かどうか
        if (!(password.equals(passwordForCheck))) {
            model.addAttribute("att3", "パスワードと確認用パスワードが一致しません");
            return "editAccount";
        }

        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setUserId(userId);

        //MySQLのデータも更新,userIdが入っていない
        usersService.userInfoUpdate(userInfo);

        model.addAttribute("att3", "更新完了");
        return "editAccount";
    }

}
