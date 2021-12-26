package com.java.impl.service;

import com.java.api.beans.CouponTemplateInfo;
import com.java.api.beans.PagedCouponTemplateInfo;
import com.java.api.beans.TemplateRule;
import com.java.api.beans.TemplateSearchParams;
import com.java.api.enums.CouponType;
import com.java.dao.CouponTemplateRepository;
import com.java.dao.converter.CouponTypeConverter;
import com.java.dao.converter.RuleConverter;
import com.java.dao.entity.CouponTemplate;
import com.java.impl.converter.CouponTemplateConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CouponService implements CouponTemplateService{
    CouponTemplateRepository couponTemplateRepository;
    CouponTemplateConverter couponTemplateConverter;
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        //限制只允许100张一下优惠券
        if(request.getShopId()!=null){
            Integer count = couponTemplateRepository.countByShopIdAndAvailable(request.getShopId(),true);
            if(count>100){
                log.error("coupon count >100");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }
        CouponTemplate couponTemplate = CouponTemplate.builder()
                .available(true)
                .name(request.getName())
                .shopId(request.getShopId())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .rule(request.getRule())
                .build();
        return couponTemplateConverter.convertToTemplateInfo(couponTemplate);
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        return null;
    }

    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        return null;
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        return null;
    }

    @Override
    public void deleteTemplate(Long id) {

    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        return null;
    }
}
