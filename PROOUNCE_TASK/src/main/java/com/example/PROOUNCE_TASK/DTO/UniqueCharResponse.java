package com.example.PROOUNCE_TASK.DTO;

public class UniqueCharResponse {
    private Character firstUnique;

    public UniqueCharResponse(Character firstUnique) {
        this.firstUnique = firstUnique;
    }

    public Character getFirstUnique() {
        return firstUnique;
    }
    public void setFirstUnique(Character firstUnique) {
        this.firstUnique = firstUnique;
    }
}