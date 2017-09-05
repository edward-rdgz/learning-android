package es.midominio.android.miaplicacion;

import es.midominio.android.miaplicacion.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MiActividadPrincipal extends ListActivity {

  private static final String RUTA = "%1$s.ex%2$02d.%3$s";

  private static final String[][] ACTIVIDADES_POR_CAPITULO =
    {
    // Ejemplo 01 - Descubrir la interfaz de usuario
      { "MedidasPantallaActividad", "ModoDeclarativoActividad",
        "ModoProgramaticoActividad", "TextViewActividad", "EditTextActividad",
        "Boton1Actividad", "Boton2Actividad", "OtrosWidgetsActividad" },
      // Ejemplo 02 - Fundamentos
      { "IntencionExplicitaActividad", "IntencionImplicitaActividad",
        "VerticalActividad", "ApaisadoActividad", "CicloDeVidaActividad",
        "ResultadoActividad", "RestauracionActividad", "PilaAActividad" },
      // Ejemplo 03 - Completar la interfaz de usuario
      { "EstilosTemasActividad", "MenuActividad", "MenuContextualActividad",
        "ToastActividad", "DialogoActividad", "NotificacionActividad" },
      // Ejemplo 04 - Componentes principales de la aplicación
      { "ServicioActividad", "ReceptorEventosActividad",
        "ListaSimpleActividad", "ListaPersonalizadaActividad" },
      // Ejemplo 05
      { "PreferenciasActividad", "ArchivosActividad", "BDDActividad",
        "ProveedorDeContenidosActividad"},
        // Ejemplo 06 - Construir interfaces complejas
        {"ComponentePersonalizadoActividad","NavigationDrawerActividad",
        	"ImagenesRedimensionablesActividad"},
        //
      // Ejemplo 07 - Concurrencia, seguridad y red
      { "ProcesoActividad", "AsyncTaskActividad", "ThreadActividad",
        "HTTPActividad", "ANRActividad" },
        // Ejemplo 08 - Redes sociales
        {"RedesSocialesActividad"},
      // Ejemplo 09 - Trazas, depuración y pruebas
      { "RegistroEventosActividad", "PruebasUnitariasActividad",
        "PruebasFuncionalesActividad", "PruebaDelMonoActividad" },
      // Ejemplo 10 - Publicar una aplicación
      { "PublicarActividad" },
      // Ejemplo 11 - Mapas y localización geográfica
      { "GeolocActividad", "MapaActividad" },
      // Ejemplo 12 - La tecnología NFC
      {"DescubrirNFCActividad"},
      // Ejemplo 13 - Funcionalidades avanzadas
      { "AppWidgetActividad", "LicenciaActividad" } };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final String[] capitulos = getResources().getStringArray(R.array.capitulos);

    setListAdapter(new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, capitulos));
  }

  @Override
  protected void onListItemClick(ListView l, View v, final int posicion, long id) {
    final int nChap = posicion + 1;

    if (ACTIVIDADES_POR_CAPITULO[posicion].length ==1) {
      iniciaActividad(nChap, posicion, 0);
      return;
    }

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setItems(ACTIVIDADES_POR_CAPITULO[posicion],
                     new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int item) {
                         iniciaActividad(nChap, posicion, item);
                       }
                     });
    builder.create()
      .show();
  }

  private void iniciaActividad(final int nChap, final int posicion, final int elt) {
    try {
      final String ruta =
        String.format(RUTA, getPackageName(), nChap,
                      ACTIVIDADES_POR_CAPITULO[posicion][elt]);
      final Intent intent =
        new Intent(MiActividadPrincipal.this, Class.forName(ruta));
      startActivity(intent);
    } catch (final ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}