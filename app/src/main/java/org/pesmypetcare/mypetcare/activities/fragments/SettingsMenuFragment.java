package org.pesmypetcare.mypetcare.activities.fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentSettingsMenuBinding;

import java.util.Objects;

public class SettingsMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentSettingsMenuBinding binding = FragmentSettingsMenuBinding.inflate(getLayoutInflater());
        ArrayAdapter<CharSequence> languages;
        languages = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.array.Languages, android.R.layout.simple_spinner_item);
        languages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.idiomSelector.setAdapter(languages);
        binding.idiomSelector.setOnItemSelectedListener(this);
        return binding.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }
}
