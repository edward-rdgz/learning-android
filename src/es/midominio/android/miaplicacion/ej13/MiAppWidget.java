package es.midominio.android.miaplicacion.ej13;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.widget.RemoteViews;
import android.widget.Toast;
import es.midominio.android.miaplicacion.MiActividadPrincipal;
import es.midominio.android.miaplicacion.R;

public class MiAppWidget extends AppWidgetProvider {
  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT)
      .show();
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
    int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Toast.makeText(
                   context,
                   context.getString(R.string.ej13_appwidget_actualiz,
                                     appWidgetIds.length), Toast.LENGTH_SHORT)
      .show();

    for (final int appWidgetId : appWidgetIds)
      Toast.makeText(context, "AppWidget Id= " + appWidgetId,
                     Toast.LENGTH_SHORT)
        .show();
    final Time t = new Time();
    t.setToNow();
    final RemoteViews views =
      new RemoteViews(context.getPackageName(), R.layout.ej13_appwidget);
    views.setTextViewText(
                          R.id.appwidget_titulo,
                          t.format(context.getString(R.string.ej13_appwidget_formato_hora)));

    final Intent intent = new Intent(context, MiActividadPrincipal.class);
    final PendingIntent pIntent =
      PendingIntent.getActivity(context, 0, intent, 0);
    views.setOnClickPendingIntent(R.id.appwidget_img, pIntent);

    appWidgetManager.updateAppWidget(appWidgetIds, views);
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
    Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT)
      .show();
  }

}
