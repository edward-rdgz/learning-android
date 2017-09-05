package es.midominio.android.miaplicacion.ej07;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class AsyncTaskActividad extends Activity {

  private NumerosPrimosTarea mTarea;

  private ProgressBar mPb;
  private TextView mProgession;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ej07_asynctask);

    mPb = (ProgressBar) findViewById(R.id.ej07_progressbar);
    mProgession = (TextView) findViewById(R.id.ej07_progress);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mTarea = new NumerosPrimosTarea();
    mTarea.execute(0, 10000000);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mTarea.cancel(true);
  }

  private class NumerosPrimosTarea extends
    AsyncTask<Integer, Integer, Integer> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      Toast.makeText(AsyncTaskActividad.this, R.string.ej07_calculo_iniciado,
                     Toast.LENGTH_SHORT)
        .show();
    }

    @Override
    protected Integer doInBackground(Integer... arg0) {
      int n = 0;
      int niveau = 0;
      final int step = (arg0[1] - arg0[0]) / 100;
      for (int i = arg0[0]; i <= arg0[1]; i++) {
        if (isCancelled()) {
          onCancelled(n);
          return n;
        }
        if (isPrime(i))
          n++;
        if ((i > arg0[0]) && (i % step == 0))
          publishProgress(++niveau);
      }

      return n;
    }

    private void onCancelled(final int nb) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(AsyncTaskActividad.this,
                         getString(R.string.ej07_calculo_anulado, nb),
                         Toast.LENGTH_LONG)
            .show();
        }
      });
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      mPb.setProgress(values[0]);
      mProgession.setText(getString(R.string.ej07_calculo_progreso, values[0]));
    }

    @Override
    protected void onPostExecute(Integer result) {
      super.onPostExecute(result);

      if (isCancelled())
        return;

      Toast.makeText(AsyncTaskActividad.this,
                     getString(R.string.ej07_calculo_resultados, result),
                     Toast.LENGTH_LONG)
        .show();
    }

    private boolean isPrime(int n) {
      final double raiz = Math.sqrt(n);
      int i = 2;

      while ((i <= raiz) && (n % i != 0))
        i++;

      return (i > raiz);
    }
  }
}