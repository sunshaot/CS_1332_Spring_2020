import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

/**
 * Your implementation of an AVL.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Textbook
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot creat AVL with null!");
        } else {
            for (T node: data) {
                add(node);
            }
        }
    }

    /**
     * calculate Height & Balance Factor
     * @param curr current node
     */
    private void processHBF(AVLNode<T> curr) {
        int lh = height(curr.getLeft());
        int rh = height(curr.getRight());
        curr.setHeight(Math.max(lh, rh) + 1);
        curr.setBalanceFactor(lh - rh);
    }

    /**
     * rebalance the AVL tree
     * @param curr current node
     * @return new balanced node
     */
    private AVLNode<T> rebalance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() == 2) {
            if (curr.getLeft().getBalanceFactor() == -1) {
                curr.setLeft(rotateL(curr.getLeft()));
            }
            curr = rotateR(curr);
        } else if (curr.getBalanceFactor() == -2) {
            if (curr.getRight().getBalanceFactor() == 1) {
                curr.setRight(rotateR(curr.getRight()));
            }
            curr = rotateL(curr);
        }
        return curr;
    }

    /**
     * left rotation
     * @param curr current node
     * @return new node after rotation
     */
    private AVLNode<T> rotateL(AVLNode<T> curr) {
        AVLNode<T> subroot = curr.getRight();
        curr.setRight(subroot.getLeft());
        subroot.setLeft(curr);
        processHBF(curr);
        processHBF(subroot);
        return subroot;
    }

    /**
     * right rotation
     * @param curr current node
     * @return new node after rotation
     */
    private AVLNode<T> rotateR(AVLNode<T> curr) {
        AVLNode<T> subroot = curr.getLeft();
        curr.setLeft(subroot.getRight());
        subroot.setRight(curr);
        processHBF(curr);
        processHBF(subroot);
        return subroot;
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular AVL and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot add null to AVL!");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * The helper method to add the data
     * @param curr current node
     * @param data the data to add
     * @return the Node that will be set to the curr
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            AVLNode<T> added = new AVLNode<>(data);
            added.setHeight(0);
            added.setBalanceFactor(0);
            return added;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        processHBF(curr);
        curr = rebalance(curr);
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot remove null from AVL!");
        } else {
            AVLNode<T> dummy = new AVLNode<>(data);
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
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Error, the data is not in the AVL!");
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
                AVLNode<T> dummy2 = new AVLNode<>(data);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        processHBF(curr);
        curr = rebalance(curr);
        return curr;
    }

    /**
     * helper method to remove Predecessor
     * @param curr current node
     * @param dummy dummy node to keep the data
     * @return the successor node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        processHBF(curr);
        curr = rebalance(curr);
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot get null from AVL!");
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
    private T rGet(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Error, the data is not in the AVL!");
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
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot search null in AVL!");
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
    private boolean rContains(AVLNode<T> curr, T data) {
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
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * The helper method for height
     * @param curr current node
     * @return the height of AVL
     */
    private int height(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find all elements within a certain distance from the given data.
     * "Distance" means the number of edges between two nodes in the tree.
     *
     * To do this, first find the data in the tree. Keep track of the distance
     * of the current node on the path to the data (you can use the return
     * value of a helper method to denote the current distance to the target
     * data - but note that you must find the data first before you can
     * calculate this information). After you have found the data, you should
     * know the distance of each node on the path to the data. With that
     * information, you can determine how much farther away you can traverse
     * from the main path while remaining within distance of the target data.
     *
     * Use a HashSet as the Set you return. Keep in mind that since it is a
     * Set, you do not have to worry about any specific order in the Set.
     *
     * Note: We recommend 2 helper methods:
     * 1. One helper method should be for adding the nodes on the path (from
     * the root to the node containing the data) to the Set (if they are within
     * the distance). This helper method will also need to find the distance
     * between each node on the path and the target data node.
     * 2. One helper method should be for adding the children of the nodes
     * along the path to the Set (if they are within the distance). The
     * private method stub called elementsWithinDistanceBelow is intended to
     * be the second helper method. You do NOT have to implement
     * elementsWithinDistanceBelow. However, we recommend you use this method
     * to help implement elementsWithinDistance.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data     the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {
        if (data == null) {
            throw new IllegalArgumentException("Error, cannot find null in tree!");
        } else if (distance < 0) {
            throw new IllegalArgumentException("Error, distance cannot be negative!");
        } else {
            ArrayList<AVLNode<T>> recorder = new ArrayList<>();
            Set<T> set = new HashSet<>();
            try {
                root = pathToCenter(root, data, recorder);
            } catch (NoSuchElementException ex) {
                throw ex;
            }
            int centerIndex = recorder.size() - 1;
            for (int i = 0; i  <= distance; i++) {
                if (centerIndex - i >= 0) {
                    AVLNode<T> node = recorder.get(centerIndex - i);
                    int maxDistance = distance;
                    int curDistance = i;
                    elementsWithinDistanceBelow(node, maxDistance, curDistance, set);
                }
            }
            return set;
        }
    }

    /**
     * record the path to node
     * @param data the data we need to find
     * @param curr current node
     * @param record record set
     * @return the list of the path to that node
     * @throws java.util.NoSuchElementException is the data is not in the tree
     */
    private AVLNode<T> pathToCenter(AVLNode<T> curr, T data, List<AVLNode<T>> record) {
        if (curr == null) {
            throw new NoSuchElementException("Error, cannot find the data in AVL!");
        } else if (data.compareTo(curr.getData()) < 0) {
            record.add(curr);
            curr.setLeft(pathToCenter(curr.getLeft(), data, record));
        } else if (data.compareTo(curr.getData()) > 0) {
            record.add(curr);
            curr.setRight(pathToCenter(curr.getRight(), data, record));
        } else {
            record.add(curr);
            return curr;
        }
        return curr;
    }

    /**
     * You do NOT have to implement this method if you choose not to.
     * However, this will help with the elementsWithinDistance method.
     *
     * Adds data to the Set if the current node is within the maximum distance
     * from the target node. Recursively call on the current node's children to
     * add their data too if the children's data are also within the maximum
     * distance from the target node.
     *
     * @param curNode         the current node
     * @param maximumDistance the maximum distance allowed
     * @param currentDistance the distance between the current node and the
     *                        target node
     * @param currentResult   the current set of data within the maximum
     *                        distance
     */
    private void elementsWithinDistanceBelow(AVLNode<T> curNode,
                                             int maximumDistance,
                                             int currentDistance,
                                             Set<T> currentResult) {
        int nextDistance = currentDistance + 1;
        if (!(currentResult.contains(curNode.getData())) && currentDistance <= maximumDistance) {
            currentResult.add(curNode.getData());
            if (curNode.getLeft() != null) {
                elementsWithinDistanceBelow(curNode.getLeft(), maximumDistance, nextDistance, currentResult);
            }
            if (curNode.getRight() != null) {
                elementsWithinDistanceBelow(curNode.getRight(), maximumDistance, nextDistance, currentResult);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
