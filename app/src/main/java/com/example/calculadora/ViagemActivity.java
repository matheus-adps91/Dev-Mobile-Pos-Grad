package com.example.calculadora;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ViagemActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viagem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btCalcularViagem = findViewById(R.id.btnCalcularViagem);
        btCalcularViagem.setOnClickListener(this);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);
        Button btnLimpar = findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(this);

        generateToast("Use '.' para valores decimais", Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCalcularViagem) {
            calcular();
        } else if (view.getId() == R.id.btnLimpar) {
            limparCampos();
        } else {
            generateToast("Tenha uma ótima viagem", Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void limparCampos() {
        EditText etPrecoPorLitro = findViewById(R.id.etnPrecoPorLitro);
        EditText etDistancia = findViewById(R.id.etnDistancia);
        EditText etMediaVeiculo = findViewById(R.id.etnMediaVeiculo);

        etPrecoPorLitro.setText("");
        etDistancia.setText("");
        etMediaVeiculo.setText("");
        etPrecoPorLitro.requestFocus();
    }

    private void calcular() {
        EditText etPrecoPorLitro = findViewById(R.id.etnPrecoPorLitro);
        EditText etDistancia = findViewById(R.id.etnDistancia);
        EditText etMediaVeiculo = findViewById(R.id.etnMediaVeiculo);

        List<EditText> entradas = Arrays.asList(etPrecoPorLitro, etDistancia, etMediaVeiculo);
        if (hasInvalidatedFields(entradas)) {
            return;
        }

        double precoPorLitro = Double.parseDouble(etPrecoPorLitro.getText().toString());
        double distancia = Double.parseDouble(etDistancia.getText().toString());
        double mediaVeiculo = Double.parseDouble(etMediaVeiculo.getText().toString());

        double total = distancia / mediaVeiculo * precoPorLitro;

        DecimalFormat df = new DecimalFormat("#.00");
        TextView etGastotal = findViewById(R.id.txtGastoTotal);
        StringBuilder sb = new StringBuilder("Gasto total: R$ ")
                .append(df.format(total));
        etGastotal.setText(sb.toString());
        etGastotal.setVisibility(VISIBLE);
    }

    private boolean hasInvalidatedFields(List<EditText> entradas) {
        boolean error = false;
        for (EditText entrada: entradas) {
            if (entrada.getText().toString().isBlank()) {
                entrada.setError("Campo não informado");
                error = true;
            }
        }
        return error;
    }

    private void generateToast(String text, int duration) {
        Toast.makeText(
                ViagemActivity.this,
                text,
                duration).show();
    }
}