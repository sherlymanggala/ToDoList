package comp5216.sydney.edu.au.todolist;

import android.widget.ArrayAdapter;

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

    public ToDoItem(String toDoItemName, String toDoItemText) {
        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
    }

    public ToDoItem(String toDoItemName, String toDoItemText, Date toDoItemDateCreated, Date toDoItemDateUpdated){

        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.toDoItemDateCreated = dateFormat.format(toDoItemDateCreated);
        this.toDoItemDateUpdated = dateFormat.format(toDoItemDateUpdated);
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
        return toDoItemName;
    }
}
