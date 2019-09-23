package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

public class CircleList implements Serializable {

    /**
     * unRead : {"count":4,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg"}
     * list : {"size":3,"totalPages":11,"content":[{"comment":24,"sex":2,"text":"123214312412","createTime":1557108135000,"groupName":"456","fileItemUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/1894554408468789.jpg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","userId":27,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg","nickName":"颖宝宝","circleId":39,"fabulous":2,"age":33},{"sex":2,"text":"123214312412","groupName":"456","fileItemUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/1894554408468789.jpg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","createTime":1557107990000,"userId":27,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg","comment":2,"nickName":"颖宝宝","circleId":38,"fabulous":2,"age":33},{"createTime":1557055815000,"age":27,"sex":2,"text":"123214312412","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","fileItemUrl":"","nickName":"AngleBaby","comment":0,"userId":23,"fabulous":1,"circleId":37}],"totalElements":33}
     */

    private UnReadBean unRead;
    private ListBean list;

    public UnReadBean getUnRead() {
        return unRead;
    }

    public void setUnRead(UnReadBean unRead) {
        this.unRead = unRead;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class UnReadBean implements Serializable{
        /**
         * count : 4
         * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg
         */

        private int count;
        private String headUrl;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }
    }

    public static class ListBean implements Serializable{
        /**
         * size : 3
         * totalPages : 11
         * content : [{"comment":24,"sex":2,"text":"123214312412","createTime":1557108135000,"groupName":"456","fileItemUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/1894554408468789.jpg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","userId":27,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg","nickName":"颖宝宝","circleId":39,"fabulous":2,"age":33},{"sex":2,"text":"123214312412","groupName":"456","fileItemUrl":"http://qianyubk.oss-cn-shanghai.aliyuncs.com/1894554408468789.jpg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","createTime":1557107990000,"userId":27,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg","comment":2,"nickName":"颖宝宝","circleId":38,"fabulous":2,"age":33},{"createTime":1557055815000,"age":27,"sex":2,"text":"123214312412","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","title":"啊啊啊啊啊啊啊啊啊啊啊啊","fileItemUrl":"","nickName":"AngleBaby","comment":0,"userId":23,"fabulous":1,"circleId":37}]
         * totalElements : 33
         */

        private int size;
        private int totalPages;
        private int totalElements;
        private List<ContentBean> content;

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

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean implements Serializable{
            /**
             * comment : 24
             * sex : 2
             * text : 123214312412
             * createTime : 1557108135000
             * groupName : 456
             * fileItemUrl : http://qianyubk.oss-cn-shanghai.aliyuncs.com/1894554408468789.jpg
             * title : 啊啊啊啊啊啊啊啊啊啊啊啊
             * userId : 27
             * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg
             * nickName : 颖宝宝
             * circleId : 39
             * fabulous : 2
             * age : 33
             */

            private int comment;
            private int sex;
            private String text;
            private long createTime;
            private int groupId;
            private String groupName;
            private String fileItemUrl;
            private String title;
            private long userId;
            private String headUrl;
            private String nickName;
            private int circleId;
            private int fabulous;
            private int age;
            private int level;
            private String type;
            private int isClick;

            public int getIsClick() {
                return isClick;
            }

            public void setIsClick(int isClick) {
                this.isClick = isClick;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getFileItemUrl() {
                return fileItemUrl;
            }

            public void setFileItemUrl(String fileItemUrl) {
                this.fileItemUrl = fileItemUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getCircleId() {
                return circleId;
            }

            public void setCircleId(int circleId) {
                this.circleId = circleId;
            }

            public int getFabulous() {
                return fabulous;
            }

            public void setFabulous(int fabulous) {
                this.fabulous = fabulous;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
