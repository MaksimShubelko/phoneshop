package com.es.core.dao.color;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-core-test.xml")
public class ColorDaoImplIT {

    @Resource
    private ColorDao colorDao;

    @Resource
    private PhoneDao phoneDao;

    @Before
    public void setUp() {

    }

    @Test
    public void deleteAllFromPhone() {
        Phone phoneBeforeDelCol = phoneDao.get(1000L).stream().findFirst().orElse(new Phone());
        int countColBeforeDel = phoneBeforeDelCol.getColors().size();

        colorDao.deleteAllFromPhone(phoneBeforeDelCol);

        Phone phoneAfterDelCol = phoneDao.get(1000L).stream().findFirst().orElse(new Phone());
        int countColAfterDel = phoneAfterDelCol.getColors().size();

        assertTrue(countColBeforeDel > 0);
        assertEquals(0, countColAfterDel);
    }

    @Test
    public void insertAllIntoPhone() {
        Phone phoneBeforeInsertCol = phoneDao.get(1000L).stream().findFirst().orElse(new Phone());
        int countColBeforeInsert = phoneBeforeInsertCol.getColors().size();
        Color color = new Color();
        color.setId(1001L);
        color.setCode("White");
        phoneBeforeInsertCol.getColors().add(color);

        colorDao.insertAllIntoPhone(phoneBeforeInsertCol);

        Phone phoneAfterInsertCol = phoneDao.get(1000L).stream().findFirst().orElse(new Phone());
        int countColAfterInsert = phoneAfterInsertCol.getColors().size();

        assertTrue(countColBeforeInsert < countColAfterInsert);

    }
}