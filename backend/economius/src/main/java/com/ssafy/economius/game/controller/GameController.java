package com.ssafy.economius.game.controller;

import com.ssafy.economius.game.dto.request.CalculateRequest;
import com.ssafy.economius.game.dto.request.FinishTurnRequest;
import com.ssafy.economius.game.dto.request.GameJoinRequest;
import com.ssafy.economius.game.dto.request.GameRoomExitRequest;
import com.ssafy.economius.game.dto.request.GameStartRequest;
import com.ssafy.economius.game.dto.response.CalculateResponse;
import com.ssafy.economius.game.dto.response.FinishTurnResponse;
import com.ssafy.economius.game.dto.response.GameJoinResponse;
import com.ssafy.economius.game.dto.response.GameRoomExitResponse;
import com.ssafy.economius.game.dto.response.GameStartResponse;
import com.ssafy.economius.game.service.GameService;
import com.ssafy.economius.game.service.FinishTurnService;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final SimpMessagingTemplate template;
    private final GameService gameService;

    // todo 추후에 하나의 서비스로 통합 예정
    private final FinishTurnService finishTurnService;

    @MessageMapping(value = "/{roomId}/join")
    public void join(@DestinationVariable int roomId, GameJoinRequest gameJoinRequest) {
        log.info(roomId + "번 방 " + gameJoinRequest.getNickname() + " 입장 요청");
        GameJoinResponse gameJoinResponse = gameService.join(
                roomId,
                gameJoinRequest.getPlayer(),
                gameJoinRequest.getNickname());

        Map<String, Object> headers = Map.of("success", true, "type", "join");
        template.convertAndSend("/sub/" + roomId, gameJoinResponse, headers);
        log.info(roomId + "번 방 " + gameJoinRequest.getNickname() + " 입장 완료");
    }

    @MessageMapping(value = "/{roomId}/start")
    public void start(@DestinationVariable int roomId, GameStartRequest gameStartRequest) {
        log.info("websocket start 호출자(방장): " + gameStartRequest.getHostPlayer());
        GameStartResponse gameStartResponse = gameService.start(roomId,
                gameStartRequest.getHostPlayer());

        Map<String, Object> headers = Map.of("success", true, "type", "start");
        template.convertAndSend("/sub/" + roomId, gameStartResponse, headers);
        log.info("websocket start 호출 결과: " + gameStartResponse);
    }

    @MessageMapping(value = "/{roomId}/exit")
    public void exit(@DestinationVariable int roomId, GameRoomExitRequest gameRoomExitRequest) {
        log.info(gameRoomExitRequest.getPlayer() + "번 플레이어" + roomId + "번 방 퇴장 요청");
        GameRoomExitResponse gameRoomExitResponse = gameService.exit(roomId,
                gameRoomExitRequest.getPlayer());

        Map<String, Object> headers = Map.of("success", true, "type", "exit");
        template.convertAndSend("/sub/" + roomId, gameRoomExitResponse, headers);
        log.info(gameRoomExitRequest.getPlayer() + "번 플레이어" + roomId + "번 방 퇴장 완료");
    }

    @MessageMapping(value = "/{roomId}/calculate")
    public void calculate(@DestinationVariable int roomId, @Payload CalculateRequest calculateRequest) {
        log.info(calculateRequest.getPlayer() + "번 플레이어" + roomId + "번 방 calculate 요청");
        CalculateResponse calculateResponse = gameService.calculate(
                roomId, calculateRequest.getPlayer());

        Map<String, Object> headers = Map.of("success", true, "type", "calculate");
        template.convertAndSend("/sub/" + roomId, calculateResponse, headers);
        log.info(calculateRequest.getPlayer() + "번 플레이어" + roomId + "번 방 calculate 요청에 대한 응답 완료");
    }


    @MessageMapping(value = "/{roomId}/finishTurn")
    public void finishTurn(@DestinationVariable int roomId, @Payload FinishTurnRequest finishTurnRequest) {
        log.info(roomId + " finishTurn 호출 : " + finishTurnRequest);
        FinishTurnResponse finishTurnResponse = finishTurnService.finish(roomId,
                finishTurnRequest.getPlayer());
        log.info(roomId + " finishTurn 결과 : " + finishTurnResponse + " by " + finishTurnRequest.getPlayer());

        Map<String, Object> headers = Map.of("success", true, "type", "finishTurn");
        template.convertAndSend("/sub/" + roomId, finishTurnResponse, headers);
    }
}
