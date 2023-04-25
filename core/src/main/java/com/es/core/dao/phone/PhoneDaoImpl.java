package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class PhoneDaoImpl implements PhoneDao {
    public static final String FIND_PHONE_BY_ID = "SELECT phones.*, colors.id AS colorId, colors.code AS colorCode FROM phones " +
            "LEFT JOIN phone2color ON phone2color.phoneId = phones.id " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId WHERE phones.id = ?";
    public static final String INSERT_PHONE = "INSERT INTO phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, " +
            "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
            "displayTechnology, backCameraMegapixels, frontCameraMegapixels, " +
            "ramGb, internalStorageGb, batteryCapacityMah, " +
            "talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "VALUES (:id, :brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, " +
            ":widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, " +
            ":displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, " +
            ":ramGb, :internalStorageGb, :batteryCapacityMah, " +
            ":talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    public static final String UPDATE_PHONE = "UPDATE phones SET brand = :brand, model = :model, price = :price, " +
            "displaySizeInches = :displaySizeInches, weightGr = :weightGr, lengthMm = :lengthMm, " +
            "widthMm = :widthMm, heightMm = :heightMm, announced = :announced, deviceType = :deviceType, " +
            "os = :os, displayResolution = :displayResolution, " +
            "pixelDensity = :pixelDensity, displayTechnology = :displayTechnology, " +
            "backCameraMegapixels = :backCameraMegapixels, frontCameraMegapixels = :frontCameraMegapixels, " +
            "ramGb = :ramGb, internalStorageGb = :internalStorageGb, batteryCapacityMah = :batteryCapacityMah, " +
            "talkTimeHours = :talkTimeHours, standByTimeHours = :standByTimeHours, bluetooth = :bluetooth, " +
            "positioning = :positioning, imageUrl = :imageUrl, description = :description WHERE id = :id";

    public static final String FIND_ALL_PHONES = "SELECT phones.*, colors.id AS colorId, colors.code AS colorCode FROM " +
            "(SELECT phones.* FROM phones " +
            "JOIN stocks ON stocks.phoneId = phones.Id AND stocks.stock > 0 AND phones.price > 0 " +
            "WHERE phones.brand ILIKE '%%%s%%' OR " +
            "phones.model ILIKE '%%%s%%' ORDER BY phones.%s %s " +
            "OFFSET ? LIMIT ?) phones " +
            "LEFT JOIN phone2color ON phone2color.phoneId = phones.id " +
            "LEFT JOIN colors ON colors.id = phone2color.colorId";

    public static final String GET_COUNT_ALL_PHONES = "SELECT COUNT(*) FROM phones " +
            "JOIN stocks ON phones.Id = stocks.phoneId AND stocks.stock - stocks.reserved > 0";

    public static final String GET_COUNT_PHONES_BY_TERM = "SELECT COUNT(*) FROM phones " +
            "JOIN stocks ON phones.Id = stocks.phoneId AND stocks.stock - stocks.reserved > 0 " +
            "AND phones.price > 0 " +
            "WHERE phones.price IS NOT NULL AND phones.brand ILIKE '%%%s%%' OR " +
            "phones.model ILIKE '%%%s%%'";

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
    }

    @Override
    public List<Phone> findAll(SearchingParamObject paramObject) {
        String query = String.format(FIND_ALL_PHONES, paramObject.getTerm(),
                paramObject.getTerm(),
                paramObject.getSortBy(),
                paramObject.getSortOrder());
        return jdbcTemplate.query(query, phoneResultSetExtractor, paramObject.getOffset(), paramObject.getPhonesPerPage());
    }

    @Override
    public int count(String term) {
        if (Objects.isNull(term) || term.isBlank()) {
            return jdbcTemplate.queryForObject(GET_COUNT_ALL_PHONES, Integer.class);
        } else {
            return jdbcTemplate.queryForObject(String.format(GET_COUNT_PHONES_BY_TERM, term, term), Integer.class);
        }

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
    }

}
