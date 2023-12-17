package sh.miles.pineapple.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    private static final char END = '$';

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String string) {
        string = string + END;
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (!current.hasEdge(at)) {
                current = current.insertEdge(at, new TrieNode());
                continue;
            }

            current = current.getChild(at);
        }
    }

    public boolean contains(String string) {
        string = string + END;
        return containsPartial(string);
    }

    public boolean containsPartial(String string) {
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (!current.hasEdge(at)) {
                return false;
            }

            current = current.getChild(at);
        }
        return true;
    }

    private static class TrieNode {
        public final Map<Character, TrieNode> nodes = new HashMap<>();

        public TrieNode insertEdge(char character, @NotNull final TrieNode child) {
            nodes.put(character, child);
            return child;
        }

        public TrieNode getChild(char character) {
            return nodes.get(character);
        }

        public boolean hasEdge(char character) {
            return nodes.containsKey(character);
        }
    }

}
