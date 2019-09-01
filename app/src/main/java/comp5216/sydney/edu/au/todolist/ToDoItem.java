package comp5216.sydney.edu.au.todolist;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

//    public ToDoItem(String toDoItemName){
//
//        this.toDoItemName = toDoItemName;
//    }

    public ToDoItem(String toDoItemName, String toDoItemText){

        this.toDoItemName = toDoItemName;
        this.toDoItemText = toDoItemText;
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
}
