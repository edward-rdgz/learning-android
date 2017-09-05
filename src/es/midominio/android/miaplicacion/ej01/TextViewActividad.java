package es.midominio.android.miaplicacion.ej01;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import es.midominio.android.miaplicacion.R;

public class TextViewActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej01_textview);

    final TextView msg = (TextView) findViewById(R.id.ej01_textview_msg);
    msg.setText(R.string.ej01_nuevo_mensaje);
  }

}
