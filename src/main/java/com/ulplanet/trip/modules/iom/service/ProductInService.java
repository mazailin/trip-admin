package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductInDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.ProductIn;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品入库Service
 * Created by zhangxd on 15/12/11.
 */
@Service
public class ProductInService extends CrudService<ProductInDao, ProductIn> {

    @Autowired
    private ProductInDao productInDao;
    @Autowired
    private CodeService codeService;
    @Autowired
    private ProductService productService;

    public void saveProductIn(ProductIn productIn) {
        if (StringUtils.isBlank(productIn.getId())) {
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_IN);
            productIn.setCode(code);
            productIn.preInsert();

            Product product = productService.get(productIn.getProduct().getId());
            double buyAmount = productIn.getBuyAmount();
            double price = productIn.getPrice();

            double totalPrice = (double) Math.round(buyAmount * price * 100) / 100;

            double productAmount = product.getTotalAmt() + buyAmount;
            double avgPrice = (double) Math.round((product.getAvgPrice() * product.getTotalAmt() + totalPrice) * 100 / productAmount) / 100;

            productIn.setTotalPrice(totalPrice);

            product.setAvgPrice(avgPrice);
            if (!"1".equals(product.getUseDetail())) {
                productIn.setAmount(buyAmount);
                product.setTotalAmt(productAmount);
                product.setRsvAmt(product.getRsvAmt() + buyAmount);
                product.setAvlAmt(product.getAvlAmt() + buyAmount);
            }

            productIn.setProduct(product);
            productInDao.insert(productIn);
            productService.saveProduct(product);

        } else {
            productIn.preUpdate();
            productInDao.update(productIn);
        }
    }

    void insertProDetail(String inDetailId, String proDetailId) {
        Parameter parameter = new Parameter(new Object[][]{{"inDetailId", inDetailId}, {"proDetailId", proDetailId}});
        productInDao.insertProDetail(parameter);
    }

    public List<ProductDetail> findDetail(String id) {
        return productInDao.findDetail(id);
    }
}
