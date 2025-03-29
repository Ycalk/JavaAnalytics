package repos;
import java.util.Random;
import javafx.scene.chart.*;

public class ChartMaker {
    private static final StudentsDataAnalytics analytics =
            new StudentsDataAnalytics(StudentsData.fromDataBase("Basic Programming"));

    public PieChart studentsRegion() {
        var pieChart = new PieChart();
        pieChart.setTitle("Распределение студентов по регионам");
        for (var entry : analytics.getStudentsByRegion().entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        return pieChart;
    }

    public BarChart<String, Number> studentCities() {
        var xAxis = new CategoryAxis();
        xAxis.setLabel("Города");

        var yAxis = new NumberAxis();
        yAxis.setLabel("Кол-во студентов");

        var barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Распределение студентов по городам");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Кол-во студентов");
        for (var entry : analytics.getStudentsByCity().entrySet()){
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        return barChart;
    }

    public LineChart<String, Number> averageScoresByMonth(){
        var xAxis = new CategoryAxis();
        xAxis.setLabel("Месяц");
        var yAxis = new NumberAxis();
        yAxis.setLabel("Средний балл");

        var lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Средний балл студентов по месяцам");

        var averageScoresByMonth = analytics.getAverageScoreByMonth();
        var series = new XYChart.Series<String, Number>();
        series.setName("Средний балл");

        for (var entry : averageScoresByMonth.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    public LineChart<String, Number> maximalScoresByMonth(){
        var xAxis = new CategoryAxis();
        xAxis.setLabel("Месяц");
        var yAxis = new NumberAxis();
        yAxis.setLabel("Максимальный балл");

        var lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Максимальный балл студентов по месяцам");

        var maximalScoresByMonth = analytics.getMaximalScoreByMonth();
        var series = new XYChart.Series<String, Number>();
        series.setName("Максимальный балл");

        for (var entry : maximalScoresByMonth.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    public ScatterChart<Number, Number> averageScoreByRegionRating() {
        var xAxis = new NumberAxis();
        xAxis.setLabel("Образовательный рейтинг региона");
        var yAxis = new NumberAxis();
        yAxis.setLabel("Среднее кол-во баллов");

        var chart = new ScatterChart<>(xAxis, yAxis);
        chart.setTitle("Зависимость среднего кол-ва баллов от образовательного рейтинга региона");

        var series = new XYChart.Series<Number, Number>();
        series.setName("Средний балл");
        var random = new Random();
        for (var entry : analytics.getAverageScoreByRegion().entrySet()) {
            var randomValue = -5 + (10 * random.nextDouble());
            var adjustedRating = entry.getKey().rating + randomValue;
            var adjustedScore = entry.getValue() + randomValue;
            series.getData().add(new XYChart.Data<>(adjustedRating, adjustedScore));
        }

        chart.getData().add(series);
        return chart;
    }
}
