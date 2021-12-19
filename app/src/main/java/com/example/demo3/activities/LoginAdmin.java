package com.example.demo3.activities;

import android.content.Intent;
import android.net.Credentials;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demo3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginAdmin extends AppCompatActivity {
    private static final String TAG = "LoginAdmin";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private String USERNAME ="";
    private String PASSWORD ="";
    private int counter = 5;
    boolean isValid = false;
    private EditText username;
    private EditText password;
    private Button btnlogin;
    private TextView batasinput;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("username/datalogin");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPasswd);
        btnlogin = findViewById(R.id.btnlogin);
        batasinput = findViewById(R.id.batasinput);
        password.setTransformationMethod(new PasswordTransformationMethod());
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String user = documentSnapshot.getString(KEY_USERNAME);
                            String pass = documentSnapshot.getString(KEY_PASSWORD);
                            USERNAME = user;
                            PASSWORD = pass;
                        } else {
                            Toast.makeText(LoginAdmin.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginAdmin.this, "eror!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hasiluser = username.getText().toString();
                String hasilpassword = password.getText().toString();
                if(hasiluser.isEmpty() || hasilpassword.isEmpty())
                {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(LoginAdmin.this, "Anda belum memasukkan username/password", Toast.LENGTH_LONG).show();

                }else {

                    /* Validate the user inputs */
                    isValid = validate(hasiluser, hasilpassword);

                    /* Validate the user inputs */

                    /* If not valid */
                    if (!isValid) {

                        /* Decrement the counter */
                        counter--;

                        /* Show the attempts remaining */
                        batasinput.setText("Batas Mencoba " + String.valueOf(counter));

                        /* Disable the login button if there are no attempts left */
                        if (counter == 0) {
                            btnlogin.setEnabled(false);
                            Toast.makeText(LoginAdmin.this, "Maaf kesempatan anda sudah habis!", Toast.LENGTH_LONG).show();
                        }
                        /* Display error message */
                        else {
                            Toast.makeText(LoginAdmin.this, "Username atau password salah!", Toast.LENGTH_LONG).show();
                        }
                    }
                    /* If valid */
                    else {

                        /* Allow the user in to your app by going into the next activity */
                        startActivity(new Intent(LoginAdmin.this, MenuAdmin.class));
                        finish();
                    }

                }

            }
        });
    }

    class Credentials
    {
        String name = USERNAME;
        String password = PASSWORD;
    }

    private boolean validate(String userinput, String passwordinput)
    {
        /* Get the object of Credentials class */
        Credentials credentials = new Credentials();
        /* Check the credentials */
        if(userinput.equals(credentials.name) && passwordinput.equals(credentials.password))
        {
            return true;
        }

        return false;
    }
}
