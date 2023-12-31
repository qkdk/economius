package com.ssafy.economius.game.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SelectBuildingResponse {

    private Long player;
    private int buildingId;
    private Long buildingOwnerPlayer;
}
