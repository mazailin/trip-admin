package com.ulplanet.trip.modules.sys.utils;

import com.google.common.collect.Lists;
import com.ulplanet.trip.common.utils.SpringContextHolder;
import com.ulplanet.trip.modules.sys.entity.Dict;
import com.ulplanet.trip.modules.sys.service.DictService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 字典工具类
 * Created by zhangxd on 15/10/20.
 */
public class DictUtils {

    private static DictService dictService = SpringContextHolder.getBean(DictService.class);

    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }

    public static List<Dict> getDictList(String type) {
        return dictService.getDictList(type);
    }

}
