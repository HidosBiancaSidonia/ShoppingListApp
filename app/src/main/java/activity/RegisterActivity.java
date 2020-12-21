package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.shoppinglist.R;
import com.google.android.material.textfield.TextInputEditText;

import database.DataBaseHelper;
import model.User;

public class RegisterActivity extends AppCompatActivity {

    private DataBaseHelper db = null;
    private EditText et_name = null;
    private EditText et_email = null;
    private EditText et_password = null;
    private TextView tv_login = null;
    Button btn_register = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBaseHelper(RegisterActivity.this);

        et_name = (EditText) findViewById(R.id.et2_name);
        et_email = (EditText) findViewById(R.id.et2_email);
        et_password = (EditText) findViewById(R.id.et2_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_login = (TextView)findViewById(R.id.tv_login);

        tv_login.setOnClickListener(v -> {
            Intent logInIntent = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(logInIntent);
        });

        btn_register.setOnClickListener(v -> {
           if(postDataToSQLite()){
               emptyInputEditText();
           }
        });
    }

    private boolean postDataToSQLite() {
        String name = (String) et_name.getText().toString().trim();
        String email = (String) et_email.getText().toString().trim();
        String password = (String) et_password.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

        boolean verify;

        if(!name.equals("") && !email.equals("") && !password.equals("")) {
            verify = true;
            if(isAlpha(name)){
                if(email.matches(emailPattern)){
                    if(password.length() > 4){
                        User user = new User(name, email, password);
                        db.addUser(user);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"The password must contain at least 5 characters!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid email address!",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(RegisterActivity.this,"The name contains only letters!",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            verify = false;
            Toast.makeText(RegisterActivity.this,"All fields must be completed!",Toast.LENGTH_SHORT).show();
        }
        return verify;
    }

    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    private void emptyInputEditText() {
        et_name.setText(null);
        et_email.setText(null);
        et_password.setText(null);
    }
}