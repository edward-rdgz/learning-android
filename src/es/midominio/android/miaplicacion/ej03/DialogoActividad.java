package es.midominio.android.miaplicacion.ej03;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class DialogoActividad extends Activity {
  private static final int DIALOGO_ALERTA = 1;
  private static final int DIALOGO_SELECCION = 2;
  private static final int DIALOGO_PROGRESION = 3;
  private static final int DIALOGO_PROGRESION_IND = 4;

  private ProgressDialog mDialogoProgreso;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.ej03_dialogo);
  }

  public void onClickButtonAlert(View view) {
    showDialog(DIALOGO_ALERTA);
  }

  public void onClickButtonChoix(View view) {
    showDialog(DIALOGO_SELECCION);
  }

  public void onClickButtonProgress(View view) {
    showDialog(DIALOGO_PROGRESION);
    final int valor = (int) (System.currentTimeMillis() % 50) + 1;
    mDialogoProgreso.setProgress(valor);
    mDialogoProgreso.setSecondaryProgress(valor + valor);
  }

  public void onClickButtonProgressInd(View view) {
    showDialog(DIALOGO_PROGRESION_IND);
  }

  // A partir de Android 2.2, debe implementarse el método
  // protected Dialog onCreateDdialogd(int id, Bundle args)
  @Override
  protected Dialog onCreateDialog(int id) {
    switch (id) {
    case DIALOGO_ALERTA: {
      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.ej03_dialogo_alerta)
        .setMessage(R.string.ej03_dialogo_alerta_msg)
        .setCancelable(false)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(android.R.string.ok,
                           new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                               Toast.makeText(DialogoActividad.this,
                                              android.R.string.ok,
                                              Toast.LENGTH_SHORT)
                                 .show();
                             }
                           })
        .setNegativeButton(android.R.string.cancel,
                           new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                               Toast.makeText(DialogoActividad.this,
                                              android.R.string.cancel,
                                              Toast.LENGTH_SHORT)
                                 .show();
                             }
                           });
      return builder.create();
    }
    case DIALOGO_SELECCION: {
      final String[] seleccion =
        { getString(R.string.ej03_dialogo_seleccion1),
          getString(R.string.ej03_dialogo_seleccion2),
          getString(R.string.ej03_dialogo_seleccion3) };

      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.ej03_dialogo_alerta)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setItems(seleccion, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            Toast.makeText(DialogoActividad.this, seleccion[item],
                           Toast.LENGTH_SHORT)
              .show();
          }
        });
      return builder.create();
    }
    case DIALOGO_PROGRESION: {
      final ProgressDialog progressDialog = new ProgressDialog(this);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      progressDialog.setMessage(getString(R.string.carga));
      progressDialog.setTitle(R.string.ej03_dialogo_progresion);
      progressDialog.setMax(100);
      progressDialog.setProgress(0);
      progressDialog.setIcon(R.drawable.icono);
      mDialogoProgreso = progressDialog;
      return progressDialog;
    }
    case DIALOGO_PROGRESION_IND: {
      final ProgressDialog progressDialog = new ProgressDialog(this);
      progressDialog.setMessage(getString(R.string.carga));
      progressDialog.setTitle(R.string.ej03_dialogo_progresion_ind);
      progressDialog.setIcon(R.drawable.icono);
      return progressDialog;
    }
    default:
      return null;
    }
  }
}
