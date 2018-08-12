package utils;

import java.util.HashSet;
import java.util.List;

public class DuplicateRemover {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
