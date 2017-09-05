package es.midominio.android.miaplicacion.ej07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class ProcesoActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej07_proceso);
  }

  public void onClickButton(View view) {
    final Intent intent = new Intent(this, OtroProcesoActividad.class);
    startActivity(intent);
  }

}
