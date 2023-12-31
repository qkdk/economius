package com.ssafy.economius.game.dto.mysql;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventStockDto {

    private Integer eventStockId;
    private Integer stockIndustryId;
    private String industry;
    private String name;
    private String description;
    private Integer rate;
    private String url;
}
