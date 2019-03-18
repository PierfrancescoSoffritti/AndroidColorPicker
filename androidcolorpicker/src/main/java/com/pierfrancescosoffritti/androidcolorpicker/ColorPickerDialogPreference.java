package com.pierfrancescosoffritti.androidcolorpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

public class ColorPickerDialogPreference extends Preference implements ColorPickerSwatch.OnColorSelectedListener {

    public interface OnNewColorSelectedListener {
        void newColorSelected(ColorPickerDialogPreference dialog, @ColorInt int color);
    }

    private int[] colors = null;
    @ColorInt private int selectedColor;
    @ColorInt private int defaultColor;
    private int columnsCount;

    private OnNewColorSelectedListener listener;
    private ImageView colorPreview;

    public ColorPickerDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAttrs(attrs);

        setLayoutResource(R.layout.colorpicker_dialog_preference_preview);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerDialogPreference, 0, 0);
        try {
            int colorsResId = ta.getResourceId(R.styleable.ColorPickerDialogPreference_colors, -1);
            if(colorsResId == -1)
                throw new IllegalStateException("You must set the colors attribute.");
            colors = getContext().getResources().getIntArray(colorsResId);

            columnsCount = ta.getInt(R.styleable.ColorPickerDialogPreference_columnNumber, -1);
            if(columnsCount <= 0) {
                Log.i(getClass().getSimpleName(), "Columns number not set or <= 0. Using default: 6");
                columnsCount = 6;
            }
        } finally {
            ta.recycle();
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        colorPreview = holder.itemView.findViewById(R.id.color_preview);
        setColorPreview(selectedColor);
    }

    private void setColorPreview(int color) {
        Drawable[] colorDrawable = new Drawable[] { ContextCompat.getDrawable(getContext(), (R.drawable.color_picker_swatch)) };
        colorPreview.setImageDrawable(new ColorStateDrawable(colorDrawable, color));
    }

    private FragmentManager fragmentManager = null;

    public void setSupportFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private ColorPickerDialog dialog = null;

    @Override
    protected void onClick() {
        super.onClick();

        dialog = ColorPickerDialog
                .newInstance(R.string.color_picker_default_title, colors, selectedColor, columnsCount, ColorPickerDialog.SIZE_SMALL);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "cpd_colorPickerDialogPreference");

        dialog.setOnColorSelectedListener(this);

        dialog.dialogObserver = new ColorPickerDialog.DialogObserver() {
            @Override
            public void onPositiveButtonClicked() {
                persistInt(selectedColor);
                setColorPreview(selectedColor);

                if(listener != null)
                    listener.newColorSelected(ColorPickerDialogPreference.this, selectedColor);
            }

            @Override
            public void onNegativeButtonClicked() {
                selectedColor = getPersistedInt(defaultColor);
            }
        };
    }

    public void dismiss() {
        if(dialog != null)
            dialog.dismiss();
    }

    @Override
    public void onColorSelected(int color) {
        selectedColor = color;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue)
            selectedColor = getPersistedInt(defaultColor);
        else {
            selectedColor = (Integer) defaultValue;
            persistInt(selectedColor);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray ta, int index) {
        defaultColor = ta.getColor(index, -1);
        if(defaultColor == -1)
            throw new IllegalStateException("You must set a default color.");

        return defaultColor;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        // Check whether this Preference is persistent (continually saved)
        if (isPersistent()) {
            // No need to save instance state since it's persistent, use superclass state
            return superState;
        }

        // Create instance of custom BaseSavedState
        final SavedState myState = new SavedState(superState);
        // Set the state's value with the class member that holds current setting value
        myState.value = selectedColor;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // Check whether we saved the state in onSaveInstanceState
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save the state, so call superclass
            super.onRestoreInstanceState(state);
            return;
        }

        // Cast state to custom BaseSavedState and pass to superclass
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());

        // Set this Preference's widget to reflect the restored state
        selectedColor = myState.value;
    }

    public void setColors(int[] colors, int selectedColor) {
        if (this.colors != colors || this.selectedColor != selectedColor) {
            this.colors = colors;
            this.selectedColor = selectedColor;
        }
    }

    public void setColors(int[] colors) {
        if (this.colors != colors) {
            this.colors = colors;
        }
    }

    public void setSelectedColor(int color) {
        if (selectedColor != color) {
            selectedColor = color;
            setColorPreview(selectedColor);
        }
    }

    public int[] getColors() {
        return colors;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public OnNewColorSelectedListener getListener() {
        return listener;
    }

    public void setListener(OnNewColorSelectedListener listener) {
        this.listener = listener;
    }

    private static class SavedState extends BaseSavedState {
        int value;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel source) {
            super(source);
            value = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(value);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
    }
}
