# 🏥 medITch (3인 프로젝트)  
### 📅 진행 기간: 2023.3 ~ 2023.6  

**기존 임상 시험 방식의 한계를 해결하기 위해 강화학습(Q-learning)을 적용한 의료 시스템**을 개발하였습니다.  
기존 임상 시험은 **환자의 상태 변화와 무관하게 무작위로 치료 방법을 선택**하여 **치료 지연, 시간·비용 낭비 문제**를 초래합니다.  
이 프로젝트에서는 **강화학습을 활용해 환자의 상태에 맞는 최적의 치료 방법을 추천**하고,  
이를 **웹 시스템으로 구현하여 보다 효율적인 임상 시험 환경을 제공**하는 것을 목표로 했습니다.  

👨‍💻 **팀 구성**  
- **Frontend 개발**: 1명 [yoon630](https://github.com/yoon630)  
- **Backend 개발**: 1명  [minseo0102](https://github.com/minseo0102)
- **강화학습 개발**: 1명 [Ha-young-Cho](https://github.com/Ha-young-Cho)
## 💻 사용 기술  
- **Python**: 강화학습 모델 개발  
- **Spring Boot**: 백엔드 서버 구축  
- **HTML / CSS**: 프론트엔드 UI 개발  

## 🖇️역할 분담
- **강화학습 개발**
  - MDP설계
  - MDP 기반 Q-learning 개발
  - Q-table 설계계
     
- **백엔드 개발**
  - 환자 및 의사 데이터설계
  - Q-table기반 결과값 도출
  - 프론트와 연동
    
- **프론트엔드 개발**  
  - 의사 로그인 및 환자 관리 UI 설계  
  - 강화학습 후 도출된 결과를 시각적으로 출력  
  - 백엔드 및 강화학습 모델과의 데이터 연동  

## 🌐 Github Repository  
🔗 [medITch GitHub Repo](https://github.com/yoon630/senior_project)  








---------------------------------------------------------------------------------------------
## 개발 시 참고 사항
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

