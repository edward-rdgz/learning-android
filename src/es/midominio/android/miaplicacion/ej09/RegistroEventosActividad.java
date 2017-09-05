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

    Log.v(TAG, "Mensaje de categor�a Verboso");
    Log.d(TAG, "Mensaje de categor�a Depuraci�n");
    Log.i(TAG, "Mensaje de categor�a Informaci�n");
    Log.w(TAG, "Mensaje de categor�a Advertencia");
    Log.e(TAG, "Mensaje de categor�a Error");
  }

}
