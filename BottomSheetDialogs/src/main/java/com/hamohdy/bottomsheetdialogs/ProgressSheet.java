package com.hamohdy.bottomsheetdialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.hamohdy.bottomsheetdialogs.databinding.ProgressDialogBinding;

import static android.view.View.GONE;

public class ProgressSheet extends AutoExpandSheetDialog {

    private static final String ARG_SHOW_CANCEL = "arg-cancel";
    private static final String ARG_INDETERMINATE = "arg-indeterminate";

    public ProgressSheet() {}

    public static ProgressSheet getInstance(@StringRes int titleRes, @StringRes int messageRes,
                                            boolean showCancelButton, boolean indeterminate) {
        ProgressSheet progressSheet = new ProgressSheet();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TITLE, titleRes);
        arguments.putInt(ARG_MESSAGE, messageRes);
        arguments.putBoolean(ARG_SHOW_CANCEL, showCancelButton);
        arguments.putBoolean(ARG_INDETERMINATE, indeterminate);
        progressSheet.setArguments(arguments);
        return progressSheet;
    }

    public static ProgressSheet getInstance(String titleRes, String messageRes,
                                            boolean showCancelButton, boolean indeterminate) {
        ProgressSheet progressSheet = new ProgressSheet();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, titleRes);
        arguments.putString(ARG_MESSAGE, messageRes);
        arguments.putBoolean(ARG_SHOW_CANCEL, showCancelButton);
        arguments.putBoolean(ARG_INDETERMINATE, indeterminate);
        arguments.putBoolean("charSet", true);
        progressSheet.setArguments(arguments);
        return progressSheet;
    }

    private View.OnClickListener clickListener;
    private ProgressDialogBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof View.OnClickListener)
            clickListener = (View.OnClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = ProgressDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        boolean showCancel = true;
        boolean indeterminate = true;

        if (getArguments() == null)
            throw new IllegalArgumentException("Message and title resources are necessary");


        if (getArguments().getBoolean("charSet", false)) {
            String titleRes = getArguments().getString(ARG_TITLE, getString(R.string.error));
            String messageRes = getArguments().getString(ARG_MESSAGE, getString(R.string.error));

            binding.title.setText(titleRes);
            binding.message.setText(messageRes);
        } else {
            int titleRes = getArguments().getInt(ARG_TITLE, R.string.error);
            int messageRes = getArguments().getInt(ARG_MESSAGE, R.string.error);

            binding.title.setText(titleRes);
            binding.message.setText(messageRes);
        }
        showCancel = getArguments().getBoolean(ARG_SHOW_CANCEL, true);
        indeterminate = getArguments().getBoolean(ARG_INDETERMINATE, true);

        binding.loading.setIndeterminate(indeterminate);
        binding.progressValue.setVisibility(indeterminate ? GONE : View.VISIBLE);

        binding.cancel.setVisibility(showCancel ? View.VISIBLE : GONE);
        binding.cancel.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onClick(v);
            dismiss();
        });

    }

    public void publishProgress(int progress) {
        if (binding == null) return;

        binding.loading.setProgress(progress, true);
        String valueText = progress + "/100";
        binding.progressValue.setText(valueText);
    }

    public int getProgress() {
        if (binding == null) return 0;
        return binding.loading.getProgress();
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
