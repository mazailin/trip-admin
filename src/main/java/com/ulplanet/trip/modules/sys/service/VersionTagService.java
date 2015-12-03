package com.ulplanet.trip.modules.sys.service;

import com.ulplanet.trip.modules.sys.dao.VersionTagDao;
import com.ulplanet.trip.modules.sys.entity.VersionTag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/11/17.
 */
@Service
public class VersionTagService {

    @Resource
    private VersionTagDao versionTagDao;

    public int save(VersionTag versionTag){
        VersionTag v = versionTagDao.get(versionTag);
        if(v != null){
            return versionTagDao.update(versionTag);
        }
        return versionTagDao.insert(versionTag);
    }
}
