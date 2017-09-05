package es.midominio.android.miaplicacion.ej02;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import es.midominio.android.miaplicacion.R;

public class RestaurarActividad extends Activity {

  private static final String CLAVE_COLOR = "color";

  private View mLayout;
  private CheckBox mSalvaguardaCheckBox;
  private int mColor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_restauracion);

    mSalvaguardaCheckBox =
      (CheckBox) findViewById(R.id.ej02_restauracion_checkbox);
    mLayout = findViewById(R.id.ej02_restauracion_layout);

    final Random r = new Random(System.currentTimeMillis());
    mColor = r.nextInt();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mLayout.setBackgroundColor(mColor);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mSalvaguardaCheckBox.isChecked())
      outState.putInt(CLAVE_COLOR, mColor);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (mSalvaguardaCheckBox.isChecked())
      mColor = savedInstanceState.getInt(CLAVE_COLOR);
  }

}
