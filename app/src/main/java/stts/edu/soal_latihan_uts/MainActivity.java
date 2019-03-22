package stts.edu.soal_latihan_uts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl,cbPudding,cbRedBean,cbCoconut;
    private TextView txtQty,txtTotal,txtNama;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder = new ArrayList<>();
    private long total;
    private int index=-1;
    private Button btnPlus,btnMinus,btnAdd,btnDelete,btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnReset = findViewById(R.id.btnReset);
        btnDelete = findViewById(R.id.btnDelete);
        txtQty = findViewById(R.id.txtQty);
        txtTotal = findViewById(R.id.txtTotal);
        txtNama = findViewById(R.id.txtNama);
        rbTea=findViewById(R.id.rbTea);
        rbCoffee=findViewById(R.id.rbCoffee);
        rbSmoothies=findViewById(R.id.rbSmoothies);
        edName=findViewById(R.id.edName);
        rgType=findViewById(R.id.rgType);
        btnAdd=findViewById(R.id.btnAdd);
        btnDelete=findViewById(R.id.btnDelete);
        cbPearl=findViewById(R.id.cbPearl);
        cbPudding=findViewById(R.id.cbPudding);
        cbCoconut=findViewById(R.id.cbCoconut);
        cbRedBean=findViewById(R.id.cbRedBean);
        rvOrder = findViewById(R.id.rvOrder);

        adapter = new OrderAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                //Toast.makeText(MainActivity.this,posisi+"",Toast.LENGTH_SHORT).show();
                index = posisi;
                txtQty.setText(arrOrder.get(posisi).getQty()+"");
                if (arrOrder.get(posisi).getType().equals("Tea"))rbTea.setChecked(true);
                if (arrOrder.get(posisi).getType().equals("Coffee"))rbCoffee.setChecked(true);
                if (arrOrder.get(posisi).getType().equals("Smoothies"))rbSmoothies.setChecked(true);

                cbPearl.setChecked(false);
                cbCoconut.setChecked(false);
                cbPudding.setChecked(false);
                cbRedBean.setChecked(false);
                for (String s :
                        arrOrder.get(posisi).getToppings()) {
                    if (s.equals("Pearl"))cbPearl.setChecked(true);
                    if (s.equals("Coconut Jelly"))cbCoconut.setChecked(true);
                    if (s.equals("Pudding"))cbPudding.setChecked(true);
                    if (s.equals("Red Bean"))cbRedBean.setChecked(true);
                }
            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nilai = Integer.parseInt(txtQty.getText().toString());
                txtQty.setText((nilai+=1)+"");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nilai = Integer.parseInt(txtQty.getText().toString());
                if(nilai>1)
                txtQty.setText((nilai-=1)+"");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edName.getText().toString().equals("")){
                    txtNama.setText(edName.getText());
                    int selectedID = rgType.getCheckedRadioButtonId();
                    String typeDrink = ((RadioButton) findViewById(selectedID)).getText().toString();
                    long subtotal = 0;
                    ArrayList<String> toppings = new ArrayList<String>();
                    if(cbPearl.isChecked()){
                        toppings.add(cbPearl.getText().toString());
                        subtotal+=3000;
                    }
                    if(cbPudding.isChecked()){
                        toppings.add(cbPudding.getText().toString());
                        subtotal+=4000;
                    }
                    if(cbRedBean.isChecked()){
                        toppings.add(cbRedBean.getText().toString());
                        subtotal+=3000;
                    }
                    if(cbCoconut.isChecked()){
                        toppings.add(cbCoconut.getText().toString());
                        subtotal+=4000;
                    }

                    if(typeDrink.equals("Coffee")) subtotal+=25000;
                    if(typeDrink.equals("Tea")) subtotal+=23000;
                    if(typeDrink.equals("Smoothies")) subtotal+=23000;

                    int qty = Integer.parseInt(txtQty.getText().toString());
                    subtotal*=qty;
                    arrOrder.add(new Order(typeDrink,toppings,qty,subtotal));

                    total+=subtotal;
                    txtTotal.setText(""+total);
                    adapter.notifyDataSetChanged();
                    reset();

                }else{
                    Toast.makeText(MainActivity.this,"Field Name cannot Be empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                total=0;
                txtTotal.setText(total+"");
                txtNama.setText("Cust");
                edName.setText("");
                arrOrder.clear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total-=arrOrder.get(index).getSubtotal();
                txtTotal.setText(total+"");
                arrOrder.remove(index);

                adapter.notifyDataSetChanged();
                reset();
            }
        });


    }

    public void reset(){
        txtQty.setText("1");
        ((RadioButton)rgType.getChildAt(0)).setChecked(true);
        cbPearl.setChecked(false);
        cbPudding.setChecked(false);
        cbRedBean.setChecked(false);
        cbCoconut.setChecked(false);
    }



}
