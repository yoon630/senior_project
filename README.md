# medITech

index.html : 로그인 페이지

src 폴더 내 main>resource>templates 에 .html파일 있음

main.html : 로그인 완료후 의사정보와 해당 의사 담당 환자 리스트가 뜨는 html

detail.html : 각 환자의 진료 내역을 출력하는 페이지 html

insert.html : 환자 상태 입력 페이지 html

data.html : 통계페이지, q-learning 결과 학습 방향 파악

error.html : 상태입력 페이지에서 정보 잘못 입력 시 오류 페이지

patient.html : 새로운 환자 정보 입력 페이지

submit.html : 상태입력 후 q-learning 결과 action 제안 페이지


input태그 안에 name을 다음과 같이 지정해주세요.(오른쪽이 name에 들어갈 데이터임)

state : state

reward : reward

혈액검사수치 : blood

심전도수치 : ECG

혈압 : bloodPressure

x-ray 사진 : xRay

초음파 사진 : ultraSound

소견 : 미정

DTO를 통해 controller에서 html로, html에서 controller로 data를 주고받음

Entity를 통해 데이터베이스에 접근하여, data를 저장, 또는 가져오기

Service를 통해 서버내에서의 동작 구현 하기




