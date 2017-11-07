package phoswald;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileListItem {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty size = new SimpleStringProperty();
    private String perms;
    private String date;

    public StringProperty fileNameProperty() {
        return name;
    }

    public StringProperty fileSizeProperty() {
        return size;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getSize() {
        return Long.parseLong(size.get());
    }

    public void setSize(long size) {
        this.size.set(Long.toString(size));
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
