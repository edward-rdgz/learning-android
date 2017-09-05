package es.midominio.android.miaplicacion.ej04;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ListaPersonalizadaActividad extends ListActivity {

  private static final int NUM_ELTS = 200;

  private class Element {
    int idDrawable;
    String titulo;
    float note;
    String msg;

    public Element(int n) {
      idDrawable = (n % 2 == 0) ? R.drawable.icono : R.drawable.ej13_appwidget;
      titulo = "Titre_" + n;
      note = n * 5.f / NUM_ELTS;
      msg = "Message_" + n;
    }
  }

  private Element[] mElts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mElts = new Element[NUM_ELTS];
    for (int i = 0; i < mElts.length; i++)
      mElts[i] = new Element(i);

    final AdaptadorPersonalizado adaptateur = new AdaptadorPersonalizado();

    setListAdapter(adaptateur);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Toast.makeText(this, mElts[position].titulo, Toast.LENGTH_SHORT)
      .show();
  }

  private class AdaptadorPersonalizado extends BaseAdapter {

    private class ViewHolder {
      View vue;
      ImageView img;
      TextView titulo;
      RatingBar note;
      TextView msg;
    }

    private final LayoutInflater mInflater;

    public AdaptadorPersonalizado() {
      mInflater = LayoutInflater.from(ListaPersonalizadaActividad.this);
    }

    @Override
    public int getCount() {
      return mElts.length;
    }

    @Override
    public Object getItem(int position) {
      return position;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

      if (convertView != null)
        holder = (ViewHolder) convertView.getTag();
      else {
        convertView = mInflater.inflate(R.layout.ej04_lista_personalizada_linea, null);

        holder = new ViewHolder();
        holder.vue = convertView.findViewById(R.id.ej04_lista_personalizada_linea);
        holder.img =
          (ImageView) convertView.findViewById(R.id.ej04_lista_personalizada_linea_img);
        holder.titulo =
          (TextView) convertView.findViewById(R.id.ej04_lista_personalizada_linea_titulo);
        holder.note =
          (RatingBar) convertView.findViewById(R.id.ej04_lista_personalizada_linea_nota);
        holder.msg =
          (TextView) convertView.findViewById(R.id.ej04_lista_personalizada_linea_msg);

        convertView.setTag(holder);
      }

      holder.vue.setBackgroundColor((position % 2 == 0) ? Color.DKGRAY
        : Color.GRAY);
      holder.img.setImageResource(mElts[position].idDrawable);
      holder.titulo.setText(mElts[position].titulo);
      holder.note.setRating(mElts[position].note);
      holder.msg.setText(mElts[position].msg);

      return convertView;
    }
  }

}
