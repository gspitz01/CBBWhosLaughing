package com.gregspitz.cbbwhoslaughing.game.domain.model;

import java.util.List;

/**
 * Immutable model class for a single Game
 */

public class Game {
    private Laugher mCorrectAnswer;
    private List<Laugher> mWrongAnswers;

    public Game(Laugher correctAnswer, List<Laugher> wrongAnswers) {
        mCorrectAnswer = correctAnswer;
        mWrongAnswers = wrongAnswers;
    }

    public Laugher getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public List<Laugher> getWrongAnswers() {
        return mWrongAnswers;
    }
}
