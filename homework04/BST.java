import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Only from textbook
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot build BST with null!");
        } else {
            for (T node : data) {
                add(node);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot add null to BST!");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * The helper method to add the data
     * @param curr the current BST node
     * @param data the data to add
     * @return the Node that will be set to the curr
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot remove null from BST!");
        } else {
            BSTNode<T> dummy = new BSTNode<>(data);
            try {
                root = rRemove(root, data, dummy);
                return dummy.getData();
            } catch (NoSuchElementException ex) {
                throw ex;
            }
        }
    }

    /**
     * THe helper method to remove data
     * @param curr current node
     * @param data data that needs to be removed
     * @param dummy dummy node to keep the removed data
     * @return The node that been removed
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Error, the data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            size--;
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(data);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * helper method to remove successor
     * @param curr current node
     * @param dummy dummy node to keep the data
     * @return the successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot get null from BST!");
        } else {
            return rGet(root, data);
        }
    }

    /**
     * The helper method for get.
     * @param curr current node
     * @param data data that we want to find
     * @return the node that == data we want to get
     */
    private T rGet(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Error, the data is not in the BST!");
        } else if (data.compareTo(curr.getData()) < 0) {
            return rGet(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rGet(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot search null in BST!");
        } else {
            return rContains(root, data);
        }
    }

    /**
     * The helper method for contains method
     * @param curr current node
     * @param data the data we want to find
     * @return boolean value
     */
    private boolean rContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) < 0) {
            return rContains(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rContains(curr.getRight(), data);
        } else {
            return curr.getData().equals(data);
        }
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        rPreorder(root, list);
        return list;
    }

    /**
     * The helper method for preorder
     * @param node current node
     * @param list result list
     */
    private void rPreorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            rPreorder(node.getLeft(), list);
            rPreorder(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        rInorder(root, list);
        return list;
    }

    /**
     * The helper method for inorder
     * @param node current node
     * @param list result list
     */
    private void rInorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            rInorder(node.getLeft(), list);
            list.add(node.getData());
            rInorder(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        rPostorder(root, list);
        return list;
    }

    /**
     * The helper method for postorder
     * @param node current node
     * @param list result list
     */
    private void rPostorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            rPostorder(node.getLeft(), list);
            rPostorder(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!(q.isEmpty())) {
            BSTNode<T> curr = q.remove();
            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * The helper method for height
     * @param curr current node
     * @return the height of BST
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return 1 + Math.max(rHeight(curr.getLeft()), rHeight(curr.getRight()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   10   15   40
     *  /
     * 13
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 13] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<>();
        root = rMDP(root, list, 0);
        return list;
    }

    /**
     * The helper method for getMaxDataPerLevel
     * @param curr current node
     * @param list max data per level list
     * @param level level of the node
     * @return curr node
     */
    private BSTNode<T> rMDP(BSTNode<T> curr, List<T> list, int level) {
        if (curr == null) {
            return null;
        } else if (level == list.size()) {
            list.add(curr.getData());
            if (curr.getRight() != null) {
                curr.setRight(rMDP(curr.getRight(), list, level + 1));
            }
            if (curr.getLeft() != null) {
                curr.setLeft(rMDP(curr.getLeft(), list, level + 1));
            }
        } else {
            if (list.get(level).compareTo(curr.getData()) < 0) {
                list.set(level, curr.getData());
            }
            if (curr.getRight() != null) {
                curr.setRight(rMDP(curr.getRight(), list, level + 1));
            }
            if (curr.getLeft() != null) {
                curr.setLeft(rMDP(curr.getLeft(), list, level + 1));
            }
        }
        return curr;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}