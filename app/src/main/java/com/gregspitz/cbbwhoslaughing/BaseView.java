package com.gregspitz.cbbwhoslaughing;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
