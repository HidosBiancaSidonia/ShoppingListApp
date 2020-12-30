package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.shoppinglist.R;

import java.util.ArrayList;

import database.DataBaseHelper;
import model.List;
import model.ListProduct;
import model.Product;
import util.NumberXProduct;

public class ProductsActivity extends AppCompatActivity {

    private TextView date_tw;
    private Integer idList;
    private Integer idUser;
    private DataBaseHelper db = null;
    private EditText number = null;
    private EditText product_name = null;
    private ArrayAdapter<String> mAdapter;
    private ListView listView_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        db = new DataBaseHelper(ProductsActivity.this);

        date_tw = (TextView)findViewById(R.id.date_tw);
        Intent myIntent = getIntent();
        idList = Integer.parseInt(myIntent.getStringExtra("idList"));
        idUser = Integer.parseInt(myIntent.getStringExtra("idUser"));

        date_tw.setText(db.findDateTime(idList));

        number = (EditText)findViewById(R.id.product_number);
        product_name = (EditText)findViewById(R.id.product_name);

        listView_product = (ListView)findViewById(R.id.listView_product);

        loadProducts();
    }

    private void loadProducts() {
        ArrayList<ListProduct> listProducts = db.getAllListProducts();
        ArrayList<Integer> number1 = new ArrayList<>();
        ArrayList<Integer> id_product = new ArrayList<>();
        ArrayList<String> productName = new ArrayList<>();


        for (ListProduct lP:listProducts) {
            number1.add(lP.getNumber());
            id_product.add(lP.getId_product());
        }

        for (Integer id: id_product) {
            productName.add(db.findProduct(id));
        }

        ArrayList<String> numberXproduct = new ArrayList<>();
        for(int i=0;i<productName.size();i++){
            numberXproduct.add("      " + number1.get(i).toString() + "      X      " + productName.get(i));
        }

        if(mAdapter==null){
            mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,numberXproduct);
            listView_product.setAdapter(mAdapter);
            listView_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                }
            });
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(numberXproduct);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void ClickAdd(View view) {
        addListProductToSQLite();
        loadProducts();
        emptyInputEditText();
    }

    private void addListProductToSQLite() {
        String nmb = (String) number.getText().toString().trim();
        String product = (String) product_name.getText().toString().trim();

        if(!nmb.equals("") && !product.equals("")) {
            Product prod = new Product(product);
            db.addProduct(prod);
            ListProduct listProduct = new ListProduct(Integer.parseInt(nmb),idList,db.findIdProduct(product));
            db.addListProduct(listProduct);
        }
        else {
            Toast.makeText(ProductsActivity.this,"All fields must be completed!",Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        number.setText(null);
        product_name.setText(null);
    }

    public void Click_DeleteList(View view) {
        db.deleteList(idList);

        //sterge si restul

        Intent shoppingListIntent = new Intent(ProductsActivity.this, ShoppingListActivity.class);
        shoppingListIntent.putExtra("idUser",idUser.toString());
        startActivity(shoppingListIntent);
    }
}