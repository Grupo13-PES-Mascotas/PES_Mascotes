package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentPostsBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;

public class PostsFragment extends Fragment {
    private static Forum forum;
    private FragmentPostsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        binding.postsViewLayout.showPosts(forum);

        return binding.getRoot();
    }

    public static Forum getForum() {
        return forum;
    }

    public static void setForum(Forum forum) {
        PostsFragment.forum = forum;
    }
}
