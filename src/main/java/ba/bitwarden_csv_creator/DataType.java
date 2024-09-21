package ba.bitwarden_csv_creator;

public enum DataType {
    IGNORE ("Ignore", "light_gray", "dark_gray"),
    TITLE ("Title", "white", "black"),
    USERNAME ("Login Name", "white", "black"),
    PASSWORD ("Password", "white", "black"),
    URI ("URI", "white", "black"),
    FOLDER ("Folder Name", "white", "black"),
    NOTES ("Notes", "white", "black"),
    TOTP ("TOTP", "light_gray", "black"),
    TYPE ("Type", "light_gray", "black"),
    FAVORITE ("Favorite", "light_gray", "black"),
    FIELDS ("Fields", "light_gray", "black"),
    REPROMPT ("Repromt", "light_gray", "black"),
    UNASSIGNED ("Unassigned", "light_red", "dark_red");

    public final String name;
    public final String bg_color;
    public final String fnt_color;

    DataType(String name, String bg_color, String fnt_color) {
        this.name = name;
        this.bg_color = bg_color;
        this.fnt_color = fnt_color;
    }

    //    folder,favorite,type,name,notes,fields,reprompt,login_uri,login_username,login_password,login_totp
}
