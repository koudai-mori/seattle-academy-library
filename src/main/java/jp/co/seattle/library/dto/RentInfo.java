package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * ユーザー情報格納DTO
 *
 */
@Configuration
@Data
public class RentInfo {
    private int ID;

    private int bookId;

    private int userId;

    private String rent_log;

    private String return_log;

    private String title;

    public RentInfo() {

    }

    public RentInfo(int ID, int bookId, int userId, String rent_log, String return_log, String title) {
        this.ID = ID;
        this.bookId = bookId;
        this.userId = userId;
        this.rent_log = rent_log;
        this.rent_log = return_log;
        this.title = title;
    }

}