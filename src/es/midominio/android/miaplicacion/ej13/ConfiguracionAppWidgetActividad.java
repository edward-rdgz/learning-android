package es.midominio.android.miaplicacion.ej13;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.midominio.android.miaplicacion.R;

public class ConfiguracionAppWidgetActividad extends Activity {

  private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej13_appwidget_configuracion);

    final Intent intent = getIntent();
    final Bundle extras = intent.getExtras();
    if (extras != null)
      mAppWidgetId =
        extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                      AppWidgetManager.INVALID_APPWIDGET_ID);

    final Intent resultat = new Intent();
    resultat.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultat);
  }

  public void validation(View v) {
    final Intent resultat = new Intent();
    resultat.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultat);
    finish();
  }

}
