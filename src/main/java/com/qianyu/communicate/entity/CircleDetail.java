package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JavaDog on 2019-5-7.
 */

public class CircleDetail implements Serializable{

    /**
     * circleInfoMap : {"comment":19,"age":33,"title":"啊啊啊啊啊啊啊啊啊啊啊啊","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=fc9959df94077f72096fd4325cadae5f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201406%2F29%2F20140629020323_YuzUt.jpeg","fabulous":2,"circleId":39,"userId":27,"sex":2,"text":"123214312412","nickName":"颖宝宝","groupName":"456","createTime":1557108142530}
     * commentMap : {"size":20,"totalPages":1,"content":[{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28}],"totalElements":19}
     * fabulousMap : {"size":20,"totalPages":1,"content":[{"nickName":"马尔扎哈","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","userId":29}],"totalElements":1}
     */

    private CircleList.ListBean.ContentBean circleInfoMap;
    private CommentMapBean commentMap;
    private FabulousMapBean fabulousMap;

    public CircleList.ListBean.ContentBean getCircleInfoMap() {
        return circleInfoMap;
    }

    public void setCircleInfoMap(CircleList.ListBean.ContentBean circleInfoMap) {
        this.circleInfoMap = circleInfoMap;
    }

    public CommentMapBean getCommentMap() {
        return commentMap;
    }

    public void setCommentMap(CommentMapBean commentMap) {
        this.commentMap = commentMap;
    }

    public FabulousMapBean getFabulousMap() {
        return fabulousMap;
    }

    public void setFabulousMap(FabulousMapBean fabulousMap) {
        this.fabulousMap = fabulousMap;
    }

    public static class CommentMapBean implements Serializable{
        /**
         * size : 20
         * totalPages : 1
         * content : [{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28},{"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg","nickName":"迪丽热巴","userId":28}]
         * totalElements : 19
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

        public static class ContentBean {
            /**
             * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=a55a247058be2477b441d87bc567d46d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20%2F448%2Fw1024h1024%2F20180604%2F3c80-hcmurvh7340775.jpg
             * nickName : 迪丽热巴
             * userId : 28
             */

            private String headUrl;
            private String nickName;
            private long userId;

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

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }
        }
    }

    public static class FabulousMapBean implements Serializable{
        /**
         * size : 20
         * totalPages : 1
         * content : [{"nickName":"马尔扎哈","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg","userId":29}]
         * totalElements : 1
         */

        private int size;
        private int totalPages;
        private int totalElements;
        private List<ContentBeanX> content;

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

        public List<ContentBeanX> getContent() {
            return content;
        }

        public void setContent(List<ContentBeanX> content) {
            this.content = content;
        }

        public static class ContentBeanX {
            /**
             * nickName : 马尔扎哈
             * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390937&di=b469fd7e079b8283e19bff3929705df5&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F634%2Fw720h714%2F20180519%2F9fef-haturfs7204469.jpg
             * userId : 29
             */

            private String nickName;
            private String headUrl;
            private long userId;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }
        }
    }
}
