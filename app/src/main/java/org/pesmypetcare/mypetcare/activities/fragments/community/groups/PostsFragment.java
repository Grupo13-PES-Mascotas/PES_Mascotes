package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.CircularEntryView;
import org.pesmypetcare.mypetcare.databinding.FragmentPostsBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;
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

        binding.btnSentMessage.setOnClickListener(v -> sendMessage());

        return binding.getRoot();
    }

    private void sendMessage() {
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

        List<CircularEntryView> components = binding.postsViewLayout.getPostComponents();

        for (CircularEntryView component : components) {
            component.setOnLongClickListener(v -> {
                Post post = (Post) component.getObject();
                User user = InfoGroupFragment.getCommunication().getUser();

                if (post.getUsername().equals(user.getUsername())) {
                    AlertDialog dialog = createEditPostDialog(component);
                    dialog.show();
                    return true;
                }

                return false;
            });
        }
    }

    /**
     * Create a dialog to delete the group.
     * @param circularEntryView The entry to which the dialog is associated to
     * @return The dialog that is associated with the entry
     */
    private AlertDialog createEditPostDialog(CircularEntryView circularEntryView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        Post post = (Post) circularEntryView.getObject();
        dialog.setTitle(R.string.edit_post_title);
        dialog.setMessage(R.string.edit_post_message);

        View editPostLayout = getLayoutInflater().inflate(R.layout.edit_post, null);
        TextInputEditText editPostMessage = editPostLayout.findViewById(R.id.editPostMessage);
        MaterialButton btnUpdatePost = editPostLayout.findViewById(R.id.updatePostButton);
        MaterialButton btnDeletePost = editPostLayout.findViewById(R.id.deletePostButton);

        editPostMessage.setText(post.getText());

        dialog.setView(editPostLayout);

        AlertDialog editPostDialog = dialog.create();

        btnUpdatePost.setOnClickListener(v -> {
            InfoGroupFragment.getCommunication().updatePost(post,
                Objects.requireNonNull(editPostMessage.getText()).toString());
        });

        btnDeletePost.setOnClickListener(v -> {
            InfoGroupFragment.getCommunication().deletePost(forum, post.getCreationDate());
            showPosts();
            editPostDialog.dismiss();
        });

        return editPostDialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        showPosts();
    }
}
