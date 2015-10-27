package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.dao.GroupDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.utils.ChatIdMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxd on 15/8/11.
 */
@Service("groupService")
public class GroupService extends CrudService<GroupDao,Group> {

    @Autowired
    private GroupDao groupDao;

    public ResponseBo addGroup(Group group) {
        ResponseBo responseBo = new ResponseBo();
        String chatId = ChatIdMaker.makeChatId(group.getName(), group.getDescription());
        if (StringUtils.isEmpty(chatId)) {
            responseBo.setStatus(0);
            responseBo.setMsg("加入聊天组失败");
            return responseBo;
        }
        group.setChatId(chatId);
        group.preInsert();
        return ResponseBo.getResult(this.groupDao.insert(group));
    }

    public ResponseBo updateGroup(Group group) {
        group.preUpdate();
        return ResponseBo.getResult(this.groupDao.update(group));
    }

    public ResponseBo deleteGroup(Group group) {
        return ResponseBo.getResult(this.groupDao.delete(group));
    }

//    Map<String, Object> listGroup(int page, int size, String searchValue);
//
}
