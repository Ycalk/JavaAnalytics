import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repos.ChartMaker;

public class AppFX extends Application {
    private static final ChartMaker chartMaker = new ChartMaker();


    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        var root = new BorderPane();

        var buttonPanel = new VBox(10);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.setStyle("-fx-background-color: #f0f0f0;");

        var studentsRegion = new Button("Студенты по регионам");
        var studentCities = new Button("Студенты по городам");
        var regionRating = new Button("Рейтинг по среднему баллу");
        var averageScoresByMonth = new Button("Средний балл по месяцам");
        var maximalScoresByMonth = new Button("Максимальный балл по месяцам");
        buttonPanel.getChildren().addAll(studentsRegion, studentCities, regionRating,
                averageScoresByMonth, maximalScoresByMonth);

        var chartPane = new StackPane();
        chartPane.setPadding(new Insets(30));

        var studentsRegionChart = chartMaker.studentsRegion();
        var studentCitiesChart = chartMaker.studentCities();
        var regionRatingChart = chartMaker.averageScoreByRegionRating();
        var averageScoresByMonthChart = chartMaker.averageScoresByMonth();
        var maximalScoresByMonthChart = chartMaker.maximalScoresByMonth();

        studentsRegion.setOnAction(_ -> {
            chartPane.getChildren().clear();
            chartPane.getChildren().add(studentsRegionChart);
        });

        studentCities.setOnAction(_ -> {
            chartPane.getChildren().clear();
            chartPane.getChildren().add(studentCitiesChart);
        });

        regionRating.setOnAction(_ -> {
            chartPane.getChildren().clear();
            chartPane.getChildren().add(regionRatingChart);
        });

        averageScoresByMonth.setOnAction(_ -> {
            chartPane.getChildren().clear();
            chartPane.getChildren().add(averageScoresByMonthChart);
        });

        maximalScoresByMonth.setOnAction(_ -> {
            chartPane.getChildren().clear();
            chartPane.getChildren().add(maximalScoresByMonthChart);
        });

        chartPane.getChildren().add(studentsRegionChart);

        root.setLeft(buttonPanel);
        root.setCenter(chartPane);

        var scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Java Analytics");
        stage.show();
    }
}
