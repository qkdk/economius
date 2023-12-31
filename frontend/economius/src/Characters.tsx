// @ts-ignore
import { useState, useEffect } from 'react';
import { Aligator, Bear, Bird, Butterfly, Camel, Cat, Chicken, Cow, Deer, Dog } from './Components/Characters';
import { useRecoilState } from 'recoil';
import { NowPlayerPositionState, IsMovingState, MoveDistState, RoomJoinUsersIdState } from '/src/recoil/animation/atom';
import { PlayerToRollState } from './recoil/game/atom';

// 캐릭터 컴포넌트
const CharacterComponent = [null, Aligator, Bear, Bird, Butterfly, Camel, Cat, Chicken, Cow, Deer, Dog];

// 캐릭터 불투명도
const opacity = 0.3;

// 캐릭터 애니메이션 변수
const radius = 0.5; // 점프 높이, 반원의 반지름
const steps = 10; // 애니메이션당 프레임 수

function Characters({ CharacterArr }) {
    // 현재 플레이어
    // 플레이어 배열
    // 현재 플레이어 최종 위치
    const [playerToRoll, setPlayerToRoll] = useRecoilState(PlayerToRollState);
    const [roomJoinUsersId, setRoomJoinUsersId] = useRecoilState<number[]>(RoomJoinUsersIdState);
    const [nowPlayerPosition, setNowPlayerPosition] = useRecoilState(NowPlayerPositionState);

    // 캐릭터 이동 여부
    // 캐릭터 이동 거리
    const [isMoving, setIsMoving] = useRecoilState(IsMovingState);
    const [moveDist, setMoveDist] = useRecoilState(MoveDistState);

    // 플레이어의 닉네임, 캐릭터(idx)

    const [player1, setPlayer1] = useState<[string, number]>(['P1', CharacterArr[0]]);
    const [player2, setPlayer2] = useState<[string, number]>(['P2', CharacterArr[1]]);
    const [player3, setPlayer3] = useState<[string, number]>(['P3', CharacterArr[2]]);
    const [player4, setPlayer4] = useState<[string, number]>(['P4', CharacterArr[3]]);

    // 플레이어의 캐릭터 컴포넌트
    const Player1 = CharacterComponent[player1[1]];
    const Player2 = CharacterComponent[player2[1]];
    const Player3 = CharacterComponent[player3[1]];
    const Player4 = CharacterComponent[player4[1]];

    // 플레이어의 위치(idx)
    const [positionIdx1, setPositionIdx1] = useState(0);
    const [positionIdx2, setPositionIdx2] = useState(0);
    const [positionIdx3, setPositionIdx3] = useState(0);
    const [positionIdx4, setPositionIdx4] = useState(0);
    const positionIdxs = [positionIdx1, positionIdx2, positionIdx3, positionIdx4];
    const setPositionIdxs = [setPositionIdx1, setPositionIdx2, setPositionIdx3, setPositionIdx4];

    // 플레이어의 불투명도
    const [opacity1, setOpacity1] = useState(opacity);
    const [opacity2, setOpacity2] = useState(opacity);
    const [opacity3, setOpacity3] = useState(opacity);
    const [opacity4, setOpacity4] = useState(opacity);
    const setOpacities = [setOpacity1, setOpacity2, setOpacity3, setOpacity4];

    // 현재 차례인 플레이어만 불투명
    useEffect(() => {
        setOpacity1(opacity), setOpacity2(opacity), setOpacity3(opacity), setOpacity4(opacity); // 전부 투명
        if (!roomJoinUsersId) return;
        if (roomJoinUsersId.indexOf(playerToRoll) === -1) return;
        setOpacities[roomJoinUsersId.indexOf(playerToRoll)](1); // 현재 플레이어만 불투명
    }, [playerToRoll]);

    // 자기 차례인 플레이어만 위치(idx)를 변경함
    useEffect(() => {
        if (!isMoving) return;
        if (!roomJoinUsersId) return;
        if (roomJoinUsersId.indexOf(playerToRoll) === -1) return;
        setPositionIdxs[roomJoinUsersId.indexOf(playerToRoll)](idx => (idx + moveDist) & 31); // (현재 위치 + 이동 거리) % 맵 칸 수
        setNowPlayerPosition((positionIdxs[roomJoinUsersId.indexOf(playerToRoll)] + moveDist) & 31);
    }, [isMoving]);

    return (
        <>
            {/* 플레이어가 골랐다면 렌더링 */}
            {Player1 && <Player1 positionIdx={positionIdx1} moveDist={moveDist} opacity={opacity1} radius={radius} steps={steps} />}
            {Player2 && <Player2 positionIdx={positionIdx2} moveDist={moveDist} opacity={opacity2} radius={radius} steps={steps} />}
            {Player3 && <Player3 positionIdx={positionIdx3} moveDist={moveDist} opacity={opacity3} radius={radius} steps={steps} />}
            {Player4 && <Player4 positionIdx={positionIdx4} moveDist={moveDist} opacity={opacity4} radius={radius} steps={steps} />}
        </>
    );
}

export default Characters;
