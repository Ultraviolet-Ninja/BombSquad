package bomb.note;

import bomb.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tools.pattern.facade.FacadeFX;

import java.util.List;

public class NotePageController extends Stage {
    private static final short HEIGHT = 400, WIDTH = 600;

    private final List<NotePageController> internalReference;

    public NotePageController(List<NotePageController> internalReference) {
        super();
        this.internalReference = internalReference;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("note.fxml"));
        loader.setController(this);
        setTitle("Extra Note");
        getIcons().add(new Image(String.valueOf(Main.class.getResource("Logo.png"))));

        Parent parent = FacadeFX.load(loader);
        setScene(new Scene(parent, WIDTH, HEIGHT));
        setOnCloseRequest(event -> close());
        show();
    }

    @Override
    public void close() {
        internalReference.remove(this);
        super.close();
    }
}
