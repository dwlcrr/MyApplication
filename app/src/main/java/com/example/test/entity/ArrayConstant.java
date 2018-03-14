package com.example.test.entity;

import com.example.test.utils.other.Status;
/**
 * 数组常量类
 */
public class ArrayConstant {

    public static Status QuotationStatus = new Status(
            add(1, "审核中")
            , add(2, "待交易")
            , add(3, "已成交")
            , add(4, "已过期")
            , add(5, "已下架")
            , add(6, "已失效")
            , add(7, "未通过审核"));
    public static Status sex = new Status(
            add(1, "男")
            , add(2, "女"));
    public static Status InvoicingDates = new Status(
            add(1, "当月开票")
            , add(2, "下月开票"));
    public static Status DeliveryTypes = new Status(
            add(1, "买方自提")
            , add(2, "卖方送货")
            , add(3, "仓库过户"));
    public static Status SettlementModes = new Status(
            add(1, "线下支付")
            , add(2, "有色安全支付"));
    public static Status PaymentMethod = new Status(
            add(1, "款到发货")
            , add(2, "货到付款"));
    public static Status CompanyTitle = new Status(
            add(1, "匿名")
            , add(2, "公开"));
    public static Status OpenGrade = new Status(
            add(1, "对所有客户公开")
            , add(2, "对白名单客户公开"));
    public static Status Measure_unit = new Status(
            add(1, "吨")
            , add(2, "公斤")
            , add(3, "只")
            , add(4, "组"));
    public static Status UserVerifyStatus = new Status(
            add(-1, "未认证")
            , add(0, "审核中")
            , add(1, "已认证")
            , add(2, "已拒绝"));
    public static Status CompanyVerifyStatus = new Status(
            add(-2, "已拒绝")
            , add(-1, "未认证")
            , add(0, "审核中")
            , add(1, "已认证"));

    private static Status.KV add(int status, String value) {
        return new Status.KV(status, value);
    }

}
