package es.midominio.android.miaplicacion.ej12;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

import es.midominio.android.miaplicacion.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EscribirNFCActividad extends Activity{

	
	private static String TAG="WRITE_NFC";
	private NfcAdapter nfcAdapter;
	
	private String writtenMessageFormat = NfcAdapter.ACTION_TAG_DISCOVERED;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.ej12_escribir_tag_nfc);
		
		nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
	}
	
	
 	@Override
	public void onResume() {
		super.onResume();
		if(nfcAdapter!=null) {
			nfcAdapter.enableForegroundDispatch(this, getPendingIntent(), getIntentFilters(), getTechLists());
			Toast.makeText(getApplicationContext(), "Prise en charge NFC ok", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if(nfcAdapter!=null)
			nfcAdapter.disableForegroundDispatch(this);
	}
	
	
	@Override
	public void onNewIntent(Intent intent) {
 		writeTag(intent);
	}
	
	private PendingIntent getPendingIntent() {
		 return PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}
	
	
	private IntentFilter[] getIntentFilters() {
		if(writtenMessageFormat.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
			IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
			ndef.addDataScheme("http");
			return new IntentFilter[] {ndef, };
		}
		else if(writtenMessageFormat.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
			IntentFilter ntech = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
			return new IntentFilter[] {ntech,};
		}
		return null;
	}
	
	@SuppressLint("NewApi")
	private String[][] getTechLists() {
		return new String[][] { new String[] { android.nfc.tech.Ndef.class.getName(), android.nfc.tech.MifareUltralight.class.getName(), android.nfc.tech.NfcA.class.getName() } };
	}
	
	public byte[] buildTextPayload(String message, String codificacion, Locale idioma) {
		byte[] payload;
		
		int codeEncodage = codificacion.equals("UTF-8") ? 0 : 128;
	    Charset charsetEncodage = Charset.forName(codificacion);
		
		byte[] langByte = idioma.getLanguage().getBytes(Charset.forName("US-ASCII"));
		byte[] messageByte = message.getBytes(charsetEncodage);
		
		payload = new byte[1 + langByte.length + messageByte.length];
		payload[0] = (byte)(codeEncodage + langByte.length);
		System.arraycopy(langByte, 0, payload, 1, langByte.length);
		System.arraycopy(messageByte, 0, payload, 1 + langByte.length, messageByte.length);

		return payload;
	}
	
	public NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
	    byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
	    Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
	    byte[] textBytes = payload.getBytes(utfEncoding);
	    int utfBit = encodeInUtf8 ? 0 : (1 << 7);
	    char status = (char) (utfBit + langBytes.length);
	    byte[] data = new byte[1 + langBytes.length + textBytes.length];
	    data[0] = (byte) status;
	    System.arraycopy(langBytes, 0, data, 1, langBytes.length);
	    System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
	    NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
	    NdefRecord.RTD_TEXT, new byte[0], data);
	    return record;
	}
	
	public void writeTag(final Intent intent) {
		NdefRecord aRecord_TnfMimeMedia;
		NdefRecord aRecord_TnfWellKnown;
		Date now = new Date();
		byte [] payload = (String.valueOf(now.getHours())+":"+String.valueOf(now.getMinutes())+ getString(R.string.ej12_este_es_el_contenido_del_tag_nfc)).getBytes();
		
		aRecord_TnfMimeMedia = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), 
				new byte[]{},payload );	

		aRecord_TnfWellKnown = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, 
				new byte[0], payload);
			
		NdefRecord[] records = new NdefRecord[] { aRecord_TnfWellKnown};
		final NdefMessage message = new NdefMessage(records);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				write(message, intent);
				return null;
			}
		}.execute((Void)null);
			
	}
	
	private void showMessage(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public boolean write(NdefMessage rawMessage, Intent intent) {
         Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
         Ndef ndef = Ndef.get(tag);
         if (ndef!=null) {
        	 try {
				ndef.connect();
			} catch (IOException e) {
				showMessage("Error de conexión ndef");
				e.printStackTrace();
			}
        	 if(!ndef.isWritable()) {
        		 showMessage("El tag ndef es de solo lectura");
        		return false;
        	 }
        	 try {
				ndef.writeNdefMessage(rawMessage);
				showMessage("Se ha escrito el tag");
			} catch (IOException e) {
				showMessage("Error IOException en la escritura de ndef");					
				e.printStackTrace();
			} catch (FormatException e) {
				showMessage("Error de formato en la escritura de ndef");
				e.printStackTrace();
				return false;
			}
        	 finally {
        		 try {
					ndef.close();
				} catch (IOException e) {
 					e.printStackTrace();
				}
        	 }
        	 return true;
         }
         else { // El tag no está formateado
	         NdefFormatable format = NdefFormatable.get(tag);
	         if (format != null) {
	             try {
	                 format.connect();
	                 format.format(rawMessage);
	                 
	                 writeNdefSuccess();
	                 
	                 return true;
	             } catch (Exception e) {
	                 writeNdefFailed(e);
	             } finally {
			         try {
			        	 	format.close();
			         } catch (IOException e) { // ignorar 
			        	 }
	             }
	         }
	         return false ;
         }
	 }


	private void writeNdefFailed(Exception e) {
		showMessage(getString(R.string.ej12_error_durante_escritura_del_tag));
		Log.d(TAG,"Erreur ecriture tag :" + e.getMessage());
	}


	private void writeNdefSuccess() {
		showMessage(getString(R.string.ej12_un_tag_nfc_se_ha_escrito));
	}
	
}
