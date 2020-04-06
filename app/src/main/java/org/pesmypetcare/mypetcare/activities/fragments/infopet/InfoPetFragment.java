package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.Objects;


public class InfoPetFragment extends Fragment {
    private static Drawable petProfileDrawable;
    private static boolean isImageModified;
    private static Pet pet = new Pet("Linux");
    private static Resources resources;
    private static boolean isDefaultPetImage;
    private static final String PET_PROFILE_IMAGE_DESCRIPTION = "pet profile image";

    private FragmentInfoPetBinding binding;
    private Button birthDate;
    private boolean modified;
    private String newWeight;
    private String newName;
    private String newBreed;
    private String newGender;
    private boolean isPetDeleted;
    private CircularImageView petProfileImage;
    private InfoPetCommunication communication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();
        resources = Objects.requireNonNull(getActivity()).getResources();
        isPetDeleted = false;



        /*updatePetListeners();
        //setCalendarPicker();
        setGenderDropdownMenu();
        setPetProfileImage();
        initializePetInfo();
        setDeletePet();
        modified = false;*/
        return binding.getRoot();
    }


}
