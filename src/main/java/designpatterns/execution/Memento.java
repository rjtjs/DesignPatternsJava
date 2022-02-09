package designpatterns.execution;

import lombok.NonNull;

/**
 * Memento pattern provides a mechanism to store the state of an object and restore it from the saved state.
 *
 * E.g., we want to save a player's progress during a game, and be able to restore their progress from the saved game.
 */
public class Memento {
    /**
     * The generic player.
     */
    public interface Player {
        void play();

        SavedScore saveScore();

        void restoreScore(SavedScore savedScore);

        /**
         * The "memento" object which stores the current state of this class.
         */
        class SavedScore {
            private final double score;

            SavedScore(final double score) {
                this.score = score;
            }

            private double getSavedScore() {
                return score;
            }
        }
    }

    /**
     * A concrete implementation of player.
     */
    public class DefaultPlayer implements Player {
        private double score = 0.0;

        @Override
        public void play() {
            this.score += Math.random() * 10;
            this.score = (this.score > 100) ? 0 : this.score;
            System.out.println(String.format("Current score is %.4g.", score));
        }

        /**
         * What it means to save the score for this type of player.
         *
         * @return {@link SavedScore} that contains the current score.
         */
        @Override
        public SavedScore saveScore() {
            System.out.println(String.format("Saving score %.4g.", score));
            return new SavedScore(this.score);
        }

        /**
         * Restores this player's score to the saved state.
         *
         * @param savedScore A {@link SavedScore} to restore from.
         */
        @Override
        public void restoreScore(@NonNull final Memento.Player.SavedScore savedScore) {
            this.score = savedScore.getSavedScore();
            System.out.println(String.format("Restored score to %.4g from saved.", score));
        }
    }
}



