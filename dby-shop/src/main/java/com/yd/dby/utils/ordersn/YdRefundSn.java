package com.yd.dby.utils.ordersn;

import java.util.Calendar;


/**
 * 生成订单号
 * @author HH志
 * @date 2016-12-29 18:55:57
 * 生成规则    毫秒 + 用户id + 6位随机数
 */
public class YdRefundSn {

	public static String get(Integer id) {
		Calendar calendar = Calendar.getInstance();
		long value = calendar.getTime().getTime() + id + (int)((Math.random()*9+1)*100000000);
        return value+"";
    }
}