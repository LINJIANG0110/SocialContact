
package com.qianyu.communicate.emotions.utils;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.qianyu.communicate.R;


/**
 * Created by SiberiaDante
 * Describe: 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 * Time: 2017/6/26
 * Email: 994537867@qq.com
 * GitHub: https://github.com/SiberiaDante
 * 博客园： http://www.cnblogs.com/shen-hua/
 */
public class EmotionUtils {

    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典表情
    private static final String TAG = EmotionUtils.class.getSimpleName();

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


    static {
        EMPTY_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

//        EMOTION_CLASSIC_MAP.put("[呵呵]", R.drawable.d_hehe);
//        EMOTION_CLASSIC_MAP.put("[嘻嘻]", R.drawable.d_xixi);
//        EMOTION_CLASSIC_MAP.put("[哈哈]", R.drawable.d_haha);
//        EMOTION_CLASSIC_MAP.put("[爱你]", R.drawable.d_aini);
//        EMOTION_CLASSIC_MAP.put("[挖鼻屎]", R.drawable.d_wabishi);
//        EMOTION_CLASSIC_MAP.put("[吃惊]", R.drawable.d_chijing);
//        EMOTION_CLASSIC_MAP.put("[晕]", R.drawable.d_yun);
//        EMOTION_CLASSIC_MAP.put("[泪]", R.drawable.d_lei);
//        EMOTION_CLASSIC_MAP.put("[馋嘴]", R.drawable.d_chanzui);
//        EMOTION_CLASSIC_MAP.put("[抓狂]", R.drawable.d_zhuakuang);
//        EMOTION_CLASSIC_MAP.put("[哼]", R.drawable.d_heng);
//        EMOTION_CLASSIC_MAP.put("[可爱]", R.drawable.d_keai);
//        EMOTION_CLASSIC_MAP.put("[怒]", R.drawable.d_nu);
//        EMOTION_CLASSIC_MAP.put("[汗]", R.drawable.d_han);
//        EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.d_haixiu);
//        EMOTION_CLASSIC_MAP.put("[睡觉]", R.drawable.d_shuijiao);
//        EMOTION_CLASSIC_MAP.put("[钱]", R.drawable.d_qian);
//        EMOTION_CLASSIC_MAP.put("[偷笑]", R.drawable.d_touxiao);
//        EMOTION_CLASSIC_MAP.put("[笑cry]", R.drawable.d_xiaoku);
//        EMOTION_CLASSIC_MAP.put("[doge]", R.drawable.d_doge);
//        EMOTION_CLASSIC_MAP.put("[喵喵]", R.drawable.d_miao);
//        EMOTION_CLASSIC_MAP.put("[酷]", R.drawable.d_ku);
//        EMOTION_CLASSIC_MAP.put("[衰]", R.drawable.d_shuai);
//        EMOTION_CLASSIC_MAP.put("[闭嘴]", R.drawable.d_bizui);
//        EMOTION_CLASSIC_MAP.put("[鄙视]", R.drawable.d_bishi);
//        EMOTION_CLASSIC_MAP.put("[花心]", R.drawable.d_huaxin);
//        EMOTION_CLASSIC_MAP.put("[鼓掌]", R.drawable.d_guzhang);
//        EMOTION_CLASSIC_MAP.put("[悲伤]", R.drawable.d_beishang);
//        EMOTION_CLASSIC_MAP.put("[思考]", R.drawable.d_sikao);
//        EMOTION_CLASSIC_MAP.put("[生病]", R.drawable.d_shengbing);
//        EMOTION_CLASSIC_MAP.put("[亲亲]", R.drawable.d_qinqin);
//        EMOTION_CLASSIC_MAP.put("[怒骂]", R.drawable.d_numa);
//        EMOTION_CLASSIC_MAP.put("[太开心]", R.drawable.d_taikaixin);
//        EMOTION_CLASSIC_MAP.put("[懒得理你]", R.drawable.d_landelini);
//        EMOTION_CLASSIC_MAP.put("[右哼哼]", R.drawable.d_youhengheng);
//        EMOTION_CLASSIC_MAP.put("[左哼哼]", R.drawable.d_zuohengheng);
//        EMOTION_CLASSIC_MAP.put("[嘘]", R.drawable.d_xu);
//        EMOTION_CLASSIC_MAP.put("[委屈]", R.drawable.d_weiqu);
//        EMOTION_CLASSIC_MAP.put("[吐]", R.drawable.d_tu);
//        EMOTION_CLASSIC_MAP.put("[可怜]", R.drawable.d_kelian);
//        EMOTION_CLASSIC_MAP.put("[打哈气]", R.drawable.d_dahaqi);
//        EMOTION_CLASSIC_MAP.put("[挤眼]", R.drawable.d_jiyan);
//        EMOTION_CLASSIC_MAP.put("[失望]", R.drawable.d_shiwang);
//        EMOTION_CLASSIC_MAP.put("[顶]", R.drawable.d_ding);
//        EMOTION_CLASSIC_MAP.put("[疑问]", R.drawable.d_yiwen);
//        EMOTION_CLASSIC_MAP.put("[困]", R.drawable.d_kun);
//        EMOTION_CLASSIC_MAP.put("[感冒]", R.drawable.d_ganmao);
//        EMOTION_CLASSIC_MAP.put("[拜拜]", R.drawable.d_baibai);
//        EMOTION_CLASSIC_MAP.put("[黑线]", R.drawable.d_heixian);
//        EMOTION_CLASSIC_MAP.put("[阴险]", R.drawable.d_yinxian);
//        EMOTION_CLASSIC_MAP.put("[打脸]", R.drawable.d_dalian);
//        EMOTION_CLASSIC_MAP.put("[傻眼]", R.drawable.d_shayan);
//        EMOTION_CLASSIC_MAP.put("[猪头]", R.drawable.d_zhutou);
//        EMOTION_CLASSIC_MAP.put("[熊猫]", R.drawable.d_xiongmao);
//        EMOTION_CLASSIC_MAP.put("[兔子]", R.drawable.d_tuzi);


//        EMOTION_CLASSIC_MAP.put("[001]", R.mipmap.ft01);
//        EMOTION_CLASSIC_MAP.put("[002]", R.mipmap.ft02);
//        EMOTION_CLASSIC_MAP.put("[003]", R.mipmap.ft03);
//        EMOTION_CLASSIC_MAP.put("[004]", R.mipmap.ft04);
//        EMOTION_CLASSIC_MAP.put("[005]", R.mipmap.ft05);
//        EMOTION_CLASSIC_MAP.put("[006]", R.mipmap.ft06);
//        EMOTION_CLASSIC_MAP.put("[007]", R.mipmap.ft07);
//        EMOTION_CLASSIC_MAP.put("[008]", R.mipmap.ft08);
//        EMOTION_CLASSIC_MAP.put("[009]", R.mipmap.ft09);
//        EMOTION_CLASSIC_MAP.put("[010]", R.mipmap.ft10);
//        EMOTION_CLASSIC_MAP.put("[011]", R.mipmap.ft11);
//        EMOTION_CLASSIC_MAP.put("[012]", R.mipmap.ft12);
//        EMOTION_CLASSIC_MAP.put("[013]", R.mipmap.ft13);
//        EMOTION_CLASSIC_MAP.put("[014]", R.mipmap.ft14);
//        EMOTION_CLASSIC_MAP.put("[015]", R.mipmap.ft15);
//        EMOTION_CLASSIC_MAP.put("[016]", R.mipmap.ft16);
//        EMOTION_CLASSIC_MAP.put("[017]", R.mipmap.ft17);
//        EMOTION_CLASSIC_MAP.put("[018]", R.mipmap.ft18);
//        EMOTION_CLASSIC_MAP.put("[019]", R.mipmap.ft19);
//        EMOTION_CLASSIC_MAP.put("[020]", R.mipmap.ft20);
//        EMOTION_CLASSIC_MAP.put("[021]", R.mipmap.ft21);
//        EMOTION_CLASSIC_MAP.put("[022]", R.mipmap.ft22);
//        EMOTION_CLASSIC_MAP.put("[023]", R.mipmap.ft23);
//        EMOTION_CLASSIC_MAP.put("[024]", R.mipmap.ft24);
//        EMOTION_CLASSIC_MAP.put("[025]", R.mipmap.ft25);
//        EMOTION_CLASSIC_MAP.put("[026]", R.mipmap.ft26);
//        EMOTION_CLASSIC_MAP.put("[027]", R.mipmap.ft27);
//        EMOTION_CLASSIC_MAP.put("[028]", R.mipmap.ft28);
//        EMOTION_CLASSIC_MAP.put("[029]", R.mipmap.ft29);

        EMOTION_CLASSIC_MAP.put("[701]", R.drawable.f701);
        EMOTION_CLASSIC_MAP.put("[702]", R.drawable.f702);
        EMOTION_CLASSIC_MAP.put("[703]", R.drawable.f703);
        EMOTION_CLASSIC_MAP.put("[704]", R.drawable.f704);
        EMOTION_CLASSIC_MAP.put("[705]", R.drawable.f705);
        EMOTION_CLASSIC_MAP.put("[706]", R.drawable.f706);
        EMOTION_CLASSIC_MAP.put("[707]", R.drawable.f707);
        EMOTION_CLASSIC_MAP.put("[708]", R.drawable.f708);
        EMOTION_CLASSIC_MAP.put("[709]", R.drawable.f709);
        EMOTION_CLASSIC_MAP.put("[710]", R.drawable.f710);
        EMOTION_CLASSIC_MAP.put("[711]", R.drawable.f711);
        EMOTION_CLASSIC_MAP.put("[712]", R.drawable.f712);
        EMOTION_CLASSIC_MAP.put("[713]", R.drawable.f713);
        EMOTION_CLASSIC_MAP.put("[714]", R.drawable.f714);
        EMOTION_CLASSIC_MAP.put("[715]", R.drawable.f715);
        EMOTION_CLASSIC_MAP.put("[716]", R.drawable.f716);
        EMOTION_CLASSIC_MAP.put("[717]", R.drawable.f717);
        EMOTION_CLASSIC_MAP.put("[718]", R.drawable.f718);
        EMOTION_CLASSIC_MAP.put("[719]", R.drawable.f719);
        EMOTION_CLASSIC_MAP.put("[720]", R.drawable.f720);
        EMOTION_CLASSIC_MAP.put("[721]", R.drawable.f721);
        EMOTION_CLASSIC_MAP.put("[722]", R.drawable.f722);
        EMOTION_CLASSIC_MAP.put("[723]", R.drawable.f723);
        EMOTION_CLASSIC_MAP.put("[724]", R.drawable.f724);
        EMOTION_CLASSIC_MAP.put("[725]", R.drawable.f725);
        EMOTION_CLASSIC_MAP.put("[726]", R.drawable.f726);
        EMOTION_CLASSIC_MAP.put("[727]", R.drawable.f727);
        EMOTION_CLASSIC_MAP.put("[728]", R.drawable.f728);
        EMOTION_CLASSIC_MAP.put("[729]", R.drawable.f729);
        EMOTION_CLASSIC_MAP.put("[730]", R.drawable.f730);
        EMOTION_CLASSIC_MAP.put("[731]", R.drawable.f731);
        EMOTION_CLASSIC_MAP.put("[732]", R.drawable.f732);
        EMOTION_CLASSIC_MAP.put("[733]", R.drawable.f733);
        EMOTION_CLASSIC_MAP.put("[734]", R.drawable.f734);
        EMOTION_CLASSIC_MAP.put("[735]", R.drawable.f735);
        EMOTION_CLASSIC_MAP.put("[736]", R.drawable.f736);
        EMOTION_CLASSIC_MAP.put("[737]", R.drawable.f737);
        EMOTION_CLASSIC_MAP.put("[738]", R.drawable.f738);
        EMOTION_CLASSIC_MAP.put("[739]", R.drawable.f739);
        EMOTION_CLASSIC_MAP.put("[740]", R.drawable.f740);
        EMOTION_CLASSIC_MAP.put("[741]", R.drawable.f741);
        EMOTION_CLASSIC_MAP.put("[742]", R.drawable.f742);
        EMOTION_CLASSIC_MAP.put("[743]", R.drawable.f743);
        EMOTION_CLASSIC_MAP.put("[744]", R.drawable.f744);
        EMOTION_CLASSIC_MAP.put("[745]", R.drawable.f745);
        EMOTION_CLASSIC_MAP.put("[746]", R.drawable.f746);
        EMOTION_CLASSIC_MAP.put("[747]", R.drawable.f747);
        EMOTION_CLASSIC_MAP.put("[748]", R.drawable.f748);
        EMOTION_CLASSIC_MAP.put("[749]", R.drawable.f749);
        EMOTION_CLASSIC_MAP.put("[750]", R.drawable.f750);
        EMOTION_CLASSIC_MAP.put("[751]", R.drawable.f751);
        EMOTION_CLASSIC_MAP.put("[752]", R.drawable.f752);
        EMOTION_CLASSIC_MAP.put("[753]", R.drawable.f753);
        EMOTION_CLASSIC_MAP.put("[754]", R.drawable.f754);
        EMOTION_CLASSIC_MAP.put("[755]", R.drawable.f755);
        EMOTION_CLASSIC_MAP.put("[756]", R.drawable.f756);
        EMOTION_CLASSIC_MAP.put("[757]", R.drawable.f757);
        EMOTION_CLASSIC_MAP.put("[758]", R.drawable.f758);
        EMOTION_CLASSIC_MAP.put("[759]", R.drawable.f759);
        EMOTION_CLASSIC_MAP.put("[760]", R.drawable.f760);
        EMOTION_CLASSIC_MAP.put("[761]", R.drawable.f761);
        EMOTION_CLASSIC_MAP.put("[762]", R.drawable.f762);
        EMOTION_CLASSIC_MAP.put("[763]", R.drawable.f763);
        EMOTION_CLASSIC_MAP.put("[764]", R.drawable.f764);
        EMOTION_CLASSIC_MAP.put("[765]", R.drawable.f765);
        EMOTION_CLASSIC_MAP.put("[766]", R.drawable.f766);
        EMOTION_CLASSIC_MAP.put("[767]", R.drawable.f767);
        EMOTION_CLASSIC_MAP.put("[768]", R.drawable.f768);
        EMOTION_CLASSIC_MAP.put("[769]", R.drawable.f769);
        EMOTION_CLASSIC_MAP.put("[770]", R.drawable.f770);
        EMOTION_CLASSIC_MAP.put("[771]", R.drawable.f771);
        EMOTION_CLASSIC_MAP.put("[772]", R.drawable.f772);
        EMOTION_CLASSIC_MAP.put("[773]", R.drawable.f773);
        EMOTION_CLASSIC_MAP.put("[774]", R.drawable.f774);
        EMOTION_CLASSIC_MAP.put("[775]", R.drawable.f775);
        EMOTION_CLASSIC_MAP.put("[776]", R.drawable.f776);
        EMOTION_CLASSIC_MAP.put("[777]", R.drawable.f777);
        EMOTION_CLASSIC_MAP.put("[778]", R.drawable.f778);
        EMOTION_CLASSIC_MAP.put("[779]", R.drawable.f779);
        EMOTION_CLASSIC_MAP.put("[780]", R.drawable.f780);
        EMOTION_CLASSIC_MAP.put("[781]", R.drawable.f781);
        EMOTION_CLASSIC_MAP.put("[782]", R.drawable.f782);
        EMOTION_CLASSIC_MAP.put("[783]", R.drawable.f783);
        EMOTION_CLASSIC_MAP.put("[784]", R.drawable.f784);
        EMOTION_CLASSIC_MAP.put("[785]", R.drawable.f785);
        EMOTION_CLASSIC_MAP.put("[786]", R.drawable.f786);
        EMOTION_CLASSIC_MAP.put("[787]", R.drawable.f787);
        EMOTION_CLASSIC_MAP.put("[788]", R.drawable.f788);
        EMOTION_CLASSIC_MAP.put("[789]", R.drawable.f789);
        EMOTION_CLASSIC_MAP.put("[790]", R.drawable.f790);
        EMOTION_CLASSIC_MAP.put("[791]", R.drawable.f791);
        EMOTION_CLASSIC_MAP.put("[792]", R.drawable.f792);
        EMOTION_CLASSIC_MAP.put("[793]", R.drawable.f793);
        EMOTION_CLASSIC_MAP.put("[794]", R.drawable.f794);
        EMOTION_CLASSIC_MAP.put("[795]", R.drawable.f795);
        EMOTION_CLASSIC_MAP.put("[796]", R.drawable.f796);
        EMOTION_CLASSIC_MAP.put("[797]", R.drawable.f797);
        EMOTION_CLASSIC_MAP.put("[798]", R.drawable.f798);
        EMOTION_CLASSIC_MAP.put("[799]", R.drawable.f799);
        EMOTION_CLASSIC_MAP.put("[800]", R.drawable.f800);
        EMOTION_CLASSIC_MAP.put("[801]", R.drawable.f801);
        EMOTION_CLASSIC_MAP.put("[802]", R.drawable.f802);
        EMOTION_CLASSIC_MAP.put("[803]", R.drawable.f803);
        EMOTION_CLASSIC_MAP.put("[804]", R.drawable.f804);
        EMOTION_CLASSIC_MAP.put("[805]", R.drawable.f805);
        EMOTION_CLASSIC_MAP.put("[806]", R.drawable.f806);
        EMOTION_CLASSIC_MAP.put("[807]", R.drawable.f807);
        EMOTION_CLASSIC_MAP.put("[808]", R.drawable.f808);
    }

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param EmotionType 表情类型标志符
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            default:
                Log.e(TAG, "getImgByName: the emotionMap is null!! Handle Yourself ");
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取表情数据
     *
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String, Integer> getEmotionMap(int EmotionType) {
        ArrayMap EmojiMap = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                EmojiMap = EMOTION_CLASSIC_MAP;
                break;
            default:
                EmojiMap = EMPTY_MAP;
                break;
        }
        return EmojiMap;
    }
}
