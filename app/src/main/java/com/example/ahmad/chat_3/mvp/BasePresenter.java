package com.example.ahmad.chat_3.mvp;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {

    protected WeakReference<V> viewReference;

    public void attachView(V view) {
        viewReference = new WeakReference<V>(view);
    }

    public void detachView() {
        onViewDetached();
        viewReference = null;
    }

    public V getView() {
        return viewReference.get();
    }

    // This checking is only necessary when returning from an asynchronous call
    public final boolean isViewAttached() {
        return viewReference.get() != null;
    }

    public void onViewDetached() {

    }
}