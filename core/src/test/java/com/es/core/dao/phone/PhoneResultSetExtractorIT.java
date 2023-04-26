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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class PhoneResultSetExtractorIT {

    public static final String FIND_ALL_PHONES = "SELECT phones.*, colors.id AS colorId, colors.code AS colorCode FROM phones " +
            "LEFT JOIN phone2color ON phone2color.phoneId = phones.id " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId " +
            "OFFSET ? LIMIT ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

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