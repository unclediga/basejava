package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
    }

    @Override
    public void save(Resume r) {
        if (size == 0) {
            storage[0] = r;
            size = 1;
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            int index = getIndex(r.getUuid());
            if (index > 0) {
                System.out.println("Resume " + r.getUuid() + " already exist");
            } else {
                // JavaDoc:
                // index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1).
                // The insertion point is defined as the point at which the key would be inserted into the array:
                // the index of the first element greater than the key, or a.length
                // if all elements in the array are less than the specified key.
                index = -(index + 1);
                for (int i = size; i > index; i--) {
                    storage[i] = storage[i - 1];
                }
                storage[index] = r;
                size++;
            }
        }

    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            size--;
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
        }

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
