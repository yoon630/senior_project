var input = document.getElementById("xray-img").value;
if (input === "null") {
  Imgevent();
}
function Imgevent() {
  errorMessage = document.getElementById("xray-img");
  errorMessage.style.display = "-";
}
