package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.RentDetailDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.RentDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品租赁明细Service
 * Created by zhangxd on 15/12/17.
 */
@Service
public class RentDetailService extends CrudService<RentDetailDao, RentDetail> {

    @Autowired
    private RentDetailDao rentDetailDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

    public void saveRentDetail(RentDetail rentDetail) {
        if (StringUtils.isBlank(rentDetail.getId())){
            rentDetail.preInsert();

            Product product = productService.get(rentDetail.getProduct().getId());
            product.setRsvAmt(product.getRsvAmt() - rentDetail.getAmount());
            product.setAvlAmt(product.getAvlAmt() - rentDetail.getAmount());
            product.setRentAmt(product.getRentAmt() + rentDetail.getAmount());

            rentDetail.setProduct(product);
            rentDetailDao.insert(rentDetail);
            productService.saveProduct(product);
        }
    }

    public void saveYRentDetail(RentDetail rentDetail) {
        if (StringUtils.isBlank(rentDetail.getId())){
            rentDetail.preInsert();

            ProductDetail productDetail = productDetailService.get(rentDetail.getProductDetail().getId());
            productDetail.setStatus(ProductDetail.STATUS_RENTED);

            Product product = productService.get(productDetail.getProduct().getId());
            product.setRsvAmt(product.getRsvAmt() - 1);
            product.setAvlAmt(product.getAvlAmt() - 1);
            product.setRentAmt(product.getRentAmt() + 1);


            rentDetail.setProduct(product);
            rentDetail.setProductDetail(productDetail);
            rentDetailDao.insert(rentDetail);
            productService.saveProduct(product);
            productDetailService.saveProductDetail(productDetail);
        }
    }
}
