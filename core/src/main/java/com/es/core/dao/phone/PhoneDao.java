package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    Optional<Phone> findPhoneByModel(String model);

    void save(Phone phone);

    List<Phone> findAll(SearchingParamObject paramObject);

    int count(String term);
}
