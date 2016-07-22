package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;

import butterknife.ButterKnife;
import database.MovieCursorWrapper;
import logic.Movie;

public class RecyclerViewCursorAdapter extends RecyclerView.Adapter<RecyclerViewCursorAdapter.MovieHolder> {

    private MovieCursorWrapper mCursor;
    private MoviesCardsAdapter.MovieClickListener movieClickListener;

    public RecyclerViewCursorAdapter(MovieCursorWrapper cursor,
                                     MoviesCardsAdapter.MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
        mCursor = cursor;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public MovieHolder(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.card_view_title);
            image = ButterKnife.findById(itemView, R.id.card_view_image);
        }

        public void bindMovie(Movie movie) {
            title.setText(movie.getTitle());
            image.setImageBitmap(movie.getPosterBitmap());
        }
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card_view, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        mCursor.moveToPosition(position);
        final Movie movie = mCursor.getMovie();
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
        return mCursor.getCount();
    }
}
