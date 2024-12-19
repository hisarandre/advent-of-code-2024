package com.challenge.adventofcode.twentyFour.day13;

import com.challenge.adventofcode.twentyFour.day13.Position;

public class Machine {

    Position buttonA;

    Position buttonB;

    Position prize;

    public Machine(Position buttonA, Position buttonB, Position prize) {
        this.buttonA = buttonA;
        this.buttonB = buttonB;
        this.prize = prize;
    }

    public Position getButtonA() {
        return buttonA;
    }

    public void setButtonA(Position buttonA) {
        this.buttonA = buttonA;
    }

    public Position getButtonB() {
        return buttonB;
    }

    public void setButtonB(Position buttonB) {
        this.buttonB = buttonB;
    }

    public Position getPrize() {
        return prize;
    }

    public void setPrize(Position prize) {
        this.prize = prize;
    }

}
