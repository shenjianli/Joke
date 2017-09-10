package com.shen.joke.core.base;

public interface MvpView {
    void startLoading();
    void hideLoading();
    void showError(String msg);
}