package com.java.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.java.dao.entity.CouponTemplate;

import java.util.List;


public interface CouponTemplateRepository extends JpaRepository<CouponTemplate,Long> {
    List<CouponTemplateRepository> findAllByShopId(Long shopId);

    // IN查询 + 分页支持的语法
    Page<CouponTemplateRepository> findAllByIdIn(List<Long> Id, Pageable page);

    // 根据shop ID + 可用状态查询店铺有多少券模板
    Integer countByShopIdAndAvailable(Long shopId, Boolean available);

    // 将优惠券设置为不可用
    @Modifying
    @Query(value = "update coupon_template c set c.available = 0 where c.id = :id",nativeQuery = true)
    int makeCouponUnavailable(@Param("id") Long id);
}
