package com.gruposeb.pedro.pim.activity.swipeView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.gruposeb.pedro.pim.R;
import com.gruposeb.pedro.pim.activity.activity.CadastroActivity;
import com.gruposeb.pedro.pim.activity.activity.ListagemProdActivity;
import com.gruposeb.pedro.pim.activity.activity.ProductActivity;
import com.gruposeb.pedro.pim.activity.dao.ProdutoDao;
import com.gruposeb.pedro.pim.activity.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {
    private AlertDialog.Builder msg_delete;

    private Context mContext;
    private ArrayList<Produto> produtos=new ArrayList<>();
    private ProdutoDao produtoDao=new ProdutoDao();



    public SwipeRecyclerViewAdapter(Context context, ArrayList<Produto> objects) {
        this.mContext = context;
        this.produtos = objects;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
          final Produto item;
              item =  produtos.get(position);

        viewHolder.nome.setText("Produto: "+item.getNome());
        viewHolder.cat.setText("Categoria: "+item.getCategoria());
        viewHolder.qtd.setText("Qtd: "+String.valueOf(item.getQtd()));
        viewHolder.preco.setText("Preço: "+String.valueOf(item.getPreco()));
        viewHolder.id.setText(String.valueOf((item.getIdProd())));


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);



        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));




        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });



        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
             Intent intent=   new Intent(view.getContext(),ProductActivity.class);
                intent.putExtra("nome",viewHolder.nome.getText());
                intent.putExtra("cat",viewHolder.cat.getText());
                intent.putExtra("qtd",viewHolder.qtd.getText());
                intent.putExtra("preco",viewHolder.preco.getText());
                intent.putExtra("prodid",viewHolder.id.getText());


                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.id.getText().toString(), Toast.LENGTH_SHORT).show();





            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " Click : " +item.getNome(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg_delete=new AlertDialog.Builder(v.getContext());

                msg_delete.setTitle("MENSAGEM");
                msg_delete.setMessage("Deseja realmente excluir este Produto ?");
                msg_delete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        produtoDao.DeleteValue(viewHolder.id.getText().toString());
                        Toast.makeText(mContext, "Produto Deletado com Sucesso!!!\n " + viewHolder.nome.getText().toString(), Toast.LENGTH_SHORT).show();
                        produtos.remove(position);
                        notifyItemRemoved(position);



                    }
                });
                msg_delete.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "Ok ", Toast.LENGTH_SHORT).show();
                    }
                });

                msg_delete.show();


                mItemManger.removeShownLayouts(viewHolder.swipeLayout);


            }
        });


        mItemManger.bindView(viewHolder.itemView, position);




    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        public TextView id;
        public TextView nome;
        public TextView cat;
        public TextView qtd;
        public TextView preco;
        public ImageButton Delete;
        public ImageButton Edit;
        public SimpleViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            id = (TextView) itemView.findViewById(R.id.prodId);
            nome = (TextView) itemView.findViewById(R.id.nomeId);
            cat = (TextView) itemView.findViewById(R.id.catId);
            qtd = (TextView) itemView.findViewById(R.id.qtdId);
            preco = (TextView) itemView.findViewById(R.id.precoId);
            Delete = (ImageButton) itemView.findViewById(R.id.Delete);
            Edit = (ImageButton) itemView.findViewById(R.id.Edit);
        }


    }


}
