package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
    private static final String COLUMN_LIST_ID_FK = "id_list";
    private static final String COLUMN_PRODUCT_ID_FK = "id_product";

    private static final String TABLE_PRODUCT = "Product";
    private static final String COLUMN_PRODUCT_ID = "id_product";
    private static final String COLUMN_PRODUCT = "product";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ");";

    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT + " TEXT" + ");";

    private String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "("
            + COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LIST_DATE + " TEXT,"
            + COLUMN_LIST_ID_USER_FK + " INTEGER, FOREIGN KEY (" + COLUMN_LIST_ID_USER_FK + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private String CREATE_LIST_PRODUCT_TABLE = "CREATE TABLE " + TABLE_LIST_PRODUCT + "("
            + COLUMN_LIST_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LIST_ID_FK + " INTEGER,"
            + COLUMN_PRODUCT_ID_FK + " INTEGER, FOREIGN KEY (" + COLUMN_LIST_ID_FK + ") REFERENCES "+ TABLE_LIST + "(" + COLUMN_LIST_ID + "), " +
            "FOREIGN KEY (" + COLUMN_PRODUCT_ID_FK + ") REFERENCES "+ TABLE_PRODUCT + "(" + COLUMN_PRODUCT_ID + "));";


    // drop table sql query
    private final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private final String DROP_LIST_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIST;
    private final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private final String DROP_LIST_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_LIST_PRODUCT;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_LIST_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
                User user = new User(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))),
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
}
