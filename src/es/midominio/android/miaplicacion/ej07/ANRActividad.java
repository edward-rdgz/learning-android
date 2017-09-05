package es.midominio.android.miaplicacion.ej07;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class ANRActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej07_anr);
  }

  public void onClickButton(View view) {
    while (true)
      ;
  }

}
