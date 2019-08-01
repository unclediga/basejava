package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void updateElement(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void saveElement(int index, Resume r) {
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
    }

    @Override
    protected void deleteElement(int index) {
        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}