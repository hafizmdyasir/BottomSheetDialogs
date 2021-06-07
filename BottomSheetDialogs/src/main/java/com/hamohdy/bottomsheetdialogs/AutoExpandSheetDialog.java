package com.hamohdy.bottomsheetdialogs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AutoExpandSheetDialog extends BottomSheetDialogFragment implements ViewTreeObserver.OnGlobalLayoutListener {

    protected static final String ARG_TITLE = "arg-title";
    protected static final String ARG_MESSAGE = "arg-message";

    private static final String ARG_AUTO_EXPAND = "auto-expand";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        boolean shouldExpand = false;
        if (getArguments() != null)
            shouldExpand = getArguments().getBoolean("ARG_SHOULD_EXPAND", true);

        if (!shouldExpand) return;

        BottomSheetDialog dialog = (BottomSheetDialog) AutoExpandSheetDialog.this.requireDialog();
        FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet == null) return;

        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setSkipCollapsed(true);
        behavior.setPeekHeight(0);
    }
}
