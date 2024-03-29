package core.bomb.modules.t.two_bit;

import core.bomb.abstractions.State;

public enum TwoBitState implements State<TwoBitState> {
    SECOND_QUERY {
        @Override
        public TwoBitState nextState() {
            return THIRD_QUERY;
        }
    },
    THIRD_QUERY {
        @Override
        public TwoBitState nextState() {
            return SUBMIT;
        }
    },
    SUBMIT {
        @Override
        public TwoBitState nextState() {
            return SECOND_QUERY;
        }
    }
}
