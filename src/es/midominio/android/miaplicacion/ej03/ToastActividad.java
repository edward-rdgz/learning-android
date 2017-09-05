package es.midominio.android.miaplicacion.ej03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ToastActividad extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej03_toast);
  }

  public void onClickButtonShortDuration(View view) {
    Toast.makeText(this, getString(R.string.ej03_toast_duracion_corta),
                   Toast.LENGTH_SHORT)
      .show();
  }

  public void onClickButtonLongDuration(View view) {
    Toast.makeText(this, getString(R.string.ej03_toast_duracion_larga),
                   Toast.LENGTH_LONG)
      .show();
  }

}
