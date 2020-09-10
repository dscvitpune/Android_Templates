//To Register a new account
package com.example.signup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Register extends AppCompatActivity {
    EditText rEmailId, rPassword;
    Button buttonNT, buttonOA;
    FirebaseAuth fAuth;
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //edit_text_Eid is for edit text Email Id
        //edit_text_Pwd is for edit text Password
        //button_next is for button Next
        //button_OldAcc is or button Old Account
        rEmailId = findViewById(R.id.edit_text_Eid);
        rPassword = findViewById(R.id.edit_text_Pwd);
        buttonNT = findViewById(R.id.button_next);
        buttonOA = findViewById(R.id.button_oldAcc) ;
        fAuth = FirebaseAuth.getInstance();
        //To start activity of Login when the button Old Account is clicked
        buttonOA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){startActivity(new Intent (getApplicationContext(), Login.class)); }});
        //To start the registration when button Next is clicked
        buttonNT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = rEmailId.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                //To check if the email edit text is empty
                if(TextUtils.isEmpty(email)){
                    rEmailId.setError("Email Id is mandatory");
                    return;
                }
                //To check if the password edit text is empty
                if(TextUtils.isEmpty(password)){
                    rPassword.setError("Password is mandatory");
                    return;
                }
                //To check if the password is less than 6 characters
                if(password.length() < 6) {
                    rPassword.setError("Password should be longer than 6 characters");
                    return;
                }
                //To start the activity for OTP verification
                startActivity(new Intent (getApplicationContext(), Otp.class));
                //Crete a new user using Email and Password entered with the help of firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If a new user has been created successfully
                        if(task.isSuccessful()){
                            //To get the current user details
                            FirebaseUser user = fAuth.getCurrentUser();
                            //To send a verification email to the current user successful
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification Message has been sent", Toast.LENGTH_LONG).show();
                                }
                            }
                            //To fail to send a verification email to the current user
                            ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG , "On Failure: Message not sent"+e.getMessage() );
                                    Toast.makeText(Register.this, "Verification Message not sent", Toast.LENGTH_LONG).show();
                                }
                            });
                            //To inform the user that their user has been created
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();
                            // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        //To inform the user that their user couldn't be created
                        else{
                            Toast.makeText(Register.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
