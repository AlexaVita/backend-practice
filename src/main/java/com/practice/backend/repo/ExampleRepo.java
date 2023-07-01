package com.practice.backend.repo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExampleRepo {
    private final DataSource dataSource;
    public ExampleRepo(@Qualifier("hikariPostgresDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<String> getNumbers() throws SQLException {
        List<String> numbers = new ArrayList<>();
        String sqlQuery = "SELECT number FROM example";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQuery);
             ResultSet set = ps.executeQuery()) {

            while (set.next()) {
                String number = set.getString("number");
                numbers.add(number);
            }
        }
        return numbers;
    }
}
