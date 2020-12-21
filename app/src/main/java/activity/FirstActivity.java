package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dam.shoppinglist.R;

public class FirstActivity extends AppCompatActivity {

    private Button btn_first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btn_first = (Button) findViewById(R.id.btn_first);

        btn_first.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent logInIntent = new Intent(FirstActivity.this, LogInActivity.class);
                startActivityForResult(logInIntent, 0);
            }

        });
    }
}