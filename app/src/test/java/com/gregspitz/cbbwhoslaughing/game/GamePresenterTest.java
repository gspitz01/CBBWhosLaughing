package com.gregspitz.cbbwhoslaughing.game;

import com.gregspitz.cbbwhoslaughing.UseCaseHandler;
import com.gregspitz.cbbwhoslaughing.UseCaseScheduler;
import com.gregspitz.cbbwhoslaughing.data.source.GameRepository;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Game;
import com.gregspitz.cbbwhoslaughing.game.domain.model.Laugher;
import com.gregspitz.cbbwhoslaughing.game.domain.usecase.NewGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link GamePresenter}
 */

public class GamePresenterTest {
    private static final Laugher CORRECT_LAUGHER = new Laugher("1", "Jimbo");

    private static final List<Laugher> WRONG_LAUGHERS = new ArrayList<>();

    private static final Game GAME = new Game(CORRECT_LAUGHER, WRONG_LAUGHERS);

    static {
        WRONG_LAUGHERS.add(new Laugher("2", "Kevin"));
        WRONG_LAUGHERS.add(new Laugher("3", "Art"));
        WRONG_LAUGHERS.add(new Laugher("4", "Tina"));
    }

    @Mock
    private GameRepository mGameRepository;

    @Mock
    private GameContract.View mGameView;

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
        // TODO: make this test work
        mGamePresenter = setupGamePresenter();
        mGamePresenter.start();

        // Verify loading indicator set
        InOrder inOrder = inOrder(mGameView);
        inOrder.verify(mGameView).setLoadingIndicator(true);

        inOrder.verify(mGameView).setLoadingIndicator(false);
        verify(mGameView).showGame(GAME);
    }

    private GamePresenter setupGamePresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        NewGame newGame = new NewGame(mGameRepository);
        return new GamePresenter(useCaseHandler, mGameView, new NewGame(mGameRepository));
    }
}
