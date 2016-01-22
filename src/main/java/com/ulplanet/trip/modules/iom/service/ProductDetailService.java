package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductDetailDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.ProductIn;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品明细Service
 * Created by zhangxd on 15/12/01.
 */
@Service
public class ProductDetailService extends CrudService<ProductDetailDao, ProductDetail> {

    @Autowired
    private ProductDetailDao productDetailDao;
    @Autowired
    private ProductInService productInService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CodeService codeService;

    public void saveProductDetail(ProductDetail productDetail) {
        if (StringUtils.isBlank(productDetail.getId())) {
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_DETAIL, productDetail.getProduct().getCode());
            productDetail.setCode(code);
            productDetail.preInsert();
            productDetailDao.insert(productDetail);
        } else {
            productDetail.preUpdate();
            productDetailDao.update(productDetail);
        }
    }

    public void saveProductDetailIn(String inId, ProductDetail productDetail) {
        if (StringUtils.isBlank(productDetail.getId())) {

            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_DETAIL, productDetail.getProduct().getCode());
            productDetail.setCode(code);
            productDetail.setStatus("1");
            productDetail.preInsert();

            ProductIn productIn = productInService.get(inId);
            Product product = productService.get(productDetail.getProduct().getId());

            productIn.setAmount(productIn.getAmount() + 1);

            product.setTotalAmt(product.getTotalAmt() + 1);
            product.setRsvAmt(product.getRsvAmt() + 1);
            productDetail.setProduct(product);

            productInService.saveProductIn(productIn);
            productService.saveProduct(product);
            productDetailDao.insert(productDetail);
            productInService.insertProDetail(inId, productDetail.getId());
        }
    }

    public void saveTestStatus(ProductDetail productDetail) {
        productDetail.preUpdate();
        productDetail.setStatus("2");
        productDetailDao.update(productDetail);
    }

    public void saveSaleStatus(ProductDetail productDetail) {
        productDetail.preUpdate();
        productDetail.setStatus("3");

        Product product = productService.get(productDetail.getProduct().getId());
        product.setAvlAmt(product.getAvlAmt() + 1);
        productDetail.setProduct(product);

        productDetailDao.update(productDetail);
        productService.saveProduct(product);
    }

    public List<ProductDetail> findAvlList(ProductDetail productDetail) {
        return productDetailDao.findAvlList(productDetail);
    }

    public Page<ProductDetail> findInDetail(Page<ProductDetail> page, ProductDetail productDetail, String inId) {
        productDetail.setPage(page);
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("inId", inId);
        productDetail.setSqlMap(sqlMap);
        page.setList(dao.findInDetailList(productDetail));
        return page;
    }

    public ProductDetail getDetailByDevice(ProductDetail productDetail) {
        return productDetailDao.findByDevice(productDetail);
    }
}
