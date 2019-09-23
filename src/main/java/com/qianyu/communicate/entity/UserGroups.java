package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-23.
 */

public class UserGroups {

    /**
     * groupInfo : {"groupId":14,"groupName":"456","headUrl":"http://kascdn.kascend.com/jellyfish/avatar/24589973/1485946759182.jpg"}
     * gzlist : [{"groupId":14,"groupName":"456","headUrl":"http://kascdn.kascend.com/jellyfish/avatar/24589973/1485946759182.jpg"},{"groupId":13,"groupName":"123","headUrl":"http://img.zcool.cn/community/0158e25983c6c50000002129f1d823.jpg@1280w_1l_2o_100sh.png"}]
     */

    private FamilyList.ContentBean groupInfo;
    private List<FamilyList.ContentBean> gzlist;

    public FamilyList.ContentBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(FamilyList.ContentBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public List<FamilyList.ContentBean> getGzlist() {
        return gzlist;
    }

    public void setGzlist(List<FamilyList.ContentBean> gzlist) {
        this.gzlist = gzlist;
    }

}
