package com.ssafy.economius.game.controller;

import com.ssafy.economius.common.exception.CustomWebsocketException;
import com.ssafy.economius.game.dto.request.SavingRequest;
import com.ssafy.economius.game.dto.response.SavingVisitResponse;
import com.ssafy.economius.game.dto.response.SavingResponse;
import com.ssafy.economius.game.service.SavingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SavingController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final SavingService savingService;

    @MessageMapping(value = "/{roomId}/bank")
    public void visitBank(@DestinationVariable int roomId, SavingRequest savingRequest) {
        Map<String, Object> headers = Map.of("success", true);
        SavingVisitResponse savingVisitResponse = savingService.visitBank(roomId, savingRequest);
        template.convertAndSend("/sub/" + roomId, savingVisitResponse, headers);
    }

    @MessageMapping(value = "/{roomId}/joinSavings")
    public void joinSavings(@DestinationVariable int roomId, SavingRequest savingRequest) {
        Map<String, Object> headers = Map.of("success", true);
        savingService.joinSavings(roomId, savingRequest);
        template.convertAndSend("/sub/" + roomId, headers);
    }


    @MessageMapping(value = "/{roomId}/stopSavings")
    public void stopSavings(@DestinationVariable int roomId, SavingRequest savingRequest) {
        Map<String, Object> headers = Map.of("success", true);
        savingService.stopSavings(roomId, savingRequest);
        template.convertAndSend("/sub/" + roomId, headers);
    }

}