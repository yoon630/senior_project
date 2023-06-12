//var state = document.getElementById("action"); // action받아오는 변수

const actionList = [
  "action1, action2, action4",
  "action1, action2, action4",
  "action3, action4",
  "action4, action5",
  "action4, action5",
  "action2, action3",
];

const qValueList = [
  " Q(s1, a1) =",
  " Q(s1, a2) =",
  " Q(s1, a4) =",
  " Q(s2, a1) =",
  " Q(s2, a2) =",
  " Q(s2, a4) =",
  " Q(s3, a3) =",
  " Q(s3, a4) =",
  " Q(s4, a4) =",
  " Q(s4, a5) =",
  " Q(s5, a4) =",
  " Q(s5, a5) =",
  " Q(s6, a2) =",
  " Q(s6, a3) =",
];

function stateResult(state) {
    console.log("함수진입했음");
  if (state === "1") {
    document.getElementById("action").innerText = actionList[0];
    document.getElementById("q1").innerText = qValueList[0];
    document.getElementById("q2").innerText = qValueList[1];
    document.getElementById("q3").innerText = qValueList[2];
  } else if (state === "2") {
    document.getElementById("action").innerText = actionList[1];
    document.getElementById("q1").innerText = qValueList[3];
    document.getElementById("q2").innerText = qValueList[4];
    document.getElementById("q3").innerText = qValueList[5];
  } else if (state === "3") {
    document.getElementById("action").innerText = actionList[2];
    document.getElementById("q1").innerText = qValueList[6];
    document.getElementById("q2").innerText = qValueList[7];
  } else if (state === "4") {
    document.getElementById("action").innerText = actionList[3];
    document.getElementById("q1").innerText = qValueList[8];
    document.getElementById("q2").innerText = qValueList[9];
  } else if (state === "5") {
    document.getElementById("action").innerText = actionList[4];
    document.getElementById("q1").innerText = qValueList[10];
    document.getElementById("q2").innerText = qValueList[11];
  } else if (state === "6") {
    document.getElementById("action").innerText = actionList[5];
    document.getElementById("q1").innerText = qValueList[12];
    document.getElementById("q2").innerText = qValueList[13];
  }
}