package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class IntencionImplicitaActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_intencion_implicita);
  }

  public void onClickButtonAction1(View view) {
    final Intent intent = new Intent("es.midominio.android.miaplicacion.ACCION1");
    intent.addCategory("es.midominio.android.miaplicacion.CATEGORIA1");
    startActivity(intent);
  }

  public void onClickButtonView(View view) {
    final Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("http://www.editions-eni.fr"));
    startActivity(intent);
  }

}
