# medITech

### qlearning에 입력받는 정보
- 현재 state, 현재 qtable 값
변수명 : state, qTable[][] list에 저장

### DB로 반환하는 정보
- 액션, reward, 다음 state, epsilon
변수명 : action, reward, nextState, policy.epsilon
혹시 순간순간의 qvalue 값도 반환해야한다면 qtable[state번호][action번호]로 접근.
index 주의해야함. 실제 mdp에서 state 1 action 1이면 여기서는 state 0 action 0임!

### 통계 페이지에서 그래프 그릴 때
- 그래프에서 x축은 환자 진료 횟수 / y축에는 epsilon 값, maxQ(s1), maxQ(s2), maxQ(s3), maxQ(s4), maxQ(s5), maxQ(s6)
변수명:
진료 횟수는 그냥 숫자로 뽑으면 됨
epsilon 값은 episodeEpsilons list에 저장되어있음
maxQ 값은 각 state마다 print1MaxQValues, print2MaxQvalues 등 이름의 list에 저장되어있음

