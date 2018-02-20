package com.gregspitz.cbbwhoslaughing.game;

import android.support.annotation.NonNull;

import com.gregspitz.cbbwhoslaughing.BasePresenter;
import com.gregspitz.cbbwhoslaughing.UseCase;
import com.gregspitz.cbbwhoslaughing.UseCaseHandler;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;
import com.gregspitz.cbbwhoslaughing.game.domain.usecase.NewGame;

/**
 * Listens to the UI and updates it as necessary
 */
public class GamePresenter implements GameContract.Presenter {
    private final UseCaseHandler mUseCaseHandler;
    private final GameContract.View mGameView;
    private final NewGame mNewGame;

    public GamePresenter(@NonNull UseCaseHandler useCaseHandler,
            @NonNull GameContract.View gameView,
            @NonNull NewGame newGame) {
        mUseCaseHandler = useCaseHandler;
        mGameView = gameView;
        mNewGame = newGame;
        mGameView.setPresenter(this);
    }

    @Override
    public void start() {
        loadNewGame();
    }

    @Override
    public void loadNewGame() {
        mGameView.setLoadingIndicator(true);

        mUseCaseHandler.execute(mNewGame, new NewGame.RequestValues(),
                new UseCase.UseCaseCallback<NewGame.ResponseValue>() {

                    @Override
                    public void onSuccess(NewGame.ResponseValue response) {
                        Game game = response.getGame();

                        // View may not be able to handle UI updates anymore
                        if (!mGameView.isActive()) {
                            return;
                        }

                        mGameView.setLoadingIndicator(false);
                        mGameView.showGame(game);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void guessGame(Laugher laugher) {

    }
}
