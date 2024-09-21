package ba.bitwarden_csv_creator.fx.view;

import ba.bitwarden_csv_creator.fx.FxStarter;
import javafx.fxml.FXML;

import java.io.File;

public class HomeController {

    private FxStarter fxStarter;

    public void setFxStarter(FxStarter fxStarter) {
        this.fxStarter = fxStarter;
    }

    @FXML
    private void showFileProzessor() {
//        File file = fxStarter.selectFile();
//        if (file != null) {
//            fxStarter.showFileProzessor(file);
//        }
        fxStarter.showFileProzessor(null);
    }

}
