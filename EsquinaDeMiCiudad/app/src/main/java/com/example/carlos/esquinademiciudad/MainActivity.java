package com.example.carlos.esquinademiciudad;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button button;
    Button gale;
    ImageView image;

    private final int SELECT_PICTURE = 200;
    private final int PHOTO_CODE = 100;
    private String APP_DIRECTORY = "Foto";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "prueba.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(   ){
        gale = (Button)findViewById(R.id.galeria);
        gale.setOnClickListener(this);
        button = (Button)findViewById(R.id.tomar);
        button.setOnClickListener(this);
        image = (ImageView)findViewById(R.id.imagen);
    }

    @Override
    public void onClick(View view) {
        int id;
        id = view.getId();
        switch (id)
        {
            case R.id.tomar:
                Bitmap bmp;
                OutputStream ot;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PHOTO_CODE);
                File fl = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
                fl.mkdirs();
                String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY +
                        File.separator + TEMPORAL_PICTURE_NAME;

                File nfl = new File(path);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(nfl));
                bmp = BitmapFactory.decodeResource(getResources(),R.id.imagen);
                try {
                    ot = new FileOutputStream(fl);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100 ,ot);
                    ot.flush();
                    ot.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.galeria:

                Intent inten = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                inten.setType("image/*");
                startActivityForResult(inten.createChooser(inten, "Seleccionar imagen"), SELECT_PICTURE);

                break;
            }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, requestCode, data);

        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle ext = data.getExtras();
                    Bitmap bmp = (Bitmap) ext.get("data");
                    image.setImageBitmap(bmp);
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK){
                    Uri path = data.getData();
                    image.setImageURI(path);
                }break;
        }

    }


}
