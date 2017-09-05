package es.midominio.android.miaplicacion.ej06;


import java.util.ArrayList;
import java.util.List;

import es.midominio.android.miaplicacion.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NavigationDrawerActividad extends ActionBarActivity{

	DrawerLayout drawerLayout;
	ActionBarDrawerToggle actionBarDrawerToggle ;
	ListView list;
	ArrayList<DrawerItem> drawerItems;
	TextView content;
	
 	String textoFicticioInicio ;//=getString(R.string.ej06_accueil);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej06_navigationdrawer);
		
		drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.ej06_abierto, R.string.ej06_cerrado) {
			@Override
			public void onDrawerOpened(View arg0) {
				getSupportActionBar().setTitle(R.string.ej06_menu);
				Toast.makeText(NavigationDrawerActividad.this, getString(R.string.ej06_abierto), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onDrawerClosed(View arg0) {
				getSupportActionBar().setTitle(R.string.ej06_contenido);
				Toast.makeText(NavigationDrawerActividad.this, getString(R.string.ej06_cerrado), Toast.LENGTH_SHORT).show();
			}
		};
		 
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		fillDrawerItems();
		
		list = (ListView)findViewById(R.id.ej06_drawer_content);
		DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.draweritem, drawerItems);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DrawerItem current = (DrawerItem)arg1.getTag();
				if(current==null)
					return;
				getSupportActionBar().setTitle(current.name);
				drawerLayout.closeDrawer(Gravity.START);
				content.setText(Html.fromHtml(current.description));
			}
		});
		
		content = (TextView)findViewById(R.id.ej06_content);
		content.setText(R.string.ej06_inicio);
		getSupportActionBar().setTitle(R.string.ej06_navegacion);
		
	}
	
	private void fillDrawerItems() {
		drawerItems = new ArrayList<DrawerItem>();
		drawerItems.add(null);
		drawerItems.add(new DrawerItem("Novedades", R.drawable.novedades,"<big>Novedades</big>") );
		drawerItems.add(new DrawerItem("Colabore como autor", R.drawable.autores,"<big>Colabore como autor</big>") );
		drawerItems.add(new DrawerItem("Nuestras colecciones", R.drawable.collections,"<big>Nuestras colecciones</big>") );
		drawerItems.add(new DrawerItem("Mejores ventas", R.drawable.mejoresventas,"<big>Mejores ventas</big>") );
		drawerItems.add(new DrawerItem("Recursos", R.drawable.ressources,"<big>Recursos</big>") );
		drawerItems.add(new DrawerItem("Temáticas", R.drawable.firmas,"<big>Temáticas</big>") );
		drawerItems.add(new DrawerItem("Próximamente", R.drawable.salidas,"<big>Próximamente</big>") );
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
   @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
	
   public class DrawerItem {

		public String name;
		public int icon;
		public String description;
		
		public DrawerItem(String _name, int autores, String description) {
			this.name = _name;
			this.icon = autores;
			this.description = description;
					
		}
		
	}
   
   public class DrawerItemAdapter extends ArrayAdapter<DrawerItem>{

		private Context currentContext;
		public DrawerItemAdapter(Context context, int textViewResourceId, List<DrawerItem> items) {
			super(context, textViewResourceId, items);
			currentContext = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final Activity currentActivity =(Activity) getContext(); 
			LayoutInflater vi =currentActivity.getLayoutInflater();
			
			View row=vi.inflate(R.layout.draweritem, parent, false);
			
			DrawerItem current = getItem(position);
			row.setTag(current);
			
			if(current==null)
				return row;
			
			TextView name=(TextView)row.findViewById(R.id.text);
			ImageView icon=(ImageView)row.findViewById(R.id.icon);

			name.setText(current.name);
			icon.setImageDrawable(currentContext.getResources().getDrawable(current.icon));
			return row;
		}
		
		
	}
}
