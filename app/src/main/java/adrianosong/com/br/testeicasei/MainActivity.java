package adrianosong.com.br.testeicasei;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int HIDE = 100;
    private int SHOW = 200;

    private TextView txtSearch;
    private TextView txtSearhInfo;
    private TextView txtQtdMovies;
    private ImageButton btnSearch;

    private RecyclerView recyclerView;

    private Movie movie;

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (toolbar != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_drawer));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMovies);

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);

        txtQtdMovies = (TextView) findViewById(R.id.txtQtdMovies);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtSearhInfo = (TextView) findViewById(R.id.txtSearchInfo);
        Typeface robotoRegular = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");

        if (txtSearch != null && txtSearhInfo != null) {
            txtSearch.setTypeface(robotoRegular);
            txtSearhInfo.setTypeface(robotoRegular);
            txtQtdMovies.setTypeface(robotoRegular);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    /**
     * mostrar ou nao as views
     * @param flag int
     */
    private void hideOrShowViews(int flag){

        if(flag == HIDE){
            txtSearch.setVisibility(View.INVISIBLE);
            txtSearhInfo.setVisibility(View.INVISIBLE);
            btnSearch.setVisibility(View.INVISIBLE);

            txtQtdMovies.setVisibility(View.VISIBLE);

        }else{
            txtSearch.setVisibility(View.VISIBLE);
            txtSearhInfo.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);

            txtQtdMovies.setVisibility(View.INVISIBLE);

        }

    }


    /**
     * Retrieve data from the cloud
     * @param query String
     */
    private void retriveData(String query){

        String url = "http://www.omdbapi.com/?t="+ query + "&y=&plot=short&r=json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                movie = new Movie();

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

                List<Movie> listaMovies = new ArrayList<>();
                listaMovies.add(movie);

                if(listaMovies.size() > 0){
                    txtQtdMovies.setText("Encontramos " + listaMovies.size() + " resultados:");
                    recyclerView.setAdapter(new RecyclerViewListAdapter(MainActivity.this, listaMovies));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideOrShowViews(SHOW);
            }
        });

        MyVolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideOrShowViews(HIDE);
                retriveData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
