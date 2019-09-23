package com.qianyu.communicate.entity;

import com.qianyu.communicate.bukaSdk.entity.UserBean;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-23.
 */

public class MsgRecord {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"createTime":1555987465698,"groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","recordId":0,"text":"125","userId":23,"userLevel":"5","userName":"AngleBaby"},{"createTime":1555987462029,"groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","recordId":0,"text":"12345","userId":23,"userLevel":"5","userName":"AngleBaby"},{"createTime":1555987453415,"groupId":13,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","recordId":0,"text":"12345","userId":23,"userLevel":"5","userName":"AngleBaby"}]
     * totalElements : 3
     */

    private int size;
    private int totalPages;
    private int totalElements;
    private List<UserBean> content;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<UserBean> getContent() {
        return content;
    }

    public void setContent(List<UserBean> content) {
        this.content = content;
    }

}
