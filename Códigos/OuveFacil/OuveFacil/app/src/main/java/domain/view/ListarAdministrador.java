package domain.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mm.ouvefacil.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import domain.model.Administrador;

public class ListarAdministrador extends AppCompatActivity {

    /*

    private ListView listView1;
    private static final int REQUEST_CODE = 1;
    private ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

    private static final int ADD_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;
    private static final int DELETE_ID = Menu.FIRST + 3;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_administrador);

        try {
            getDataPhone();
        } catch (Exception e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
    }

    private class requestTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;
        private Context applicationContext;
        private String actionflag;

        @Override
        protected void onPreExecute(){
            this.dialog = ProgressDialog.show(applicationContext, "Load Data From Server Process", "Please Wait...", true);
        }

        @Override
        protected String doInBackground (String... urls){
            String response = "";
            response = getDataServer(urls[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String result){
            this.dialog.cancel();
            if(actionflag.equals("list")){
                if(result.equals("l")){
                    setListData();
                }
            } else if(actionflag.equals("delete")){
                getDataPhone();
            }
        }
    }

    public void getDataPhone(){
        mylist.clear();
        requestTask task = new requestTask();
        task.applicationContext = this;
        task.actionflag = "list";
        String url = "http://192.168.1.105/OuveFacil/listarAdministrador.php";
        task.execute(new String[]{ url });
    }

    public String getDataServer(String url){
        String sret = "1";

        JSONObject json = JSONfunctions.getJSONfromURL(url);

        try{
            if(json.getString("errocode").equals("0")){
                Toast.makeText(getBaseContext(), json.getString("errormsg"), Toast.LENGTH_SHORT).show();

                sret = "0";
            }

            JSONArray makanan = json.getJSONArray("data");

            for (int i = 0; i < makanan.length(); i++){
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = makanan.getJSONObject(i);

                map.put("id", jsonobj.getString("id"));
                map.put("phone_name", jsonobj.getString("phone_name"));
                map.put("price", jsonobj.getString("price"));

                mylist.add(map);

            }
        } catch (JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return sret;
    }

    private void showToast(String msg, int flag){
        Toast.makeText(getBaseContext(), "Result" + msg, Toast.LENGTH_SHORT).show();
    }

    private void setListData(){
        listView1 = (ListView) findViewById(R.id.listViewAdministrador);

        ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.row, new String[] { "phone_name, price"}, new int[]{R.id.phonename, R.id.price});
        listView1.setAdapter(adapter);

        registerForContextMenu(listView1);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(Menu.NONE, ADD_ID, Menu.NONE, "Add").setIcon(R.drawable.ic_launcher).setAlphabeticShortcut('a');
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Edit").setIcon(R.drawable.ic_launcher).setAlphabeticShortcut('d');
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete").setIcon(R.drawable.ic_launcher).setAlphabeticShortcut('e');
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int idphone = (int) info.id;
        HashMap<String, String> o = (HashMap<String, String>) listView1.getItemAtPosition(idphone);

        switch (item.getItemId()){
            case ADD_ID:
                addData(o, "add");
                return true;
            case DELETE_ID:
                deleteData(o);
                return true;
            case EDIT_ID:
                editData(o, "edit");
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    private void editData(HashMap<String, String> o, String action){
        Log.d("*****editData*****", "id:" + o.get("id"));
        callIntent(o, action);
    }

    private void deleteData(HashMap<String, String> o){
        requestTask task = new requestTask();
        task.applicationContext = ListDataPhoneActivity.this;
        task.actionflag = "delete";

        String url = "http://192.168.1.105/OuveFacil/apagarAdministrador.php" + o.get("id");
        task.execute(new String[] { url });
    }

    private void addData (HashMap<String, String> o, String action){
        callIntent(o, action);
    }

    private void callIntent(HashMap<String, String> o, String action){
        Intent i = new Intent(this, AddPhoneAcitivity.class);

        i.putExtra("action", action);
        i.putExtra("dataphone", o);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("refreshflag")){
                if(data.getExtras().getString("refreshflag").equals("1")){
                    getDataPhone();
                }
            }
        }
    }

*/





// Ainda não consigo listar do meu banco de dados para a minha aplicação, e consequentemente não consigo alterar nem remover!

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
    }


    public void pegarDados(View view){

        new Thread(){
            public void run(){
                EditText editNome = (EditText) findViewById(R.id.editTextNome);
                EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
                EditText editSenha = (EditText) findViewById(R.id.editTextSenha);
                EditText editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);

                postHttp(editNome.getText().toString(), editLogin.getText().toString(), editSenha.getText().toString(), editCpfCnpj.getText().toString());
            }
        }.start();

        finish();

    }

    public void postHttp(String nome, String login, String senha, String cpfCnpj){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/listarAdministrador.php");

        Administrador administrador = new Administrador();



        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("login", login));
            valores.add(new BasicNameValuePair("senha", senha));
            valores.add(new BasicNameValuePair("cpfCnpj", cpfCnpj));

            ListView listView;

            listView = (ListView) findViewById(R.id.listViewAdministrador);

            //ArrayList<Administrador> arrayAdministrador = new ArrayList<>();

            ArrayAdapter<NameValuePair> arrayAdapter = new ArrayAdapter<NameValuePair>(this, android.R.layout.simple_list_item_checked, valores);

            listView.setAdapter(arrayAdapter);

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT);
                    } catch(ParseException e){
                        e.printStackTrace();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch(ClientProtocolException e){

        } catch(IOException e){

        }
    }


}
