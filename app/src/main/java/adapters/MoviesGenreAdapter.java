package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import butterknife.ButterKnife;
import logic.Genre;

public class MoviesGenreAdapter extends RecyclerView.Adapter<MoviesGenreAdapter.GenreHolder> {

    private List<Genre> mGenreList;

    public MoviesGenreAdapter(List<Genre> genreList) {
        mGenreList = genreList;
    }

    public static class GenreHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public GenreHolder(View itemView) {
            super(itemView);
            mTextView = ButterKnife.findById(itemView, R.id.movie_detail_genre_view);
        }

        public void bindGenre(Genre genre) {
            mTextView.setText(genre.getName());
        }
    }

    @Override
    public GenreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_genre_view, parent, false);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreHolder holder, int position) {
        holder.bindGenre(mGenreList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }
}
