package es.midominio.android.miaplicacion.ej12;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class DescubrirNFCActividad extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej12_principal_nfc);
		Button lireTag = (Button)findViewById(R.id.ej12_leer_tag);
		lireTag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DescubrirNFCActividad.this,LeerNFCActividad.class));
			}
		});
		Button ecrireTag = (Button)findViewById(R.id.ej12_escribir_tag);
		ecrireTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DescubrirNFCActividad.this,EscribirNFCActividad.class));
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		hasNFC();
	}
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void hasNFC() {
		NfcAdapter nfcAdapter= NfcAdapter.getDefaultAdapter(getApplicationContext());
		if(nfcAdapter ==null) {
			Toast.makeText(this, "Tecnología NFC no disponible", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, "Bienvenido al mundo de NFC", Toast.LENGTH_SHORT).show();
		if(!nfcAdapter.isEnabled()) {
			Intent intent;
			 if (android.os.Build.VERSION.SDK_INT >= 10) 
			       intent = new Intent("android.settings.NFC_SETTINGS");
			     else 
			        intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        this.startActivity(intent);

		}
		
	}
	
}
