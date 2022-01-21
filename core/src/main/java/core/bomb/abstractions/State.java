package core.bomb.abstractions;

@FunctionalInterface
public interface State<T> {
    T nextState();
}
