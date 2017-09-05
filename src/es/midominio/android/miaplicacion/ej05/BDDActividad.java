package es.midominio.android.miaplicacion.ej05;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;
import es.midominio.android.miaplicacion.R;

public class BDDActividad extends Activity {

  private class BDDAssistant extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NOMBRE_BDD = "miBDD";
    private static final String NOMBRE_TABLA = "miTabla";
    private static final String CAMPO_NOMBRE = "nombre";

    public BDDAssistant(Context context) {
      super(context, NOMBRE_BDD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
      arg0.execSQL("CREATE TABLE " + NOMBRE_TABLA + " ( " + BaseColumns._ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej05_bdd);
    creacionBdd();
    lecturaBdd();
  }

  private void creacionBdd() {
    final BDDAssistant bddAss = new BDDAssistant(this);
    final SQLiteDatabase bdd = bddAss.getWritableDatabase();

    ContentValues valores = new ContentValues();
    valores.put(BDDAssistant.CAMPO_NOMBRE, "Juan_" + System.currentTimeMillis());
    bdd.insertOrThrow(BDDAssistant.NOMBRE_TABLA, null, valores);

    valores = new ContentValues();
    valores.put(BDDAssistant.CAMPO_NOMBRE, "Pedro_" + System.currentTimeMillis());
    bdd.insertOrThrow(BDDAssistant.NOMBRE_TABLA, null, valores);

    valores = new ContentValues();
    valores.put(BDDAssistant.CAMPO_NOMBRE, "Jaime_" + System.currentTimeMillis());
    bdd.insertOrThrow(BDDAssistant.NOMBRE_TABLA, null, valores);

    bdd.close();
    bddAss.close();
  }

  private void lecturaBdd() {
    final BDDAssistant bddAss = new BDDAssistant(this);
    final SQLiteDatabase bdd = bddAss.getReadableDatabase();

    final StringBuffer nombres = new StringBuffer("Nombres :\n");
    final Cursor cursor =
      bdd.query(BDDAssistant.NOMBRE_TABLA,
                new String[] { BDDAssistant.CAMPO_NOMBRE }, null, null, null,
                null, BDDAssistant.CAMPO_NOMBRE + " desc");
    while (cursor.moveToNext()) {
      final String nombre = cursor.getString(0);
      nombres.append(nombre + "\n");
    }

    bdd.close();
    bddAss.close();

    final TextView tv = (TextView) findViewById(R.id.ej05_bdd_nombres);
    tv.setText(nombres);
  }
}
