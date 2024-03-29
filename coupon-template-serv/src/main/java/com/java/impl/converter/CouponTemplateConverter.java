package com.java.impl.converter;

import com.java.api.beans.CouponTemplateInfo;
import com.java.dao.entity.CouponTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponTemplateConverter {
    public static CouponTemplateInfo convertToTemplateInfo(CouponTemplate template){
    return CouponTemplateInfo.builder()
            .id(template.getId())
            .name(template.getName())
            .desc(template.getDescription())
            .type(template.getCategory().getCode())
            .shopId(template.getShopId())
            .available(template.getAvailable())
            .rule(template.getRule())
            .build();
}
}
