package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentNotImplementedBinding;

public class NotImplementedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNotImplementedBinding binding = FragmentNotImplementedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
