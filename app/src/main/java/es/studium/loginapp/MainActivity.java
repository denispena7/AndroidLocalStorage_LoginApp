package es.studium.loginapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    public static final String MyPREFERENCES = "LoginCredentials";
    public static final String NOMBRE = "nombreKey";
    public static final String CLAVE = "claveKey";

    SharedPreferences sharedPreferences;

    // Credenciales
    final String N = "Denis";
    final String C = "12345678A";

    EditText nombre, clave;
    Switch guardarCredenciales;
    Button botonAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String usuarioGuardado = sharedPreferences.getString("nombreKey", null);

        // Si hay credenciales guardadas, redirigir a GreetingActivity
        if (usuarioGuardado != null)
        {
            Intent intent = new Intent(MainActivity.this, GreetingActivity.class);
            startActivity(intent);
            finish(); // Terminar MainActivity para que no regrese atrás
        }

        nombre = findViewById(R.id.txtNombre);
        clave = findViewById(R.id.txtClave);

        guardarCredenciales = findViewById(R.id.switchCredenciales);

        botonAcceder = findViewById(R.id.btnAcceder);

        botonAcceder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == R.id.btnAcceder)
                {
                    String txtNombre = nombre.getText().toString().trim();
                    String txtClave = clave.getText().toString().trim();
                    boolean isChecked = guardarCredenciales.isChecked();

                    if(txtNombre.isEmpty() || txtClave.isEmpty())
                    {
                        StringBuilder mensaje = new StringBuilder();

                        if (txtNombre.isEmpty()) mensaje.append(getString(R.string.faltaNombre)).append("\n");
                        if (txtClave.isEmpty()) mensaje.append(getString(R.string.faltaClave)).append("\n");

                        // Si hay más de un campo vacío, pedir que se rellenen todos los campos
                        if (mensaje.toString().split("\n").length > 1)
                        {
                            mensaje = new StringBuilder("Cumplimente los campos");
                        }

                        Toast.makeText(MainActivity.this, mensaje.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if (txtClave.length() != 9)
                        {
                            // Mostrar Toast si la clave no tiene exactamente 9 caracteres
                            Toast.makeText(MainActivity.this, "La clave debe tener 9 caracteres", Toast.LENGTH_SHORT).show();
                        }
                        else if(!txtNombre.equals(N) || !txtClave.equals(C))
                        {
                            Toast.makeText(MainActivity.this, R.string.credencialesIncorrectas, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(isChecked)
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(NOMBRE, txtNombre);
                                editor.putString(CLAVE, txtClave);
                                editor.apply();

                                Toast.makeText(MainActivity.this, R.string.toastSave, Toast.LENGTH_LONG).show();
                            }

                            Intent intent = new Intent(MainActivity.this, GreetingActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}