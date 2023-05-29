package nine.teta.colormixer.adapter;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_colors")
public class FavouriteColorItemModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int color;
    @NonNull
    private String hexCode;
    private String name;

    public FavouriteColorItemModel(int color, String hexCode, String name) {
        this.color = color;
        this.hexCode = hexCode;
        this.name = name;
    }

    //// getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FavouriteColorItemModel{" +
                "id=" + id +
                ", color=" + color +
                ", hexCode='" + hexCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
