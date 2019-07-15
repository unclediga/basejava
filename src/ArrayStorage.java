import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int curSize = 0;


    void clear() {
        for (int i = 0; i < curSize; i++) {
            storage[i] = null;
        }
        curSize = 0;
    }

    void save(Resume r) {
        storage[curSize++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].uuid.equals(uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int i = 0;
        boolean flag = false; // true = элемент найден  и удалён
        // найти и удалить
        while( i < curSize){
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                flag = true;
                break;
            }
            i++;
        }
        // сместить хвост, закрыв null
        while (i < curSize-1){
            storage[i] = storage[i+1];
            i++;
        }
        if (flag) curSize--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, curSize);
    }

    int size() {
        return curSize;
    }
}
