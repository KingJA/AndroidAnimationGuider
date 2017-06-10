package example.kingja.animation;

/**
 * Description:TODO
 * Create Time:2017/6/9 9:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RandomPosition {
    private float x;
    private float y;
    private Orientation orientation;

    public RandomPosition(Orientation orientation, float x, float y) {
        this.orientation = orientation;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

}
