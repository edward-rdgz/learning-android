package es.midominio.android.miaplicacion.ej07;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class HTTPActivite extends Activity {

  private static final String AGENTE_USUARIO = "%s/%s (Android/%s/%s/%s/%s)";
  private static String URL =
    "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20query%3D%22restaurant%22%20and%20location%3D%22san%20francisco%2C%20ca%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
  private static String sAgenteUsuario;

  private HTTPTache mTache;

  private static String getAgentUtilisateur(Context context) {
    if (sAgenteUsuario != null)
      return sAgenteUsuario;

    final PackageManager manager = context.getPackageManager();
    PackageInfo info = null;
    try {
      info = manager.getPackageInfo(context.getPackageName(), 0);
    } catch (final NameNotFoundException e1) {
    }
    final String modelo = Build.MODEL;
    final String versionSistema = Build.VERSION.RELEASE;
    final String codigoSistema = Build.DISPLAY;
    final String paramsReg = Locale.getDefault()
      .toString();
    final String nombrePaquete = info.packageName;
    final String nombreVersion = info.versionName;
    sAgenteUsuario =
      String.format(AGENTE_USUARIO, nombrePaquete, nombreVersion, modelo,
                    versionSistema, codigoSistema, paramsReg);

    return sAgenteUsuario;
  }

  public static boolean isConnected(Context context) {
    final ConnectivityManager connectivityManager =
      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null) {
      final State networkState = networkInfo.getState();
      if (networkState.compareTo(State.CONNECTED) == 0)
        return true;
    }
    return false;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.ej07_http);

    if (isConnected(this) == false) {
      Toast.makeText(this, getString(R.string.ej07_http_conexion_ko),
                     Toast.LENGTH_LONG)
        .show();
      finish();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mTache = new HTTPTache();
    mTache.execute(URL);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mTache != null)
      mTache.cancel(true);
  }

  private class HTTPTache extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(String... params) {
      String respuesta = null;
      final HttpGet httpGet = new HttpGet(params[0]);
      httpGet.setHeader("User-Agent", getAgentUtilisateur(HTTPActivite.this));
      final HttpClient httpClient = new DefaultHttpClient();
      // A partir de Android 2.2, se utiliza la classe AndroidHttpClient
      // final AndroidHttpClient httpClient =
      // AndroidHttpClient.newInstance(getAgentUtilisateur(HTTPActivite.this));
      try {
        respuesta = httpClient.execute(httpGet, new BasicResponseHandler());
      } catch (final HttpResponseException e) {
        // int errno = e.getStatusCode();
        e.printStackTrace();
        return null;
      } catch (final ClientProtocolException e) {
        e.printStackTrace();
      } catch (final IOException e) {
        e.printStackTrace();
      } finally {
        httpClient.getConnectionManager()
          .shutdown();
        // Reemplazar a partir de Android 2.2 por
        // httpClient.close();
      }

      return respuesta;
    }

    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);

      setProgressBarIndeterminateVisibility(false);

      if (result == null) {
        Toast.makeText(HTTPActivite.this, getString(R.string.ej07_http_error),
                       Toast.LENGTH_LONG)
          .show();
        return;
      }

      JSONObject json;
      try {
        json = new JSONObject(result);
        final String nomRestaurant = json.getJSONObject("query")
          .getJSONObject("resultados")
          .getJSONArray("Result")
          .getJSONObject(0)
          .getString("Titulo");
        Toast.makeText(HTTPActivite.this,
                       getString(R.string.ej07_http_resultado, nomRestaurant),
                       Toast.LENGTH_LONG)
          .show();
      } catch (final JSONException e) {
        e.printStackTrace();
      }
    }

    @Override
    protected void onCancelled() {
      setProgressBarIndeterminateVisibility(false);
      Toast.makeText(HTTPActivite.this, getString(R.string.ej07_http_anular),
                     Toast.LENGTH_LONG)
        .show();
    }

  }

}