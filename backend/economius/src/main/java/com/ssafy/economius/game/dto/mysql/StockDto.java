package com.ssafy.economius.game.dto.mysql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {

    private Integer stockId;
    private Integer stockIndustryId;
    private String industry;
    private String type;
    private String company;
    private Integer initialValue;
}