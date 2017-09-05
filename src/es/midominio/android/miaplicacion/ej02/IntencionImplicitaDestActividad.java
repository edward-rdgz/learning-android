package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class IntencionImplicitaDestActividad extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_destinatario);

    final String uri = getIntent().getDataString();
    if (uri != null)
      Toast.makeText(this, uri, Toast.LENGTH_LONG)
        .show();
  }

}