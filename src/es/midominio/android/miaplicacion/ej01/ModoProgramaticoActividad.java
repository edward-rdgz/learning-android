package es.midominio.android.miaplicacion.ej01;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import es.midominio.android.miaplicacion.R;

public class ModoProgramaticoActividad extends Activity {

  private RelativeLayout mLayout;
  private TextView mTexte;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mLayout = new RelativeLayout(this);
    // A partir de Android 2.2, FILL_PARENT ddebe remplazarse por MATCH_PARENT
    final LayoutParams lp =
      new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    mLayout.setLayoutParams(lp);

    mTexte = new TextView(this);
    final LayoutParams lpTv =
      new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    mLayout.addView(mTexte, lpTv);

    final int orientacionPantalla = getResources().getConfiguration().orientation;
    setValoresSegunOrientacionPantalla(orientacionPantalla);

    setContentView(mLayout);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    setValoresSegunOrientacionPantalla(newConfig.orientation);
  }

  private void setValoresSegunOrientacionPantalla(int orientacionPantalla) {
    if (orientacionPantalla == Configuration.ORIENTATION_LANDSCAPE) {
      mLayout.setBackgroundColor(Color.BLACK);
      mTexte.setText(R.string.ej01_mensaje_apaisado);
    } else {
      mLayout.setBackgroundColor(Color.BLUE);
      mTexte.setText(R.string.ej01_mensaje_vertical);
    }
  }
}