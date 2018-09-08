package com.sergtm.treeView;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private List<TreeNode> children;
    private boolean leaf;
    private String text;

    public TreeNode(String text){
        this(text, true, new ArrayList<>());
    }

    public TreeNode(String text, boolean leaf) {
        this(text, leaf, new ArrayList<>());
    }

    public TreeNode(String text, boolean leaf, List<TreeNode> children){
        this.text = text;
        this.leaf = leaf;
        this.children = children;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public String getText() {
        return text;
    }

    public boolean isLeaf() {
        return leaf;
    }
}
