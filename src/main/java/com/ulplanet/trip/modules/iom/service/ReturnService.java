package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ReturnDao;
import com.ulplanet.trip.modules.iom.entity.*;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品租赁Service
 * Created by zhangxd on 15/12/17.
 */
@Service
public class ReturnService extends CrudService<ReturnDao, Return> {

    @Autowired
    private ReturnDao returnDao;
    @Autowired
    private RentService rentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private RentDetailService rentDetailService;
    @Autowired
    private CodeService codeService;

    public void saveReturn(Return re) {
        if (StringUtils.isBlank(re.getId())){
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_RETURN);
            re.preInsert();
            re.setCode(code);

            Rent rent = rentService.get(re.getRent().getId());
            rent.setStatus(1);

            List<RentDetail> rentDetails = rentDetailService.findList(new RentDetail(null, rent));
            for (RentDetail rentDetail : rentDetails) {
                String productId = rentDetail.getProduct().getId();
                String productDetailId = rentDetail.getProductDetail().getId();
                double amount = rentDetail.getAmount();

                Product product = productService.get(productId);
                if (StringUtils.isBlank(productDetailId)) {
                    product.setRsvAmt(product.getRsvAmt() + amount);
                    product.setAvlAmt(product.getAvlAmt() + amount);
                    product.setRentAmt(product.getRentAmt() - amount);
                } else {
                    ProductDetail productDetail = productDetailService.get(productDetailId);
                    productDetail.setStatus("2");

                    product.setRsvAmt(product.getRsvAmt() + 1);
                    product.setRentAmt(product.getRentAmt() - 1);
                    productDetailService.saveProductDetail(productDetail);
                }
                productService.saveProduct(product);
            }

            re.setRent(rent);
            returnDao.insert(re);
            rentService.saveRent(rent);
        }else{
            re.preUpdate();
            returnDao.update(re);
        }
    }
}
