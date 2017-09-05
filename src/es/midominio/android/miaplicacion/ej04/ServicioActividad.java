package es.midominio.android.miaplicacion.ej04;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ServicioActividad extends Activity {

  private Button mBotonBindUnbind;
  private Button mBotonAction;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej04_service);

    mBotonAction = (Button) findViewById(R.id.ej04_service_boton_action);
    mBotonBindUnbind =
      (Button) findViewById(R.id.ej04_service_boton_bind_unbind);
  }

  public void onClickButtonStartService(View v) {
    final Intent intent = new Intent(this, MiServicio.class);
    startService(intent);
  }

  public void onClickButtonStopService(View v) {
    final Intent intent = new Intent(this, MiServicio.class);
    stopService(intent);
  }

  public void onClickButtonStopSelfService(View v) {
    final Intent intent = new Intent(this, MiServicio.class);
    intent.putExtra(MiServicio.CLAVE_STOP, true);
    startService(intent);
  }

  // ////////////////////////////////////////////////////////
  // Esto gestiona las conexiones al servicio //

  private MiServicio mService;

  public void onClickButtonBindUnbindService(View v) {
    if (mService == null) {
      final Intent intent = new Intent(this, MiServicio.class);
      bindService(intent, mConnexion, Context.BIND_AUTO_CREATE);
    } else {
      unbindService(mConnexion);
      mService = null;
      mBotonAction.setEnabled(false);
      mBotonBindUnbind.setText(R.string.ej04_service_boton_bind);
    }
  }

  public void onClickButtonActionService(View v) {
    mService.faisQuelqueChose();
  }

  private final ServiceConnection mConnexion = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName arg0, IBinder arg1) {
      Toast.makeText(ServicioActividad.this, "onServiceConnected",
                     Toast.LENGTH_SHORT)
        .show();
      mService = ((MiServicio.MonServiceBinder) arg1).getService();
      mBotonAction.setEnabled(true);
      mBotonBindUnbind.setText(R.string.ej04_service_boton_unbind);
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      Toast.makeText(ServicioActividad.this, "onServiceDisconnected",
                     Toast.LENGTH_SHORT)
        .show();
      mService = null;
      mBotonAction.setEnabled(false);
      mBotonBindUnbind.setText(R.string.ej04_service_boton_bind);
    }

  };

}
