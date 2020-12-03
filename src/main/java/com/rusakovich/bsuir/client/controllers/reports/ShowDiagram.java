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

    public void setData(String dataType, String groupField, LocalDate begin, LocalDate end) {
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String query = dataType +
                "?command=groupBy" +
                "&groupField=" + groupField +
                "&memberAccountId=" + accountId +
                "&begin=" + begin.format(formatter) +
                "&end=" + end.format(formatter);
        Map<String, String> params = Client.doRequest(query);
        if ("ok".equals(params.get("status"))) {
            ArrayList<Map<String, String>> stats = Client.getResponseArray(params.get("data"));
            ObservableList<PieChart.Data> dataSet = FXCollections.observableArrayList(new ArrayList<>());
            for(Map<String, String> statItem: stats) {
                dataSet.add(new PieChart.Data(statItem.get(groupField), Double.parseDouble(statItem.get("amount"))));
            }
            pieChart.setData(dataSet);
            calculateSummary();

            pieChart.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> {
                        caption.setText("Процентное соотношение: " + Math.round((data.getPieValue() / summary) * 10000)/100 + "%");
                    }));

            title.setText("Итоговая диаграмма " + (dataType.equals("income") ? "доходов" : "расходов") +
                    ", сформированная по полю " + (groupField.equals("category") ? "'Категория'" : "'Счёт'") +
                    "\nна период с " + begin.format(formatter) + "по " + end.format(formatter));
        } else {
            title.setText(params.get("error"));
        }
    }

    private void calculateSummary(){
        pieChart.getData().forEach(data -> summary += data.getPieValue());
    }
}
