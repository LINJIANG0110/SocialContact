package com.qianyu.communicate.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JavaDog on 2019-4-8.
 */

public class FamilyMember implements Serializable {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"createTime":1553587453000,"groupId":9,"groupInfo":{"createTime":1553571634000,"details":"个性签名","groupId":9,"groupName":"群聊1","headUrl":"www.baidu.com","joinType":1,"qrcodeUrl":"www.hao123.com","sort":1,"state":1,"updateTime":1553581841000},"groupUserId":14,"state":1,"updateTime":1553587900000,"userId":23,"userInfo":{"age":27,"charm":210592,"createTime":1552362244000,"details":"各种嗨~起来嗨~","diamond":10000,"experience":212480,"gold":10000,"headUrl":"http://www.baidu.com","height":173,"huanxinDetail":"{\"duration\":0,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552362244169,\"modified\":1552362244169,\"type\":\"user\",\"uuid\":\"14c4e390-4479-11e9-bf84-fb2a6cdd4e96\",\"username\":\"17611038965\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552362244178}","huanxinId":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","label":"电影,音乐,游戏,赛马","lastLoginLatitude":39.971138,"lastLoginLongitude":116.178614,"level":1,"nickName":"杨文远234","phone":"17611038965","plute":212480,"role":"1","sex":1,"stars":10000,"state":1,"updateTime":1553681745000,"userId":23,"userOtherList":[{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}],"userState":1,"weight":65.32,"yuanbao":10000},"userType":1}]
     * totalElements : 1
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
         * createTime : 1553587453000
         * groupId : 9
         * groupInfo : {"createTime":1553571634000,"details":"个性签名","groupId":9,"groupName":"群聊1","headUrl":"www.baidu.com","joinType":1,"qrcodeUrl":"www.hao123.com","sort":1,"state":1,"updateTime":1553581841000}
         * groupUserId : 14
         * state : 1
         * updateTime : 1553587900000
         * userId : 23
         * userInfo : {"age":27,"charm":210592,"createTime":1552362244000,"details":"各种嗨~起来嗨~","diamond":10000,"experience":212480,"gold":10000,"headUrl":"http://www.baidu.com","height":173,"huanxinDetail":"{\"duration\":0,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552362244169,\"modified\":1552362244169,\"type\":\"user\",\"uuid\":\"14c4e390-4479-11e9-bf84-fb2a6cdd4e96\",\"username\":\"17611038965\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552362244178}","huanxinId":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","label":"电影,音乐,游戏,赛马","lastLoginLatitude":39.971138,"lastLoginLongitude":116.178614,"level":1,"nickName":"杨文远234","phone":"17611038965","plute":212480,"role":"1","sex":1,"stars":10000,"state":1,"updateTime":1553681745000,"userId":23,"userOtherList":[{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}],"userState":1,"weight":65.32,"yuanbao":10000}
         * userType : 1
         */

        private long createTime;
        private long groupId;
        private GroupInfoBean groupInfo;
        private long groupUserId;
        private int state;
        private long updateTime;
        private long userId;
        private UserInfoBean userInfo;
        private int userType;
        private boolean manage=false;

        public boolean isManage() {
            return manage;
        }

        public void setManage(boolean manage) {
            this.manage = manage;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public GroupInfoBean getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(GroupInfoBean groupInfo) {
            this.groupInfo = groupInfo;
        }

        public long getGroupUserId() {
            return groupUserId;
        }

        public void setGroupUserId(long groupUserId) {
            this.groupUserId = groupUserId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public static class GroupInfoBean {
            /**
             * createTime : 1553571634000
             * details : 个性签名
             * groupId : 9
             * groupName : 群聊1
             * headUrl : www.baidu.com
             * joinType : 1
             * qrcodeUrl : www.hao123.com
             * sort : 1
             * state : 1
             * updateTime : 1553581841000
             */

            private long createTime;
            private String details;
            private long groupId;
            private String groupName;
            private String headUrl;
            private int joinType;
            private String qrcodeUrl;
            private int sort;
            private int state;
            private long updateTime;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public long getGroupId() {
                return groupId;
            }

            public void setGroupId(long groupId) {
                this.groupId = groupId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getJoinType() {
                return joinType;
            }

            public void setJoinType(int joinType) {
                this.joinType = joinType;
            }

            public String getQrcodeUrl() {
                return qrcodeUrl;
            }

            public void setQrcodeUrl(String qrcodeUrl) {
                this.qrcodeUrl = qrcodeUrl;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class UserInfoBean {
            /**
             * age : 27
             * charm : 210592
             * createTime : 1552362244000
             * details : 各种嗨~起来嗨~
             * diamond : 10000
             * experience : 212480
             * gold : 10000
             * headUrl : http://www.baidu.com
             * height : 173
             * huanxinDetail : {"duration":0,"path":"/users","application":"58b0d870-40af-11e9-9e8e-dff4478d9617","entities":[{"created":1552362244169,"modified":1552362244169,"type":"user","uuid":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","username":"17611038965","activated":true}],"organization":"1121190307181214","action":"post","uri":"https://a1.easemob.com/1121190307181214/qianyu/users","applicationName":"qianyu","timestamp":1552362244178}
             * huanxinId : 14c4e390-4479-11e9-bf84-fb2a6cdd4e96
             * label : 电影,音乐,游戏,赛马
             * lastLoginLatitude : 39.971138
             * lastLoginLongitude : 116.178614
             * level : 1
             * nickName : 杨文远234
             * phone : 17611038965
             * plute : 212480
             * role : 1
             * sex : 1
             * stars : 10000
             * state : 1
             * updateTime : 1553681745000
             * userId : 23
             * userOtherList : [{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}]
             * userState : 1
             * weight : 65.32
             * yuanbao : 10000
             */

            private int age;
            private double charm;
            private long createTime;
            private String details;
            private double diamond;
            private double experience;
            private double gold;
            private String headUrl;
            private int height;
            private String huanxinDetail;
            private String huanxinId;
            private String label;
            private double lastLoginLatitude;
            private double lastLoginLongitude;
            private int level;
            private String nickName;
            private String phone;
            private double plute;
            private String role;
            private int sex;
            private int stars;
            private int state;
            private long updateTime;
            private long userId;
            private int userState;
            private double weight;
            private double yuanbao;
            private List<UserOtherListBean> userOtherList;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public double getCharm() {
                return charm;
            }

            public void setCharm(double charm) {
                this.charm = charm;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public double getDiamond() {
                return diamond;
            }

            public void setDiamond(double diamond) {
                this.diamond = diamond;
            }

            public double getExperience() {
                return experience;
            }

            public void setExperience(double experience) {
                this.experience = experience;
            }

            public double getGold() {
                return gold;
            }

            public void setGold(double gold) {
                this.gold = gold;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getHuanxinDetail() {
                return huanxinDetail;
            }

            public void setHuanxinDetail(String huanxinDetail) {
                this.huanxinDetail = huanxinDetail;
            }

            public String getHuanxinId() {
                return huanxinId;
            }

            public void setHuanxinId(String huanxinId) {
                this.huanxinId = huanxinId;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public double getLastLoginLatitude() {
                return lastLoginLatitude;
            }

            public void setLastLoginLatitude(double lastLoginLatitude) {
                this.lastLoginLatitude = lastLoginLatitude;
            }

            public double getLastLoginLongitude() {
                return lastLoginLongitude;
            }

            public void setLastLoginLongitude(double lastLoginLongitude) {
                this.lastLoginLongitude = lastLoginLongitude;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public double getPlute() {
                return plute;
            }

            public void setPlute(double plute) {
                this.plute = plute;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getStars() {
                return stars;
            }

            public void setStars(int stars) {
                this.stars = stars;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public int getUserState() {
                return userState;
            }

            public void setUserState(int userState) {
                this.userState = userState;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public double getYuanbao() {
                return yuanbao;
            }

            public void setYuanbao(double yuanbao) {
                this.yuanbao = yuanbao;
            }

            public List<UserOtherListBean> getUserOtherList() {
                return userOtherList;
            }

            public void setUserOtherList(List<UserOtherListBean> userOtherList) {
                this.userOtherList = userOtherList;
            }

            public static class UserOtherListBean {
                /**
                 * createTime : 1552362244000
                 * otherId : 33
                 * otherToken : ffffffffffffffffffffffffffffffffffffffffffff
                 * otherType : 3
                 * state : 1
                 * userId : 23
                 */

                private long createTime;
                private long otherId;
                private String otherToken;
                private int otherType;
                private int state;
                private long userId;

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public long getOtherId() {
                    return otherId;
                }

                public void setOtherId(long otherId) {
                    this.otherId = otherId;
                }

                public String getOtherToken() {
                    return otherToken;
                }

                public void setOtherToken(String otherToken) {
                    this.otherToken = otherToken;
                }

                public int getOtherType() {
                    return otherType;
                }

                public void setOtherType(int otherType) {
                    this.otherType = otherType;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
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
}
