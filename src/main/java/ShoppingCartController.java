import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ShoppingCartController {
    private GUI gui;
    private LanguageOption currentLanguage;

    @FXML private ListView<Item> itemList;
    @FXML private ComboBox<LanguageOption> languageOptions;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private Text totalCostValue;
    @FXML private Text errorText;

    @FXML
    private void initialize() {
        LanguageOption english = new LanguageOption("English", "en", "US");
        currentLanguage = english;
        LanguageOption finnish = new LanguageOption("Finnish", "fi", "FI");
        LanguageOption japanese = new LanguageOption("Japanese", "ja", "JP");
        LanguageOption swedish = new LanguageOption("Swedish", "sv", "SE");

        languageOptions.getItems().addAll(english, finnish, japanese, swedish);

    }

    public void calculateAndDisplayTotalCost() {
        double totalCost = 0;
        for (Item item : itemList.getItems()) {
            totalCost += item.getQuantity() * item.getPrice();
        }

        totalCostValue.setText(Double.toString(totalCost));
    }


    public void addItem(ActionEvent actionEvent) {
        errorText.setVisible(false);
        String name = nameField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();

        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            errorText.setVisible(true);
            return;
        }

        try {
            double priceDouble = Double.parseDouble(price);
            int quantityInt = Integer.parseInt(quantity);
            Item item = new Item(name, quantityInt, priceDouble);

            itemList.getItems().add(item);
            calculateAndDisplayTotalCost();

            nameField.clear();
            priceField.clear();
            quantityField.clear();



        } catch (Exception e) {
            errorText.setVisible(true);
        }
    }

    public void changeLanguage(ActionEvent actionEvent) {
        LanguageOption selectedLanguage = languageOptions.getSelectionModel().getSelectedItem();
        currentLanguage = selectedLanguage;
        gui.loadFXML(selectedLanguage.getLocale());
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
