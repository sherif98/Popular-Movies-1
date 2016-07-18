package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import logic.Movie;

public class MoviesCardsAdapter extends RecyclerView.Adapter<MoviesCardsAdapter.MovieViewHolder> {

    public interface MovieClickListener {
        void onClickMovie(Movie movie);
    }

    private MovieClickListener movieClickListener;
    private List<Movie> moviesList;

    public MoviesCardsAdapter(List<Movie> moviesList, MovieClickListener movieClickListener) {
        this.moviesList = moviesList;
        this.movieClickListener = movieClickListener;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_title)
        TextView title;
        @BindView(R.id.card_view_image)
        ImageView image;

        public MovieViewHolder(View itemView) {
            super(itemView);
        }

        public void bindMovie(Movie movie) {
            title.setText(movie.getTitle());
            Picasso.with(image.getContext()).load(appendPath(movie.getPosterPath())).into(image);
        }

        private String appendPath(String imagePath) {
            //TODO remove hardcoded sized image and use prefrences
            return "http://image.tmdb.org/t/p/w500/" + imagePath;
        }

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = moviesList.get(position);
        holder.bindMovie(movie);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieClickListener.onClickMovie(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
