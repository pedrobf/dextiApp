package com.gruposeb.pedro.pim.activity.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.gruposeb.pedro.pim.R;
import com.gruposeb.pedro.pim.activity.config.ConfiguracaoFirebase;
import com.gruposeb.pedro.pim.activity.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button loginBtn;
    private  Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editPassword);
        loginBtn = findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if ( !email.isEmpty() ) {
                    if ( !senha.isEmpty() ) {
                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        validaLogin();

                        } else {
                            Toast.makeText(LoginActivity.this, "Preencha a senha"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha o email"
                                , Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    public void validaLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this
                    , "Logado com sucesso!"
                    , Toast.LENGTH_SHORT).show();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario não esta cadastrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Usuário ou senha inválida!";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuario " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this
                    , excecao
                    , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
