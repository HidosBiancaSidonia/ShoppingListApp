package activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dam.shoppinglist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.DataBaseHelper;
import model.List;

public class ShoppingListActivity extends AppCompatActivity {

    private TextView name_usr;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ArrayAdapter<String> mAdapter;
    private DataBaseHelper db = null;
    private Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        db = new DataBaseHelper(ShoppingListActivity.this);

        name_usr = (TextView)findViewById(R.id.name_user);
        Intent myIntent = getIntent();
        idUser = Integer.parseInt(myIntent.getStringExtra("idUser"));


        name_usr.setText(db.findNameUser(idUser));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listView = (ListView)findViewById(R.id.listView_lv);

        loadLists();
    }

    public void loadLists() {
        ArrayList<List> lists = db.getAllLists();
        ArrayList<String> listsDate = new ArrayList<String>() ;

        for (List list:lists) {
            if(list.getId_user_Fk().equals(idUser)) {
                listsDate.add(list.getDate());
            }
        }

        if(mAdapter==null){
            mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listsDate);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent productListIntent = new Intent(ShoppingListActivity.this, ProductsActivity.class);
                    productListIntent.putExtra("idList",db.findIdList(listsDate.get(position)).toString());
                    productListIntent.putExtra("idUser",idUser.toString());
                    startActivity(productListIntent);
                }
            });
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(listsDate);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout1){
        drawerLayout1.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout1){
        drawerLayout1.openDrawer(GravityCompat.START);
        if(drawerLayout1.isDrawerOpen(GravityCompat.START)){
            drawerLayout1.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickLogOut(View view){
        logout(this);
    }

    private static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void logout(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                redirectActivity(activity,LogInActivity.class);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void ClickAdd(View view) {
        addListToSQLite();
    }

    private void addListToSQLite(){
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        boolean verify;
        List list = new List(date,idUser);
        db.addList(list);
        loadLists();
    }
}
