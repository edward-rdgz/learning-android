package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.MiActividadDeDestino;
import es.midominio.android.miaplicacion.R;

public class IntencionExplicitaActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_intencion_explicita);
  }

  public void onClickButton(View view) {
    final Intent intent = new Intent(this, MiActividadDeDestino.class);
    startActivity(intent);
  }

}
