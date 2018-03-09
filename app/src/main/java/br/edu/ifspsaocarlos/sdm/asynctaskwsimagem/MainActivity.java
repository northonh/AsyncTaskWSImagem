package br.edu.ifspsaocarlos.sdm.asynctaskwsimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    // Constantes para acesso ao WS
    private final String URL_BASE = "http://www.nobile.pro.br/sdm/";
    private final String ARQUIVO_IMAGEM = "logo_ifsp.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void acessarWs(View view) {
        if (view.getId() == R.id.bt_acessar_ws) {
            buscarImagem(URL_BASE + ARQUIVO_IMAGEM);
        }
    }

    private void buscarImagem(String url) {
        AsyncTask<String, Void, Bitmap> buscaImagemAT = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                // Recupera a URL que veio por parâmetro
                String url = strings[0];
                try {
                    // Cria uma Conexão Http a partir da URL
                    HttpURLConnection conexao = (HttpURLConnection) (new URL(url)).openConnection();
                    if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // Se a conexão foi bem sucedida, extrai um InputStream
                        InputStream inputStream = conexao.getInputStream();
                        // Gera um Bitmap a partir de um BitmapFactory usando o InputStream e retorna
                        return BitmapFactory.decodeStream(inputStream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                ImageView imagemIV = findViewById(R.id.iv_imagem);
                imagemIV.setImageBitmap(bitmap);
            }
        };

        // Coloca a AsyncTask para executar
        buscaImagemAT.execute(url);
    }

}
