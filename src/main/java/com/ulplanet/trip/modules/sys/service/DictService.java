package com.ulplanet.trip.modules.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.sys.dao.DictDao;
import com.ulplanet.trip.modules.sys.entity.Dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 字典Service
 * Created by zhangxd on 15/10/20.
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
	}

    public List<Dict> getDictList(String type) {
        Map<String, List<Dict>> dictMap = Maps.newHashMap();
        for (Dict dict : dao.findAllList(new Dict())) {
            List<Dict> dictList = dictMap.get(dict.getType());
            if (dictList != null) {
                dictList.add(dict);
            } else {
                dictMap.put(dict.getType(), Lists.newArrayList(dict));
            }
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

}
