package com.rusakovich.bsuir.client.controllers.reports;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class ShowDiagram {

    public PieChart pieChart;
    public Label title;
    public Label caption = new Label("");
    private Double summary = 0.0;

    public void setData(ObservableList<PieChart.Data> dataSet, String titleStr) {
        pieChart.setData(dataSet);
        calculateSummary();

        pieChart.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    caption.setText("Процентное соотношение: " + Math.round((data.getPieValue() / summary) * 10000)/100 + "%");
                }));
        title.setText(titleStr);
    }

    private void calculateSummary(){
        summary = 0.0;
        pieChart.getData().forEach(data -> summary += data.getPieValue());
    }
}
