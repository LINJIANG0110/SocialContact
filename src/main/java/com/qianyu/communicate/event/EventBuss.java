package com.qianyu.communicate.event;

/**
 * 作者 ： dyj
 * 时间 ： 2016/4/29.
 */

public class EventBuss {

    private int state;
    private Object message;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public EventBuss(int state) {
        this.state = state;
    }

    public static int HOME_MSG_REFRESH = 1;//消息刷新红点
    public static int INCOME_LIST = 2;//收支明细
    public static int ADD_PRAISE = 3;//点赞
    public static int CANCEL_PRAISE = 4;//取消点赞
    public static int COMMENT = 5;//评论
    public static int IMPRESS_TAG = 6;//印象标签
    public static int WORK_TAG = 7;//行业标签
    public static int MINE_TAB = 8;//我的页面
    public static int EXIT_LOGIN = 9;//退出登录
    public static int FRIEND_CIRCLE = 10;//发布动态
    public static int GOOD_TAG = 11;//擅长标签
    public static int GIFT_DATA = 12;//礼物数据
    public static int EVENT_RECORD = 13;//事件记录
    public static int FAMILY_WELCOME = 14;//更新家族欢迎词
    public static int FAMILY_INTRODUCE = 15;//更新家族介绍
    public static int FRIEND_SELECT = 16;//筛选附近的人
    public static int CIRCLE_MSG = 17;//动态消息
    public static int CIRCLE_READ = 18;//动态消息清空
    public static int FAMILY_ENTER = 19;//加入群
    public static int FAMILY_RECRUIT = 20;//家族召唤
    public static int WORLD_RECRUIT = 21;//世界招募
    public static int FAMILY_BOSS = 22;//家族boss
    public static int PUSH_GIFT = 23;//送礼推送
    public static int PUSH_SKILL = 24;//技能推送
    public static int FAMILY_REFRESH = 25;//聊天室刷新
    public static int FAMILY_REDUCE = 26;//踢人
    public static int FAMILY_EXIT = 27;// 退出家族
    public static int COMMENT_DELETE = 28;//删除评论
    public static int FRIEND_REQUEST = 29;//好友申请
    public static int FRIEND_CLEAR = 30;//好友申请已读
    public static int MALL_BILL = 31;//商户支出收入总计
    public static int FAMILY_EXIT_ENTER = 32;// 退出家族并进入另一家族
    public static int WORLD_MSG = 33;//世界发言
    public static int FAMILY_APPLY = 34;//群申请通过
    public static int MY_EVENT = 35;//个人事件
    public static int MY_EVENT_READ = 36;//个人事件已读
    public static int FAMILY_NAME = 37;//更新家族名字
    public static int GURAD_CDART = 38;//劫镖推送
    public static int GURAD_SAVE = 39;//解救推送
    public static int LOGIN_OUT = 40;//注销登录
    public static int GUARD_RESULT = 41;//押镖结算
    public static int CREATE_REDPACKE = 42;// 创建红包成功
    public static int TOPIC_ANSWER = 43;// 话题回答
    public static int TOPIC_ZAN = 44;// 话题点赞
    public static int TOPIC_PL = 45;// 话题评论
}
