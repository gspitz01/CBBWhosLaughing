package com.gregspitz.cbbwhoslaughing.data.source;

import android.support.annotation.NonNull;

import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;

import java.util.List;

/**
 * Main entry point for accessing game data.
 */

public interface GameDataSource {

    interface GetLaughersCallback {

        void onLaughersLoaded(List<Laugher> laughers);

        void onDataNotAvailable();
    }

    void getLaughers(@NonNull GetLaughersCallback callback);
}
