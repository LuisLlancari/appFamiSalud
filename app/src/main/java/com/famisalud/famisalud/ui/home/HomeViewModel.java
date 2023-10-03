package com.famisalud.famisalud.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.famisalud.famisalud.R;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de INICIO");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<SlideModel> getSlideModelsList(){
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.banner4, ScaleTypes.FIT));
        return slideModelList;
    }

}