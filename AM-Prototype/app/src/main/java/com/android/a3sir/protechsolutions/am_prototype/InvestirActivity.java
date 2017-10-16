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

public class InvestirActivity extends AppCompatActivity {

    private TextView txtSaldoInvestir;
    private Button btnPoupancaInvestir;
    private Button btnTesouroInvestir;
    private Button btnCdbInvestir;

    private Usuario usuario;
    private UsuarioDAO usuarioDao;
    private TransacaoDAO transacaoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investir);

        txtSaldoInvestir = (TextView) findViewById(R.id.txtSaldoInvestir);
        btnPoupancaInvestir = (Button) findViewById(R.id.btnPoupancaInvestir);
        btnTesouroInvestir = (Button) findViewById(R.id.btnTesouroInvestir);
        btnCdbInvestir = (Button) findViewById(R.id.btnCdbInvestir);

        usuarioDao = new UsuarioDAO(this);
        transacaoDao = new TransacaoDAO(this);

        Intent i = getIntent();
        String usuarioSrc = i.getStringExtra("emailUsuario");
        usuario = usuarioDao.buscarUsuario(usuarioSrc);

        txtSaldoInvestir.setText("R$ " + usuario.getSaldoUsuario());
    }



    protected void investirPoupanca (View v){
        investir("Poupanca");
    }

    protected void investirTesouro (View v){
        investir("Tesouro");
    }

    protected void investirCdb (View v){
        investir("Cdb");
    }

    protected void voltar(View v){
        this.finish();
    }

    protected void investir (final String origem){

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
                    realizarTransacao(input,origem);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        builder.show();
    }

    protected void realizarTransacao(EditText input,String origem){
        long saldoInvestido;
        long saldoAntigo;
        long saldoNovo;

        saldoInvestido = Long.parseLong(input.getText().toString());
        saldoAntigo = usuario.getSaldoUsuario();

        if(saldoInvestido <= saldoAntigo){
            saldoNovo = saldoAntigo - saldoInvestido;

            usuario.setSaldoUsuario(saldoNovo);

            if(origem.equals("Poupanca")){
                usuario.setSaldoPoupancaUsuario(usuario.getSaldoPoupancaUsuario() + saldoInvestido);
            }else{
                if(origem.equals("Tesouro")){
                    usuario.setSaldoTesouroUsuario(usuario.getSaldoTesouroUsuario() + saldoInvestido);
                }else{
                    if(origem.equals("Cdb")){
                        usuario.setSaldoCdbUsuario(usuario.getSaldoCdbUsuario() + saldoInvestido);
                    }
                }
            }

            usuarioDao.atualizar(usuario);

            Transacao transacao = new Transacao();

            transacao.setDataTransacao(GregorianCalendar.getInstance().getTime().toString());
            transacao.setValorTransacao(saldoInvestido);
            transacao.setTipoTransacao("Investimento");
            transacao.setNomeInvestimentoTransacao(origem + "- Aplicacao");
            transacao.setIdUsuarioTransacao(usuario.getIdUsuario());

            TransacaoDAO transacaoDao = new TransacaoDAO(this);
            transacaoDao.adicionarTransacao(transacao);

            txtSaldoInvestir.setText("RS " + saldoNovo);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro");
            builder.setMessage("O valor investido deve ser menor ou igual ao seu Saldo");
            builder.setCancelable(true);
            builder.setPositiveButton("OK",null);
            builder.show();
        }



    }

}
