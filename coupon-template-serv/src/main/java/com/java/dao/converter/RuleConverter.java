package com.java.dao.converter;


import com.alibaba.fastjson.JSON;
import com.java.api.beans.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RuleConverter implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSON.parseObject(rule, TemplateRule.class);
    }
}
