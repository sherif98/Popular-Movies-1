package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import butterknife.ButterKnife;
import logic.Review;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewHolder> {

    private List<Review> mReviewList;

    public MovieReviewAdapter(List<Review> reviewList) {
        this.mReviewList = reviewList;
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView contentTextView;

        public ReviewHolder(View itemView) {
            super(itemView);
            authorTextView = ButterKnife.findById(itemView, R.id.movie_detail_review_author);
            contentTextView = ButterKnife.findById(itemView, R.id.movie_detail_review_content);
        }

        public void bindReview(Review review) {
            authorTextView.setText(review.getAuthor());
            contentTextView.setText(review.getContet());
        }
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_card_view, parent, false);
        return new ReviewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.bindReview(mReviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}
