package com.example.readjson;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readjson.Adapter.AdapterItemSql;
import com.example.readjson.Data.DetailDataNote;
import com.example.readjson.DataBase.DataBaseSqlite;
import com.example.readjson.Interface.DataVariable;
import com.example.readjson.Interface.ItemClickListener;
import com.example.readjson.ItemData.ItemSql;

import java.util.ArrayList;
import java.util.List;

public class SQLiteToDoList extends AppCompatActivity implements DataVariable, ItemClickListener {

    public DataBaseSqlite dataBase;
    ImageView ivNavigation;
    RecyclerView rvWorking;
    AdapterItemSql adapterItemSql;

    List<ItemSql> list;
    Cursor dataWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_to_do_list);

        mapping();
        list = new ArrayList<>();

        adapterItemSql = new AdapterItemSql(this);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this,
                        RecyclerView.VERTICAL, false);
        rvWorking.setLayoutManager(linearLayoutManager);
        rvWorking.setAdapter(adapterItemSql);

        dataBase = new DataBaseSqlite(this,
                "noteLifeDay.sqlite", null, 1);

//        CREATE TABLE DATA WORKING
        dataBase.queryData(SqlCommand.createTable);
//        INSERT DATA
//        dataBase.queryData("INSERT INTO working VALUES(null, 'Schools')");
        selectData();
    }

    private void onClickGoToDataNote(ItemSql itemSql){
        Intent intent = new Intent(this, DetailDataNote.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_NOTE_OBJ, itemSql);
        intent.putExtra(DATA_NOTE, bundle);

        startActivity(intent);
    }

    //    ADD DATA USING DIALOG
    private void dialogAddItem() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_item);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(false);

        EditText etDialog = dialog.findViewById(R.id.et_content_add);
        Button btDialogCf = dialog.findViewById(R.id.bt_dialog_confirm);
        Button btDialogCc = dialog.findViewById(R.id.bt_dialog_cancel);

        btDialogCc.setOnClickListener(v -> dialog.dismiss());

        btDialogCf.setOnClickListener(v -> {
            String noteText = etDialog.getText().toString();
            if (noteText.trim().length() == 0) {
                Toast.makeText(SQLiteToDoList.this,
                        "Not Data", Toast.LENGTH_SHORT).show();
            } else {
                dataBase.queryData("INSERT INTO today VALUES(null, '" + noteText + "')");
                Toast.makeText(SQLiteToDoList.this,
                        "Add Note Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                selectData();
            }
        });
        dialog.show();
    }

    @NonNull
    private List<ItemSql> getListItemSql(String name, int positionWorking) {
        list.add(new ItemSql(name, positionWorking));
        return list;
    }

    private void mapping() {
        ivNavigation = findViewById(R.id.iv_sql_nav);
        rvWorking = findViewById(R.id.rv_data_sql);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn_add_item_sql, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.it_add) {
            dialogAddItem();
        }
        return super.onOptionsItemSelected(item);
    }

    //    SELECT DATA
    public void selectData() {
        dataWorking = dataBase.getData(SqlCommand.selectAllData);
        list.clear();
        while (dataWorking.moveToNext()) {
            String name = dataWorking.getString(1);
            int positionWorking = dataWorking.getInt(0);
            adapterItemSql.setData(getListItemSql(name, positionWorking));
        }
    }

    @Override
    public void itemClick(int position) {

    }

    public static class SqlCommand {
        static String createTable =
                "CREATE TABLE IF NOT EXISTS " +
                        "today(id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV nvarchar(50))";
        static String selectAllData = "SELECT * FROM today";
    }
}