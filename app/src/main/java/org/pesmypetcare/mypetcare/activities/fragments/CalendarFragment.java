package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pesmypetcare.mypetcare.activities.communication.CalendarCommunication;
import org.pesmypetcare.mypetcare.databinding.FragmentCalendarBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private CalendarCommunication communication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        communication = (CalendarCommunication) getActivity();

        return binding.getRoot();
    }
}
