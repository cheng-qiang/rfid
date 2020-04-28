package org.chen.rfid.model;

import java.util.List;

/**
 * @author 程强
 * @date 2020年04月10日 16:19
 * @Description:
 * 分页实体封装
 */
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
