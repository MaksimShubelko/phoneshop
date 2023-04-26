package com.es.core.dao.color;

import com.es.core.model.phone.Phone;

public interface ColorDao {

    void insertAllIntoPhone(Phone phone);

    void deleteAllFromPhone(Phone phone);
}
