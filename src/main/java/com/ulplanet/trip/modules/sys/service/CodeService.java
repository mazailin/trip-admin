package com.ulplanet.trip.modules.sys.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.sys.dao.CodeDao;
import com.ulplanet.trip.modules.sys.entity.Code;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 编码生成Service
 * Created by zhangxd on 15/11/23.
 */
@Service
public class CodeService extends CrudService<CodeDao, Code> {

    public static final String CODE_TYPE_GROUP_USER         = "1";
    public static final String CODE_TYPE_PRODUCT_DETAIL     = "2";
    public static final String CODE_TYPE_PRODUCT_IN         = "3";
    public static final String CODE_TYPE_PRODUCT_DISCARD    = "4";
    public static final String CODE_TYPE_PRODUCT_RENT       = "5";
    public static final String CODE_TYPE_PRODUCT_RETURN     = "6";

    @Autowired
    private CodeDao codeDao;

    public String getCode(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        String lock;
        synchronized (this) {
            lock = type.intern();
        }
        synchronized (lock) {
            return _getCode(type);
        }
    }

    private String _getCode(String type) {

        Code code = codeDao.getByType(new Code(NumberUtils.toInt(type, 0)));

        if (code == null) {
            return null;
        }

        String prefix = Objects.toString(code.getPrefix(), "").trim();
        Integer numLength = Integer.valueOf(Objects.toString(code.getNumLength(), "4").trim());
        String currNo = Objects.toString(code.getNextNum(), "1").trim();
        Integer useDate = Integer.valueOf(Objects.toString(code.getUseDate(), "1").trim());
        String dateFormat = Objects.toString(code.getDateFmt(), "yyyyMMdd").trim();
        String separator = Objects.toString(code.getSeparator(), "").trim();

        Integer iNextNo;
        String sNextNo;
        String sCurrNo;
        Integer iMode = 1;
        String sDate = "";
        String sPrefix;
        Integer i;

        for (i = 0; i < numLength; i++) {
            iMode = iMode * 10;
        }

        iNextNo = (NumberUtils.toInt(currNo, 0) + 1) % iMode;
        sNextNo = String.valueOf(iNextNo);

        sCurrNo = String.valueOf(currNo);
        while (sCurrNo.length() < numLength) {
            sCurrNo = "0" + sCurrNo;
        }

        //更新数据库下一个单据号
        Code nextCode = new Code(NumberUtils.toInt(type, 0), sNextNo);
        nextCode.preUpdate();
        codeDao.updateNext(nextCode);

        if (useDate == 1) {
            sDate = DateUtils.formatDate(new Date(), dateFormat);
        }

        sPrefix = prefix.toUpperCase();

        String sResult = sCurrNo;

        if (StringUtils.isNotBlank(separator)) {
            if (StringUtils.isNotBlank(sDate)) {
                sResult = sDate + separator + sResult;
            }

            if (StringUtils.isNotBlank(sPrefix)) {
                sResult = sPrefix + separator + sResult;
            }
        } else {
            sResult = sPrefix + sDate + sCurrNo;
        }
        return sResult;
    }

    public void update(Code code) {
        if (StringUtils.isNotEmpty(code.getId())) {
            code.preUpdate();
            codeDao.update(code);
        }
    }
}
