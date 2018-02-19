package br.com.appauth.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import br.com.appauth.CadastroActivity;
import br.com.appauth.model.UsuarioDto;
import br.com.appauth.response.Response;
import br.com.appauth.response.UsuarioResponse;

public class CadastroUsuarioService extends AsyncTask<UsuarioDto, Void, UsuarioResponse> {

    private Context context;
    private ProgressDialog load;

    public CadastroUsuarioService(@NonNull Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        this.load = new ProgressDialog(context);
        this.load.setTitle("Aguarde...");
        this.load.show();
    }

    @Override
    protected UsuarioResponse doInBackground(UsuarioDto... params) {
        try {
            HttpService<UsuarioResponse,UsuarioDto> httpService = new HttpService<>("usuario",UsuarioResponse.class,getToken());
            UsuarioResponse  response = (UsuarioResponse) httpService.post(params[0]);
            return response;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(UsuarioResponse usuarioResponse) {
        if(usuarioResponse == null){
           Toast.makeText(context,"Erro no servidor tente novamente mais tarde",Toast.LENGTH_SHORT).show();
        }else{
            for(String erro : usuarioResponse.getErros()){
                Toast.makeText(this.context,erro,Toast.LENGTH_SHORT).show();
           }
        }
        this.load.dismiss();
    }

    private String getToken(){
        SharedPreferences sharedPref = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        String token = sharedPref.getString("token","");
        return  sharedPref.getString("token","");
    }
}