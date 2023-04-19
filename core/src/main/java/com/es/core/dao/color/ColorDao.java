package com.es.core.dao.color;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;

import java.util.Set;

public interface ColorDao {

    void insertAllIntoPhone(Phone phone);

    void deleteAllFromPhone(Phone phone);
}
