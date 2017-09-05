package es.midominio.android.miaplicacion.ej09;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import es.midominio.android.miaplicacion.R;

public class PruebasUnitariasActividad extends Activity {

  public static final String CLAVE_TEXTO = "texto";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.ej09_tests);

    final String texto = getIntent().getStringExtra(CLAVE_TEXTO);
    if (texto != null) {
      final EditText mEditText = (EditText) findViewById(R.id.ej09_edittext);
      mEditText.setText(texto);
    }
  }

}
