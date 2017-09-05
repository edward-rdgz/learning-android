package es.midominio.android.miaplicacion.ej09;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import es.midominio.android.miaplicacion.R;

public class RegistroEventosActividad extends Activity {

  private static final String TAG = "RegistroEventosActividad";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.ej09_logs);

    Log.v(TAG, "Mensaje de categoría Verboso");
    Log.d(TAG, "Mensaje de categoría Depuración");
    Log.i(TAG, "Mensaje de categoría Información");
    Log.w(TAG, "Mensaje de categoría Advertencia");
    Log.e(TAG, "Mensaje de categoría Error");
  }

}
