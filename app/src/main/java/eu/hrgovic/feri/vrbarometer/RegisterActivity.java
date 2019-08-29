package eu.hrgovic.feri.vrbarometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mNameField;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mPasswordConfirmField;
    private MaterialButton mRegisterButton;
    private TextView mLoginLink;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_register);

        // Assign views
        mNameField = (TextInputEditText)findViewById(R.id.input_name);
        mEmailField = (TextInputEditText)findViewById(R.id.input_email);
        mPasswordField = (TextInputEditText)findViewById(R.id.input_password);
        mPasswordConfirmField = (TextInputEditText)findViewById(R.id.input_password_confirm);
        mRegisterButton = (MaterialButton)findViewById(R.id.button_register);
        mLoginLink = (TextView)findViewById(R.id.link_login);

        // Add listeners
        mRegisterButton.setOnClickListener(this);
        mLoginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_register) {
            register();
        } else if (i == R.id.link_login) {
            startLoginActivity();
        }
    }

    private void register() {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Registration validation fail", Toast.LENGTH_LONG).show();
            return;
        }

        String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Couldn't register", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private boolean validate() {
        boolean valid = true;

        String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String passwordConfirm = mPasswordConfirmField.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mNameField.setError("at least 3 characters");
            valid = false;
        } else {
            mNameField.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailField.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailField.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordField.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        if (passwordConfirm.isEmpty() || passwordConfirm.length() < 4 || passwordConfirm.length() > 10 || !(passwordConfirm.equals(password))) {
            mPasswordConfirmField.setError("Password Do not match");
            valid = false;
        } else {
            mPasswordConfirmField.setError(null);
        }

        return valid;
    }

    private void startLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
