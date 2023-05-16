package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class PhoneDaoImplIT {

    @Value("${phones.per.page}")
    private int phonesPerPage;
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
    public void findAll_without_sorting_and_paging() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(Integer.MAX_VALUE)
                .term("")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

        List<Phone> phones = phoneDao.findAll(paramObject);

        assertEquals(10, phones.size());
    }

    @Test
    public void findAll_with_paging() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(phonesPerPage)
                .term("")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

        List<Phone> phones = phoneDao.findAll(paramObject);

        assertEquals(phonesPerPage, phones.size());
    }

    @Test
    public void findAll_with_sorting() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(phonesPerPage)
                .term("")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

        paramObject.setSortBy("model");

        List<Phone> phones = phoneDao.findAll(paramObject);
        List<Phone> sortedPhonesActual = phoneDao.findAll(paramObject);
        List<Phone> sortedPhonesExpected = phones.stream()
                .sorted(Comparator.comparing(Phone::getModel))
                .collect(Collectors.toList());

        for (int i = 0; i <= 6; i++) {
            assertEquals(sortedPhonesActual.get(i).getModel(), sortedPhonesExpected.get(i).getModel());
        }
    }

    @Test
    public void findAll_with_searching() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(Integer.MAX_VALUE)
                .term("")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

        String searchingCriteriaLower = "b";
        String searchingCriteriaUpper = "B";
        List<Phone> phones = phoneDao.findAll(paramObject);

        paramObject.setTerm(searchingCriteriaLower);

        List<Phone> searchedPhonesActual = phoneDao.findAll(paramObject);
        List<Phone> searchedPhonesExpected = phones.stream()
                .filter(ph -> ph.getModel().contains(searchingCriteriaLower)
                        || ph.getBrand().contains(searchingCriteriaLower)
                        || ph.getModel().contains(searchingCriteriaUpper)
                        || ph.getBrand().contains(searchingCriteriaUpper))
                .collect(Collectors.toList());

        searchedPhonesExpected.forEach(phone -> {
            assertTrue(phone.getModel().contains(searchingCriteriaLower)
                    || phone.getBrand().contains(searchingCriteriaLower)
                    || phone.getModel().contains(searchingCriteriaUpper)
                    || phone.getBrand().contains(searchingCriteriaUpper));
        });

        assertEquals(searchedPhonesExpected.size(), searchedPhonesActual.size());
        assertNotEquals(searchedPhonesExpected.size(), phones.size());
    }


    @Test
    public void count_without_searching() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(Integer.MAX_VALUE)
                .term("")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

       List<Phone> phones = phoneDao.findAll(paramObject);

        int count = phoneDao.count("");

        assertEquals(count, phones.size());
    }

    @Test
    public void count_with_searching() {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .offset(0)
                .phonesPerPage(Integer.MAX_VALUE)
                .term("b")
                .sortBy("id")
                .sortOrder("ASC")
                .build();

        List<Phone> phones = phoneDao.findAll(paramObject);

        int count = phoneDao.count("b");

        assertEquals(count, phones.size());
    }
}