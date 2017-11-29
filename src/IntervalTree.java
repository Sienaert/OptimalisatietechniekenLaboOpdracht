import java.util.ArrayList;
import java.util.List;

public class IntervalTree {

    private class Node {
        Node left;
        Interval interval;
        int max;
        Node right;

        public Node(Node left, Interval interval, int maxEnd, Node right) {
            this.left = left;
            this.interval = interval;
            this.max = maxEnd;
            this.right = right;
        }

        @Override
        public String toString(){
            return "[" + interval.getLow() + "," + interval.getHigh() + "]";
        }
    }


    private Node root;

    public IntervalTree() {
    }

    public IntervalTree(List<Interval> list){
        for(Interval interval : list) add(interval);
    }

    public void add (Interval interval) {
        if (interval.getLow() >= interval.getHigh()) throw new IllegalArgumentException("The end " + interval.getHigh() + " should be greater than start " + interval.getLow());

        Node node = root;
        while (node != null) {
            node.max = (interval.getHigh() > node.max) ? interval.getHigh() : node.max;
            if (interval.getLow() < node.interval.getLow()) {
                if (node.left == null) {
                    node.left = new Node(null, interval , interval.getHigh(), null);
                    return;
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Node(null, interval , interval.getHigh(), null);
                    return;
                }
                node = node.right;
            }
        }
        root =  new Node(null, new Interval(interval.getLow(), interval.getHigh()) , interval.getHigh(), null);
    }

    public List<Node> findOverlapping(int value) {
        List<Node> list = new ArrayList<>();

        Node intervalNode = root;

        while (intervalNode != null) {
            if (intersection(value, intervalNode.interval.getLow(), intervalNode.interval.getHigh())) list.add(intervalNode);

            if (goLeft(value, intervalNode.left)) {
                intervalNode = intervalNode.left;
            } else {
                intervalNode = intervalNode.right;
            }
        }
        return list;
    }

    public List<Node> findOverlapping(int low, int high) {
        if (low >= high) throw new IllegalArgumentException("The end " + high + " should be greater than start " + low);

        List<Node> list = new ArrayList<>();

        Node intervalNode = root;

        while (intervalNode != null) {
            if (intersection(low, high, intervalNode.interval.getLow(), intervalNode.interval.getHigh())) list.add(intervalNode);

            if (goLeft(low, high, intervalNode.left)) {
                intervalNode = intervalNode.left;
            } else {
                intervalNode = intervalNode.right;
            }
        }
        return list;
    }

    private boolean intersection (int start, int end, int intervalStart, int intervalEnd) {
        return start < intervalEnd && end > intervalStart;
    }

    private boolean intersection (int value, int intervalStart, int intervalEnd) {
        return value <= intervalEnd && value >= intervalStart;
    }

    private boolean goLeft(int start, int end, Node intervalLeftSubtree) {
        return intervalLeftSubtree != null && intervalLeftSubtree.max > start;
    }

    private boolean goLeft(int value, Node intervalLeftSubtree) {
        return intervalLeftSubtree != null && intervalLeftSubtree.max >= value;
    }

    public void printIntervals(){
        System.out.println("Interval in order: ");
        inorderTraversal(root);
    }

    private void inorderTraversal( Node node )
    {
        if ( node == null )
            return;

        inorderTraversal( node.left );
        System.out.print( "[" + node.interval.getLow() + ","+node.interval.getHigh()+"]");
        inorderTraversal( node.right );

    }

    public boolean noOverlap(int low, int high) {
        if (low >= high) throw new IllegalArgumentException("The end " + high + " should be greater than start " + low);

        List<Node> list = new ArrayList<>();

        Node intervalNode = root;

        while (intervalNode != null) {
            if (intersection(low, high, intervalNode.interval.getLow(), intervalNode.interval.getHigh())) list.add(intervalNode);

            if (goLeft(low, high, intervalNode.left)) {
                intervalNode = intervalNode.left;
            } else {
                intervalNode = intervalNode.right;
            }
        }
        return list.isEmpty();
    }


}


