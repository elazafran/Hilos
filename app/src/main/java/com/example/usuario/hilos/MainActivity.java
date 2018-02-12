package com.example.usuario.hilos;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * es para calcular el factorial y hacer con un bucle en vez de recursividad
 *
 * esto para resolver de manera asincrona no nos sirve
 *
 */
public class MainActivity extends Activity {
    private EditText entrada,tvContador;
    private TextView salida,tvSalida;
    private Button btnPararContador,btnIniciarContador;
    private static boolean stop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //se define el diesño de la interfaz
        entrada = (EditText) findViewById(R.id.entrada);
        //salida = (TextView) findViewById(R.id.salida);
        tvSalida= (TextView) findViewById(R.id.tvSalida);
        btnIniciarContador= (Button) findViewById(R.id.btnIniciarContador);
        btnPararContador= (Button) findViewById(R.id.btnPararContador);
        btnIniciarContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContadorTarea contador = new ContadorTarea();
                contador.execute();
                Toast.makeText(MainActivity.this, "hola inicimaos", Toast.LENGTH_SHORT).show();
            }
        });

        btnPararContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.stop = true;
                Toast.makeText(MainActivity.this, "hola paramos el contador", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     *
     * @param view
     */
    /*public void calcularOperacion(View view) {
        //obitne un entero de la cajita de entrasda
        int n = Integer.parseInt(entrada.getText().toString());
        //añade al objeto de salida
        salida.append(n +"! = ");
        //aqui llamamos al facotiral
        int res = factorial(n);
        //el resultado se lo añaade a la salida
        salida.append(res +"\n");
    }*/

    public void calcularOperacion(View view) {

        int n = Integer.parseInt(entrada.getText().toString());

        salida.append(n + "! = ");

        MiThread thread = new MiThread(n);
        //lanza la ejecutción y ahce que pase por el metdodo run
        //thread.start();

//        MiTarea tarea = new MiTarea();
//        tarea.execute(n );






    }

    /**
     * calculamos el factroial
     * @param n
     * @return
     */
    public int factorial(int n) {
        int res=1;
        for (int i=1; i<=n; i++){
            res*=i;
            // utilizamos el metod statactico de la clase systemclock y le dice que cada vuelta un retraso
            // a medida que vayamos metiendo valores grandes se ira retrasando
            // esto es para que android lo intente echar
            //SystemClock.sleep(1000);
        }

        return res;

    }


    public class MiThread extends Thread{
        private int n, res;

        public MiThread(int n){
            this.n = n;

        }
        @Override
        public void run() {
            res = factorial(n);
            // salida.append(res +"\n");

            //ejecutalo en hilo la interfaz de usuario es lo uqe le decimos
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    salida.append(res + "\n");
                }
            });
        }



    }


    public class MiTarea extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] n) {

            return factorial(Integer.valueOf(n[0].toString()));

        }
        @Override
        protected void onPostExecute(Object res) {

            salida.append(res.toString() + "\n");

        }


    }

    public class ContadorTarea extends AsyncTask {

        Integer count=0 ;

        @Override
        protected Object doInBackground(Object[] n) {

            while(!stop){

                count++;
                publishProgress(new Integer[]{count});
            }

            return count++;

        }

        @Override
        protected void onProgressUpdate(Object[] prog) {
            tvSalida.setText(prog[0].toString());

        }
        @Override
        protected void onPostExecute(Object res) {
            Toast.makeText(MainActivity.this, "se para el contador", Toast.LENGTH_SHORT).show();


        }



    }



}
