package org.chen.rfid.util;

/**
 * @author 程强
 * @date 2020年03月22日 11:28
 * @Description:
 */
public interface Constants {
    int SUCCESS_NUMBER = 1;
    String SUCCESS = "操作成功";
    String ERROR = "操作失败";
    String EXPORT_PATTERN_DATE = "yyyy年MM月dd日";
    /**基站信息模块*/
    String STATION_FILE_NAME = "定位基站信息表";
    String[] STATION_EXPORT_EXCEL_HEADERS_EN = new String[]{"id","code","name","version","ip","status","date"};
    String[] STATION_EXPORT_EXCEL_HEADERS_ZH = new String[]{"ID","编号","名称","版本号","IP地址","状态","购买日期"};
    int[] STATION_EXPORT_EXCEL_CELL_WIDTH = {10,50,20,20,50,10,50};

    //邮件发送模块
    /**消息投递中*/
    Integer DELIVERING = 0;
    /**消息投递成功*/
    Integer MAIL_SUCCESS = 1;
    /**消息投递失败*/
    Integer MAIL_FAILURE = 2;
    /**最大重试次数*/
    Integer MAX_TRY_COUNT = 3;
    /**消息超时时间*/
    Integer MSG_TIMEOUT = 1;
    /**访客邮件队列名称*/
    String MAIL_QUEUE_NAME = "chen.mail.visitors";
    /**交换机名称*/
    String MAIL_EXCHANGE_NAME = "chen.mail.exchange";
    /**路由名称*/
    String MAIL_ROUTING_KEY_NAME = "chen.mail.routing.key";
}
