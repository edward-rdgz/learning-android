package es.midominio.android.miaplicacion.ej06;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import es.midominio.android.miaplicacion.R;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MiCustomAutoComplete extends AutoCompleteTextView {

	public MiCustomAutoComplete(Context context) {
		super(context);
	}
	
	public MiCustomAutoComplete(Context context, AttributeSet attr) {
		super(context, attr);

 		String atributo_1 = null;

 		// Recuperación del valor del atributo miAtributoCustom_1 a partir del xml de layout
 		// El atributo contiene el método que debe invocarse para obtener la lista de palabras de autocompletado
		TypedArray customAttributes = context.obtainStyledAttributes(attr, R.styleable.misAtributosCustoms);
		if(customAttributes!=null) {
			atributo_1 = customAttributes.getString(R.styleable.misAtributosCustoms_miAtributoCustom_1);

			Method m=null;
			String[] datas =null;
		 
			try {
				DataSource ds = new DataSource();
				m = DataSource.class.getMethod(atributo_1, new Class[] {Context.class, String.class, Integer.TYPE});
				datas = (String[]) m.invoke(ds, new Object[]{context,"someData",3});
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		
			if(datas==null)
				return;
		
				this.setAdapter(new ArrayAdapter<String>(context,android.R.layout.select_dialog_item, datas));
		}
		customAttributes.recycle();
 	}
}
