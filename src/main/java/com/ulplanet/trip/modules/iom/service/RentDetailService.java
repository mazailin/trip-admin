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
    @Autowired
    private ProductCalService productCalService;

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

    public void saveNReturn(RentDetail rentDetail) {
        String productId = rentDetail.getProduct().getId();
        double oldReturnAmount = rentDetail.getOldReturnAmount();
        double returnAmount = rentDetail.getReturnAmount();

        Product product = productService.get(productId);
        product.setRsvAmt(product.getRsvAmt() - oldReturnAmount + returnAmount);
        product.setAvlAmt(product.getAvlAmt() - oldReturnAmount + returnAmount);
        product.setRentAmt(product.getRentAmt() + oldReturnAmount - returnAmount);
        productService.saveProduct(product);
        rentDetailDao.updateReturnAmount(rentDetail);
    }

    public void saveYReturn(RentDetail rentDetail) {
        String productId = rentDetail.getProduct().getId();
        String productDetailId = rentDetail.getProductDetail().getId();

        ProductDetail productDetail = productDetailService.get(productDetailId);
        productDetail.setStatus(ProductDetail.STATUS_TESTED);

        productDetailService.saveProductDetail(productDetail);
        productCalService.updateAmount(productId);

        rentDetail.setReturnAmount(1.0);
        rentDetailDao.updateReturnAmount(rentDetail);
    }
}
