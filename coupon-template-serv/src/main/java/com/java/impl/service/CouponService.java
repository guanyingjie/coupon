package com.java.impl.service;

import cn.hutool.core.bean.BeanUtil;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
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
        couponTemplateRepository.save(couponTemplate);
        return couponTemplateConverter.convertToTemplateInfo(couponTemplate);
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplate> templateInfo = couponTemplateRepository.findById(id);

        return couponTemplateConverter.convertToTemplateInfo(templateInfo.get());
    }

    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        CouponTemplate couponTemplate = couponTemplateRepository.findById(templateId)
                .orElseThrow(()->new IllegalArgumentException("invaild templateid"));
        CouponTemplate copeCoupon = new CouponTemplate();
        BeanUtils.copyProperties(couponTemplate,copeCoupon);
        copeCoupon.setAvailable(true);
        copeCoupon.setId(null);
        couponTemplateRepository.save(copeCoupon);
        return couponTemplateConverter.convertToTemplateInfo(couponTemplate);
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate couponTemplate = CouponTemplate.builder()
                .shopId(request.getShopId())
                .name(request.getName())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .build();
        Pageable pageable = PageRequest.of(request.getPage(),request.getPageSize());
        Page<CouponTemplate>result = couponTemplateRepository.findAll(Example.of(couponTemplate),pageable);
        List<CouponTemplateInfo> couponTemplateInfoList = result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());
        PagedCouponTemplateInfo response = PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfoList)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    @Override
    public void deleteTemplate(Long id) {
        couponTemplateRepository.deleteById(id);

    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate>couponTemplateList = couponTemplateRepository.findAllById(ids);

        Map<Long,CouponTemplateInfo>couponTemplateInfoList = couponTemplateList.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
        return couponTemplateInfoList;
    }
}
