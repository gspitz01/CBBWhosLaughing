package com.gregspitz.cbbwhoslaughing.game.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gregspitz.cbbwhoslaughing.UseCase;
import com.gregspitz.cbbwhoslaughing.data.source.GameDataSource;
import com.gregspitz.cbbwhoslaughing.data.source.GameRepository;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A use case for creating a new {@link Game}
 */
public class NewGame extends UseCase<NewGame.RequestValues, NewGame.ResponseValue> {

    private final GameRepository mGameRepository;
    private final Random mRandom;

    public NewGame(@NonNull GameRepository gameRepository) {
        mGameRepository = gameRepository;
        mRandom = new Random();
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mGameRepository.getLaughers(new GameDataSource.GetLaughersCallback() {
            @Override
            public void onLaughersLoaded(List<Laugher> laughers) {
                if (laughers != null) {
                    Laugher correctAnswer = selectCorrectAnswer(laughers);
                    Game newGame = new Game(correctAnswer, selectWrongAnswers(laughers, correctAnswer));
                    getUseCaseCallback().onSuccess(new ResponseValue(newGame));
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    private List<Laugher> selectWrongAnswers(List<Laugher> laughers, Laugher correctAnswer) {
        List<Laugher> copyLaughers = new ArrayList<>(laughers);
        List<Laugher> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int selection = mRandom.nextInt(copyLaughers.size());
            if (copyLaughers.get(selection) != correctAnswer) {
                wrongAnswers.add(copyLaughers.get(selection));
                copyLaughers.remove(selection);
            }
        }
        return wrongAnswers;
    }

    private Laugher selectCorrectAnswer(List<Laugher> laughers) {
        int selection = mRandom.nextInt(laughers.size());
        return laughers.get(selection);
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
