package es.midominio.android.miaplicacion.ej01;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import es.midominio.android.miaplicacion.R;

public class MedidasPantallaActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej01_medidas_pantalla);

    final DisplayMetrics medidas = new DisplayMetrics();
    getWindowManager().getDefaultDisplay()
      .getMetrics(medidas);

    final StringBuffer buf = new StringBuffer();
    buf.append("mesures.density = ")
      .append(medidas.density)
      .append("\nmesures.densityDpi = ")
      .append(medidas.densityDpi)
      .append("\nmesures.widthPixels = ")
      .append(medidas.widthPixels)
      .append("\nmetrics.heightPixels = ")
      .append(medidas.heightPixels)
      .append("\nmetrics.scaledDensity = ")
      .append(medidas.scaledDensity)
      .append("\nmetrics.xdpi = ")
      .append(medidas.xdpi)
      .append("\nmetrics.ydpi = ")
      .append(medidas.ydpi);

    final TextView msg =
      (TextView) findViewById(R.id.ej01_medidas_pantalla_textview);
    msg.setText(buf.toString());
  }

}
