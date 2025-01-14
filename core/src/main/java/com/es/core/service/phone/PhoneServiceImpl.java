package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.dao.phone.SearchingParamObject;
import com.es.core.exception.UnknownProductException;
import com.es.core.model.phone.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PhoneServiceImpl implements PhoneService {

    @Value("${phones.per.page}")
    private int phonesPerPage;

    private final PhoneDao phoneDao;

    public void setPhonesPerPage(int phonesPerPage) {
        this.phonesPerPage = phonesPerPage;
    }

    @Override
    public List<Phone> findAll(SearchingParamObject paramObject) {
        int offset = (paramObject.getPage() - 1) * phonesPerPage;
        paramObject.setOffset(offset);
        paramObject.setPhonesPerPage(phonesPerPage);

        return phoneDao.findAll(paramObject);
    }

    @Override
    public int getCountPages(String term) {
        return phoneDao.count(term) / phonesPerPage;
    }

    @Override
    public Phone getById(Long phoneId) {
        return phoneDao.get(phoneId).orElseThrow(UnknownProductException::new);
    }

    @Override
    public Phone findPhoneByModel(String model) {
        return phoneDao.findPhoneByModel(model).orElseThrow(UnknownProductException::new);
    }
}
