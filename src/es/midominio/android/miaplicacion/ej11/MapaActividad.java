package es.midominio.android.miaplicacion.ej11;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import es.midominio.android.miaplicacion.R;

public class MapaActividad extends MapActivity {

  private MyLocationOverlay mLocUsuario;

  @Override
  protected void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.ej11_mapa);

    final MapView mapa = (MapView) findViewById(R.id.ej11_mapview);
    mapa.setBuiltInZoomControls(true);
    mapa.setSatellite(true);

    // Consulte el capítulo 'Publicar una aplicación' para las features
    // FEATURE_LOCATION disponible a partir de Android 2.2.
    // Considearmos, por defecto, que un dispositivo de localización
    // está presente.
    final boolean locPresent = true;
    // getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION);
    if (locPresent) {
      Toast.makeText(this, getString(R.string.ej11_geoloc_loc),
                     Toast.LENGTH_LONG)
        .show();
      setProgressBarIndeterminateVisibility(true);
      mLocUsuario = new MyLocationOverlay(this, mapa);
      mapa.getOverlays()
        .add(mLocUsuario);

      mLocUsuario.runOnFirstFix(new Runnable() {
        public void run() {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              setProgressBarIndeterminateVisibility(false);
            }
          });
          final MapController controleur = mapa.getController();
          controleur.animateTo(mLocUsuario.getMyLocation());
          controleur.setZoom(15);
        }
      });
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mLocUsuario != null) {
      mLocUsuario.enableMyLocation();
      mLocUsuario.enableCompass();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mLocUsuario != null) {
      mLocUsuario.disableMyLocation();
      mLocUsuario.disableCompass();
    }
  }

  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }

}