package store;

import org.junit.jupiter.api.*;
import stepsApi.StepsStore;

public class StoreTest {

    StepsStore store = new StepsStore();

    @Test
    // проверяем, что магазин создан
    public void checkCreateStore(){
        store.createStore();
        store.readStore();
        store.deleteStore();
    }

    @Test
    // проверяем, что магазин удален
    public void checkDeleteStore(){
        store.createStore();
        store.deleteStore();
        store.readStore404();
    }
}