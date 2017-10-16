package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.a3sir.protechsolutions.am_prototype.Models.Transacao;
import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.TransacaoDAO;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

public class ResgatarInvestimentosActivity extends AppCompatActivity {

    private TextView txtSaldoResInv;
    protected TextView txtSaldoPoupancaResInv;
    protected TextView txtSaldoTesouroResInv;
    protected TextView txtSaldoCdbResInv;

    private Button btnPoupancaResInv;
    private Button btnTesouroResInv;
    private Button btnCdbResInv;



    private Usuario usuario;
    private UsuarioDAO usuarioDao;
    private TransacaoDAO transacaoDao;

    private String origem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investir);

        txtSaldoResInv = (TextView) findViewById(R.id.txtSaldoResInv);
        txtSaldoPoupancaResInv = (TextView) findViewById(R.id.txtSaldoResInv);
        txtSaldoTesouroResInv = (TextView) findViewById(R.id.txtSaldoResInv);
        txtSaldoCdbResInv = (TextView) findViewById(R.id.txtSaldoCdbResInv);

        btnPoupancaResInv = (Button) findViewById(R.id.btnPoupancaResInv);
        btnTesouroResInv = (Button) findViewById(R.id.btnTesouroResInv);
        btnCdbResInv = (Button) findViewById(R.id.btnCdbResInv);



        usuarioDao = new UsuarioDAO(this);
        transacaoDao = new TransacaoDAO(this);

        Intent i = getIntent();
        String usuarioSrc = i.getStringExtra("emailUsuario");
        usuario = usuarioDao.buscarUsuario(usuarioSrc);

        chamarAlerta("Saldo", "R$ " + usuario.getSaldoUsuario());

        txtSaldoResInv.setText("R$ "  + usuario.getSaldoUsuario());

        txtSaldoPoupancaResInv.setText("R$ " + usuario.getSaldoPoupancaUsuario());

        txtSaldoTesouroResInv.setText("R$ " + usuario.getSaldoTesouroUsuario());
        txtSaldoCdbResInv.setText("R$ " + usuario.getSaldoCdbUsuario());


    }



    protected void resgatarPoupanca (View v){
        origem = "Poupanca";
        resgatar();
    }

    protected void resgatarTesouro (View v){
        origem = "Tesouro";
        resgatar();
    }

    protected void resgatarCdb (View v){
        origem = "Cdb";
        resgatar();
    }

    protected void voltar(View v){
        this.finish();
    }

    protected void resgatar (){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o valor desejado");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    validarTransacao(input,origem);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        builder.show();
    }

    protected void validarTransacao(EditText input,String origem){
        long saldoResgatado;


        saldoResgatado = Long.parseLong(input.getText().toString());




        if(origem.equals("Poupanca")){
            if(saldoResgatado <= usuario.getSaldoPoupancaUsuario()){
                usuario.setSaldoPoupancaUsuario(usuario.getSaldoPoupancaUsuario() - saldoResgatado);
                txtSaldoPoupancaResInv.setText("R$ " + usuario.getSaldoPoupancaUsuario());
                realizarTransacao(saldoResgatado);
            }else{
                chamarAlerta("Erro","O saldo resgatado deve ser menor ou igual ao seu investimento");
            }
        }else{
            if(origem.equals("Tesouro")){
                if(saldoResgatado <= usuario.getSaldoTesouroUsuario()){
                    usuario.setSaldoTesouroUsuario(usuario.getSaldoTesouroUsuario() - saldoResgatado);
                    txtSaldoTesouroResInv.setText("R$ " + usuario.getSaldoTesouroUsuario());
                    realizarTransacao(saldoResgatado);
                }else{
                    chamarAlerta("Erro","O saldo resgatado deve ser menor ou igual ao seu investimento");
                }
            }else{
                if(origem.equals("Cdb")){
                    if(saldoResgatado <= usuario.getSaldoCdbUsuario()){
                        usuario.setSaldoCdbUsuario(usuario.getSaldoCdbUsuario() - saldoResgatado);
                        txtSaldoCdbResInv.setText("R$ " + usuario.getSaldoCdbUsuario());
                        realizarTransacao(saldoResgatado);
                    }else{
                        chamarAlerta("Erro","O saldo resgatado deve ser menor ou igual ao seu investimento");
                    }

                }
            }
        }



    }

    private void realizarTransacao(long saldoResgatado){
        long saldoAntigo;
        long saldoNovo;

        saldoAntigo = usuario.getSaldoUsuario();
        saldoNovo = saldoAntigo + saldoResgatado;

        usuario.setSaldoUsuario(saldoNovo);

        usuarioDao.atualizar(usuario);

        Transacao transacao = new Transacao();

        transacao.setDataTransacao(GregorianCalendar.getInstance().getTime().toString());
        transacao.setValorTransacao(saldoResgatado);
        transacao.setTipoTransacao("Investimento");
        transacao.setNomeInvestimentoTransacao(origem + " - Resgate");
        transacao.setIdUsuarioTransacao(usuario.getIdUsuario());

        TransacaoDAO transacaoDao = new TransacaoDAO(this);
        transacaoDao.adicionarTransacao(transacao);

        txtSaldoResInv.setText("RS " + saldoNovo);
    }

    protected void chamarAlerta(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.show();
    }
}
