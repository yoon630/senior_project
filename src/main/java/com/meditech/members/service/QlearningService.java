package com.meditech.members.service;

import com.meditech.members.dto.PatientRecordDTO;
import com.meditech.members.entity.*;
import com.meditech.members.repository.EpisodeRepository;
import com.meditech.members.repository.PatientRecordRepository;
import com.meditech.members.repository.QtableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QlearningService {
//    private static class EpsilonGreedyPolicy {
//        private double epsilon;
//
//        public EpsilonGreedyPolicy(double epsilon) {
//            this.epsilon = epsilon;
//        }
//
//        public int chooseAction(double[][] qTable, int state) {
//            int numActions = qTable[state].length;
//            Random rand = new Random();
//            if (rand.nextDouble() < epsilon) {
//                // 탐색
//                return rand.nextInt(numActions);
//            } else {
//                // 활용
//                int maxAction = 0;
//                for (int i = 1; i < numActions; i++) {
//                    if (qTable[state][i] > qTable[state][maxAction]) {
//                        maxAction = i;
//                    }
//                }
//                return maxAction;
//            }
//        }
//
//        public void updateEpsilon(double decayRate) {
//            epsilon *= decayRate;
//        }
//    }
//    private static final int NUM_TERMINAL = 3;
//    private static final int NUM_STATES = 9; // state + terminal
//    private static final int NUM_ACTIONS = 5;
//
//    private static final int[][] ACTION = {
//            {0, 1, 3},   // 상태 1에서 가능한 행동들
//            {0, 1, 3},   // 상태 2에서 가능한 행동들
//            {2, 3},   // 상태 3에서 가능한 행동들
//            {3, 4},   // 상태 4에서 가능한 행동들
//            {3, 4},   // 상태 5에서 가능한 행동들
//            {1, 2}  // 상태 6에서 가능한 행동들
//    };
//
//    private static double[][] qTable;
//    private static double[][] rewards;
//
//    private static List<Double> episodeEpsilons = new ArrayList<>();
//    private static List<Double> avgMaxQValues = new ArrayList<>();
//    private static List<List<Double>> printMaxQValues = new ArrayList<>();
//
//    private static double generateGaussianReward(double mean, double stdDev) {
//        Random rand = new Random();
//        return mean + stdDev * rand.nextGaussian();
//    }
//
//    public void qLearningAlgorithm() {
//        qTable = new double[NUM_STATES][NUM_ACTIONS];
//        rewards = new double[NUM_STATES][NUM_ACTIONS];
//
//        // Q-Table 초기화
//        for (int i = 0; i < NUM_STATES; i++) {
//            for (int j = 0; j < NUM_ACTIONS; j++) {
//                qTable[i][j] = -50.0;
//            }
//        }
//
//        double alpha = 0.5;              // 학습률
//        double gamma = 0.9;              // 할인율
//        int numEpisodes = 1000;          // 학습 반복 횟수
//        double initialEpsilon = 0.5;     // 초기 입실론 값
//        double minEpsilon = 0.01;        // 최소 입실론 값
//        double decayRate = 0.99;         // 입실론 감소 비율
//
//        EpsilonGreedyPolicy policy = new EpsilonGreedyPolicy(initialEpsilon);
//
//        // 학습 반복문
//        for (int episode = 0; episode < numEpisodes; episode++) {
//            double epsilon = Math.max(minEpsilon, initialEpsilon * Math.pow(decayRate, episode));
//            episodeEpsilons.add(epsilon);
//
//            double maxQValueSum = 0.0;
//            List<Double> maxQValues = new ArrayList<>();
//
//            // 에피소드 진행
//            int state = new Random().nextInt(NUM_STATES);
//            while (state < NUM_TERMINAL) {
//                int action = policy.chooseAction(qTable, state);
//                double reward = generateGaussianReward(0, 1); // 보상 생성
//
//                int nextState = ACTION[state][action];
//                double maxQValue = 0.0;
//                if (nextState >= NUM_TERMINAL) {
//                    maxQValue = Math.max(qTable[nextState][0], qTable[nextState][1]);
//                }
//
//                // Q-Table 업데이트
//                qTable[state][action] += alpha * (reward + gamma * maxQValue - qTable[state][action]);
//
//                maxQValues.add(maxQValue);
//                maxQValueSum += maxQValue;
//
//                state = nextState;
//            }
//
//            avgMaxQValues.add(maxQValueSum / maxQValues.size());
//            printMaxQValues.add(maxQValues);
//        }
//
//        // 학습 결과 출력 -> db에 저장되도록 수정(repository)
//        for (int i = 0; i < NUM_STATES; i++) {
//            for (int j = 0; j < NUM_ACTIONS; j++) {
//                System.out.printf("Q[%d][%d]: %.2f\n", i, j, qTable[i][j]);
//            }
//        }
//
//        // 입실론 값과 최대 Q값의 평균 그래프 출력 등의 추가 작업 가능
//    }

    public static class EpsilonGreedyPolicy {
        private double epsilon;
        private int[][] action = {{0,1,3}, {0,1,3}, {2,3}, {3,4}, {3,4}, {1,2}};

        public EpsilonGreedyPolicy(double epsilon) {
            this.epsilon = epsilon;
        }

        public int chooseAction(double[][] qTable, int state) {
            int numActions = action[state].length; //시행할 수 있는 action 개수
            Random rand = new Random();

            if (rand.nextDouble() < epsilon) { // exploration
                int randomIndex = rand.nextInt(numActions);
                return action[state][randomIndex];
            }
            else { //exploitation
                int max_index = action[state][0];

                for (int i = 1; i < numActions; i++) {
                    if (qTable[state][action[state][i]] > qTable[state][max_index]) {
                        max_index = action[state][i];
                    }
                }

                return max_index;
            }
        }

        public void updateEpsilon(double decayRate, double minEpsilon) {
            epsilon = Math.max(epsilon *= decayRate, minEpsilon);
        }
    }

    private static final int NUM_TERMINAL = 3;
    private static final int NUM_STATES = 9; // state + terminal
    private static final int NUM_ACTIONS = 5;

    private final int[][] ACTION = {
            {0, 1, 3},   // 상태 1에서 가능한 행동들
            {0, 1, 3},   // 상태 2에서 가능한 행동들
            {2, 3},   // 상태 3에서 가능한 행동들
            {3, 4},   // 상태 4에서 가능한 행동들
            {3, 4},   // 상태 5에서 가능한 행동들
            {1, 2}  // 상태 6에서 가능한 행동들
    };

    private double[][] qTable;

    private double generateGaussianReward(double mean, double stdDev) {
        Random rand = new Random();
        return mean + stdDev * rand.nextGaussian();
    }

    private final QtableRepository qtableRepository;
    private final EpisodeRepository episodeRepository;
    private final PatientRecordRepository patientRecordRepository;
    public void qlearning(PatientRecordDTO patientRecordDTO, HttpSession session) {
        qTable = new double[NUM_STATES][NUM_ACTIONS];

        //!!!!!!!!!!!!!!!!!!!!입력: db에서 qtable 값 불러와서 초기화해야함!!!!!!!!!!!!!!!!!
        //index 잘 확인하기. db에서 state1은 코드에서 state index 0임. 액션도 마찬가지!
        // Q-Table 초기화
        for (int i = 0; i < NUM_STATES; i++) {
            for (int j = 0; j < NUM_ACTIONS; j++) {
                qTable[i][j] = qtableRepository.findMaxQ((i+1)*10+(j+1)).orElse(0.0);
                //q테이블에서 id가 (i+1)*10+(j+1)인 레코드의 maxQ값을 할당
            }
        }

        //하이퍼파라미터
        double alpha = 0.005;              // 학습률
        double gamma = 0.99;              // 할인율
        double initialEpsilon = 0.9;     // 초기 입실론 값
        double minEpsilon = 0.05;        // 최소 입실론 값
        double decayRate = 0.9995;         // 입실론 감소 비율

        double reward = 0.0;
        int nextState = 0;
        double randValue;//0.0해줘도 되고 안해줘도 됨

        //!!!!!!!!!!!!!!!입력: 여기에 전달 받은 epsilon 값 넣기!!!!!!!!!!!!
        double epsilon = episodeRepository.findLatestEpsilon();
//        double epsilon = 0.9;


        EpsilonGreedyPolicy policy = new EpsilonGreedyPolicy(epsilon);

        // !!!!!!!!!!!!!!!!!!!입력: 여기에 html로부터 전달 받은 state를 넣어야 함!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!근데 index 차이 때문에 db값에서의 state - 1 값이 state에 들어가게 !!!!!!!!!!
        int state = patientRecordDTO.getState()-1;
//        int state = new Random().nextInt(NUM_STATES);

        int action = policy.chooseAction(qTable, state);

        if (state == 0) { // S1
            if (action == 0) { // a1
                randValue = Math.random();
                if (randValue < 0.7) {
                    reward = generateGaussianReward(-40.0, 1.0);
                    nextState = 1; // s2
                } else {
                    reward = generateGaussianReward(-20.0, 0.5);
                    nextState = 2; // s3
                }
            } else if (action == 1) { // a2
                reward = generateGaussianReward(0.0, 1.0);
                nextState = 2; // s3
            } else if (action == 3) { // a4
                randValue = Math.random();
                if (randValue < 0.8) {
                    reward = generateGaussianReward(-30.0, 8.0);
                    nextState = 1; // s2
                } else {
                    reward = generateGaussianReward(-150.0, 3.0);
                    nextState = 8; // t3
                }
            }
        } else if (state == 1) { // s2
            if (action == 0) { // a1
                randValue = Math.random();
                if (randValue < 0.8) {
                    reward = generateGaussianReward(20.0, 1.0);
                    nextState = 6; // t1
                } else {
                    reward = generateGaussianReward(-80.0, 2.0);
                    nextState = 7; // t2
                }
            } else if (action == 1) { // a2
                reward = generateGaussianReward(-90.0, 1.0);
                nextState = 7; // t2
            } else if (action == 3) { // a4
                reward = generateGaussianReward(30.0, 3.0);
                nextState = 6; // t1
            }
        } else if (state == 2) { // s3
            if (action == 2) { // a3
                reward = generateGaussianReward(20.0, 2.0);
                nextState = 6; // t1
            } else if (action == 3) { // a4
                reward = generateGaussianReward(-80.0, 0.5);
                //nextState = 0; // s1 //이미 0ㅇ로 초기화 되있어서 굳이 한번더 할당안해줘도 됨
            }
        } else if (state == 3) { // s4
            if (action == 3) { // a4
                randValue = Math.random();
                if (randValue < 0.9) {
                    reward = generateGaussianReward(0.0, 5.0);
                    nextState = 4; // s5
                } else {
                    reward = generateGaussianReward(-100.0, 1.0);
                    nextState = 5; // s6
                }
            } else if (action == 4) { // a5
                randValue = Math.random();
                if (randValue < 0.6) {
                    reward = generateGaussianReward(-10.0, 4.0);
                    nextState = 4; // s5
                } else if (randValue >= 0.6 && randValue < 0.9) {
                    reward = generateGaussianReward(-50.0, 1.0);
                    nextState = 3; // s4
                } else {
                    reward = generateGaussianReward(-80.0, 3.0);
                    nextState = 7; // t2
                }
            }
        } else if (state == 4) { // s5
            if (action == 3) { // a4
                randValue = Math.random();
                if (randValue < 0.7) {
                    reward = generateGaussianReward(-70.0, 2.0);
                    nextState = 7; // t2
                } else {
                    reward = generateGaussianReward(25.0, 2.0);
                    nextState = 6; // t1
                }
            } else if (action == 4) { // a5
                randValue = Math.random();
                if (randValue < 0.8) {
                    reward = generateGaussianReward(-10.0, 0.8);
                    nextState = 2; // s3
                } else {
                    reward = generateGaussianReward(-40.0, 0.5);
                    nextState = 4; // s5
                }
            }
        } else if (state == 5) { // s6
            if (action == 1) { // a2
                randValue = Math.random();
                if (randValue < 0.75) {
                    reward = generateGaussianReward(-10.0, 3.0);
                    nextState = 4; // s5
                } else {
                    reward = generateGaussianReward(0.0, 1.0);
                    nextState = 2; // s3
                }
            } else if (action == 2) { // a3
                reward = generateGaussianReward(-20.0, 4.0);
                nextState = 1; // s2
            }
        }

        // Q-Table 업데이트
        double maxNextQValue = qTable[nextState][ACTION[nextState][0]];
        for (int j = 1; j < ACTION[nextState].length; j++) {
            if (qTable[nextState][ACTION[nextState][j]] > maxNextQValue) {
                maxNextQValue = qTable[nextState][ACTION[nextState][j]];
            }
        }
        qTable[state][action] += alpha * (reward + gamma * maxNextQValue - qTable[state][action]);

        policy.updateEpsilon(decayRate, minEpsilon); //epsilon 업데이트

        //그래프를 위한 maxqvalue
        double maxqvalue1 = qTable[0][ACTION[0][0]];
        for (int j = 1; j < ACTION[0].length; j++) {
            if (qTable[0][ACTION[0][j]] > maxqvalue1) {
                maxqvalue1 = qTable[0][ACTION[0][j]];
            }
        }
        double maxqvalue2 = qTable[1][ACTION[1][0]];
        for (int j = 1; j < ACTION[1].length; j++) {
            if (qTable[1][ACTION[1][j]] > maxqvalue2) {
                maxqvalue2 = qTable[1][ACTION[1][j]];
            }
        }
        double maxqvalue3 = qTable[2][ACTION[2][0]];
        for (int j = 1; j < ACTION[2].length; j++) {
            if (qTable[2][ACTION[2][j]] > maxqvalue3) {
                maxqvalue3 = qTable[2][ACTION[2][j]];
            }
        }
        double maxqvalue4 = qTable[3][ACTION[3][0]];
        for (int j = 1; j < ACTION[3].length; j++) {
            if (qTable[3][ACTION[3][j]] > maxqvalue4) {
                maxqvalue4 = qTable[3][ACTION[3][j]];
            }
        }
        double maxqvalue5 = qTable[4][ACTION[4][0]];
        for (int j = 1; j < ACTION[4].length; j++) {
            if (qTable[4][ACTION[4][j]] > maxqvalue5) {
                maxqvalue5 = qTable[4][ACTION[4][j]];
            }
        }
        double maxqvalue6 = qTable[5][ACTION[5][0]];
        for (int j = 1; j < ACTION[5].length; j++) {
            if (qTable[5][ACTION[5][j]] > maxqvalue6) {
                maxqvalue6 = qTable[5][ACTION[5][j]];
            }
        }

        // 학습 결과 출력
        for (int i = 0; i < NUM_STATES - NUM_TERMINAL; i++) {
            for (int j = 0; j < NUM_ACTIONS; j++) {
                System.out.printf("Q[%d][%d]: %.2f\n", i + 1, j + 1, qTable[i][j]);
            }
        }
        //action, reward 값 추가 업데이트
        patientRecordRepository.updateRewardAndAction(patientRecordDTO.getPatientId(), patientRecordDTO.getTurn(), reward, action+1);
        //다음 방문 회차에 next state 저장
        Optional<Integer> latestTurn = patientRecordRepository.findLatestTurnByPatientId(patientRecordDTO.getPatientId());
        PatientRecordEntity patientRecordEntity = new PatientRecordEntity();
        Long patientId = patientRecordDTO.getPatientId();//long
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId((long)session.getAttribute("loginId"));
        patientEntity.setMemberEntity(memberEntity);
        PatientRecordId recordId = new PatientRecordId();
        recordId.setPatientEntity(patientEntity);//patient id를 가져오기 위한 patientEntity타입
        recordId.setTurn(latestTurn.orElse(0)+1);

        patientRecordEntity.setId(recordId);
        patientRecordEntity.setState(nextState+1);//id와 nextstate만을 담은 entity객체 생성 완료
        patientRecordRepository.save(patientRecordEntity);//nextstate를 담은 [다음 방문 회차] 레코드 삽입

        //각 state에서의 maxQ
        int maxid = episodeRepository.findMaxEpisodeId();
        episodeRepository.updateEpisode(maxqvalue1, maxqvalue2, maxqvalue3, maxqvalue4, maxqvalue5, maxqvalue6, maxid);

        //업데이트 된 엡실론 값 다음 에피소드에서 사용하도록 저장(레코드 추가)
        EpisodeEntity episodeEntity = new EpisodeEntity();
        episodeEntity.setEpsilon(policy.epsilon);
        episodeRepository.save(episodeEntity);//엡실론 저장을 위해 에피소드에 레코드 추가

        //q_table업데이트
        for (int i = 0; i < NUM_STATES; i++) {
            for (int j = 0; j < NUM_ACTIONS; j++) {
                qtableRepository.updateMaxQ((i+1)*10+(j+1),qTable[i][j]);//해당 인덱스(id)를 가지고 maxQ업데이트
            }
        }

        session.setAttribute("patientAction",action+1);
        session.setAttribute("patientReward",reward);
        session.setAttribute("patientNextState",nextState+1);

    }

        //!!!!!!!!!!!!DB에 반환할 내용 정리!!!!!!!!!!!
        //action -> patient_record_table
        //reward -> patient_record_table
        //nextState -> patient_record_table (다음 방문 회차에)
        //policy.epsilon -> episode_table
        //qtable -> q_table
        //maxqvalue1, maxqvalue2, maxqvalue3, maxqvalue4, maxqvalue5, maxqvalue6 -> episode_table
}
