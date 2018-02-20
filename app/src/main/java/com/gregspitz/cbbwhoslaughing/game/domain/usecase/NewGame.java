package com.gregspitz.cbbwhoslaughing.game.domain.usecase;

import android.support.annotation.NonNull;

import com.gregspitz.cbbwhoslaughing.UseCase;
import com.gregspitz.cbbwhoslaughing.data.source.GameRepository;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;

/**
 * A use case for creating a new {@link Game}
 */
public class NewGame extends UseCase<NewGame.RequestValues, NewGame.ResponseValue> {

    private final GameRepository mGameRepository;

    public NewGame(@NonNull GameRepository gameRepository) {
        mGameRepository = gameRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private Game mGame;

        public ResponseValue(@NonNull Game game) {
            mGame = game;
        }

        public Game getGame() {
            return mGame;
        }
    }
}
