package es.midominio.android.miaplicacion.ej08;

import es.midominio.android.miaplicacion.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;


public class RedesSocialesActividad extends Activity{

	
	Button share;
	
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.ej08_redes_sociales);
		share = (Button)findViewById(R.id.ej08_share);
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			    startActivity(Intent.createChooser(createShareIntent(), "Compartir en..."));				
			}
		});
	}
	
	ShareActionProvider mShareActionProvider;
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.ej08_compartir, menu);
	    MenuItem item = menu.findItem(R.id.menu_item_share);

	    ShareActionProvider mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    mShareActionProvider.setShareIntent(createShareIntent());
 	    return true;
	}

	private Intent createShareIntent() {
		Intent shareIntent=new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Ejemplo de compartir");
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Texto del mensaje compartido");
		return shareIntent;
	}
	
}
