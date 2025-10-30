import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class GUI extends Application {
    Stage stage;
    public void start(Stage stage) throws Exception {
        stage.setTitle("Shopping Cart / SAMUEL SARIMO");
        this.stage = stage;
        stage.setResizable(false);
        Locale locale = new Locale("en", "US");
        loadFXML(locale);

    }

    public void loadFXML(Locale locale) {
        try {
            URL fxml = GUI.class.getResource("shoppingCart.fxml");
            ResourceBundle rb = ResourceBundle.getBundle("LanguageBundle", locale);
            FXMLLoader loader = new FXMLLoader(fxml, rb);
            Scene scene = new Scene(loader.load());
            ShoppingCartController controller = loader.getController();
            controller.setGUI(this);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
