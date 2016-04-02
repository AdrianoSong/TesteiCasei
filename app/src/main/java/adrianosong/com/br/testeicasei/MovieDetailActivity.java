package adrianosong.com.br.testeicasei;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar config
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        ShortMovie shortMovie = (ShortMovie)extras.getSerializable("shortMovie");

        final ImageView imgPoster = (ImageView) findViewById(R.id.imgPoster);

        final TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        final TextView txtPlot = (TextView) findViewById(R.id.txtPlot);
        final TextView txtActors = (TextView) findViewById(R.id.txtActors);
        final TextView txtDirectors = (TextView) findViewById(R.id.txtDirectors);
        final TextView txtReleased = (TextView) findViewById(R.id.txtReleased);
        final TextView txtDuration = (TextView) findViewById(R.id.txtDuration);
        final TextView txtGenre = (TextView) findViewById(R.id.txtGenre);
        final TextView txtRating = (TextView) findViewById(R.id.txtRating);
        final TextView txtAwards = (TextView) findViewById(R.id.txtAwards);
        final TextView txtLocation = (TextView) findViewById(R.id.txtLocation);

        Typeface robotoRegular = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        Typeface helvetica = Typeface.createFromAsset(getAssets(),"Helvetica.ttf");

        txtTitle.setTypeface(robotoRegular);
        txtPlot.setTypeface(helvetica);
        txtActors.setTypeface(helvetica);
        txtDirectors.setTypeface(helvetica);
        txtReleased.setTypeface(helvetica);
        txtDuration.setTypeface(helvetica);
        txtGenre.setTypeface(helvetica);
        txtGenre.setTypeface(helvetica);
        txtRating.setTypeface(helvetica);
        txtAwards.setTypeface(helvetica);
        txtLocation.setTypeface(helvetica);

        String url = "http://www.omdbapi.com/?i=" + shortMovie.getImdbID() + "&plot=short&r=json";

        JsonObjectRequest requestMovie = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    txtTitle.setText(response.getString("Title"));
                    txtPlot.setText(response.getString("Plot"));
                    txtActors.setText(response.getString("Actors"));
                    txtDirectors.setText(response.getString("Director"));
                    txtReleased.setText(response.getString("Released"));
                    txtDuration.setText(response.getString("Runtime"));
                    txtGenre.setText(response.getString("Genre"));
                    txtRating.setText(response.getString("Rated"));
                    txtAwards.setText(response.getString("Awards"));
                    txtLocation.setText(response.getString("Country"));

                } catch (JSONException e) {
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
        MyVolleySingleton.getInstance(this).addToRequestQueue(requestMovie);

        try {
            // Retrieves an image specified by the URL, displays it in the UI. Via Volley
            ImageRequest imageRequest = new ImageRequest(shortMovie.getPoster(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imgPoster.setImageBitmap(response);
                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            //adicionando a requisicao
            MyVolleySingleton.getInstance(this).addToRequestQueue(imageRequest);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
