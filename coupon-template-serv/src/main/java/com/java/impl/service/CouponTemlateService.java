package com.java.impl.service;

import com.java.api.beans.CouponTemplateInfo;
import com.java.api.beans.PagedCouponTemplateInfo;
import com.java.api.beans.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

interface CouponTemplateService {
    // 创建优惠券模板
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

    // 通过模板ID查询优惠券模板
    CouponTemplateInfo loadTemplateInfo(Long id);

    // 克隆券模板
    CouponTemplateInfo cloneTemplate(Long templateId);


    // 模板查询（分页）
    PagedCouponTemplateInfo search(TemplateSearchParams request);

    // 删除券模板
    void deleteTemplate(Long id);

    //批量读取模板
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);

    // 完整方法列表请至源码查看
}
