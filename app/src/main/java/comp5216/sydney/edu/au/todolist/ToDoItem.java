package comp5216.sydney.edu.au.todolist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "todolist")
public class ToDoItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "toDoItemID")
    private int toDoItemID;

    @ColumnInfo(name = "toDoItemName")
    private String toDoItemName;

    @ColumnInfo(name = "toDoItemText")
    private String toDoItemText;

    @ColumnInfo(name = "toDoItemDateCreated")
    private String toDoItemDateCreated;

    @ColumnInfo(name = "toDoItemDateUpdated")
    private String toDoItemDateUpdated;

    // Constructor for update Item
    public ToDoItem(String toDoItemName, String toDoItemText, Date date){

        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.toDoItemDateUpdated = dateFormat.format(date);
    }

    // Constructor for add new Item
    public ToDoItem(String toDoItemName, String toDoItemText, Date newDate, Date updateDate) {
        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.toDoItemDateCreated = dateFormat.format(newDate);
        this.toDoItemDateUpdated = dateFormat.format(updateDate);
    }

    public ToDoItem(String toDoItemName, String toDoItemText, String toDoItemDateCreated, String toDoItemDateUpdated) {
        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
        this.toDoItemDateCreated = toDoItemDateCreated;
        this.toDoItemDateUpdated = toDoItemDateUpdated;
    }

    public int getToDoItemID() {

        return toDoItemID;
    }

    public void setToDoItemID(int toDoItemID) {

        this.toDoItemID = toDoItemID;
    }

    public String getToDoItemName() {

        return toDoItemName;
    }

    public void setToDoItemName(String toDoItemName) {

        this.toDoItemName = toDoItemName;
    }

    public void setToDoItemText(String toDoItemText) {
        this.toDoItemText = toDoItemText;
    }

    public String getToDoItemText() {
        return toDoItemText;
    }

    public void setToDoItemDateCreated(String toDoItemDateCreated) {
        this.toDoItemDateCreated = toDoItemDateCreated;
    }

    public String getToDoItemDateCreated() {
        return toDoItemDateCreated;
    }

    public void setToDoItemDateUpdated(String toDoItemDateUpdated) {
        this.toDoItemDateUpdated = toDoItemDateUpdated;
    }

    public String getToDoItemDateUpdated() {
        return toDoItemDateUpdated;
    }

    public String toString() {
        return "\n" + toDoItemName + "\n\nCreated: " + toDoItemDateCreated + "\nLast Updated: " + toDoItemDateUpdated + "\n";
    }
}
