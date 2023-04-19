package com.es.core.dao.color;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ColorDaoImpl implements ColorDao {

    public static final String DELETE_PHONE_COLORS = "DELETE FROM phone2color WHERE phoneId = ?";

    public static final String INSERT_PHONES_COLORS_FOR_PHONE_BY_ID = "INSERT INTO phone2color (phoneId, colorId) " +
            "VALUES (?, ?)";
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteAllFromPhone(Phone phone) {
        jdbcTemplate.update(DELETE_PHONE_COLORS, phone.getId());
    }

    @Override
    public void insertAllIntoPhone(Phone phone) {
        Long phoneId = phone.getId();
        Set<Color> colors = phone.getColors();

        List<Object[]> phoneColors = colors.stream()
                .map(cl -> new Object[]{phoneId, cl.getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(INSERT_PHONES_COLORS_FOR_PHONE_BY_ID, phoneColors);
    }
}
