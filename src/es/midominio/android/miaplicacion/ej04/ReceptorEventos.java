package es.midominio.android.miaplicacion.ej04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ReceptorEventos extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Toast.makeText(context, R.string.ej04_receptor_evts_recepcion_evt,
                   Toast.LENGTH_SHORT)
      .show();
  }

}
