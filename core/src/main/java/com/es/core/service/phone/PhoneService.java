package com.es.core.service.phone;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> findAll(int offset, String sortingField, String sortingType, String term);
}
