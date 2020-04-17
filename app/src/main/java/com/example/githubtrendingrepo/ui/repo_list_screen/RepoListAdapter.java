package com.example.githubtrendingrepo.ui.repo_list_screen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubtrendingrepo.R;
import com.example.githubtrendingrepo.repository.db.entity.GithubRepo;
import com.example.githubtrendingrepo.util.Utils;

import java.util.List;
import java.util.Objects;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private static final int POSITION = 100;
    private final LayoutInflater mInflater;
    private List<GithubRepo> mRepoList;

    public RepoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(List<GithubRepo> repoList) {
        mRepoList = repoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mMainLayoutCv.setTag(position);
        holder.bind(mRepoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mRepoList != null) {
            return mRepoList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Group mRepoDetailGroup;
        CardView mMainLayoutCv;
        ImageView mAvatarCiv;
        TextView mOwnerTv, mRepositoryTitleTv, mDescriptionTv, mLanguageTv, mStarsTv, mForksTv;
        int positionSelected = -1;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarCiv = itemView.findViewById(R.id.civ_avatar);
            mOwnerTv = itemView.findViewById(R.id.txt_owner);
            mRepositoryTitleTv = itemView.findViewById(R.id.txt_repository_title);
            mDescriptionTv = itemView.findViewById(R.id.txt_desc);
            mLanguageTv = itemView.findViewById(R.id.txt_lang);
            mStarsTv = itemView.findViewById(R.id.txt_stars);
            mForksTv = itemView.findViewById(R.id.txt_forks);
            mMainLayoutCv = itemView.findViewById(R.id.cv_main_layout);
            mRepoDetailGroup = itemView.findViewById(R.id.group1);
            mMainLayoutCv.setOnClickListener(this);
        }

        void bind(GithubRepo repo, int position) {
            Glide.with(itemView.getContext())
                    .load(repo.getAvatar())
                    .placeholder(R.drawable.ic_placeholder)
                    .centerCrop()
                    .into(mAvatarCiv);

            mOwnerTv.setText(repo.getAuthor());
            mRepositoryTitleTv.setText(repo.getName());
            mDescriptionTv.setText(repo.getDescription());
            mLanguageTv.setText(repo.getLanguage());
            mStarsTv.setText(repo.getStars());
            mForksTv.setText(repo.getForks());
            if (!TextUtils.isEmpty(repo.getLanguageColor())) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(itemView.getContext(), R.drawable.circle_drawable);
                Drawable wrappedDrawable = DrawableCompat.wrap(Objects.requireNonNull(unwrappedDrawable));
                if (repo.getLanguageColor().length() < 7) {
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor(Utils.parseColor(repo.getLanguageColor())));
                } else {
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor(repo.getLanguageColor()));
                }
            }

            if (positionSelected == position) {
                mRepoDetailGroup.setVisibility(View.VISIBLE);
            } else {
                mRepoDetailGroup.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (mRepoDetailGroup.getVisibility() != View.VISIBLE) {
                positionSelected = (int) v.getTag();
                mRepoDetailGroup.setVisibility(View.VISIBLE);
            } else {
                positionSelected = -1;
                mRepoDetailGroup.setVisibility(View.GONE);
            }
        }
    }

}
