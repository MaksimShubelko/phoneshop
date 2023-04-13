package com.es.core.dao.phone;

import com.es.core.model.phone.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PhoneDaoImpl implements PhoneDao {
    public static final String FIND_PHONE_BY_ID = "select phones.*, colors.id as colorId, colors.code as colorCode from phones " +
            "left join phone2color on phone2color.phoneId = phones.id " +
            "left join colors on colors.id = phone2color.colorId where phones.id = ?";
    public static final String INSERT_PHONE = "insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, " +
            "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
                        "displayTechnology, backCameraMegapixels, frontCameraMegapixels, " +
                        "ramGb, internalStorageGb, batteryCapacityMah, " +
                        "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
                        "values (:id, :brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, " +
                        ":widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, " +
                        ":displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, " +
                        ":ramGb, :internalStorageGb, :batteryCapacityMah, " +
                        ":talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    public static final String UPDATE_PHONE = "update phones set brand = :brand, model = :model, price = :price, " +
            "displaySizeInches = :displaySizeInches, weightGr = :weightGr, lengthMm = :lengthMm, " +
            "widthMm = :widthMm, heightMm = :heightMm, announced = :announced, deviceType = :deviceType, " +
            "os = :os, displayResolution = :displayResolution, " +
            "pixelDensity = :pixelDensity, displayTechnology = :displayTechnology, " +
            "backCameraMegapixels = :backCameraMegapixels, frontCameraMegapixels = :frontCameraMegapixels, " +
            "ramGb = :ramGb, internalStorageGb = :internalStorageGb, batteryCapacityMah = :batteryCapacityMah, " +
            "talkTimeHours = :talkTimeHours, standByTimeHours = :standByTimeHours, bluetooth = :bluetooth, " +
            "positioning = :positioning, imageUrl = :imageUrl, description = :description where id = :id";
    public static final String DELETE_PHONE_COLORS = "delete from phone2color where phoneId = ?";

    public static final String INSERT_PHONES_COLORS_FOR_PHONE_BY_ID = "insert into phone2color (phoneId, colorId) " +
            "values (?, ?)";

    public static final String FIND_ALL_PHONES = "select phones.*, colors.id as colorId, colors.code as colorCode from phones " +
            "left join phone2color on phone2color.phoneId = phones.id " +
            "left join colors on colors.id = phone2color.colorId " +
            "offset ? limit ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private ResultSetExtractor<List<Phone>> phoneResultSetExtractor;

    @Override
    public Optional<Phone> get(Long key) {
        List<Phone> phones = jdbcTemplate.query(FIND_PHONE_BY_ID, phoneResultSetExtractor, key);

        if (phones.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(phones.get(0));
    }

    @Override
    public void save(Phone phone) {
        if (Objects.isNull(phone.getId())) {
            insert(phone);
        } else {
            update(phone);
        }
        insertColors(phone);
    }

    @Override
    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL_PHONES, phoneResultSetExtractor, new Object[]{offset, limit});
        return phones;
    }

    private void insert(Phone phone) {
        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(phone);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_PHONE, namedParamsPhone, keyHolder);
        phone.setId(keyHolder.getKey().longValue());
    }

    private void update(Phone phone) {
        SqlParameterSource namedParamsPhone = new BeanPropertySqlParameterSource(phone);
        namedParameterJdbcTemplate.update(UPDATE_PHONE, namedParamsPhone);
        deleteColors(phone);
    }

    private void deleteColors(Phone phone) {
        jdbcTemplate.update(DELETE_PHONE_COLORS, phone.getId());
    }

    private void insertColors(Phone phone) {
        Long phoneId = phone.getId();
        Set<Color> colors = phone.getColors();

        List<Object[]> phoneColors = colors.stream()
                .map(cl -> new Object[]{phoneId, cl.getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(INSERT_PHONES_COLORS_FOR_PHONE_BY_ID, phoneColors);
    }

}
