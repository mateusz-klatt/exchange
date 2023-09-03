package ee.klatt;

public enum Side {
    Buy(1),
    Sell(-1);
    private final int value;

    Side(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static Side fromValue(int value) {
        for (Side side : Side.values()) {
            if (side.value== value) {
                return side;
            }
        }
        throw new IllegalArgumentException("Invalid Side value: " + value);
    }
}
