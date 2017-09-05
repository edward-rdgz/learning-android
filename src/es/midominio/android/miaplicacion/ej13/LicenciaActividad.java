package es.midominio.android.miaplicacion.ej13;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

import es.midominio.android.miaplicacion.R;

public class LicenciaActividad extends Activity {
  // Sustituya la clave por la de AndroidMarket
  private static final String CLAVE_PUBLICA_BASE64 =
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmX/oXOP4MVdLpMk407Ks8fuoTnyk7Ym/jkiQEj2QCg9qyhFE7yWwOkFOu5JloWwfXP3Jk5juWmpi2ksxeJCBBTYLXORGShfqzh4j2SvGTj+LslBBtY5igieipF8yatx/BXFIyML39oUbIeVwQs9UpS3P/5VRyfA2D2WCKl6nF66XWgnN6mXbGePByDhrrStYGVtmhrHMZB39oYuuS/n411/+9iN1wr/1zpeqGhbQUregrfOMKZN4wik52BaEjxGkNcWaaPxiIZLKjV2cI7z2DuCXZ1bjeMcRaAh58EPVvcnF5OiG7sEaYmVeDsa6J/VgNaotv7iBcy2Wo1Ye/hRS9QIDAQAB";

  // Sustituya los siguientes bytes por los que desee
  private static final byte[] SALT =
    new byte[] { -42, 13, -37, 5, 86, 45, -123, 102, -15, -3, 123, 5, 42, -115,
      2, 110, 25, 53, 5, -128 };

  private TextView mEstadoTextView;
  private Button mBoton;

  private ResultatsLicence mLicenciaResultados;
  private LicenseChecker mLicVerificador;
  private Handler mHandler;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.ej13_licencia);
    setProgressBarIndeterminateVisibility(false);

    mHandler = new Handler();
    mEstadoTextView = (TextView) findViewById(R.id.ej13_estado_licencia);
    mBoton = (Button) findViewById(R.id.ej13_boton_verif_licencia);

    final String deviceId =
      Secure.getString(getContentResolver(), Secure.ANDROID_ID);
    final AESObfuscator obf =
      new AESObfuscator(SALT, getPackageName(), deviceId);
    final ServerManagedPolicy politica = new ServerManagedPolicy(this, obf);
    mLicVerificador = new LicenseChecker(this, politica, CLAVE_PUBLICA_BASE64);

    mLicenciaResultados = new ResultatsLicence();
  }

  // A partir de Android 2.2, debe implementarse el método
  // protected Dialog onCreateDdialogd(int id, Bundle args)
  @Override
  protected Dialog onCreateDialog(int id) {
    return new AlertDialog.Builder(this).setTitle(R.string.ej13_dialogo_titulo)
      .setMessage(R.string.ej13_dialogo_mensaje)
      .setPositiveButton(R.string.ej13_dialogo_comprar,
                         new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                             final Intent marketIntent =
                               new Intent(
                                 Intent.ACTION_VIEW,
                                 Uri.parse("http://market.android.com/details?id="
                                   + getPackageName()));
                             startActivity(marketIntent);
                           }
                         })
      .setNegativeButton(R.string.ej13_dialogo_salir,
                         new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                             finish();
                           }
                         })
      .create();
  }

  public void onButtonVerification(View v) {
    setProgressBarIndeterminateVisibility(true);
    mBoton.setEnabled(false);
    mEstadoTextView.setText(R.string.ej13_estado_licencia_en_curso);
    mLicVerificador.checkAccess(mLicenciaResultados);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLicVerificador.onDestroy();
  }

  private void afficheStatut(final String message) {
    mHandler.post(new Runnable() {
      public void run() {
        setProgressBarIndeterminateVisibility(false);
        mEstadoTextView.setText(message);
        mBoton.setEnabled(true);
      }
    });
  }

  private class ResultatsLicence implements LicenseCheckerCallback {
    @Override
    public void allow(int reason) {
      if (isFinishing())
        return;
      afficheStatut(getString(R.string.ej13_estado_licencia_ok));
    }

    @Override
    public void applicationError(int errorCode) {
      if (isFinishing())
        return;
      final String msg =
        String.format(getString(R.string.ej13_error_aplicacion), errorCode);
      afficheStatut(msg);
    }

    @Override
    public void dontAllow(int reason) {
      if (isFinishing())
        return;
      afficheStatut(getString(R.string.ej13_estado_licencia_ko));
      showDialog(0);
    }
  }

}
