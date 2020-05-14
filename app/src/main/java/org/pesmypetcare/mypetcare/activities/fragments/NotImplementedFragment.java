package org.pesmypetcare.mypetcare.activities.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.controllers.user.TrObtainUserImage;
import org.pesmypetcare.mypetcare.controllers.user.UserControllersFactory;
import org.pesmypetcare.mypetcare.databinding.FragmentNotImplementedBinding;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotImplementedFragment extends Fragment {
    private FragmentNotImplementedBinding binding;
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotImplementedBinding.inflate(inflater, container, false);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            TrObtainUserImage trObtainUserImage = UserControllersFactory.createTrObtainUserImage();
            trObtainUserImage.setAccessToken("Token");
            trObtainUserImage.setUsername("Albert Pinto i Gil");

            try {
                trObtainUserImage.execute();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            this.bitmap = trObtainUserImage.getResult();
        });

        executorService.shutdown();
        System.out.println("WAITING TO FINISH");

        try {
            executorService.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("FINISHED");
        System.out.println("BITMAP " + bitmap);

        binding.displayTestImage.setImageBitmap(bitmap);
        return binding.getRoot();
    }
}
