package es.studium.loginapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GreetingActivity extends AppCompatActivity
{
    TextView saludo;
    ImageButton borrar;
    TextView lbl_borrar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        saludo = findViewById(R.id.lbl_saludo);
        borrar = findViewById(R.id.btnBorrar);
        lbl_borrar = findViewById(R.id.lbl_btnBorrar);

        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String usuarioGuardado = sharedPreferences.getString("nombreKey", null);

        // Si hay credenciales guardadas, redirigir a GreetingActivity
        if (usuarioGuardado == null)
        {
            borrar.setVisibility(View.GONE);
            lbl_borrar.setVisibility(View.GONE);
        }

        borrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == (R.id.btnBorrar))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(GreetingActivity.this, R.string.toastDelete, Toast.LENGTH_SHORT).show();
                    borrar.setOnClickListener(null);

                    // Después de borrar las credenciales, redirigir a MainActivity
                    Intent intent = new Intent(GreetingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}