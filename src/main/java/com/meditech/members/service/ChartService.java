package com.meditech.members.service;

import com.meditech.members.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ChartService {
    private final EpisodeRepository episodeRepository;

    public void generateGraph() throws IOException {
        // 데이터 생성
        List<Double> epsilonValues = episodeRepository.findAllEpsilons();
        ; // double 타입의 List (Y1 축)
        List<Integer> idValues = episodeRepository.findAllId(); // int 타입의 List (X 축)
        List<Double> s1Values = episodeRepository.findAllS1();
        ; // double 타입의 List (Y2 축)
        List<Double> s2Values = episodeRepository.findAllS2(); // double 타입의 List (Y3 축)
        List<Double> s3Values = episodeRepository.findAllS3(); // double 타입의 List (Y4 축)
        List<Double> s4Values = episodeRepository.findAllS4(); // double 타입의 List (Y5 축)
        List<Double> s5Values = episodeRepository.findAllS5(); // double 타입의 List (Y6 축)
        List<Double> s6Values = episodeRepository.findAllS6(); // double 타입의 List (Y6 축)

        // 그래프 데이터셋 생성
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data1 = new double[2][epsilonValues.size()];//id-epsilon
        for (int i = 0; i < epsilonValues.size(); i++) {
            data1[0][i] = idValues.get(i);//x축데이터
            data1[1][i] = epsilonValues.get(i);//y축데이터
        }
        dataset.addSeries("Epsilon", data1);

        double[][] data2 = new double[2][s1Values.size()];
        for (int i = 0; i < s1Values.size(); i++) {
            data2[0][i] = idValues.get(i);//x축데이터
            data2[1][i] = s1Values.get(i);//y축데이터
        }
        dataset.addSeries("S1", data2);

        double[][] data3 = new double[2][s2Values.size()];
        for (int i = 0; i < s2Values.size(); i++) {
            data3[0][i] = idValues.get(i);//x축데이터
            data3[1][i] = s2Values.get(i);//y축데이터
        }
        dataset.addSeries("S2", data3);

        double[][] data4 = new double[2][s3Values.size()];
        for (int i = 0; i < s3Values.size(); i++) {
            data4[0][i] = idValues.get(i);//x축데이터
            data4[1][i] = s3Values.get(i);//y축데이터
        }
        dataset.addSeries("S3", data4);

        double[][] data5 = new double[2][s4Values.size()];
        for (int i = 0; i < s4Values.size(); i++) {
            data5[0][i] = idValues.get(i);//x축데이터
            data5[1][i] = s4Values.get(i);//y축데이터
        }
        dataset.addSeries("S4", data5);

        double[][] data6 = new double[2][s5Values.size()];
        for (int i = 0; i < s5Values.size(); i++) {
            data6[0][i] = idValues.get(i);//x축데이터
            data6[1][i] = s5Values.get(i);//y축데이터
        }
        dataset.addSeries("S5", data6);

        double[][] data7 = new double[2][s6Values.size()];
        for (int i = 0; i < s6Values.size(); i++) {
            data7[0][i] = idValues.get(i);//x축데이터
            data7[1][i] = s6Values.get(i);//y축데이터
        }
        dataset.addSeries("S6", data7);

        // 그래프 생성
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Q-learning Chart", // 그래프 제목
                "epsiode", // X 축 레이블
                "maxQ", // Y 축 레이블
                dataset, // 데이터셋
                PlotOrientation.VERTICAL, // 그래프 방향
                true, // 범례 표시 여부
                true, // 도구 팁 표시 여부
                false // URL 클릭 이벤트 처리 여부
        );

        // X 축 설정
        NumberAxis xAxis = (NumberAxis) ((XYPlot) chart.getPlot()).getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Y 축 설정
        NumberAxis yAxis1 = new NumberAxis("maxQ");//오른쪽 Y축
        NumberAxis yAxis2 = new NumberAxis("Epsilon");//오른쪽 Y축
        //NumberAxis yAxis2 = new NumberAxis("Epsilon");
//        NumberAxis yAxis3 = new NumberAxis("S2");
//        NumberAxis yAxis4 = new NumberAxis("S3");
//        NumberAxis yAxis5 = new NumberAxis("S4");
//        NumberAxis yAxis6 = new NumberAxis("S5");
//        NumberAxis yAxis7 = new NumberAxis("S6");

        ((XYPlot) chart.getPlot()).setRangeAxis(1, yAxis1);
        ((XYPlot) chart.getPlot()).setRangeAxis(0, yAxis2);
        //((XYPlot) chart.getPlot()).setRangeAxis(1, yAxis2);
//        ((XYPlot) chart.getPlot()).setRangeAxis(2, yAxis3);
//        ((XYPlot) chart.getPlot()).setRangeAxis(3, yAxis4);
//        ((XYPlot) chart.getPlot()).setRangeAxis(4, yAxis5);
//        ((XYPlot) chart.getPlot()).setRangeAxis(5, yAxis6);
//        ((XYPlot) chart.getPlot()).setRangeAxis(6, yAxis7);

//        yAxis1.setRange(0,1);
//        yAxis2.setRange(-70,50);
//        yAxis3.setRange(-70,50);
//        yAxis4.setRange(-70,50);
//        yAxis5.setRange(-70,50);
//        yAxis6.setRange(-70,50);
//        yAxis7.setRange(-70,50);

        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(1, 0);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(0, 1);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(2, 0);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(3, 0);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(4, 0);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(5, 0);
        ((XYPlot) chart.getPlot()).mapDatasetToRangeAxis(6, 0);


        // 데이터셋 렌더링 순서 설정
        ((XYPlot) chart.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // 이미지로 렌더링
        int width = 800; // 이미지 너비
        int height = 600; // 이미지 높이
        File outputImage = new File("C:/Users/kms47/Downloads/종설/members/src/main/resources/static/chart.png"); // 이미지 출력 경로
        ChartUtils.saveChartAsPNG(outputImage, chart, width, height);
    }
}