# 🏥 medITch (3인 프로젝트)  
### 📅 진행 기간: 2023.3 ~ 2023.6  

**강화학습을 활용한 의료 시스템 개발 프로젝트**에서 프론트엔드 개발을 담당하였습니다.  
의사의 **로그인 시스템 구축**, **환자의 진료 기록 조회 UI 개발**, **강화학습 결과 시각화 기능**을 구현하였습니다.  

또한, 백엔드 및 강화학습 모델과의 **데이터 연동을 통해 원활한 시스템 작동을 지원**하였으며,  
**직관적인 UI 설계**를 통해 사용성이 높은 환경을 조성하는 데 기여하였습니다.  

👨‍💻 **팀 구성**  
- **Frontend 개발**: 1명 [yoon630](https://github.com/yoon630)  
- **Backend 개발**: 1명  [minseo]
- **강화학습 개발**: 1명 [hayoung]

## 💻 사용 기술  
- **Python**: 강화학습 모델 개발  
- **Spring Boot**: 백엔드 서버 구축  
- **HTML / CSS**: 프론트엔드 UI 개발  

## 🖇️ 내가 기여한 부분 (기여도 30%)  
- **프론트엔드 개발**  
  - 의사 로그인 및 환자 관리 UI 구축  
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

