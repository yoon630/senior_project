# medITech

** index 주의해야함. 실제 mdp에서 state 1 action 1이면 코드에서는 state 0 action 0임!

### qlearning에 입력받는 정보
- 현재 state(from html), 현재 qtable 값(from DB), 현재 epsilon 값(from DB)
- 변수명 : state, qTable[][] list에 저장, epsilon

### DB로 반환하는 정보
- 액션, reward, 다음 state, epsilon
- 변수명 : action, reward, nextState, policy.epsilon
- 혹시 순간순간의 qvalue 값도 반환해야한다면 qtable[state번호][action번호]로 접근.

### 통계 페이지에서 그래프 그릴 때 (DB에서 값 가져다 쓰기)
- x축 : 진료 횟수이기 때문에 단순 숫자
- y축 1 : epsilon 값 from episode_table
- y축 2 : state 6개의 max qvalue 값 from episode_table

