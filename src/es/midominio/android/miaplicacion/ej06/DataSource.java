package es.midominio.android.miaplicacion.ej06;

import android.content.Context;

public class DataSource {

	
	public static String[] getCifras(Context context, String aParameter, int anotherOne)
	{
		/*
		 .. normalmente, aquí, encontramos
		 ... código que utiliza el contexto, así como los dos parámetros.
		 */
		return new String[]{"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};
	}
	
	public static String[] getCifras()
	{
		return new String[]{"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};
	}
	
}
