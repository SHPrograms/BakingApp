package com.sh.study.udacitynano.bakingapp.recipes;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter used to show list of steps in Recycler View.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepAdapterViewHolder> {
    private ArrayList<Step> steps;

    final private VideoInterface clickHandler;

    StepsAdapter(VideoInterface clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_steps, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapterViewHolder holder, int position) {
        holder.stepName.setText(holder.itemView.getResources().getString(R.string.steps_number, steps.get(position).getId()));
        holder.stepDescriptionShort.setText(String.valueOf(steps.get(position).getShortDescription()));
        holder.stepDescriptionLong.setText(String.valueOf(steps.get(position).getDescription()));

        if ((steps.get(position).getVideoURL() != null) && (!steps.get(position).getVideoURL().isEmpty())) {
            // TODO: Create thumb or put image from getThumbnailURL as a Thumb for player
            holder.stepThumb.setVisibility(View.GONE);
            // TODO: it doesn't work
//            holder.stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.placeholder_50x50));
        } else {
            holder.stepVideo.setVisibility(View.GONE);
            if ((steps.get(position).getThumbnailURL() != null) && (!steps.get(position).getThumbnailURL().isEmpty())) {
                Picasso.with(holder.stepThumb.getContext())
                        .load(String.valueOf(steps.get(position).getThumbnailURL()))
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_50x50)
                        .error(R.drawable.placeholder_50x50)
                        .into(holder.stepThumb);
            } else holder.stepThumb.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        else return steps.size();
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_name)
        TextView stepName;

        @BindView(R.id.recipe_step_description_short)
        TextView stepDescriptionShort;

        @BindView(R.id.recipe_step_description_long)
        TextView stepDescriptionLong;

        @BindView(R.id.recipe_step_thumb_url)
        ImageView stepThumb;

        //TODO: Replace SimpleExoPlayerView with a Button to create a new Activity or fragment only for Video!
        @BindView(R.id.recipe_step_video_url)
        SimpleExoPlayerView stepVideo;

        StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    Step step = steps.get(adapterPosition);
                    clickHandler.onClickVideo(step.getVideoURL(), stepVideo);
                }
            });
//            stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.placeholder_50x50));
/*
            stepVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    Step step = steps.get(adapterPosition);
                    clickHandler.onClickVideo(step.getVideoURL(), stepVideo);
                }
            });
*/
            //stepVideo.setOnClickListener((View v) -> {VideoClicked(stepVideo);});
/*
            stepVideo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
*/
        }

        private void VideoClicked(SimpleExoPlayerView view) {
            int adapterPosition = getAdapterPosition();
            Step step = steps.get(adapterPosition);
            clickHandler.onClickVideo(step.getVideoURL(), view);
        }
    }
}
