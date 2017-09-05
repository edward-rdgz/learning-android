package es.midominio.android.miaplicacion.ej05;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import es.midominio.android.miaplicacion.R;

public class ArchivosActividad extends Activity {

  private static final int TAMAÑO_BUFFER = 1024;
  private static final String NOMBRE_ARCHIVO_LOGO = "logo.jpg";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej05_archivos);

    escrituraArchivoAlmacenamientoInterno();
    lecturaArchivoAlmacenamientoInterno();
    rutas();
  }

  public void escrituraArchivoAlmacenamientoInterno() {
    final Bitmap logo =
      BitmapFactory.decodeResource(getResources(), R.drawable.icono);
    final ByteArrayOutputStream flujoImg = new ByteArrayOutputStream();
    logo.compress(Bitmap.CompressFormat.JPEG, 100, flujoImg);
    final byte[] buffer = flujoImg.toByteArray();

    try {
      final FileOutputStream flujo =
        openFileOutput(NOMBRE_ARCHIVO_LOGO, Context.MODE_PRIVATE);
      flujo.write(buffer);
      flujo.close();
    } catch (final FileNotFoundException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  public void lecturaArchivoAlmacenamientoInterno() {
    final ByteArrayBuffer bufImg = new ByteArrayBuffer(100);
    try {
      final byte[] buffer = new byte[TAMAÑO_BUFFER];
      final FileInputStream flujo = openFileInput(NOMBRE_ARCHIVO_LOGO);
      int n = 0;
      while (-1 != (n = flujo.read(buffer)))
        bufImg.append(buffer, 0, n);
      flujo.close();
      final Bitmap logo =
        BitmapFactory.decodeByteArray(bufImg.buffer(), 0, bufImg.length());
      final ImageView imgView =
        (ImageView) findViewById(R.id.ej05_archivos_imagen);
      imgView.setImageBitmap(logo);
    } catch (final FileNotFoundException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  public void rutas() {
    final String ruta1 = getFilesDir().getPath();
    final String ruta2 = getCacheDir().getPath();
    final String ruta3 = null;
    final String ruta4 = null;
    final String ruta5 = null;
    final String ruta6 = null;
    final String estadoSoporteExterno = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(estadoSoporteExterno)) {
      // Siguiente código disponible a partir de Android 2.2.
      // ruta3 = getExternalCacheDir().getPath();
      // ruta4 = getExternalFilesDir(null).getPath();
      // ruta5 =
      // getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
      // ruta6 =
      // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
    }
    final String rutas =
      getString(R.string.ej05_archivos_rutas, ruta1, ruta2,
                estadoSoporteExterno, ruta3, ruta4, ruta5, ruta6);
    final TextView rutasTV =
      (TextView) findViewById(R.id.ej05_archivos_rutas);
    rutasTV.setText(rutas);
  }
}
