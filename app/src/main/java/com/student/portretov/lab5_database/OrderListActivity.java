package com.student.portretov.lab5_database;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by adminvp on 9/9/17.
 */

public class OrderListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    OrderDbHelper orderDbHelper;
    ListView orderListView;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderListView = (ListView) findViewById(R.id.orderListView);

        orderDbHelper = new OrderDbHelper(this);
        orderList = orderDbHelper.findOrderList();

        ArrayAdapter<Order> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, orderList);
        orderListView.setAdapter(adapter);
        orderListView.setOnItemClickListener(this);
    }

    private Order getOrderByToString(List<Order> orderList, String str){
        for (Order order: orderList) {
            if(order.toString().equals(str)){
                return order;
            }
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String str = ((TextView) view).getText().toString();
        Order order = getOrderByToString(orderList, str);

        if(order != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", order.getId());
            intent.putExtra("login", order.getLogin());
            intent.putExtra("password", order.getPassword());
            intent.putExtra("email", order.getEmail());
            intent.putExtra("title", order.getTitle());
            startActivity(intent);
        }
    }
}
