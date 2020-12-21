package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.shoppinglist.R;
import com.dam.shoppinglist.ShoppingListActivity;

import java.util.ArrayList;

import database.DataBaseHelper;
import model.User;

public class LogInActivity extends AppCompatActivity {

    private DataBaseHelper db = null;
    private EditText et_email = null;
    private EditText et_password = null;
    private Button btn_login = null;
    private TextView tv_register = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DataBaseHelper(LogInActivity.this);

        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_register = (TextView)findViewById(R.id.tv_register);

        tv_register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LogInActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        btn_login.setOnClickListener(v -> {
            if(verifyFromSQLite()){
                Intent shoppingListIntent = new Intent(LogInActivity.this, ShoppingListActivity.class);
                startActivity(shoppingListIntent);
            }
        });
    }

    private boolean verifyFromSQLite(){

        String email = (String) et_email.getText().toString().trim();
        String password = (String) et_password.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

        boolean verify = false;

        if(!email.equals("") && !password.equals("")) {
            if(email.matches(emailPattern) ) {
                if(password.length() > 4){
                    ArrayList<User> users = db.getAllUsers();
                    for (User user : users) {
                        if(email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                            verify = true;
                        }
                    }

                    if(!verify){
                        Toast.makeText(getApplicationContext(),"Email address or password is incorrect!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"The password must contain at least 5 characters!",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid email address!",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            verify = false;
            Toast.makeText(LogInActivity.this,"All fields must be completed!",Toast.LENGTH_SHORT).show();
        }
        return verify;
    }
}