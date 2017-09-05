package es.midominio.android.miaplicacion;

import es.midominio.android.miaplicacion.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

public class SplashActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splashscreen);

    new Handler().postAtTime(new Runnable() {
      @Override
      public void run() {
        final Intent intent =
          new Intent(SplashActivity.this, MiActividadPrincipal.class);
        startActivity(intent);
      }
    }, SystemClock.uptimeMillis() + 1000);
  }
}