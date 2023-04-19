package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Value("${phones.per.page}")
    private int phonesPerPage;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> findAll(int selectedPage, String sortingField, String sortingType, String term) {
        int offset = (selectedPage - 1) * phonesPerPage;

        return phoneDao.findAll(offset, phonesPerPage, getParams(term, sortingField, sortingType));
    }

    public int getCountPages(String term) {
        return phoneDao.count(term) / phonesPerPage;
    }

    private String[] getParams(String term, String sortingField, String sortingType) {
        final String defaultSortingField = "id";
        String sortingTypeValid = getSortingType(sortingType);
        if (Objects.isNull(term)) {
            term = "";
        }

        String[] defaultParams = new String[] {term, term, defaultSortingField, sortingTypeValid};
        if (Objects.isNull(sortingField)) {
            return defaultParams;
        }

        switch (sortingField) {
            case "model":
            case "brand":
                return new String[] {term, term, sortingField, sortingTypeValid};
            default:
                return defaultParams;
        }
    }

    private String getSortingType(String sortingType) {
        if (Objects.isNull(sortingType) || sortingType.equalsIgnoreCase("asc")) {
            return "ASC";
        }

        if (sortingType.equalsIgnoreCase("desc")) {
            return "DESC";
        }

        return "ASC";
    }

}
