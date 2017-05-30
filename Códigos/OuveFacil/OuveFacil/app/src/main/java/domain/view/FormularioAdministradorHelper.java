package domain.view;

import android.widget.EditText;

import com.mm.ouvefacil.R;

import domain.model.Administrador;

/**
 * Created by Matheus on 16/05/2017.
 */
public class FormularioAdministradorHelper {

    private EditText editNome;
    private EditText editLogin;
    private EditText editSenha;
    private EditText editCpfCnpj;

    public FormularioAdministradorHelper(FormularioAdministrador formularioAdministrador) {
        editNome = (EditText) formularioAdministrador.findViewById(R.id.editTextNome);
        editLogin = (EditText) formularioAdministrador.findViewById(R.id.editTextLogin);
        editSenha = (EditText) formularioAdministrador.findViewById(R.id.editTextSenha);
        editCpfCnpj = (EditText) formularioAdministrador.findViewById(R.id.editTextCpfCnpj);

    }

    public Administrador pegaAdministradorDoFormulario() {

        Administrador administrador = new Administrador();

        administrador.setNome(editNome.getText().toString());
        administrador.setLogin(editLogin.getText().toString());
        administrador.setSenha(editSenha.getText().toString());
        administrador.setCpfCnpj(editCpfCnpj.getText().toString());

        return administrador;
    }
}
