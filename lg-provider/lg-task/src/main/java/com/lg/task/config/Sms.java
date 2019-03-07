package com.lg.task.config;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: lg-master
 * @description:
 * @author: 徐子楼
 * @create: 2019-02-26 19:54
 **/
public class Sms {

    public static final String TPL_SEND_VERIFICATION_CODE_URL = "http://v.juhe.cn/sms/send?mobile=%s&tpl_id=%s" +
            "&tpl_value=%s%s%s%s%s&key=%s";

    public static final String KEY = "9af736635fcc218fe967b57420e22cbb";

    public static final String TPL_CODE = "%23code%23%3D";


    public static final String TPL_M = "%23m%23%3D";

    public static final String TPL_AND = "%26";

    public static final String TPL_SEND_VERIFICATION_CODE = "138002";

}
