package es.midominio.android.miaplicacion.ej05;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ProveedorDeContenidosActividad extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej05_proveedor_de_contenidos);

    final Cursor cursor =
      managedQuery(
                   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                   null, null, null, null);
    final LayoutParams lp = new LayoutParams(200, 200);
    final LinearLayout layout =
      (LinearLayout) findViewById(R.id.ej05_proveedor_de_contenidos);
    if (cursor.isAfterLast()) {
      Toast.makeText(ProveedorDeContenidosActividad.this,
                     R.string.ej05_proveedor_de_contenidos_sin_imagenes,
                     Toast.LENGTH_LONG)
        .show();
      return;
    }
    int n = 10;
    while ((cursor.moveToNext()) && (n-- > 0)) {
      final int col = cursor.getColumnIndex("_data");
      final String uriStr = cursor.getString(col);
      final ImageView img = new ImageView(ProveedorDeContenidosActividad.this);
      img.setImageURI(Uri.parse(uriStr));
      layout.addView(img, lp);
    }
  }

}
