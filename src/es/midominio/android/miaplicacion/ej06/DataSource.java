package es.midominio.android.miaplicacion.ej06;

import android.content.Context;

public class DataSource {

	
	public static String[] getCifras(Context context, String aParameter, int anotherOne)
	{
		/*
		 .. normalmente, aqu�, encontramos
		 ... c�digo que utiliza el contexto, as� como los dos par�metros.
		 */
		return new String[]{"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};
	}
	
	public static String[] getCifras()
	{
		return new String[]{"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};
	}
	
}
