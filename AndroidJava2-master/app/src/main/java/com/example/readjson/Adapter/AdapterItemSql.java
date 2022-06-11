package com.example.readjson.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readjson.Interface.ItemClickListener;
import com.example.readjson.ItemData.ItemSql;
import com.example.readjson.R;
import com.example.readjson.SQLiteToDoList;

import java.util.List;

// STEP 4:
public class AdapterItemSql extends RecyclerView.Adapter<AdapterItemSql.ItemSqlViewHolder> {

    //    STEP 5:
    private final SQLiteToDoList context;
    private List<ItemSql> listItem;


    // STEP 6:
    public AdapterItemSql(SQLiteToDoList context) {
        this.context = context;
    }

    //    STEP 7:
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ItemSql> list) {
        this.listItem = list;
        notifyDataSetChanged();
    }

    @NonNull
//    STEP 8
    @Override
    public ItemSqlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_work_strean, parent, false);

        return new ItemSqlViewHolder(view);
    }

    //    STEP 9:
    @Override
    public void onBindViewHolder(@NonNull ItemSqlViewHolder holder, int position) {
        ItemSql itemSql = listItem.get(position);
        if (itemSql == null) {
            return;
        }
        holder.tvContent.setText(itemSql.getItemWork());
    }

    @Override
    public int getItemCount() {
        if (listItem != null) {
            return listItem.size();
        }
        return 0;
    }

//    ?STEP 3:

    public class ItemSqlViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;
        private final ImageView ivNavigation;

        public ItemSqlViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_sql_name_working);
            ivNavigation = itemView.findViewById(R.id.iv_sql_nav);

            ivNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = listItem.get(getAdapterPosition()).getNav();
                    showMenuNav(position);
                }
            });
        }

//        SHOW MENU WHEN CLICK NAV IN RV
        private void showMenuNav(int position) {
            PopupMenu popupMenu = new PopupMenu(context, ivNavigation);
            popupMenu.getMenuInflater().inflate(R.menu.mn_sql_nav, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.it_update:
                        Toast.makeText(context,
                                getAdapterPosition() + "", Toast.LENGTH_SHORT).show();
                        dialogUpdateItem(Gravity.CENTER, position);
                        break;
                    case R.id.it_delete:
                        dialogDeleteItem(tvContent.getText().toString(), position);
                        context.selectData();
                        Toast.makeText(context,
                                "ahihi delete succesfull", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            });
            popupMenu.show();
        }

//        DIALOG UPDATE
        @SuppressLint("SetTextI18n")
        private void dialogUpdateItem(int gravity, int position) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_dialog_update_item);

            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttribute = window.getAttributes();
            windowAttribute.gravity = gravity;
            window.setAttributes(windowAttribute);

            dialog.setCancelable(Gravity.CENTER != gravity);

            EditText etDialog = dialog.findViewById(R.id.et_content_add);
            Button btDialogCf = dialog.findViewById(R.id.bt_dialog_confirm);
            Button btDialogCc = dialog.findViewById(R.id.bt_dialog_cancel);

            etDialog.setText(tvContent.getText().toString() + "");

            btDialogCc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btDialogCf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String noteText = etDialog.getText().toString();
                    if (noteText.trim().length() == 0 || noteText.
                            equals(tvContent.getText().toString())) {
                    } else {
                        context.dataBase.queryData("UPDATE today SET TenCV = '"
                                + noteText + "' WHERE id = '" + position + "'");
                        context.selectData();
                    }
                }
            });
            dialog.show();
        }

        public void dialogDeleteItem(String content, int position) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("You want delete note '" + content + "' ?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.dataBase.
                            queryData("DELETE FROM today WHERE id = '" + position + "'");
                    context.selectData();
                    notifyDataSetChanged();
                }
            });
            alertDialog.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }
    }

}
