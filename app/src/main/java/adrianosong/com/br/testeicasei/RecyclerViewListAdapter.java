package adrianosong.com.br.testeicasei;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

/**
 * Created by song on 29/03/16.
 *
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolderMovie> {

    private Context context;
    private List<Movie> listaMovies;

    public RecyclerViewListAdapter(Context context, List<Movie> listaMovies){

        this.context = context;
        this.listaMovies = listaMovies;
    }


    @Override
    public ViewHolderMovie onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_movies_item, parent, false);
        ViewHolderMovie viewHolderMovie = new ViewHolderMovie(context, view, new ViewHolderMovie.OnItemClick() {
            @Override
            public void onItemClick(View caller, int itemPosition) {

                Movie movie = listaMovies.get(itemPosition);

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });

        return viewHolderMovie;
    }

    @Override
    public void onBindViewHolder(final ViewHolderMovie holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Movie movie = listaMovies.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtPlot.setText(movie.getPlot());

        if(movie.getPoster() != null) {
            // Retrieves an image specified by the URL, displays it in the UI. Via Volley
            ImageRequest imageRequest = new ImageRequest(movie.getPoster(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    holder.imgPoster.setImageBitmap(response);
                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            //adicionando a requisicao
            MyVolleySingleton.getInstance(context).addToRequestQueue(imageRequest);
        }


    }

    @Override
    public int getItemCount() {
        return listaMovies.size();
    }

    public static class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context context;

        private ImageView imgPoster;
        private TextView txtPlot;
        private TextView txtTitle;
        private TextView txtDetalhes;

        private OnItemClick mListener;

        public ViewHolderMovie(Context context, View itemView, OnItemClick mListener) {
            super(itemView);
            this.context = context;
            this.mListener = mListener;

            imgPoster = (ImageView) itemView.findViewById(R.id.imgPoster);
            txtPlot = (TextView) itemView.findViewById(R.id.txtPlot);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDetalhes = (TextView) itemView.findViewById(R.id.txtDetalhes);

            Typeface robotoRegular = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
            Typeface robotoMedium = Typeface.createFromAsset(context.getAssets(),"Roboto-Medium.ttf");

            txtPlot.setTypeface(robotoRegular);
            txtTitle.setTypeface(robotoRegular);
            txtDetalhes.setTypeface(robotoMedium);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            mListener.onItemClick(v, getAdapterPosition());

        }

        public interface OnItemClick{
            public void onItemClick(View caller, int itemPosition);
        }
    }

}
