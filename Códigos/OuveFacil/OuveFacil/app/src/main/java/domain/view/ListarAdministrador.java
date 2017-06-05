package domain.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mm.ouvefacil.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Administrador;

public class ListarAdministrador extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private EditText editLogin;
    private EditText editSenha;
    private EditText editCpfCnpj;
    private String nome;
    private String login;
    private String senha;
    private String cpfCnpj;
    private ArrayList<String> param = new ArrayList<String>();
    private Administrador administrador = new Administrador();

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_administrador);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        //Toast.makeText(this, "Teste de Mensagem", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewAdministrador);

        editNome = (EditText) findViewById(R.id.editTextNome);
        editLogin = (EditText) findViewById(R.id.editTextLogin);
        editSenha = (EditText) findViewById(R.id.editTextSenha);
        editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);

       /* editNome.setText(getIntent().getExtras().getString("nome"));
        editLogin.setText(getIntent().getExtras().getString("login"));
        editSenha.setText(getIntent().getExtras().getString("senha"));
        editCpfCnpj.setText(getIntent().getExtras().getString("cpfCnpj"));
*/

        Task task = new Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                        /*Intent intent = new Intent(getApplicationContext(), ClipData.Item.class);
                        String codAdministrador = adapter.getItem(position);
                        intent.putExtra("codAdministrador", codAdministrador);
                        startActivity(intent);*/

                //administrador = (Administrador) listView.getItemAtPosition(position);
                //administrador = adapter.getItemAtPosition(position);
                Toast.makeText(ListarAdministrador.this, "Item selecionado "+ listView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                editNome.setText(administrador.getNome());
                editLogin.setText(administrador.getLogin());
                editSenha.setText(administrador.getSenha());
                editCpfCnpj.setText(administrador.getCpfCnpj());

            }
        });


    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarAdministrador.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = "http://192.168.1.105/OuveFacil/listarAdministrador.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarAdministrador.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = br.readLine()) != null){
                    sb.append(line+"\n");
                }
                is.close();
                result = sb.toString();

            }catch(Exception e){
                Log.e("log_tag", "Erro ao converter o resultado " + e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void v){

            try {
                JSONArray Jarray = new JSONArray(result);



                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = Jarray.getJSONObject(i);

                    // output na tela
                    //String codAdministrador = jsonObject.getString("codAdministrador");
                    nome = jsonObject.getString("nome");
                    login = jsonObject.getString("login");
                    senha = jsonObject.getString("senha");
                    cpfCnpj = jsonObject.getString("cpfCnpj");


                    administrador.setNome(nome);
                    administrador.setLogin(login);
                    administrador.setSenha(senha);
                    administrador.setCpfCnpj(cpfCnpj);

                    param.add("Nome: "+administrador.getNome()+"\nCPF/CNPJ: "+administrador.getCpfCnpj());
                    //param.add(new BasicNameValuePair("login", login));
                    //param.add(new BasicNameValuePair("senha", senha));
                    //param.add(cpfCnpj);




                    ArrayAdapter<String> ad = new ArrayAdapter<String>(ListarAdministrador.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }
}
