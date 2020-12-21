package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.shoppinglist.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button btn_first = (Button) findViewById(R.id.btn_first);

        btn_first.setOnClickListener(view -> {
            Intent logInIntent = new Intent(FirstActivity.this, LogInActivity.class);
            startActivityForResult(logInIntent, 0);
        });
    }
}