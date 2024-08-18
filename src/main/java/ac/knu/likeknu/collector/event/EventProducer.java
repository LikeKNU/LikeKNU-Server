package ac.knu.likeknu.collector.event;

public interface EventProducer<T> {

    void produce(T event);
}
