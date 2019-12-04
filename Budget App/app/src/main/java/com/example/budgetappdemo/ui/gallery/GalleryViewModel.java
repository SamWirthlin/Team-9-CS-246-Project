package com.example.budgetappdemo.ui.gallery;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("----Add a new Category----");

    }

    public LiveData<String> getText() {
        return mText;
    }
    public void get(View view) {}
}