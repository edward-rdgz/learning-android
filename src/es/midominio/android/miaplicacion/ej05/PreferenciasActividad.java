package es.midominio.android.miaplicacion.ej05;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import es.midominio.android.miaplicacion.R;

public class PreferenciasActividad extends Activity {

  private static final String PREF_SELECCION = "seleccion";
  private static final String PREF_TEXTO = "texto";
  private static final int OPCION_A = 0;
  private static final int OPCION_B = 1;

  private RadioGroup mSeleccionRG;
  private EditText mTextoET;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej05_prefs);

    mSeleccionRG = (RadioGroup) findViewById(R.id.ej05_prefs_eleccion);
    mTextoET = (EditText) findViewById(R.id.ej05_prefs_texto);

    restaura();
  }

  public void onClickButtonCopiaDeSeguridad(View view) {
    salvaguarda();
  }

  public void onClickButtonRestaura(View view) {
    restaura();
  }

  public void salvaguarda() {
    final SharedPreferences.Editor editPrefs =
      getPreferences(Context.MODE_PRIVATE).edit();
    final int seleccion =
      (mSeleccionRG.getCheckedRadioButtonId() == R.id.ej05_prefs_opcion_b)
        ? OPCION_B : OPCION_A;
    editPrefs.putInt(PREF_SELECCION, seleccion)
      .putString(PREF_TEXTO, mTextoET.getText()
        .toString())
      .commit();
  }

  public void restaura() {
    final SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
    final int seleccion = prefs.getInt(PREF_SELECCION, 0);
    mSeleccionRG.check((seleccion == OPCION_B) ? R.id.ej05_prefs_opcion_b
      : R.id.ej05_prefs_opcion_a);
    final String texto = prefs.getString(PREF_TEXTO, "");
    mTextoET.setText(texto);
  }
}
