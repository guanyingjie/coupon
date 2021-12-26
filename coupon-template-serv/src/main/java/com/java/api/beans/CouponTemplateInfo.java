package com.java.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponTemplateInfo {
    private Long id;

    private String name;

    // 优惠券描述
    private String desc;

    // 优惠券类型
    private String type;

    // 适用门店 - 若无则为全店通用券
    private Long shopId;

    /** 优惠券规则 */
    private TemplateRule rule;

    private Boolean available;
}
