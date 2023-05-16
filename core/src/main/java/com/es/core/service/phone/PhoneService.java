package com.es.core.service.phone;

import com.es.core.dao.phone.SearchingParamObject;
import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> findAll(SearchingParamObject paramObject);

    int getCountPages(String term);

    Phone getById(Long phoneId);
}
