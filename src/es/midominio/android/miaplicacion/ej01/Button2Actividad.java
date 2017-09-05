package es.midominio.android.miaplicacion.ej01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import es.midominio.android.miaplicacion.R;

public class Button2Actividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej01_boton1);

    final Button boton = (Button) findViewById(R.id.ej01_boton);
    boton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
    	  boton.setText(R.string.ej01_boton_pulsar);
      }
    });
  }

}
