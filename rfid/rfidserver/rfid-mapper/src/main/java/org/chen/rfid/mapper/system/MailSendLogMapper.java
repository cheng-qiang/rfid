package org.chen.rfid.mapper.system;

import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.MailSendLog;

import java.util.Date;
import java.util.List;

/**
 * @author 言少钱
 * @date 2020年05月11日 16:34
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
public interface MailSendLogMapper {
    /**
     * 修改数据库中的记录，消息投递成功
     * @param msgId
     * @param status
     * @return
     */
    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    /**
     * 插入邮件发送日志
     * @param mailSendLog
     * @return
     */
    Integer insert(MailSendLog mailSendLog);

    /**
     * 查询需要处理的
     * @return
     */
    List<MailSendLog> findMailSendLogByStatus();

    /**
     * 更新重试次数
     * @param msgId
     * @param date
     * @return
     */
    Integer updateCount(@Param("msgId") String msgId, @Param("date") Date date);
}
