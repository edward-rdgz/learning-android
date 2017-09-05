package es.midominio.android.miaplicacion.ej03;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class MenuActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej03_menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    final MenuInflater convertidorMenu = getMenuInflater();
    convertidorMenu.inflate(R.menu.ej03_principal, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Toast.makeText(this,
                   getString(R.string.ej03_menu_seleccion, item.getTitle()),
                   Toast.LENGTH_SHORT)
      .show();

    switch (item.getItemId()) {
    case R.id.menu_principal_opcion1:
      return true;
    default:
      break;
    }
    return super.onOptionsItemSelected(item);
  }
}
