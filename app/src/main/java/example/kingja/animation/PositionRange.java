package example.kingja.animation;

import java.util.Random;

/**
 * Description:TODO
 * Create Time:2017/6/9 9:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PositionRange {
    private float rangeFrom;
    private float rangeTo;

    public PositionRange(float rangeFrom, float rangeTo) {
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public float getRandomPosition() {
        return rangeFrom + new Random().nextInt((int) (rangeTo - rangeFrom));
    }
}
