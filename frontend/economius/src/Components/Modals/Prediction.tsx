import Modal from 'react-modal';
import { useEffect } from 'react';
import predictionimg from '/Prediction/prediction.png';
import * as S from './GlobalModal.stye';
import { ExitButton } from './GlobalModal.stye';
import { useRecoilState, useSetRecoilState, useRecoilValue } from 'recoil';
import { CallBackState, IsModalOpenState } from '/src/recoil/animation/atom';
import { GetPredictionState } from '/src/recoil/trading/atom.tsx';
import { PredictionState, PlayerToRollState, PlayerIdState } from '/src/recoil/game/atom.tsx';
import BigEvent from '/src/Components/Modals/BigEvent.tsx';
import { effectAudioPopup, effectAudioClick } from '/src/Audio';
import OtherPerson from './OtherPerson';
import BigEventRound from './BigEventRound';

function Prediction() {
    const playerId = useRecoilValue(PlayerIdState);
    const playerToRoll = useRecoilValue(PlayerToRollState);
    const [isModalOpen, setIsModalOpen] = useRecoilState(IsModalOpenState);
    const setGetPrediction = useSetRecoilState(GetPredictionState);
    const [prediction, setPrediction] = useRecoilState(PredictionState);
    const setCallBack = useSetRecoilState(CallBackState);

    useEffect(() => {
        if (prediction !== null) {
            console.log(prediction);
        }
    }, [prediction]);

    const closeModal = () => {
        setIsModalOpen(false);
        setGetPrediction(false);
        setPrediction(null);
        setCallBack(true);
    };

    // modal style
    const modalStyle: any = {
        overlay: {
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0,0,0,0.6)',
            zIndex: 10,
        },
        content: {
            display: 'flex',
            flexDirextion: 'column',
            backgroundColor: 'rgba(255,255,255,0.95)',
            overflow: 'auto',
            zIndex: 10,
            margin: 'auto',
            right: '25%',
            width: '500px',
            height: '350px',
            border: '5px solid white',
            borderRadius: '20px',
            padding: '0px',
        },
    };

    useEffect(() => {
        effectAudioPopup.play(); // 출력할 위치에 작성
    }, []);

    return playerToRoll === playerId ? (
        <>
            {prediction == null ? (
                <Modal isOpen={isModalOpen} style={modalStyle}>
                    <ExitButton onClick={() => (closeModal(), effectAudioClick.play())} src='/button/exit.png' alt='exit' />
                    <S.Main>
                        <S.Top>
                            <S.TopTitle>예언소</S.TopTitle>
                        </S.Top>

                        <S.Mid>
                            <S.MidImg src={predictionimg} alt='predictionimg' />
                            <S.MidDesc>다음에 일어날 경제 이슈를 예언해줍니다.</S.MidDesc>
                        </S.Mid>

                        <S.RoundButton onClick={() => (setGetPrediction(true), effectAudioClick.play())}>
                            <span>예언듣기</span>
                        </S.RoundButton>
                    </S.Main>
                </Modal>
            ) : (
                <BigEvent issue={prediction} predictionFlag={true}></BigEvent>
                // <BigEventRound />
            )}
        </>
    ) : (
        <OtherPerson />
    );
}

export default Prediction;
