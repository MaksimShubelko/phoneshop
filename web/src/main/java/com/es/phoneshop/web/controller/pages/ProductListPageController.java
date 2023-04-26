package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneServiceImpl;
import com.es.core.dao.phone.SearchingParamObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneServiceImpl phoneService;

    @Resource
    private CartService cartService;

    @GetMapping
    public String showProductList(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "sortingField", required = false) String sortingField,
                                  @RequestParam(value = "sortingType", required = false) String sortingType,
                                  @RequestParam(value = "term", required = false) String term,
                                  Model model) {
        SearchingParamObject paramObject = SearchingParamObject.newBuilder()
                .page(page)
                .sortBy(sortingField)
                .sortOrder(sortingType)
                .term(term)
                .build();
        model.addAttribute("phones", phoneService.findAll(paramObject));
        model.addAttribute("selectedPage", page);
        model.addAttribute("sortingField", sortingField);
        model.addAttribute("sortingType", sortingType);
        model.addAttribute("pagesTotal", phoneService.getCountPages(term));
        model.addAttribute("term", term);
        model.addAttribute("cart", cartService.getCart());

        return "productList";
    }

}
