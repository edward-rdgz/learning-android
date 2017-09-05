package es.midominio.android.miaplicacion.ej12;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import es.midominio.android.miaplicacion.R;

 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LeerNFCActividad extends Activity{
	static String TAG="NFC_READER";
	NfcAdapter nfcAdapter;
	TextView content;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.ej12_leer_tag_nfc);
		content = (TextView)findViewById(R.id.ej12_leer_tag_content);
		nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
	 
		if(nfcAdapter==null)
			Log.d(TAG,"Su dispositivo no dispone de la tecnología NFC");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(nfcAdapter==null)
			return;
		nfcAdapter.enableForegroundDispatch(this, getPendingIntent(), getIntentFilters(), getTechLists());
	}
	
	private PendingIntent getPendingIntent() {
		return  PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

	}
	
	private String[][] getTechLists() {
		return new String[][] { new String[] { android.nfc.tech.Ndef.class.getName(), android.nfc.tech.IsoDep.class.getName(), android.nfc.tech.Ndef.class.getName() } };
		//return new String[][] { new String[] { android.nfc.tech.Ndef.class.getName() } };
	}



	private IntentFilter[] getIntentFilters() {
		IntentFilter ntech = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		return new IntentFilter[] {ntech};
	}
	@Override
	public void onPause() {
		super.onPause();
		if(nfcAdapter==null)
			return;
		nfcAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	public void onNewIntent(Intent intent) {
	  readTag(intent);
	}
	
	private void showMessage(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				content.setText(content.getText() + "\n" + message);
			}
		});
	}
	@SuppressLint("NewApi")
	private void readTag(Intent intent) {
		if(intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED) || intent.getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED)) {
			Parcelable[] parcelableNedfMessages = 
					intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if(parcelableNedfMessages==null)
				return;
			NdefMessage[] messages = new NdefMessage[parcelableNedfMessages.length];
			for(int i =0;i<parcelableNedfMessages.length;i++) 
				messages[i] = (NdefMessage)parcelableNedfMessages[i];
			
			for(NdefMessage ndefMessage: messages) {
				NdefRecord[] records = ndefMessage.getRecords();
				for(NdefRecord record : records) {
					try {
						if(record.getTnf()==NdefRecord.TNF_ABSOLUTE_URI)
							showMessage("Tag de tipo TNF_ABSOLUTE_URI" );
						if(record.getTnf()==NdefRecord.TNF_EMPTY)
							showMessage("Tag de tipo TNF_EMPTY" );
						if(record.getTnf()==NdefRecord.TNF_EXTERNAL_TYPE)
							showMessage("Tag de tipo TNF_EXTERNAL_TYPE" );
						if(record.getTnf()==NdefRecord.TNF_MIME_MEDIA)
							showMessage("Tag de tipo TNF_MIME_MEDIA" );
						if(record.getTnf()==NdefRecord.TNF_UNCHANGED)
							showMessage("Tag de tipo TNF_UNCHANGED" );
						if(record.getTnf()==NdefRecord.TNF_UNKNOWN)
							showMessage("Tag de tipo TNF_UNKNOWN" );
						if(record.getTnf()==NdefRecord.TNF_WELL_KNOWN) {
							showMessage("Tag de tipo TNF_WELL_KNOWN" );

							if(Arrays.equals(record.getType(),NdefRecord.RTD_ALTERNATIVE_CARRIER))
								showMessage("getType de RTD_ALTERNATIVE_CARRIER" );
							
							if(Arrays.equals(record.getType(),NdefRecord.RTD_HANDOVER_CARRIER))
								showMessage("getType de RTD_HANDOVER_CARRIER" );
							
							if(Arrays.equals(record.getType(),NdefRecord.RTD_HANDOVER_REQUEST))
								showMessage("getType de RTD_HANDOVER_REQUEST" );
							
							if(Arrays.equals(record.getType(),NdefRecord.RTD_HANDOVER_SELECT))
								showMessage("getType de RTD_HANDOVER_SELECT" );
							
							if(Arrays.equals(record.getType(),NdefRecord.RTD_SMART_POSTER))
								showMessage("getType de RTD_SMART_POSTER" );
							
							if(Arrays.equals(record.getType(),NdefRecord.RTD_TEXT)) {
								showMessage("getType de RTD_TEXT" );
							
							}
							if(Arrays.equals(record.getType(),NdefRecord.RTD_URI)) {
								showMessage("getType de RTD_URI" );
							}
						}
						
						showMessage(new String( record.getPayload(), "UTF-8"));
						Log.d(TAG,"lectura:" + new String(record.getPayload(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						showMessage("Error en la lectura del payload");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	private Uri readUri(byte[] payload) {
		String[] protocol = new String[] {
				"http://www.",
				"https://www.",
				"http://",
				"https://",
				"tel:",
				"mailto:",
				"ftp://anonymous:anonymous@",
				"ftp://ftp.",
				"ftps://"
				};
		if(payload.length<2)
			return null;
	    int prefijoIndex = (payload[0] & (byte)0xFF);
        
	    if (prefijoIndex < 0 || prefijoIndex >= protocol.length) 
            return null;
        
        String prefijo = protocol[prefijoIndex];
        String sufijo = null;
		try {
			sufijo = new String(Arrays.copyOfRange(payload, 1, payload.length),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return Uri.parse(prefijo + sufijo); 
	}
	
	
	private String readText(byte[] payload) throws UnsupportedEncodingException {
		
		String formatoCodificacion = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int longitudCodigoIdioma = payload[0] & 0077;
        String codigoIdioma = new String(payload, 1, longitudCodigoIdioma, "US-ASCII");
		return new String(payload, longitudCodigoIdioma + 1, payload.length - longitudCodigoIdioma - 1, formatoCodificacion);
	}
}
