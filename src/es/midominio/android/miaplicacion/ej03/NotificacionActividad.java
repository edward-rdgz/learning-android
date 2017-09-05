package es.midominio.android.miaplicacion.ej03;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import es.midominio.android.miaplicacion.MiActividadDeDestino;
import es.midominio.android.miaplicacion.R;

public class NotificacionActividad extends Activity {
  private int mId = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej03_notificacion);
  }

  public void onClickButton(View view) {
    mId++;

    final long[] patternVibrations =
      { 0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

    final Intent notificacionIntent =
      new Intent(this, MiActividadDeDestino.class);
    notificacionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    final PendingIntent pendingIntent =
      PendingIntent.getActivity(this, 0, notificacionIntent, 0);

    final Notification notificacion =
      new Notification(R.drawable.icono,
        getString(R.string.ej03_notificacion_ticker),
        System.currentTimeMillis());
    notificacion.setLatestEventInfo(
                                    this,
                                    getString(R.string.ej03_notificacion_titulo),
                                    getString(R.string.ej03_notificacion_texto),
                                    pendingIntent);

    notificacion.defaults |= Notification.DEFAULT_LIGHTS;
    notificacion.flags |= Notification.FLAG_AUTO_CANCEL;
    notificacion.vibrate = patternVibrations;

    Toast.makeText(
                   this,
                   (notificacion == null) ? R.string.ej03_notificacion_texto
                     : R.string.ej03_notificacion_agregado, Toast.LENGTH_LONG)
      .show();

    if (notificacion != null) {
      final NotificationManager notificacionManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      notificacionManager.notify(mId, notificacion);
    }
  }
}
