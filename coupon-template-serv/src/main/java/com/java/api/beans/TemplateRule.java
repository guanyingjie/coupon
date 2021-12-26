package com.java.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {
    private Discount discount;
    private Integer limitation;
    private Long deadline;
}
