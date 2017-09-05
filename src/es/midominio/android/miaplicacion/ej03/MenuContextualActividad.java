package es.midominio.android.miaplicacion.ej03;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class MenuContextualActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.ej03_menu_contextual);

    final View vue = findViewById(R.id.ej03_menu_contextual);
    registerForContextMenu(vue);
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
    ContextMenuInfo menuInfo) {
    switch (v.getId()) {
    case R.id.ej03_menu_contextual:
      final MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.ej03_contextual, menu);
      break;
    default:
      super.onCreateContextMenu(menu, v, menuInfo);
      break;
    }
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    Toast.makeText(this,
                   getString(R.string.ej03_menu_seleccion, item.getTitle()),
                   Toast.LENGTH_SHORT)
      .show();
    switch (item.getItemId()) {
    case R.id.menu_contextual_opcion3:
    case R.id.menu_contextual_opcion4:
    case R.id.menu_contextual_opcion5:
    case R.id.menu_contextual_opcion6:
      item.setChecked(!item.isChecked());
      return true;
    case R.id.menu_contextual_opcion1:
      Toast.makeText(this, getString(R.string.ej03_menu_seleccion_opcion1),
                     Toast.LENGTH_SHORT)
        .show();
      return true;
    }
    return super.onContextItemSelected(item);
  }

}
