package com.student.portretov.lab5_database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLogin, editTextPassword, editTextEmail, editTextTitle;
    Long idCurrentOrder;
    TextView tvIdCurrentOrder;
    OrderDbHelper orderDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderDbHelper =  new OrderDbHelper(getApplicationContext());

        tvIdCurrentOrder = (TextView) findViewById(R.id.tvIdCurrentOrder);
        editTextLogin = (EditText)findViewById(R.id.editTextLogin);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextTitle = (EditText)findViewById(R.id.editTextTitle);

        findViewById(R.id.btnCreate).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnUpdate).setOnClickListener(this);
        findViewById(R.id.btnList).setOnClickListener(this);

        Intent intent = getIntent();
        idCurrentOrder = intent.getLongExtra("id", 0);

        Order order = new Order(
                idCurrentOrder,
                intent.getStringExtra("login"),
                intent.getStringExtra("password"),
                intent.getStringExtra("email"),
                intent.getStringExtra("title")
        );
        fillText(order);
    }

    private void fillText(Order order){
        clearText();
        tvIdCurrentOrder.setText(String.format("ID заявки - %d", idCurrentOrder));
        editTextLogin.getText().append(order.getLogin());
        editTextPassword.getText().append(order.getPassword());
        editTextEmail.getText().append(order.getEmail());
        editTextTitle.getText().append(order.getTitle());
    }

    private void clearText(){
        tvIdCurrentOrder.setText("");
        editTextLogin.getText().clear();
        editTextPassword.getText().clear();
        editTextEmail.getText().clear();
        editTextTitle.getText().clear();
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
    }

    private void createOrder(){
        Order order = new Order(
                editTextLogin.getText().toString(),
                editTextPassword.getText().toString(),
                editTextEmail.getText().toString(),
                editTextTitle.getText().toString()
        );
        idCurrentOrder = orderDbHelper.createOrder(order);

        if (idCurrentOrder > 1) {
            tvIdCurrentOrder.setText(String.format("ID заявки - %d", idCurrentOrder));
            showToast("Заявка создана успешно");
        } else {
            showToast("Заполните все поля, заявка не создана!");
        }
    }

    private void updateOrder(){
        Order order = new Order(
                idCurrentOrder,
                editTextLogin.getText().toString(),
                editTextPassword.getText().toString(),
                editTextEmail.getText().toString(),
                editTextTitle.getText().toString()
        );
        int result = orderDbHelper.updateOrder(order);
        String message = (result == 1) ? "Заявка успешно обновлена" : "Заявка не обновлена!";
        showToast(message);
    }

    private void deleteOrder(){
        int result = orderDbHelper.deleteOrder(this.idCurrentOrder);

        if (result == 1) {
            showToast("Заявка успешно удалена!");
            clearText();
        } else {
            showToast("Заполните все поля, заявка не создана!");
        }
    }

    @Override
    public void onClick(View view) {
        Button clickBtn = (Button) view;

        switch (clickBtn.getId()){
            case R.id.btnCreate:
                createOrder();
                break;
            case R.id.btnUpdate:
                updateOrder();
                break;
            case R.id.btnDelete:
                deleteOrder();
                break;
            case R.id.btnList:
                Intent intent = new Intent(this, OrderListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
