package com.ulplanet.trip.modules.sys.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.CacheUtils;
import com.ulplanet.trip.modules.sys.dao.DictDao;
import com.ulplanet.trip.modules.sys.entity.Dict;
import com.ulplanet.trip.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
