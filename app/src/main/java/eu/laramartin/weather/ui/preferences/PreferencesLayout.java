package eu.laramartin.weather.ui.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.weather.R;
import eu.laramartin.weather.ui.common.Dialogs;
import eu.laramartin.weather.ui.common.TextUtils;

/**
 * Created by Lara on 30/10/2016.
 */

public class PreferencesLayout extends FrameLayout implements PreferencesView{

    private static final String LOG_TAG = PreferencesLayout.class.getCanonicalName();
    private Context context;
    @BindView(R.id.sys_of_units_settings_view)
    RelativeLayout sys_of_units_settings_layout;
    PreferencesPresenter presenter;
    @BindView(R.id.sys_of_units_selected_settings_text_view)
    TextView selectedUnitsTextView;

    public PreferencesLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.layout_preferences, this);
        ButterKnife.bind(this, view);
        presenter = new PreferencesPresenter(new Settings(context));
        presenter.bind(this);
        presenter.load();

        sys_of_units_settings_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showPreferencesItemListDialog(context, R.string.sys_of_units_prefs_subtitle, R.array.sys_of_units, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.selectedUnitsSystem(i);
                    }
                });
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.unbind();
    }

    @Override
    public void updateSelectedUnits(String unitsSystem) {
        selectedUnitsTextView.setText(TextUtils.setFirstCharInUppercase(unitsSystem));
    }
}
