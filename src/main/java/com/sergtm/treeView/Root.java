package com.sergtm.treeView;

import java.util.List;

public class Root extends TreeNode {
    private boolean success;

    public Root(String text, boolean success, List<TreeNode> children){
        super(text, false, children);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
