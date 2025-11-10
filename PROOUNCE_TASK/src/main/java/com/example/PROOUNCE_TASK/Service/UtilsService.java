package com.example.PROOUNCE_TASK.Service;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UtilsService {

    /**
     * Part D: Finds the first non-repeating character in a string.
     * Uses a LinkedHashMap to maintain insertion order and store frequencies.
     * This is an O(n) algorithm. [cite: 77]
     */
    public Character findFirstUniqueChar(String text) {
        
        // Part D: Handles edge case (empty) [cite: 80]
        if (text == null || text.isEmpty()) {
            return null;
        }

        // Use LinkedHashMap to maintain insertion order
        Map<Character, Integer> counts = new LinkedHashMap<>();

        // First pass: build frequency map (O(n))
        for (char c : text.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Second pass: find the first entry with a count of 1
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        // Part D: Handles edge case (no unique) [cite: 80]
        return null;
    }
}