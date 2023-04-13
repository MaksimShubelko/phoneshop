package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class PhoneDaoImplIT {

    @Resource
    private PhoneDao phoneDao;

    private Phone phoneTest;

    @Before
    public void setUp() throws Exception {
        phoneTest = new Phone();

        phoneTest.setBrand("test_brand");
        phoneTest.setModel("test_model");
        phoneTest.setPrice(BigDecimal.TEN);
    }

    @Test
    public void getPhoneById_exists() {
        Optional<Phone> phone;
        for (long i = 1000; i <= 1010; i++) {
            phone = phoneDao.get(i);

            assertTrue(phone.isPresent());
        }
    }

    @Test
    public void getPhoneById_not_exists() {
        Optional<Phone> phone;
        phone = phoneDao.get(Long.MAX_VALUE);

        assertFalse(phone.isPresent());
    }

    @Test
    public void save_new_phone() {
        phoneDao.save(phoneTest);
        Long generatedId = phoneTest.getId();

        assertNotNull(generatedId);
    }

    @Test
    public void save_updated_phone() {
        Optional<Phone> phone;
        String initialModel = phoneTest.getModel();
        String updatedModel = null;

        phoneDao.save(phoneTest);
        Long generatedId = phoneTest.getId();

        phoneTest.setModel("new_model");
        phoneDao.save(phoneTest);

        phone = phoneDao.get(generatedId);

        if (phone.isPresent()) {
            updatedModel = phone.get().getModel();
        }

        assertNotNull(generatedId);
        assertTrue(phone.isPresent());
        assertNotNull(updatedModel);
        assertNotEquals(initialModel, updatedModel);
    }

    @Test
    public void findAll_without_paging() {
        int expectedListSize = 11;
        int offset = 0;
        int limit = Integer.MAX_VALUE;

        List<Phone> phones = phoneDao.findAll(offset, limit);

        assertEquals(expectedListSize, phones.size());
    }
    @Test
    public void findAll_with_paging() {
        int expectedListSize = 10;
        int offset = 0;
        int limit = 10;

        List<Phone> phones = phoneDao.findAll(offset, limit);

        assertEquals(expectedListSize, phones.size());
    }

    @Test
    public void findAll_with_too_big_offset() {
        int expectedListSize = 0;
        int offset = Integer.MAX_VALUE;
        int limit = 10;

        List<Phone> phones = phoneDao.findAll(offset, limit);

        assertEquals(expectedListSize, phones.size());
    }
}