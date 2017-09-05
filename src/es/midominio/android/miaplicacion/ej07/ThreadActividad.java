package es.midominio.android.miaplicacion.ej07;

import java.math.BigInteger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;
import es.midominio.android.miaplicacion.R;

public class ThreadActividad extends Activity {

  private static final int MSG_INICIO = 1;
  private static final int MSG_FIN = 2;
  private static final int DELAY_MILIS = 5000;

  private NumerosPrimosTarea mTarea;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.ej07_thread);

    mTarea = new NumerosPrimosTarea();
  }

  private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(android.os.Message msg) {
      switch (msg.what) {
      case MSG_INICIO:
        setProgressBarIndeterminateVisibility(true);
        Toast.makeText(ThreadActividad.this,
                       getString(R.string.ej07_calculo_iniciado),
                       Toast.LENGTH_SHORT)
          .show();
        break;
      case MSG_FIN:
        setProgressBarIndeterminateVisibility(false);
        Toast.makeText(ThreadActividad.this,
                       getString(R.string.ej07_calculo_resultados, msg.arg1),
                       Toast.LENGTH_SHORT)
          .show();
        break;
      default:
        break;
      }
    };
  };

  final Runnable mInfo = new Runnable() {
    @Override
    public void run() {
      Toast.makeText(ThreadActividad.this,
                     getString(R.string.ej07_thread_5segundos),
                     Toast.LENGTH_SHORT)
        .show();
      mHandler.postDelayed(mInfo, DELAY_MILIS);
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    mTarea.start();
    mHandler.postDelayed(mInfo, DELAY_MILIS);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mTarea.cancel();
    mHandler.removeCallbacks(mInfo);
  }

  private class NumerosPrimosTarea extends Thread {

    @Override
    public void run() {
      mHandler.sendEmptyMessage(MSG_INICIO);

      int n = 0;
      BigInteger nb = BigInteger.ONE;
      while (!isInterrupted()) {
        nb = nb.nextProbablePrime();
        n++;
      }

      final Message msg = mHandler.obtainMessage(MSG_FIN, n, 0);
      mHandler.sendMessage(msg);
    }

    public void cancel() {
      interrupt();
      muestra(getString(R.string.ej07_thread_stop));
    }

    private void muestra(final String message) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(ThreadActividad.this, message, Toast.LENGTH_SHORT)
            .show();
        }
      });
    }
  }
}