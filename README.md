# medITech

** index 주의해야함. 실제 mdp에서 state 1 action 1이면 코드에서는 state 0 action 0임!

### qlearning에 입력받는 정보
- 현재 state(from html), 현재 qtable 값(from DB), 현재 epsilon 값(from DB)
- 변수명 : state / qTable[][] list에 저장 / epsilon

### DB로 반환하는 정보
- 액션, reward, 다음 state, epsilon, qtable, 각 state의 maxQvalue (6개)
- 변수명 : action(to patient_record_table) / reward(to patient_record_table) / nextState(to patient_record_table 단 다음 방문 회차 record) / policy.epsilon(to episode_table) / qTable[state 번호][action 번호](to q_table) / maxqvalue1 ~ maxqvalue6(to episode_table)

### 통계 페이지에서 그래프 그릴 때 (DB에서 값 가져다 쓰기)
- x축 : 진료 횟수. id from episode_table
- y축 1 : epsilon 값 from episode_table
- y축 2 : state 6개의 max qvalue 값 from episode_table

