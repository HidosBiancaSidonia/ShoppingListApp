package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.List;
import model.ListProduct;
import model.Product;
import model.User;

public class DataBaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "ShoppingListApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String TABLE_LIST = "List";
    private static final String COLUMN_LIST_ID = "id_list";
    private static final String COLUMN_LIST_DATE = "data";
    private static final String COLUMN_LIST_ID_USER_FK = "id_user";

    private static final String TABLE_LIST_PRODUCT = "List_Product";
    private static final String COLUMN_LIST_PRODUCT_ID = "id_list_product";
    private static final String COLUMN_LIST_PRODUCT_NUMBER = "number";
    private static final String COLUMN_LIST_ID_FK = "id_list";
    private static final String COLUMN_PRODUCT_ID_FK = "id_product";

    private static final String TABLE_PRODUCT = "Product";
    private static final String COLUMN_PRODUCT_ID = "id_product";
    private static final String COLUMN_PRODUCT = "product";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ");";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT + " TEXT NOT NULL" + ");";

        String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                + COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LIST_DATE + " TEXT NOT NULL,"
                + COLUMN_LIST_ID_USER_FK + " INTEGER, FOREIGN KEY (" + COLUMN_LIST_ID_USER_FK + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

        String CREATE_LIST_PRODUCT_TABLE = "CREATE TABLE " + TABLE_LIST_PRODUCT + "("
                + COLUMN_LIST_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LIST_PRODUCT_NUMBER + " INTEGER NOT NULL,"
                +COLUMN_LIST_ID_FK + " INTEGER,"
                + COLUMN_PRODUCT_ID_FK + " INTEGER , FOREIGN KEY (" + COLUMN_LIST_ID_FK + ") REFERENCES "+ TABLE_LIST + "(" + COLUMN_LIST_ID + "), " +
                "FOREIGN KEY (" + COLUMN_PRODUCT_ID_FK + ") REFERENCES "+ TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + "));";


        // creating required tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_LIST_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table sql query
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        String DROP_LIST_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIST;
        String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
        String DROP_LIST_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIST_PRODUCT;

        // on upgrade drop older tables
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_LIST_TABLE);
        db.execSQL(DROP_LIST_PRODUCT_TABLE);

        // create new tables
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public ArrayList<User> getAllUsers() {

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder = COLUMN_USER_NAME + " ASC";
        ArrayList<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT, product.getProduct());

        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public ArrayList<Product> getAllProducts() {

        String[] columns = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT
        };

        ArrayList<Product> products = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return products;
    }

    public void addList(List list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_DATE, list.getDate());
        values.put(COLUMN_LIST_ID_USER_FK, list.getId_user_Fk());

        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    public void addListProduct(ListProduct listProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_ID_FK, listProduct.getId_list());
        values.put(COLUMN_PRODUCT_ID_FK, listProduct.getId_product());
        values.put(COLUMN_LIST_PRODUCT_NUMBER, listProduct.getNumber());

        db.insert(TABLE_LIST_PRODUCT, null, values);
        db.close();
    }

    public String findNameUser(Integer id){
        ArrayList<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user.getName();
            }
        }
        return null;
    }

    public ArrayList<List> getAllLists() {

        String[] columns = {
                COLUMN_LIST_ID,
                COLUMN_LIST_DATE,
                COLUMN_LIST_ID_USER_FK
        };

        ArrayList<List> lists = new ArrayList<List>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIST, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                List list = new List(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_ID))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LIST_DATE)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_ID_USER_FK))));
                lists.add(list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lists;
    }

    public void deleteList(Integer id){
        String[] columns = {
                COLUMN_LIST_ID,
                COLUMN_LIST_DATE,
                COLUMN_LIST_ID_USER_FK
        };
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, COLUMN_LIST_ID +  "=" + id, null) ;
    }

    public Integer findIdList(String datetime){
        ArrayList<List> lists = getAllLists();
        for (List list : lists) {
            if (list.getDate().equals(datetime)) {
                return list.getId_list();
            }
        }
        return 0;
    }

    public String findDateTime(Integer id){
        ArrayList<List> lists = getAllLists();
        for (List list : lists) {
            if (list.getId_list().equals(id)) {
                return list.getDate();
            }
        }
        return null;
    }

    public ArrayList<ListProduct> getAllListProducts() {

        String[] columns = {
                COLUMN_LIST_PRODUCT_ID,
                COLUMN_LIST_PRODUCT_NUMBER,
                COLUMN_LIST_ID_FK,
                COLUMN_PRODUCT_ID_FK
        };

        ArrayList<ListProduct> listProducts = new ArrayList<ListProduct>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIST_PRODUCT, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ListProduct listProduct = new ListProduct(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_PRODUCT_ID))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_PRODUCT_NUMBER))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LIST_ID_FK))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID_FK))));
                listProducts.add(listProduct);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return listProducts;
    }

    public Integer findIdProduct(String product){
        ArrayList<Product> products = getAllProducts();
        for (Product product1 : products) {
            if (product1.getProduct().equals(product)) {
                return product1.getId_product();
            }
        }
        return 0;
    }

    public String findProduct(Integer id){
        ArrayList<Product> products = getAllProducts();
        for (Product product1 : products) {
            if (product1.getId_product().equals(id)) {
                return product1.getProduct();
            }
        }
        return null;
    }
}
