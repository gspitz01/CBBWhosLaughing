package com.gregspitz.cbbwhoslaughing.game;

import com.gregspitz.cbbwhoslaughing.BasePresenter;
import com.gregspitz.cbbwhoslaughing.BaseView;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;

/**
 * Specifies the contract between the view and the presenter
 */

public interface GameContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showGame(Game game);
        void showRightAnswer();
        void showWrongAnswer();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loadNewGame();
        void guessGame(Laugher laugher);
    }
}
