package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import butterknife.ButterKnife;
import logic.APIKeys;
import logic.Trailer;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerHolder> {

    private List<Trailer> mTrailers;
    private TrailerClickListener mTrailerClickListener;

    public interface TrailerClickListener {
        void onTrailerClicked(String link);
    }

    public MovieTrailerAdapter(List<Trailer> trailers, TrailerClickListener trailerClickListener) {
        mTrailers = trailers;
        mTrailerClickListener = trailerClickListener;
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder {
        YouTubeThumbnailView mYouTubeThumbnailView;
        TextView mTextView;

        public TrailerHolder(View itemView) {
            super(itemView);
            mYouTubeThumbnailView = ButterKnife.findById(itemView, R.id.movie_detail_trailer_thumbnail);
            mTextView = ButterKnife.findById(itemView, R.id.movie_detil_trailer_title);
        }

        public void bindTrailer(final Trailer trailer) {
            mYouTubeThumbnailView.initialize(APIKeys.YOUTUBE, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(trailer.getVideoKey());
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
            mTextView.setText(trailer.getName());
        }
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_card_view, parent, false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerHolder holder, int position) {
        final Trailer trailer = mTrailers.get(position);
        holder.bindTrailer(trailer);
        holder.mYouTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrailerClickListener.onTrailerClicked(trailer.getVideoKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
