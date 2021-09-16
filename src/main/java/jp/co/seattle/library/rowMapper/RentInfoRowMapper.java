package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentInfo;

@Configuration
public class RentInfoRowMapper implements RowMapper<RentInfo> {

    @Override
    public RentInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
        RentInfo rentInfo = new RentInfo();
        rentInfo.setID(rs.getInt("ID"));
        rentInfo.setBookId(rs.getInt("bookId"));
        rentInfo.setUserId(rs.getInt("userId"));
        rentInfo.setRent_log(rs.getString("rent_log"));
        rentInfo.setReturn_log(rs.getString("return_log"));
        rentInfo.setTitle(rs.getString("title"));
        return rentInfo;
    }
}