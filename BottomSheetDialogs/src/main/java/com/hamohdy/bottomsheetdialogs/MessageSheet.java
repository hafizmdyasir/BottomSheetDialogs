package com.hamohdy.bottomsheetdialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.hamohdy.bottomsheetdialogs.databinding.MessageDialogBinding;

public class MessageSheet extends AutoExpandSheetDialog {

    private static final String ARG_NEGATIVE = "neg-res";
    private static final String ARG_POSITIVE = "pos-res";

    public MessageSheet() {}
    public static MessageSheet getInstance(@StringRes int titleRes, @StringRes int messageRes,
                                           @StringRes int negativeRes, @StringRes int positiveRes) {
        MessageSheet progressSheet = new MessageSheet();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TITLE, titleRes);
        arguments.putInt(ARG_MESSAGE, messageRes);
        arguments.putInt(ARG_NEGATIVE, negativeRes);
        arguments.putInt(ARG_POSITIVE, positiveRes);
        progressSheet.setArguments(arguments);
        return progressSheet;
    }

    public static MessageSheet getInstance(String titleRes, String messageRes, String negativeRes, String positiveRes) {
        MessageSheet progressSheet = new MessageSheet();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, titleRes);
        arguments.putString(ARG_MESSAGE, messageRes);
        arguments.putString(ARG_NEGATIVE, negativeRes);
        arguments.putString(ARG_POSITIVE, positiveRes);
        arguments.putBoolean("charSet", true);
        progressSheet.setArguments(arguments);
        return progressSheet;
    }
    
    private View.OnClickListener clickListener;
    private MessageDialogBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof View.OnClickListener)
            clickListener = (View.OnClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = MessageDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null)
            throw new IllegalArgumentException("Message and title resources are necessary");

        if (getArguments().getBoolean("charSet", false)) {

            String titleRes = getArguments().getString(ARG_TITLE, getString(R.string.error));
            String messageRes = getArguments().getString(ARG_MESSAGE, getString(R.string.error));
            String positiveRes = getArguments().getString(ARG_POSITIVE, getString(R.string.error));
            String negativeRes = getArguments().getString(ARG_NEGATIVE, getString(R.string.error));

            binding.title.setText(titleRes);
            binding.message.setText(messageRes);

            binding.cancel.setText(negativeRes);
            binding.done.setText(positiveRes);
        }

        else {

            int titleRes = getArguments().getInt(ARG_TITLE, R.string.error);
            int messageRes = getArguments().getInt(ARG_MESSAGE, R.string.error);
            int positiveRes = getArguments().getInt(ARG_POSITIVE, R.string.error);
            int negativeRes = getArguments().getInt(ARG_NEGATIVE, R.string.error);

            binding.title.setText(titleRes);
            binding.message.setText(messageRes);

            binding.cancel.setText(negativeRes);
            binding.done.setText(positiveRes);

        }

        binding.cancel.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onClick(v);
            dismiss();
        });
        binding.done.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onClick(v);
            dismiss();
        });
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
