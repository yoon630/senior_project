// 엑스레이랑 초음파 사진 입력안하면 - 표시하게 하는 부분
var input = document.getElementById("xray-img").value;
if (input === "null") {
  Imgevent();
}
function Imgevent() {
  errorMessage = document.getElementById("xray-img");
  errorMessage.style.display = "block";
}

// State 한글로 출력하도록 하는 부분
var state = document.getElementById("state");
var stateValue = parseInt(state.value);

var stateList = ["미열", "감기", "구토", "설사", "고열"];

if (!isNaN(stateValue) && stateValue >= 0 && stateValue < stateList.length) {
  innerText = stateList[stateValue];
  //리스트에 있는 index 값을 state 로 입력된 값으로 출력
} else {
  innerText = "invalid state";
}
