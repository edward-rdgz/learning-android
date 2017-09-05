package es.midominio.android.miaplicacion.ej11;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;
import es.midominio.android.miaplicacion.R;

public class GeolocActividad extends Activity {

  private LocationManager mLocManager;
  private ToggleButton mBotonActRegulares;
  private EditText mInfos;
  private Criteria mCriterios;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej11_geoloc);

    // Consulte el capítulo 'Publicar una aplicación' para las features
    // FEATURE_LOCATION disponible a partir de Android 2.2.
    // Se considera por defecto que un dispositivo de localización
    // está presente.
    // final boolean locPresent =
    // getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION);
    // if (locPresent == false) {
    // Toast.makeText(this, getString(R.string.ex09_geoloc_pas_de_materiel),
    // Toast.LENGTH_LONG);
    // finish();
    // return;
    // }

    mBotonActRegulares =
      (ToggleButton) findViewById(R.id.ej11_geoloc_actualiz_regular);
    mInfos = (EditText) findViewById(R.id.ej11_geoloc_textview);
    mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    mCriterios = new Criteria();
    mCriterios.setAccuracy(Criteria.ACCURACY_COARSE);
  }

  public void onClickButtonCache(View v) {
    final LocationManager locManager =
      (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Location loc =
      locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    mostrarLocalizacion(R.string.ej11_geoloc_cache_red, loc);
    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    mostrarLocalizacion(R.string.ej11_geoloc_cache_gps, loc);
    // PASSIVE_PROVIDER disponible a partir de Android 2.2
    // loc = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    // mostrarLocalizacion(R.string.ex09_geoloc_cache_passif, loc);
  }

  public void onClickButtonActualizRegular(View v) {
    if (mBotonActRegulares.isChecked())
      try {
        final String provider = mLocManager.getBestProvider(mCriterios, true);
        mLocManager.requestLocationUpdates(provider, 0, 0.f, mLocListener);
      } catch (final IllegalArgumentException e) {
        mostrar(e.getMessage());
        mBotonActRegulares.setChecked(false);
      }
    else
      mLocManager.removeUpdates(mLocListener);
  }

  LocationListener mLocListener = new LocationListener() {
    public void onLocationChanged(Location location) {
      mostrarLocalizacion(R.string.ej11_geoloc_actualiz_regular, location);
    }

    public void onProviderDisabled(String provider) {
      mostrar(String.format(getString(R.string.ej11_geoloc_dispositivo_off),
                            provider));
    }

    public void onProviderEnabled(String provider) {
      mostrar(String.format(getString(R.string.ej11_geoloc_dispositivo_on),
                            provider));
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
      String statusTxt = null;
      switch (status) {
      case LocationProvider.AVAILABLE:
        statusTxt = getString(R.string.ej11_geoloc_status_on);
        final Integer nb = (Integer) extras.get("satellites");
        if (nb != null)
          statusTxt +=
            String.format(getString(R.string.ej11_geoloc_status_satellites), nb);
        break;
      case LocationProvider.OUT_OF_SERVICE:
        statusTxt = getString(R.string.ej11_geoloc_status_off);
        break;
      case LocationProvider.TEMPORARILY_UNAVAILABLE:
        statusTxt = getString(R.string.ej11_geoloc_status_ko);
        break;
      default:
        break;
      }
      final String txt =
        String.format(getString(R.string.ej11_geoloc_status_changed), provider,
                      status);
      mostrar(txt);
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    if (mBotonActRegulares.isChecked())
      onClickButtonActualizRegular(null);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mLocManager.removeUpdates(mLocListener);
  }

  private void mostrarLocalizacion(int idInfos, Location loc) {
    final String txt =
      String.format(getString(R.string.ej11_geoloc_loc), getString(idInfos),
                    loc);
    mostrar(txt);
  }

  private void mostrar(String texto) {
    mInfos.append("\n" + texto);
    mInfos.setSelection(mInfos.getText()
      .length());
  }

}