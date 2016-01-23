package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductDiscardDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.ProductDiscard;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品报废Service
 * Created by zhangxd on 15/12/15.
 */
@Service
public class ProductDiscardService extends CrudService<ProductDiscardDao, ProductDiscard> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductDiscardDao productDiscardDao;
    @Autowired
    private CodeService codeService;
    @Autowired
    private ProductCalService productCalService;

    public void saveProductDiscard(ProductDiscard productDiscard) {
        if (StringUtils.isBlank(productDiscard.getId())){
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_DISCARD, "");
            productDiscard.preInsert();
            productDiscard.setCode(code);

            String productDetailId = productDiscard.getProductDetail().getId();
            if (StringUtils.isNotBlank(productDetailId)) {
                //明细产品报废
                ProductDetail productDetail = productDetailService.get(productDetailId);

                productDiscard.setAmount(1);
                productDetail.setStatus(ProductDetail.STATUS_DISCARD);

                productDiscard.setProductDetail(productDetail);
                productDetailService.save(productDetail);

                productCalService.updateAmount(productDiscard.getProduct().getId(), true);
            } else {
                Product product = productService.get(productDiscard.getProduct().getId());
                product.setTotalAmt(product.getTotalAmt() - productDiscard.getAmount());
                product.setRsvAmt(product.getRsvAmt() - productDiscard.getAmount());
                product.setAvlAmt(product.getAvlAmt() - productDiscard.getAmount());
                productDiscard.setProduct(product);
                productService.save(product);
            }

            productDiscardDao.insert(productDiscard);
        } else {
            productDiscard.preUpdate();
            productDiscardDao.update(productDiscard);
        }
    }
}
