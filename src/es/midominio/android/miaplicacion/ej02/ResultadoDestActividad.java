package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class ResultadoDestActividad extends Activity {

  public static String CLAVE1 = "clave1";
  public static String CLAVE2 = "clave2";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_resultado_dest);

    setResult(RESULT_CANCELED);
  }

  public void onClickButton(View view) {
    final Intent datos = new Intent();
    datos.putExtra(CLAVE1, "valor1");
    datos.putExtra(CLAVE2, true);
    setResult(RESULT_OK, datos);
    finish();
  }

}
