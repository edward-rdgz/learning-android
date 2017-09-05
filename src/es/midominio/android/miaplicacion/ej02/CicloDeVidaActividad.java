package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class CicloDeVidaActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_ciclodevida);
    Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onStart() {
    super.onStart();
    Toast.makeText(this, "onStart", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onResume() {
    super.onResume();
    Toast.makeText(this, "onResume", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onPause() {
    super.onPause();
    Toast.makeText(this, "onPause - isFinishing=" + isFinishing(),
                   Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onStop() {
    super.onStop();
    Toast.makeText(this, "onStop - isFinishing=" + isFinishing(),
                   Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  protected void onDestroy() {
    Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT)
      .show();
    super.onDestroy();
  }

}
