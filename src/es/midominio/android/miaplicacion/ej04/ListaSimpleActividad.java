package es.midominio.android.miaplicacion.ej04;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ListaSimpleActividad extends ListActivity {

  private String[] mEtiquetas;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mEtiquetas = getResources().getStringArray(R.array.etiquetas);

    final ArrayAdapter<String> adaptador =
      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        mEtiquetas);

    setListAdapter(adaptador);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Toast.makeText(this, mEtiquetas[position], Toast.LENGTH_SHORT)
      .show();
  }

}
