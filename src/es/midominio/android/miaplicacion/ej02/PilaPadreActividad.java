package es.midominio.android.miaplicacion.ej02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import es.midominio.android.miaplicacion.MiActividadPrincipal;
import es.midominio.android.miaplicacion.R;

public abstract class PilaPadreActividad extends Activity {
  public static String CLAVE_PILA = "pile";

  private String mPila;
  private TextView mPilaTV;
  private Button mDesapilarABoton;
  private Button mDesapilarBBoton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej02_pila);

    mPila = getIntent().getStringExtra(CLAVE_PILA);
    if (mPila == null)
      mPila = getString(R.string.ej02_pila_init) + "A";
    mPilaTV = (TextView) findViewById(R.id.ej02_pila_estado);
    mPilaTV.setText(mPila);

    mDesapilarABoton = (Button) findViewById(R.id.ej02_pila_boton_desapilar_a);
    mDesapilarBBoton = (Button) findViewById(R.id.ej02_pila_boton_desapilar_b);
    actualizarBotones();
  }

  public void onClickButtonEmpilerA(View view) {
    final Intent intent = new Intent(this, PilaAActividad.class);
    intent.putExtra(CLAVE_PILA, mPila + "A");
    startActivity(intent);
    actualizarBotones();
  }

  public void onClickButtonEmpilerB(View view) {
    final Intent intent = new Intent(this, PilaBActividad.class);
    intent.putExtra(CLAVE_PILA, mPila + "B");
    startActivity(intent);
    actualizarBotones();
  }

  // El flag FLAG_ACTIVITY_CLEAR_TOP desapila las actividades hasta encontrar
  // la primera actividad de tipo indicado, aquí  PilaAActividad.
  // El flag FLAG_ACTIVITY_SINGLE_TOP permite reutilizar la actividad
  // PilaAActividad de destino sin volver a crearla.
  public void onClickButtonDepilerJusquaA(View view) {
    final Intent intent = new Intent(this, PilaAActividad.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
    actualizarBotones();
  }

  // El flag FLAG_ACTIVITY_CLEAR_TOP desapila las actividades hasta encontrar
  // lla primera actividad de tipo indicado, aquí PilaBActividad.
  // El flag FLAG_ACTIVITY_SINGLE_TOP permite reutilizar la actividad
  // PilaBActividad de destino sin volver a crearla.
  public void onClickButtonDepilerJusquaB(View view) {
    final Intent intent = new Intent(this, PilaBActividad.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
    actualizarBotones();
  }

  // Para desapilar todas las actividades hasta la actividad de inicio
  // MiActividadPrincipal, debe especificarse el atributo
  // android:launchMode="singleTask" en su etiqueta activity.
  public void onClickButtonDesapilarTodo(View view) {
    final Intent intent = new Intent(this, MiActividadPrincipal.class);
    startActivity(intent);
  }

  private void actualizarBotones() {
    mDesapilarABoton.setEnabled(mPila.matches(".*AB+$"));
    mDesapilarBBoton.setEnabled(mPila.matches(".*BA+$"));
  }

}
