package com.example.testcourse25;

import static android.app.PendingIntent.getActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tTask;
    EditText eValue;
    Button btnCheck;

    int range=10;
    int secretValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tTask = findViewById(R.id.tTask);
        eValue = findViewById(R.id.eValue);
        btnCheck = findViewById(R.id.btnCheck);

        setSecretValue();

        btnCheck.setOnClickListener(v->{
            try {
                int userValue = Integer.parseInt(eValue.getText().toString());
                checkValue(userValue);
            }
            catch (Exception ex){
                Toast.makeText(getApplicationContext(),
                                "Введите число в поле ввода!",
                                Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    void checkValue(int userValue) {
        if (secretValue==userValue){
            showAlert();
        } else if (userValue>secretValue) {
            Toast.makeText(getApplicationContext(),
                            "Ваше число больше секретного!",
                            Toast.LENGTH_SHORT)
                    .show();

        }
        else {
            Toast.makeText(getApplicationContext(),
                            "Ваше число меньше секретного!",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void setSecretValue() {
        Random random = new Random();
        secretValue = random.nextInt(range)+1;//0-9 1-10
        Log.i("key",secretValue+"");
    }

    void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Вы выиграли! Хотите сыграть еще раз?")
                .setTitle("Win!")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        range+=10;//level
                        setSecretValue();//new secret key
                        eValue.getText().clear();// clear edit text
                    }
                })
                .setNegativeButton("Нет, выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       finish();
                    }
                });
        // Create the AlertDialog object and return it.
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}