package com.meditech.members.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QlearningService {
    private static class EpsilonGreedyPolicy {
        private double epsilon;

        public EpsilonGreedyPolicy(double epsilon) {
            this.epsilon = epsilon;
        }

        public int chooseAction(double[][] qTable, int state) {
            int numActions = qTable[state].length;
            Random rand = new Random();
            if (rand.nextDouble() < epsilon) {
                // 탐색
                return rand.nextInt(numActions);
            } else {
                // 활용
                int maxAction = 0;
                for (int i = 1; i < numActions; i++) {
                    if (qTable[state][i] > qTable[state][maxAction]) {
                        maxAction = i;
                    }
                }
                return maxAction;
            }
        }

        public void updateEpsilon(double decayRate) {
            epsilon *= decayRate;
        }
    }
    private static final int NUM_TERMINAL = 3;
    private static final int NUM_STATES = 9; // state + terminal
    private static final int NUM_ACTIONS = 5;

    private static final int[][] ACTION = {
            {0, 1, 3},   // 상태 1에서 가능한 행동들
            {0, 1, 3},   // 상태 2에서 가능한 행동들
            {2, 3},   // 상태 3에서 가능한 행동들
            {3, 4},   // 상태 4에서 가능한 행동들
            {3, 4},   // 상태 5에서 가능한 행동들
            {1, 2}  // 상태 6에서 가능한 행동들
    };

    private static double[][] qTable;
    private static double[][] rewards;

    private static List<Double> episodeEpsilons = new ArrayList<>();
    private static List<Double> avgMaxQValues = new ArrayList<>();
    private static List<List<Double>> printMaxQValues = new ArrayList<>();

    private static double generateGaussianReward(double mean, double stdDev) {
        Random rand = new Random();
        return mean + stdDev * rand.nextGaussian();
    }

    public void qLearningAlgorithm() {
        qTable = new double[NUM_STATES][NUM_ACTIONS];
        rewards = new double[NUM_STATES][NUM_ACTIONS];

        // Q-Table 초기화
        for (int i = 0; i < NUM_STATES; i++) {
            for (int j = 0; j < NUM_ACTIONS; j++) {
                qTable[i][j] = -50.0;
            }
        }

        double alpha = 0.5;              // 학습률
        double gamma = 0.9;              // 할인율
        int numEpisodes = 1000;          // 학습 반복 횟수
        double initialEpsilon = 0.5;     // 초기 입실론 값
        double minEpsilon = 0.01;        // 최소 입실론 값
        double decayRate = 0.99;         // 입실론 감소 비율

        EpsilonGreedyPolicy policy = new EpsilonGreedyPolicy(initialEpsilon);

        // 학습 반복문
        for (int episode = 0; episode < numEpisodes; episode++) {
            double epsilon = Math.max(minEpsilon, initialEpsilon * Math.pow(decayRate, episode));
            episodeEpsilons.add(epsilon);

            double maxQValueSum = 0.0;
            List<Double> maxQValues = new ArrayList<>();

            // 에피소드 진행
            int state = new Random().nextInt(NUM_STATES);
            while (state < NUM_TERMINAL) {
                int action = policy.chooseAction(qTable, state);
                double reward = generateGaussianReward(0, 1); // 보상 생성

                int nextState = ACTION[state][action];
                double maxQValue = 0.0;
                if (nextState >= NUM_TERMINAL) {
                    maxQValue = Math.max(qTable[nextState][0], qTable[nextState][1]);
                }

                // Q-Table 업데이트
                qTable[state][action] += alpha * (reward + gamma * maxQValue - qTable[state][action]);

                maxQValues.add(maxQValue);
                maxQValueSum += maxQValue;

                state = nextState;
            }

            avgMaxQValues.add(maxQValueSum / maxQValues.size());
            printMaxQValues.add(maxQValues);
        }

        // 학습 결과 출력 -> db에 저장되도록 수정(repository)
        for (int i = 0; i < NUM_STATES; i++) {
            for (int j = 0; j < NUM_ACTIONS; j++) {
                System.out.printf("Q[%d][%d]: %.2f\n", i, j, qTable[i][j]);
            }
        }

        // 입실론 값과 최대 Q값의 평균 그래프 출력 등의 추가 작업 가능
    }
}
