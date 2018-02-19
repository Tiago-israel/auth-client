package br.com.appauth;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnDelicia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.binding();
        final MediaPlayer player = MediaPlayer.create(this,R.raw.delicia);
        this.btnDelicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
    }

    private void binding(){
        this.btnDelicia = (Button)findViewById(R.id.btnDelicia);
    }
}
