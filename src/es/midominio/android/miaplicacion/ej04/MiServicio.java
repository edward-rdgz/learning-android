package es.midominio.android.miaplicacion.ej04;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import es.midominio.android.miaplicacion.MiActividadDeDestino;
import es.midominio.android.miaplicacion.R;

public class MiServicio extends Service {

  public static final String CLAVE_STOP = "stop";

  @Override
  public void onCreate() {
    super.onCreate();
    Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // La ejecucións se realiza en el thread principal.
    // Para no bloquear la aplicació, se recomienda
    // ejecutar todos los procesamientos largos en un thread secundario.
    // Consulte el Capítulo 'Concurrencia, seguridad y red'.

    Toast.makeText(this, "onStartCommand: startId=" + startId,
                   Toast.LENGTH_SHORT)
      .show();

    afficheNotification(startId);

    final boolean stop = intent.getBooleanExtra(CLAVE_STOP, false);
    if (stop) {
      Toast.makeText(this, "stopSelf", Toast.LENGTH_SHORT)
        .show();
      stopSelf();
    }

    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT)
      .show();
    final NotificationManager notificationManager =
      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancel(0);
    super.onDestroy();
  }

  private void afficheNotification(final int nb) {
    // El método setOngoing permite agregar una notificación
    // persistente = que no puede suprimirla el usuario
    // Esto permite indicar al usuario que existe un servicio en curso.
    final long[] patternVibrations =
      { 0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

    final Intent notificationIntent =
      new Intent(this, MiActividadDeDestino.class);
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    final PendingIntent pendingIntent =
      PendingIntent.getActivity(this, 0, notificationIntent, 0);

    final Notification notification =
      new Notification(R.drawable.icono,
        getString(R.string.ej04_notificacion_ticker),
        System.currentTimeMillis());
    notification.setLatestEventInfo(
                                    this,
                                    getString(R.string.ej04_notificacion_titulo),
                                    getString(R.string.ej04_notificacion_texto),
                                    pendingIntent);

    notification.defaults |=
      Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
    notification.flags |= Notification.FLAG_ONGOING_EVENT;
    notification.vibrate = patternVibrations;

    if (notification != null) {
      final NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.notify(0, notification);
    }
  }

  // ////////////////////////////////////////////////////////
  // Relativo a las conexiones del servicio //

  // Esta clase permite especializar la clase Binder
  // devolviendo la instancia del servicio MiServicio.
  public class MonServiceBinder extends Binder {
    MiServicio getService() {
      return MiServicio.this;
    }
  }

  // El objeto mBinder es el objeto que se provee al componente
  // mediante el método onBind.
  private final IBinder mBinder = new MonServiceBinder();

  @Override
  public IBinder onBind(Intent intent) {
    Toast.makeText(this, "onBind", Toast.LENGTH_SHORT)
      .show();
    afficheNotification(0);
    return mBinder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT)
      .show();
    return super.onUnbind(intent);
    // Remplace la línea anterior por la siguiente para permitir
    // llamadas al método onRebind;
    // return true;
  }

  @Override
  public void onRebind(Intent intent) {
    Toast.makeText(this, "onRebind", Toast.LENGTH_SHORT)
      .show();
    super.onRebind(intent);

  }

  public void faisQuelqueChose() {
    Toast.makeText(this, "hacerAlgo", Toast.LENGTH_SHORT)
      .show();
  }
}
