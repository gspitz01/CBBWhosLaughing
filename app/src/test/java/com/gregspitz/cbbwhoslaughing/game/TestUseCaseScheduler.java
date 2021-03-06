package com.gregspitz.cbbwhoslaughing.game;

import com.gregspitz.cbbwhoslaughing.UseCase;
import com.gregspitz.cbbwhoslaughing.UseCaseScheduler;

/**
 * A scheduler that runs synchronously, for testing purposes.
 */
public class TestUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(UseCase.UseCaseCallback<V> useCaseCallback) {
        useCaseCallback.onError();
    }
}
