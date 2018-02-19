package br.com.appauth.service;

import android.os.AsyncTask;

import br.com.appauth.model.UsuarioDto;
import br.com.appauth.response.TokenResponse;

public class LoginService extends AsyncTask<UsuarioDto, Void, TokenResponse> {


    @Override
    protected TokenResponse doInBackground(UsuarioDto... params) {
        HttpService<TokenResponse,UsuarioDto>httpService = new HttpService<>("usuario/login",TokenResponse.class);
        TokenResponse tokenResponse = httpService.post(params[0]);
        return tokenResponse;
    }
}