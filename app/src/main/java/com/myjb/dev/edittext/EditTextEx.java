package com.myjb.dev.edittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Customize Copy, Paste, Cut, Share
 * Ref : https://developer.android.com/guide/topics/ui/menus.html
 */
public class EditTextEx extends android.support.v7.widget.AppCompatEditText implements ActionMode.Callback {

    public EditTextEx(Context context) {
        super(context);
        setCustomSelectionActionModeCallback(this);
    }

    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomSelectionActionModeCallback(this);
    }

    public EditTextEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomSelectionActionModeCallback(this);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.copy
                || id == android.R.id.cut
                || id == android.R.id.paste) {
            return true;
        } else if (id == android.R.id.selectAll) {
            setSelection(0, length());
            return true;
        }

        return super.onTextContextMenuItem(id);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        /**
         * Comment by JB.
         *
         * Customize copy, paste, cut, shareText and ect...
         */
        menu.removeItem(android.R.id.copy);
        menu.removeItem(android.R.id.paste);
        menu.removeItem(android.R.id.cut);
        menu.removeItem(android.R.id.shareText);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}