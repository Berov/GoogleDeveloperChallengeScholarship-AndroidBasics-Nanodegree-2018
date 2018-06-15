package bg.berov.sportnews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private static Preference numberOfItems, firstSport, secondSport, thitdSport, fourthSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            firstSport = findPreference(getString(R.string.first_key));
            bindPreferenceSummaryToValue(firstSport);

            secondSport = findPreference(getString(R.string.second_key));
            bindPreferenceSummaryToValue(secondSport);

            thitdSport = findPreference(getString(R.string.third_key));
            bindPreferenceSummaryToValue(thitdSport);

            fourthSport = findPreference(getString(R.string.fourth_key));
            bindPreferenceSummaryToValue(fourthSport);

            numberOfItems = findPreference(getString(R.string.items_number_key));
            bindPreferenceSummaryToValue(numberOfItems);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference == numberOfItems && Integer.valueOf(stringValue) < 5) {
                Toast.makeText(getActivity(), getString(R.string.minimum_value_is), Toast.LENGTH_LONG).show();
                return false;
            }

            if (preference == numberOfItems && Integer.valueOf(stringValue) > 50) {
                Toast.makeText(getActivity(), getString(R.string.maximum_value_is), Toast.LENGTH_LONG).show();
                return false;
            }


            preference.setSummary(stringValue);

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
