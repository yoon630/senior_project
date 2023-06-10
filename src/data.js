let xAxisData = ["id"]; // x축 데이터
let seriesData = [];

window.onload = function () {
  document.getElementById("drawLine1").addEventListener("click", drawChart);
  document.getElementById("drawLine2").addEventListener("click", drawChart);
};

function drawChart() {
  var myChart = echarts.init(document.getElementById("chart"));

  option = {
    xAxis: {
      type: "category",
      data: xAxisData, // 위에서 정의한 x 축 데이터
    },

    yAxis: {
      type: "value",
    },
    series: [
      {
        data: seriesData, // 위에서 정의한 데이터 시리즈
        type: this.value,
      },
    ],
  };
  myChart.setOption(option); // 차트 디스플레이
}
