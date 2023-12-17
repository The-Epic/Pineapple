package sh.miles.pineapple.collection;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Trie {

    private static final char END = '$';

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public Trie(Collection<String> input) {
        this();
        for (final String s : input) {
            insert(s);
        }
    }

    public Trie(String... input) {
        this();
        for (final String s : input) {
            insert(s);
        }
    }

    public void insert(String string) {
        string = string + END;
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (current.hasNoEdge(at)) {
                current = current.insertEdge(at, new TrieNode());
                continue;
            }

            current = current.getChild(at);
        }
    }

    public boolean contains(String string) {
        string = string + END;
        return contains0(string);
    }

    public boolean contains(String string, boolean partial) {
        if (!partial) {
            string = string + END;
        }
        return contains0(string, !partial);
    }

    private boolean contains0(String string) {
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (current.hasNoEdge(at)) {
                return false;
            }

            current = current.getChild(at);
        }
        return true;
    }

    private boolean contains0(String string, boolean pure) {
        char lastKey = ' ';
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (current.hasNoEdge(at)) {
                return false;
            }

            current = current.getChild(at);
            lastKey = at;
        }

        if (lastKey == END && pure) {
            return true;
        }

        // no need for pure comparison we know its false
        return lastKey != END;
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

        public boolean hasNoEdge(char character) {
            return !nodes.containsKey(character);
        }
    }

}
