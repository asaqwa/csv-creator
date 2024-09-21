module ba.bitwarden_csv_creator {
    requires javafx.controls;
    requires javafx.fxml;

    opens ba.bitwarden_csv_creator to javafx.fxml;
    opens ba.bitwarden_csv_creator.fx to javafx.fxml;
    opens ba.bitwarden_csv_creator.fx.view to javafx.fxml;

    exports ba.bitwarden_csv_creator;
    exports ba.bitwarden_csv_creator.fx;
}