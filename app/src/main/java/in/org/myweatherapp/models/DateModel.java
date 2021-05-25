package in.org.myweatherapp.models;

public class DateModel {
    String icon;
    String date;

    public DateModel(String icon, String date) {
        this.icon = icon;
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
