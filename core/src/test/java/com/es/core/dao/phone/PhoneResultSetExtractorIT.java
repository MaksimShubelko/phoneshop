package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class PhoneResultSetExtractorIT {

    public static final String FIND_ALL_PHONES = "select phones.*, colors.id as colorId, colors.code as colorCode from phones " +
            "left join phone2color on phone2color.phoneId = phones.id " +
            "left join colors on colors.id = phone2color.colorId " +
            "offset ? limit ?";

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ResultSetExtractor<List<Phone>> resultSetExtractor;

    @Before
    public void setUp() throws Exception {
        resultSetExtractor = new PhoneResultSetExtractor();
    }

    @Test
    public void extractData_with_colors() {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL_PHONES, resultSetExtractor, 0, 5);

        phones.stream()
                .map(Phone::getColors)
                .forEach(clrs -> assertFalse(clrs.isEmpty()));
    }
    @Test
    public void extractData_without_colors() {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL_PHONES, resultSetExtractor, 5, 6);

        phones.stream()
                .map(Phone::getColors)
                .forEach(clrs -> assertTrue(clrs.isEmpty()));

    }
}