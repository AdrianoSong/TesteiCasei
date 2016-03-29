package adrianosong.com.br.testeicasei;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class MovieDetailActivity extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        Movie movie = (Movie)extras.getSerializable("movie");

        final ImageView imgPoster = (ImageView) findViewById(R.id.imgPoster);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtPlot = (TextView) findViewById(R.id.txtPlot);
        TextView txtActors = (TextView) findViewById(R.id.txtActors);
        TextView txtDirectors = (TextView) findViewById(R.id.txtDirectors);
        TextView txtReleased = (TextView) findViewById(R.id.txtReleased);
        TextView txtDuration = (TextView) findViewById(R.id.txtDuration);
        TextView txtGenre = (TextView) findViewById(R.id.txtGenre);
        TextView txtRating = (TextView) findViewById(R.id.txtRating);
        TextView txtAwards = (TextView) findViewById(R.id.txtAwards);
        TextView txtLocation = (TextView) findViewById(R.id.txtLocation);

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

        txtTitle.setText(movie.getTitle());
        txtPlot.setText(movie.getPlot());
        txtActors.setText(movie.getActors());
        txtDirectors.setText(movie.getDirector());
        txtReleased.setText(movie.getReleased());
        txtDuration.setText(movie.getRuntime());
        txtGenre.setText(movie.getGenre());
        txtRating.setText(movie.getRated());
        txtAwards.setText(movie.getAwards());
        txtLocation.setText(movie.getCountry());

        // Retrieves an image specified by the URL, displays it in the UI. Via Volley
        ImageRequest imageRequest = new ImageRequest(movie.getPoster(), new Response.Listener<Bitmap>() {
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
