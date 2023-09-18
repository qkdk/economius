package com.ssafy.economius.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    @MessageMapping(value = "/{roomId}/enter")
    public void enter() {
        template.convertAndSend("");
    }

    @MessageMapping(value = "/{roomId}/calculate")
    public void calculate() {
        template.convertAndSend("");
    }

}
