package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ResultadoActividad extends Activity {

  private static final int REQ_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_resultado);
  }

  public void onClickButton(View view) {
    final Intent intent = new Intent(this, ResultadoDestActividad.class);
    startActivityForResult(intent, REQ_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case REQ_CODE:
      String texto;
      final String resTxt =
        (resultCode == RESULT_OK) ? "RESULT_OK" : "RESULT_CANCELED";
      if (resultCode == RESULT_OK) {
        final String val1 = data.getStringExtra(ResultadoDestActividad.CLAVE1);
        final boolean val2 =
          data.getBooleanExtra(ResultadoDestActividad.CLAVE2, false);
        texto =
          getString(R.string.ej02_resultado_ok, resTxt,
                    ResultadoDestActividad.CLAVE1, val1, ResultadoDestActividad.CLAVE2,
                    val2);
      } else
        texto = getString(R.string.ej02_resultado_ko, resTxt);
      Toast.makeText(this, texto, Toast.LENGTH_LONG)
        .show();
      break;
    // ...
    default:
      super.onActivityResult(requestCode, resultCode, data);
      break;
    }
  }

}
