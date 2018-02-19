package br.com.appauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import br.com.appauth.model.UsuarioDto;
import br.com.appauth.response.TokenResponse;
import br.com.appauth.service.LoginService;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private EditText editLogin, editPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.binding();
        this.novoUsuario();
        this.login();
    }

    private void novoUsuario() {
        this.txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivityForResult(intent, 1);


            }
        });
    }

    private void login() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenResponse response = getToken(getUsuarioDto());
                if (response != null) {
                    if (response.getData().getToken() != null) {
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", response.getData().getToken());
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivityForResult(intent, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Nullable
    private TokenResponse getToken(UsuarioDto usuarioDto) {
        try {
            return new LoginService().execute(usuarioDto).get();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
        return null;
    }

    private UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(this.editLogin.getText().toString());
        usuarioDto.setSenha(this.editPwd.getText().toString());
        return usuarioDto;
    }

    private void binding() {
        this.txtCadastro = (TextView) findViewById(R.id.txtCadastro);
        this.editLogin = (EditText) findViewById(R.id.editLogin);
        this.editPwd = (EditText) findViewById(R.id.editPwd);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
    }


}
