package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by JavaDog on 2018-2-8.
 */

public class MyGift {

    /**
     * size : 20
     * totalPages : 1
     * content : [{"acceptUserId":23,"acceptUserInfo":{"age":27,"charm":210592,"constellationId":1,"createTime":1552362244000,"details":"各种嗨~起来嗨~","diamond":11776,"experience":210391,"gold":2189,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","height":173,"huanxinDetail":"{\"duration\":0,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552362244169,\"modified\":1552362244169,\"type\":\"user\",\"uuid\":\"14c4e390-4479-11e9-bf84-fb2a6cdd4e96\",\"username\":\"17611038965\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552362244178}","huanxinId":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","label":"电影,音乐,游戏,赛马","lastLoginLatitude":39.971138,"lastLoginLongitude":116.178614,"level":5,"nickName":"AngleBaby","phone":"17611038965","plute":212480,"role":"1","sex":0,"stars":10000,"state":1,"updateTime":1554795554000,"userId":23,"userOtherList":[{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}],"userState":1,"weight":65.32,"yuanbao":10000},"createTime":1553681745000,"giftId":2,"giftInfo":{"acceptCharm":2,"acceptCharmSpecial":1.0E-4,"acceptExperience":2,"acceptPlute":2,"createTime":1553668556000,"giftId":2,"giftName":"玫瑰花","giftPrice":1,"giftType":2,"sendCharm":2,"sendCharmSpecial":1.0E-4,"sendExperience":2,"sendPlute":2,"state":1,"updateTime":1553679404000},"giftNumber":25,"recordId":8,"sendUserId":26,"sendUserInfo":{"age":22,"charm":2003071,"constellationId":1,"createTime":1552379938000,"diamond":9950,"experience":212480,"gold":9850,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg","huanxinDetail":"{\"duration\":32,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552379935446,\"modified\":1552379935446,\"type\":\"user\",\"uuid\":\"4597876a-44a2-11e9-b398-8758ceb888e2\",\"username\":\"17611038900\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552379935445}","huanxinId":"4597876a-44a2-11e9-b398-8758ceb888e2","lastLoginLatitude":39.993695,"lastLoginLongitude":116.345915,"level":1,"nickName":"刘诗诗","phone":"17611038900","plute":212480,"role":"1","sex":0,"stars":10000,"state":1,"updateTime":1554712777000,"userId":26,"userOtherList":[{"createTime":1552379938000,"otherId":37,"otherToken":"17611038900","otherType":4,"state":1,"userId":26}],"yuanbao":59100},"state":1}]
     */

    private int size;
    private int totalPages;
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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * acceptUserId : 23
         * acceptUserInfo : {"age":27,"charm":210592,"constellationId":1,"createTime":1552362244000,"details":"各种嗨~起来嗨~","diamond":11776,"experience":210391,"gold":2189,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg","height":173,"huanxinDetail":"{\"duration\":0,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552362244169,\"modified\":1552362244169,\"type\":\"user\",\"uuid\":\"14c4e390-4479-11e9-bf84-fb2a6cdd4e96\",\"username\":\"17611038965\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552362244178}","huanxinId":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","label":"电影,音乐,游戏,赛马","lastLoginLatitude":39.971138,"lastLoginLongitude":116.178614,"level":5,"nickName":"AngleBaby","phone":"17611038965","plute":212480,"role":"1","sex":0,"stars":10000,"state":1,"updateTime":1554795554000,"userId":23,"userOtherList":[{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}],"userState":1,"weight":65.32,"yuanbao":10000}
         * createTime : 1553681745000
         * giftId : 2
         * giftInfo : {"acceptCharm":2,"acceptCharmSpecial":1.0E-4,"acceptExperience":2,"acceptPlute":2,"createTime":1553668556000,"giftId":2,"giftName":"玫瑰花","giftPrice":1,"giftType":2,"sendCharm":2,"sendCharmSpecial":1.0E-4,"sendExperience":2,"sendPlute":2,"state":1,"updateTime":1553679404000}
         * giftNumber : 25
         * recordId : 8
         * sendUserId : 26
         * sendUserInfo : {"age":22,"charm":2003071,"constellationId":1,"createTime":1552379938000,"diamond":9950,"experience":212480,"gold":9850,"headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg","huanxinDetail":"{\"duration\":32,\"path\":\"/users\",\"application\":\"58b0d870-40af-11e9-9e8e-dff4478d9617\",\"entities\":[{\"created\":1552379935446,\"modified\":1552379935446,\"type\":\"user\",\"uuid\":\"4597876a-44a2-11e9-b398-8758ceb888e2\",\"username\":\"17611038900\",\"activated\":true}],\"organization\":\"1121190307181214\",\"action\":\"post\",\"uri\":\"https://a1.easemob.com/1121190307181214/qianyu/users\",\"applicationName\":\"qianyu\",\"timestamp\":1552379935445}","huanxinId":"4597876a-44a2-11e9-b398-8758ceb888e2","lastLoginLatitude":39.993695,"lastLoginLongitude":116.345915,"level":1,"nickName":"刘诗诗","phone":"17611038900","plute":212480,"role":"1","sex":0,"stars":10000,"state":1,"updateTime":1554712777000,"userId":26,"userOtherList":[{"createTime":1552379938000,"otherId":37,"otherToken":"17611038900","otherType":4,"state":1,"userId":26}],"yuanbao":59100}
         * state : 1
         */

        private int acceptUserId;
        private AcceptUserInfoBean acceptUserInfo;
        private long createTime;
        private int giftId;
        private GiftList.ContentBean giftInfo;
        private int giftNumber;
        private int recordId;
        private int sendUserId;
        private SendUserInfoBean sendUserInfo;
        private int state;

        public int getAcceptUserId() {
            return acceptUserId;
        }

        public void setAcceptUserId(int acceptUserId) {
            this.acceptUserId = acceptUserId;
        }

        public AcceptUserInfoBean getAcceptUserInfo() {
            return acceptUserInfo;
        }

        public void setAcceptUserInfo(AcceptUserInfoBean acceptUserInfo) {
            this.acceptUserInfo = acceptUserInfo;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGiftId() {
            return giftId;
        }

        public void setGiftId(int giftId) {
            this.giftId = giftId;
        }

        public GiftList.ContentBean getGiftInfo() {
            return giftInfo;
        }

        public void setGiftInfo(GiftList.ContentBean giftInfo) {
            this.giftInfo = giftInfo;
        }

        public int getGiftNumber() {
            return giftNumber;
        }

        public void setGiftNumber(int giftNumber) {
            this.giftNumber = giftNumber;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public SendUserInfoBean getSendUserInfo() {
            return sendUserInfo;
        }

        public void setSendUserInfo(SendUserInfoBean sendUserInfo) {
            this.sendUserInfo = sendUserInfo;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public static class AcceptUserInfoBean {
            /**
             * age : 27
             * charm : 210592
             * constellationId : 1
             * createTime : 1552362244000
             * details : 各种嗨~起来嗨~
             * diamond : 11776
             * experience : 210391
             * gold : 2189
             * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554791111&di=e7e3f3093189e1cc2a8625bea525fe29&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F28%2F20151128143542_GzrLf.jpeg
             * height : 173
             * huanxinDetail : {"duration":0,"path":"/users","application":"58b0d870-40af-11e9-9e8e-dff4478d9617","entities":[{"created":1552362244169,"modified":1552362244169,"type":"user","uuid":"14c4e390-4479-11e9-bf84-fb2a6cdd4e96","username":"17611038965","activated":true}],"organization":"1121190307181214","action":"post","uri":"https://a1.easemob.com/1121190307181214/qianyu/users","applicationName":"qianyu","timestamp":1552362244178}
             * huanxinId : 14c4e390-4479-11e9-bf84-fb2a6cdd4e96
             * label : 电影,音乐,游戏,赛马
             * lastLoginLatitude : 39.971138
             * lastLoginLongitude : 116.178614
             * level : 5
             * nickName : AngleBaby
             * phone : 17611038965
             * plute : 212480
             * role : 1
             * sex : 0
             * stars : 10000
             * state : 1
             * updateTime : 1554795554000
             * userId : 23
             * userOtherList : [{"createTime":1552362244000,"otherId":33,"otherToken":"ffffffffffffffffffffffffffffffffffffffffffff","otherType":3,"state":1,"userId":23},{"createTime":1552362244000,"otherId":34,"otherToken":"17611038965","otherType":4,"state":1,"userId":23}]
             * userState : 1
             * weight : 65.32
             * yuanbao : 10000
             */

            private int age;
            private int charm;
            private int constellationId;
            private long createTime;
            private String details;
            private int diamond;
            private int experience;
            private int gold;
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
            private int plute;
            private String role;
            private int sex;
            private int stars;
            private int state;
            private long updateTime;
            private int userId;
            private int userState;
            private double weight;
            private int yuanbao;
            private List<UserOtherListBean> userOtherList;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }

            public int getConstellationId() {
                return constellationId;
            }

            public void setConstellationId(int constellationId) {
                this.constellationId = constellationId;
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

            public int getDiamond() {
                return diamond;
            }

            public void setDiamond(int diamond) {
                this.diamond = diamond;
            }

            public int getExperience() {
                return experience;
            }

            public void setExperience(int experience) {
                this.experience = experience;
            }

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
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

            public int getPlute() {
                return plute;
            }

            public void setPlute(int plute) {
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
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

            public int getYuanbao() {
                return yuanbao;
            }

            public void setYuanbao(int yuanbao) {
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
                private int otherId;
                private String otherToken;
                private int otherType;
                private int state;
                private int userId;

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getOtherId() {
                    return otherId;
                }

                public void setOtherId(int otherId) {
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

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }
            }
        }

        public static class GiftInfoBean {
            /**
             * acceptCharm : 2
             * acceptCharmSpecial : 1.0E-4
             * acceptExperience : 2
             * acceptPlute : 2
             * createTime : 1553668556000
             * giftId : 2
             * giftName : 玫瑰花
             * giftPrice : 1
             * giftType : 2
             * sendCharm : 2
             * sendCharmSpecial : 1.0E-4
             * sendExperience : 2
             * sendPlute : 2
             * state : 1
             * updateTime : 1553679404000
             */

            private int acceptCharm;
            private double acceptCharmSpecial;
            private int acceptExperience;
            private int acceptPlute;
            private long createTime;
            private int giftId;
            private String giftName;
            private String giftUrl;
            private int giftPrice;
            private int giftType;
            private int sendCharm;
            private double sendCharmSpecial;
            private int sendExperience;
            private int sendPlute;
            private int state;
            private long updateTime;

            public int getAcceptCharm() {
                return acceptCharm;
            }

            public void setAcceptCharm(int acceptCharm) {
                this.acceptCharm = acceptCharm;
            }

            public double getAcceptCharmSpecial() {
                return acceptCharmSpecial;
            }

            public void setAcceptCharmSpecial(double acceptCharmSpecial) {
                this.acceptCharmSpecial = acceptCharmSpecial;
            }

            public int getAcceptExperience() {
                return acceptExperience;
            }

            public void setAcceptExperience(int acceptExperience) {
                this.acceptExperience = acceptExperience;
            }

            public int getAcceptPlute() {
                return acceptPlute;
            }

            public void setAcceptPlute(int acceptPlute) {
                this.acceptPlute = acceptPlute;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getGiftId() {
                return giftId;
            }

            public void setGiftId(int giftId) {
                this.giftId = giftId;
            }

            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public String getGiftUrl() {
                return giftUrl;
            }

            public void setGiftUrl(String giftUrl) {
                this.giftUrl = giftUrl;
            }

            public int getGiftPrice() {
                return giftPrice;
            }

            public void setGiftPrice(int giftPrice) {
                this.giftPrice = giftPrice;
            }

            public int getGiftType() {
                return giftType;
            }

            public void setGiftType(int giftType) {
                this.giftType = giftType;
            }

            public int getSendCharm() {
                return sendCharm;
            }

            public void setSendCharm(int sendCharm) {
                this.sendCharm = sendCharm;
            }

            public double getSendCharmSpecial() {
                return sendCharmSpecial;
            }

            public void setSendCharmSpecial(double sendCharmSpecial) {
                this.sendCharmSpecial = sendCharmSpecial;
            }

            public int getSendExperience() {
                return sendExperience;
            }

            public void setSendExperience(int sendExperience) {
                this.sendExperience = sendExperience;
            }

            public int getSendPlute() {
                return sendPlute;
            }

            public void setSendPlute(int sendPlute) {
                this.sendPlute = sendPlute;
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

        public static class SendUserInfoBean {
            /**
             * age : 22
             * charm : 2003071
             * constellationId : 1
             * createTime : 1552379938000
             * diamond : 9950
             * experience : 212480
             * gold : 9850
             * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554196390938&di=93fa906c27e80289e248afd4ada6e6bc&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn14%2F0%2Fw1200h1200%2F20180519%2F9966-haturfs7204243.jpg
             * huanxinDetail : {"duration":32,"path":"/users","application":"58b0d870-40af-11e9-9e8e-dff4478d9617","entities":[{"created":1552379935446,"modified":1552379935446,"type":"user","uuid":"4597876a-44a2-11e9-b398-8758ceb888e2","username":"17611038900","activated":true}],"organization":"1121190307181214","action":"post","uri":"https://a1.easemob.com/1121190307181214/qianyu/users","applicationName":"qianyu","timestamp":1552379935445}
             * huanxinId : 4597876a-44a2-11e9-b398-8758ceb888e2
             * lastLoginLatitude : 39.993695
             * lastLoginLongitude : 116.345915
             * level : 1
             * nickName : 刘诗诗
             * phone : 17611038900
             * plute : 212480
             * role : 1
             * sex : 0
             * stars : 10000
             * state : 1
             * updateTime : 1554712777000
             * userId : 26
             * userOtherList : [{"createTime":1552379938000,"otherId":37,"otherToken":"17611038900","otherType":4,"state":1,"userId":26}]
             * yuanbao : 59100
             */

            private int age;
            private int charm;
            private int constellationId;
            private long createTime;
            private int diamond;
            private int experience;
            private int gold;
            private String headUrl;
            private String huanxinDetail;
            private String huanxinId;
            private double lastLoginLatitude;
            private double lastLoginLongitude;
            private int level;
            private String nickName;
            private String phone;
            private int plute;
            private String role;
            private int sex;
            private int stars;
            private int state;
            private long updateTime;
            private int userId;
            private int yuanbao;
            private List<UserOtherListBeanX> userOtherList;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getCharm() {
                return charm;
            }

            public void setCharm(int charm) {
                this.charm = charm;
            }

            public int getConstellationId() {
                return constellationId;
            }

            public void setConstellationId(int constellationId) {
                this.constellationId = constellationId;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDiamond() {
                return diamond;
            }

            public void setDiamond(int diamond) {
                this.diamond = diamond;
            }

            public int getExperience() {
                return experience;
            }

            public void setExperience(int experience) {
                this.experience = experience;
            }

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
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

            public int getPlute() {
                return plute;
            }

            public void setPlute(int plute) {
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getYuanbao() {
                return yuanbao;
            }

            public void setYuanbao(int yuanbao) {
                this.yuanbao = yuanbao;
            }

            public List<UserOtherListBeanX> getUserOtherList() {
                return userOtherList;
            }

            public void setUserOtherList(List<UserOtherListBeanX> userOtherList) {
                this.userOtherList = userOtherList;
            }

            public static class UserOtherListBeanX {
                /**
                 * createTime : 1552379938000
                 * otherId : 37
                 * otherToken : 17611038900
                 * otherType : 4
                 * state : 1
                 * userId : 26
                 */

                private long createTime;
                private int otherId;
                private String otherToken;
                private int otherType;
                private int state;
                private int userId;

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getOtherId() {
                    return otherId;
                }

                public void setOtherId(int otherId) {
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

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }
            }
        }
    }
}
