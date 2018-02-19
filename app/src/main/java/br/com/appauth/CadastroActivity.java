package br.com.appauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.com.appauth.model.UsuarioDto;
import br.com.appauth.response.UsuarioResponse;
import br.com.appauth.service.CadastroUsuarioService;

public class CadastroActivity extends AppCompatActivity {

    private static final int CADASTRO = 1;
    private EditText editNome, editEmail, editSenha, editConfirmSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.binding();
        this.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos() && validarSenhasCorrespondentes() && validarTamanhoSenha()){
                    cadastrarUsuario(novoUsuario());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CADASTRO){
            finish();
        }
    }

    private UsuarioDto novoUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome(editNome.getText().toString());
        usuarioDto.setEmail(editEmail.getText().toString());
        usuarioDto.setSenha(editSenha.getText().toString());
        return usuarioDto;
    }

    private void cadastrarUsuario(UsuarioDto usuarioDto) {
        try {
            UsuarioResponse usuarioResponse = new CadastroUsuarioService(CadastroActivity.this).execute(usuarioDto).get();
            if(usuarioResponse != null && usuarioResponse.getData() != null){
                Toast.makeText(this,"Usuário cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivityForResult(intent,CADASTRO);
            }

        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
    }

    private void binding() {
        this.editNome = (EditText) findViewById(R.id.editNome);
        this.editEmail = (EditText) findViewById(R.id.editEmail);
        this.editSenha = (EditText) findViewById(R.id.editSenha);
        this.editConfirmSenha = (EditText) findViewById(R.id.editConfirmSenha);
        this.btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
    }

    private boolean validarCampos(){
        boolean condicao = true;
        if(this.editNome.getText().toString().isEmpty()){
            this.setMessagemErro(this.editNome,"Nome");
            condicao = false;
        }if(this.editEmail.getText().toString().isEmpty()){
            this.setMessagemErro(this.editEmail,"Email");
            condicao = false;
        }if(this.editSenha.getText().toString().isEmpty()){
            this.setMessagemErro(this.editSenha,"Senha");
            condicao = false;
        }if(this.editConfirmSenha.getText().toString().isEmpty()){
            this.setMessagemErro(this.editConfirmSenha,"Confirmar senha");
            condicao = false;
        }
        return condicao;
    }

    private boolean validarSenhasCorrespondentes(){
        if(!this.editSenha.getText().toString().equals(this.editConfirmSenha.getText().toString())){
            this.editConfirmSenha.setError("As senhas não correspondem");
            return false;
        }
        return true;
    }

    private boolean validarTamanhoSenha(){
        if(!(this.editSenha.getText().toString().length() >= 5 && this.editSenha.getText().toString().length() <= 20)){
            this.editSenha.setError("O campo senha deve conter entre 5 e 20 caracteres");
            return false;
        }
        return true;
    }

    private void setMessagemErro(EditText editText , String nomeCampo){
        editText.setError("O campo "+nomeCampo+" é obrigatório.");
    }
}
