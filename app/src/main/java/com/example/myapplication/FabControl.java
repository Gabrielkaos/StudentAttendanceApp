package com.example.myapplication;

public interface FabControl {
    void showFab();
    void hideFab();
    void setFabClickListener(Runnable action);

    void setFabIcon(int resDrawableId);

}
