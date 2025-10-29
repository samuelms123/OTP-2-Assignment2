import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageOption {
    private final String languageName;
    private final String languageCode;
    private final String countryCode;

    public LanguageOption(String language, String languageCode, String countryCode) {
        this.languageName = language;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
    }

    public String getName() {
        return languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Locale getLocale() {
        return new Locale(languageCode, countryCode);
    }

    public String toString() {
        return languageName;
    }
}