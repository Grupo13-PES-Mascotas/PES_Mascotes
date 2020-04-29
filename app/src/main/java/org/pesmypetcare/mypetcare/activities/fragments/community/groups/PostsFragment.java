package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.communitymanager.ChatException;
import org.pesmypetcare.communitymanager.ChatModel;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.databinding.FragmentPostsBinding;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;
import java.util.Objects;

public class PostsFragment extends Fragment {
    private static Forum forum;
    private FragmentPostsBinding binding;
    private String reportMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        binding.forumName.setHint(forum.getName());

        setForumName();

        binding.btnSentMessage.setOnClickListener(v -> sendMessage());

        return binding.getRoot();
    }

    /**
     * Method responsible for sending a message.
     */
    private void sendMessage() {
        String message = Objects.requireNonNull(binding.postMessage.getText()).toString();

        if (!isUserSubscriber()) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.should_be_subscribed),
                Toast.LENGTH_LONG);
            toast.show();
        } else if (!isMessageEmpty(message)) {
            InfoGroupFragment.getCommunication().addNewPost(forum, message);
            binding.postMessage.setText("");
        }
    }

    /**
     * Method responsible for checking if a user is a subscriber.
     * @return True if the user is subscriber or false otherwise
     */
    private boolean isUserSubscriber() {
        return forum.getGroup().isUserSubscriber(InfoGroupFragment.getCommunication().getUser());
    }

    /**
     * Method responsible for checking if a message is empty.
     * @param message The message to check
     * @return True if the message is empty or false otherwise
     */
    private boolean isMessageEmpty(String message) {
        return "".equals(message);
    }

    /**
     * Method responsible for setting the forum name.
     */
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

    /**
     * Getter of the current forum.
     * @return The current forum
     */
    public static Forum getForum() {
        return forum;
    }

    /**
     * Setter of the forum of the fragment.
     * @param forum The forum to set to the fragment
     */
    public static void setForum(Forum forum) {
        PostsFragment.forum = forum;
    }

    /**
     * Method responsible for showing all the posts of the forum.
     */
    private void showPosts() {
        binding.postsViewLayout.removeAllViews();
        binding.postsViewLayout.showPosts(forum);

        List<CircularEntryView> components = binding.postsViewLayout.getPostComponents();
        User user = InfoGroupFragment.getCommunication().getUser();

        for (CircularEntryView component : components) {
            component.setOnLongClickListener(v -> setLongClickEvent(component));
            component.setOnClickListener(v -> setOnClickEvent(user, component));
        }
    }

    /**
     * Set the on click event to the post.
     * @param user The actual user
     * @param component The component with the post
     */
    private void setOnClickEvent(User user, CircularEntryView component) {
        Post post = (Post) component.getObject();

        if (!post.getUsername().equals(user.getUsername())) {
            if (post.isLikedByUser(user.getUsername())) {
                InfoGroupFragment.getCommunication().unlikePost(post);
            } else {
                InfoGroupFragment.getCommunication().likePost(post);
            }

            showPosts();
        }
    }

    /**
     * Set the long click event.
     * @param component The component to add the event
     * @return True if the click is valid or false otherwise
     */
    private boolean setLongClickEvent(CircularEntryView component) {
        Post post = (Post) component.getObject();
        User user = InfoGroupFragment.getCommunication().getUser();
        AlertDialog dialog;

        if (post.getUsername().equals(user.getUsername())) {
            dialog = createEditPostDialog(component);
        } else {
            dialog = createOptionsPostDialog(component);
        }

        dialog.show();

        return true;
    }

    /**
     * Create a dialog for the options of the post.
     * @param circularEntryView The entry to which the dialog is associated to
     * @return The dialog that is associated with the entry
     */
    private AlertDialog createOptionsPostDialog(CircularEntryView circularEntryView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.post_options_title);
        dialog.setMessage(R.string.post_options_message);

        View optionsPostLayout = getLayoutInflater().inflate(R.layout.post_options, null);
        dialog.setView(optionsPostLayout);
        AlertDialog editPostDialog = dialog.create();

        MaterialButton btnReport = optionsPostLayout.findViewById(R.id.reportPostButtons);
        btnReport.setOnClickListener(v -> {
            editPostDialog.dismiss();
            AlertDialog reportDialog = createReportDialog(circularEntryView);
            reportDialog.show();
        });
        return editPostDialog;
    }

    private AlertDialog createReportDialog(CircularEntryView circularEntryView) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.post_options_title);
        dialog.setMessage(R.string.post_options_message);

        View reportPostLayout = getLayoutInflater().inflate(R.layout.report_options, null);
        dialog.setView(reportPostLayout);
        AlertDialog reportPostDialog = dialog.create();

        RadioGroup reportOptions = reportPostLayout.findViewById(R.id.reportButtons);
        TextInputEditText otherMessage = reportPostLayout.findViewById(R.id.otherMessage);
        MaterialButton confirmReport = reportPostLayout.findViewById(R.id.confirmReportPost);

        setOtherMessageListener(otherMessage);
        setRadioButtonsListeners(reportOptions);
        setConfirmReportListener(circularEntryView, confirmReport, reportPostDialog);

        return reportPostDialog;
    }

    /**
     * Set the confirm report listener.
     * @param circularEntryView The component of the post
     * @param confirmReport The button
     * @param reportPostDialog The alert dialog that is currently displayed
     */
    private void setConfirmReportListener(CircularEntryView circularEntryView, MaterialButton confirmReport,
                                          AlertDialog reportPostDialog) {
        confirmReport.setOnClickListener(v -> {
            Post post = (Post) circularEntryView.getObject();
            InfoGroupFragment.getCommunication().reportPost(post, reportMessage);
            reportPostDialog.dismiss();
        });
    }

    /**
     * Set the radio buttons listeners.
     * @param reportOptions The radio button group
     */
    private void setRadioButtonsListeners(RadioGroup reportOptions) {
        for (int actual = 0; actual < reportOptions.getChildCount(); ++actual) {
            if (reportOptions.getChildAt(actual) instanceof RadioButton) {
                RadioButton reportButton = (RadioButton) reportOptions.getChildAt(actual);
                boolean isOtherMessage = reportButton.getText().toString()
                    .equals(getString(R.string.report_reason_other));

                reportButton.setOnClickListener(v -> {
                    if (!isOtherMessage) {
                        reportMessage = reportButton.getText().toString();
                    }
                });
            }
        }
    }

    /**
     * Set the other message listener.
     * @param otherMessage The other message text field
     */
    private void setOtherMessageListener(TextInputEditText otherMessage) {
        otherMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not implemented
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reportMessage = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not implemented
            }
        });
    }

    /**
     * Create a dialog to delete the post.
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

        editPostMessage.setText(post.getText());
        dialog.setView(editPostLayout);
        AlertDialog editPostDialog = dialog.create();
        addButtonsListeners(post, editPostMessage, editPostLayout, editPostDialog);
        return editPostDialog;
    }

    /**
     * Add the buttons listeners.
     * @param post The post
     * @param editPostMessage The message
     * @param editPostLayout The layout
     * @param editPostDialog The edit post dialog
     */
    private void addButtonsListeners(Post post, TextInputEditText editPostMessage, View editPostLayout,
                                     AlertDialog editPostDialog) {
        MaterialButton btnUpdatePost = editPostLayout.findViewById(R.id.updatePostButton);
        MaterialButton btnDeletePost = editPostLayout.findViewById(R.id.deletePostButton);
        btnUpdatePost.setOnClickListener(v -> {
            InfoGroupFragment.getCommunication().updatePost(post,
                Objects.requireNonNull(editPostMessage.getText()).toString());
        });

        btnDeletePost.setOnClickListener(v -> {
            InfoGroupFragment.getCommunication().deletePost(forum, post.getCreationDate());
            editPostDialog.dismiss();
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ChatModel chatModel = new ViewModelProvider(requireActivity()).get(ChatModel.class);
        chatModel.getMessage().observe(requireActivity(), messageData -> {
            Post post = new Post(messageData.getCreator(), messageData.getText(),
                DateTime.Builder.buildFullString(messageData.getPublicationDate()), forum);
            forum.addPost(post);
            showPosts();
        });

        try {
            chatModel.doAction(forum.getGroup().getName(), forum.getName());
        } catch (ChatException e) {
            e.printStackTrace();
        }
    }
}
