package com.dunera.seckill.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lyx
 * @date 2018/11/17
 */
public class SecKillGoodDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long secKillId;

    private Long goodsId;

    private String name;

    private String image;

    private BigDecimal price;

    private BigDecimal secKillPrice;

    private Integer secKillStock;

    private Date startTime;

    private Date endTime;

    private String details;

    public Long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(Long secKillId) {
        this.secKillId = secKillId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSecKillPrice() {
        return secKillPrice;
    }

    public void setSecKillPrice(BigDecimal secKillPrice) {
        this.secKillPrice = secKillPrice;
    }

    public Integer getSecKillStock() {
        return secKillStock;
    }

    public void setSecKillStock(Integer secKillStock) {
        this.secKillStock = secKillStock;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
