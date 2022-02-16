package bomb;

import bomb.selection.Puzzle;
import bomb.selection.SelectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EnumSet;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final Image logo = new Image(String.valueOf(Main.class.getResource("Logo.png")));

        EnumSet<Puzzle> bombSelection = retrieveBombSelection(stage, logo);
        if (!bombSelection.isEmpty()) {
            loadBombManual(stage, logo, bombSelection);
            return;
        }

    }

    private static EnumSet<Puzzle> retrieveBombSelection(Stage stage, Image logo) throws IOException {
        FXMLLoader selectionScreenLoader = new FXMLLoader(SelectionController.class.getResource("selection_screen.fxml"));
        Scene scene = new Scene(selectionScreenLoader.load(), 320, 240);
        SelectionController controller = selectionScreenLoader.getController();

        stage.setTitle("KTANE Module Selection Screen");
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.showAndWait();

        return controller.getPuzzleSet();
    }

    private static void loadBombManual(Stage stage, Image logo, EnumSet<Puzzle> bombSelection) throws IOException {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
