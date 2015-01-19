package com.example.patrichuan.quickexpress;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class PantallaDeJuego extends ActionBarActivity {

    private Palabra Palabra;
    private ImageView ImagenBomba;
    private TextView PalabraTextView;
    private MediaPlayer SoundCuentaAtras;
    private MediaPlayer SoundExplosion;
    private Boolean BombaActivada;
    private Button BotonSiguiente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Ocultar ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.pantalladejuego_activity);
        Inicializar();

        // Listeners
        BotonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedirPalabra();
            }
        });

        ImagenBomba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarBomba();
            }
        });
    }

    public void Inicializar() {

        BotonSiguiente = (Button) findViewById(R.id.BotonSiguiente);
        ImagenBomba = (ImageView) findViewById(R.id.ImagenBomba);
        PalabraTextView = (TextView) findViewById(R.id.PalabraTextView);
        Palabra = new Palabra(this);
        BombaActivada = false;
    }

    public void PedirPalabra() {

        // Grito el YES !! y libero el MediaPlayer
        MediaPlayer SoundYes = MediaPlayer.create(PantallaDeJuego.this, R.raw.small_crowd_says_yes);
        SoundYes.start();
        SoundYes.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        // Cambio la palabra
        PalabraTextView.setText(Palabra.getPalabra());

        // Animo el texto
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        animacion.setFillAfter(true);
        PalabraTextView.setAnimation(animacion);
    }

    public void LanzarBomba () {

        // Paro y "libero" los MediaPlayers que esten sonando
        if (SoundCuentaAtras!=null) {
            SoundCuentaAtras.stop();
            SoundCuentaAtras.release();
            SoundCuentaAtras=null;
        }

        if (SoundExplosion!=null){
            SoundExplosion.stop();
            SoundExplosion.release();
            SoundExplosion=null;
        }

        // Asigno los sonidos
        SoundCuentaAtras = MediaPlayer.create(PantallaDeJuego.this, R.raw.cuentaatras55sec);
        SoundExplosion = MediaPlayer.create(PantallaDeJuego.this, R.raw.explosion);

        // Actualizo el sprite de la bomba para informar de su estado
        // - Si esta activada la desactivo
        // - Si esta desactivada la activo
        if (!BombaActivada) {
            SoundCuentaAtras.start();
            BombaActivada = true;
            ImagenBomba.setImageResource(R.drawable.bombactivada);
        } else {
            SoundCuentaAtras.stop();
            BombaActivada = false;
            ImagenBomba.setImageResource(R.drawable.bombadesactivada);
        }

        // Listeners de los sonidos
        SoundCuentaAtras.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                SoundExplosion.start();
            }
        });

        SoundExplosion.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent Intent = new Intent(PantallaDeJuego.this, FinDejuego.class);
                startActivity(Intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantallainicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop () {
        if(SoundCuentaAtras!= null)
        {
            SoundCuentaAtras.stop();
            SoundCuentaAtras.release();
            SoundCuentaAtras=null;
        }

        super.onStop();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(SoundCuentaAtras!= null)
            {
                SoundCuentaAtras.stop();
                SoundCuentaAtras=null;
            }

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
