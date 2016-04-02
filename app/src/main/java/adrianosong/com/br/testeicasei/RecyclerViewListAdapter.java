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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by song on 29/03/16.
 *
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.ViewHolderMovie> {

    private Context context;
    private List<ShortMovie> listShortMovies;

    public RecyclerViewListAdapter(Context context, List<ShortMovie> listaMovies){

        this.context = context;
        this.listShortMovies = listaMovies;
    }


    @Override
    public ViewHolderMovie onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_movies_item, parent, false);
        ViewHolderMovie viewHolderMovie = new ViewHolderMovie(context, view, new ViewHolderMovie.OnItemClick() {
            @Override
            public void onItemClick(View caller, int itemPosition) {

                ShortMovie movie = listShortMovies.get(itemPosition);

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("shortMovie", movie);
                context.startActivity(intent);
            }
        });

        return viewHolderMovie;
    }

    @Override
    public void onBindViewHolder(final ViewHolderMovie holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ShortMovie shortMovie = listShortMovies.get(position);

        holder.txtTitle.setText(shortMovie.getTitle());

        String url = "http://www.omdbapi.com/?i=" + shortMovie.getImdbID() + "&plot=short&r=json";

        JsonObjectRequest requestPlot = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Movie movie = new Movie();

                try {
                    movie.setTitle(response.getString("Title"));
                    movie.setYear(response.getString("Year"));
                    movie.setRated(response.getString("Rated"));
                    movie.setReleased(response.getString("Released"));
                    movie.setRuntime(response.getString("Runtime"));
                    movie.setGenre(response.getString("Genre"));
                    movie.setDirector(response.getString("Director"));
                    movie.setWriter(response.getString("Writer"));
                    movie.setActors(response.getString("Actors"));
                    movie.setPlot(response.getString("Plot"));
                    movie.setLanguage(response.getString("Language"));
                    movie.setCountry(response.getString("Country"));
                    movie.setAwards(response.getString("Awards"));
                    movie.setPoster(response.getString("Poster"));
                    movie.setMetascore(response.getString("Metascore"));
                    movie.setImdbRating(response.getString("imdbRating"));
                    movie.setImdbVotes(response.getString("imdbVotes"));
                    movie.setImdbId(response.getString("imdbID"));
                    movie.setType(response.getString("Type"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                holder.txtPlot.setText(movie.getPlot());

                try {
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

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //adicionando a requisicao
        MyVolleySingleton.getInstance(context).addToRequestQueue(requestPlot);
    }

    @Override
    public int getItemCount() {
        return listShortMovies.size();
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
            void onItemClick(View caller, int itemPosition);
        }
    }

}
