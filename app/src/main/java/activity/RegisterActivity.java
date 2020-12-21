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

import database.DataBaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private EditText et_name;
    private EditText et_email;
    private EditText et_password;
    private Button btn_register;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBaseHelper(RegisterActivity.this);

        et_name = (EditText)findViewById(R.id.et2_name);
        et_email = (EditText)findViewById(R.id.et2_email);
        et_name = (EditText)findViewById(R.id.et2_password);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_register = (Button)findViewById(R.id.btn_register);
        tv_login = (TextView)findViewById(R.id.tv_login);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logInIntent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(logInIntent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}