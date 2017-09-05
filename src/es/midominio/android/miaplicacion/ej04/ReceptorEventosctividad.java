package es.midominio.android.miaplicacion.ej04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class ReceptorEventosctividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej04_receptor_evts_actividad);
  }

  public void onClickButtonEnvoiEvt(View v) {
    final Intent intent = new Intent("es.midominio.android.miaplicacion.EVT_1");
    sendBroadcast(intent);
  }

}
