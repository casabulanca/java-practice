package ruiliu2.practice.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by casa on 2017/6/5.
 */
public class CollectionApplication {

    public static void main(String... args) {
        try {
            Map<String, String> maps = new HashMap<>();
            ArrayList array = new ArrayList();

            Class arrayClazz = array.getClass();
            Field[] arrayFields = arrayClazz.getDeclaredFields();
            for (Field item : arrayFields) {
                item.setAccessible(true);

                System.out.println(String.format("Array: Name: %s, Value: %s", item.getName(), item.get(array)));

            }

            Class mapsClazz = maps.getClass();
            Field[] mapFields = mapsClazz.getDeclaredFields();
            for (Field item : mapFields) {
                item.setAccessible(true);
                System.out.println(String.format("Maps: Name: %s, Value: %s", item.getName(), item.get(maps)));
            }

            for (int i = 0; i < 11; i++) {
                long start = System.currentTimeMillis();
                array.add(new Object());
                System.out.println(String.format("no: %s, cost: %s", i, System.currentTimeMillis() - start));
            }


            for (Field item : arrayFields) {
                item.setAccessible(true);

                System.out.println(String.format("Array: Name: %s, Value: %s", item.getName(), item.get(array)));
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
