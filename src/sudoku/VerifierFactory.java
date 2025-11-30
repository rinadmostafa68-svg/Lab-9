package sudoku;

public class VerifierFactory {

    public static VerifierInterface getVerifier(int mode) {

        if (mode == 0) {
            return new SequentialVerifier();
        }

        if (mode == 3) {
            return new ThreeModeVerifier();
        }

        if (mode == 27) {
            return new TwentySevenModeVerifier();
        }
        throw new IllegalArgumentException("Mode must be 0, 3 or 27");
    }
}
