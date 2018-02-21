package com.gregspitz.cbbwhoslaughing.game;

import com.gregspitz.cbbwhoslaughing.UseCaseHandler;
import com.gregspitz.cbbwhoslaughing.UseCaseScheduler;
import com.gregspitz.cbbwhoslaughing.data.source.GameDataSource;
import com.gregspitz.cbbwhoslaughing.data.source.GameRepository;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;
import com.gregspitz.cbbwhoslaughing.game.domain.usecase.NewGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link GamePresenter}
 */

public class GamePresenterTest {
    private static final Laugher CORRECT_LAUGHER = new Laugher("1", "Jimbo");

    private static final List<Laugher> WRONG_LAUGHERS = new ArrayList<>();

    private static final List<Laugher> ALL_LAUGHERS = new ArrayList<>();

    private static final Game GAME = new Game(CORRECT_LAUGHER, WRONG_LAUGHERS);

    static {
        WRONG_LAUGHERS.add(new Laugher("2", "Kevin"));
        WRONG_LAUGHERS.add(new Laugher("3", "Art"));
        WRONG_LAUGHERS.add(new Laugher("4", "Tina"));
        ALL_LAUGHERS.addAll(WRONG_LAUGHERS);
        ALL_LAUGHERS.add(CORRECT_LAUGHER);
    }

    @Mock
    private GameRepository mGameRepository;

    @Mock
    private GameContract.View mGameView;

    @Captor
    private ArgumentCaptor<GameDataSource.GetLaughersCallback> mGetLaughersCallbackCaptor;

    @Captor
    private ArgumentCaptor<Game> mGameArgumentCaptor;

    private GamePresenter mGamePresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // The presenter won't update the view unless it is active
        when(mGameView.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToTheView() {
        mGamePresenter = setupGamePresenter();

        verify(mGameView).setPresenter(mGamePresenter);
    }

    @Test
    public void start_loadsGameIntoView() {
        mGamePresenter = setupGamePresenter();
        mGamePresenter.start();

        verify(mGameRepository).getLaughers(mGetLaughersCallbackCaptor.capture());
        // Verify loading indicator set
        InOrder inOrder = inOrder(mGameView);
        inOrder.verify(mGameView).setLoadingIndicator(true);

        // Trigger callback
        mGetLaughersCallbackCaptor.getValue().onLaughersLoaded(ALL_LAUGHERS);

        inOrder.verify(mGameView).setLoadingIndicator(false);
        verify(mGameView).showGame(mGameArgumentCaptor.capture());
        assertTrue(verifyLaughersInGame(mGameArgumentCaptor.getValue()));
    }

    @Test
    public void startWithoutProperGameLoading_showsFailedToLoadGameInView() {
        mGamePresenter = setupGamePresenter();
        mGamePresenter.start();

        verify(mGameRepository).getLaughers(mGetLaughersCallbackCaptor.capture());
        // Verify loading indicator set
        InOrder inOrder = inOrder(mGameView);
        inOrder.verify(mGameView).setLoadingIndicator(true);

        // Trigger callback
        mGetLaughersCallbackCaptor.getValue().onDataNotAvailable();

        inOrder.verify(mGameView).setLoadingIndicator(false);
        verify(mGameView).showFailedToLoadGame();
    }

    @Test
    public void correctGuees_showsCorrectGuessInView() {
        mGamePresenter = setupGamePresenter();
        mGamePresenter.start();
        verify(mGameRepository).getLaughers(mGetLaughersCallbackCaptor.capture());
        mGetLaughersCallbackCaptor.getValue().onLaughersLoaded(ALL_LAUGHERS);
        verify(mGameView).showGame(mGameArgumentCaptor.capture());

        Game createdGame = mGameArgumentCaptor.getValue();
        mGamePresenter.guessGame(createdGame.getCorrectAnswer());

        verify(mGameView).showRightAnswer();
    }

    @Test
    public void wrongGuess_showsWrongAnswerInView() {
        mGamePresenter = setupGamePresenter();
        mGamePresenter.start();
        verify(mGameRepository).getLaughers(mGetLaughersCallbackCaptor.capture());
        mGetLaughersCallbackCaptor.getValue().onLaughersLoaded(ALL_LAUGHERS);
        verify(mGameView).showGame(mGameArgumentCaptor.capture());

        Game createdGame = mGameArgumentCaptor.getValue();
        mGamePresenter.guessGame(createdGame.getWrongAnswers().get(0));

        verify(mGameView).showWrongAnswer();
    }

    private boolean verifyLaughersInGame(Game game) {
        List<Laugher> laughersInGame = new ArrayList<>();
        laughersInGame.add(game.getCorrectAnswer());
        laughersInGame.addAll(game.getWrongAnswers());
        return laughersInGame.containsAll(ALL_LAUGHERS);
    }

    private GamePresenter setupGamePresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        NewGame newGame = new NewGame(mGameRepository);
        return new GamePresenter(useCaseHandler, mGameView, newGame);
    }
}
