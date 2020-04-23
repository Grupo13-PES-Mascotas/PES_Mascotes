package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentPostsBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;

import java.util.Objects;

public class PostsFragment extends Fragment {
    private static Forum forum;
    private FragmentPostsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        binding.forumName.setHint(forum.getName());

        showPosts();
        setForumName();

        binding.btnSentMessage.setOnClickListener(v -> {
            String message = Objects.requireNonNull(binding.postMessage.getText()).toString();

            if (!isUserSubscriber()) {
                Toast toast = Toast.makeText(getContext(), getString(R.string.should_be_subscribed),
                    Toast.LENGTH_LONG);
                toast.show();
            } else if (!isMessageEmpty(message)) {
                InfoGroupFragment.getCommunication().addNewPost(forum, message);
                showPosts();
                binding.postMessage.setText("");
            }
        });

        return binding.getRoot();
    }

    private boolean isUserSubscriber() {
        return forum.getGroup().isUserSubscriber(InfoGroupFragment.getCommunication().getUser());
    }

    private boolean isMessageEmpty(String message) {
        return "".equals(message);
    }

    private void setForumName() {
        binding.txtForumName.setText(R.string.no_tags);

        if (forum.getTags().size() > 0) {
            StringBuilder tags = new StringBuilder();

            for (String tag : forum.getTags()) {
                tags.append('#').append(tag).append(',');
            }

            tags.deleteCharAt(tags.length() - 1);
            binding.txtForumName.setText(tags.toString());
        }
    }

    public static Forum getForum() {
        return forum;
    }

    public static void setForum(Forum forum) {
        PostsFragment.forum = forum;
    }

    private void showPosts() {
        binding.postsViewLayout.removeAllViews();
        binding.postsViewLayout.showPosts(forum);
    }

    @Override
    public void onResume() {
        super.onResume();

        showPosts();
    }
}
