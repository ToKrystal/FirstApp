package com.chao.bookviki.app;

import android.os.Environment;

import java.io.File;

/**
 * 常量
 */
public class Constants {

    //================= TYPE ====================

    public static final int TYPE_BOOK = 113;

    public static final int JING_XUAN_CONSTANT = 201;
    public static final int YU_LU_CONSTATNT = 202;

    public static final int TYPE_WEB = 104;

    public static final int TYPE_GIRL = 105;

    public static final int TYPE_JINGXUAN1 = 107;

    public static final int TYPE_GOLD = 108;

    public static final int TYPE_SETTING = 110;

    public static final int TYPE_LIKE = 111;

    public static final int TYPE_ABOUT = 112;

    public static final int TYPE_USER_INFO = 114;


    //================= KEY ====================
    public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

    //================= PREFERENCE ====================

    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    public static final String SP_LIKE_POINT = "like_point";

    public static final String SP_VERSION_POINT = "version_point";

    public static final String SP_MANAGER_POINT = "manager_point";

    public static final String BAI_TUI_BIND = "baidutuisongbind_state";

    public static final String BAI_DU_YUN_CHANNLEID = "baiduyunchannleid";

    public static final String PUSH_STATE_NOT_LOGIN = "pushstatenotlogin";

    //================= INTENT ====================

    public static final String JING_XUAN = "jingxuan";
    public static final String JING_XUAN_CODE = "jingxuan_code";

    public static final String YU_LU = "yulu";
    public static final String YU_LU_CODE = "yulu_code";

    public static final String IT_DETAIL_TITLE = "title";

    public static final String IT_DETAIL_URL = "url";

    public static final String IT_DETAIL_IMG_URL = "img_url";

    public static final String IT_DETAIL_ID = "id";

    public static final String IT_DETAIL_TYPE = "type";

    public static final String IT_GOLD_TYPE = "type";
    public static final String IT_BOOK_TYPE = "type";

    public static final String IT_GOLD_TYPE_STR = "type_str";
    public static final String IT_BOOK_TYPE_STR = "type_str";

    public static final String IT_GOLD_MANAGER = "manager";

}
