package sh.miles.pineapple.collection.trie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutorTrie<F> {

    private static final char END = '$';

    private final TrieNode<F> root;

    public ExecutorTrie() {
        this.root = new TrieNode<>();
    }

    public void insert(String string, F function) {
        string = string + END;
        TrieNode<F> current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (current.hasNoEdge(at)) {
                if (i == string.length() - 1) {
                    current = current.insertEdge(at, new TrieNode<>(function));
                } else {
                    current = current.insertEdge(at, new TrieNode<>());
                }
                continue;
            }

            current = current.getChild(at);
        }
    }

    public Optional<F> findExecutor(@NotNull String string) {
        string = string + END;
        TrieNode<F> current = root;
        for (int i = 0; i < string.length(); i++) {
            char at = string.charAt(i);
            if (current.hasNoEdge(at)) {
                return Optional.empty();
            }

            current = current.getChild(at);
        }

        return Optional.of(current.getFunction());
    }

    private static class TrieNode<F> {
        private final Map<Character, TrieNode<F>> nodes = new HashMap<>();
        private final F function;

        public TrieNode() {
            this.function = null;
        }

        public TrieNode(F function) {
            this.function = function;
        }

        public TrieNode<F> insertEdge(char character, @NotNull final TrieNode<F> child) {
            nodes.put(character, child);
            return child;
        }

        public TrieNode<F> getChild(char character) {
            return nodes.get(character);
        }

        public boolean hasNoEdge(char character) {
            return !nodes.containsKey(character);
        }

        public boolean hasNoEdges() {
            return nodes.isEmpty();
        }

        @Nullable
        public F getFunction() {
            return function;
        }
    }

}

