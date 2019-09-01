package comp5216.sydney.edu.au.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Define variables
    ListView listView;
    ArrayList<ToDoItem> items;
    ArrayAdapter<ToDoItem> itemsAdapter;
    ToDoItemDB db;
    ToDoItemDao toDoItemDao;

    public final int EDIT_ITEM_REQUEST_CODE = 647;
    public final int ADD_ITEM_REQUEST_CODE = 648;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);

        // Reference the "listView" variable to the id "lstView" in the layout
        listView = findViewById(R.id.lstView);

        // Create an instance of ToDoItemDB and Dao
        db = ToDoItemDB.getDatabase(this.getApplication().getApplicationContext());
        toDoItemDao = db.toDoItemDao();
        readItemsFromDatabase();

        // Create an adapter for the list view using Android's built-in item layout
        itemsAdapter = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_1, items);

        // Connect the listView and the adapter
        listView.setAdapter(itemsAdapter);

        // Setup listView listeners
        setupListViewListener();
    }

    public void onAddItemClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditToDoItemActivity.class);
        if(intent != null) {
            // put "extras" into the bundle for access in the edit activity
            intent.putExtra("item", "");
            intent.putExtra("text", "");
            intent.putExtra("position", 2);
            // brings up the second activity
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            itemsAdapter.notifyDataSetChanged();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Extract name value from result extras
                String editedItemTitle = data.getExtras().getString("item");
                String editedItemText = data.getExtras().getString("text");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date editedDate = new Date();
                int position = data.getIntExtra("position", -1);
                //ToDoItem editedItem = new ToDoItem(editedItemTitle, editedItemText, editedDate);

                // Update all item's information
                items.get(position).setToDoItemName(editedItemTitle);
                items.get(position).setToDoItemText(editedItemText);
                items.get(position).setToDoItemDateUpdated(dateFormat.format(editedDate));

                // After update the item, move the item to the top
                Collections.swap(items, position, 0);

                itemsAdapter.notifyDataSetChanged();

                saveItemsToDatabase();
            }
        }

        if (requestCode == ADD_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Extract name value from result extras
                String newAddItem = data.getExtras().getString("item");
                String newAddText = data.getExtras().getString("text");
                Date newDate = new Date();
                int position = data.getIntExtra("position", -1);
                ToDoItem newToDo = new ToDoItem(newAddItem, newAddText, newDate, newDate);

                // Insert new item to the top
                items.add(0, newToDo);

                Log.i("Updated Item in list:", newAddItem + ",position:"
                        + position);
                itemsAdapter.notifyDataSetChanged();

                saveItemsToDatabase();
            }
        }
    }

    private void setupListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId)
            {
                Log.i("MainActivity", "Long Clicked item " + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                items.remove(position); // Remove item from the ArrayList
                                itemsAdapter.notifyDataSetChanged(); // Notify listView adapter to update the list

                                saveItemsToDatabase();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });

                builder.create().show();
                return true;
            }
        });

        // TODO

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String updateItemTitle = itemsAdapter.getItem(position).getToDoItemName();
                String updateItemText = itemsAdapter.getItem(position).getToDoItemText();
                Log.i("MainActivity", "Clicked item " + position + ": " + updateItemTitle);

                Intent intent = new Intent(MainActivity.this, EditToDoItemActivity.class);
                if (intent != null) {
                    // put "extras" into the bundle for access in the edit activity
                    intent.putExtra("item", updateItemTitle);
                    intent.putExtra("text", updateItemText);
                    intent.putExtra("position", position);
                    // brings up the second activity
                    startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
                    itemsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void readItemsFromDatabase() {
        // Use asynchronous task to run query on the background and wait for result
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //reads items from database
                    List<ToDoItem> itemsFromDB = toDoItemDao.listAll();
                    items = new ArrayList<ToDoItem>();
                    if(itemsFromDB != null & itemsFromDB.size() > 0) {
                        for(ToDoItem item : itemsFromDB) {
                            items.add(new ToDoItem(item.getToDoItemName(), item.getToDoItemText(), item.getToDoItemDateCreated(), item.getToDoItemDateUpdated()));
                            Log.d("list", items.toString());
                            Log.i("SQLite read item", "ID: " + item.getToDoItemID() + " Name: " + item.getToDoItemName());
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }

    private void saveItemsToDatabase() {
        // Use asynchronous task to run query on the background to avoid locking UI
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //delete all items and re-insert
                toDoItemDao.deleteAll();
                for(ToDoItem todo : items) {
                    ToDoItem item = new ToDoItem(todo.getToDoItemName(), todo.getToDoItemText(), todo.getToDoItemDateCreated(), todo.getToDoItemDateUpdated());
                    toDoItemDao.insert(item);
                    Log.d("item: ", item.toString());
                    //Log.i("SQLite saved item", todo);
                }
                return null;
            }
        }.execute();
    }
}